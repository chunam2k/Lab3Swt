/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lab.main;

import com.lab.admin.AdminView;
import com.lab.role.UserRole;
import com.lab.order.Order;
import com.lab.order.OrderView;
import com.lab.product.Product;
import com.lab.product.ProductView;
import com.lab.user.User;
import com.lab.user.UserView;
import com.lab.utilities.OrderDataIO;
import com.lab.utilities.ProductDataIO;
import com.lab.utilities.UserDataIO;
import com.lab.utilities.Validate;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {

    static Validate validate;

    static ArrayList<User> users;
    static ArrayList<Product> products;
    static ArrayList<Order> orders;

    static UserView userView;
    static OrderView orderView;
    static AdminView adminView;
    static ProductView productView;

    static UserDataIO userDataIO;
    static ProductDataIO productDataIO;
    static OrderDataIO orderDataIO;

    static Logger logger = Logger.getLogger(Main.class.getName());
    static Properties properties;
    static String log = "log4j.properties";

    public static void intilizacontructor() {

        try (FileInputStream fis = new FileInputStream(log)) {
            validate = new Validate();
            userView = new UserView();
            orderView = new OrderView();
            adminView = new AdminView();
            productView = new ProductView();
            userDataIO = new UserDataIO();
            productDataIO = new ProductDataIO();
            orderDataIO = new OrderDataIO();
            properties = new Properties();
            try {
                properties.load(fis);
            } catch (FileNotFoundException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                fis.close();
            }
            PropertyConfigurator.configure(properties);
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void initData() throws ParseException {
        users = new ArrayList<>();
        products = new ArrayList<>();
        products.add(new Product(1, "pro1", 10, 100, "or1"));
        products.add(new Product(2, "pro2", 20, 100, "or2"));
        products.add(new Product(3, "pro3", 30, 100, "or3"));
        products.add(new Product(4, "pro4", 40, 100, "or4"));
        products.add(new Product(5, "pro5", 50, 100, "or5"));
        products.add(new Product(6, "pro6", 60, 100, "or6"));
        products.add(new Product(7, "pro7", 70, 100, "or7"));
        products.add(new Product(8, "pro8", 80, 100, "or8"));
        products.add(new Product(9, "pro9", 90, 100, "or9"));
        products.add(new Product(10, "pro10", 100, 100, "or10"));
        productDataIO.writeData(products);

        orders = new ArrayList<>();
    }

    public static void main(String[] args) throws ParseException, IOException {
        intilizacontructor();
        initData();
        loginMenu();
        mainMenu();

    }

    private static void printLoginMenu() {
        logger.debug("--------------------------------");
        logger.debug("*** Welcome to Sale Management Program ***");
        logger.debug("1. Login");
        logger.debug("0. Exit");
        logger.debug("--------------------------------");
        logger.debug("Your choice : ");
    }

    private static void printAdminMenu() {
        logger.debug("--------------------------------");
        logger.debug("ADMIN PANEL");
        logger.debug("1. View list, add, update, delete product");
        logger.debug("2. Add, update, delete order details then update order status");
        logger.debug("3. Query order information, including the total revenues, group by sales");
        logger.debug("4. View list, add, update, delete user; change user’s password");
        logger.debug("5. Query & print err the product statistics with following information, \n"
                + "group by the product category: id, name, avail, sold, cancelled");
        logger.debug("6. Change password");
        logger.debug("7. Logerr");
        logger.debug("--------------------------------");

    }

    private static void printSaleMenu() {
        logger.debug("--------------------------------");
        logger.debug("SALE PANEL");
        logger.debug("1. Add, update, delete order details then update order status");
        logger.debug("2. Query order information, including the total revenues, group by sales");
        logger.debug("3. Change password");
        logger.debug("4. Log err");
        logger.debug("--------------------------------");
    }

    private static void loginMenu() {
        int choice = -1;
        while (true) {
            try {
                printLoginMenu();
                choice = validate.getINT_LIMIT("", 0, 1);
                Boolean isLoggedIn = false;
                switch (choice) {
                    case 0:
                        logger.debug("THANK YOU ! WELCOME BACK ^_^");
                        return;
                    case 1:
                        isLoggedIn = userView.login();
                        break;
                    default:
                }

                if (isLoggedIn) {
                    logger.debug("LOGGED IN SUCCESSFULLY!!");
                    break;
                } else {
                    logger.debug("LOGGED IN FAILED!!");
                }
            } catch (IOException ex) {
            }
        }
    }

    private static void mainMenu() {
        User user = userView.getLoggedInUser();

        if (user != null) {
            if (user.getUserRole() == UserRole.ADMIN) {
                adminMenu();
            } else if (user.getUserRole() == UserRole.SALE) {
                saleMenu();
            }
        }
    }

    private static void adminMenu() {
        int choice = -1;
        while (true) {
            try {
                printAdminMenu();
                choice = validate.getINT_LIMIT("Your choice: ", 1, 7);

                switch (choice) {
                    case 1:
                        productView.viewProduct();
                        break;
                    case 2:
                        orderView.viewOrder();
                        break;
                    case 3:
                        orderView.viewOrderByGroup();
                        break;
                    case 4:
                        adminView.viewMenuAdmin();
                        break;
                    case 5:
                        productView.viewGroupProduct();
                        break;
                    case 6:
                        userView.changePassword();
                        break;
                    case 7:
                        userView.logerr();
                        loginMenu();
                        mainMenu();
                        return;
                    default:
                        break;

                }
            } catch (IOException ex) {
            }
        }

    }

    private static void saleMenu() {
        int choice = -1;
        while (true) {
            try {
                printSaleMenu();
                choice = validate.getINT_LIMIT("Your choice: ", 1, 4);

                switch (choice) {
                    case 1:
                        orderView.viewOrder();
                        break;
                    case 2:
                        orderView.viewOrderByGroup();
                        break;
                    case 3:
                        userView.changePassword();
                        break;
                    case 4:
                        userView.logerr();
                        loginMenu();
                        mainMenu();
                        return;
                    default:
                        break;
                }
            } catch (IOException ex) {
            }
        }
    }

}
