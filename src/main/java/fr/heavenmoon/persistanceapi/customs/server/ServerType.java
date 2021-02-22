package fr.heavenmoon.persistanceapi.customs.server;

public enum ServerType {

    PROXY("PROXY", "Proxy"),
    LOBBY("LOBBY", "Lobby"),
    GAME("GAME", "Game"),
    OTHER("OTHER", "Autre");

    // Define attributs
    private String name;
    private String description;

    /**
     * Constructor
     * @param name        The name of the server type
     * @param description The destription of the server type
     */
    ServerType(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    /**
     * Method to get a server type by name
     *
     * @param name The type name
     * @return {@link ServerType}
     */
    public static ServerType getByName(String name)
    {
        for (ServerType type : values())
        {
            if (type.getName().equals(name)) return type;
        }
        return OTHER;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
}
