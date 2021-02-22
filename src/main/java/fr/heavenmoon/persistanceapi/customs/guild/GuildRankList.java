package fr.heavenmoon.persistanceapi.customs.guild;

import java.util.Arrays;

public enum GuildRankList {

    MENTOR("Mentor", 'e', " [Mentor]", 9),
    COLLECTIONNEUR("Collectionneur", 'e', " [Collectionneur]", 8),
    TRESORIEER("Trésorier", 'e', " [Trésorier]", 7),
    SPECIALISTE("Spécialiste", 'e', " [Spécialiste]", 6),
    EXPERT("Expert", 'e', " [Expert]", 5),
    NEGOCIANT("Négociant", 'e', " [Negociant]", 4),
    ARTISAN("Artisan", 'e', " [Artisan]", 3),
    MARCHAND("Marchand", 'e', " [Marchand]", 2),
    APPRENTIS("Apprentis", 'e', " [Apprentis]", 1),

    LEGENDE("Légende",  '2', " [Légende]", 8),
    HEROS("Héros",  '2', " [Héros]", 7),
    MAJOR("Major",  '2', " [Major]", 6),
    COMMANDORE("Commandore",  '2', " [Commandore]", 5),
    MAITRE("Maître",  '2', " [Maître]", 4),
    RENEGAT("Renégat",  '2', " [Rénégat]", 3),
    ECLAIREUR("Eclaireur",  '2', " [Eclaireur]", 2),
    REBEL("Rebel",  '2', " [Rebel]", 1),

    GENERAL("Général",  'c', " [Général]", 9),
    COLONEL("Colonel",  'c', " [Colonel]", 8),
    COMMANDANT("Commandant",  'c', " [Commandant]", 7),
    CAPITAINE("Capitaine",  'c', " [Capitaine]", 6),
    LIEUTENANT("Lieutenant",  'c', " [Lieutenant]", 5),
    ASPIRANT("Aspirant",  'c', " [Aspirant]", 4),
    SERGENT("Sergent",  'c', " [Sergent]", 3),
    CAPORAL("Caporal",  'c', " [Caporal]", 2),
    SOLDAT("Soldat",  'c', " [Soldat]", 1),

    AUCUN("Aucun", '7', "", -1);


    private String name;
    private char styleCode;
    private String title;
    private int power;

    GuildRankList(String name, char styleCode, String title, int power) {
        this.name = name;
        this.styleCode = styleCode;
        this.title = title;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public char getStyle() {
        return styleCode;
    }

    public String getTitle() {
        return title;
    }

    public int getPower() {
        return power;
    }

    public static GuildRankList getByName(String name) {
        return Arrays.stream(values()).filter(r -> r.getName().equalsIgnoreCase(name)).findAny().orElse(GuildRankList.AUCUN);
    }

}
