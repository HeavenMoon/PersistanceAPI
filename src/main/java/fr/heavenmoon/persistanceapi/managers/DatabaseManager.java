package fr.heavenmoon.persistanceapi.managers;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import fr.heavenmoon.persistanceapi.PersistanceManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DatabaseManager
{

    // define attributs
    private static volatile DatabaseManager instance = null;
    private String base;
    private String host;
    private int port;
    private MongoClient addressClient = null;
    private String addressUser;
    private String addressPassword;
    private String addressDatabase;
    private MongoClient logClient = null;
    private String logUser;
    private String logPassword;
    private String logDatabase;
    private MongoClient playerClient = null;
    private String playerUser;
    private String playerPassword;
    private String playerDatabase;
    private MongoClient sanctionClient = null;
    private String sanctionUser;
    private String sanctionPassword;
    private String sanctionDatabase;
    private MongoClient serverClient = null;
    private String serverUser;
    private String serverPassword;
    private String serverDatabase;
    private MongoClient factionClient = null;
    private String factionUser;
    private String factionPassword;
    private String factionDatabase;

    /**
     *  Constructor
     *
     * @param persistanceManager The PersistanceManager
     */
    public DatabaseManager(PersistanceManager persistanceManager)
    {
        // Super constructor
        super();
        this.base = persistanceManager.getDatabaseConfig().getBase();
        this.host = persistanceManager.getDatabaseConfig().getHost();
        this.port = persistanceManager.getDatabaseConfig().getPort();
        
        this.addressUser = persistanceManager.getDatabaseConfig().getAddressUser();
        this.addressPassword = persistanceManager.getDatabaseConfig().getAddressPassword();
        this.addressDatabase = persistanceManager.getDatabaseConfig().getAddressDatabase();
        this.addressClient = new MongoClient(new MongoClientURI(base + addressUser + ":" + addressPassword + "@" + host + ":" + port + "/" + addressDatabase));
        
        this.logUser = persistanceManager.getDatabaseConfig().getLogUser();
        this.logPassword = persistanceManager.getDatabaseConfig().getLogPassword();
        this.logDatabase = persistanceManager.getDatabaseConfig().getLogDatabase();
        this.logClient = new MongoClient(new MongoClientURI(base + logUser + ":" + logPassword + "@" + host + ":" + port + "/" + logDatabase));
        
        this.playerUser = persistanceManager.getDatabaseConfig().getPlayerUser();
        this.playerPassword = persistanceManager.getDatabaseConfig().getPlayerPassword();
        this.playerDatabase = persistanceManager.getDatabaseConfig().getPlayerDatabase();
        this.playerClient = new MongoClient(new MongoClientURI(base + playerUser + ":" + playerPassword + "@" + host + ":" + port + "/" + playerDatabase));
        
        this.sanctionUser = persistanceManager.getDatabaseConfig().getSanctionUser();
        this.sanctionPassword = persistanceManager.getDatabaseConfig().getSanctionPassword();
        this.sanctionDatabase = persistanceManager.getDatabaseConfig().getSanctionDatabase();
        this.sanctionClient = new MongoClient(new MongoClientURI(base + sanctionUser + ":" + sanctionPassword + "@" + host + ":" + port + "/" + sanctionDatabase));
        
        this.serverUser = persistanceManager.getDatabaseConfig().getServerUser();
        this.serverPassword = persistanceManager.getDatabaseConfig().getServerPassword();
        this.serverDatabase = persistanceManager.getDatabaseConfig().getServerDatabase();
        this.serverClient = new MongoClient(new MongoClientURI(base + serverUser + ":" + serverPassword + "@" + host + ":" + port + "/" + serverDatabase));
    
        this.factionUser = persistanceManager.getDatabaseConfig().getFactionUser();
        this.factionPassword = persistanceManager.getDatabaseConfig().getFactionPassword();
        this.factionDatabase = persistanceManager.getDatabaseConfig().getFactionDatabase();
        this.factionClient = new MongoClient(new MongoClientURI(base + factionUser + ":" + factionPassword + "@" + host + ":" + port + "/" + factionDatabase));
    }

    /**
     * Singletone generator
     *
     * @param persistanceManager The PersistanceManager
     * @return {@link DatabaseManager}
     */
    public final static DatabaseManager getInstance(PersistanceManager persistanceManager) throws ParseException
    {
        if (DatabaseManager.instance == null)
        {
            synchronized(DatabaseManager.class)
            {
                if (DatabaseManager.instance == null)
                {
                    DatabaseManager.instance = new DatabaseManager(persistanceManager);
                }
            }
        }
        return DatabaseManager.instance;
    }

    /**
     * Methode to close connection to mongodb server
     */
    public void close()
    {
        addressClient.close();
        logClient.close();
        playerClient.close();
        sanctionClient.close();
        serverClient.close();
    }
    
    // Getters
    public MongoClient getAddressClient()
    {
        return addressClient;
    }
    
    public String getAddressUser()
    {
        return addressUser;
    }
    
    public String getAddressPassword()
    {
        return addressPassword;
    }
    
    public String getAddressDatabase()
    {
        return addressDatabase;
    }
    
    public MongoClient getLogClient() { return logClient; }
    
    public String getLogUser()
    {
        return logUser;
    }
    
    public String getLogPassword()
    {
        return logPassword;
    }
    
    public String getLogDatabase()
    {
        return logDatabase;
    }
    
    public MongoClient getPlayerClient()
    {
        return playerClient;
    }
    
    public String getPlayerUser()
    {
        return playerUser;
    }
    
    public String getPlayerPassword()
    {
        return playerPassword;
    }
    
    public String getPlayerDatabase()
    {
        return playerDatabase;
    }
    
    public MongoClient getSanctionClient()
    {
        return sanctionClient;
    }
    
    public String getSanctionUser()
    {
        return sanctionUser;
    }
    
    public String getSanctionPassword()
    {
        return sanctionPassword;
    }
    
    public String getSanctionDatabase()
    {
        return sanctionDatabase;
    }
    
    public MongoClient getServerClient()
    {
        return serverClient;
    }
    
    public String getServerUser()
    {
        return serverUser;
    }
    
    public String getServerPassword()
    {
        return serverPassword;
    }
    
    public String getServerDatabase()
    {
        return serverDatabase;
    }
    
    public MongoClient getFactionClient() { return factionClient; }
    
    public String getFactionUser() { return factionUser; }
    
    public String getFactionPassword() { return factionPassword; }
    
    public String getFactionDatabase() { return factionDatabase; }
}
