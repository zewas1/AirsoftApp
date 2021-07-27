package Views.Menus;

import LoginSystem.LoginSystem;
import main.MainClass;

public class AdminMenuViews {

    public static void getMenuSelection() {
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
