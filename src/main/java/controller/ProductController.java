/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.lab.main.Main;
import com.lab.product.Product;
import com.lab.utilities.ProductDataIO;
import com.lab.utilities.Validate;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Admin
 */
public class ProductController {

    ArrayList<Product> products;
    ProductDataIO productDataIO;
    public static ProductController productView = null;
    static Logger logger = Logger.getLogger(ProductController.class.getName());
    static Properties properties;
    static String log = "log4j.properties";

    public ProductController() throws FileNotFoundException, IOException {
        products = new ArrayList<>();
        productDataIO = new ProductDataIO();
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(log)) {
            try {
                properties.load(fis);
            } catch (FileNotFoundException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        PropertyConfigurator.configure(properties);
    }

    public static ProductController getInstance() throws IOException {
        if (productView == null) {
            productView = new ProductController();
        }
        return productView;
    }

    public ArrayList<Product> getProducts() {
        return productDataIO.readData();
    }

    public void addProduct(Product product) {
        products = productDataIO.readData();
        if (products == null) {
            products = new ArrayList<>();
            products.add(product);
            productDataIO.writeData(products);
        } else {
            products.add(product);
            productDataIO.writeData(products);
        }
    }

    public String deleteProduct(int id) {
        products = productDataIO.readData();
        int checkIdExist = Validate.checkIdExist(products, id);
        if (checkIdExist != -1) {
            products.remove(checkIdExist);

            for (int i = id - 1; i < products.size(); i++) {
                products.get(i).setProductId(i+1);
            }
            productDataIO.writeData(products);
            return "Delete Successful !!!";
        }
        return "Delete Fail !!!";

    }

    public String updateProduct(Product productUpd) {
        products = productDataIO.readData();
        for(Product u:products) {
            if (u.getProductId() == productUpd.getProductId()) {
                u.setName(productUpd.getName());
                u.setPrice(productUpd.getPrice());
                u.setQuantity(productUpd.getQuantity());
                u.setOrigin(productUpd.getOrigin());
            }else
                return "Update Fail !!!";
        }
        productDataIO.writeData(products);
        return "Update Successful !!!";
    }
}
