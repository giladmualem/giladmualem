package fasade;

import entites.Category;
import entites.Coupon;
import exceptions.AlreadyExistException;
import exceptions.DoubelCouponsException;
import exceptions.NotExistException;
import exceptions.NotLoginException;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class CompanyFacadeTest {
    private CompanyFacade companyFacade;

    @Test
    public void login() {
        companyFacade = new CompanyFacade();
        System.out.println(companyFacade.login("sano@.co.il", "1234"));

    }

    @Test
    public void addCoupon() {
        login();
        try {
            // Coupon coupon = companyFacade.creatCoupon(null, "cola", "6 pack", LocalDate.now()
            //       , LocalDate.now().plusDays(3), 40, 5.30, "www.cola.html");
            companyFacade.addCoupon(companyFacade.creatCoupon(Category.FOOD, "www", "diner", LocalDate.now(), LocalDate.now().plusDays(1), 4, 55.5, "aaaa"));
            companyFacade.couponsDBDAO.getOneCoupon(1L);
        } catch (NotLoginException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteCoupon() {
        login();
        try {
            companyFacade.deleteCoupon(20L);
        } catch (NotLoginException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void updateCoupon() {
        //companyFacade=new CompanyFacade();
        login();
        Coupon coupon = companyFacade.getCouponById(21L);
        coupon.setPrice(111);
        try {
            companyFacade.updateCoupon(coupon);
        } catch (NotLoginException e) {
            e.printStackTrace();
        } catch (DoubelCouponsException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getCompanyCoupons() {
//        companyFacade=new CompanyFacade();
        login();
        try {
            companyFacade.getCompanyCoupons().forEach(System.out::println);
        } catch (NotLoginException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllCompnyCouponsByType() {
        //companyFacade =new CompanyFacade();
        login();
        try {
            companyFacade.getCompanyCouponsByCategory(Category.FOOD).forEach(System.out::println);
        } catch (NotLoginException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllCompnyCouponsByMaxPrice(){
        //companyFacade=new CompanyFacade();
        login();
        try {
            companyFacade.getCompanyCouponsByMaxPrice(0D).forEach(System.out::println);
        } catch (NotLoginException e) {
            e.printStackTrace();
        }
    }


@Test
    public void getCompanyDetails(){
        //companyFacade=new CompanyFacade();
    login();
    try {
        System.out.println(companyFacade.getCompanyDetails());
    } catch (NotLoginException e) {
        e.printStackTrace();
    }
}

}