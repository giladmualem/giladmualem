package dao.test;

import exceptions.NotLoginException;
import fasade.AdminFacade;
import fasade.ClientFacade;
import fasade.CompanyFacade;
import fasade.CustomerFacade;
import job.CouponExpirationDailyJob;
import loginManager.ClientType;
import loginManager.LoginManager;
import pool.ConnectionPool;
import java.util.Scanner;

public class Test {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private LoginManager manager = LoginManager.getInstance();
    private AdminFacade adminFacade;
    private ClientFacade clientFacade;
    private CompanyFacade companyFacade;
    private CustomerFacade customerFacade;
    private Thread job;

    //  run the program to remove all the unupdated coupons
    public Test() {
        job = new Thread(new CouponExpirationDailyJob());
        job.setDaemon(true);
        job.start();
    }
    //  select the user and login
    //  run the task you like
    public void testAll() {

        Scanner input = new Scanner(System.in);
        String action = "";
        String exit = "exit";
        String login = "login";
        String back = "back";
        while (!action.equals(exit)) {
            System.out.println("please a pike (login , exit)");
            action = input.nextLine();
            if (action.equals(login)) {

                //  select the user type and login
                while (!action.equals(back) && !action.equals(exit)) {
                    System.out.println("are you \nADMINISTRATOR,\n" +
                            "    COMPANY,\n" +
                            "    CUSTOMER");
                    String userType = input.nextLine();
                    System.out.println("enter your email");
                    String userEmail = input.nextLine();
                    System.out.println("enter your password");
                    String userPassword = input.nextLine();
                    userType = userType.toUpperCase().trim();
                    try {
                        ClientType user = ClientType.valueOf(userType);
                        clientFacade = manager.login(user, userEmail, userPassword);
                    } catch (Exception e) {
                    }

                    //  if you login you go to the task for you
                    if (clientFacade instanceof AdminFacade) {
                        adminFacade = (AdminFacade) clientFacade;
                        adminAction();
                        adminFacade = null;
                    } else if (clientFacade instanceof CustomerFacade) {
                        customerFacade = (CustomerFacade) clientFacade;
                        customerAction();
                        customerFacade = null;
                    } else if (clientFacade instanceof CompanyFacade) {
                        companyFacade = (CompanyFacade) clientFacade;
                        companyAction();
                        companyFacade = null;
                    }
                    action = back;
                }

            }
        }
        //  torn off the program in the back
        //  CouponExpirationDailyJob
        turnOff();
    }

    public void turnOff() {
        job.interrupt();
        pool.closeConnection();
    }

    //              you add and change the task as you like

    //  task for admin
    public void adminAction() {
        System.out.println("admin your in");
        try {
            adminFacade.getAllCompanies().forEach(System.out::println);
        } catch (NotLoginException e) {
            e.printStackTrace();
        }
        try {
            adminFacade.getAllCustomers().forEach(System.out::println);
        } catch (NotLoginException e) {
            e.printStackTrace();
        }
    }

    //  task for customer
    public void customerAction() {
        System.out.println("customer your in");
        try {
            System.out.println(customerFacade.getCustomerDetails());
        } catch (NotLoginException e) {
            e.printStackTrace();
        }
        try {
            customerFacade.getAllCustomerCoupons().forEach(System.out::println);
        } catch (NotLoginException e) {
            e.printStackTrace();
        }
    }
    //  task for company
    public void companyAction() {
        System.out.println("company your ins");
        try {
            System.out.println(companyFacade.getCompanyDetails());
        } catch (NotLoginException e) {
            e.printStackTrace();
        }
        try {
            companyFacade.getCompanyCoupons().forEach(System.out::println);
        } catch (NotLoginException e) {
            e.printStackTrace();
        }
    }
}