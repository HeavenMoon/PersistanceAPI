package fr.heavenmoon.persistanceapi.customs.server;

public class CustomServer
{
    // Define attributs
    private String name;
    private String host;
    private ServerType type;
    private ServerStatus status;
    private ServerWhitelist whitelist;
    private int online;

    /**
     * Constructor
     *
     * @param name      The name of the server
     * @param host      The host address of the server
     * @param type      The server type {@link ServerType}
     * @param status    The server status {@link ServerStatus}
     * @param whitelist The whitelist of the server {@link ServerWhitelist}
     * @param online    The amount of online player
     */
    public CustomServer(String name, String host, ServerType type, ServerStatus status, ServerWhitelist whitelist, int online)
    {
        this.name = name;
        this.host = host;
        this.type = type;
        this.status = status;
        this.whitelist = whitelist;
        this.online = online;
    }

    // Getters
    public String getName() { return name; }
    public String getHost() { return host; }
    public ServerType getType() { return type; }
    public ServerStatus getStatus() { return status; }
    public ServerWhitelist getWhitelist() { return whitelist; }
    public int getOnline() { return online; }

    //Setters
    public void setName(String name) { this.name = name; }
    public void setHost(String host) { this.host = host; }
    public void setType(ServerType type) { this.type = type; }
    public void setStatus(ServerStatus status) { this.status = status; }
    public void setWhitelist(ServerWhitelist whitelist) { this.whitelist = whitelist; }
    public void setOnline(int online) { this.online = online; }
    public void addOnline(int amount) { this.online = this.online + amount; }
    public void removeOnline(int amount) { this.online = this.online - amount; }
}
