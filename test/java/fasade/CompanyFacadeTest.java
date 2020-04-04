package fasade;

import entites.Coupon;
import exceptions.NotLoginException;
import org.junit.Test;

import static org.junit.Assert.*;

public class CompanyFacadeTest {
    private CompanyFacade companyFacade;
    @Test
    public void login() {
        companyFacade=new CompanyFacade();
        System.out.println( companyFacade.login("cola@.co.il","bbb"));
    }

    @Test
    public void addCoupon() {
        companyFacade=new CompanyFacade();
        Coupon coupon=null;
        try {
            companyFacade.addCoupon(coupon);
        } catch (NotLoginException e) {
            System.out.println(e.getMessage());
        }
    }
}