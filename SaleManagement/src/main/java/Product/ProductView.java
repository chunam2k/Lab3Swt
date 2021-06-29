/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Product;

import Utilities.ProductDataIO;
import Utilities.Validate;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ProductView {

    ArrayList<Product> products;
    ProductDataIO productDataIO;
    public static ProductView productView = null;

    public ProductView() {
        products = new ArrayList<>();
        productDataIO = new ProductDataIO();
    }

    public static ProductView getInstance() {
        if (productView == null) {
            productView = new ProductView();
        }
        return productView;
    }

    public ArrayList<Product> getProducts() {
        return productDataIO.readData();
    }

    public void addProduct(Product product) {
        products = productDataIO.readData();
        if (products == null) {
            products = new ArrayList<Product>();
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
            System.err.println("Delete successful !!!");
//            for (int i = id - 1; i < products.size(); i++) {
//                products.get(i).setProductId(products.get(i).getProductId() - 1);
//            }
        } else {
            System.err.println("Delete fail !!!");
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
