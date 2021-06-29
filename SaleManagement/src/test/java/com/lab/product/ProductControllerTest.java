/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Product;

import Utilities.ProductDataIO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductControllerTest {
    
    public ProductControllerTest() {
    }

    @Test
    public void testGroupProduct() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\SaleManagement\\Product.dat"));
        ArrayList<Product> products = (ArrayList<Product>) in.readObject();
        
        String str = "Cake 2\n" +
                "Sweet Cake 2\n" +
                "Biscuist 2";
        
        assertTrue (str.equals(new ProductDataIO().readData1()));
    }
}
