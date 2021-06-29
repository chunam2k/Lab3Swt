package com.lab.admin;

import com.lab.main.Main;
import com.lab.user.User;
import com.lab.user.UserView;
import controller.UserController;
import com.lab.utilities.Validate;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class AdminView {

    static String log = "log4j.properties";

    static Logger logger = Logger.getLogger(AdminView.class.getName());
    private Properties properties;

    private List<User> users;
    private final Validate validate;

    public AdminView() throws IOException {
        this.users = null;
        validate = new Validate();
        users = new ArrayList<>();
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(log)) {
            try {
                properties.load(fis);
            } catch (FileNotFoundException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        PropertyConfigurator.configure(properties);
    }

    private static void printAdminOptionMenu() {
        logger.debug("--------------------------------");
        logger.debug("ADMIN PANEL");
        logger.debug("1. View");
        logger.debug("2. Add");
        logger.debug("3. Update");
        logger.debug("4. Delete User");
        logger.debug("5. Change User's Password");
        logger.debug("6. Exit to sub-menu");
        logger.debug("--------------------------------");
    }

    public void viewMenuAdmin() throws IOException {
        while (true) {
            printAdminOptionMenu();
            users = UserController.getInstance().getUsers();
            int choice = validate.getINT_LIMIT("Your choice: ", 1, 6);
            switch (choice) {
                case 1:
                    UserController.getInstance().printUser(users);
                    break;

                case 2:
                    UserController.getInstance().addUser(UserController.getInstance().getNewUser("\n--* NEW USER PANAL: *--"));
                    break;

                case 3:
                    UserController.getInstance().updateUser(UserController.getInstance().getNewUser("Update user: "));
                    break;

                case 4:
                    String userCode = validate.getString("Enter user code to delete: ");
                    UserController.getInstance().deleteUser(userCode);
                    break;

                case 5:
                    UserController.getInstance().printUser(users);
                    User user = UserController.getInstance().getUser(validate.getString("Enter code of user want to change password: "));
                    UserView uc = UserView.getInstance();
                    uc.setUser(user);
                    uc.changePassword();
                    break;

                case 6:
                    return;
                default:
                    break;
            }
        }
    }

}
