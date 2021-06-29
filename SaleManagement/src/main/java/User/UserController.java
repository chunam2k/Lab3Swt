
package User;

import Utilities.UserDataIO;
import Utilities.Validate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserController {

    public static UserController userController = null;
    private User user;
    private UserDataIO userDataIO;
    private Validate validate;

    public UserController() {
        userDataIO = new UserDataIO();
        validate = new Validate();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static UserController getInstance() {
        if (userController == null) {
            userController = new UserController();
        }
        return userController;
    }

    //Return true if log in successfully
    //Return false if not
    public Boolean login() {
        try {
            //Doc file
            ArrayList<User> users = UserView.getInstance().getUsers();
            System.out.println("---* MENU LOGIN *---");
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

        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public void logout() {
        this.user = null;
    }

    public void changePassword() {

        while (true) {
            try {
                System.out.println("--------------------------------");
                System.out.println("CHANGE PASSWORD");
                System.out.println("1. Change Password");
                System.out.println("0. Cancel");
                System.out.println("--------------------------------");

                int choice = validate.getINT_LIMIT("Your choice: ", 0, 1);

                switch (choice) {
                    case 0:

                        return;

                    case 1:
                        if (user != null) {

                            String oldPassword = validate.getString("Enter old password: ");
                            if (user.getPassword().equals(oldPassword)) {

                                String newPassword = validate.getPassword("Enter new password: ");
                                String confirmNewPassword = validate.getPassword("Confirm new password: ");

                                if (confirmNewPassword.equals(newPassword)) {
                                    user.setPassword(newPassword);
                                    UserView.getInstance().updateUser(user);

                                    System.out.println("Password changed successfully!!");
                                } else {
                                    System.out.println("Passwords don't match!!");
                                }

                            } else {
                                System.out.println("Wrong password!!");
                            }

                        }
                        break;
                }

            } catch (IOException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void printLoginMenu() {
        System.out.println("--------------------------------");
        System.out.println("Welcome to Sale Management Program");
        System.out.println("1. Login");
        System.out.println("0. Exit");
        System.out.println("--------------------------------");
    }

    public User getLoggedInUser() {
        return user;
    }

}
