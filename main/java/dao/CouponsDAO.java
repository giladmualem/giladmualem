package dao;

import entites.Coupon;
import exceptions.AlreadyExistException;
import exceptions.CouponEmptyException;
import exceptions.DoubelCouponsException;
import exceptions.NotExistException;

import java.util.List;

public interface CouponsDAO {
    void addCoupon(Coupon coupon)throws AlreadyExistException;
    void update(Coupon coupon)throws NotExistException;
    void delete (long couponId)throws NotExistException;
    List<Coupon>getAllCoupons();
    Coupon getOneCoupon(long couponId)throws NotExistException;
    void addCouponPurchase(long customerId,long couponId) throws CouponEmptyException, DoubelCouponsException;
    void deleteCouponPurchase(long customerId,long couponId) throws NotExistException;
}
