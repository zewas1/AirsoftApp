package LoginSystem.Objects;

public class User {
    public int userId;
    public int isAdmin;
    public String userLogin;
    public String userPassword;
    public int killCount;
    public int deathCount;
    public int assistCount;
    public int userLevel;
    public int playerRank;

    public static final int regularUserType = 0;
    public static final int adminUserType = 1;

    public static final int regularUserLevel = 0;
    public static final int elevatedUserLevel = 1;

    /**
     * @return playerRank
     */
    public int getPlayerRank() {
        return playerRank;
    }

    public void setPlayerRank(int playerRank) {
        this.playerRank = playerRank;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void setDeathCount(int deathCount) {
        this.deathCount = deathCount;
    }

    public int getAssistCount() {
        return assistCount;
    }

    public void setAssistCount(int assistCount) {
        this.assistCount = assistCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

}