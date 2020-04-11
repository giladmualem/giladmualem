package fasade;

import dao.DBDAO.CategoryDBDAO;
import dao.DBDAO.PurchaseDBDAO;
import entites.Category;
import entites.Coupon;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;
import exceptions.NotLoginException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class CompanyFacade extends ClientFacade {

    private CategoryDBDAO categoryDBDAO = new CategoryDBDAO();
    private Long companyId = null;
    //private Predicate<Long> isLoggedIn = Objects::nonNull;

    //login
    @Override
    public Boolean login(String email, String password) {
        if (companiesDBDAO.isCompanyExists(email, password)) {
            companyId = companiesDBDAO.getByEmailAndPassword(email, password).getId();
            return true;
        } else {
            companyId = null;
            return false;
        }
    }

    public Coupon creatCoupon(Category category, String title, String description, LocalDate startDate
            , LocalDate endDate, int amount, double price, String image) throws NotLoginException {
        if (companyId == null) {
            throw new NotLoginException("you need to login");
        }
        if (!categoryDBDAO.isExist(category)) {
            categoryDBDAO.addCategory(category);
        }
        Long categoryId = categoryDBDAO.getIdCategory(category);
        Coupon newCoupon = new Coupon(categoryId, companyId, title, description, startDate, endDate, amount, price, image);

        return newCoupon;
    }

    //  add coupon is unique title for company
    public void addCoupon(Coupon coupon) throws NotLoginException, AlreadyExistException {
        if (companyId == null) {
            throw new NotLoginException("you need to login");
        }
        if (coupon != null) {
            if (couponsDBDAO.isCouponExists(companyId, coupon)) {
                throw new AlreadyExistException("the company already have " + coupon.getTitle() + "coupon");
            }
            couponsDBDAO.addCoupon(coupon);
        }
    }

    //public void updateCoupon(Coupon coupon)
    public void updateCoupon(Coupon coupon) throws NotLoginException {
        if (companyId == null) {
            throw new NotLoginException("you need to login");
        }
        if (coupon != null && coupon.getCompanyId().equals(companyId)) {
            couponsDBDAO.update(coupon);
        }
    }

    //public void deleteCoupon(Long couponId)
    public void deleteCoupon(Long couponId) throws NotLoginException, NotExistException {
        if(companyId==null){
            throw new NotLoginException("you nees to login");
        }
        if(couponId==null){
            throw new NotExistException("there is no coupon to delete");
        }
        Coupon del=couponsDBDAO.getOneCoupon(couponId);
        if(del!=null&&del.getCompanyId().equals(companyId)){
            couponsDBDAO.delete(couponId);
          //  purchaseDBDAO.deleteAllPurchase(couponId);
        }

    }


}
//public ArrayList<Coupon>getCompanyCoupons()
//public ArrayList<Coupon>getCompanyCoupons(Category category)
//public ArrayList<Coupon>getCompanyCoupons(Double maxPrice)
//public Companies getCompanyDetails()


