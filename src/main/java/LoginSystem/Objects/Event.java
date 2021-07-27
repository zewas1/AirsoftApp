package LoginSystem.Objects;

public class Event {

    /**
     * @var int
     */
    private int id;

    /**
     * @var boolean
     */
    private boolean isActive;

    /**
     * @return bool isActive
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * @param active
     */
    public void setIsActive(boolean active) {
        isActive = active;
    }

    /**
     * @return int id
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

}
