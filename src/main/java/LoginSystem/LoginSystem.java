package LoginSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import menus.MainMenu;

public class LoginSystem {
    public static final Scanner scan = new Scanner(System.in);
    private static boolean passwordFit = false;
    private static boolean usernameFit = false;
    public static List<User> userList = new ArrayList<>();
    private static String createUsername;
    private static String createPassword;
    public static boolean loginSuccessful = false;
    public static int userIsAdmin;
    public static String currentUser = null;
    public static int currentKills = 0;
    public static int currentDeaths = 0;
    public static int currentAssists = 0;
    public static List<User> userListComparison = new ArrayList<>();
    public static int selectedUserKills = 0;
    public static int selectedUserDearths = 0;
    public static int selectedUserAssists = 0;

    public static void loginCheck() {
        String tryUsername;
        String tryPassword;
        System.out.println("Please type in your username:");
        tryUsername = scan.next();
        System.out.println("Please type in your password:");
        tryPassword = scan.next();

        for (User user : userList) {
            if (user.getUserLogin().equalsIgnoreCase(tryUsername)) {
                if (user.getUserPassword().equals(tryPassword)) {
                    loginSuccessful = true;
                    currentUser = user.getUserLogin();
                    userIsAdmin = user.getIsAdmin();
                    currentKills = user.getKillCount();
                    currentAssists = user.getAssistCount();
                    currentDeaths = user.getDeathCount();
                }
            }
        }
        if (loginSuccessful) {
            System.out.println("Login successful");
        } else {
            System.out.println("Username or password is not correct.");
        }
    }

    public static void dataRefresh() throws SQLException {
        userList.clear();
        userListComparison.clear();
        getUsersFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        for (User user : userList) {
            selectedUserKills = user.getKillCount();
            selectedUserDearths = user.getDeathCount();
            selectedUserAssists = user.getAssistCount();
        }
    }

    public static void getUsersFromDb(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        if (userList.size() > userListComparison.size() || userList.isEmpty()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUserLogin(resultSet.getString("userLogin"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setIsAdmin(resultSet.getInt("isAdmin"));
                user.setKillCount(resultSet.getInt("killCount"));
                user.setDeathCount(resultSet.getInt("deathCount"));
                user.setAssistCount(resultSet.getInt("assistCount"));
                userList.add(user);
            }
            resultSet.close();
            statement.close();
        }
        connection.close();
    }

    public static void userCreation(String url, String username, String password) throws SQLException {

        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        usernameCreation();
        passwordCreation();
        String sqlString = "INSERT INTO users (userLogin,userPassword) VALUES('" + createUsername + "','" +
                createPassword + "')";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
    }

    private static void usernameCreation() throws SQLException {
        getUsersFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        if (userList.isEmpty()) {
            usernameInput();
            usernameFit = true;
        } else {
            do {
                usernameInput();
                for (User user : userList) {
                    if (user.getUserLogin().equalsIgnoreCase(createUsername)) {
                        System.out.println("This username already exists!");
                        usernameFit = false;
                        break;
                    } else {
                        usernameFit = true;
                    }
                }
            } while (!usernameFit);
        }
        userList.clear();
        userListComparison.clear();
    }

    private static void usernameInput() {
        System.out.println("Create your username");
        createUsername = scan.next();
    }

    private static void passwordCreation() {
        // create password hashing (optional)
        do {
            System.out.println("Create a password");
            createPassword = scan.next();
            if (PasswordCheck.isValid(createPassword)) {
                System.out.println("User created successfully!");
                passwordFit = true;
            } else {
                System.out.println("Password is not valid, must be at least 8 units, contain numbers," +
                        "lower, capital letters and symbols, please create a new password.");
            }
        } while (!passwordFit);
    }

    public static void getSelection() {
        // think about ways to transfer console commands into UI
        System.out.println("Welcome to the airsoft data management tool.");
        System.out.println("1. Register a new user");
        System.out.println("2. Login with an already existing user");
        //System.out.println("3. Show top 5 players");
        System.out.println("3. Exit");
        MainMenu.selection = Integer.parseInt(scan.next());
    }
}