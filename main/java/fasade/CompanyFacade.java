package fasade;

import entites.Coupon;

import java.util.Objects;
import java.util.function.Predicate;

public class CompanyFacade extends ClientFacade {

    private Long companyId;

    private Predicate<Long> isLoggedIn = Objects::nonNull;

    //login
    @Override
    public boolean login(String email, String password) {
        if (!companiesDBDAO.isCompanyExists(email, password)) return false;
        companyId = companiesDBDAO.getByEmailAndPassword(email, password).getId();
        return true;
    }

    // if coupon exist (name and com_id)
    public void addCoupon(Coupon coupon) {
        if (!isLoggedIn.test(companyId)) {
        }
    }

    //public void updateCoupon(Coupon coupon)
    //public void deleteCoupon(Long CouponId)
    //public ArrayList<Coupon>getCompanyCoupons()
    //public ArrayList<Coupon>getCompanyCoupons(Category category)
    //public ArrayList<Coupon>getCompanyCoupons(Double maxPrice)
    //public Companies getCompanyDetails()

}
