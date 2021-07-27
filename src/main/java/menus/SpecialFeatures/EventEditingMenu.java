package menus.SpecialFeatures;

import LoginSystem.LoginSystem;
import LoginSystem.Objects.Event;
import LoginSystem.Objects.EventDetails;
import LoginSystem.Objects.User;
import LoginSystem.Utilities.DataRefresh;
import Views.Menus.SpecialFeatures.EventEditingMenuView;
import main.MainClass;
import menus.AdminMenu;
import menus.MainMenu;
import menus.UserMenu;
import org.jetbrains.annotations.NotNull;

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

    /**
     * @throws SQLException
     */
    private static void eventEdit() throws SQLException {
        int getEventEditSelection;

        do {
            EventEditingMenuView.eventEditMenu();
            getEventEditSelection = Integer.parseInt(MainClass.scan.next());
            switch (getEventEditSelection) {
                case seeParticipatingPlayers:
                    getParticipantsList();
                    break;
                case changeEventStatus:
                    eventStatusChange();
                    break;
                case quitEventEditMenu:
                    break;
                default:
                    System.out.println("No such menu option.");
                    EventEditingMenuView.eventEditMenu();
                    break;
            }
        } while (getEventEditSelection != quitEventEditMenu);
    }

    private static void clearEventListCache() {
        eventDetailList.clear();
        eventDetailListComparison.clear();
    }

    /**
     * @throws SQLException
     */
    private static void getParticipantsList() throws SQLException {
        showParticipantList = true;
        uploadParticipantList();
        for (EventDetails event : eventDetailList) {
            System.out.println(event.getUserLogin() + " Participates in this event.");
        }
        showParticipantList = false;
    }

    /**
     * @throws SQLException
     */
    private static void uploadParticipantList() throws SQLException {
        Connection connection = DriverManager.getConnection(MainMenu.url, MainMenu.username, MainMenu.password);
        Statement statement = connection.createStatement();
        if (eventDetailList.size() > eventDetailListComparison.size() || eventDetailList.isEmpty()) {
            validateEventEditingScenario(statement);
            while (resultSet.next()) {
                EventDetails event = new EventDetails();
                event.setId(resultSet.getInt("id"));
                event.setEvent((Event) resultSet.getObject("eventId"));
                event.setUserLogin(resultSet.getString("userLogin"));
                event.setUser((User) resultSet.getObject("userId"));
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

    /**
     * @throws SQLException
     */
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

    /**
     *
     * @param isValidEventSelected
     * @param event
     * @return boolean isValidEventSelected
     * @throws SQLException
     */
    private static boolean userTypeValidation(boolean isValidEventSelected, @NotNull Event event) throws SQLException {
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

    /**
     * @param event
     * @return bool
     * @throws SQLException
     */
    private static boolean duplicateEntryValidation(Event event) throws SQLException {
        duplicateEntry = true;
        uploadParticipantList();
        for (EventDetails details : eventDetailList) {
            if (details.getEvent().getId() == selectEvent && details.getUser().getUserId() == DataRefresh.currentUserId) {
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

        return true;
    }

    /**
     * @throws SQLException
     */
    private static void eventEditValidation(@org.jetbrains.annotations.NotNull Event event) throws SQLException {
        System.out.println("Event " + event.getId() + " selected.");
        if (event.getIsActive()) {
            eventStatusConst = false;
        } else if (!event.getIsActive()) {
            eventStatusConst = true;
        }
        eventEdit();
    }

    /**
     * @throws SQLException
     */
    static void eventStatusChange() throws SQLException {
        Connection connection = DriverManager.getConnection(MainMenu.url, MainMenu.username, MainMenu.password);
        Statement statement = connection.createStatement();
        String sqlString = "update events set isActive = " + eventStatusConst + " WHERE id =" + selectEvent + ";";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
        System.out.println("Event status changed!");
    }

    /**
     * @throws SQLException
     */
    private static void newParticipantUpload() throws SQLException {
        Connection connection = DriverManager.getConnection(MainMenu.url, MainMenu.username, MainMenu.password);
        Statement statement = connection.createStatement();
        String sqlString = "INSERT INTO `event details` (eventID, userLogin, userId) VALUES ('" + selectEvent + "', '"
                + LoginSystem.currentUser + "', '" + DataRefresh.currentUserId + "');";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
    }

    /**
     * @throws SQLException
     */
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

    /**
     * @param statement - Sql statement
     * @throws SQLException
     */
    private static void validateEventEditingScenario(Statement statement) throws SQLException {
        if (showParticipantList) {
            resultSet = statement.executeQuery("SELECT * FROM `event details` WHERE eventID='" + selectEvent + "'");
        } else if (duplicateEntry) {
            resultSet = statement.executeQuery("select * from `event details`");
        }
    }
}