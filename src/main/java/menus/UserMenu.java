package menus;

import LoginSystem.Utilities.DataRefresh;
import LoginSystem.LoginSystem;
import Views.Menus.UserMenuViews;
import main.MainClass;
import menus.SpecialFeatures.EventEditingMenu;
import menus.SpecialFeatures.EventMenu;
import menus.SpecialFeatures.TeamMenu;
import menus.SpecialFeatures.TopFiveMenu;
import java.sql.SQLException;

public class UserMenu {

    private static final int doShowStats = 1;
    private static final int doCountKda = 2;
    private static final int doJoinCurrentEvents = 3;
    private static final int doOpenTeamMenu = 4;
    private static final int topFivePlayers = 5;
    private static final int doExitMenu = 6;
    public static boolean userInputValidation = false;

    /**
     * @throws SQLException
     */
    public static void getUserMenu() throws SQLException {
        do {
            UserMenuViews.getMenuSelection();
            switch (MainClass.menuSelection) {
                case doShowStats:
                    UserMenuViews.showMyStats();
                    break;
                case doCountKda:
                    countKda();
                    break;
                case doJoinCurrentEvents:
                    userInputValidation = true;
                    joinCurrentEvents();
                    userInputValidation = false;
                    break;
                case doOpenTeamMenu:
                    TeamMenu.openTeamMenu();
                case topFivePlayers:
                    TopFiveMenu.topFivePlayers();
                case doExitMenu:
                    break;
                default:
                    System.out.println("No such menu option.");
                    UserMenuViews.getMenuSelection();
                    break;
            }
        } while (MainClass.menuSelection != doExitMenu);
        userDisconnected();
    }

    /**
     * @throws SQLException
     */
    public static void countKda() throws SQLException {
        DataRefresh.statRefresh();
        try {
            kdaCalculations();
        } catch (ArithmeticException e) {
            kdaSpecialCalculations();
        }
    }

    private static void kdaCalculations() {
        if (DataRefresh.selectedUserKills / DataRefresh.selectedUserDeaths >= 9000) {
            System.out.println("It's over 9000.");
        } else {
            System.out.println("KDA: " + (double) ((DataRefresh.selectedUserAssists) / 2 + DataRefresh.selectedUserKills) /
                    DataRefresh.selectedUserDeaths);
        }
    }

    private static void kdaSpecialCalculations() {
        if (DataRefresh.selectedUserKills > DataRefresh.selectedUserDeaths ||
                DataRefresh.selectedUserAssists > DataRefresh.selectedUserDeaths) {
            System.out.println("You have a perfect KDA. " + DataRefresh.selectedUserKills + " kills, " +
                    DataRefresh.selectedUserAssists + " assists, " + DataRefresh.selectedUserDeaths + " deaths.");
        } else {
            System.out.println("KDA is 0.");
        }
    }

    /**
     * @throws SQLException
     */
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