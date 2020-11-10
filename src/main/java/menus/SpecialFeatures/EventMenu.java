package menus.SpecialFeatures;

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
    public static List<Event> eventListComparison = new ArrayList<>();


    public static void openEventMenu() throws SQLException {
        do {
            getCreateEventMenu();
            if (MainClass.menuSelection == doCreateEvent) {
                createNewEvent(MainMenu.url, MainMenu.username, MainMenu.password);
            } else if (MainClass.menuSelection == doEditEvent) {
                EventEditingMenu.EventEditingSelection();
            }
        } while (MainClass.menuSelection != doExitEventMenu);
    }

    private static void getCreateEventMenu() {
        System.out.println("Please choose one of the available options:");
        System.out.println("1. Create a new event.");
        System.out.println("2. Check created events.");
        System.out.println("3. Exit.");
        try {
            MainClass.menuSelection = Integer.parseInt(MainClass.scan.next());
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
    }

    static void createNewEvent(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String sqlString = "INSERT INTO events (isActive) VALUES ('1');";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
    }


    public static void getEventsFromDb(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        if (eventList.size() > eventListComparison.size() || eventList.isEmpty()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM events");
            while (resultSet.next()) {
                Event event = new Event();
                event.setId(resultSet.getInt("id"));
                event.setIsActive(resultSet.getBoolean("isActive"));
                eventList.add(event);
            }
            resultSet.close();
            statement.close();
        }
        connection.close();
    }
}