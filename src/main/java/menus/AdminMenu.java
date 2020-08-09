package menus;
import LoginSystem.LoginSystem;
import main.MainClass;
import LoginSystem.User;

import java.sql.SQLException;

public class AdminMenu {

    private static int selectUser;
    private static User user = new User();

    // idea to self, make menu lead into services based on what functions i'm gonna add, because some services
    // might be the same for multiple menus (e.g. check stats).

    public static void getAdminMenu() throws SQLException {
        do {
            getMenuSelection();
            if (MainClass.menuSelection == 1) {
                showStats();
            } else if (MainClass.menuSelection == 2) {
                countKda();
            } else if (MainClass.menuSelection == 3){
                LoginSystem.getUsersFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
                System.out.println("Select user by ID");
                for (User user : LoginSystem.userList) {
                    System.out.println(user.getUserId()+ " " + user.getUserLogin());
                }
                selectUser = Integer.parseInt(MainClass.scan.next());
                for (User user : LoginSystem.userList) {
                    if (user.getUserId() == selectUser){
                        System.out.println("User " + user.userLogin + " selected.");
                    }
                }
            }
        } while (MainClass.menuSelection != 4);
        if (MainClass.menuSelection == 4)
        {
            LoginSystem.loginSuccessful = false;
            LoginSystem.userList.clear();
            LoginSystem.userListComparison.clear();
        }
    }

    private static void countKda() {
        System.out.println("KDA: " + (double) ((LoginSystem.currentAssists) / 2 + LoginSystem.currentKills) /
                LoginSystem.currentDeaths);
    }

    private static void showStats() {
        System.out.println("Kill count: " + LoginSystem.currentKills);
        System.out.println("Death count: " + LoginSystem.currentDeaths);
        System.out.println("Assist count: " + LoginSystem.currentAssists);
    }

    private static void getMenuSelection() {
        System.out.println("Welcome, " + LoginSystem.currentUser);
        System.out.println("This is a Admin menu. Available functions are:");
        System.out.println("1. Check your stats");
        System.out.println("2. Calculate KDA");
        System.out.println("3. Upload user stats");
        System.out.println("4. exit.");
        MainClass.menuSelection = Integer.parseInt(MainClass.scan.next());
    }
}