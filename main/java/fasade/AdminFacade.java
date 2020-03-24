package fasade;

import dao.DBDAO.CompaniesDBDAO;
import dao.DBDAO.CouponsDBDAO;
import dao.DBDAO.CustomersDBDAO;
import entites.Companies;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;

import java.util.ArrayList;

public class AdminFacade extends ClientFacade {

    private ArrayList<Companies> companiesDelete = null;

    //  constructor
    public AdminFacade(String email, String password) {
        companiesDAO = new CompaniesDBDAO();
        couponsDAO = new CouponsDBDAO();
        customersDAO = new CustomersDBDAO();
        login(email, password);
    }

    //login Boolean(String email,String password)
    @Override
    public Boolean login(String email, String password) {
        String theEmail = "admin@admin.com";
        String thePassword = "admin";
        isLogin = (email.equals(theEmail) && password.equals(thePassword));
        return isLogin;
    }

    private void doExist(Companies company) throws AlreadyExistException {
    }

    //  not double email
    //  not double name
    //public void addCompany(Companies company)
    public void addCompany(Companies company) throws AlreadyExistException {
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        if (companiesDAO.isCompanyExists(company.getEmail(), company.getPassword()) != null) {
            throw new AlreadyExistException("this company is already exist");
        }
        if (companiesDBDAO.isNameCompanyExists(company.getName())) {
            throw new AlreadyExistException("this company name is already taken");
        }
        if (companiesDBDAO.isEmailCompanyExists(company.getEmail())) {
            throw new AlreadyExistException("this company email is already taken");
        }
        companiesDAO.addCompany(company);
    }

    //  not update id
    //  not update name
    //public void updateCompany(Companies company)
    public void updateCompany(Companies company) throws NotExistException {
        Companies com = companiesDAO.getOneCompany(company.getId());
        if (com == null) {
            throw new NotExistException("there is not such a company");
        }
        com.setEmail(company.getEmail());
        com.setPassword(company.getPassword());
        companiesDAO.updateCompany(com);
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
