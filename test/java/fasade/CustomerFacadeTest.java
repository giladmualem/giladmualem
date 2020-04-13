package fasade;

import entites.Category;
import entites.Coupon;
import entites.Customer;
import exceptions.CouponEmptyException;
import exceptions.DoubelCouponsException;
import exceptions.NotExistException;
import exceptions.NotLoginException;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerFacadeTest {
    //    private Long customerId=null;
    private CustomerFacade customerFacade;

    @Test
    public void login() {
        customerFacade.login("ss@gmail.co.il", "999");
        System.out.println(customerFacade);
    }

    @Test
    public void getCustomerDetails() {
        customerFacade = new CustomerFacade();
        login();
        //       CustomerFacade customerFacade=new CustomerFacade();
        try {
            System.out.println(customerFacade.getCustomerDetails());
            customerFacade.getCustomerDetails().getCoupons().forEach(System.out::println);
        } catch (NotLoginException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void purchaseCoupon() {
        customerFacade = new CustomerFacade();
        login();
        Coupon coupon = customerFacade.couponsDBDAO.getOneCoupon(21L);
        try {
            customerFacade.purchaseCoupon(coupon);
        } catch (NotLoginException e) {
            e.printStackTrace();
        } catch (DoubelCouponsException e) {
            e.printStackTrace();
        } catch (CouponEmptyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllCustomerCoupons() {
        customerFacade = new CustomerFacade();
        login();
        try {
            System.out.println(customerFacade.getAllCustomerCoupons());
        } catch (NotLoginException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCustomerCouponsByCategory(){
        customerFacade=new CustomerFacade();
        login();
        try {
            System.out.println(customerFacade.getCustomerCouponsByCategory(Category.RESTAURANT));
        } catch (NotLoginException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCustomerCouponsMaxPrice(){
        customerFacade=new CustomerFacade();
        login();
        try {
            System.out.println(customerFacade.getCustomerCouponsMaxPrice(55.4D));
        } catch (NotLoginException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        }
    }
}