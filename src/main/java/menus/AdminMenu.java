package menus;

import Views.Menus.AdminMenuViews;
import Views.Menus.UserMenuViews;
import main.MainClass;
import menus.SpecialFeatures.*;
import java.sql.SQLException;

public class AdminMenu {

    private static final int doShowStats = 1;
    private static final int doCountKda = 2;
    private static final int doJoinCurrentEvents = 3;
    private static final int doChangeUserStats = 4;
    private static final int doOpenTeamMenu = 5;
    private static final int doCreateEvent = 6;
    private static final int topFivePlayers = 7;
    private static final int doExitMenu = 8;

    public static boolean adminInputValidation;

    public static void getAdminMenu() throws SQLException {
        do {
            AdminMenuViews.getMenuSelection();
            switch (MainClass.menuSelection) {
                case doShowStats:
                    UserMenuViews.showMyStats();
                    break;
                case doCountKda:
                    UserMenu.countKda();
                    break;
                case doJoinCurrentEvents:
                    UserMenu.userInputValidation = true;
                    UserMenu.joinCurrentEvents();
                    UserMenu.userInputValidation = false;
                    break;
                case doChangeUserStats:
                    ChangeStatsMenu.specialSelectUser();
                    break;
                case doOpenTeamMenu:
                    TeamMenu.openTeamMenu();
                case doCreateEvent:
                    adminInputValidation = true;
                    EventMenu.openEventMenu();
                    adminInputValidation = false;
                    break;
                case topFivePlayers:
                    TopFiveMenu.topFivePlayers();
                case doExitMenu:
                    break;
                default:
                    System.out.println("No such menu option.");
                    AdminMenuViews.getMenuSelection();
                    break;
            }
        } while (MainClass.menuSelection != doExitMenu);
        UserMenu.userDisconnected();
    }
}