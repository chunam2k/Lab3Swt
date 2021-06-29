/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lab.utilities;

import com.lab.main.Main;
import com.lab.user.User;
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
public class UserDataIO {

    static Logger logger = Logger.getLogger(UserDataIO.class.getName());
    static Properties properties;
    static String log = "log4j.properties";

    public UserDataIO() throws FileNotFoundException, IOException {
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

    public ArrayList<User> readData() {
        ArrayList<User> users = null;
        try {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("users.dat"))) {
                users = (ArrayList<User>) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            users = new ArrayList<>();
            logger.debug(e.getMessage());
        } finally {

        }

        return users;
    }

    public void writeData(List<User> users) {
        try {
            try (FileOutputStream fos = new FileOutputStream("users.dat"); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(users);
            }
        } catch (IOException ioe) {
            logger.debug(ioe.getMessage());
        } finally {

        }
    }

}
