/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lab.product;

import com.lab.main.Main;
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
            } finally {
                fis.close();
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

    public void deleteProduct(int id) {
        products = productDataIO.readData();
        int checkIdExist = Validate.checkIdExist(products, id);
        if (checkIdExist != -1) {
            products.remove(checkIdExist);
            logger.debug("Delete successful !!!");
//            for (int i = id - 1; i < products.size(); i++) {
//                products.get(i).setProductId(products.get(i).getProductId() - 1);
//            }
        } else {
            logger.debug("Delete fail !!!");
        }
        productDataIO.writeData(products);
    }

    public void updateProduct(Product productUpd) {
        products = productDataIO.readData();
        products.forEach((u) -> {
            if (u.getProductId() == productUpd.getProductId()) {
                u.setName(productUpd.getName());
                u.setPrice(productUpd.getPrice());
                u.setQuantity(productUpd.getQuantity());
                u.setOrigin(productUpd.getOrigin());
            }
        });
        productDataIO.writeData(products);
    }
}
