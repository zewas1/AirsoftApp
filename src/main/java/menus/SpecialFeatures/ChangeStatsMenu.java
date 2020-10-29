package menus.SpecialFeatures;

import LoginSystem.LoginSystem;
import LoginSystem.DataRefresh.DataRefresh;
import LoginSystem.User;
import main.MainClass;
import menus.MainMenu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangeStatsMenu {

    private static boolean isValidUserSelected = false;
    private static int setStat = 0;
    private static String uploadField = null;
    public static int selectUser = 0;
    private static String statChangeName = null;

    private static void specialChangeStatsMenu() {
        System.out.println("What stats would you like to change?");
        System.out.println("1. Kill count.");
        System.out.println("2. Death count.");
        System.out.println("3. Assist count.");
        System.out.println("4. Exit.");
    }

    public static void specialSelectUser() throws SQLException {
        LoginSystem.getUsersFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        System.out.println("Select user by ID");
        for (User user : LoginSystem.userList) {
            System.out.println(user.getUserId() + " " + user.getUserLogin());
        }
        try {
            selectUser = Integer.parseInt(MainClass.scan.next());
            for (User user : LoginSystem.userList) {
                if (user.getUserId() == selectUser) {
                    isValidUserSelected = true;
                    System.out.println("User " + user.userLogin + " selected.");
                    DataRefresh.dataRefresh();
                    showSelectUserStats();
                    specialChangeStats();
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

    private static void specialChangeStat() throws SQLException {
        for (User user : LoginSystem.userList) {
            if (user.getUserId() == selectUser) {
                System.out.println("How many " + statChangeName + " would you like to set?");
                setStat = Integer.parseInt(MainClass.scan.next());
                setUploadField();
                uploadStatChanges(MainMenu.url, MainMenu.username, MainMenu.password);
                DataRefresh.dataRefresh();
                showSelectUserStats();
            }
        }
    }

    private static void specialChangeStats() throws SQLException {
        int getSelectStatChange;
        int changeKillCount = 1;
        int changeDeathCount = 2;
        int changeAssistCount = 3;
        int exitChangeStats = 4;

        do {
            specialChangeStatsMenu();
            getSelectStatChange = Integer.parseInt(MainClass.scan.next());
            if (getSelectStatChange == changeKillCount) {
                statChangeName = "kills";
                specialChangeStat();
            } else if (getSelectStatChange == changeDeathCount) {
                statChangeName = "deaths";
                specialChangeStat();
            } else if (getSelectStatChange == changeAssistCount) {
                statChangeName = "assists";
                specialChangeStat();
            }

        } while (getSelectStatChange != exitChangeStats);
    }

    private static void setUploadField() {
        switch (statChangeName) {
            case "kills":
                uploadField = "killCount";
                break;
            case "deaths":
                uploadField = "deathCount";
                break;
            case "assists":
                uploadField = "assistCount";
                break;
        }
    }

    private static void uploadStatChanges(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
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
