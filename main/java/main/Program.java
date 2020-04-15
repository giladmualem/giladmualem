package main;

import dao.DAO.CategoryDAO;
import dao.DAO.CompaniesDAO;
import dao.DAO.CouponsDAO;
import dao.DAO.CustomersDAO;
import dao.DBDAO.*;
import dao.test.Test;
import entites.Category;
import entites.Companies;
import entites.Coupon;
import entites.Customer;
import exceptions.AlreadyExistException;
import exceptions.NotExistException;
import exceptions.NotLoginException;
import fasade.AdminFacade;
import fasade.ClientFacade;
import fasade.CompanyFacade;
import fasade.CustomerFacade;
import job.CouponExpirationDailyJob;
import loginManager.ClientType;
import loginManager.LoginManager;
import pool.ConnectionPool;

import java.lang.invoke.LambdaConversionException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Program {
    public static void main(String[] args) {
        Test test=new Test();
        test.testAll();

    }

}
