package fasade;

import entites.Companies;
import entites.Coupon;
import entites.Customer;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;
import exceptions.NotLoginException;

import java.util.ArrayList;
import java.util.List;

public class AdminFacade extends ClientFacade {
    private Boolean isLogin = false;
    private List<Companies> deleteCompanies = null;
    private List<Customer> deleteCustomers = null;


    // let the admin to login
    @Override
    public Boolean login(String email, String password) {
        String theEmail = "admin@admin.com";
        String thePassword = "admin";
        isLogin = (email.equals(theEmail) && password.equals(thePassword));
        return isLogin;
    }

    //  you can add company unless email or name is already taken
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

    // you can update company except id and name
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

    //  delete company with all the coupons and here purchase
    //  save the company and here coupons if you regret
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
        if (com != null) {
            com.setCoupons(couponsDBDAO.getAllCompnyCoupons(com.getId()));
            if (com != null) {
                if (com.getCoupons() != null) {
                    for (Coupon coupon : com.getCoupons()) {
                        purchaseDBDAO.deletePurchaseCouponsByCouponId(coupon.getId());
                        couponsDBDAO.delete(coupon.getId());
                    }
                    com.setCoupons(null);
                }
                companiesDBDAO.deleteCompany(com.getId());
                if (deleteCompanies == null) {
                    deleteCompanies = new ArrayList<>();
                }
                deleteCompanies.add(com);
            }
        }
    }


    // bring back the company and here coupons
    public void returnOneDeleteCompany(Long id) throws NotExistException {
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

    //  return a company
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

    // return all the companies in the date ,not deleted
    public List<Companies> getAllCompanies() throws NotLoginException {
        if (!isLogin) {
            throw new NotLoginException("you nees to login");
        }
        return companiesDBDAO.getAllCompanies();
    }

    // add customer with unique email
    public void addCustomer(Customer customer) throws NotLoginException, AlreadyExistException {
        // only admin can preform
        if (!isLogin) {
            throw new NotLoginException("you need to login");
        }
        // email should by unique
        if (customersDBDAO.isCustemerExistByEmail(customer.getEmail())) {
            throw new AlreadyExistException("there already exists customer");
        }
        customersDBDAO.addCustomer(customer);
    }

    // update customer - email need to by unique
    public void updeateCustomer(Customer customer) throws NotLoginException, NotExistException, AlreadyExistException {
        // only admin can preform
        if (!isLogin) {
            throw new NotLoginException("you need to login");
        }
        // find a customer by id
        if (customer.getId() == null) {
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

    // delete customer with is purchase
    // and save the customer in case you regret
    public void deleteCustomer(Long customerId) throws NotLoginException, NotExistException {
        //  login
        if (!isLogin) {
            throw new NotLoginException("you need to login");
        }
        Customer customer = customersDBDAO.getOneCustomer(customerId);
        if (customer != null) {
            //  delete custemer
            purchaseDBDAO.deletePurchaseCouponsByCustomerId(customerId);
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

    // return all the customer in the date - not deleted
    public List<Customer> getAllCustomers() throws NotLoginException {
        if (!isLogin) {
            throw new NotLoginException("you need to login");
        }
        return customersDBDAO.allCustomer();

    }

    //  return one deleted customer back to the database  by id
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

    //  return all the deleted customers back
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
    //  return one customer by id
    public Customer getOneCustomer(Long customerId) throws NotLoginException {
        if (!isLogin) {
            throw new NotLoginException("you nees to login");
        }
        return customersDBDAO.getOneCustomer(customerId);
    }
}

