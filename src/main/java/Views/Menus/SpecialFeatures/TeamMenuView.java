package Views.Menus.SpecialFeatures;

import main.MainClass;

public class TeamMenuView {

    public static void getTeamMenuSelection() {
        System.out.println("Please choose one of the available options:");
        System.out.println("1. Join a team.");
        System.out.println("2. Open team management.");
        System.out.println("3. Exit.");
        try {
            MainClass.menuSelection = Integer.parseInt(MainClass.scan.next());
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
    }
}
