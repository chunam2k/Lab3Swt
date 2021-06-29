package com.lab.utilities;

import com.lab.main.Main;
import com.lab.product.Product;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ProductDataIO {

    static Logger logger = Logger.getLogger(ProductDataIO.class.getName());
    static Properties properties;
    static String log = "log4j.properties";

    public ProductDataIO() throws FileNotFoundException, IOException {
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

    public ArrayList<Product> readData() {
        ArrayList<Product> products = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Product.dat"))) {;
            products = (ArrayList<Product>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            products = new ArrayList<>();
            logger.debug(e.getMessage());
        } finally {

        }
        return products;
    }

    public void writeData(List<Product> products) {
        try {
            try (FileOutputStream fos = new FileOutputStream("Product.dat"); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(products);
            }
        } catch (IOException ioe) {
            logger.debug("[ IOException ioe ] : " + ioe);
        } finally {

        }
    }

}
