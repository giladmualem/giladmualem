package main;

import dao.DAO.CategoryDAO;
import dao.DAO.CompaniesDAO;
import dao.DAO.CouponsDAO;
import dao.DAO.CustomersDAO;
import dao.DBDAO.CategoryDBDAO;
import dao.DBDAO.CompaniesDBDAO;
import dao.DBDAO.CouponsDBDAO;
import dao.DBDAO.CustomersDBDAO;
import entites.Category;
import entites.Companies;
import entites.Coupon;
import entites.Customer;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;
import fasade.AdminFacade;
import pool.ConnectionPool;

import java.time.LocalDate;
import java.util.List;

public class Run {
    public static void main(String[] args) {
        ConnectionPool pool = ConnectionPool.getInstance();
        CustomersDAO customersDAO = new CustomersDBDAO();
        List <Customer>a=customersDAO.allCustomer();
        for (Customer customer:a)
        System.out.println(customer);
        pool.closeConnection();

//        CouponsDAO daoCou=new CouponsDBDAO();
//        CompaniesDBDAO companiesDBDAO=new CompaniesDBDAO();
//        Companies company=companiesDBDAO.getOneCompany(2L);
//        company.setCoupons(((CouponsDBDAO) daoCou).getAllCompnyCoupons(company.getId()));
//        for(Coupon coupon:company.getCoupons()){
//            System.out.println(coupon);
//        }
//        LocalDate t=LocalDate.now();
//        List <Coupon>all=daoCou.getAllCoupons();
//        for(Coupon a:all){
//            System.out.println(a);
//        }

//        Coupon coupon=new Coupon(2,2,"12pack","12 pack of cola",t,t.plusDays(4),50,35.0,"url...");
//        daoCou.addCoupon(coupon);

//        Coupon cou=daoCou.getOneCoupon(1);
//        daoCou.delete(1);
//        CompaniesDAO daoCom = new CompaniesDBDAO();
//        Companies com = daoCom.getOneCompany(2L);
//        daoCou.addCoupon(cou);

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