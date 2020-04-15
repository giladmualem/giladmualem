package job;

import dao.DBDAO.CouponsDBDAO;
import dao.DBDAO.PurchaseDBDAO;
import entites.Coupon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CouponExpirationDailyJob implements Runnable {

    private CouponsDBDAO couponsDBDAO;
    private PurchaseDBDAO purchaseDBDAO;

    public CouponExpirationDailyJob() {
        couponsDBDAO = new CouponsDBDAO();
        purchaseDBDAO = new PurchaseDBDAO();
    }
    // this method check to remove all the coupons that expired in the background
    @Override
    public void run() {
        List<Coupon>expired=new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            System.out.println(i);
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(30));
                expired=couponsDBDAO.getAllCouponsExpired();
                if(expired!=null){
                    expired.forEach(x->{
                            purchaseDBDAO.deletePurchaseCouponsByCouponId(x.getId());
                            couponsDBDAO.delete(x.getId());
                    });
                }
            } catch (InterruptedException e) {
                e.fillInStackTrace();

            }

        }
    }


}
