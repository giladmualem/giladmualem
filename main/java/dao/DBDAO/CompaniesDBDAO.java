package dao.DBDAO;

import dao.DAO.CompaniesDAO;
import entites.Companies;
import pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompaniesDBDAO implements CompaniesDAO {

    private ConnectionPool pool;

    public CompaniesDBDAO() {
        pool = ConnectionPool.getInstance();
    }

    @Override
    public Boolean isCompanyExists(String email, String password) {
        Boolean isExist = false;
        String sql = "SELECT * FROM companies WHERE EMAIL=? AND PASSWORD=?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setString(1, email);
            prstm.setString(2, password);
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
    public void addCompany(Companies company) {
        String sql = "INSERT INTO companies (NAME ,EMAIL,PASSWORD)VALUES (?,?,?)";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prstm.setString(1, company.getName());
            prstm.setString(2, company.getEmail());
            prstm.setString(3, company.getPassword());
            prstm.executeUpdate();
            ResultSet resultSet = prstm.getGeneratedKeys();
            if (resultSet.next()) {
                company.setId(resultSet.getLong(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
    }

    @Override
    public void updateCompany(Companies company) {
        String sql = "UPDATE companies SET NAME= ? ,EMAIL= ? ,PASSWORD= ? WHERE ID= ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setString(1, company.getName());
            prstm.setString(2, company.getEmail());
            prstm.setString(3, company.getPassword());
            prstm.setLong(4, company.getId());
            prstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
    }

    @Override
    public void deleteCompany(Long companyId) {
        String sql = "DELETE FROM companies WHERE ID= ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, companyId);
            prstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
    }

    @Override
    public ArrayList<Companies> getAllCompanies() {
        ArrayList<Companies> all = null;
        String sql = "SELECT * FROM companies";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            ResultSet resultSet = prstm.executeQuery();
            all = new ArrayList();
            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String name = resultSet.getString("NAME");
                String email = resultSet.getString("EMAIL");
                String password = resultSet.getString("PASSWORD");
                all.add(new Companies(id, name, email, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return all;
    }

    public Companies getByEmailAndPassword(String email, String password) {
        Companies company = null;
        String sql = "SELECT * FROM  companies WHERE EMAIL = ? AND PASSWORD = ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setString(1, email);
            prstm.setString(2, password);
            ResultSet resultSet = prstm.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                company = new Companies(id, name, email, password);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return company;
    }

    public Optional<Companies> findById(Long id) {
        return Optional.ofNullable(getOneCompany(id));
    }


    @Override
    public Companies getOneCompany(Long id) {
        Companies company = null;
        String sql = "SELECT * FROM  companies WHERE ID= ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setLong(1, id);
            ResultSet resultSet = prstm.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("NAME");
                String email = resultSet.getString("EMAIL");
                String password = resultSet.getString("PASSWORD");
                company = new Companies(id, name, email, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return company;
    }

    public Companies getOneCompany(String name) {
        Companies company = null;
        String sql = "SELECT * FROM  companies WHERE NAME= ?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setString(1, name);
            ResultSet resultSet = prstm.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String email = resultSet.getString("EMAIL");
                String password = resultSet.getString("PASSWORD");
                company = new Companies(id, name, email, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnConnetion(connection);
        }
        return company;
    }

    public Boolean isNameCompanyExists(String name) {
        Boolean isExist = false;
        String sql = "SELECT * FROM companies WHERE NAME =? ";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setString(1, name);
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

    public Boolean isEmailCompanyExists(String email) {
        Boolean isExist = false;
        String sql = "SELECT * FROM companies WHERE EMAIL=?";
        Connection connection = pool.getConnection();
        try (PreparedStatement prstm = connection.prepareStatement(sql)) {
            prstm.setString(1, email);
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
}

