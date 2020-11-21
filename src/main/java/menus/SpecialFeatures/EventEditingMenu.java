package menus.SpecialFeatures;

import LoginSystem.LoginSystem;
import LoginSystem.Objects.Event;
import LoginSystem.Objects.EventDetails;
import LoginSystem.Utilities.DataRefresh;
import main.MainClass;
import menus.AdminMenu;
import menus.MainMenu;
import menus.UserMenu;

import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class EventEditingMenu {

    public static String selectEvent = "0";
    public static boolean eventStatusConst = true;
    public static List<EventDetails> eventDetailList = new ArrayList<>();
    public static List<EventDetails> eventDetailListComparison = new ArrayList<>();
    private static final int seeParticipatingPlayers = 1;
    private static final int changeEventStatus = 2;
    private static final int quitEventEditMenu = 3;
    private static final String quitEventSelection = "exit";

    private static void eventEdit() throws SQLException {
        int getEventEditSelection;

        do {
            eventEditMenu();
            getEventEditSelection = Integer.parseInt(MainClass.scan.next());
            switch (getEventEditSelection) {
                case seeParticipatingPlayers:
                    showParticipantList();
                    break;
                case changeEventStatus:
                    eventStatusChange(MainMenu.url, MainMenu.username, MainMenu.password);
                    break;
                default:
                    System.out.println("No such menu option.");
                    eventEditMenu();
                    break;
            }
        } while (getEventEditSelection != quitEventEditMenu);
    }

    private static void clearEventListCache() {
        eventDetailList.clear();
        eventDetailListComparison.clear();
    }

    private static void eventEditMenu() {
        clearEventListCache();
        System.out.println("1. See participating player list.");
        System.out.println("2. Change event status.");
        System.out.println("3. Exit.");
    }

    private static void showParticipantList() throws SQLException {
        uploadParticipantList(MainMenu.url, MainMenu.username, MainMenu.password);
        for (EventDetails event : eventDetailList) {
            System.out.println(event.getUserLogin() + " Participates in this event.");
        }
    }

    private static void uploadParticipantList(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        if (eventDetailList.size() > eventDetailListComparison.size() || eventDetailList.isEmpty()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `event details` WHERE eventID='" + selectEvent + "'");
            while (resultSet.next()) {
                EventDetails event = new EventDetails();
                event.setId(resultSet.getInt("id"));
                event.setEventId(resultSet.getInt("eventID"));
                event.setUserLogin(resultSet.getString("userLogin"));
                event.setUserId(resultSet.getInt("userId"));
                event.setEventKills(resultSet.getInt("eventKills"));
                event.setEventDeaths(resultSet.getInt("eventDeaths"));
                event.setEventAssists(resultSet.getInt("eventAssists"));
                eventDetailList.add(event);
            }
            resultSet.close();
            statement.close();
        }
        connection.close();
    }

    public static void EventEditingSelection() throws SQLException {
        boolean isValidEventSelected = false;
        eventSelectionList();
        try {
            selectEvent = MainClass.scan.next();
            for (Event event : EventMenu.eventList) {
                if (String.valueOf(event.getId()).equals(selectEvent) && AdminMenu.adminInputValidation) {
                    isValidEventSelected = true;
                    System.out.println("Event " + event.getId() + " selected.");
                    if (event.getIsActive()) {
                        eventStatusConst = false;
                    } else if (!event.getIsActive()) {
                        eventStatusConst = true;
                    }
                    eventEdit();
                } else if (String.valueOf(event.getId()).equals(selectEvent) && event.getIsActive() && UserMenu.userInputValidation) {
                    isValidEventSelected = true;
                    System.out.println("Event " + event.getId() + " selected.");
                    newParticipantUpload(MainMenu.url, MainMenu.username, MainMenu.password);
                    selectEvent = "0";
                } else if (selectEvent.equals(quitEventSelection) && AdminMenu.adminInputValidation ||
                        selectEvent.equals(quitEventSelection) && UserMenu.userInputValidation) {
                    isValidEventSelected = true;
                    break;
                } else if (!selectEvent.equals(String.valueOf(event.getId())) && AdminMenu.adminInputValidation ||
                        !selectEvent.equals(String.valueOf(event.getId())) && UserMenu.userInputValidation){
                    isValidEventSelected = false;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
        if (!isValidEventSelected && UserMenu.userInputValidation) {
            invalidEventSelected();
        } else if (!isValidEventSelected && AdminMenu.adminInputValidation) {
            invalidEventSelected();
        }
        selectEvent = "0";
        clearEventListCache();
    }

    private static void invalidEventSelected() throws SQLException {
        System.out.println("You have selected an inactive or a non-existent event. Please select again.");
        selectEvent = "0";
        EventEditingSelection();
    }

    static void eventStatusChange(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String sqlString = "update events set isActive = " + eventStatusConst + " WHERE id =" + selectEvent + ";";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
        System.out.println("Event status changed!");
    }

    private static void newParticipantUpload(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String sqlString = "INSERT INTO `event details` (eventID, userLogin, userId) VALUES ('" + selectEvent + "', '" + LoginSystem.currentUser + "', '" + DataRefresh.currentUserId + "');";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
    }

    private static void eventSelectionList() throws SQLException {
        EventMenu.getEventsFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        System.out.println("Select Event by ID. Type in 'exit' to leave.");
        for (Event event : EventMenu.eventList) {
            if (event.getId() > 0 && event.getIsActive()) {
                System.out.println(event.getId() + " - is Active.");
            } else if (event.getId() > 0 && !event.getIsActive()) {
                System.out.println(event.getId() + " - is Inactive.");
            }
        }
    }
}