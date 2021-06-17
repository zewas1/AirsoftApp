package menus.SpecialFeatures;

import LoginSystem.LoginSystem;
import LoginSystem.Utilities.DataRefresh;
import LoginSystem.Objects.User;
import main.MainClass;
import menus.MainMenu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangeStatsMenu {

    public static int selectUser = 0;
    private static int setStat = 0;

    private static String uploadField = null;
    private static String statChangeName = null;

    private static final int changeKillCount = 1;
    private static final int changeDeathCount = 2;
    private static final int changeAssistCount = 3;
    private static final int exitChangeStats = 4;
    private static final int quitUserSelection = 0;

    private static void specialChangeStatsMenu() {
        System.out.println("What stats would you like to change?");
        System.out.println("1. Kill count.");
        System.out.println("2. Death count.");
        System.out.println("3. Assist count.");
        System.out.println("4. Exit.");
    }

    public static void specialSelectUser() throws SQLException {
        boolean isValidUserSelected = false;
        showUserList();
        try {
            selectUser = Integer.parseInt(MainClass.scan.next());
            for (User user : LoginSystem.userList) {
                if (user.getUserId() == selectUser) {
                    isValidUserSelected = true;
                    System.out.println("User " + user.userLogin + " selected.");
                    DataRefresh.statRefresh();
                    showSelectUserStats();
                    specialChangeStats();
                } else if (selectUser == quitUserSelection) {
                    System.out.println("You have left the menu.");
                    isValidUserSelected = true;
                    break;
                }
            }
            if (!isValidUserSelected) {
                System.out.println("Invalid user selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
        selectUser = 0;
    }

    private static void showUserList() throws SQLException {
        LoginSystem.getUsersFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        System.out.println("Select user by ID. Type '0' to leave this menu.");
        for (User user : LoginSystem.userList) {
            System.out.println(user.getUserId() + " " + user.getUserLogin());
        }
    }

    private static void specialChangeStat() throws SQLException {
        for (User user : LoginSystem.userList) {
            if (user.getUserId() == selectUser) {
                System.out.println("How many " + statChangeName + " would you like to set? Type '0' to leave this menu.");
                setStat = Integer.parseInt(MainClass.scan.next());
                if (setStat != quitUserSelection) {
                    uploadStatChanges();
                    DataRefresh.statRefresh();
                    showSelectUserStats();
                } else {
                    System.out.println("You have left the menu.");
                }
            }
        }
    }

    private static void specialChangeStats() throws SQLException {
        int getSelectStatChange;

        do {
            specialChangeStatsMenu();
            getSelectStatChange = Integer.parseInt(MainClass.scan.next());
            switch (getSelectStatChange) {
                case changeKillCount:
                    uploadField = "killCount";
                    statChangeName = "kills";
                    specialChangeStat();
                    break;
                case changeDeathCount:
                    uploadField = "deathCount";
                    statChangeName = "deaths";
                    specialChangeStat();
                    break;
                case changeAssistCount:
                    uploadField = "assistCount";
                    statChangeName = "assists";
                    specialChangeStat();
                    break;
                case exitChangeStats:
                    break;
                default:
                    System.out.println("No such menu option.");
                    specialChangeStatsMenu();
                    break;
            }
        } while (getSelectStatChange != exitChangeStats);
    }

    private static void uploadStatChanges() throws SQLException {
        Connection connection = DriverManager.getConnection(MainMenu.url, MainMenu.username, MainMenu.password);
        Statement statement = connection.createStatement();
        String sqlString = "UPDATE `users` SET `" + uploadField + "` ='" + setStat + "' WHERE `userId` = '" + selectUser + "'";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
    }

    private static void showSelectUserStats() {
        System.out.println("Stat info:");
        System.out.println("Kills: " + DataRefresh.selectedUserKills);
        System.out.println("Deaths: " + DataRefresh.selectedUserDeaths);
        System.out.println("Assists: " + DataRefresh.selectedUserAssists);
    }
}
