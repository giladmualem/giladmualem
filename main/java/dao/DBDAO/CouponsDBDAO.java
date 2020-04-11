package dao.DBDAO;

import dao.DAO.CouponsDAO;
import entites.Category;
import entites.Companies;
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
    public void delete(Long couponId) {
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
    public Coupon getOneCoupon(Long couponId) {
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
    public ArrayList<Coupon> getAllCoupons() {
        ArrayList<Coupon> all = null;
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

    public ArrayList<Coupon> getAllCompnyCoupons(Long com_id) {
        ArrayList<Coupon> allCouponCompny = null;
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


    public Boolean isCouponExists(Long companyId,Coupon coupon){
        Boolean isExists=false;
        String sql = "SELECT * FROM coupons WHERE COM_ID = ? AND TITEL = ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, companyId);
            prstm.setString(2, coupon.getTitle());
            ResultSet resultSet = prstm.executeQuery();
            if(resultSet.next()){
                isExists=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            pool.returnConnetion(connection);
        }
        return isExists;
    }

}
