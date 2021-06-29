/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lab.user;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author CHU NAM
 */
public class UserViewTest {

    static UserView userView = null;
    static String newPassword;
    static String oldPass;
    private String us;
    private String p;

    public UserViewTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        userView = new UserView();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setUser method, of class UserView.
     */
    @Test
    public void testSetUser() {
//        System.out.println("setUser");
//        User user = null;
//        UserView instance = new UserView();
//        instance.setUser(user);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getInstance method, of class UserView.
     */
    @Test
    public void testGetInstance() {
//        System.out.println("getInstance");

    }

    /**
     * Test of login method, of class UserView.
     */
    @Test
    public void testLogin() {
        // test case 1 mat khau va username dung
        System.out.println("login");
        us = "admin01";
        p = "01admin";
        boolean result = userView.loginTest(us, p);
        assertEquals(true, result);
        
        // test case 2 mat khau sai
//        us = "admin01";
//        p = "01admin11";
//        boolean result = userView.loginTest(us, p);
//        assertEquals(false, result);
        
        //test case 3 username sai
//        us = "admin0111";
//        p = "01admin";
//        boolean result = userView.loginTest(us, p);
//        assertEquals(false, result);
        
        //test case 4
        us = "";
        p ="";
        String result1 = userView.checkLogin(us, p);
        assertEquals("Input is not empty", result1);
       
    }

    /**
     * Test of logerr method, of class UserView.
     */
    @Test
    public void testLogerr() {

        /**
         * Test of changePassword method, of class UserView.
         */
    }

    @Test
    public void testChangePassword() throws IOException {

        // test case 5
        System.out.println("changePassword");              
        newPassword = "01admin";        
        String result = userView.changePass(newPassword);
        assertEquals("New password must not be the same as the old password",result);
        
        //test case 6
        oldPass = "";
        result = userView.getCheck(oldPass);
        assertEquals("Input is not empty",result);
        
        //test case 7
        oldPass = "aaqfw";
        result = userView.checkChage(oldPass);
        assertEquals("Wrong password!!", result);
        
        //test case 8
        int choice = -1;
        int min = 0,max = 1;
        result = userView.checkOption(choice, min, max);
        assertEquals("Valid input are in the range of[" + min + ", " + max + "]", result);
    }

    /**
     * Test of getLoggedInUser method, of class UserView.
     */
    @Test
    public void testGetLoggedInUser() throws IOException {
//        System.out.println("getLoggedInUser");

    }

}
