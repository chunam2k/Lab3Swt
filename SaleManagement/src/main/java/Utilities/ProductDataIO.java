package Utilities;

import Product.Product;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductDataIO {

    public ProductDataIO() {
    }

    public ArrayList<Product> readData() {
        ArrayList<Product> products = null;
        try {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Product.dat"))) {
                products = (ArrayList<Product>) in.readObject();
                return products;
            }
        } catch (IOException | ClassNotFoundException e) {

        }
        return null;
    }

    public void writeData(List<Product> products) {
        try {
            FileOutputStream fos = new FileOutputStream("Product.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(products);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            System.out.println("[ IOException ioe ] : " + ioe);
        }
    }

}
