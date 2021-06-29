package com.lab.product;

import com.lab.main.Main;
import com.lab.utilities.ProductDataIO;
import com.lab.utilities.Validate;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Admin
 */
public class ProductView {

    private ProductView productController = null;
    private Product product;
    private final ProductDataIO productDataIO;
    private final Validate validate;
    private final ProductController productView;
    private ArrayList<Product> list_product;
    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ProductView.class.getName());
    static Properties properties;
    static String log = "log4j.properties";

    public ProductView() throws FileNotFoundException, IOException {
        productDataIO = new ProductDataIO();
        validate = new Validate();
        productView = new ProductController();
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

    public ProductView getInstance() throws IOException {
        if (productController == null) {
            productController = new ProductView();
        }
        return productController;
    }

    public void menu() {
        logger.debug("--------------------------------");
        logger.debug("Manage Product");
        logger.debug("1. View");
        logger.debug("2. Add");
        logger.debug("3. Update");
        logger.debug("4. Delete");
        logger.debug("5. Back");
        logger.debug("--------------------------------");
    }

    public void displayProduct(ArrayList<Product> list) {
        logger.debug("----------------------------------------------------------------------------");
        if (list == null) {
            logger.debug("==> LIST IS EMPTY");
            return;
        } else {
            System.err.format("|%10s|%20s|%10s|%10s|%20s|\n", "id", "name", "price", "quantity", "origin");
            list.forEach((p) -> {
                System.err.format("|%10s|%20s|%10.2f|%10d|%20s|\n", p.getProductId(), p.getName(), p.getPrice(), p.getQuantity(), p.getOrigin());
            });
        }
        logger.debug("----------------------------------------------------------------------------");
    }

    public void add() {
        try {
            String name = (new Validate()).getString("Name: ");
            Double price = (new Validate()).getDOUBLE("Price: ");
            int quantity = (new Validate()).getINT("Quantity: ");
            String origin = (new Validate()).getString("Origin: ");
            Integer size;
            try {
                size = productView.getProducts().size();
                logger.debug("[STATUS SIZE : ]" + size);
                logger.debug("[ SIZE ] : " + size);
            } catch (Exception e) {
                size = 0;
                logger.debug("EX SIZE : " + size);
            }
            size = size + 1;
            product = new Product(size, name, price, quantity, origin);
            logger.debug("[ AFTER ADD ] " + size);
            productView.addProduct(product);
            logger.debug("Successful!!!\n");
        } catch (IOException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete() {
        try {
            int id = (new Validate()).getINT("id: ");
            productView.deleteProduct(id);
        } catch (IOException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update() {
        try {
            int id = (new Validate()).getINT("id: ");
            String name = (new Validate()).getString("Name: ");
            Double price = (new Validate()).getDOUBLE("Price: ");
            int quantity = (new Validate()).getINT("Quantity: ");
            String origin = (new Validate()).getString("Origin: ");
            productView.updateProduct(new Product(id, name, price, quantity, origin));
            logger.debug("Successful!!!\n");
        } catch (IOException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viewProduct() {

        int choice = -1;
        while (true) {
            try {
                menu();
                choice = validate.getINT_LIMIT("Your choice: ", 1, 5);
                switch (choice) {
                    case 1:
                        displayProduct(productView.getProducts());
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
                    default:
                        break;
                }
            } catch (IOException ex) {
            }
        }
    }

    public void viewGroupProduct() {
        list_product = productDataIO.readData();
        HashMap<String, Integer> map = new HashMap();

        list_product.forEach((i) -> {
            if (map.containsKey(i.getName())) {
                int count = map.get(i.getName());
                map.put(i.getName(), ++count);
            } else {
                map.put(i.getName(), 1);
            }
        });

        logger.debug("Product group by the product category : ");
        Set<String> set = map.keySet();
        set.forEach((key) -> {
            logger.debug(key + "\t\t" + map.get(key));
        });
    }

}
