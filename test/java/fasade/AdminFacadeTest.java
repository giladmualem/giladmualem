package fasade;

import entites.Companies;
import entites.Coupon;
import entites.Customer;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;
import exceptions.NotLoginException;
import org.junit.Test;
import pool.ConnectionPool;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class AdminFacadeTest {


    private AdminFacade adminFacade = new AdminFacade();

    @Test
    public void login() {
        System.out.println(adminFacade.login("admin@admin.com", "admin"));
    }

    @Test
    public void addCompany() {
        adminFacade.login("admin@admin.com", "admin");
        Companies com = new Companies("sano", "sano@.co.il", "1234");
        try {
            adminFacade.addCompany(com);
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        } catch (NotLoginException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateCompany() {
        adminFacade.login("admin@admin.com", "admin");
        Companies company = new Companies(5, "tnuva", "tnuva@co.il", "9876");
        try {
            adminFacade.updateCompany(company);
        } catch (NotExistException e) {
            e.printStackTrace();
        } catch (NotLoginException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteCompany() {
        adminFacade.login("admin@admin.com", "admin");
        //Companies com = new Companies("zer4u", "zer4u@.co.il", "ccc");
        try {
            Companies com = adminFacade.getOneCompany(2L);
            System.out.println(com +" deleted");
            adminFacade.deleteCompany(com);
        } catch (NotLoginException e) {
            System.out.println(e.getMessage());
        } catch (NotExistException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void returnOneDeleteCompony() {
        adminFacade.login("admin@admin.com", "admin");
        Companies companies = new Companies(2, "sano", "tnuva@co.il", "9876");
        try {
            adminFacade.deleteCompany(companies);
        } catch (NotLoginException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        }
        try {
            adminFacade.returnOneDeleteCompony(companies.getId());
        } catch (NotExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getOneCompany() {
        login();
        try {
            Companies companies = adminFacade.getOneCompany(10L);

            System.out.println(companies);
            companies.getCoupons().forEach(System.out::println);

        } catch (NotLoginException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void getAllCompanies() {
        try {
            adminFacade.login("admin@admin.com", "admin");
            List<Companies> all = adminFacade.getAllCompanies();
            if (all != null) {
                all.forEach(System.out::println);
            }
        } catch (NotLoginException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addCustomer() {
        adminFacade.login("admin@admin.com", "admin");
        Customer customer = new Customer("eee", "ddd", "eee@gmail.co.il", "999");
        try {
            adminFacade.addCustomer(customer);
        } catch (NotLoginException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateCustomer() {
        adminFacade.login("admin@admin.com", "admin");
        Customer customer = new Customer(1L, "a", "a", "a", "a");
        try {
            adminFacade.updeateCustomer(customer);
        } catch (NotLoginException e) {
            System.out.println(e.getMessage());
        } catch (NotExistException e) {
            System.out.println(e.getMessage());
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteCustomer() {
        adminFacade.login("admin@admin.com", "admin");
        try {
            adminFacade.deleteCustomer(20L);
            adminFacade.deleteCustomer(21L);
        } catch (NotLoginException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void returnDeleteCustomer() {
        deleteCustomer();
        try {
            adminFacade.returnDeleteCustomer(7L);
        } catch (NotLoginException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void returnDeletesCustomers() {
        deleteCustomer();
        try {
            adminFacade.returnDeletesCustomers();
        } catch (NotLoginException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();

        }
    }

    @Test
    public void getOneCustomer() {
        adminFacade.login("admin@admin.com", "admin");
        try {
            System.out.println(adminFacade.getOneCustomer(23L));

        } catch (NotLoginException e) {
            e.printStackTrace();
        }

    }
}