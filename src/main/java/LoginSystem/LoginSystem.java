package LoginSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import LoginSystem.Utilities.DataRefresh;
import LoginSystem.Objects.User;

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
            validateLoginInput(tryUsername, tryPassword, user);
        }
        if (loginSuccessful) {
            System.out.println("Login successful");
        } else {
            System.out.println("Username or password is not correct.");
        }
    }

    private static void validateLoginInput(String tryUsername, String tryPassword, User user) {
        if (user.getUserLogin().equalsIgnoreCase(tryUsername)) {
            if (PasswordHashing.checkPassword(tryPassword, user.getUserPassword())) {
                loginSuccessful = true;
                currentUser = user.getUserLogin();
                userIsAdmin = user.getIsAdmin();
                DataRefresh.currentUserId = user.getUserId();
            }
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
                user.setUserLevel(resultSet.getInt("userLevel"));
                userList.add(user);
            }
            resultSet.close();
            statement.close();
        }
        connection.close();
    }
}