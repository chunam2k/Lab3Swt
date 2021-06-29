package Admin;

import User.User;
import User.UserController;
import User.UserView;
import Utilities.Validate;
import java.io.IOException;
import java.util.ArrayList;

public class AdminController {

    public ArrayList<User> users = null;
    private Validate validate;

    public AdminController() {
        validate = new Validate();
        users = new ArrayList<User>();
    }

    private static void printAdminOptionMenu() {
        System.out.println("--------------------------------");
        System.out.println("ADMIN PANEL");
        System.out.println("1. View");
        System.out.println("2. Add");
        System.out.println("3. Update");
        System.out.println("4. Delete User");
        System.out.println("5. Change User's Password");
        System.out.println("6. Exit to sub-menu");
        System.out.println("--------------------------------");
    }

    public void viewMenuAdmin() throws IOException {
        while (true) {
            printAdminOptionMenu();
            users = UserView.getInstance().getUsers();
            int choice = validate.getINT_LIMIT("Your choice: ", 1, 6);
            switch (choice) {
                case 1: {
                    UserView.getInstance().printUser(users);
                    break;
                }
                case 2: {
                    UserView.getInstance().addUser(UserView.getInstance().getNewUser("\n--* NEW USER PANAL: *--"));
                    break;
                }
                case 3: {
                    UserView.getInstance().updateUser(UserView.getInstance().getNewUser("Update user: "));
                    break;
                }
                case 4: {
                    String userCode = validate.getString("Enter user code to delete: ");
                    UserView.getInstance().deleteUser(userCode);
                    break;
                }
                case 5: {
                    UserView.getInstance().printUser(users);
                    User user = UserView.getInstance().getUser(validate.getString("Enter code of user want to change password: "));
                    UserController uc = UserController.getInstance();
                    uc.setUser(user);
                    uc.changePassword();
                    break;
                }
                case 6: {
                    return;
                }
            }
        }
    }

}
