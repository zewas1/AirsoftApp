package menus.SpecialFeatures;

import main.MainClass;

public class TeamManagementMenu {

    private static int teamManagementSelection = 0;



    private static void getTeamManagementSelection() {
        System.out.println("Please choose one of the available options:");
        System.out.println("1. Create a team.");
        System.out.println("2. Exit.");
        try {
            teamManagementSelection = Integer.parseInt(MainClass.scan.next());
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
    }


}
