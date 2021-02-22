package fr.heavenmoon.persistanceapi.customs.player.data;

public enum RankList
{

    EXPLORATEUR("Explorateur", '7',  "Explorateur ", "", 'f', "L", 0),
    ASTRONAUTE("Astronaute", '9', "Astronaute ", "", 'f', "K", 10),
    CONQUERANT("Conquérant", 'e',  "Conquérant ", "", 'f',"J", 20),
    SELENITE("Sélénite", 'd', "Sélénite ", "", 'f', "I", 30),
    HEAVEN("Heaven", '5', "Heaven ", "", 'f', "H", 40),
    REDACTEUR("Rédacteur", '2', "Rédacteur ", "", 'f', "G", 50),
    GRAPHISTE("Graphiste", '2', "Graphiste ", "", 'f', "F", 50),
    BUILDER("Builder", '2', "Builder ", "", 'f', "E", 50),
    GUIDE("Guide", 'a', "Guide ", "", 'f', "D", 60),
    MODERATEUR("Modérateur", 'b', "Modérateur ", "", 'f', "C", 70),
    SUPERMODO("SuperModo",'6', "SuperModo ", "", 'f', "B", 80),
    ADMINISTRATEUR("Administrateur", 'c', "Admin ", "", 'f',  "A", 90);

    private String name;
    private char styleCode;
    private String prefix;
    private String suffix;
    private char chatStyleCode;
    private String order;
    private int permission;

    /**
     * Constructor
     *
     * @param name           The rank name
     * @param styleCode      The rank style code
     * @param prefix         The rank prefix
     * @param suffix         The rank suffix
     * @param chatStyleCode  The rank chat style code
     * @param order          The order in tablist
     * @param permission     The permission int
     * @param permissionList The permission list
     */
    RankList(String name, char styleCode, String prefix, String suffix, char chatStyleCode, String order, int permission)
    {
        this.name = name;
        this.styleCode = styleCode;
        this.prefix = prefix;
        this.suffix = suffix;
        this.chatStyleCode = chatStyleCode;
        this.order = order;
        this.permission = permission;
    }

    /**
     * Method to get a RankList by the rank name
     *
     * @param rankName The rank name
     * @return {@link RankList}
     */
    public static RankList getByName(String rankName)
    {
        for (RankList rankList : values())
        {
            if (rankList.getName().equalsIgnoreCase(rankName)) return rankList;
        }
        return EXPLORATEUR;
    }

    /**
     * Method to get a RankList by the rank permission
     *
     * @param permission The rank permission
     * @return {@link RankList}
     */
    public static RankList getByPermission(int permission)
    {
        for (RankList rankList : values())
        {
            if (rankList.getPermission() == permission) return rankList;
        }
        return EXPLORATEUR;
    }

    // Getters
    public String getName() { return name; }
    public char getStyleCode() { return styleCode; }
    public String getPrefix() { return prefix; }
    public String getSuffix() { return suffix; }
    public char getChatStyleCode() { return chatStyleCode; }
    public String getOrder() { return order; }
    public int getPermission() { return permission; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setStyleCode(char styleCode) { this.styleCode = styleCode; }
    public void setPrefix(String prefix) { this.prefix = prefix; }
    public void setSuffix(String suffix) { this.suffix = suffix; }
    public void setChatStyleCode(char chatStyleCode) { this.chatStyleCode = chatStyleCode; }
    public void setOrder(String order) { this.order = order; }
    public void setPermission(int permission) { this.permission = permission; }
}
