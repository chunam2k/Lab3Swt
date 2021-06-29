package Utilities;

import Product.Product;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import Utilities.ProductDataIO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDataIOTest {
    
    public ProductDataIOTest() {
        
    }

    @org.junit.Test
    public void testReadData1() throws FileNotFoundException, IOException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\SaleManagement\\Product.dat"));
        try {
            ArrayList<Product> products = (ArrayList<Product>) in.readObject();
            int count = products.size();
            products.add(new Product(1, "Cake", 23, 23, "Ha Noi"));
            count++;
            products.add(new Product(1, "Cake", 32, 32, "Hoi An"));
            count++;
            products.add(new Product(2, "Biscuist", 2, 2, "Hoa Lac"));
            count++;
            
            assertEquals(products.size(), count);
            // if add method perform fail
            
            assertFalse(products.add(new Product(1, "Cake", 32, 32, "Hoi An")));
            assertFalse(products.add(new Product(1, "Cake", 56, 76, "Hoi An")));
            
            //if add null obj
             
            assertFalse(products.add(null));
        } catch (ClassNotFoundException ex) {
            System.out.println("Loi: " + ex.getMessage());
        }
    }
    
    
}
