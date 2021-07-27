package menus;

import LoginSystem.LoginSystem;
import LoginSystem.Objects.User;
import LoginSystem.UserCreation;
import LoginSystem.Utilities.DataRefresh;
import Views.Menus.MainMenuViews;

import java.sql.SQLException;

public class MainMenu {

    public static final String url = "jdbc:mysql://127.0.0.1:3306/airsoftapp?useSSL=false";
    public static final String username = "appuser"; //  system username
    public static final String password = "K5991FXi"; // system password.

    public static int selection = 0;

    private static final int doCreateUser = 1;
    private static final int doConnectUser = 2;
    private static final int exitMenu = 3;

    /**
     * @throws SQLException
     */
    public static void getMainMenu() throws SQLException {

        do {
            MainMenuViews.getSelection();
            switch (selection) {
                case doCreateUser:
                    UserCreation.userCreation(url, username, password);
                    break;
                case doConnectUser:
                    checkUserConnection();
                    if (LoginSystem.loginSuccessful) {
                        DataRefresh.statRefresh();
                        validateUserType();
                    } else {
                        System.out.println("Login was unsuccessful.");
                    }
                    break;
                case exitMenu:
                    break;
                default:
                    System.out.println("No such menu option.");
                    MainMenuViews.getSelection();
                    break;
            }
        } while (selection != exitMenu);
    }

    /**
     * @throws SQLException
     */
    private static void checkUserConnection() throws SQLException {
        LoginSystem.getUsersFromDb(url, username, password);
        LoginSystem.userListComparison = LoginSystem.userList;
        LoginSystem.loginCheck();
    }

    /**
     * @throws SQLException
     */
    private static void validateUserType() throws SQLException {
        if (LoginSystem.userIsAdmin == User.adminUserType) {
            AdminMenu.getAdminMenu();
        } else if (LoginSystem.userIsAdmin == User.regularUserType) {
            UserMenu.getUserMenu();
        }
    }
}