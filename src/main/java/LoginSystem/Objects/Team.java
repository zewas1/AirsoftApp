package LoginSystem.Objects;

public class Team {

    /**
     * @var int
     */
    private int id;

    /**
     * @var String
     */
    private String teamName;

    /**
     * @var String
     */
    private String teamLead;

    /**
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return String
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @param teamName
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * @return String
     */
    public String getTeamLead() {
        return teamLead;
    }

    /**
     * @param teamLead
     */
    public void setTeamLead(String teamLead) {
        this.teamLead = teamLead;
    }
}
