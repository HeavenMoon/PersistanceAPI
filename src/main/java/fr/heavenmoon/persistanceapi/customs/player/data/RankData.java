package fr.heavenmoon.persistanceapi.customs.player.data;

import java.util.ArrayList;
import java.util.List;

public class RankData
{

    // Define attributs
    private RankList rank;
    private int permission;
    private char styleCode;
    private String prefix;
    private String suffix;
    private char chatStyleCode;
    private String order;
    private List<String> permissionList;

    /**
     * Contructor
     *
     * @param rank           The Rank
     * @param permission     The permission
     * @param styleCode      The style code
     * @param prefix         The prefix
     * @param suffix         The suffix
     * @param chatStyleCode  The chat style code
     * @param order          The order in tab
     * @param permissionList The permission list
     */
    public RankData(RankList rank, int permission, char styleCode, String prefix, String suffix, char chatStyleCode, String order,
            List<String> permissionList) {
        this.rank = rank;
        this.permission = permission;
        this.styleCode = styleCode;
        this.prefix = prefix;
        this.suffix = suffix;
        this.chatStyleCode = chatStyleCode;
        this.order = order;
        this.permissionList = permissionList;
    }

    /**
     * Constructor empty
     */
    public RankData() {
        this.rank = RankList.EXPLORATEUR;
        this.permission = RankList.EXPLORATEUR.getPermission();
        this.styleCode = RankList.EXPLORATEUR.getStyleCode();
        this.prefix = RankList.EXPLORATEUR.getPrefix();
        this.suffix = RankList.EXPLORATEUR.getSuffix();
        this.chatStyleCode = RankList.EXPLORATEUR.getChatStyleCode();
        this.order = RankList.EXPLORATEUR.getOrder();
        this.permissionList = new ArrayList<>();
    }

    // Getters
    public RankList getRank() { return rank; }
    public int getPermission() { return permission; }
    public char getStyleCode() { return styleCode; }
    public String getPrefix() { return prefix; }
    public String getSuffix() { return suffix; }
    public char getChatStyleCode() { return chatStyleCode; }
    public String getOrder() { return order; }
    public List<String> getPermissionList() { return permissionList; }

    // Setters
    public void setRank(RankList rank) { this.rank = rank; }
    public void setPermission(int permission) { this.permission = permission; }
    public void setStyleCode(char styleCode) { this.styleCode = styleCode; }
    public void setPrefix(String prefix) { this.prefix = prefix; }
    public void setSuffix(String suffix) { this.suffix = suffix; }
    public void setChatStyleCode(char chatStyleCode) { this.chatStyleCode = chatStyleCode; }
    public void setOrder(String order) { this.order = order; }
    public void setPermissionList(List<String> permissionList) { this.permissionList = permissionList; }
}
