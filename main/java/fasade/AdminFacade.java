package fasade;

import dao.DBDAO.CompaniesDBDAO;
import dao.DBDAO.CouponsDBDAO;
import dao.DBDAO.CustomersDBDAO;
import entites.Companies;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;
import exceptions.NotLoginException;

import java.util.ArrayList;

public class AdminFacade extends ClientFacade {

    private ArrayList<Companies> companiesDelete = null;

    private boolean isLogin = false;

    //login Boolean(String email,String password)
    @Override
    public boolean login(String email, String password) {
        String theEmail = "admin@admin.com";
        String thePassword = "admin";
        isLogin = (email.equals(theEmail) && password.equals(thePassword));
        return isLogin;
    }

    //  not double email
    //  not double name
    //public void addCompany(Companies company)
    public void addCompany(Companies company) throws AlreadyExistException , NotLoginException {
        if(!isLogin){
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
    public void updateCompany(Companies company) throws NotExistException ,NotLoginException{
        if(!isLogin){
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

    //  delete coupones company
    //  delete coupones customers
    //public void deleteCompany(Companies companies)

    //  get back all delete componies
    //  get back one delete compony (id)
    //public ArrayList<Companies> getAllCompanies()
    //public Companies getOneCompany(Long companyId)
    //public void addCustomer(Customer customer)
    //public void updeateCustomer(Customer customer)
    //public void deleteCustomer(Long customerId)
    //public ArrayList<Customer> getAllCustomers()
    //public Customer getOneCustomer(Long customerId)
}
