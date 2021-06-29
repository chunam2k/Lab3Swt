package com.lab.user;

import com.lab.role.UserRole;
import java.io.Serializable;

public class User implements Serializable {

    private String userCode; //not null, unique
    private String userName; //>=5 chars, must start with a letter
    private String password; //>=6 chars, include both letter & numbers, no other type of chars
    private UserRole userRole; //0 = ADMIN, 1 = DOCTOR

    public User() {
    }

    public User(String userName, String password, UserRole userRole) {
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
    }

    public User(String userCode, String userName, String password, UserRole userRole) {
        this.userCode = userCode;
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return userCode + " | " + userName + " | " + userRole.name();
    }

}
