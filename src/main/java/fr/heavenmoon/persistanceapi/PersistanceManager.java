package fr.heavenmoon.persistanceapi;

import fr.heavenmoon.persistanceapi.config.DatabaseConfig;
import fr.heavenmoon.persistanceapi.config.RedisConfig;
import fr.heavenmoon.persistanceapi.customs.redis.RedisKey;
import fr.heavenmoon.persistanceapi.customs.server.CustomServer;
import fr.heavenmoon.persistanceapi.customs.server.ServerStatus;
import fr.heavenmoon.persistanceapi.managers.*;
import org.json.simple.parser.ParseException;

import java.nio.file.Path;

public class PersistanceManager
{
    // Defines attributes
    private String serverName;
    private DatabaseConfig databaseConfig;
    private RedisConfig redisConfig;
    private DatabaseManager databaseManager;
    private RedisManager redisManager;
    private ServerManager serverManager;
    private PlayerManager playerManager;
    private AddressManager addressManager;
    private SanctionManager sanctionManager;
    private FactionsManager factionsManager;
    private FPlayersManager fPlayersManager;
    
    /**
     * Constructor
     *
     * @param serverName The serverName
     */
    public PersistanceManager(String serverName, DatabaseConfig databaseConfig, RedisConfig redisConfig)
    {
        this.serverName = serverName;
        this.databaseConfig = databaseConfig;
        this.redisConfig = redisConfig;
        
        this.databaseManager = new DatabaseManager(this);
        this.redisManager = new RedisManager(this);
        this.playerManager = new PlayerManager(this);
        this.addressManager = new AddressManager(this);
        this.sanctionManager = new SanctionManager(this);
        this.serverManager = new ServerManager(this);
        this.factionsManager = new FactionsManager(this);
        this.fPlayersManager = new FPlayersManager(this);
    }

    /**
     * Method on disable of plugin
     */
    public void shutdown()
    {
        databaseManager.close();
        redisManager.close();
    }

    // Getters
    public String getServerName() { return serverName; }
    public DatabaseConfig getDatabaseConfig() { return databaseConfig; }
    public RedisConfig getRedisConfig() { return redisConfig; }
    public DatabaseManager getDatabaseManager() { return databaseManager; }
    public RedisManager getRedisManager() { return redisManager; }
    public ServerManager getServerManager() { return serverManager; }
    public PlayerManager getPlayerManager() { return playerManager; }
    public AddressManager getAddressManager() { return addressManager; }
    public SanctionManager getSanctionManager() { return sanctionManager; }
    public FactionsManager getFactionsManager() { return factionsManager; }
    public FPlayersManager getfPlayersManager() { return fPlayersManager; }
}
