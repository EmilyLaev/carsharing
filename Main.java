package carsharing;

//import carsharing.dao.BaseDao;
//import carsharing.dao.CarDao;
//import carsharing.entitie.Car;
//import carsharing.dao.CompanyDao;
//import carsharing.entitie.Company;
//import carsharing.dao.CustomerDao;
//import carsharing.entitie.Customer;


import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        ControllerH2 controllerH2 = new ControllerH2(getDbName(args));
        CompanyDaoH2Impl companyDaoH2 = new CompanyDaoH2Impl(controllerH2.getConnection());
        CarDaoH2Impl carDaoH2 = new CarDaoH2Impl(controllerH2.getConnection());
        CustomerDaoH2Impl customerDaoH2 = new CustomerDaoH2Impl(controllerH2.getConnection());

        for(;;) {
            MenuPrinter.mainMenu();
            switch (Menu.userChoice()) {
                case 1:
                    Menu.managementCompany(companyDaoH2, carDaoH2);
                    break;
                case 2:
                    Menu.managementCustomerMenu(customerDaoH2, companyDaoH2, carDaoH2);
                    break;
                case 3:
                    Menu.managementCreateCustomer(customerDaoH2);
                    break;
                case 0:
                    controllerH2.closeConnection();
                    return;
            }
        }
    }


    private static String getDbName(String[] args) {
        if (args.length >= 2 && args[0].equals("-databaseFileName")) {
            return args[1];
        } else {
            return "carsharing";
        }
    }
}