package dao.DBDAO;

import dao.DAO.PurchaseDAO;
import pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PurchaseDBDAO implements PurchaseDAO {

    private ConnectionPool pool;

    public PurchaseDBDAO() {
        pool = ConnectionPool.getInstance();
    }

    @Override
    public Boolean isCouponPurchase(Long customerId, Long couponId) {
        Boolean isExists=false;
        String sql="SELECT * FROM customersvscoupons WHERE  CUS_ID=? AND COU_ID=?;";
        Connection connection=pool.getConnection();
        try(PreparedStatement prstm=connection.prepareStatement(sql)){
            prstm.setLong(1,customerId);
            prstm.setLong(2,couponId);
            ResultSet resultSet=prstm.executeQuery();
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

    @Override
    public void addPurchase(Long customerId, Long couponId) {
        String sql="INSERT INTO customersvscoupons (CUS_ID, COU_ID) values (?,?)";
        Connection connection=pool.getConnection();
        try(PreparedStatement prstm=connection.prepareStatement(sql)){
            prstm.setLong(1,customerId);
            prstm.setLong(2,couponId);
            prstm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            pool.returnConnetion(connection);
        }

    }

    @Override
    public void deletePurchase(Long customerId, Long couponId) {
        String sql="DELETE FROM customersvscoupons WHERE  CUS_ID=? AND COU_ID=?";
        Connection connection=pool.getConnection();
        try(PreparedStatement prstm=connection.prepareStatement(sql)){
            prstm.setLong(1,customerId);
            prstm.setLong(2,couponId);
            prstm.executeUpdate();
        }catch (Exception e){

        }finally {
            pool.returnConnetion(connection);
        }
    }

   public ArrayList<Long>customerPurchase(Long couponId){
        ArrayList<Long>customer=new ArrayList<>();
        String sql="SELECT * FROM coupons.customersvscoupons WHERE COU_ID=?";
        Connection connection=pool.getConnection();
        try(PreparedStatement prstm=connection.prepareStatement(sql)){
            prstm.setLong(1,couponId);
            ResultSet resultSet=prstm.executeQuery();
            if(resultSet.next()){
                customer.add(resultSet.getLong("CUS_ID"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            pool.returnConnetion(connection);
        }
        return customer;
   }
}
