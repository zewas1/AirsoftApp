package menus.SpecialFeatures;

import main.MainClass;

public class TeamManagementMenu {


    private static final int doCreateTeam = 1;
    private static final int doKickPlayers = 2;
    private static final int doExitMenu = 3;
    private static int teamManagementSelection = 0;

    public static void openTeamManagementMenu() {

        do {
            getTeamManagementSelection();
            switch (teamManagementSelection) {
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
                    getTeamManagementSelection();
                    break;
            }
        } while (teamManagementSelection != doExitMenu);
    }


    private static void getTeamManagementSelection() {
        System.out.println("Please choose one of the available options:");
        System.out.println("1. Create a team.");
        System.out.println("2. Kick player from team.");
        System.out.println("3. Exit.");
        try {
            teamManagementSelection = Integer.parseInt(MainClass.scan.next());
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
    }

    private static void createTeam() {

    }

    private static void kickPlayers() {

    }


}
