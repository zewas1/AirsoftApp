package menus;

import LoginSystem.LoginSystem;
import main.MainClass;
import menus.SpecialFeatures.ChangeStatsMenu;
import menus.SpecialFeatures.EventMenu;

import java.sql.SQLException;

public class AdminMenu {

    private static final int doShowStats = 1;
    private static final int doCountKda = 2;
    private static final int doChangeUserStats = 3;
    private static final int doCreateEvent = 4;
    private static final int doExitMenu = 5;


    public static void getAdminMenu() throws SQLException {
        do {
            getMenuSelection();
            if (MainClass.menuSelection == doShowStats) {
                UserMenu.showMyStats();
            } else if (MainClass.menuSelection == doCountKda) {
                UserMenu.countKda();
            } else if (MainClass.menuSelection == doChangeUserStats) {
                ChangeStatsMenu.specialSelectUser();
            } else if (MainClass.menuSelection == doCreateEvent){
                EventMenu.CreateEvent();
            }
        } while (MainClass.menuSelection != doExitMenu);
        UserMenu.userDisconnected();
    }

    private static void getMenuSelection() {
        System.out.println("Welcome, " + LoginSystem.currentUser);
        System.out.println("This is an admin menu. Available functions are:");
        System.out.println("1. Check your stats");
        System.out.println("2. Calculate KDA");
        System.out.println("3. Change user stats.");
        System.out.println("4. Create a new event.");
        System.out.println("5. exit.");
        try {
            MainClass.menuSelection = Integer.parseInt(MainClass.scan.next());
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
    }
}