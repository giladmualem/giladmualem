package main;

import dao.DAO.CategoryDAO;
import dao.DAO.CompaniesDAO;
import dao.DAO.CouponsDAO;
import dao.DAO.CustomersDAO;
import dao.DBDAO.CategoryDBDAO;
import dao.DBDAO.CompaniesDBDAO;
import dao.DBDAO.CouponsDBDAO;
import dao.DBDAO.CustomersDBDAO;
import entites.Category;
import entites.Companies;
import entites.Coupon;
import entites.Customer;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;
import exceptions.NotLoginException;
import fasade.AdminFacade;
import pool.ConnectionPool;

import java.lang.invoke.LambdaConversionException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Run {
    public static void main(String[] args) {
        ConnectionPool pool = ConnectionPool.getInstance();



        pool.closeConnection();

    }

}