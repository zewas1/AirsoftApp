package menus.SpecialFeatures;

import LoginSystem.LoginSystem;
import LoginSystem.Utilities.DataRefresh;
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
    public static int selectEvent = 0;

    public static void CreateEvent() throws SQLException {
        do {
            getCreateEventMenu();
            if (MainClass.menuSelection == doCreateEvent) {
                newEventCreation(MainMenu.url, MainMenu.username, MainMenu.password);
            } else if (MainClass.menuSelection == doEditEvent) {
                currentEditingSelection();
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

    private static void newEventCreation(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String sqlString = "INSERT INTO events (isActive) VALUES ('1');";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
    }

    public static void currentEditingSelection() throws SQLException {
        boolean isValidEventSelected = false;
        eventSelectionList();
        try {
            selectEvent = Integer.parseInt(MainClass.scan.next());
            for (Event event : eventList) {
                if (event.getId() == selectEvent) {
                    isValidEventSelected = true;
                    System.out.println("Event " + event.getId() + " selected.");
                    eventEdit();
                }
            }
            if (!isValidEventSelected) {
                System.out.println("Invalid event selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
        selectEvent = 0;
    }

    public static void currentUserSelection() throws SQLException {
        eventSelectionList();
        try {
            selectEvent = Integer.parseInt(MainClass.scan.next());
            for (Event event : eventList) {
                if (event.getId() == selectEvent && event.getIsActive()) {
                    System.out.println("Event " + event.getId() + " selected.");
                    newParticipantUpload(MainMenu.url, MainMenu.username, MainMenu.password);
                    selectEvent = 0;
                }
                else if (event.getId() == selectEvent && !event.getIsActive()){
                    System.out.println("You have selected an inactive or a non-existent event. Please select again.");
                    selectEvent = 0;
                    currentUserSelection();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
    }

    private static void newParticipantUpload (String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String sqlString = "INSERT INTO `event details` (eventID, userLogin, userId) VALUES ('"+ selectEvent +"', '"+ LoginSystem.currentUser +"', '"+ DataRefresh.currentUserId+"');";
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

    private static void eventSelectionList() throws SQLException {
        getEventsFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        System.out.println("Select Event by ID");
        for (Event event : eventList) {
            if (event.getId() > 0 && event.getIsActive()) {
                System.out.println(event.getId() + " - is Active.");
            } else if (event.getId() > 0 && !event.getIsActive()) {
                System.out.println(event.getId() + " - is Inactive.");
            }
        }
    }

    private static void eventEdit() throws SQLException {
        int getEventEditSelection;
        int seeParticipatingPlayers = 1;
        int changeEventStatus = 2;
        int quitEventEditMenu = 3;

        do {
            eventEditMenu();
            getEventEditSelection = Integer.parseInt(MainClass.scan.next());
            if (getEventEditSelection == seeParticipatingPlayers) {
                placeholder();
            } else if (getEventEditSelection == changeEventStatus) {
                placeholder();
            }
        } while (getEventEditSelection != quitEventEditMenu);
    }

    private static void eventEditMenu(){
        System.out.println("1. See participating player list.");
        System.out.println("2. Change event status.");
        System.out.println("3. Exit.");
    }

    private static void placeholder(){

    }
}
