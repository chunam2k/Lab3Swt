package com.lab.user;

import controller.UserController;
import com.lab.main.Main;
import com.lab.role.UserRole;
import com.lab.utilities.UserDataIO;
import com.lab.utilities.Validate;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.PropertyConfigurator;

public class UserView {

    public static UserView userController = null;
    private User user;
    private final UserDataIO userDataIO;
    private final Validate validate;
    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserView.class.getName());
    static Properties properties;
    static String log = "log4j.properties";

    public UserView() throws FileNotFoundException, IOException {
        userDataIO = new UserDataIO();
        validate = new Validate();
        properties = new Properties();
        try ( FileInputStream fis = new FileInputStream(log)) {
            try {
                properties.load(fis);
            } catch (FileNotFoundException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                fis.close();
            }
        }
        PropertyConfigurator.configure(properties);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static UserView getInstance() throws IOException {
        if (userController == null) {
            userController = new UserView();
        }
        return userController;
    }

    //Return true if log in successfully
    //Return false if not
    
    public String checkLogin(String u,String p){
        if(u.isEmpty()||p.isEmpty()){
            return "Input is not empty";
        }
        return null;
    }
    
    public Boolean loginTest(String us, String p){
        try {
            //Doc file
            ArrayList<User> users = UserController.getInstance().getUsers();

            users.forEach((u) -> {
                if (u.getUserName().equals(us) && u.getPassword().equals(p)) {
                    user = new User();
                    user.setUserName(us);
                    user.setPassword(p);
                    user.setUserCode(u.getUserCode());
                    user.setUserRole(u.getUserRole());
                }
            });

            return (user != null);

        } catch (Exception ex) {
            Logger.getLogger(UserView.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    public Boolean login() {
        try {
            //Doc file
            ArrayList<User> users = UserController.getInstance().getUsers();
            logger.debug("---* MENU LOGIN *---");
            //Read userInput
            String userName;
            userName = validate.getString("\nInput username: ");
            String password;
            password = validate.getString("\nInput password: ");

            users.forEach((u) -> {
                if (u.getUserName().equals(userName) && u.getPassword().equals(password)) {
                    user = new User();
                    user.setUserName(userName);
                    user.setPassword(password);
                    user.setUserCode(u.getUserCode());
                    user.setUserRole(u.getUserRole());
                }
            });

            return (user != null);

        } catch (Exception ex) {
            Logger.getLogger(UserView.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public void logerr() {
        this.user = null;
    }

    public String changePass(String newPassword) throws IOException {
            User userTest = new User("admin01","01admin",UserRole.ADMIN);
            if(newPassword.equals(userTest.getPassword())){
                return "New password must not be the same as the old password";
            }           
    return null;
}
    
    
    public String getCheck(String oldPassword){
        User userTest = new User("admin01",oldPassword,UserRole.ADMIN);
        if(oldPassword.isEmpty()){
            return "Input is not empty";
        }
        return null;
    }
    public String checkChage(String oldPass){
        User userTest = new User("admin01","admin01",UserRole.ADMIN);
        if(userTest.getPassword().equals(oldPass)){
            return "Correct";
        }else{
            return "Wrong password!!";
        }
    }
    
    public String checkOption(int choice,int MIN,int MAX){
        if(choice < 0 || choice > 1){
            return "Valid input are in the range of[" + MIN + ", " + MAX + "]";
        }
        return null;
    }

public void changePassword() {

        while (true) {
            try {
                logger.debug("--------------------------------");
                logger.debug("CHANGE PASSWORD");
                logger.debug("1. Change Password");
                logger.debug("0. Cancel");
                logger.debug("--------------------------------");

                int choice = validate.getINT_LIMIT("Your choice: ", 0, 1);

                switch (choice) {
                    case 0:

                        return;

                    case 1:
                        if (user != null) {

                            String oldPassword = validate.getString("Enter old password: ");
                            if (user.getPassword().equals(oldPassword)) {

                                String newPassword = validate.getPassword("Enter new password: "); 
                                    if(newPassword.equals(oldPassword)){
                                    showMess("New password must not be the same as the old password");
                                    newPassword = validate.getPassword("Enter again: ");
                                }
                                String confirmNewPassword = validate.getPassword("Confirm new password: "); 

                                if (confirmNewPassword.equals(newPassword)) {
                                    user.setPassword(newPassword);
                                    UserController.getInstance().updateUser(user);

                                    showMess("Password changed successfully!!");
                                } else {
                                    showMess("Passwords don't match!!");
                                }

                            } else {
                                showMess("Wrong password!!");
                            }

                        }
                        break;
                    default:
                        break;

}

            } catch (IOException ex) {
                Logger.getLogger(UserView.class  

.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void printLoginMenu() {
        logger.debug("--------------------------------");
        logger.debug("Welcome to Sale Management Program");
        logger.debug("1. Login");
        logger.debug("0. Exit");
        logger.debug("--------------------------------");
    }

    public User getLoggedInUser() {
        return user;
    }

    private void showMess(String mess){
        System.out.println(mess);
    }
}
