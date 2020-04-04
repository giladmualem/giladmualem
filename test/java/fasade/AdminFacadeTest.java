package fasade;

import entites.Companies;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;
import exceptions.NotLoginException;
import org.junit.Test;
import pool.ConnectionPool;

import static org.junit.Assert.*;

public class AdminFacadeTest {


    private AdminFacade adminFacade = new AdminFacade();

    @Test
    public void login() {
//        System.out.println(adminFacade.login("admin@admin.com","admin"));
    }

    @Test
    public void addCompany() {
//        adminFacade.login("admin@admin.com","admin");
//        Companies com = new Companies("sano", "sano@.co.il", "1234");
//        try {
//            adminFacade.addCompany(com);
//        } catch (AlreadyExistException e) {
//            e.printStackTrace();
//        } catch (NotLoginException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void updateCompany() {
//        adminFacade.login("admin@admin.com","admin");
//        Companies company=new Companies(5,"tnuva","tnuva@co.il","9876");
//        try {
//            adminFacade.updateCompany(company);
//        } catch (NotExistException e) {
//            e.printStackTrace();
//        } catch (NotLoginException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void deleteCompany() {
//        adminFacade.login("admin@admin.com", "admin");
//        Companies com = new Companies("zer4u", "zer4u@.co.il", "ccc");
//        try {
//            adminFacade.deleteCompany(com);
//        } catch (NotLoginException e) {
//            System.out.println(e.getMessage());
//        } catch (NotExistException e) {
//            System.out.println(e.getMessage());
//        }

    }

    @Test
    public void returnOneDeleteCompony() {
//        adminFacade.login("admin@admin.com", "admin");
//        Companies companies=new Companies(8,"sano","tnuva@co.il","9876");
//        try {
//            adminFacade.deleteCompany(companies);
//        } catch (NotLoginException e) {
//            e.printStackTrace();
//        } catch (NotExistException e) {
//            e.printStackTrace();
//        }
//        try {
//            adminFacade.returnOneDeleteCompony(companies.getId());
//        } catch (NotExistException e) {
//            e.printStackTrace();
//        }
    }


}