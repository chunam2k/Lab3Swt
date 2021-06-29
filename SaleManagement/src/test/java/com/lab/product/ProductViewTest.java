/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lab.product;

import com.lab.order.Order;
import com.lab.role.UserRole;
import com.lab.user.User;
import com.lab.utilities.OrderDataIO;
import com.lab.utilities.ProductDataIO;
import com.lab.utilities.UserDataIO;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Trung
 */
public class ProductViewTest {

    List<User> listUsers = null;
    List<Order> listOrders = null;
    List<Product> listProducts = null;
    List<Product> listProducts1 = null;
    List<Product> listProducts2 = null;

    UserDataIO u = null;
    ProductDataIO p = null;
    OrderDataIO o = null;

    public void initData() throws IOException {
       
    }

    public ProductViewTest() throws IOException {
        listUsers = new ArrayList<>();
        listOrders = new ArrayList<>();
        listProducts = new ArrayList<>();
        listProducts1 = new ArrayList<>();
        listProducts2 = new ArrayList<>();

        u = new UserDataIO();
        p = new ProductDataIO();
        o = new OrderDataIO();

        initData();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testViewGroupProduct() throws IOException {
        System.out.println("viewGroupProduct");
        ProductView instance = new ProductView();
        instance.viewGroupProduct();
        // TODO review the generated test code and remove the default call to fail.
    }

}
