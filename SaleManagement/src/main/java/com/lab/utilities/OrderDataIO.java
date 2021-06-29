/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lab.utilities;

import com.lab.main.Main;
import com.lab.order.Order;
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

/**
 *
 * @author Admin
 */
public class OrderDataIO {

    static Logger logger = Logger.getLogger(OrderDataIO.class.getName());
    static Properties properties;
    static String log = "log4j.properties";

    public OrderDataIO() throws FileNotFoundException, IOException {
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

    public ArrayList<Order> readData() throws IOException {
        ArrayList<Order> orders = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Order.dat"))) {
            orders = (ArrayList<Order>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.debug(e.getMessage());
        }
        return orders;
    }

    public void writeData(List<Order> orders) throws IOException {

        try (FileOutputStream fos = new FileOutputStream("Order.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(orders);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            logger.debug(ioe.getMessage());
        } finally {

        }
    }

}
