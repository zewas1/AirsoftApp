package menus.SpecialFeatures;

import LoginSystem.Utilities.DataRefresh;
import menus.MainMenu;

import java.sql.SQLException;

public class TopFiveMenu {

    private static int mostKills;

    public static void topFivePlayers() throws SQLException {
        DataRefresh.getDataFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        // for (User user : LoginSystem.showDataList)

    }
}

