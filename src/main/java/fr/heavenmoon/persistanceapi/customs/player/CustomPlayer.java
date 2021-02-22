package fr.heavenmoon.persistanceapi.customs.player;

import fr.heavenmoon.persistanceapi.customs.guild.Guild;
import fr.heavenmoon.persistanceapi.customs.player.data.CoreData;
import fr.heavenmoon.persistanceapi.customs.player.data.ModerationData;
import fr.heavenmoon.persistanceapi.customs.player.data.RankData;
import fr.heavenmoon.persistanceapi.customs.player.data.RankList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomPlayer
{

    // Define attributs
    private UUID UniqueID;
    private String name;
    private String displayName;
    private boolean premium;
    private boolean azlauncher;
    private boolean canTp;

    private boolean online;
    private int gamemode;
    private String serverName;
    private String proxyName;
    private String passwordHash;

    private int gemmes;
    private int stars;
    private double level;
    private boolean exist;
    private long lastLogin;
    private long firstLogin;
    private String lastIP;
    private long timePlayed;
    private List<String> allAddress;

    private Guild guild;
    private PlayerSettings playerSettings;
    private CoreData coreData;
    private ModerationData moderationData;
    private RankData rankData;

    // Empty constructor
    public CustomPlayer()
    {
        super();
    }

    /**
     * Constructor
     *
     * @param uniqueID       The UniqueID
     * @param name           The name
     * @param displayName    The displayname
     * @param azlauncher     The azlauncher status
     * @param canTp          The canTp status
     * @param online         The onlien status
     * @param gamemode       The gamemode of player
     * @param serverName     The name of player server
     * @param proxyName      The name of player proxy
     * @param passwordHash   The Hashed password
     * @param gemmes         The coins amount
     * @param stars          The money amount
     * @param level          The level of the player
     * @param exist          The exist status
     * @param lastLogin      The last login timestamp
     * @param firstLogin     The first login timestamp
     * @param lastIP         The last ip address of the player
     * @param timePlayed     The total played timeof a player
     * @param allAddress     All the address of player
     * @param guild          The Guild data of player
     * @param playerSettings The settings of the player {@link PlayerSettings}
     * @param coreData       The core data {@link CoreData}
     * @param moderationData The moderation data {@link ModerationData}
     * @param rankData       The rank data {@link RankData}
     */
    public CustomPlayer(UUID uniqueID, String name, String displayName, boolean premium, boolean azlauncher, boolean online, int gamemode,
            boolean canTp, String serverName, String proxyName, String passwordHash, int gemmes, int stars, double level, boolean exist,
            long lastLogin, long firstLogin, String lastIP, long timePlayed, List<String> allAddress, Guild guild, PlayerSettings playerSettings,
            CoreData coreData, ModerationData moderationData, RankData rankData) {
        this.UniqueID = uniqueID;
        this.name = name;
        this.displayName = displayName;
        this.premium = premium;
        this.azlauncher = azlauncher;
        this.canTp = canTp;
        
        this.online = online;
        this.gamemode = gamemode;
        this.serverName = serverName;
        this.proxyName = proxyName;
        this.passwordHash = passwordHash;

        this.gemmes = gemmes;
        this.stars = stars;
        this.level = level;
        this.exist = exist;
        this.lastLogin = lastLogin;
        this.firstLogin = firstLogin;
        this.lastIP = lastIP;
        this.timePlayed = timePlayed;
        this.allAddress = allAddress;

        this.guild = guild;
        this.playerSettings = playerSettings;
        this.coreData = coreData;
        this.moderationData = moderationData;
        this.rankData = rankData;
    }

    /**
     * Constructor only uuid
     *
     * @param uniqueID The uuid of player
     */
    public CustomPlayer(UUID uniqueID) {
        this.UniqueID = uniqueID;
        this.name = "";
        this.displayName = "";
        this.premium = false;
        this.azlauncher = false;
        this.canTp = true;
        
        this.online = false;
        this.gamemode = 0;
        this.serverName = "";
        this.proxyName = "";
        this.passwordHash = "";

        this.gemmes = 0;
        this.stars = 0;
        this.level = 0.0;
        this.exist = false;
        this.lastLogin = 0;
        this.firstLogin = 0;
        this.lastIP = "";
        this.timePlayed = 0;
        this.allAddress = new ArrayList<String>();

        this.guild = new Guild();
        this.playerSettings = new PlayerSettings();
        this.coreData = new CoreData();
        this.moderationData = new ModerationData();
        this.rankData = new RankData();
    }

    /**
     * Method to check if player has permission
     *
     * @param rank The rank of player {@link RankList}
     * @return {@link Boolean}
     */
    public boolean hasPermission(RankList rank) { return hasPermission(rank.getPermission()); }

    /**
     * Method to check if player has permission
     *
     * @param permission The permission of the rank of player
     * @return {@link Boolean}
     */
    public boolean hasPermission(int permission)
    {
        if (rankData.getPermission() >= permission) return true;
        return false;
    }
    
    public boolean hasPermission(String permission)
    {
        if (rankData.getPermissionList().contains(permission)) return true;
        return false;
    }

    /**
     * Method to check if player has a permission in particular
     *
     * @param rank The rank of player {@link RankList}
     * @return {@link Boolean}
     */
    public boolean hasOnlyPermission(RankList rank) { return hasOnlyPermission(rank.getPermission()); }

    /**
     * Method to check if player has permission in particular
     *
     * @param permission The permission of the rank of player
     * @return {@link Boolean}
     */
    public boolean hasOnlyPermission(int permission)
    {
        if (rankData.getPermission() == permission) return true;
        return false;
    }

    // Getters
    public UUID getUniqueID() { return UniqueID; }
    public String getName() { return name; }
    public String getDisplayName() { return displayName; }
    public boolean isPremium() { return premium; }
    public boolean isAzlauncher() { return azlauncher; }
    public boolean canTp() { return canTp; }
    public boolean isOnline() { return online; }
    public int getGamemode() { return gamemode; }
    public String getServerName() { return serverName; }
    public String getProxyName() { return proxyName; }
    public String getPasswordHash() { return passwordHash; }
    public int getGemmes() { return gemmes; }
    public int getStars() { return stars; }
    public double getLevel() { return level; }
    public boolean isExist() { return exist; }
    public long getLastLogin() { return lastLogin; }
    public long getFirstLogin() { return firstLogin; }
    public String getLastIP() { return lastIP; }
    public long getTimePlayed() { return timePlayed; }
    public List<String> getAllAddress() { return allAddress; }
    public Guild getGuild() { return guild; }
    public PlayerSettings getPlayerSettings() { return playerSettings; }
    public CoreData getCoreData() { return coreData; }
    public ModerationData getModerationData() { return moderationData; }
    public RankData getRankData() { return rankData; }

    // Setters
    public void setUniqueID(UUID uniqueID) { UniqueID = uniqueID; }
    public void setName(String name) { this.name = name; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public void setPremium(boolean premium) { this.premium = premium; }
    public void setAzlauncher(boolean azlauncher) { this.azlauncher = azlauncher; }
    public void setCanTp(boolean canTp) { this.canTp = canTp; }
    public void setOnline(boolean online) { this.online = online; }
    public void setGamemode(int gamemode) { this.gamemode = gamemode; }
    public void setServerName(String serverName) { this.serverName = serverName; }
    public void setProxyName(String proxyName) { this.proxyName = proxyName; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setGemmes(int gemmes) { this.gemmes = gemmes; }
    public void setStars(int stars) { this.stars = stars; }
    public void setLevel(double level) { this.level = level; }
    public void setExist(boolean exist) { this.exist = exist; }
    public void setLastLogin(long lastLogin) { this.lastLogin = lastLogin; }
    public void setFirstLogin(long firstLogin) { this.firstLogin = firstLogin; }
    public void setLastIP(String lastIP) { this.lastIP = lastIP; }
    public void setTimePlayed(long timePlayed) { this.timePlayed = timePlayed; }
    public void setAllAddress(List<String> allAddress) { this.allAddress = allAddress; }
    public void setGuild(Guild guild) { this.guild = guild; }
    public void setPlayerSettings(PlayerSettings playerSettings) { this.playerSettings = playerSettings; }
    public void setCoreData(CoreData coreData) { this.coreData = coreData; }
    public void setModerationData(ModerationData moderationData) { this.moderationData = moderationData; }
    public void setRankData(RankData rankData) { this.rankData = rankData; }
}
