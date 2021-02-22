package fr.heavenmoon.persistanceapi.customs.server;

import fr.heavenmoon.persistanceapi.customs.player.data.RankList;

public enum ServerWhitelist {

    NONE("NONE", "", RankList.EXPLORATEUR),
    VIP("VIP", "§cCe serveur est réservé aux §9Conquérant §cet plus !", RankList.CONQUERANT),
    STAFF("STAFF", "§cCe serveur est réservé au staff !", RankList.GRAPHISTE),
    MAINTENANCE("MAINTENANCE", "§CMaintenance en cours. plus d'info sur §e§ndiscord.gg/heavenmoon", RankList.SUPERMODO),
    DEVELOPMENT("DEVELOPMENT", "§cPas touche ! Serveur en développement. ", RankList.GUIDE);

    // Define attributs
    private String name;
    private String description;
    private RankList rank;

    /**
     * Constructor
     *
     * @param name        The name of the whitelist
     * @param description The path to the description of the whitelist
     * @param rank        The rank required to join
     */
    ServerWhitelist(String name, String description, RankList rank)
    {
        this.name = name;
        this.description = description;
        this.rank = rank;
    }

    /**
     * Method to get a ServerWhitelist by name
     *
     * @param whitelistName The whitelist name
     * @return {@link ServerWhitelist}
     */
    public static ServerWhitelist getByName(String whitelistName)
    {
        for (ServerWhitelist whitelist : values())
        {
            if (whitelist.getName().equals(whitelistName)) return whitelist;
        }
        return NONE;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public RankList getRank() { return rank; }
}
