package fasade;

import dao.DBDAO.CategoryDBDAO;
import entites.Category;
import entites.Companies;
import entites.Coupon;
import exceptions.AlreadyExistException;
import exceptions.DoubelCouponsException;
import exceptions.NotExistException;
import exceptions.NotLoginException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompanyFacade extends ClientFacade {

    private CategoryDBDAO categoryDBDAO = new CategoryDBDAO();
    private Long companyId = null;
    //private Predicate<Long> isLoggedIn = Objects::nonNull;

    //login
    @Override
    public Boolean login(String email, String password) {
        if (companiesDBDAO.isCompanyExists(email, password)) {
            companyId = companiesDBDAO.getByEmailAndPassword(email, password).getId();
        } else {
            companyId = null;

        }
        return companyId != null;
    }

    public Coupon creatCoupon(Category category, String title, String description, LocalDate startDate
            , LocalDate endDate, int amount, double price, String image) throws NotLoginException, NotExistException {
        if (companyId == null) {
            throw new NotLoginException("you need to login");
        }
        if (!categoryDBDAO.isExist(category)) {
            categoryDBDAO.addCategory(category);
        }
        Long categoryId = categoryDBDAO.getIdCategory(category);
        Coupon newCoupon = new Coupon(companyId, categoryId, title, description, startDate, endDate, amount, price, image);

        return newCoupon;
    }

    //  add coupon is unique title for company
    public void addCoupon(Coupon coupon) throws NotLoginException, AlreadyExistException {
        if (companyId == null) {
            throw new NotLoginException("you need to login");
        }
        if (coupon != null) {
            if (couponsDBDAO.isCouponExistsByCompanyAndTitel(companyId, coupon)) {
                throw new AlreadyExistException("the company already have " + coupon.getTitle() + "coupon");
            }
            couponsDBDAO.addCoupon(coupon);
        }
    }

    public Coupon getCouponById(Long id) {
        if (id != null) {
            Coupon coupon = couponsDBDAO.getOneCoupon(id);
            if (coupon != null && coupon.getCompanyId().equals(companyId)) {
                return coupon;
            }
        }
        return null;
    }

    //public void updateCoupon(Coupon coupon)
    public void updateCoupon(Coupon coupon) throws NotLoginException, DoubelCouponsException, NotExistException {
        if (companyId == null) {
            throw new NotLoginException("you need to login");
        }
        if (coupon == null || !coupon.getCompanyId().equals(companyId)) {
            throw new NotExistException("there is not such a coupon that belong to your company");
        }
        Coupon original = couponsDBDAO.getOneCoupon(coupon.getId());
        if (!original.getTitle().equals(coupon.getTitle())) {
            if (couponsDBDAO.isCouponExistsByCompanyAndTitel(companyId, coupon)) {
                throw new DoubelCouponsException("you already have coupon with the seam title");
            }
            original.setTitle(coupon.getTitle());
        }
        original.setCategoryId(coupon.getCategoryId());
        original.setDescription(coupon.getDescription());
        original.setStartDate(coupon.getStartDate());
        original.setEndDate(coupon.getEndDate());
        original.setAmount(coupon.getAmount());
        original.setPrice(coupon.getPrice());
        original.setImage(coupon.getImage());

        couponsDBDAO.update(original);
    }

    //public void deleteCoupon(Long couponId)
    public void deleteCoupon(Long couponId) throws NotLoginException, NotExistException {
        if (companyId == null) {
            throw new NotLoginException("you nees to login");
        }
        if (couponId == null) {
            throw new NotExistException("there is no coupon to delete");
        }
        Coupon coupon = couponsDBDAO.getOneCoupon(couponId);
        if (coupon == null || !coupon.getId().equals(couponId)) {
            throw new NotExistException("you don't have this coupon");
        }
        purchaseDBDAO.deletePurchaseCouponsByCouponId(couponId);
        couponsDBDAO.delete(couponId);
    }

    //public ArrayList<Coupon>getCompanyCoupons()
    public List<Coupon> getCompanyCoupons() throws NotLoginException {
        if (companyId == null) {
            throw new NotLoginException("you need to login");
        }
        List<Coupon> all = couponsDBDAO.getAllCompnyCoupons(companyId);
        return all;
    }

    //public ArrayList<Coupon>getCompanyCoupons(Category category)
    public List<Coupon> getCompanyCouponsByCategory(Category category) throws NotLoginException {
        List<Coupon> couponsByType = null;
        if (companyId == null) {
            throw new NotLoginException("you need to login");
        }
        Long categoryId = categoryDBDAO.getIdCategory(category);
        if (categoryId != null) {
            couponsByType = couponsDBDAO.getAllCompnyCouponsByType(companyId, categoryId);
        }
        return couponsByType;
    }

    //public ArrayList<Coupon>getCompanyCoupons(Double maxPrice)
    public List<Coupon> getCompanyCouponsByMaxPrice(Double maxPrice) throws NotLoginException {
        List<Coupon> allCompanyCouponsByMaxPrice = null;
        if (companyId == null) {
            throw new NotLoginException("you need to login");
        }
        if (maxPrice != null && maxPrice > 0) {
            allCompanyCouponsByMaxPrice = couponsDBDAO.getAllCompnyCouponsByMaxPrice(companyId, maxPrice);
        }
        return allCompanyCouponsByMaxPrice;
    }

    //public Companies getCompanyDetails()
    public Companies getCompanyDetails() throws NotLoginException {
        if(companyId==null){
            throw new NotLoginException("you need to login");
        }
        return companiesDBDAO.getOneCompany(companyId);
    }
}




