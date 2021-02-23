package menus.SpecialFeatures;

import main.MainClass;

public class TeamMenu {

    private static final int doJoinTeam = 1;
    private static final int doOpenTeamManagement = 2;
    private static final int doExitTeamMenu = 3;
    private static int teamMenuSelection = 0;

    public static void openTeamMenu(){
        do {
            getTeamMenuSelection();
            switch (teamMenuSelection) {
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
                    getTeamMenuSelection();
                    break;
            }
        } while (teamMenuSelection!=doExitTeamMenu);

    }

    private static void getTeamMenuSelection() {
        System.out.println("Please choose one of the available options:");
        System.out.println("1. Join a team.");
        System.out.println("2. Open team management.");
        System.out.println("3. Exit.");
        try {
            teamMenuSelection = Integer.parseInt(MainClass.scan.next());
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
    }

    private static void teamJoin(){

    }
}
