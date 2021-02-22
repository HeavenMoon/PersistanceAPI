package fr.heavenmoon.persistanceapi.customs.factions;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionPlayer {

    private UUID uniqueID;
    private String name;
    private boolean exist;
    private boolean godmode;
    private CustomFaction customFaction;
    private List<CustomHome> homes;
    private long dusts;
    private long money;
    
    private boolean online;
    private long firstLogin;
    private long lastLogin;
    private long timePlayed;
    
    public FactionPlayer(UUID uniqueID)
    {
        this.uniqueID = uniqueID;
        this.name = "";
        this.exist = false;
        this.godmode = false;
        this.customFaction = null;
        this.homes = new ArrayList<>();
        this.dusts = 0;
        this.money = 0;
        this.online = false;
        this.firstLogin = 0;
        this.lastLogin = 0;
        this.timePlayed = 0;
    }
    
    public FactionPlayer(String name, UUID uniqueID)
    {
        this.uniqueID = uniqueID;
        this.name = name;
        this.exist = false;
        this.godmode = false;
        this.customFaction = null;
        this.homes = new ArrayList<>();
        this.dusts = 0;
        this.money = 0;
        this.online = false;
        this.firstLogin = 0;
        this.lastLogin = 0;
        this.timePlayed = 0;
    }

    public FactionPlayer(UUID uniqueID, String name, Boolean exist, Boolean godmode, CustomFaction customFaction, List<CustomHome> homes,
            long dusts,
            int money, boolean online, long firstLogin, long lastLogin, long timePlayed)
    {
        this.uniqueID = uniqueID;
        this.name = name;
        this.exist = exist;
        this.godmode = godmode;
        this.customFaction = customFaction;
        this.homes = homes;
        this.dusts = dusts;
        this.money = money;
        this.online = online;
        this.firstLogin = firstLogin;
        this.lastLogin = lastLogin;
        this.timePlayed = timePlayed;
    }

    public UUID getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(UUID uniqueId) {
        this.uniqueID = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isExist() { return exist; }
    
    public void setExist(boolean exist) { this.exist = exist; }
    
    public boolean isGodmode() { return godmode; }
    
    public void setGodmode(boolean godmode) { this.godmode = godmode; }
    
    public CustomFaction getCustomFaction() {
        return customFaction;
    }

    public void setCustomFaction(CustomFaction customFaction) {
        this.customFaction = customFaction;
    }

    public List<CustomHome> getHomes() {
        return homes;
    }

    public void setHomes(List<CustomHome> homes) {
        this.homes = homes;
    }

    public long getDusts() {
        return dusts;
    }

    public void setDusts(long dusts) {
        this.dusts = dusts;
    }

    public boolean hasFaction() {
        return customFaction != null;
    }
    
    public long getMoney() { return money; }
    
    public void setMoney(long money) { this.money = money; }
    
    public boolean isOnline() { return online; }
    
    public void setOnline(boolean online) { this.online = online; }
    
    public long getFirstLogin() { return firstLogin; }
    
    public void setFirstLogin(long firstLogin) { this.firstLogin = firstLogin; }
    
    public long getLastLogin() { return lastLogin; }
    
    public void setLastLogin(long lastLogin) { this.lastLogin = lastLogin; }
    
    public long getTimePlayed() { return timePlayed; }
    
    public void setTimePlayed(long timePlayed) { this.timePlayed = timePlayed; }
    
}
