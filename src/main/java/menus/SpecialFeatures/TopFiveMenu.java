package menus.SpecialFeatures;

import LoginSystem.Objects.User;
import menus.MainMenu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopFiveMenu {

    private static List<User> playerList = new ArrayList<>();
    private static List<User> playerListComparison = new ArrayList<>();

    public static void topFivePlayers() throws SQLException {
        clearLists();
        Connection connection = DriverManager.getConnection(MainMenu.url, MainMenu.username, MainMenu.password);
        Statement statement = connection.createStatement();
        getRanksFromDb(statement);
        connection.close();
        showTopFive();
    }

    private static void getRanksFromDb(Statement statement) throws SQLException {
        if (playerList.size() > playerListComparison.size() || playerList.isEmpty()) {
            ResultSet resultSet = statement.executeQuery("SELECT userLogin, killCount, RANK() OVER" +
                    " (ORDER BY killCount DESC) as playerRank from users;");
            while (resultSet.next()) {
                User user = new User();
                user.setUserLogin(resultSet.getString("userLogin"));
                user.setKillCount(resultSet.getInt("killCount"));
                user.setPlayerRank(resultSet.getInt("playerRank"));
                playerList.add(user);
            }
            resultSet.close();
            statement.close();
        }
    }

    private static void showTopFive() {
        for (User user : playerList) {
            int maxTopPlayers = 5;
            if (user.getPlayerRank() <= maxTopPlayers) {
                System.out.println("User " + user.getUserLogin() + " is " + user.getPlayerRank() + " with " + user.getKillCount() + " kills");
            }
        }
    }

    private static void clearLists() {
        playerList.clear();
        playerListComparison.clear();
    }
}

