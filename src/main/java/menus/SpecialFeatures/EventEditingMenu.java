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
import java.util.ArrayList;
import java.util.List;

public class EventEditingMenu {

    public static int selectEvent;
    public static boolean eventStatusConst = true;

    public static List<EventDetails> eventDetailList = new ArrayList<>();
    public static List<EventDetails> eventDetailListComparison = new ArrayList<>();

    private static final int seeParticipatingPlayers = 1;
    private static final int changeEventStatus = 2;
    private static final int quitEventEditMenu = 3;
    private static final int quitEventSelection = 0;

    private static boolean showParticipantList = false;
    private static boolean duplicateEntry = false;

    private static ResultSet resultSet;

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
                    eventStatusChange();
                    break;
                case quitEventEditMenu:
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
        showParticipantList = true;
        uploadParticipantList();
        for (EventDetails event : eventDetailList) {
            System.out.println(event.getUserLogin() + " Participates in this event.");
        }
        showParticipantList = false;
    }

    private static void uploadParticipantList() throws SQLException {
        Connection connection = DriverManager.getConnection(MainMenu.url, MainMenu.username, MainMenu.password);
        Statement statement = connection.createStatement();
        if (eventDetailList.size() > eventDetailListComparison.size() || eventDetailList.isEmpty()) {
            validateEventEditingScenario(statement);
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
            selectEvent = Integer.parseInt(MainClass.scan.next());
            for (Event event : EventMenu.eventList) {
                isValidEventSelected = userTypeValidation(isValidEventSelected, event);
                if (selectEvent == quitEventSelection) {
                    System.out.println("You have left the menu.");
                    isValidEventSelected = true;
                    break;
                }
            }
            if (!isValidEventSelected && UserMenu.userInputValidation) {
                System.out.println("You have selected an inactive or a non-existent event. Please select again.");
                selectEvent = 0;
                EventMenu.eventListCacheClear();
                EventEditingSelection();
            } else if (!isValidEventSelected && AdminMenu.adminInputValidation) {
                System.out.println("Invalid event selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
        eventEditingSelectionClose();
    }

    private static boolean userTypeValidation(boolean isValidEventSelected, Event event) throws SQLException {
        if (selectEvent == event.getId() && AdminMenu.adminInputValidation) {
            isValidEventSelected = true;
            eventEditValidation(event);
        } else if (selectEvent == event.getId() && event.getIsActive() && UserMenu.userInputValidation ||
                event.getId() == selectEvent && event.getIsActive() && AdminMenu.adminInputValidation) {
            isValidEventSelected = duplicateEntryValidation(event);
        }
        return isValidEventSelected;
    }

    private static void eventEditingSelectionClose() {
        duplicateEntry = false;
        selectEvent = 0;
        clearEventListCache();
    }

    private static boolean duplicateEntryValidation(Event event) throws SQLException {
        boolean isValidEventSelected;
        isValidEventSelected = true;
        duplicateEntry = true;
        uploadParticipantList();
        for (EventDetails details : eventDetailList) {
            if (details.eventId == selectEvent && details.userId == DataRefresh.currentUserId) {
                duplicateEntry = false;
                break;
            }
        }
        if (duplicateEntry) {
            System.out.println("Event " + event.getId() + " selected.");
            newParticipantUpload();
        } else {
            System.out.println("You have already joined this event!");
        }
        return isValidEventSelected;
    }

    private static void eventEditValidation(Event event) throws SQLException {
        System.out.println("Event " + event.getId() + " selected.");
        if (event.getIsActive()) {
            eventStatusConst = false;
        } else if (!event.getIsActive()) {
            eventStatusConst = true;
        }
        eventEdit();
    }

    static void eventStatusChange() throws SQLException {
        Connection connection = DriverManager.getConnection(MainMenu.url, MainMenu.username, MainMenu.password);
        Statement statement = connection.createStatement();
        String sqlString = "update events set isActive = " + eventStatusConst + " WHERE id =" + selectEvent + ";";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
        System.out.println("Event status changed!");
    }

    private static void newParticipantUpload() throws SQLException {
        Connection connection = DriverManager.getConnection(MainMenu.url, MainMenu.username, MainMenu.password);
        Statement statement = connection.createStatement();
        String sqlString = "INSERT INTO `event details` (eventID, userLogin, userId) VALUES ('" + selectEvent + "', '"
                + LoginSystem.currentUser + "', '" + DataRefresh.currentUserId + "');";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
    }

    private static void eventSelectionList() throws SQLException {
        EventMenu.getEventsFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        System.out.println("Select Event by ID. Type in '0' to leave.");
        for (Event event : EventMenu.eventList) {
            if (event.getId() > 0 && event.getIsActive()) {
                System.out.println(event.getId() + " - is Active.");
            } else if (event.getId() > 0 && !event.getIsActive()) {
                System.out.println(event.getId() + " - is Inactive.");
            }
        }
    }

    private static void validateEventEditingScenario(Statement statement) throws SQLException {
        if (showParticipantList) {
            resultSet = statement.executeQuery("SELECT * FROM `event details` WHERE eventID='" + selectEvent + "'");
        } else if (duplicateEntry) {
            resultSet = statement.executeQuery("select * from `event details`");
        }
    }
}