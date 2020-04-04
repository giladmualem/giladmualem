package fasade;

import dao.DBDAO.CategoryDBDAO;
import entites.Category;
import entites.Coupon;
import exceptions.NotLoginException;

import java.util.Objects;
import java.util.function.Predicate;

public class CompanyFacade extends ClientFacade {

    private Long companyId=null;
    private CategoryDBDAO categoryDBDAO=new CategoryDBDAO();
    private Predicate<Long> isLoggedIn = Objects::nonNull;

    //login
    @Override
    public Boolean login(String email, String password) {
        if (!companiesDBDAO.isCompanyExists(email, password)) return false;
        companyId = companiesDBDAO.getByEmailAndPassword(email, password).getId();
        return true;
    }

    // if coupon exist (name and com_id)
    public void addCoupon(Coupon coupon) throws NotLoginException{
        if (companyId==null) {
            throw new NotLoginException("you need  first to login");
        }


    }

    //public void updateCoupon(Coupon coupon)
    //public void deleteCoupon(Long CouponId)
    //public ArrayList<Coupon>getCompanyCoupons()
    //public ArrayList<Coupon>getCompanyCoupons(Category category)
    //public ArrayList<Coupon>getCompanyCoupons(Double maxPrice)
    //public Companies getCompanyDetails()

}
