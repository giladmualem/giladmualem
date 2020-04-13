package dao.DBDAO;

import dao.DAO.CustomersDAO;
import entites.Customer;
import pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomersDBDAO implements CustomersDAO {
    private ConnectionPool pool;

    public CustomersDBDAO() {
        pool = ConnectionPool.getInstance();
    }

    @Override
    public Boolean isCustomerExists(String email, String password) {
        Boolean isExist = false;
        String sql = "SELECT * FROM customers WHERE EMAIL=? AND PASSWORD=?;";
        Connection connection = pool.getConnection();
        try (PreparedStatement prsmt = connection.prepareStatement(sql)) {
            prsmt.setString(1, email);
            prsmt.setString(2, password);
            ResultSet resultSet = prsmt.executeQuery();
            if (resultSet.next()) {
                isExist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return isExist;
    }

    @Override
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (FNAME, LNAME,EMAIL,PASSWORD) VALUES(?,?,?,?)";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prstm.setString(1, customer.getFirstName());
            prstm.setString(2, customer.getLastName());
            prstm.setString(3, customer.getEmail());
            prstm.setString(4, customer.getPassword());
            prstm.executeUpdate();
            ResultSet resultSet = prstm.getGeneratedKeys();
            if (resultSet.next()) {
                customer.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET FNAME=?,LNAME=?,EMAIL=?,PASSWORD=? WHERE ID=?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prsmt = connection.prepareStatement(sql)) {
            prsmt.setString(1, customer.getFirstName());
            prsmt.setString(2, customer.getLastName());
            prsmt.setString(3, customer.getEmail());
            prsmt.setString(4, customer.getPassword());
            prsmt.setLong(5, customer.getId());
            prsmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
    }

    @Override
    public void deleteCustomer(Long customerID) {
        String sql = "DELETE FROM customers WHERE ID=?;";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, customerID);
            prstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
    }

    @Override
    public List<Customer> allCustomer() {
        List<Customer> all = null;
        String sql = "SELECT * FROM customers ";
        Connection connection = pool.getConnection();
        try (PreparedStatement prsmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = prsmt.executeQuery();
            all = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String email = resultSet.getString(4);
                String password = resultSet.getString(5);
                Customer customer = new Customer(id, firstName, lastName, email, password);
                all.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return all;
    }

    @Override
    public Customer getOneCustomer(Long customerID) {
        Customer customer = null;
        String sql = "SELECT * FROM coupons.customers WHERE ID = ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prsmt = connection.prepareStatement(sql)) {
            prsmt.setLong(1, customerID);
            ResultSet resultSet = prsmt.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("FNAME");
                String lastName = resultSet.getString("LNAME");
                String email = resultSet.getString("EMAIL");
                String password = resultSet.getString("PASSWORD");
                customer = new Customer(customerID, firstName, lastName, email, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return customer;
    }

    public Customer getOneCustomer(String email, String password) {
        Customer customer = null;
        String sql = "SELECT * FROM coupons.customers WHERE EMAIL = ? AND PASSWORD = ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prsmt = connection.prepareStatement(sql)) {
            prsmt.setString(1, email);
            prsmt.setString(2, password);
            ResultSet resultSet = prsmt.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String firstName = resultSet.getString("FNAME");
                String lastName = resultSet.getString("LNAME");
                customer = new Customer(id, firstName, lastName, email, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return customer;
    }

    public Boolean isCustemerExistByEmail(String email) {
        Boolean isExits = false;
        String sql = "SELECT * FROM customers WHERE EMAIL=? ";
        Connection connection = pool.getConnection();
        try (PreparedStatement prsmt = connection.prepareStatement(sql)) {
            prsmt.setString(1, email);
            ResultSet resultSet = prsmt.executeQuery();
            if (resultSet.next()) {
                isExits = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return isExits;

    }
}
