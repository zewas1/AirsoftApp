package menus;

import LoginSystem.LoginSystem;
import LoginSystem.UserCreation;
import LoginSystem.Utilities.DataRefresh;

import java.sql.SQLException;

public class MainMenu {

    public static String url = "jdbc:mysql://127.0.0.1:3306/airsoftapp?useSSL=false";
    public static String username = "appuser"; //  system username
    public static String password = "K5991FXi"; // system password. FYI, this is a bad practice, think about ways to remedy this.
    public static int selection = 0;

    private static final int doCreateUser = 1;
    private static final int doConnectUser = 2;
    private static final int exitMenu = 3;

    public static void getMainMenu() throws SQLException {

        do {
            LoginSystem.getSelection();
            switch (selection) {
                case doCreateUser:
                    UserCreation.userCreation(url, username, password);
                    break;
                case doConnectUser:
                    LoginSystem.getUsersFromDb(url, username, password);
                    LoginSystem.userListComparison = LoginSystem.userList;
                    LoginSystem.loginCheck();
                    if (LoginSystem.loginSuccessful) {
                        DataRefresh.statRefresh();
                        if (LoginSystem.userIsAdmin > 0) {
                            AdminMenu.getAdminMenu();
                        } else if (LoginSystem.userIsAdmin == 0) {
                            UserMenu.getUserMenu();
                        }
                    } else {
                        System.out.println("Login was unsuccessful.");
                    }
                    break;
                case exitMenu:
                    break;
                default:
                    System.out.println("No such menu option.");
                    LoginSystem.getSelection();
                    break;
            }
        } while (selection != exitMenu);
    }
}