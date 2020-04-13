package dao.DBDAO;

import dao.DAO.CompaniesDAO;
import dao.DAO.PurchaseDAO;
import entites.Category;
import entites.Coupon;
import pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PurchaseDBDAO implements PurchaseDAO {

    private ConnectionPool pool;

    public PurchaseDBDAO() {
        pool = ConnectionPool.getInstance();
    }

    @Override
    public Boolean isCouponPurchase(Long customerId, Long couponId) {
        Boolean isExists = false;
        String sql = "SELECT * FROM customersvscoupons WHERE  CUS_ID=? AND COU_ID=?;";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, customerId);
            prstm.setLong(2, couponId);
            ResultSet resultSet = prstm.executeQuery();
            if (resultSet.next()) {
                isExists = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return isExists;
    }

    @Override
    public void addPurchase(Long customerId, Long couponId) {
        String sql = "INSERT INTO customersvscoupons (CUS_ID, COU_ID) values (?,?)";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, customerId);
            prstm.setLong(2, couponId);
            prstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }

    }

    @Override
    public void deletePurchase(Long customerId, Long couponId) {
        String sql = "DELETE FROM customersvscoupons WHERE  CUS_ID=? AND COU_ID=?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, customerId);
            prstm.setLong(2, couponId);
            prstm.executeUpdate();
        } catch (Exception e) {

        } finally {
            pool.returnConnetion(connection);
        }
    }

    public List<Coupon> getPurchaseCouponsByCustomerId(Long customerId) {
        List<Coupon> coupons = new ArrayList<>();
        String sql = "SELECT * FROM coupons where id in " +
                "(select cou_id from customersvscoupons where cus_id = ?)";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, customerId);
            ResultSet resultSet = prstm.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                long companyId = resultSet.getLong(2);
                long cat = resultSet.getLong(3);
                String titel = resultSet.getString(4);
                String description = resultSet.getString(5);
                LocalDate start = resultSet.getDate(6).toLocalDate();
                LocalDate end = resultSet.getDate(7).toLocalDate();
                int amount = resultSet.getInt(8);
                double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);
                Coupon coupon = new Coupon(id, companyId, cat, titel, description, start, end, amount, price, image);
                coupons.add(coupon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return coupons;
    }

    public void deletePurchaseCouponsByCouponId(Long couponId) {
        String sql = "SELECT * FROM customersvscoupons WHERE  COU_ID=?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, couponId);
            ResultSet resultSet = prstm.executeQuery();
            while (resultSet.next()) {
                deletePurchase(resultSet.getLong("CUS_ID"), couponId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
    }

    public void deletePurchaseCouponsByCustomerId(Long customerId) {
        String sql = "SELECT * FROM customersvscoupons WHERE  CUS_ID=?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, customerId);
            ResultSet resultSet = prstm.executeQuery();
            while (resultSet.next()) {
                deletePurchase(customerId, resultSet.getLong("COU_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
    }

    public List<Coupon> getCustomerCouponsByCategory(Long categoryId, Long customerId) {
        List<Coupon> coupons = new ArrayList<>();
        String sql = "SELECT * FROM coupons where CAT_ID=? and id in " +
                "(select cou_id from customersvscoupons where cus_id = ?)";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, categoryId);
            prstm.setLong(2, customerId);
            ResultSet resultSet = prstm.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                long companyId = resultSet.getLong(2);
//                long cat = resultSet.getLong(3);
                String titel = resultSet.getString(4);
                String description = resultSet.getString(5);
                LocalDate start = resultSet.getDate(6).toLocalDate();
                LocalDate end = resultSet.getDate(7).toLocalDate();
                int amount = resultSet.getInt(8);
                double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);
                Coupon coupon = new Coupon(id, companyId, categoryId, titel, description, start, end, amount, price, image);
                coupons.add(coupon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return coupons;
    }

    public List<Coupon> getCustomerCouponsUpToMaxPrice(Double max, Long customerId) {
        List<Coupon> coupons = new ArrayList<>();
        String sql = "SELECT * FROM coupons WHERE PRICE <= ? and id in (select cou_id from customersvscoupons where cus_id = ?)";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setDouble(1, max);
            prstm.setLong(2, customerId);
            ResultSet resultSet = prstm.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                long companyId = resultSet.getLong(2);
                long cat = resultSet.getLong(3);
                String titel = resultSet.getString(4);
                String description = resultSet.getString(5);
                LocalDate start = resultSet.getDate(6).toLocalDate();
                LocalDate end = resultSet.getDate(7).toLocalDate();
                int amount = resultSet.getInt(8);
                double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);
                Coupon coupon = new Coupon(id, companyId, cat, titel, description, start, end, amount, price, image);
                coupons.add(coupon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return coupons;
    }

//    public List<Coupon> allCustomerCoupons(Long id) {
//        List<Coupon> all = new ArrayList<>();
//        String sql = "SELECT * FROM coupons ,customersvscoupons WHERE " +
//                " coupons.ID = customersvscoupons.COU_ID" +
//                "AND customersvscoupons.CUS_ID=?;";
//        Connection connection = pool.getConnection();
//        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
//            prstm.setLong(1, id);
//            ResultSet resultSet = prstm.executeQuery();
//            while (resultSet.next()) {
//                Long company = resultSet.getLong(2);
//                Long categoy = resultSet.getLong(3);
//                String titel = resultSet.getString(4);
//                String description = resultSet.getString(5);
//                LocalDate start = resultSet.getDate(6).toLocalDate();
//                LocalDate end = resultSet.getDate(7).toLocalDate();
//                Integer amount = resultSet.getInt(8);
//                Double prise = resultSet.getDouble(9);
//                String image = resultSet.getString(10);
//                all.add(new Coupon(id, company, categoy, titel, description, start, end, amount, prise, image));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            pool.returnConnetion(connection);
//        }
//        return all;
//    }

}
