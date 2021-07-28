package menus.SpecialFeatures;

import LoginSystem.Objects.User;
import menus.MainMenu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopFiveMenu {

    private static List<User> playerList = new ArrayList<>();
    private static final int maxTopPlayers = 5;

    /**
     * @throws SQLException
     */
    public static void topFivePlayers() throws SQLException {
        clearLists();
        Connection connection = DriverManager.getConnection(MainMenu.url, MainMenu.username, MainMenu.password);
        Statement statement = connection.createStatement();
        getRanksFromDb(statement);
        connection.close();
        getTopFivePlayers();
    }

    /**
     * @param statement
     * @throws SQLException
     */
    private static void getRanksFromDb(Statement statement) throws SQLException {
            ResultSet resultSet = statement.executeQuery("SELECT userLogin, killCount, RANK() OVER" +
                    " (ORDER BY killCount DESC) as playerRank from users LIMIT " + maxTopPlayers+ ";");
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

    private static void getTopFivePlayers() {
        for (User user : playerList) {
                System.out.println("User " + user.getUserLogin() + " is " + user.getPlayerRank() + " with " + user.getKillCount() + " kills");
        }
    }

    private static void clearLists() {
        playerList.clear();
    }
}
