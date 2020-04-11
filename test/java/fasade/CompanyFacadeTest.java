package fasade;

import entites.Category;
import entites.Coupon;
import exceptions.AlreadyExistException;
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
        System.out.println(companyFacade.login("cola@.co.il", "bbb"));
    }

    @Test
    public void addCoupon() {
        login();
        try {
           // Coupon coupon = companyFacade.creatCoupon(null, "cola", "6 pack", LocalDate.now()
             //       , LocalDate.now().plusDays(3), 40, 5.30, "www.cola.html");
            companyFacade.addCoupon(companyFacade.creatCoupon(Category.RESTAURANT,"steck","diner",LocalDate.now(),LocalDate.now().plusDays(1),4,55.5,"aaaa"));
        } catch (NotLoginException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteCoupon(){
        login();
        try {
            companyFacade.deleteCoupon(9L);
        } catch (NotLoginException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        }

    }
}