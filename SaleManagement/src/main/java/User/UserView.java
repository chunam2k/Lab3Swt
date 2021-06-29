
package User;

import Common.UserRole;
import Utilities.UserDataIO;
import Utilities.Validate;
import java.io.IOException;
import java.util.ArrayList;

public class UserView {

    ArrayList<User> users;
    UserDataIO userDataIO;
    public static UserView userView = null;

    public UserView() {
        users = new ArrayList<>();
        userDataIO = new UserDataIO();
    }

    public static UserView getInstance() {
        if (userView == null) {
            userView = new UserView();
        }

        return userView;
    }

    public ArrayList<User> getUsers() {
        return userDataIO.readData();
    }

    public void addUser(User user) {
        users = userDataIO.readData();
        users.add(user);
        userDataIO.writeData(users);
    }

    public void deleteUser(String userCode) {
        users = userDataIO.readData();
        users.forEach((u) -> {
            if (u.getUserCode().equalsIgnoreCase(userCode)) {
                users.remove(u);
            }
        });
        userDataIO.writeData(users);
    }

    public void updateUser(User userUpdate) {
        users = userDataIO.readData();
        users.forEach((u) -> {
            if (u.getUserCode().equalsIgnoreCase(userUpdate.getUserCode())) {
                u.setUserName(userUpdate.getUserName());
                u.setPassword(userUpdate.getPassword());
            }
        });
        userDataIO.writeData(users);
    }

    public User getUser(String code) {
        User user = null;
        for (User u : users) {
            if (code.equals(u.getUserCode())) {
                user = u;
            }
        }
        return user;
    }

    public void printUser(ArrayList<User> user) {
        for (User u : user) {
            System.out.println(u.toString());
        }
    }

    public User getNewUser(String mess) throws IOException {
        System.out.println(mess);
        Validate validate = new Validate();
        String userName, userCode, password;
        UserRole userRole = null;
        userCode = validate.getString("\nInput new user code: ");
        userName = validate.getString("\nInput new user name: ");
        password = validate.getString("\nInput new user password: ");
        System.out.println("SELECT ROLE <1 - ADMIN : 2 - SALE>");
        System.out.println("1: ADMIN");
        System.out.println("2: SALE");
        int roleSelect = validate.getINT_LIMIT("Input new user role: ", 1, 3);

        switch (roleSelect) {
            case 1: {
                userRole = UserRole.ADMIN;
                break;
            }
            case 2: {
                userRole = UserRole.SALE;
                break;
            }
        }
        return new User(userCode, userName, password, userRole);
    }

}
