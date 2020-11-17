package LoginSystem.Utilities;

import LoginSystem.LoginSystem;
import LoginSystem.Objects.User;
import menus.MainMenu;
import menus.SpecialFeatures.ChangeStatsMenu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRefresh {
    public static List<User> showDataList = new ArrayList<>();
    private static List<User> showDataListComparison = new ArrayList<>();
    public static int currentUserId = 0;
    public static int selectedUserKills = 0;
    public static int selectedUserDeaths = 0;
    public static int selectedUserAssists = 0;

    public static void statRefresh() throws SQLException {
        showDataList.clear();
        showDataListComparison.clear();
        getDataFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        if (ChangeStatsMenu.selectUser == 0) {
            ChangeStatsMenu.selectUser = currentUserId;
        }
        for (User user : showDataList) {
            if (ChangeStatsMenu.selectUser == (user.getUserId())) {
                selectedUserKills = user.getKillCount();
                selectedUserDeaths = user.getDeathCount();
                selectedUserAssists = user.getAssistCount();
            }
        }
    }

    public static void getDataFromDb(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        if (showDataList.size() > showDataListComparison.size() || showDataList.isEmpty()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUserLogin(resultSet.getString("userLogin"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setIsAdmin(resultSet.getInt("isAdmin"));
                user.setKillCount(resultSet.getInt("killCount"));
                user.setDeathCount(resultSet.getInt("deathCount"));
                user.setAssistCount(resultSet.getInt("assistCount"));
                showDataList.add(user);
            }
            resultSet.close();
            statement.close();
        }
        connection.close();
    }

    public static void userListRefresh() {
        LoginSystem.userList.clear();
        LoginSystem.userListComparison.clear();
    }
}
