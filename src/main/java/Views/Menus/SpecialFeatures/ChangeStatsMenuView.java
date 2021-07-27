package Views.Menus.SpecialFeatures;

import LoginSystem.Utilities.DataRefresh;

public class ChangeStatsMenuView {

    public static void specialChangeStatsMenu() {
        System.out.println("What stats would you like to change?");
        System.out.println("1. Kill count.");
        System.out.println("2. Death count.");
        System.out.println("3. Assist count.");
        System.out.println("4. Exit.");
    }

    public static void showSelectUserStats() {
        System.out.println("Stat info:");
        System.out.println("Kills: " + DataRefresh.selectedUserKills);
        System.out.println("Deaths: " + DataRefresh.selectedUserDeaths);
        System.out.println("Assists: " + DataRefresh.selectedUserAssists);
    }

}
