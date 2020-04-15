package fasade;

import dao.DBDAO.CategoryDBDAO;
import entites.Category;
import entites.Coupon;
import entites.Customer;
import exceptions.*;
import java.time.LocalDate;
import java.util.List;

public class CustomerFacade extends ClientFacade {
    private CategoryDBDAO categoryDBDAO = new CategoryDBDAO();
    private Long clientId = null;

    // confirm login to customer facade by email and password
    @Override
    public Boolean login(String email, String password) {
        if (customersDBDAO.isCustomerExists(email, password)) {
            clientId = customersDBDAO.getOneCustomer(email, password).getId();
            return true;
        } else {
            clientId = null;
            return false;
        }
    }

    //public Customer getCustomerDetails()
    public Customer getCustomerDetails() throws NotLoginException {
        if (clientId == null) {
            throw new NotLoginException("you need to login");
        }
        Customer customer = customersDBDAO.getOneCustomer(clientId);
        if (customer != null) {
            customer.setCoupons(purchaseDBDAO.getPurchaseCouponsByCustomerId(clientId));
        }
        return customer;
    }


    //  can't purchase coupon you already have, amount=0 ,date end < now  - update amount
    public void purchaseCoupon(Coupon coupon) throws NotLoginException, DoubelCouponsException, CouponEmptyException {
        if (clientId == null) {
            throw new NotLoginException("you need to login");
        }
        if (coupon.getAmount() <= 0 ||
                (LocalDate.now().isBefore(coupon.getStartDate()) &&
                        LocalDate.now().isAfter(coupon.getEndDate()))) {
            throw new CouponEmptyException("this coupon is not available");
        }
        if (purchaseDBDAO.isCouponPurchase(clientId, coupon.getId())) {
            throw new DoubelCouponsException("you can't buy more then one");
        }
        purchaseDBDAO.addPurchase(clientId, coupon.getId());
        coupon.setAmount(coupon.getAmount() - 1);
        couponsDBDAO.update(coupon);
    }

    // return all the coupons that customer purchase
    public List<Coupon> getAllCustomerCoupons() throws NotLoginException {
        if (clientId == null) {
            throw new NotLoginException("you need to login");
        }
        List<Coupon> all = purchaseDBDAO.getPurchaseCouponsByCustomerId(clientId);
        return all;
    }

    // return all the coupons that customer purchase from specific category
    public List<Coupon> getCustomerCouponsByCategory(Category category) throws NotLoginException, NotExistException {
        if (clientId == null) {
            throw new NotLoginException("you need to login");
        }
        Long categoryId = categoryDBDAO.getIdCategory(category);
        if (categoryId == null) {
            throw new NotExistException("you need enter category");
        }
        List<Coupon> couponsType = purchaseDBDAO.getCustomerCouponsByCategory(categoryId, clientId);
        return couponsType;
    }

    // return all the coupons that customer purchase upto the price
    public List<Coupon> getCustomerCouponsMaxPrice(Double maxPrice) throws NotLoginException, NotExistException {
        if (clientId == null) {
            throw new NotLoginException("you need to login");
        }
        if (maxPrice == null || maxPrice<=0D) {
            throw new NotExistException("you need enter max purchase");
        }
        return purchaseDBDAO.getCustomerCouponsUpToMaxPrice(maxPrice,clientId);
    }


}
