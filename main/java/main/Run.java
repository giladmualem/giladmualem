package main;

import dao.DAO.CategoryDAO;
import dao.DAO.CompaniesDAO;
import dao.DAO.CouponsDAO;
import dao.DBDAO.CategoryDBDAO;
import dao.DBDAO.CompaniesDBDAO;
import dao.DBDAO.CouponsDBDAO;
import entites.Category;
import entites.Companies;
import entites.Coupon;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;
import fasade.AdminFacade;
import pool.ConnectionPool;

import java.time.LocalDate;
import java.util.List;

public class Run {
    public static void main(String[] args) {
        ConnectionPool pool = ConnectionPool.getInstance();
        CompaniesDAO daoCom = new CompaniesDBDAO();
        CouponsDAO daoCou=new CouponsDBDAO();
        Companies com = daoCom.getOneCompany(2L);
        CategoryDAO daoCat=new CategoryDBDAO();
        if(!daoCat.isExist(Category.FOOD)){
            daoCat.addCategory(Category.FOOD);
        }
        Coupon cou=new Coupon(com.getId(),daoCat.getIdCategory(Category.FOOD),"6 pack","get 2 pack free", LocalDate.now(),LocalDate.now().plusDays(7),100,20.5,"url/..img");
        daoCou.addCoupon(cou);

        pool.closeConnection();
//        dao1.deleteCategory(dao1.getIdCategory(Category.FOOD));
//        AdminFacade facade=new AdminFacade("admin@admin.com","admin");
//        Companies com = new Companies(7,"zer4u", "zer4u@.co.il", "fff");
//        try {
//            facade.updateCompany(com);
//        } catch (NotExistException e) {
//            e.printStackTrace();
//        }
//        List <Companies> a=dao.getAllCompanies();
//        for(Companies c:a){
//            System.out.println(c);
//        }
//        System.out.println(dao.getOneCompany(3L));
//        Companies com = new Companies("cola", "cola@.co.il", "bbb");
//        dao.addCompany(com);
//        com = new Companies("zara", "zara@.co.il", "aaa");
//        dao.addCompany(com);
//        dao.deleteCompany(1);
//        dao.updateCompany(com);
//        System.out.println(dao.isCompanyExists(com.getEmail(),com.getPassword()));
//        System.out.println(dao.isCompanyExists(com.getEmail(),com.getPassword()));
    }
}