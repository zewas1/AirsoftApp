package LoginSystem.Objects;

public class EventDetails {

    /**
     * @var int
     */
    private int id;

    /**
     * @var Event
     */
    private Event event;

    /**
     * @var String
     */
    private String userLogin;

    /**
     * @var User
     */
    private User user;

    /**
     * @var int
     */
    private int eventKills;

    /**
     * @var int
     */
    private int eventDeaths;

    /**
     * @var int
     */
    private int eventAssists;

    /**
     * @var int
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Event eventId
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @param event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * @return User user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return String userLogin
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * @param userLogin
     */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * @return int eventKills
     */
    public int getEventKills() {
        return eventKills;
    }

    /**
     * @param eventKills
     */
    public void setEventKills(int eventKills) {
        this.eventKills = eventKills;
    }

    /**
     * @return int eventDeaths
     */
    public int getEventDeaths() {
        return eventDeaths;
    }

    /**
     * @param eventDeaths
     */
    public void setEventDeaths(int eventDeaths) {
        this.eventDeaths = eventDeaths;
    }

    /**
     * @return int eventAssists
     */
    public int getEventAssists() {
        return eventAssists;
    }

    /**
     * @param eventAssists
     */
    public void setEventAssists(int eventAssists) {
        this.eventAssists = eventAssists;
    }
}
