package fasade;

import entites.Companies;
import entites.Coupon;
import entites.Customer;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;
import exceptions.NotLoginException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AdminFacade extends ClientFacade {
    private Boolean isLogin = false;
    private ArrayList<Companies> deleteCompanies = null;
    private ArrayList<Customer> deleteCustomers = null;


    //login Boolean(String email,String password)
    @Override
    public Boolean login(String email, String password) {
        String theEmail = "admin@admin.com";
        String thePassword = "admin";
        isLogin = (email.equals(theEmail) && password.equals(thePassword));
        return isLogin;
    }

    //  not double email
    //  not double name
    //public void addCompany(Companies company)
    public void addCompany(Companies company) throws AlreadyExistException, NotLoginException {
        if (!isLogin) {
            throw new NotLoginException("you have to log in");
        }
        if (companiesDBDAO.isNameCompanyExists(company.getName())) {
            throw new AlreadyExistException("this company name is already taken");
        }
        if (companiesDBDAO.isEmailCompanyExists(company.getEmail())) {
            throw new AlreadyExistException("this company email is already taken");
        }
        companiesDBDAO.addCompany(company);
    }

    //  not update id
    //  not update name
    //public void updateCompany(Companies company)
    public void updateCompany(Companies company) throws NotExistException, NotLoginException {
        if (!isLogin) {
            throw new NotLoginException("you have to log in");
        }
        Companies companyById = companiesDBDAO.getOneCompany(company.getId());
        if (companyById == null) {
            throw new NotExistException("there is not such a company");
        }
        companyById.setEmail(company.getEmail());
        companyById.setPassword(company.getPassword());
        companiesDBDAO.updateCompany(companyById);
    }


    //                                  not finish
    //  delete coupones company
    //  delete coupones customers
    public void deleteCompany(Companies companies) throws NotLoginException, NotExistException {
        if (!isLogin) {
            throw new NotLoginException("you need to login first");
        }
        if (!companiesDBDAO.isCompanyExists(companies.getEmail(), companies.getPassword())) {
            throw new NotExistException("there is no company");
        }
        Companies com = companiesDBDAO.getByEmailAndPassword(companies.getEmail(), companies.getPassword());
        if (deleteCompanies == null) {
            deleteCompanies = new ArrayList<>();
        }
        com.setCoupons(couponsDBDAO.getAllCompnyCoupons(companies.getId()));
        if (com != null) {
            for (Coupon c : com.getCoupons()) {

              //  ArrayList<Long> customerId = purchaseDBDAO.customerPurchase(c.getId());
                //customerId.forEach(x -> purchaseDBDAO.deletePurchase(x, c.getId()));

                couponsDBDAO.delete(c.getId());
            }

            //deleteCompanies.add(com);
            //companiesDBDAO.deleteCompany(com.getId());
        }
    }

    //                          need to return the coupon of the company
    //  get back one delete compony (id)
    public void returnOneDeleteCompony(Long id) throws NotExistException {
        Companies back = null;
        for (int i = 0; deleteCompanies != null && i < deleteCompanies.size(); i++) {
            if (deleteCompanies.get(i).getId() == id) {
                back = deleteCompanies.remove(i);
                back.setId(null);
                try {
                    addCompany(back);
                } catch (AlreadyExistException e) {
                    e.printStackTrace();
                } catch (NotLoginException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public Companies getOneCompany(Long id) throws NotLoginException, NotExistException {
        if (!isLogin) {
            throw new NotLoginException("you need to login");
        }
        Companies theCompany = companiesDBDAO.getOneCompany(id);
        if (theCompany == null) {
            throw new NotExistException("there is no such a company");
        }
        theCompany.setCoupons(couponsDBDAO.getAllCompnyCoupons(id));
        return theCompany;
    }

    //  get back all delete componies
    public ArrayList<Companies> getAllCompanies() throws NotLoginException {
        if (!isLogin) {
            throw new NotLoginException("you nees to login");
        }
        return companiesDBDAO.getAllCompanies();
    }

    //public void addCustomer(Customer customer)
    public void addCustomer(Customer customer) throws NotLoginException, AlreadyExistException {
        // only admin can preform
        if (!isLogin) {
            throw new NotLoginException("you need to login");
        }
        // email should by unique
        if (customersDBDAO.isCustemerExistByEmail(customer.getEmail())) {
            throw new AlreadyExistException("there already exists customer");
        }
        //customersDBDAO.addCustomer(customer);
        customersDBDAO.addCustomer(customer);
    }

    //public void updeateCustomer(Customer customer)
    public void updeateCustomer(Customer customer) throws NotLoginException, NotExistException, AlreadyExistException {
        // only admin can preform
        if (!isLogin) {
            throw new NotLoginException("you need to login");
        }
        // find a customer by id
        if (customer.getId() == 0) {
            throw new NotExistException("send customer with id");
        }
        Customer orignal = customersDBDAO.getOneCustomer(customer.getId());
        //  update custemer
        //  check if email update - email unique
        if (orignal != null) {
            // updeta the email (unique email)
            if (!orignal.getEmail().equals(customer.getEmail())) {
                if (customersDBDAO.isCustemerExistByEmail(customer.getEmail())) {
                    throw new AlreadyExistException("you need to give a new email, this is already taken");
                }
                orignal.setEmail(customer.getEmail());
            }

            orignal.setFirstName(customer.getFirstName());
            orignal.setLastName(customer.getLastName());
            orignal.setPassword(customer.getPassword());
            customersDBDAO.updateCustomer(orignal);
        } else {
            // not find
            throw new NotExistException("there is no such customer");
        }
    }

    //public void deleteCustomer(Long customerId)
    public void deleteCustomer(Long customerId) throws NotLoginException, NotExistException {
        //  login
        if (!isLogin) {
            throw new NotLoginException("you need to login");
        }
        Customer customer = customersDBDAO.getOneCustomer(customerId);
        if (customer != null) {
            //  delete custemer
            customersDBDAO.deleteCustomer(customerId);
            //  save custemer
            if (deleteCustomers == null) {
                deleteCustomers = new ArrayList<>();
            }
            deleteCustomers.add(customer);
        } else {
            throw new NotExistException("there not such a customer");
        }
        // delete purchase
    }

    //public ArrayList<Customer> getAllCustomers()
    public ArrayList<Customer> getAllCustomers() throws NotLoginException {
        if (!isLogin) {
            throw new NotLoginException("you need to login");
        }
        ArrayList<Customer> all = customersDBDAO.allCustomer();
        return null;
    }

    public void returnDeleteCustomer(Long id) throws NotLoginException, NotExistException {
        if (!isLogin) {
            throw new NotLoginException("you need to login");
        }
        if (deleteCustomers == null) {
            throw new NotExistException("there is no customer to return");
        }
        for (int i = 0; i < deleteCustomers.size(); i++) {
            if (id.equals(deleteCustomers.get(i).getId())) {
                Customer back = deleteCustomers.remove(i);
                back.setId(null);
                customersDBDAO.addCustomer(back);
                break;
            }
        }
    }

    public void returnDeletesCustomers() throws NotLoginException, NotExistException {
        if (!isLogin) {
            throw new NotLoginException("you need to login");
        }
        if (deleteCustomers == null) {
            throw new NotExistException("there is no customer to return");
        }
        while (deleteCustomers.size() > 0) {
            Customer back = deleteCustomers.remove(0);
            back.setId(null);
            try {
                addCustomer(back);
            } catch (AlreadyExistException e) {
                e.printStackTrace();
            }
        }
    }

    public Customer getOneCustomer(Long customerId) throws NotLoginException {
        if (!isLogin) {
            throw new NotLoginException("you nees to login");
        }
        return customersDBDAO.getOneCustomer(customerId);
    }
}

