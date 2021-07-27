package Views.Menus.SpecialFeatures;

import main.MainClass;

public class TeamManagementMenuView {

    public static void getTeamManagementSelection() {
        System.out.println("Please choose one of the available options:");
        System.out.println("1. Create a team.");
        System.out.println("2. Kick player from team.");
        System.out.println("3. Exit.");
        try {
            MainClass.menuSelection = Integer.parseInt(MainClass.scan.next());
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
    }
}
