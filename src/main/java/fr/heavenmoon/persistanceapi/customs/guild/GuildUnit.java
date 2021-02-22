package fr.heavenmoon.persistanceapi.customs.guild;



public enum GuildUnit {

    CONFRERY("Confrérie des marchands", 'e', "Marchand", false),
    REBEL("Alliance rebel", '2', "Rebel", true),
    ARMY("Armée lunaire", 'c', "Armée", true),
    Aucune("Aucune", '7', "Aucune", true);

    private String name;
    private char color;
    private String prefix;
    private boolean canCreateFaction;

    GuildUnit(String name, char color, String prefix, boolean canCreateFaction) {
        this.name = name;
        this.color = color;
        this.prefix = prefix;
        this.canCreateFaction = canCreateFaction;
    }

    public static GuildUnit getByName(String name) {
        for (GuildUnit guildUnit : values()) {
            if (guildUnit.getName().equalsIgnoreCase(name))
                return guildUnit;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public char getColor()
    {
        return color;
    }
    
    public String getPrefix() {
        return prefix;
    }


    public boolean isCanCreateFaction() {
        return canCreateFaction;
    }


}
