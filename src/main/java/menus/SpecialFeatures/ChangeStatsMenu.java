package menus.SpecialFeatures;

import LoginSystem.LoginSystem;
import LoginSystem.Utilities.DataRefresh;
import LoginSystem.Objects.User;
import Views.Menus.SpecialFeatures.ChangeStatsMenuView;
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

    /**
     * @throws SQLException
     */
    public static void specialSelectUser() throws SQLException {
        boolean isValidUserSelected = false;
        showUserList();
        try {
            selectUser = Integer.parseInt(MainClass.scan.next());
            for (User user : LoginSystem.userList) {
                if (user.getUserId() == selectUser) {
                    isValidUserSelected = true;
                    System.out.println("User " + user.getUserLogin() + " selected.");
                    DataRefresh.statRefresh();
                    ChangeStatsMenuView.showSelectUserStats();
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

    /**
     * @throws SQLException
     */
    private static void showUserList() throws SQLException {
        LoginSystem.getUsersFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        System.out.println("Select user by ID. Type '0' to leave this menu.");
        for (User user : LoginSystem.userList) {
            System.out.println(user.getUserId() + " " + user.getUserLogin());
        }
    }

    /**
     * @throws SQLException
     */
    private static void specialChangeStat() throws SQLException {
        for (User user : LoginSystem.userList) {
            if (user.getUserId() == selectUser) {
                System.out.println("How many " + statChangeName + " would you like to set? Type '0' to leave this menu.");
                setStat = Integer.parseInt(MainClass.scan.next());
                statChangeAction();
            }
        }
    }

    /**
     * @throws SQLException
     */
    private static void statChangeAction() throws SQLException {
        if (setStat != quitUserSelection) {
            uploadStatChanges();
            DataRefresh.statRefresh();
            ChangeStatsMenuView.showSelectUserStats();
        } else {
            System.out.println("You have left the menu.");
        }
    }

    /**
     * @throws SQLException
     */
    private static void specialChangeStats() throws SQLException {
        int getSelectStatChange;

        do {
            ChangeStatsMenuView.specialChangeStatsMenu();
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
                    ChangeStatsMenuView.specialChangeStatsMenu();
                    break;
            }
        } while (getSelectStatChange != exitChangeStats);
    }

    /**
     * @throws SQLException
     */
    private static void uploadStatChanges() throws SQLException {
        Connection connection = DriverManager.getConnection(MainMenu.url, MainMenu.username, MainMenu.password);
        Statement statement = connection.createStatement();
        String sqlString = "UPDATE `users` SET `" + uploadField + "` ='" + setStat + "' WHERE `userId` = '" + selectUser + "'";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
    }

}
