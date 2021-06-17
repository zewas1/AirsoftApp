package menus;

import LoginSystem.LoginSystem;
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
            getMenuSelection();
            switch (MainClass.menuSelection) {
                case doShowStats:
                    UserMenu.showMyStats();
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
                    getMenuSelection();
                    break;
            }
        } while (MainClass.menuSelection != doExitMenu);
        UserMenu.userDisconnected();
    }

    private static void getMenuSelection() {
        System.out.println("Welcome, " + LoginSystem.currentUser);
        System.out.println("This is an admin menu. Available functions are:");
        System.out.println("1. Check your stats");
        System.out.println("2. Calculate KDA");
        System.out.println("3. Join on-going events.");
        System.out.println("4. Change user stats.");
        System.out.println("5. Teams menu.");
        System.out.println("6. Access event menu.");
        System.out.println("7. Top five players.");
        System.out.println("8. exit.");
        try {
            MainClass.menuSelection = Integer.parseInt(MainClass.scan.next());
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
    }
}