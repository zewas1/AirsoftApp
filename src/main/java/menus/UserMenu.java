package menus;

import LoginSystem.DataRefresh.DataRefresh;
import LoginSystem.LoginSystem;
import main.MainClass;

import java.sql.SQLException;

public class UserMenu {

    private static final int doShowStats = 1;
    private static final int doCountKda = 2;
    private static final int doExitMenu = 3;


    public static void getUserMenu() throws SQLException {
        do {
            getMenuSelection();
            if (MainClass.menuSelection == doShowStats) {
                showMyStats();
            } else if (MainClass.menuSelection == doCountKda) {
                countKda();
            }
        } while (MainClass.menuSelection != doExitMenu);
        userDisconnected();
    }

    static void countKda() throws SQLException {
        DataRefresh.dataRefresh();
        System.out.println("KDA: " + (double) ((DataRefresh.selectedUserAssists) / 2 + DataRefresh.selectedUserKills) /
                DataRefresh.selectedUserDeaths);
    }

    static void showMyStats() throws SQLException {
        DataRefresh.dataRefresh();
        System.out.println("Kill count: " + DataRefresh.selectedUserKills);
        System.out.println("Death count: " + DataRefresh.selectedUserDeaths);
        System.out.println("Assist count: " + DataRefresh.selectedUserAssists);
    }

    private static void getMenuSelection() {
        System.out.println("Welcome, " + LoginSystem.currentUser);
        System.out.println("This is a user menu. Available functions are:");
        System.out.println("1. Check your stats");
        System.out.println("2. Calculate KDA");
        //System.out.println("3. Play accuracy game.");
        System.out.println("3. exit.");
        try {
            MainClass.menuSelection = Integer.parseInt(MainClass.scan.next());
        } catch (NumberFormatException e){
            System.out.println("Only numbers are allowed.");
        }
    }
    public static void userDisconnected() {
        LoginSystem.loginSuccessful = false;
        LoginSystem.userList.clear();
        LoginSystem.userListComparison.clear();
    }

}