package menus;

import LoginSystem.LoginSystem;
import LoginSystem.Objects.User;
import LoginSystem.UserCreation;
import LoginSystem.Utilities.DataRefresh;
import Views.Menus.MainMenuViews;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.SQLException;

public class MainMenu {
    private static Dotenv dotenv = Dotenv.load();
    public static final String url = dotenv.get("DB_HOSTNAME");
    public static final String username = dotenv.get("DB_USER");
    public static final String password = dotenv.get("DB_PASSWORD");

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