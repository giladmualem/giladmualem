package fasade;

import dao.CompaniesDAO;
import dao.CompaniesDBDAO;
import dao.CouponsDBDAO;
import dao.CustomersDBDAO;
import entites.Companies;
import entites.Customer;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;
import exceptions.NotLoginException;
import pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminFacade extends ClientFacade {

    private ConnectionPool pool;
    private ArrayList<Companies> companiesDelete = null;

    //  constructor
    public AdminFacade(String email, String password) {
        companiesDAO = new CompaniesDBDAO();
        couponsDAO = new CouponsDBDAO();
        customersDAO = new CustomersDBDAO();
        login(email, password);
        pool = ConnectionPool.getInstance();
    }

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
    //public void addCompany(Companies companies)
    public void addCompany(Companies companies) throws NotLoginException, AlreadyExistException {
        if (!isLogin) throw new NotLoginException("you need to login");
        Boolean byName = isNameCompanyExists(companies.getName());
        Boolean byMail = isEmailCompanyExists(companies.getEmail());
        if (byMail || byName) {
            throw new AlreadyExistException("the email or the name Company is already taken");
        }
        System.out.println(companiesDAO.addCompany(companies));
    }

    public Boolean isNameCompanyExists(String name) {
        return isCompanyExistsWith("SELECT ID FROM coupons.companies WHERE NAME=?", name);
    }

    public Boolean isEmailCompanyExists(String email) {
        return isCompanyExistsWith("SELECT ID FROM coupons.companies WHERE EMAIL=?", email);
    }

    public Boolean isCompanyExistsWith(String sql, String val) {
        Boolean isExist = false;
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setString(1, val);
            ResultSet resultSet = prstm.executeQuery();
            if (resultSet.next()) {
                isExist = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return isExist;
    }


    //  not update id
    //  not update name
    //public void updateCompany(Companies companies)
    public void updateCompany(Companies companies) throws NotExistException {
        if (!isValid(companies)) {
            return;
        }
        if (confirmedCompany(companies.getId(), companies.getName())) {
            companiesDAO.updateCompany(companies);
        } else {
            throw new NotExistException("you put a wrong id or name company");
        }
    }

    public Boolean confirmedCompany(Long id, String name) {
        Boolean isExist = null;
        String sql = "SELECT * FROM coupons.companies WHERE ID=? AND NAME=?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, id);
            prstm.setString(2, name);
            ResultSet resultSet = prstm.executeQuery();
            if (resultSet.next()) {
                isExist = true;
            } else {
                isExist = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return isExist;
    }

    public Boolean isValid(Companies companies) {
        Boolean valid = true;
        if (companies == null ||
                companies.getEmail().isEmpty() ||
                companies.getPassword().isEmpty() ||
                companies.getName().isEmpty()) {
            valid = false;
        }
        return valid;
    }

    //  delete coupones company
    //  delete coupones customers
    //public void deleteCompany(Companies companies)
    public void deleteCompany(Companies companies) {
        try {
            companiesDAO.deletCompany(companies.getId());
            if (companiesDelete == null) {
                //  save the delete company
                companiesDelete = new ArrayList<>();
            }
            companiesDelete.add(companies);
        } catch (NotExistException e) {
            e.printStackTrace();
        }
    }

    //  get back all delete componies
    public void getBackAllDeleteComponies() {
        for (Companies companies : companiesDelete) {
            String name = companies.getName();
            String email = companies.getEmail();
            String password = companies.getPassword();
            Companies company = new Companies(name, email, password);
            try {
                companiesDAO.addCompany(company);
            } catch (AlreadyExistException e) {
                e.printStackTrace();
            }
        }
    }

    //  get back one delete compony (id)
    public void getBackOneDeleteCompany(Long id) {

    }

    //public ArrayList<Companies> getAllCompanies()
    //public Companies getOneCompany(Long companyId)
    //public void addCustomer(Customer customer)
    //public void updeateCustomer(Customer customer)
    //public void deleteCustomer(Long customerId)
    //public ArrayList<Customer> getAllCustomers()
    //public Customer getOneCustomer(Long customerId)

}
