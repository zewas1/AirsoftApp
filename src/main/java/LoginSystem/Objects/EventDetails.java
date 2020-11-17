package LoginSystem.Objects;

public class EventDetails {
    public int id;
    public int eventId;
    public String userLogin;
    public int userId;
    public int eventKills;
    public int eventDeaths;
    public int eventAssists;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventKills() {
        return eventKills;
    }

    public void setEventKills(int eventKills) {
        this.eventKills = eventKills;
    }

    public int getEventDeaths() {
        return eventDeaths;
    }

    public void setEventDeaths(int eventDeaths) {
        this.eventDeaths = eventDeaths;
    }

    public int getEventAssists() {
        return eventAssists;
    }

    public void setEventAssists(int eventAssists) {
        this.eventAssists = eventAssists;
    }
}
