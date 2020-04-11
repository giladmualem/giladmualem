package dao.DAO;

public interface PurchaseDAO {
    Boolean isCouponPurchase(Long customerId,Long couponId);
    void addPurchase(Long customerId,Long couponId);
    void deletePurchase(Long customerId,Long couponId);
}
