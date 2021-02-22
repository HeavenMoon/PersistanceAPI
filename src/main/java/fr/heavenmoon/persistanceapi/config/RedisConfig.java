package fr.heavenmoon.persistanceapi.config;

public class RedisConfig
{
  private String host;
  private int port;
  private String password;
  
  private String player;
  private String uuid;
  private String server;
  private String booster;
  private String report;
  private String mute;
  
  private String faction;
  private String factionPlayer;
  
  public RedisConfig(String host, int port, String password, String player, String uuid, String server, String booster, String report,
          String mute, String faction, String factionPlayer) {
    this.host = host;
    this.port = port;
    this.password = password;
    this.player = player;
    this.uuid = uuid;
    this.server = server;
    this.booster = booster;
    this.report = report;
    this.mute = mute;
    this.faction = faction;
    this.factionPlayer = factionPlayer;
  }
  
  public String getHost() {
    return this.host;
  }
  
  public int getPort() {
    return this.port;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public String getPlayer() {
    return this.player;
  }
  
  public String getUuid() {
    return this.uuid;
  }
  
  public String getServer() {
    return this.server;
  }
  
  public String getBooster() {
    return this.booster;
  }
  
  public String getReport() {
    return this.report;
  }
  
  public String getMute() {
    return this.mute;
  }
  
  public String getFaction() { return faction; }
  
  public String getFactionPlayer() { return factionPlayer; }
  
  public boolean isValid() {
    return (this.host != null && !this.host.isEmpty() && this.password != null && !this.password.isEmpty() && this.player != null && 
      !this.player.isEmpty() && this.uuid != null && !this.uuid.isEmpty() && this.server != null && !this.server.isEmpty() && this.booster != null &&
      !this.booster.isEmpty() && this.report != null && !this.report.isEmpty() && this.mute != null && !this.mute.isEmpty());
  }
  
  public String toString() {
    return "RedisConfig{HOST='" + this.host + "', PASSWORD='" + this.password + "', PORT='" + this.port + "', PLAYER='" + this.player + "', UUID='" + this.uuid + "', SERVER='" + this.server + "', BOOSTER='" + this.booster + "', MUTE='" + this.mute + "'}";
  }
}
