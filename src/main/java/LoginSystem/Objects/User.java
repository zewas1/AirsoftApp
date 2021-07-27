package LoginSystem.Objects;

import LoginSystem.Objects.EventDetails;

public class User {

    /**
     * @var int
     */
    private int userId;

    /**
     * @var int
     */
    private int isAdmin;

    /**
     * @var String
     */
    private String userLogin;

    /**
     * @var String
     */
    private String userPassword;

    /**
     * @var int
     */
    private int killCount;

    /**
     * @var int
     */
    private int deathCount;

    /**
     * @var int
     */
    private int assistCount;

    /**
     * @var int
     */
    private int userLevel;

    /**
     * @var int
     */
    private int playerRank;

    /**
     * @var const
     */
    public static final int regularUserType = 0;

    /**
     * @var const
     */
    public static final int adminUserType = 1;

    /**
     * @var const
     */
    public static final int regularUserLevel = 0;

    /**
     * @var const
     */
    public static final int elevatedUserLevel = 1;

    /**
     * @return int
     */
    public int getPlayerRank() {
        return playerRank;
    }

    /**
     * @param playerRank
     */
    public void setPlayerRank(int playerRank) {
        this.playerRank = playerRank;
    }

    /**
     * @return int
     */
    public int getKillCount() {
        return killCount;
    }

    /**
     * @param killCount
     */
    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    /**
     * @return int
     */
    public int getDeathCount() {
        return deathCount;
    }

    /**
     * @param deathCount
     */
    public void setDeathCount(int deathCount) {
        this.deathCount = deathCount;
    }

    /**
     * @return int
     */
    public int getAssistCount() {
        return assistCount;
    }

    /**
     * @param assistCount
     */
    public void setAssistCount(int assistCount) {
        this.assistCount = assistCount;
    }

    /**
     * @return int
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return int
     */
    public int getIsAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin
     */
    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return String
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
     * @return String
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * @return int
     */
    public int getUserLevel() {
        return userLevel;
    }

    /**
     * @param userLevel
     */
    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

}