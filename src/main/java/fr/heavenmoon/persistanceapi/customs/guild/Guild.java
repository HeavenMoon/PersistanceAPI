package fr.heavenmoon.persistanceapi.customs.guild;

public class Guild {

    private GuildUnit guild;
    private GuildRankList rank;

    public Guild(GuildUnit guild, GuildRankList rank) {
        this.guild = guild;
        this.rank = rank;
    }

    public Guild() {
        this.guild = GuildUnit.Aucune;
        this.rank = GuildRankList.AUCUN;
    }

    public GuildUnit getGuild() {
        return guild;
    }

    public void setGuild(GuildUnit guild) {
        this.guild = guild;
    }

    public GuildRankList getRank() {
        return rank;
    }

    public void setRank(GuildRankList rank) {
        this.rank = rank;
    }
}
