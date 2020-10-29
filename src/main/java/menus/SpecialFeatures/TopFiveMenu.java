package menus.SpecialFeatures;

import LoginSystem.LoginSystem;
import menus.MainMenu;
import LoginSystem.User;

import java.sql.SQLException;

public class TopFiveMenu {

    private static int mostKills;

    public static void topFivePlayers() throws SQLException {
        LoginSystem.getDataFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        // for (User user : LoginSystem.showDataList)

    }
}

