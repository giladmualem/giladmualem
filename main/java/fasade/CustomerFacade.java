package fasade;

import entites.Category;
import entites.Coupon;
import entites.Customer;

import java.util.ArrayList;

public class CustomerFacade extends ClientFacade {

    private Long customerId = null;

    //public login
    @Override
    public Boolean login(String email, String password) {
        if (customersDBDAO.isCustomerExists(email, password)) {
            customerId = customersDBDAO.getOneCustomer(email, password).getId();
            return true;
        } else {
            customerId = null;
            return false;
        }
    }
    //  can't purchase coupon you already have, amount=0 ,date end < now    - update amount
    //public void purchaseCoupon(Coupon coupon)

    //public ArrayList<Coupon> getCustomerCoupons()
    //public ArrayList<Coupon> getCustomerCoupons(Category category)
    //public ArrayList<Coupon> getCustomerCoupons(Double maxPrice)
    //public Customer getCustomerDetails()

}
