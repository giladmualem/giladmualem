package dao;

import entites.Customer;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;
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
    public boolean isCustomerExists(String email, String password) {
        String sql = "SELECT * FROM coupons.customers WHERE EMAIL=? AND PASSWORD=?;";
        Connection connection = pool.getConnection();
        boolean isExists = false;
        try (PreparedStatement prsmt = connection.prepareStatement(sql)) {
            prsmt.setString(1, email);
            prsmt.setString(2, password);
            ResultSet resultSet = prsmt.executeQuery();
            if (resultSet.next()) {
                isExists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }

        return isExists;
    }

    @Override
    public Customer addCustomer(Customer customer) throws AlreadyExistException {
        if (isCustomerExists(customer.getEmail(), customer.getPassword())) {
            throw new AlreadyExistException("the customer is allready exist");
        }
        String sql = "INSERT INTO CUSTOMERS (FIRST_NAME, LAST_NAME,EMAIL,PASSWORD) VALUES(?,?,?,?)";
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

        return customer;
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE coupons.customers SET FIRST_NAME=?,LAST_NAME=?,EMAIL=?,PASSWORD=? WHERE ID=?";
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
    public void deleteCustomer(long customerID) throws NotExistException {
        String sql = "DELETE FROM coupons.customers WHERE ID=?;";
        Connection connection = pool.getConnection();
        if (getOneCustomer(customerID) == null) {
            throw new NotExistException("there is no customer");
        }
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
        String sql = "SELECT * FROM customers ";
        Connection connection = pool.getConnection();
        List<Customer> all = new ArrayList<>();
        try (PreparedStatement prsmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = prsmt.executeQuery();
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
    public Customer getOneCustomer(long customerID) throws NotExistException {
        Customer customer = null;
        String sql = "SELECT * FROM coupons.customers WHERE ID = ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prsmt = connection.prepareStatement(sql)) {
            prsmt.setLong(1, customerID);
            ResultSet resultSet = prsmt.executeQuery();
            if (resultSet.next() == false) {
                throw new NotExistException("there is no customer");
            }
            String firstName = resultSet.getString("FIRST_NAME");
            String lastName = resultSet.getString("LAST_NAME");
            String email = resultSet.getString("EMAIL");
            String password = resultSet.getString("PASSWORD");
            customer = new Customer(customerID, firstName, lastName, email, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return customer;
    }
}
