package menus;
import LoginSystem.LoginSystem;
import main.MainClass;

public class AdminMenu {

    // idea to self, make menu lead into services based on what functions i'm gonna add, because some services
    // might be the same for multiple menus (e.g. check stats).

    public static void getAdminMenu() {
        do {
            System.out.println("Welcome, " + LoginSystem.currentUser);
            System.out.println("This is a Admin menu. Available functions are:");
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
            LoginSystem.userList.clear();
            LoginSystem.userListComparison.clear();
        }
    }
}