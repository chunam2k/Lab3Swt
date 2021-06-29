package Main;


import Admin.AdminController;
import Common.UserRole;
import Order.Order;
import Order.OrderController;
import Product.Product;
import Product.ProductController;
import static Product.ProductController.productController;
import User.User;
import User.UserController;
import Utilities.OrderDataIO;
import Utilities.ProductDataIO;
import Utilities.UserDataIO;
import Utilities.Validate;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    static Validate validate;

    static ArrayList<User> users;
    static ArrayList<Product> products;
    static ArrayList<Order> orders;

    static UserController userController;
    static OrderController orderController;
    static AdminController adminController;

    static UserDataIO userDataIO;
    static ProductDataIO productDataIO;
    static OrderDataIO orderDataIO;

    public static void intilizacontructor() {
        validate = new Validate();

        userController = new UserController();
        orderController = new OrderController();
        adminController = new AdminController();
        productController = new ProductController();

        userDataIO = new UserDataIO();
        productDataIO = new ProductDataIO();
        orderDataIO = new OrderDataIO();

    }

    public static void initData() throws ParseException {
        users = new ArrayList<>();
        products = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public static void main(String[] args) throws ParseException {
        intilizacontructor();
        initData();
        loginMenu();
        mainMenu();
    }

    private static void printLoginMenu() {
        System.out.println("--------------------------------");
        System.out.println("*** Welcome to Sale Management Program ***");
        System.out.println("1. Login");
        System.out.println("0. Exit");
        System.out.println("--------------------------------");
        System.out.println("Your choice : ");
    }

    private static void printAdminMenu() {
        System.out.println("--------------------------------");
        System.out.println("ADMIN PANEL");
        System.out.println("1. View list, add, update, delete product");
        System.out.println("2. Add, update, delete order details then update order status");
        System.out.println("3. Query order information, including the total revenues, group by sales");
        System.out.println("4. View list, add, update, delete user; change user’s password");
        System.out.println("5. Query & print out the product statistics with following information, \n"
                + "group by the product category: id, name, avail, sold, cancelled");
        System.out.println("6. Change password");
        System.out.println("7. Logout");
        System.out.println("--------------------------------");

    }

    private static void printSaleMenu() {
        System.out.println("--------------------------------");
        System.out.println("SALE PANEL");
        System.out.println("1. Add, update, delete order details then update order status");
        System.out.println("2. Query order information, including the total revenues, group by sales");
        System.out.println("3. Change password");
        System.out.println("4. Log out");
        System.out.println("--------------------------------");
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
                        System.out.println("THANK YOU ! WELCOME BACK ^_^");
                        return;
                    case 1:
                        isLoggedIn = userController.login();
                        break;
                    default:
                }

                if (isLoggedIn) {
                    System.out.println("LOGGED IN SUCCESSFULLY!!");
                    break;
                } else {
                    System.out.println("LOGGED IN FAILED!!");
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void mainMenu() {
        User user = userController.getLoggedInUser();

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
                        productController.viewProduct();
                        break;
                    case 2:
                        orderController.viewOrder();
                        break;
                    case 3:
                        orderController.viewOrderByGroup();
                        break;
                    case 4:
                        adminController.viewMenuAdmin();
                        break;
                    case 5:
                        productController.viewGroupProduct();
                        break;
                    case 6:
                        userController.changePassword();
                        break;
                    case 7:
                        userController.logout();
                        loginMenu();
                        mainMenu();
                        return;
                    default:
                        break;

                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
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
                        orderController.viewOrder();
                        break;
                    case 2:
                        orderController.viewOrderByGroup();
                        break;
                    case 3:
                        userController.changePassword();
                        break;
                    case 4:
                        userController.logout();
                        loginMenu();
                        mainMenu();
                        return;
                    default:
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
