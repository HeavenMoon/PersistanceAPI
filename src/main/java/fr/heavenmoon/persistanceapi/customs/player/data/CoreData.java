package fr.heavenmoon.persistanceapi.customs.player.data;

public class CoreData
{

    // Define
    private boolean a2f;
    private boolean registered;
    private boolean authentified;

    /**
     * Default constructor
     */
    public CoreData() {
        super();
        this.a2f = false;
        this.registered = false;
        this.authentified = false;
    }

    /**
     * Constructor
     * @param a2f The a2f status
     * @param registered The registered status
     * @param authentified The authentified status
     */
    public CoreData( boolean a2f, boolean registered, boolean authentified) {
        this.a2f = a2f;
        this.registered = registered;
        this.authentified = authentified;
    }

    // Getters
    public boolean isA2f() { return a2f; }
    public boolean isRegistered() { return registered; }
    public boolean isAuthentified() { return authentified; }

    // Setters
    public void setA2f(boolean a2f) { this.a2f = a2f; }
    public void setRegistered(boolean registered) { this.registered = registered; }
    public void setAuthentified(boolean authentified) { this.authentified = authentified; }
}
