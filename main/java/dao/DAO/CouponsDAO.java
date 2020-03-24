package dao.DAO;

import entites.Coupon;
import exceptions.AlreadyExistException;
import exceptions.CouponEmptyException;
import exceptions.DoubelCouponsException;
import exceptions.NotExistException;

import java.util.List;

public interface CouponsDAO {
    void addCoupon(Coupon coupon);
    void update(Coupon coupon);
    void delete (long couponId);
    List<Coupon>getAllCoupons();
    Coupon getOneCoupon(long couponId);
    void addCouponPurchase(long customerId,long couponId) ;
    void deleteCouponPurchase(long customerId,long couponId) ;
}
