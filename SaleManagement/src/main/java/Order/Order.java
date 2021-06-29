/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order;

import Product.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Order implements Serializable {

    String customerName;
    Date orderDate;
    String userCode;
    String status;
    ArrayList<Product> products;
    double amount;
    
    public Order(String customerName, Date orderDate, String userCode, String status, ArrayList<Product> products, double amount) {
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.userCode = userCode;
        this.status = status;
        this.products = products;
        this.amount = amount;
    }

    public Order(String customerName, Date orderDate, String userCode, String status, ArrayList<Product> products) {
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.userCode = userCode;
        this.status = status;
        this.products = products;
    }

    public Order() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public double getAmount() {
        amount = 0;
        products.forEach((o) -> {
            amount += o.getPrice() * o.getQuantity();
        });
        return amount;
    }

}
