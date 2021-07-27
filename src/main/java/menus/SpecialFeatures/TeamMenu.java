package menus.SpecialFeatures;

import Views.Menus.SpecialFeatures.TeamMenuView;
import main.MainClass;

public class TeamMenu {

    private static final int doJoinTeam = 1;
    private static final int doOpenTeamManagement = 2;
    private static final int doExitTeamMenu = 3;

    public static void openTeamMenu() {
        do {
            TeamMenuView.getTeamMenuSelection();
            switch (MainClass.menuSelection) {
                case doJoinTeam:
                    System.out.println("Team join options");
                    teamJoin();
                    break;
                case doOpenTeamManagement:
                    System.out.println("Team management menu");
                    TeamManagementMenu.openTeamManagementMenu();
                    break;
                case doExitTeamMenu:
                    break;
                default:
                    System.out.println("No such menu option.");
                    TeamMenuView.getTeamMenuSelection();
                    break;
            }
        } while (MainClass.menuSelection != doExitTeamMenu);

    }

    private static void teamJoin() {

    }
}
