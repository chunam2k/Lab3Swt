/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order;

import Common.UserRole;
import Product.Product;
import User.User;
import User.UserController;
import Utilities.OrderDataIO;
import Utilities.Validate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderController {

    public static OrderController orderController = null;
    private Product product;
    private Order order;

    ArrayList<Order> orders;
    private OrderDataIO orderDataIO;
    private Validate validate;

    public OrderController() {
        orderDataIO = new OrderDataIO();
        validate = new Validate();
        orders = new ArrayList<Order>();
    }

    public static OrderController getInstance() {
        if (orderController == null) {
            orderController = new OrderController();
        }
        return orderController;
    }

    public void menu() {
        System.out.println("--------------------------------");
        System.out.println("Manage Order");
        System.out.println("1. Update status");
        System.out.println("2. Add");
        System.out.println("3. Update");
        System.out.println("4. Delete");
        System.out.println("5. Back");
        System.out.println("--------------------------------");
    }

    public void displayOrder(ArrayList<Order> list) {
        System.out.println("----------------------------------------------------------------------------");
        System.out.format("|%20s|%15s|%20s|%10s|\n", "CustomerName", "OrderDate", "Sale'sUserCode", "Status");
        if (list != null) {
            for (Order o : list) {
                System.out.format("|%20s|%15s|%20s|%10s|\n", o.getCustomerName(), o.getOrderDate(), o.getUserCode(), o.getStatus());
                for (Product p : o.getProducts()) {
                    System.out.format("Product|id:%10s|price:%10.2f|quantity:%10d|\n", p.getProductId(), p.getPrice(), p.getQuantity());

                }
                System.out.format("|Amount: %20.2f|\n", o.getAmount());

            }
        } else {
            System.out.println("ngu");
        }
        System.out.println("----------------------------------------------------------------------------");
    }

    public void add() {
        try {
            String CustomerName = (new Validate()).getString("CustomerName: ");
            Date OrderDate = (new Validate()).getDate("OrderDate: ");
            String UserCode = (new Validate()).getString("UserCode: ");
            String Status = (new Validate()).getOrderStatus("Status: ", 0);

            int choice = -1;
            ArrayList<Product> listProduct = new ArrayList<Product>();
            Product product = new Product();
            while (true) {

                choice = validate.getINT_LIMIT("Your choice: \n 1:add product\n 2:exit", 1, 2);
                switch (choice) {
                    case 1:
                        int id = (new Validate()).getINT("ProductID: ");
                        product = (new OrderView()).getProductID(id);
                        System.out.println(product);
                        if (product == null) {
                            System.out.println("Dont have product with id %d" + id);
                        } else {
                            listProduct.add(product);
                        }
                        break;
                    case 2:

                        (new OrderView()).updateOrder(new Order(CustomerName, OrderDate, UserCode, Status, listProduct));
                        System.out.println("Successful!!!\n");
                        return;

                }

            }

        } catch (IOException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete() {
        try {
            String userCode = (new Validate()).getString("Sale’s UserCode: ");
            (new OrderView()).deleteOrder(userCode);
            System.out.println("Successful!!!\n");
        } catch (IOException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update() {
        try {
            String UserCode = "";
            while (true) {
                UserCode = (new Validate()).getString("Sale's UserCode: ");
                if ((new OrderView()).getOrderbyUserCode(UserCode) != null) {
                    break;
                }
                System.out.println("Pls, Usercode not found!!, PLS try again ");
            }
            String CustomerName = (new Validate()).getString("newCustomerName: ");
            Date OrderDate = (new Validate()).getDate("newOrderDate: ");
            String Status = (new Validate()).getOrderStatus("newStatus: ", 0);
            int choice = -1;
            ArrayList<Product> listProduct = new ArrayList<Product>();
            Product product = new Product();
            while (true) {
                try {
                    choice = validate.getINT_LIMIT("Your choice: \n 1:add product\n 2:exit", 1, 2);
                    switch (choice) {
                        case 1:
                            int id = (new Validate()).getINT("ProductID:");
                            product = (new OrderView()).getProductID(id);
                            if (product == null) {
                                System.out.println("Dont have product with id %d" + id);
                            } else {
                                listProduct.add(product);
                            }
                            break;
                        case 2:
                            (new OrderView()).updateOrder(new Order(CustomerName, OrderDate, UserCode, Status, listProduct));
                            System.out.println("Successful!!!\n");
                            return;

                    }
                } catch (Exception e) {
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateStatus(User user) {
        try {
            String UserCode = "";
            String Status = "";
            Order order = null;
            while (true) {
                UserCode = (new Validate()).getString("Sale's UserCode: ");
                order = (new OrderView()).getOrderbyUserCode(UserCode);
                if (order != null) {
                    break;
                }

                System.out.println("Pls, Usercode not found!!, PLS try again ");
            }

            if (order.getStatus().equalsIgnoreCase("submitted")) {
                Status = (new Validate()).getOrderStatus("Status: ", 1);
            } else {
                if (user.getUserRole() == UserRole.ADMIN) {
                    Status = (new Validate()).getOrderStatus("Status: ", 0);
                } else {
                    System.out.println("You cant update status");
                    return;
                }
            }
            (new OrderView()).updateOrderStatus(UserCode, Status);

        } catch (Exception e) {
            System.out.println(e.getMessage());
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
                        updateStatus((new UserController()).getLoggedInUser());
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

            }
        }
    }

    public void viewOrderByGroup() {
        orders = orderDataIO.readData();
        HashMap<String, Double> map = new HashMap();
        for (Order i : orders) {
            if (map.containsKey(i.getUserCode())) {
                double price = map.get(i.getUserCode()) + i.getAmount();
                map.put(i.getUserCode(), price);
            } else {
                map.put(i.getUserCode(), i.getAmount());
            }
        }

        System.out.println("---------------- Orders group by sales ----------------");
        Set<String> set = map.keySet();
        for (String key : set) {
            System.out.println(key + "\t\t" + map.get(key));
        }
    }
}
