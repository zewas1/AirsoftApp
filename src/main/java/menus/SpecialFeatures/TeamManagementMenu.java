package menus.SpecialFeatures;

import Views.Menus.SpecialFeatures.TeamManagementMenuView;
import main.MainClass;

public class TeamManagementMenu {


    private static final int doCreateTeam = 1;
    private static final int doKickPlayers = 2;
    private static final int doExitMenu = 3;


    public static void openTeamManagementMenu() {

        do {
            TeamManagementMenuView.getTeamManagementSelection();
            switch (MainClass.menuSelection) {
                case doCreateTeam:
                    System.out.println("Create a team.");
                    createTeam();
                    break;
                case doKickPlayers:
                    System.out.println("Kick player from team.");
                    kickPlayers();
                    break;
                case doExitMenu:
                    break;
                default:
                    System.out.println("No such menu option.");
                    TeamManagementMenuView.getTeamManagementSelection();
                    break;
            }
        } while (MainClass.menuSelection != doExitMenu);
    }

    private static void createTeam() {

    }

    private static void kickPlayers() {

    }

}
