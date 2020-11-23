package menus;

import LoginSystem.Utilities.DataRefresh;
import LoginSystem.LoginSystem;
import main.MainClass;
import menus.SpecialFeatures.EventEditingMenu;
import menus.SpecialFeatures.EventMenu;

import java.sql.SQLException;

public class UserMenu {

    private static final int doShowStats = 1;
    private static final int doCountKda = 2;
    private static final int doJoinCurrentEvents = 3;
    private static final int doExitMenu = 4;
    public static boolean userInputValidation = false;

    public static void getUserMenu() throws SQLException {
        do {
            getMenuSelection();
            switch (MainClass.menuSelection) {
                case doShowStats:
                    showMyStats();
                    break;
                case doCountKda:
                    countKda();
                    break;
                case doJoinCurrentEvents:
                    userInputValidation = true;
                    joinCurrentEvents();
                    userInputValidation = false;
                    break;
                case doExitMenu:
                    break;
                default:
                    System.out.println("No such menu option.");
                    getMenuSelection();
                    break;
            }
        } while (MainClass.menuSelection != doExitMenu);
        userDisconnected();
    }

    static void countKda() throws SQLException {
        DataRefresh.statRefresh();
        try {
            if (DataRefresh.selectedUserKills / DataRefresh.selectedUserDeaths >= 9000) {
                System.out.println("It's over 9000.");
            } else {
                System.out.println("KDA: " + (double) ((DataRefresh.selectedUserAssists) / 2 + DataRefresh.selectedUserKills) /
                        DataRefresh.selectedUserDeaths);
            }
        } catch (ArithmeticException e){
            if (DataRefresh.selectedUserKills > DataRefresh.selectedUserDeaths ||
                    DataRefresh.selectedUserAssists > DataRefresh.selectedUserDeaths){
                System.out.println("You have a perfect KDA. " + DataRefresh.selectedUserKills + " kills, " +
                        DataRefresh.selectedUserAssists + " assists, " + DataRefresh.selectedUserDeaths + " deaths.");
            } else {
                System.out.println("KDA is 0.");
            }
        }

    }

    static void showMyStats() throws SQLException {
        DataRefresh.statRefresh();
        System.out.println("Kill count: " + DataRefresh.selectedUserKills);
        System.out.println("Death count: " + DataRefresh.selectedUserDeaths);
        System.out.println("Assist count: " + DataRefresh.selectedUserAssists);
    }

    private static void getMenuSelection() {
        System.out.println("Welcome, " + LoginSystem.currentUser);
        System.out.println("This is a user menu. Available functions are:");
        System.out.println("1. Check your stats");
        System.out.println("2. Calculate KDA");
        System.out.println("3. Join on-going events.");
        System.out.println("4. Exit.");
        try {
            MainClass.menuSelection = Integer.parseInt(MainClass.scan.next());
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
    }

    public static void joinCurrentEvents() throws SQLException {
        EventEditingMenu.EventEditingSelection();
        EventMenu.eventListCacheClear();
    }

    public static void userDisconnected() {
        LoginSystem.loginSuccessful = false;
        DataRefresh.userListRefresh();
        EventMenu.eventList.clear();
        EventMenu.eventListComparison.clear();
    }
}