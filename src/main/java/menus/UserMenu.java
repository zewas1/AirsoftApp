package menus;
import LoginSystem.LoginSystem;
import main.MainClass;

public class UserMenu {
    public static void getUserMenu() {
        do {
            System.out.println("Welcome, " + LoginSystem.currentUser);
            System.out.println("This is a user menu. Available functions are:");
            System.out.println("1. Check your stats");
            System.out.println("2. Calculate KDA");
            //System.out.println("3. Play accuracy game.");
            System.out.println("3. exit.");
            MainClass.menuSelection = Integer.parseInt(MainClass.scan.next());
            if (MainClass.menuSelection == 1) {
                System.out.println("Kill count: " + LoginSystem.currentKills);
                System.out.println("Death count: " + LoginSystem.currentDeaths);
                System.out.println("Assist count: " + LoginSystem.currentAssists);
            } else if (MainClass.menuSelection == 2) {
                System.out.println("KDA: " + (double) ((LoginSystem.currentAssists) / 2 + LoginSystem.currentKills) /
                        LoginSystem.currentDeaths);
            }
        } while (MainClass.menuSelection != 3);
        if (MainClass.menuSelection == 3)
        {
            LoginSystem.loginSuccessful = false;
        }
    }
}