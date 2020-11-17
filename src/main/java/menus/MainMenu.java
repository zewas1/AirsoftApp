package menus;

import LoginSystem.LoginSystem;
import LoginSystem.UserCreation;
import LoginSystem.Utilities.DataRefresh;

import java.sql.SQLException;

public class MainMenu {
    public static String url = "jdbc:mysql://194.5.157.29:3306/airsoftapp?useSSL=false";
    public static String username = "appuser"; //  username
    public static String password = "K5991FXi"; // password
    public static int selection = 0;

    public static void getMainMenu() throws SQLException {
        int doCreateUser = 1;
        int doConnectUser = 2;
        //int showTopFive = 3;
        int exitMenu = 3;

        do {
            LoginSystem.getSelection();
            if (selection == doCreateUser) {
                UserCreation.userCreation(url, username, password);
            } else if (selection == doConnectUser) {
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
            } /*else if (selection == showTopFive){
                TopFiveMenu.topFivePlayers();
            }*/
        } while (selection != exitMenu);
    }
}