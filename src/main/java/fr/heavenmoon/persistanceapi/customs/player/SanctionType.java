package fr.heavenmoon.persistanceapi.customs.player;

public enum SanctionType
{
    KICK("KICK", "Expulsion du server"),
    MUTE("MUTE", "Reduction au silence"),
    BAN("BAN", "Bannissement");

    // Define attributs
    public String name;
    public String displayName;

    /**
     * Constructor
     *
     * @param name        The name (identifier)
     * @param displayName The display name of sanction
     */
    SanctionType(String name, String displayName)
    {
        this.name = name;
        this.displayName = displayName;
    }

    // Getters
    public String getName() { return name; }
    public String getDisplayName() { return displayName; }
}
