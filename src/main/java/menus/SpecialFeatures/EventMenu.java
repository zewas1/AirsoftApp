package menus.SpecialFeatures;

import Views.Menus.SpecialFeatures.EventMenuView;
import main.MainClass;
import menus.MainMenu;
import LoginSystem.Objects.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventMenu {

    private static final int doCreateEvent = 1;
    private static final int doEditEvent = 2;
    private static final int doExitEventMenu = 3;
    public static List<Event> eventList = new ArrayList<>();

    /**
     * @throws SQLException
     */
    public static void openEventMenu() throws SQLException {
        do {
            EventMenuView.getCreateEventMenu();
            switch (MainClass.menuSelection) {
                case doCreateEvent:
                    createNewEvent();
                    break;
                case doEditEvent:
                    EventEditingMenu.EventEditingSelection();
                    break;
                case doExitEventMenu:
                    break;
                default:
                    System.out.println("No such menu option.");
                    EventMenuView.getCreateEventMenu();
                    break;
            }
        } while (MainClass.menuSelection != doExitEventMenu);
    }

    /**
     * @throws SQLException
     */
    static void createNewEvent() throws SQLException {
        Connection connection = DriverManager.getConnection(MainMenu.url, MainMenu.username, MainMenu.password);
        Statement statement = connection.createStatement();
        String sqlString = "INSERT INTO events (isActive) VALUES ('1');";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
        System.out.println("Event created successfully!");
    }

    public static void eventListCacheClear() {
        eventList.clear();
    }

    /**
     * @param url
     * @param username
     * @param password
     * @throws SQLException
     */
    public static void getEventsFromDb(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM events");
            while (resultSet.next()) {
                Event event = new Event();
                event.setId(resultSet.getInt("id"));
                event.setIsActive(resultSet.getBoolean("isActive"));
                eventList.add(event);
            }
            resultSet.close();
            statement.close();
        connection.close();
    }
}