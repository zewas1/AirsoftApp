import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class loginSystem {
    public static final Scanner scan = new Scanner(System.in);
    public static int selection = 0;
    public static boolean passwordFit = false;
    public static List<User> userList = new ArrayList<>();
    public static String createUsername;
    public static String createPassword;
    public static boolean loginSuccessful = false;

    String tryUsername;
    String tryPassword;

    public static void loginCheck() {
        String tryUsername;
        String tryPassword;
        System.out.println("Please type in your username:");
        tryUsername = scan.next();
        System.out.println("Please type in your password:");
        tryPassword = scan.next();

        for (User user : userList){

            if (user.getUserLogin().equalsIgnoreCase(tryUsername)){
                if (user.getUserPassword().equals(tryPassword)){
                    loginSuccessful = true;
                }
            }
        }
        if (loginSuccessful){
            System.out.println("Login successful");
        } else {
            System.out.println("Username or password is not correct.");
        }
    }

    public static void getUsersFromDb(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url,username,password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
        while (resultSet.next()){
            User user = new User();
            user.setUserId(resultSet.getInt("userId"));
            user.setUserLogin(resultSet.getString("userLogin"));
            user.setUserPassword(resultSet.getString("userPassword"));
            user.setIsAdmin(resultSet.getInt("isAdmin"));
            userList.add(user);
        }
        resultSet.close();
        statement.close();
        connection.close();
    }

    public static void userCreation(String url, String username, String password) throws SQLException {

        Connection connection = DriverManager.getConnection(url,username,password);
        Statement statement = connection.createStatement();
        usernameCreation();
        passwordCreation();
        String sqlString = "INSERT INTO users (userLogin,userPassword) VALUES('" + createUsername + "','" +
                createPassword + "')";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
    }

    private static void usernameCreation() {
        // establish username uniqueness
        System.out.println("Create your username");
        createUsername = scan.next();
    }

    private static void passwordCreation() {
        // create password hashing (at some point lol...)
        do {
            System.out.println("Create a password");
            createPassword = scan.next();
            if (passwordCheck.isValid(createPassword)) {
                System.out.println("User created successfully!");
                passwordFit = true;
            } else {
                System.out.println("Password is not valid, must be at least 8 symbols, contain numbers," +
                        "lower and capital letters, please try to create a new password again.");
            }
        } while (!passwordFit);
    }

    public static void getSelection() {
        // think about ways to transfer console commands into UI
        System.out.println("Welcome to the airsoft data management tool.");
        System.out.println("1. Register a new user");
        System.out.println("2. Login with an already existing user");
        System.out.println("3. Exit");
        mainClass.selection = Integer.parseInt(scan.next());
    }


}
