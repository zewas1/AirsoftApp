import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class mainClass {
    public static final Scanner scan = new Scanner(System.in);
    public static int selection = 0;
    public static boolean passwordFit = false;
    public static List<User> userList = new ArrayList<>();
    public static String createUsername;
    public static String createPassword;
    public static boolean loginSuccessful = false;

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/airsoftapp?useSSL=false";
        String username = "root"; //  username
        String password = "z_1755a1B!2c,/3Jk"; // password
        String tryUsername;
        String tryPassword;

        do {
            getSelection();
            if (selection == 1){
                userCreation(url, username, password);
            }
            else if (selection == 2){
                getUsersFromDb(url, username, password);
                loginCheck();
            }

        } while (selection!=3);
    }

    private static void loginCheck() {
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

    private static void getUsersFromDb(String url, String username, String password) throws SQLException {
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

    private static void userCreation(String url, String username, String password) throws SQLException {

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

    private static void getSelection() {
        // think about ways to transfer console commands into UI
        System.out.println("Welcome to the airsoft data management tool.");
        System.out.println("1. Register a new user");
        System.out.println("2. Login with an already existing user");
        System.out.println("3. Exit");
        selection = Integer.parseInt(scan.next());
    }
}