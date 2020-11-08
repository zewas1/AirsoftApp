package LoginSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import LoginSystem.Utilities.DataRefresh;
import LoginSystem.Objects.User;
import menus.MainMenu;
import menus.SpecialFeatures.EventMenu;

public class LoginSystem {
    public static final Scanner scan = new Scanner(System.in);
    public static List<User> userList = new ArrayList<>();
    public static List<User> userListComparison = new ArrayList<>();
    public static boolean loginSuccessful = false;
    public static int userIsAdmin;
    public static String currentUser = null;

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
                    DataRefresh.currentUserId = user.getUserId();
                }
            }
        }
        if (loginSuccessful) {
            System.out.println("Login successful");
        } else {
            System.out.println("Username or password is not correct.");
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


    public static void getSelection() {
        // think about ways to transfer console commands into UI
        System.out.println("Welcome to the airsoft data management tool.");
        System.out.println("1. Register a new user");
        System.out.println("2. Login with an already existing user");
        //System.out.println("3. Show top 5 players");
        System.out.println("3. Exit");
        try {
            MainMenu.selection = Integer.parseInt(scan.next());
        } catch (NumberFormatException e){
            System.out.println("Only numbers are allowed.");
        }
    }
}