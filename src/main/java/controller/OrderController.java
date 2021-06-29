package controller;

import com.lab.main.Main;
import com.lab.order.Order;
import com.lab.product.Product;
import com.lab.utilities.OrderDataIO;
import com.lab.utilities.ProductDataIO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class OrderController {

    List<Order> orders;
    OrderDataIO orderDataIO;
    List<Product> products;
    ProductDataIO productDataIO;
    public static OrderController orderView = null;

    static Logger logger = Logger.getLogger(OrderController.class.getName());
    static Properties properties;
    static String log = "log4j.properties";

    public OrderController() throws FileNotFoundException, IOException {
        orders = new ArrayList<>();
        orderDataIO = new OrderDataIO();
        productDataIO = new ProductDataIO();
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

    public static OrderController getInstance() throws IOException {
        if (orderView == null) {
            orderView = new OrderController();
        }

        return orderView;
    }

    public List<Order> getOrders() throws IOException {
        return orderDataIO.readData();
    }

    public void addOrder(Order order) throws IOException {
        orders = orderDataIO.readData();
        orders.add(order);
        orderDataIO.writeData(orders);
    }

    public void deleteOrder(String userCode) throws IOException {
        orders = orderDataIO.readData();
        orders.forEach((o) -> {
            if (o.getUserCode().equalsIgnoreCase(userCode)) {
                orders.remove(o);
            }
        });
        orderDataIO.writeData(orders);
    }

    public void updateOrder(Order orderUpdate) throws IOException {
        orders = orderDataIO.readData();
        orders.forEach((o) -> {
            if (o.getUserCode().equalsIgnoreCase(orderUpdate.getUserCode())) {
                o.setCustomerName(orderUpdate.getCustomerName());
                o.setOrderDate(orderUpdate.getOrderDate());
                o.setStatus(orderUpdate.getStatus());
                o.setProducts(orderUpdate.getProducts());
            }
        });
        orderDataIO.writeData(orders);
    }

    public void updateOrderStatus(String userCode, String status) throws IOException {
        orders = orderDataIO.readData();
        orders.forEach((o) -> {
            if (o.getUserCode().equalsIgnoreCase(userCode)) {
                o.setStatus(status);
            }
        });
        orderDataIO.writeData(orders);
    }

    public Order getOrderbyUserCode(String UserCode) throws IOException {
        orders = orderDataIO.readData();
        for (Order order : orders) {
            if (order.getUserCode().equalsIgnoreCase(UserCode)) {
                return order;
            }
        }
        return null;
    }

    public Product getProductID(int id) {
        products = productDataIO.readData();
        if (products == null) {
            logger.debug("null");
            return null;
        }
        for (Product o : products) {
            if (o.getProductId() == id) {
                return o;
            }
        }
        return null;
    }
}
