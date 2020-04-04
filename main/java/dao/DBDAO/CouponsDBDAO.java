package dao.DBDAO;

import dao.DAO.CouponsDAO;
import entites.Category;
import entites.Coupon;
import entites.Customer;
import exceptions.AlreadyExistException;
import exceptions.CouponEmptyException;
import exceptions.DoubelCouponsException;
import exceptions.NotExistException;
import pool.ConnectionPool;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CouponsDBDAO implements CouponsDAO {

    private ConnectionPool pool;

    public CouponsDBDAO() {
        pool = ConnectionPool.getInstance();
    }

    // if coupon dont exist
    @Override
    public void addCoupon(Coupon coupon) {
        String sql = "INSERT INTO coupons " +
                "(COM_ID ,CAT_ID ,TITEL ,DESCRIPTION ,SDATE ,EDATE ,AMOUNT ,PRICE ,IMAGE )" +
                " VALUES(?,?,?,?,?,?,?,?,?)";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prstm.setLong(1, coupon.getCompanyId());
            prstm.setLong(2, coupon.getCategoryId());
            prstm.setString(3, coupon.getTitle());
            prstm.setString(4, coupon.getDescription());
            prstm.setDate(5, Date.valueOf(coupon.getStartDate()));
            prstm.setDate(6, Date.valueOf(coupon.getEndDate()));
            prstm.setInt(7, coupon.getAmount());
            prstm.setDouble(8, coupon.getPrice());
            prstm.setString(9, coupon.getDescription());
            prstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
    }


    @Override
    public void update(Coupon coupon) {
        String sql = "UPDATE coupons SET CAT_ID= ?, TITEL= ?, DESCRIPTION= ?, " +
                "SDATE= ?, EDATE= ?, AMOUNT= ?, PRICE= ?, IMAGE= ? WHERE ID= ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prsmt = connection.prepareStatement(sql)) {
            prsmt.setLong(1, coupon.getCategoryId());
            prsmt.setString(2, coupon.getTitle());
            prsmt.setString(3, coupon.getDescription());
            prsmt.setDate(4, Date.valueOf(coupon.getStartDate()));
            prsmt.setDate(5, Date.valueOf(coupon.getEndDate()));
            prsmt.setInt(6, coupon.getAmount());
            prsmt.setDouble(7, coupon.getPrice());
            prsmt.setString(8, coupon.getImage());
            prsmt.setLong(9, coupon.getId());
            prsmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }

    }

    @Override
    public void delete(long couponId) {
        String sql = "DELETE FROM coupons WHERE ID=?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, couponId);
            prstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
    }

    @Override
    public Coupon getOneCoupon(long couponId) {
        Coupon coupon = null;
        String sql = "SELECT * FROM coupons WHERE ID= ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, couponId);
            ResultSet resultSet = prstm.executeQuery();
            while (resultSet.next()) {
                long com = resultSet.getLong(2);
                long cat = resultSet.getLong(3);
                String titel = resultSet.getString(4);
                String description = resultSet.getString(5);
                LocalDate start = resultSet.getDate(6).toLocalDate();
                LocalDate end = resultSet.getDate(7).toLocalDate();
                int amount = resultSet.getInt(8);
                double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);
                coupon = new Coupon(couponId, com, cat, titel, description, start, end, amount, price, image);
            }
        } catch (Exception e) {

        } finally {
            pool.returnConnetion(connection);
        }
        return coupon;
    }

    @Override
    public List<Coupon> getAllCoupons() {
        List<Coupon> all = null;
        String sql = "SELECT * FROM coupons";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            ResultSet resultSet = prstm.executeQuery();
            all = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                long com = resultSet.getLong(2);
                long cat = resultSet.getLong(3);
                String titel = resultSet.getString(4);
                String description = resultSet.getString(5);
                LocalDate start = resultSet.getDate(6).toLocalDate();
                LocalDate end = resultSet.getDate(7).toLocalDate();
                int amount = resultSet.getInt(8);
                double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);
                all.add(new Coupon(id, com, cat, titel, description, start, end, amount, price, image));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return all;
    }

    public List<Coupon> getAllCompnyCoupons(Long com_id) {
        List<Coupon> allCouponCompny = null;
        String sql = "SELECT * FROM coupons WHERE COM_ID= ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prdtm = connection.prepareStatement(sql)) {
            prdtm.setLong(1, com_id);
            ResultSet resultSet = prdtm.executeQuery();
            allCouponCompny = new ArrayList<>();
            while (resultSet.next()) {
                long idCoupon = resultSet.getLong(1);
                long com = resultSet.getLong(2);
                long cat = resultSet.getLong(3);
                String titel = resultSet.getString(4);
                String description = resultSet.getString(5);
                LocalDate start = resultSet.getDate(6).toLocalDate();
                LocalDate end = resultSet.getDate(7).toLocalDate();
                int amount = resultSet.getInt(8);
                double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);
                allCouponCompny.add(new Coupon(com_id, com, cat, titel, description, start, end, amount, price, image));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return allCouponCompny;
    }

    @Override
    public void addCouponPurchase(long customerId, long couponId) {

    }

    @Override
    public void deleteCouponPurchase(long customerId, long couponId) {

    }


