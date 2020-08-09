package menus;

import LoginSystem.LoginSystem;

import java.sql.SQLException;

public class MainMenu {
    public static String url = "jdbc:mysql://localhost:3306/airsoftapp?useSSL=false";
    public static String username = "root"; //  username
    public static String password = "z_1755a1B!2c,/3Jk"; // password
    public static int selection = 0;

    public static void getMainMenu() throws SQLException {
        do {
            LoginSystem.getSelection();
            if (selection == 1) {
                LoginSystem.userCreation(url, username, password);
            } else if (selection == 2) {
                LoginSystem.getUsersFromDb(url, username, password);
                LoginSystem.userListComparison = LoginSystem.userList;
                LoginSystem.loginCheck();
                if (LoginSystem.loginSuccessful) {
                    if (LoginSystem.userIsAdmin>0){
                        AdminMenu.getAdminMenu();
                    }
                    else if (LoginSystem.userIsAdmin==0){
                        UserMenu.getUserMenu();
                    }
                } else {
                    System.out.println("Login was unsuccessful.");
                }
            }
        } while (selection != 3);
    }
}