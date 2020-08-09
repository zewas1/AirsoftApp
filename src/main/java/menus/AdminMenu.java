package menus;
import LoginSystem.LoginSystem;
import main.MainClass;
import LoginSystem.User;

import java.sql.SQLException;

public class AdminMenu {

    public static void getAdminMenu() throws SQLException {
        do {
            UserMenu.getMenuSelection();
            if (MainClass.menuSelection == 1) {
                UserMenu.showStats();
            } else if (MainClass.menuSelection == 2) {
                UserMenu.countKda();
            } else if (MainClass.menuSelection == 3){
                changeUserStats();
            }
        } while (MainClass.menuSelection != 4);
        LoginSystem.loginSuccessful = false;
        LoginSystem.userList.clear();
        LoginSystem.userListComparison.clear();
    }

    private static void changeUserStats() throws SQLException {
        LoginSystem.getUsersFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        System.out.println("Select user by ID");
        for (User user : LoginSystem.userList) {
            System.out.println(user.getUserId()+ " " + user.getUserLogin());
        }
        int selectUser = Integer.parseInt(MainClass.scan.next());
        for (User user : LoginSystem.userList) {
            if (user.getUserId() == selectUser){
                System.out.println("User " + user.userLogin + " selected.");
            }
        }
    }
}