package fr.heavenmoon.persistanceapi.customs.player.data;

import java.util.UUID;

public class ModerationData
{

    private boolean bypass;
    private boolean enable;
    private boolean alerts;
    private boolean tools;
    private boolean vanish;
    private boolean speed;
    private boolean fly;
    private boolean freeze;
    private String currentSanctionId;

    public ModerationData()
    {
        super();
        this.bypass = false;
        this.enable = false;
        this.alerts = false;
        this.tools = false;
        this.vanish = false;
        this.speed = false;
        this.fly = false;
        this.freeze = false;
        this.currentSanctionId = "null";
    }

    /**
     * Constructur
     *
     * @param bypass            The bypass status
     * @param enable            The enable status
     * @param alerts            The alerts status
     * @param tools             The tools status
     * @param vanish            The vanish status
     * @param speed             The speed status
     * @param fly               The fly status
     * @param freeze            The freeze status
     * @param currentSanctionId The current sanction id
     */
    public ModerationData(boolean bypass, boolean enable, boolean alerts, boolean tools, boolean vanish, boolean speed, boolean fly,
            boolean freeze, String currentSanctionId)
    {
        this.bypass = bypass;
        this.enable = enable;
        this.alerts = alerts;
        this.tools = tools;
        this.vanish = vanish;
        this.speed = speed;
        this.fly = fly;
        this.freeze = freeze;
        this.currentSanctionId = currentSanctionId;
    }

    // Getters
    public boolean isBypass() { return bypass; }
    public boolean isEnable() { return enable; }
    public boolean isAlerts() { return alerts; }
    public boolean isTools() { return tools; }
    public boolean isVanish() { return vanish; }
    public boolean isSpeed() { return speed; }
    public boolean isFly() { return fly; }
    public boolean isFreeze() { return freeze; }
    public String getCurrentSanctionId() { return currentSanctionId; }

    // Setters
    public void setBypass(boolean bypass) { this.bypass = bypass; }
    public void setEnable(boolean enable) { this.enable = enable; }
    public void setAlerts(boolean alerts) { this.alerts = alerts; }
    public void setTools(boolean tools) { this.tools = tools; }
    public void setVanish(boolean vanish) { this.vanish = vanish; }
    public void setSpeed(boolean speed) { this.speed = speed; }
    public void setFly(boolean fly) { this.fly = fly; }
    public void setFreeze(boolean freeze) { this.freeze = freeze; }
    public void setCurrentSanctionId(String currentSanctionId) { this.currentSanctionId = currentSanctionId; }
}
