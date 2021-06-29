/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lab.order;

import com.lab.main.Main;
import static com.lab.order.OrderController.properties;
import com.lab.role.UserRole;
import com.lab.product.Product;
import com.lab.user.User;
import com.lab.user.UserView;
import com.lab.utilities.OrderDataIO;
import com.lab.utilities.Validate;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.PropertyConfigurator;

public class OrderView {

    public static OrderView orderController = null;
    private Product product;
    private Order order;

    ArrayList<Order> orders;
    private final OrderDataIO orderDataIO;
    private final Validate validate;

    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(OrderView.class.getName());
    static Properties properties;
    static String log = "log4j.properties";

    public OrderView() throws FileNotFoundException, IOException {
        orderDataIO = new OrderDataIO();
        validate = new Validate();
        orders = new ArrayList<>();
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(log)) {
            try {
                properties.load(fis);
            } catch (FileNotFoundException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                fis.close();
            }
        }
        PropertyConfigurator.configure(properties);
    }

    public static OrderView getInstance() throws IOException {
        if (orderController == null) {
            orderController = new OrderView();
        }
        return orderController;
    }

    public void menu() {
        logger.debug("--------------------------------");
        logger.debug("Manage Order");
        logger.debug("1. Update status");
        logger.debug("2. Add");
        logger.debug("3. Update");
        logger.debug("4. Delete");
        logger.debug("5. Back");
        logger.debug("--------------------------------");
    }

    public void displayOrder(ArrayList<Order> list) {
        logger.debug("----------------------------------------------------------------------------");
        System.err.format("|%20s|%15s|%20s|%10s|\n", "CustomerName", "OrderDate", "Sale'sUserCode", "Status");
        if (list != null) {
            list.stream().map((o) -> {
                System.err.format("|%20s|%15s|%20s|%10s|\n", o.getCustomerName(), o.getOrderDate(), o.getUserCode(), o.getStatus());
                return o;
            }).map((Order o) -> {
                o.getProducts().forEach((p) -> {
                    System.err.format("Product|id:%10s|price:%10.2f|quantity:%10d|\n", p.getProductId(), p.getPrice(), p.getQuantity());
                });
                return o;
            }).forEachOrdered((o) -> {
                System.err.format("|Amount: %20.2f|\n", o.getAmount());
            });
        } else {
            logger.debug("ngu");
        }
        logger.debug("----------------------------------------------------------------------------");
    }

    public void add() {
        try {
            String CustomerName = (new Validate()).getString("CustomerName: ");
            Date OrderDate = (new Validate()).getDate("OrderDate: ");
            String UserCode = (new Validate()).getString("UserCode: ");
            String Status = (new Validate()).getOrderStatus("Status: ", 0);

            int choice = -1;
            ArrayList<Product> listProduct = new ArrayList<>();
            Product p = new Product();
            while (true) {

                choice = validate.getINT_LIMIT("Your choice: \n 1:add product\n 2:exit", 1, 2);
                switch (choice) {
                    case 1:
                        int id = (new Validate()).getINT("ProductID: ");
                        p = (new OrderController()).getProductID(id);
                        logger.debug(p);
                        if (p == null) {
                            logger.debug("Dont have product with id %d" + id);
                        } else {
                            listProduct.add(p);
                        }
                        break;
                    case 2:

                        (new OrderController()).updateOrder(new Order(CustomerName, OrderDate, UserCode, Status, listProduct));
                        logger.debug("Successful!!!\n");
                        return;

                }

            }

        } catch (IOException ex) {
            Logger.getLogger(OrderView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete() {
        try {
            String userCode = (new Validate()).getString("Sale’s UserCode: ");
            (new OrderController()).deleteOrder(userCode);
            logger.debug("Successful!!!\n");
        } catch (IOException ex) {
            Logger.getLogger(OrderView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update() {
        try {
            String UserCode = "";
            while (true) {
                UserCode = (new Validate()).getString("Sale's UserCode: ");
                if ((new OrderController()).getOrderbyUserCode(UserCode) != null) {
                    break;
                }
                logger.debug("Pls, Usercode not found!!, PLS try again ");
            }
            String CustomerName = (new Validate()).getString("newCustomerName: ");
            Date OrderDate = (new Validate()).getDate("newOrderDate: ");
            String Status = (new Validate()).getOrderStatus("newStatus: ", 0);
            int choice = -1;
            ArrayList<Product> listProduct = new ArrayList<>();
            Product p = new Product();
            while (true) {
                try {
                    choice = validate.getINT_LIMIT("Your choice: \n 1:add product\n 2:exit", 1, 2);
                    switch (choice) {
                        case 1:
                            int id = (new Validate()).getINT("ProductID:");
                            p = (new OrderController()).getProductID(id);
                            if (p == null) {
                                logger.debug("Dont have product with id %d" + id);
                            } else {
                                listProduct.add(p);
                            }
                            break;
                        case 2:
                            (new OrderController()).updateOrder(new Order(CustomerName, OrderDate, UserCode, Status, listProduct));
                            logger.debug("Successful!!!\n");
                            return;

                    }
                } catch (IOException e) {
                    Logger.getLogger(OrderView.class.getName()).log(Level.SEVERE, null, e);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(OrderView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateStatus(User user) {
        try {
            String UserCode = "";
            String Status = "";
            Order order = null;
            while (true) {
                UserCode = (new Validate()).getString("Sale's UserCode: ");
                order = (new OrderController()).getOrderbyUserCode(UserCode);
                if (order != null) {
                    break;
                }

                logger.debug("Pls, Usercode not found!!, PLS try again ");
            }

            if (order.getStatus().equalsIgnoreCase("submitted")) {
                Status = (new Validate()).getOrderStatus("Status: ", 1);
            } else {
                if (user.getUserRole() == UserRole.ADMIN) {
                    Status = (new Validate()).getOrderStatus("Status: ", 0);
                } else {
                    logger.debug("You cant update status");
                    return;
                }
            }
            (new OrderController()).updateOrderStatus(UserCode, Status);

        } catch (IOException e) {
            logger.debug(e.getMessage());
        }

    }

    public void viewOrder() {

        int choice = -1;
        while (true) {
            try {
                menu();
                choice = validate.getINT_LIMIT("Your choice: ", 1, 5);
                switch (choice) {
                    case 1:
                        updateStatus((new UserView()).getLoggedInUser());
                        break;
                    case 2:
                        add();
                        break;
                    case 3:
                        update();
                        break;
                    case 4:
                        delete();
                        break;
                    case 5:
                        return;
                }
            } catch (IOException ex) {
                Logger.getLogger(OrderView.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    public void viewOrderByGroup() throws IOException {
        orders = orderDataIO.readData();
        HashMap<String, Double> map = new HashMap();
        orders.forEach((i) -> {
            if (map.containsKey(i.getUserCode())) {
                double price = map.get(i.getUserCode()) + i.getAmount();
                map.put(i.getUserCode(), price);
            } else {
                map.put(i.getUserCode(), i.getAmount());
            }
        });

        logger.debug("---------------- Orders group by sales ----------------");
        Set<String> set = map.keySet();
        set.forEach((key) -> {
            logger.debug(key + "\t\t" + map.get(key));
        });
    }
}
