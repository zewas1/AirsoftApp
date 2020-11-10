package menus.SpecialFeatures;

import LoginSystem.LoginSystem;
import LoginSystem.Objects.Event;
import LoginSystem.Objects.EventDetails;
import LoginSystem.Objects.User;
import LoginSystem.Utilities.DataRefresh;
import main.MainClass;
import menus.AdminMenu;
import menus.MainMenu;
import menus.UserMenu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventEditingMenu {

    public static int selectEvent = 0;
    public static boolean eventStatusValidation = false;
    public static int eventStatusConst = 1;
    public static List<EventDetails> eventDetailList = new ArrayList<>();
    public static List<EventDetails> eventDetailListComparison = new ArrayList<>();

    private static void eventEdit() throws SQLException {
        int getEventEditSelection;
        int seeParticipatingPlayers = 1;
        int changeEventStatus = 2;
        int quitEventEditMenu = 3;

        do {
            eventEditMenu();
            getEventEditSelection = Integer.parseInt(MainClass.scan.next());
            if (getEventEditSelection == seeParticipatingPlayers) {
                showParticipantList();
                eventDetailList.clear();
                eventDetailListComparison.clear();
            } else if (getEventEditSelection == changeEventStatus) {
                changeEventStatus();
            }
        } while (getEventEditSelection != quitEventEditMenu);
    }

    private static void eventEditMenu(){
        System.out.println("1. See participating player list.");
        System.out.println("2. Change event status.");
        System.out.println("3. Exit.");
    }

    private static void showParticipantList () throws SQLException {
        uploadParticipantList(MainMenu.url, MainMenu.username, MainMenu.password);
        for (EventDetails event : eventDetailList){
            System.out.println(event.getUserLogin() + " Participates in this event.");
        }
    }

    private static void uploadParticipantList(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        if (eventDetailList.size() > eventDetailListComparison.size() || eventDetailList.isEmpty()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `event details` WHERE eventID='" + selectEvent+"'");
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

    private static void changeEventStatus() throws SQLException {
        EventMenu.getEventsFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        eventStatusValidation = true;
        EventEditingSelection();
    }

    public static void EventEditingSelection() throws SQLException {
        boolean isValidEventSelected = false;
        eventSelectionList();
        try {
            selectEvent = Integer.parseInt(MainClass.scan.next());
            for (Event event : EventMenu.eventList) {
                if (event.getId() == selectEvent && AdminMenu.adminInputValidation) {
                    isValidEventSelected = true;
                    System.out.println("Event " + event.getId() + " selected.");
                    eventEdit();
                } else if (event.getId() == selectEvent && event.getIsActive() && UserMenu.userInputValidation){
                    isValidEventSelected = true;
                    System.out.println("Event " + event.getId() + " selected.");
                    newParticipantUpload(MainMenu.url, MainMenu.username, MainMenu.password);
                    selectEvent = 0;
                } else if (event.getId() == selectEvent && eventStatusValidation){
                    if (event.getIsActive()){
                        eventStatusConst = 0;
                        eventStatusChange(MainMenu.url, MainMenu.username, MainMenu.password);
                    } else if (!event.getIsActive()){
                        eventStatusConst = 1;
                        eventStatusChange(MainMenu.url, MainMenu.username, MainMenu.password);
                    }
                }
            }
            if (!isValidEventSelected && UserMenu.userInputValidation){
                System.out.println("You have selected an inactive or a non-existent event. Please select again.");
                selectEvent = 0;
                EventEditingSelection();
            } else if (!isValidEventSelected && AdminMenu.adminInputValidation) {
                System.out.println("Invalid event selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Only numbers are allowed.");
        }
        selectEvent = 0;
    }

    static void eventStatusChange(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String sqlString = "UPDATE `events` SET isActive = '" + EventEditingMenu.eventStatusConst +"' WHERE id = '"+selectEvent+"');";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
        System.out.println("Event status changed!");
    }

    private static void newParticipantUpload (String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        String sqlString = "INSERT INTO `event details` (eventID, userLogin, userId) VALUES ('"+ selectEvent +"', '"+ LoginSystem.currentUser +"', '"+ DataRefresh.currentUserId+"');";
        statement.executeUpdate(sqlString);
        statement.close();
        connection.close();
    }

    private static void eventSelectionList() throws SQLException {
        EventMenu.getEventsFromDb(MainMenu.url, MainMenu.username, MainMenu.password);
        System.out.println("Select Event by ID");
        for (Event event : EventMenu.eventList) {
            if (event.getId() > 0 && event.getIsActive()) {
                System.out.println(event.getId() + " - is Active.");
            } else if (event.getId() > 0 && !event.getIsActive()) {
                System.out.println(event.getId() + " - is Inactive.");
            }
        }
    }
}
