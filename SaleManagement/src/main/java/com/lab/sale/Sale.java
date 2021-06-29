package com.lab.sale;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.lab.role.UserRole;
import com.lab.user.User;
import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class Sale extends User implements Serializable{
    public Sale() {
    }

    public Sale(String userName, String password, UserRole userRole) {
        super(userName, password, userRole);
    }

    public Sale(String userCode, String userName, String password, UserRole userRole) {
        super(userCode, userName, password, userRole);
    }
    
    
}