//    private Boolean thereIsMoreCoupons(Long couponId) {
//        boolean Purchase = false;
//        String sql = "SELECT AMOUNT FROM coupons.coupons WHERE ID=?";
//        Connection connection = pool.getConnection();
//        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
//            prstm.setLong(1, couponId);
//            ResultSet resultSet = prstm.executeQuery();
//            if (resultSet.next() && resultSet.getInt(1) > 0) {
//                Purchase = true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            pool.returnConnetion(connection);
//        }
//        return Purchase;
//    }
//
//    private Boolean youHaveThisCoupon(Long couponId, Long customerId) {
//        String sql = "SELECT * FROM coupons.customers_vs_coupons WHERE CUSTOMER_ID=? AND COUPONS_ID=?";
//        Boolean haveIt = false;
//        Connection connection = pool.getConnection();
//        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
//            prstm.setLong(1, customerId);
//            prstm.setLong(2, couponId);
//            ResultSet resultSet = prstm.executeQuery();
//            if (resultSet.next()) {
//                haveIt = true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            pool.returnConnetion(connection);
//        }
//        return haveIt;
//    }
//
//    private Boolean oneLastCouponCategory(long categoryId) {
//        String sql = "SELECT * FROM coupons.coupons WHERE CATEGORY_ID=?";
//        Connection connection = pool.getConnection();
//        Integer count = 0;
//        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
//            prstm.setLong(1, categoryId);
//            ResultSet resultSet = prstm.executeQuery();
//            while (resultSet.next() && count > 1) {
//                count++;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            pool.returnConnetion(connection);
//        }
//        return count <= 1;
//    }
//
//
//    private void deleteCategory(long categoryId) {
//        String sql = "DELETE FROM categories WHERE ID=?";
//        Connection connection = pool.getConnection();
//        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
//            prstm.setLong(1, categoryId);
//            prstm.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            pool.returnConnetion(connection);
//        }
//    }
//
//    private Boolean isCouponExist(Coupon coupon) {
//        Boolean exist = false;
//        String sql = "SELECT FROM coupons.coupons WHERE TITEL=?";
//        Connection connection = pool.getConnection();
//        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
//            prstm.setString(1, coupon.getTitle());
//            ResultSet resultSet = prstm.executeQuery();
//            if (resultSet.next()) exist = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            pool.returnConnetion(connection);
//        }
//        return exist;
//    }
//
//    private Boolean isCouponExist(Long id) {
//        Boolean exist = false;
//        String sql = "SELECT FROM coupons.coupons WHERE ID=?";
//        Connection connection = pool.getConnection();
//        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
//            prstm.setLong(1, id);
//            ResultSet resultSet = prstm.executeQuery();
//            if (resultSet.next()) exist = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            pool.returnConnetion(connection);
//        }
//        return exist;
//    }
//    @Override
//    public void update(Coupon coupon) throws NotExistException {
//        if (!isCouponExist(coupon)) {
//            throw new NotExistException("this coupon is not exist");
//        }
//        String sql = "UPDATE coupons.coupons SET CATEGORY_ID=?,TITEL=?,COUPON_DESERIPTION=?," +
//                "START_DATE=?,END_DATE=?,AMOUNT=?,PRICE=?,IMAGE=? WHERE ID=?";
//        Connection connection = pool.getConnection();
//        try (PreparedStatement prsmt = connection.prepareStatement(sql)) {
//            prsmt.setLong(1, coupon.getCategoryId());
//            prsmt.setString(2, coupon.getTitle());
//            prsmt.setString(3, coupon.getDescription());
//            prsmt.setDate(4, Date.valueOf(coupon.getStartDate()));
//            prsmt.setDate(5, Date.valueOf(coupon.getEndDate()));
//            prsmt.setInt(6, coupon.getAmount());
//            prsmt.setDouble(7, coupon.getPrice());
//            prsmt.setString(8, coupon.getImage());
//            prsmt.setLong(9, coupon.getId());
//            prsmt.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            pool.returnConnetion(connection);
//        }
//    }
//
//    @Override
//    public void delete(long couponId) throws NotExistException {
//        if (!isCouponExist(couponId)) throw new NotExistException("there is not exist coupon");
//
//        String sql = "DELETE FROM coupons.coupons WHERE ID=?";
//        Connection connection = pool.getConnection();
//        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
//            prstm.setLong(1, couponId);
//            prstm.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            pool.returnConnetion(connection);
//        }
//
//        if (oneLastCouponCategory(couponId)) deleteCategory(couponId);
//    }
//
//
//    @Override
//    public List<Coupon> getAllCoupons() {
//        List<Coupon> all = new ArrayList<>();
//        Connection connection = pool.getConnection();
//        String sql = "SELECT * FROM coupons.coupon";
//        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
//            ResultSet resultSet = prstm.executeQuery();
//            while (resultSet.next()) {
//                long id = resultSet.getLong(1);
//                long companyId = resultSet.getLong(2);
//                long categoryId = resultSet.getLong(3);
//                String titel = resultSet.getString(4);
//                String couponDescription = resultSet.getString(5);
//                LocalDate startDate = resultSet.getDate(6).toLocalDate();
//                LocalDate endDate = resultSet.getDate(7).toLocalDate();
//                int amount = resultSet.getInt(8);
//                double price = resultSet.getDouble(9);
//                String image = resultSet.getString(10);
//                Coupon coupon = new Coupon(id, companyId, categoryId, titel, couponDescription, startDate, endDate, amount, price, image);
//                all.add(coupon);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            pool.returnConnetion(connection);
//        }
//        return all;
//    }
//
//    @Override
//    public Coupon getOneCoupon(long couponId) throws NotExistException {
//        Coupon coupon = null;
//        String sql = "SELECT * FROM coupons.coupons WHERE ID=?";
//        Connection connection = pool.getConnection();
//        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
//            ResultSet resultSet = prstm.executeQuery();
//            if (resultSet.next()) {
//                long companyId = resultSet.getLong(2);
//                long categoryId = resultSet.getLong(3);
//                String titel = resultSet.getString(4);
//                String couponDescription = resultSet.getString(5);
//                LocalDate startDate = resultSet.getDate(6).toLocalDate();
//                LocalDate endDate = resultSet.getDate(7).toLocalDate();
//                int amount = resultSet.getInt(8);
//                double price = resultSet.getDouble(9);
//                String image = resultSet.getString(10);
//                coupon = new Coupon(couponId, companyId, categoryId, titel, couponDescription, startDate, endDate, amount, price, image);
//            } else {
//                throw new NotExistException("there is no coupon");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            pool.returnConnetion(connection);
//        }
//        return coupon;
//    }
//
//    @Override
//    public void addCouponPurchase(long customerId, long couponId) throws CouponEmptyException, DoubelCouponsException {
//        Integer amount = null;
//        if (!thereIsMoreCoupons(couponId)) {
//            throw new CouponEmptyException("the coupon is no more evilable");
//        } else {
//            try {
//                amount = getOneCoupon(couponId).getAmount();
//            } catch (NotExistException e) {
//                e.printStackTrace();
//            }
//        }
//        if (youHaveThisCoupon(couponId, customerId))
//            throw new DoubelCouponsException("you can not have more then one of the same coupon");
//
//        String sql = "UPDATE coupons.coupons SET AMOUNT=? WHERE ID=?";
//        Connection connection = pool.getConnection();
//        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
//            prstm.setInt(1, amount - 1);
//            prstm.setLong(2, couponId);
//            prstm.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            pool.returnConnetion(connection);
//        }
//        sql = "INSERT INTO coupons.customers_vs_coupons (CUSTOMER_ID,COUPONS_ID) VALUES (?,?)";
//        connection = pool.getConnection();
//        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
//            prstm.setLong(1, customerId);
//            prstm.setLong(2, couponId);
//            prstm.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            pool.returnConnetion(connection);
//        }
//
//    }
//
//    @Override
//    public void deleteCouponPurchase(long customerId, long couponId) throws NotExistException {
//        if (!youHaveThisCoupon(couponId, customerId)) throw new NotExistException("you don't have this coupon");
//
//        String sql = "DELETE FROM coupons.customers_vs_coupons WHERE CUSTOMER_ID=? AND COUPONS_ID=?";
//        Connection connection = pool.getConnection();
//        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
//            prstm.setLong(1, customerId);
//            prstm.setLong(2, couponId);
//            prstm.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            pool.returnConnetion(connection);
//        }
//    }
}
