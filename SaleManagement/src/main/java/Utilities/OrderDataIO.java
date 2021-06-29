/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Order.Order;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class OrderDataIO {

    public OrderDataIO() {
    }

    public ArrayList<Order> readData() {
        ArrayList<Order> orders = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("Order.dat"));
            orders = (ArrayList<Order>) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return orders;
    }

    public void writeData(List<Order> orders) {
        try {
            FileOutputStream fos = new FileOutputStream("Order.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(orders);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
}
