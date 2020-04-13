package dao.DBDAO;

import dao.DAO.CategoryDAO;
import entites.Category;
import exceptions.NotExistException;
import pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CategoryDBDAO implements CategoryDAO {

    private ConnectionPool pool;

    public CategoryDBDAO() {
        pool = ConnectionPool.getInstance();
    }

    @Override
    public Boolean isExist(Category category) throws NotExistException {
        Boolean isExist = false;
        if(category==null){
            throw new NotExistException("you need to enter value");
        }
        String sql = "SELECT * FROM categories WHERE NAME= ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setString(1, category.name());
            ResultSet resultSet = prstm.executeQuery();
            if (resultSet.next()) {
                isExist = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return isExist;
    }

    public Boolean isExistById(Long categoryId) throws NotExistException {
        Boolean isExist = false;
        if(categoryId==null){
            throw new NotExistException("you need to enter value");
        }
        String sql = "SELECT * FROM categories WHERE ID= ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, categoryId);
            ResultSet resultSet = prstm.executeQuery();
            if (resultSet.next()) {
                isExist = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return isExist;
    }

    @Override
    public void addCategory(Category category) {
        String sql = "INSERT INTO categories (NAME ) VALUES (?)";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prstm.setString(1, category.name());
            prstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
    }

    @Override
    public Long getIdCategory(Category category) {
        Long id = null;
        String sql = "SELECT * FROM categories WHERE NAME= ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setString(1, category.name());
            ResultSet resultSet = prstm.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getLong("ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return id;
    }

    @Override
    public void deleteCategory(Long id) {
        String sql = "DELETE FROM categories WHERE ID= ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, id);
            prstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
    }

    public Boolean isCategoryInUse(Long id) throws NotExistException {
        Boolean isInUse=false;
        if(id==null){
            throw new NotExistException("you need to enter value");
        }
        String sql = "SELECT * FROM coupons WHERE CAT_ID = ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, id);
            ResultSet resultSet = prstm.executeQuery();
            if (resultSet.next()) {
                isInUse=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return isInUse;    }
}
