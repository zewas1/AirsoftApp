package menus;

import LoginSystem.LoginSystem;
import main.MainClass;
import LoginSystem.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminMenu {

    private static final int doShowStats = 1;
    private static final int doCountKda = 2;
    private static final int doChangeUserStats = 3;
    private static final int doExitMenu = 4;
    private static boolean isValidUserSelected = false;
    public static int selectUser = 0;
    private static String statChangeName = null;
    private static int setStat = 0;


    public static void getAdminMenu() throws SQLException {
        do {
            getMenuSelection();
            if (MainClass.menuSelection == doShowStats) {
                UserMenu.showMyStats();
            } else if (MainClass.menuSelection == doCountKda) {
                UserMenu.countKda();
            } else if (MainClass.menuSelection == doChangeUserStats) {
                specialSelectUser();
            }
        } while (MainClass.menuSelection != doExitMenu);
        LoginSystem.loginSuccessful = false;
        LoginSystem.userList.clear();
        LoginSystem.userListComparison.clear();
    }

    private static void specialSelectUser() throws SQLException {
        LoginSystem.getUsersFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        System.out.println("Select user by ID");
        for (User user : LoginSystem.userList) {
            System.out.println(user.getUserId() + " " + user.getUserLogin());
        }
        selectUser = Integer.parseInt(MainClass.scan.next());
        for (User user : LoginSystem.userList) {
            if (user.getUserId() == selectUser) {
                isValidUserSelected = true;
                System.out.println("User " + user.userLogin + " selected.");
                LoginSystem.dataRefresh();
                showSelectUserStats();
                specialChangeStats();
            }
        }
        if (!isValidUserSelected) {
            System.out.println("Invalid user selection.");
        }
    }

    private static void showSelectUserStats(){
        System.out.println("Stat info:");
        System.out.println("Kills: " + LoginSystem.selectedUserKills);
        System.out.println("Deaths: " + LoginSystem.selectedUserDeaths);
        System.out.println("Assists: " + LoginSystem.selectedUserAssists);
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

    private static void specialChangeStat() throws SQLException {
        for (User user : LoginSystem.userList) {
            if (user.getUserId() == selectUser) {
                System.out.println("How many " + statChangeName + " would you like to set?");
                setStat = Integer.parseInt(MainClass.scan.next());
                uploadStatChanges(MainMenu.url, MainMenu.username, MainMenu.password);
                LoginSystem.dataRefresh();
                showSelectUserStats();
            }
        }
    }

    private static void uploadStatChanges(String url, String username, String password) throws SQLException {
        String uploadField = null;
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

        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String sqlString = "UPDATE `users` SET `" + uploadField + "` ='" + setStat + "' WHERE `userId` = '" + selectUser + "'";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
    }

    private static void specialChangeStatsMenu() {
        System.out.println("What stats would you like to change?");
        System.out.println("1. Kill count.");
        System.out.println("2. Death count.");
        System.out.println("3. Assist count.");
        System.out.println("4. Exit.");
    }

    private static void getMenuSelection() {
        System.out.println("Welcome, " + LoginSystem.currentUser);
        System.out.println("This is an admin menu. Available functions are:");
        System.out.println("1. Check your stats");
        System.out.println("2. Calculate KDA");
        System.out.println("3. Change user stats.");
        System.out.println("4. exit.");
        MainClass.menuSelection = Integer.parseInt(MainClass.scan.next());
    }
}