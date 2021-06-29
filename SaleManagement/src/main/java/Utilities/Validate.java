package Utilities;

import Product.Product;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public final class Validate {

    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static Pattern patternUsername = Pattern.compile("^[A-Za-z][A-Za-z0-9]{4,}$");
    static Pattern patternPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{6,}$");

    public String getPassword(String MSG) throws IOException {
        while (true) {
            System.out.print(MSG);
            String check = in.readLine().trim();
            if (patternPassword.matcher(check).find()) {
                return check;
            } else {
                System.out.println("Wrong format! (Password >=6 char, include both number and char, not include any other type of char)");
                System.err.println("Enter again: ");
            }
        }
    }

    public String getUsername(String MSG) throws IOException {
        while (true) {
            System.out.print(MSG);
            String check = in.readLine().trim();
            if (patternUsername.matcher(check).find()) {
                return check;
            } else {
                System.out.println("Wrong format! (Password >=5 char, starts with character)");
                System.err.println("Enter again: ");
            }
        }
    }

    public String getString(String MSG) throws IOException {
        while (true) {
            System.out.println(MSG);
            String check = in.readLine().trim();
            if (check.isEmpty()) {
                System.err.println("Input is not empty");
                continue;
            } else {
                return check;
            }
        }
    }

    public int getINT(String MSG) throws IOException {
        while (true) {
            try {
                System.out.println(MSG);
                int number = Integer.parseInt(in.readLine());
                return number;
            } catch (NumberFormatException e) {
                System.err.println("Enter \"int\" type [" + Integer.MIN_VALUE + ", " + Integer.MAX_VALUE + "]");
            }
        }
    }

    public int getINT_LIMIT(String MSG, int MIN, int MAX) throws IOException {
        while (true) {
            try {
                System.out.println(MSG);
                int number = Integer.parseInt(in.readLine());
                if (number < MIN || number > MAX) {
                    throw new NumberFormatException();
                }
                return number;
            } catch (NumberFormatException e) {
                System.err.println("Valid input are in the range of[" + MIN + ", " + MAX + "]. ");
            }
        }
    }

    public double getDOUBLE(String MSG) throws IOException {
        while (true) {
            try {
                System.out.println(MSG);
                double number = Double.parseDouble(in.readLine());
                return number;
            } catch (NumberFormatException e) {
                System.err.println("Enter \"double\" type [" + Double.MIN_VALUE + ", " + Double.MAX_VALUE + "]");
            }
        }
    }

    public float getFLOAT(String MSG) throws IOException {
        while (true) {
            try {
                System.out.println(MSG);
                float number = Float.parseFloat(in.readLine());
                return number;
            } catch (NumberFormatException e) {
                System.err.println("Enter \"float\" type [" + Float.MIN_VALUE + ", " + Float.MAX_VALUE + "]");
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
                System.err.println("That day was not found");
            }
        }
    }

    public String getOrderStatus(String MSG, int not) throws IOException {
        while (true) {
            try {
                System.out.println(MSG);
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
                    System.err.println("You must input ID greater than 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error :" + e.getMessage());
            }
        }
    }

    public static int checkIdExist(ArrayList<Product> list, int id) {
        for (Product product : list) {
            if (product.getProductId() == id) {
                return id;
            }
        }
        return -1;
    }

}
