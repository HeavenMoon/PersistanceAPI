package fr.heavenmoon.persistanceapi.managers;

import fr.heavenmoon.persistanceapi.PersistanceManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedisManager
{

    // define attributs
    private static volatile RedisManager instance = null;
    private RedissonClient redissonClient = null;
    private String clientName;
    private String url;
    private String password;
    private int database;

    /**
     *  Constructor
     * @param persistanceManager The PersistanceManager
     */
    public RedisManager(PersistanceManager persistanceManager)
    {
        // Super constructor
        super();
        this.clientName = persistanceManager.getServerName();
        this.url = persistanceManager.getRedisConfig().getHost() + ":" + persistanceManager.getRedisConfig().getPort();
        this.password = persistanceManager.getRedisConfig().getPassword();
        this.database = 0;
        final Config config = new Config();
        config.setUseLinuxNativeEpoll(true);
        config.setThreads(8);
        config.setNettyThreads(8);
        config.useSingleServer().setAddress(url).setPassword(password).setDatabase(database).setClientName(clientName);
        this.redissonClient = Redisson.create(config);
    }

    /**
     *  Singletone generator
     *
     * @param persistanceManager The PersistanceManager
     * @return
     */
    public final static RedisManager getInstance(PersistanceManager persistanceManager) throws ParseException
    {
        if (RedisManager.instance == null)
        {
            synchronized(RedisManager.class)
            {
                if (RedisManager.instance == null)
                {
                    RedisManager.instance = new RedisManager(persistanceManager);
                }
            }
        }
        return RedisManager.instance;
    }


    public void close()
    {
        redissonClient.shutdown();
    }

    // Getters
    public RedissonClient getRedissonClient() { return redissonClient; }
    public String getUrl() { return url; }
    public String getClientName() { return clientName; }
    public String getPassword() { return password; }
    public int getDatabase() { return database; }
}
