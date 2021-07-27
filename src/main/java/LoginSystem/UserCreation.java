package LoginSystem;

import LoginSystem.Objects.User;
import menus.MainMenu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UserCreation {
    private static String createUsername;
    private static String createPassword;
    private static boolean passwordFit = false;
    private static boolean usernameFit = false;

    /**
     * @param url
     * @param username
     * @param password
     * @throws SQLException
     */
    public static void userCreation(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        usernameCreation();
        passwordCreation();
        if (passwordFit = true) {
            String sqlString = "INSERT INTO users (userLogin,userPassword,userLevel) VALUES('" + createUsername + "','" +
                    PasswordHashing.hashPassword(createPassword) + "','" + User.regularUserLevel +  "')";
            statement.executeUpdate(sqlString);
        }
        statement.close();
        connection.close();
    }

    /**
     * @throws SQLException
     */
    private static void usernameCreation() throws SQLException {
        LoginSystem.getUsersFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        if (LoginSystem.userList.isEmpty()) {
            usernameInput();
            usernameFit = true;
        } else {
            do {
                usernameInput();
                validateUserLogin();
            } while (!usernameFit);
        }
        LoginSystem.userList.clear();
        LoginSystem.userListComparison.clear();
    }

    private static void validateUserLogin() {
        for (User user : LoginSystem.userList) {
            if (user.getUserLogin().equalsIgnoreCase(createUsername)) {
                System.out.println("This username already exists!");
                usernameFit = false;
                break;
            } else {
                usernameFit = true;
            }
        }
    }

    private static void usernameInput() {
        System.out.println("Create your username");
        createUsername = LoginSystem.scan.next();
    }

    private static void passwordCreation() {
        do {
            System.out.println("Create a password");
            createPassword = LoginSystem.scan.next();
            if (PasswordCheck.isValid(createPassword)) {
                System.out.println("User created successfully!");
                passwordFit = true;
            } else {
                System.out.println("Password is not valid, must be at least 8 symbols, contain numbers," +
                        "lower, capital letters and symbols, please create a new password.");
            }
        } while (!passwordFit);
    }
}
