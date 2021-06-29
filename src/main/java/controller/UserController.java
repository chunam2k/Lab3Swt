package controller;

import com.lab.main.Main;
import com.lab.role.UserRole;
import com.lab.user.User;
import com.lab.utilities.UserDataIO;
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

public class UserController {

    ArrayList<User> users;
    UserDataIO userDataIO;
    public static UserController userView = null;
    static Logger logger = Logger.getLogger(UserController.class.getName());
    static Properties properties;
    static String log = "log4j.properties";

    public UserController() throws FileNotFoundException, IOException {
        users = new ArrayList<>();
        userDataIO = new UserDataIO();
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(log)) {
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

    public static UserController getInstance() throws IOException {
        if (userView == null) {
            userView = new UserController();
        }

        return userView;
    }

    public ArrayList<User> getUsers() {
        return userDataIO.readData();
    }

    public void addUser(User user) throws IOException {
        users = userDataIO.readData();
        if(checkCodeExist(user.getUserCode())){
            System.out.println("this code has exist");
            addUser(UserController.getInstance().getNewUser("\n--* NEW USER PANAL: *--"));
        }
        else{
            users.add(user);
        }
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

    public void updateUser(User userUpdate) throws IOException{
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

    public void printUser(List<User> user) {
        user.forEach((u) -> {
            logger.debug(u.toString());
        });
    }

    public User getNewUser(String mess) throws IOException {
        logger.debug(mess);
        Validate validate = new Validate();
        String userName, userCode, password;
        UserRole userRole = null;
        userCode = validate.getString("\nInput new user code: ");
        userName = validate.getString("\nInput new user name: ");
        password = validate.getString("\nInput new user password: ");
        logger.debug("SELECT ROLE <1 - ADMIN : 2 - SALE>");
        logger.debug("1: ADMIN");
        logger.debug("2: SALE");
        int roleSelect = validate.getINT_LIMIT("Input new user role: ", 1, 3);

        switch (roleSelect) {
            case 1:
                userRole = UserRole.ADMIN;
                break;

            case 2:
                userRole = UserRole.SALE;
                break;
            default:
                break;
        }
        return new User(userCode, userName, password, userRole);
    }
    
    private boolean checkCodeExist(String code){
        users = userDataIO.readData();
        for (User u : users) {
            if(code.equalsIgnoreCase(u.getUserCode())){
                return true;
            }
        }
        return false;
    }
}
