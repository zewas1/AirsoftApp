package Views.Menus;

import LoginSystem.LoginSystem;
import LoginSystem.Utilities.DataRefresh;
import main.MainClass;

import java.sql.SQLException;

public class UserMenuViews {

    public static void getMenuSelection() {
        System.out.println("Welcome, " + LoginSystem.currentUser);
        System.out.println("This is a user menu. Available functions are:");
        System.out.println("1. Check your stats");
        System.out.println("2. Calculate KDA");
        System.out.println("3. Join on-going events.");
        System.out.println("4. Teams menu.");
        System.out.println("5. Top five players.");
        System.out.println("6. Exit menu.");
        try {
            MainClass.menuSelection = Integer.parseInt(MainClass.scan.next());
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
    }

    public static void showMyStats() throws SQLException {
        DataRefresh.statRefresh();
        System.out.println("Kill count: " + DataRefresh.selectedUserKills);
        System.out.println("Death count: " + DataRefresh.selectedUserDeaths);
        System.out.println("Assist count: " + DataRefresh.selectedUserAssists);
    }

}
