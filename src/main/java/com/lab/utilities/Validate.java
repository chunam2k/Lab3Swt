package com.lab.utilities;

import com.lab.main.Main;
import com.lab.product.Product;
import com.lab.role.UserRole;
import com.lab.user.User;
import controller.ProductController;
import static com.lab.utilities.ProductDataIO.properties;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public final class Validate {

    static Logger logger = Logger.getLogger(Validate.class.getName());
    static Properties properties;
    static String log = "log4j.properties";

    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static Pattern patternUsername = Pattern.compile("^[A-Za-z][A-Za-z0-9]{4,}$");
    static Pattern patternPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{6,}$");
    ProductController productView;

    public Validate() throws FileNotFoundException, IOException {
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

    public String getPassword(String MSG) throws IOException {
        logger.debug(MSG);
        while (true) {
            String check = in.readLine().trim();
            if (patternPassword.matcher(check).find()) {
                return check;
            } else {
                logger.debug("Wrong format! (Password >=6 char, include both number and char, not include any other type of char)");
                logger.debug("Enter again: ");
            }
        }
    }

    public String getUsername(String MSG) throws IOException {
        while (true) {
            logger.debug(MSG);
            String check = in.readLine().trim();
            if (patternUsername.matcher(check).find()) {
                return check;
            } else {
                logger.debug("Wrong format! (Password >=5 char, starts with character)");
                logger.debug("Enter again: ");
            }
        }
    }
    
    
    
    public String getString(String MSG) throws IOException {
        while (true) {
            logger.debug(MSG);
            String check = in.readLine().trim();
            if (check.isEmpty()) {
                logger.debug("Input is not empty");
                continue;
            } else {
                return check;
            }
        }
    }

    public int getINT(String MSG) throws IOException {
        while (true) {
            try {
                logger.debug(MSG);
                int number = Integer.parseInt(in.readLine());
                return number;
            } catch (NumberFormatException e) {
                logger.debug("Enter \"int\" type [" + Integer.MIN_VALUE + ", " + Integer.MAX_VALUE + "]");
            }
        }
    }

    public int getINT_LIMIT(String MSG, int MIN, int MAX) throws IOException {
        while (true) {
            try {
                logger.debug(MSG);
                int number = Integer.parseInt(in.readLine());
                if (number < MIN || number > MAX) {
                    throw new NumberFormatException();
                }
                return number;
            } catch (NumberFormatException e) {
                logger.debug("Valid input are in the range of[" + MIN + ", " + MAX + "]. ");
            }
        }
    }

    public double getDOUBLE(String MSG) throws IOException {
        while (true) {
            try {
                logger.debug(MSG);
                double number = Double.parseDouble(in.readLine());
                return number;
            } catch (NumberFormatException e) {
                logger.debug("Enter \"double\" type [" + Double.MIN_VALUE + ", " + Double.MAX_VALUE + "]");
            }
        }
    }

    public float getFLOAT(String MSG) throws IOException {
        while (true) {
            try {
                logger.debug(MSG);
                float number = Float.parseFloat(in.readLine());
                return number;
            } catch (NumberFormatException e) {
                logger.debug("Enter \"float\" type [" + Float.MIN_VALUE + ", " + Float.MAX_VALUE + "]");
            }
        }
    }

    public Date getDate(String MSG) throws IOException {
        Date now = new Date();
        while (true) {
            String check = getString(MSG);
            SimpleDateFormat fd = new SimpleDateFormat("dd/MM/yyyy");
            fd.setLenient(false);
            try {
                Date date = fd.parse(check);
                return date;
            } catch (ParseException e) {
                logger.debug("That day was not found");
            }
        }
    }

    public String getOrderStatus(String MSG, int not) throws IOException {
        while (true) {
            try {
                logger.debug(MSG);
                int choice = -1;
                choice = getINT_LIMIT("Status:\n1.Submitted\n2.Cancelled\n3.Completed", 1, 3);
                if (choice != not) {
                    switch (choice) {
                        case 1:
                            return "Submitted";
                        case 2:
                            return "Cancelled";
                        case 3:
                            return "Completed";
                        default:
                            break;
                    }
                }
            } catch (NumberFormatException e) {

            }

        }
    }

    public int getID() throws IOException {
        while (true) {
            try {
                int id = getINT("Enter your ID : ");
                if (id >= 0) {
                    return id;
                } else {
                    logger.debug("You must input ID greater than 0");
                }
            } catch (NumberFormatException e) {
                logger.debug("Error :" + e.getMessage());
            }
        }
    }

    public static int checkIdExist(ArrayList<Product> list, int id) {
        logger.debug("[ LIST STATUS ] : " + list);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProductId() == id) {
                logger.debug("[ ID ] : " + list.get(i).getProductId());
                return i;
            }
        }
        return -1;
    }

}
