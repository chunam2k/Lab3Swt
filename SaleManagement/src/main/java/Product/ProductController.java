package Product;

import Utilities.ProductDataIO;
import Utilities.Validate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ProductController {

    public static ProductController productController = null;
    private Product product;
    private ProductDataIO productDataIO;
    private final Validate validate;
    private ProductView productView;
    private ArrayList<Product> list_product;

    public ProductController() {
        productDataIO = new ProductDataIO();
        validate = new Validate();
        productView = new ProductView();
    }

    public static ProductController getInstance() {
        if (productController == null) {
            productController = new ProductController();
        }
        return productController;
    }

    public void menu() {
        System.out.println("--------------------------------");
        System.out.println("Manage Product");
        System.out.println("1. View");
        System.out.println("2. Add");
        System.out.println("3. Update");
        System.out.println("4. Delete");
        System.out.println("5. Back");
        System.out.println("--------------------------------");
    }

    public void displayProduct(ArrayList<Product> list) {
        System.out.println("----------------------------------------------------------------------------");
        if (list == null) {
            System.out.println("==> LIST IS EMPTY");
            return;
        } else {
            System.out.format("|%10s|%20s|%10s|%10s|%20s|\n", "id", "name", "price", "quantity", "origin");
            list.forEach((p) -> {
                System.out.format("|%10s|%20s|%10.2f|%10d|%20s|\n", p.getProductId(), p.getName(), p.getPrice(), p.getQuantity(), p.getOrigin());
            });
        }
        System.out.println("----------------------------------------------------------------------------");
    }

    public void add() {
        try {
            String name = (new Validate()).getString("Name: ");
            Double price = (new Validate()).getDOUBLE("Price: ");
            int quantity = (new Validate()).getINT("Quantity: ");
            String origin = (new Validate()).getString("Origin: ");
            Integer size;
            try {
                size = productView.getProducts().size();
                System.out.println("[ SIZE ] : "+size);
            } catch (Exception e) {
                size = 0;                
                System.out.println("EX SIZE : " +size);
            }
            size = size + 1;
            if (productView.getProducts() == null) {
                product = new Product(size, name, price, quantity, origin);
                System.out.println("[ AFTER NULL ] "+size);
                productView.addProduct(product);
                System.out.println("Successful!!!\n");
            } else {
                product = new Product(size, name, price, quantity, origin);
                System.out.println("[ AFTER ADD ] "+size);
                productView.addProduct(product);
                System.out.println("Successful!!!\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete() {
        try {
            int id = (new Validate()).getINT("id: ");
            productView.deleteProduct(id);
        } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update() {
        try {
            int id = (new Validate()).getINT("id: ");
            String name = (new Validate()).getString("Name: ");
            Double price = (new Validate()).getDOUBLE("Price: ");
            int quantity = (new Validate()).getINT("Quantity: ");
            String origin = (new Validate()).getString("Origin: ");
            productView.updateProduct(new Product(id, name, price, quantity, origin));
            System.out.println("Successful!!!\n");
        } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viewProduct() {

        int choice = -1;
        while (true) {
            try {
                menu();
                choice = validate.getINT_LIMIT("Your choice: ", 1, 5);
                switch (choice) {
                    case 1:
                        displayProduct(productView.getProducts());
                        break;
                    case 2:
                        add();
                        break;
                    case 3:
                        update();
                        break;
                    case 4:
                        delete();
                        break;
                    case 5:
                        return;
                }
            } catch (IOException ex) {
            }
        }
    }

    public void viewGroupProduct() {
        list_product = productDataIO.readData();
        HashMap<String, Integer> map = new HashMap();

        list_product.forEach((i) -> {
            if (map.containsKey(i.getName())) {
                int count = map.get(i.getName());
                map.put(i.getName(), ++count);
            } else {
                map.put(i.getName(), 1);
            }
        });

        System.out.println("Product group by the product category : ");
        Set<String> set = map.keySet();
        set.forEach((key) -> {
            System.out.println(key + "\t\t" + map.get(key));
        });
    }

}
