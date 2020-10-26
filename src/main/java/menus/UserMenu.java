package menus;

import LoginSystem.LoginSystem;
import main.MainClass;

import java.sql.SQLException;
import java.util.Collections;

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
        LoginSystem.loginSuccessful = false;
        LoginSystem.userList.clear();
        LoginSystem.userListComparison.clear();
    }

    static void countKda() throws SQLException {
        LoginSystem.dataRefresh();
        System.out.println("KDA: " + (double) ((LoginSystem.selectedUserAssists) / 2 + LoginSystem.selectedUserKills) /
                LoginSystem.selectedUserDeaths);
    }

    static void showMyStats() throws SQLException {
        LoginSystem.dataRefresh();
        System.out.println("Kill count: " + LoginSystem.selectedUserKills);
        System.out.println("Death count: " + LoginSystem.selectedUserDeaths);
        System.out.println("Assist count: " + LoginSystem.selectedUserAssists);
    }

    private static void getMenuSelection() {
        System.out.println("Welcome, " + LoginSystem.currentUser);
        System.out.println("This is a user menu. Available functions are:");
        System.out.println("1. Check your stats");
        System.out.println("2. Calculate KDA");
        //System.out.println("3. Play accuracy game.");
        System.out.println("3. exit.");
        MainClass.menuSelection = Integer.parseInt(MainClass.scan.next());
    }

}