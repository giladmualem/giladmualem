package dao;

import entites.Companies;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;
import pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompaniesDBDAO implements CompaniesDAO {

    private ConnectionPool pool;

    public CompaniesDBDAO() {
        pool = ConnectionPool.getInstance();
    }

    public Companies addCompany(Companies companies) throws AlreadyExistException {
        if (companies == null) {
            return null;
        }
        if (isCompanyExists(companies.getEmail(), companies.getPassword())) {
            throw new AlreadyExistException("the Company is already exists");
        }

        String sql = "INSERT INTO COMPANIES(NAME , EMAIL, PASSWORD) VALUES (? ,? , ?)";
        Connection connection = pool.getConnection();
        try (PreparedStatement pstmt =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, companies.getName());
            pstmt.setString(2, companies.getEmail());
            pstmt.setString(3, companies.getPassword());

            pstmt.executeUpdate();

            ResultSet resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                companies.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return companies;
    }

    @Override
    public Companies getOneCompany(long id) throws NotExistException {
        Companies company = null;
        String sql = "SELECT * FROM COMPANIES WHERE ID=?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, id);
            ResultSet resultSet = prstm.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistException("there is no company");
            } else {
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                company = new Companies(id, name, email, password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return company;
    }


    @Override
    public boolean isCompanyExists(String email, String password) {
        String sql = "SELECT * FROM coupons.companies WHERE EMAIL=? AND PASSWORD=?";
        Connection connection = pool.getConnection();
        boolean isExists = false;
        try (PreparedStatement prsmt = connection.prepareStatement(sql)) {
            prsmt.setString(1, email);
            prsmt.setString(2, password);
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
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
    public void updateCompany(Companies company) throws NotExistException {
        if (getOneCompany(company.getId()) == null) throw new NotExistException("there is no company");
        String sql = "UPDATE coupons.companies SET NAME=?,EMAIL=?,PASSWORD=? WHERE ID=?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prsmt = connection.prepareStatement(sql)) {
            prsmt.setString(1, company.getName());
            prsmt.setString(2, company.getEmail());
            prsmt.setString(3, company.getPassword());
            prsmt.setLong(4, company.getId());
            prsmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
    }

    @Override
    public void deleteCompany(long companyId) throws NotExistException {
        if (getOneCompany(companyId) == null) throw new NotExistException("there is no company");

        String sql = "DELETE FROM coupons.companies WHERE ID=?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prsmt = connection.prepareStatement(sql)) {
            prsmt.setLong(1, companyId);
            prsmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
    }

    @Override
    public List<Companies> getAllCompanies() {
        List<Companies> allCompanies = new ArrayList();
        String sql = "SELECT * FROM coupons.companies";
        Connection connection = pool.getConnection();
        try (PreparedStatement prsmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = prsmt.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("ID");
                String name = resultSet.getString("NAME");
                String email = resultSet.getString("EMAIL");
                String password = resultSet.getString("PASSWORD");
                Companies company = new Companies(id, name, email, password);
                allCompanies.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return allCompanies;
    }
}
