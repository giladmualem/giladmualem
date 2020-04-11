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
    void delete (Long couponId);
    List<Coupon>getAllCoupons();
    Coupon getOneCoupon(Long couponId);
    //void addCouponPurchase(Long customerId,Long couponId) ;
    //void deleteCouponPurchase(Long customerId,Long couponId) ;
}
