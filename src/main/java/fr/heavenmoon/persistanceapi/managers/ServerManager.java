package fr.heavenmoon.persistanceapi.managers;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.heavenmoon.persistanceapi.PersistanceManager;
import fr.heavenmoon.persistanceapi.customs.server.CustomServer;
import fr.heavenmoon.persistanceapi.customs.server.ServerStatus;
import fr.heavenmoon.persistanceapi.customs.server.ServerType;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServerManager
{
    //Define attributs
    private final PersistanceManager persistanceManager;

    /**
     * Constructor
     *
     * @param persistanceManager The Game Service Manager {@link PersistanceManager}
     */
    public ServerManager(PersistanceManager persistanceManager) {
        this.persistanceManager = persistanceManager;
    }

    /**
     * Method to get a custom plauer
     *
     * @param name The name of the server
     * @return {@link CustomServer}
     */
    public CustomServer getCustomServer(String name)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getServerClient().getDatabase(
                persistanceManager.getDatabaseManager().getServerDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomServer");
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();

        RBucket<String> customServerRBucket = redissonClient.getBucket(persistanceManager.getRedisConfig().getServer() + name);
        CustomServer customServer = null;

        if (customServerRBucket.isExists())
        {
            customServer = fromJson(customServerRBucket.get());
            return customServer;
        }
        else
        {
            Document serverdoc = new Document("name", name);
            Document found = mongoCollection.find(serverdoc).first();
            if (found != null)
            {
                customServer = fromJson(found.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED)
                        .build()));
                commit(customServer);
            }
            return customServer;
        }
    }

    /**
     * Method to check if CustomServer exist
     *
     * @param serverName The server name
     * @return {@link Boolean}
     */
    public boolean exist(String serverName)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getServerClient().getDatabase(
                persistanceManager.getDatabaseManager().getServerDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomServer");
        Document query = new Document("name", serverName);
        Document found = mongoCollection.find(query).first();
        return found != null;
    }

    /**
     * Method to get the global online count of player
     *
     * @return {@link Integer}
     */
    public int getNetworkOnline()
    {
        int networkOnline = 0;
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RKeys keys = redissonClient.getKeys();
        RBucket<String> customServerRBucket = null;
        for (String key : keys.getKeys())
        {
            if (key.contains("CustomServer:"))
            {
                customServerRBucket = redissonClient.getBucket(key);
                CustomServer customServer = fromJson(customServerRBucket.get());
                if (!customServer.getType().equals(ServerType.PROXY) && customServer.getStatus().equals(ServerStatus.STARTED))
                    networkOnline += customServer.getOnline();
            }
        }
        return networkOnline;
    }

    /**
     * Method to get all available hubs
     *
     * @return {@link List}
     */
    public List<CustomServer> getHubs()
    {
        List<CustomServer> customServerList = new ArrayList<>();
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RKeys keys = redissonClient.getKeys();
        RBucket<String> customServerRBucket = null;
        for (String key : keys.getKeys())
        {
            if (key.contains("CustomServer:"))
            {
                customServerRBucket = redissonClient.getBucket(key);
                CustomServer customServer = fromJson(customServerRBucket.get());
                if (customServer.getType().equals(ServerType.LOBBY) && customServer.getStatus().equals(ServerStatus.STARTED))
                {
                    System.out.println(customServer.getName());
                    customServerList.add(customServer);
                }
            }
        }
        return customServerList;
    }

    /**
     * Method to get a random hub server
     *
     * @return {@link CustomServer}
     */
    public CustomServer getRandomHub()
    {
        List<CustomServer> customServerList = new ArrayList<>();
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RKeys keys = redissonClient.getKeys();
        RBucket<String> customServerRBucket = null;
        for (String key : keys.getKeys())
        {
            if (key.contains("CustomServer:"))
            {
                customServerRBucket = redissonClient.getBucket(key);
                CustomServer customServer = fromJson(customServerRBucket.get());
                if (customServer.getType().equals(ServerType.LOBBY) && customServer.getStatus().equals(ServerStatus.STARTED))
                    customServerList.add(customServer);
            }
        }
        Random random = new Random();
        return customServerList.get(random.nextInt(customServerList.size()));
    }

    /**
     * Method to add a customServer to the database
     *
     * @param customServer The CustomServer {@link CustomServer}
     */
    public void add(CustomServer customServer)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getServerClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getServerDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomServer");
        mongoCollection.insertOne(Document.parse(toJson(customServer)));
    }

    /**
     * Method to delete a customServer from the database
     *
     * @param customServer The CustomServer {@link CustomServer}
     */
    public void delete(CustomServer customServer)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getServerClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getServerDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomServer");
        mongoCollection.findOneAndDelete(Document.parse(toJson(customServer)));
    }

    /**
     * Method to update a customServer into the database
     *
     * @param customServer The CustomServer {@link CustomServer}
     */
    public void update(CustomServer customServer)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getServerClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getServerDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomServer");
        Document query = new Document("name", customServer.getName());
        mongoCollection.findOneAndReplace(query, Document.parse(toJson(customServer)));
    }

    /**
     * Method to add a CustomServer to cache
     *
     * @param customServer The CustomServer {@link CustomServer}
     */
    public void commit(CustomServer customServer)
    {
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RBucket<String> customServerRBucket = redissonClient.getBucket(persistanceManager.getRedisConfig().getServer() + customServer.getName());
        customServerRBucket.set(toJson(customServer));
    }

    /**
     * Method to remove a CustomServer from cache
     *
     * @param customServer The CustomServer {@link CustomServer}
     */
    public void remove(CustomServer customServer)
    {
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RBucket<String> customServerRBucket =
                redissonClient.getBucket(persistanceManager.getRedisConfig().getServer() + customServer.getName());
        if (customServerRBucket.isExists())
        {
            customServerRBucket.delete();
            System.out.println("remove exist server, key: " + persistanceManager.getRedisConfig().getServer() + customServer.getName());
        }
    }

    /**
     * Method to transform CustomServer into JSON
     * @param customServer The customServer {@link CustomServer}
     * @return {@link String}
     */
    public String toJson(CustomServer customServer) { return new Gson().toJson(customServer); }

    /**
     * Method to transform JSON String into CustomServer
     * @param json The customServer in JSON String {@link String}
     * @return {@link CustomServer}
     */
    public CustomServer fromJson(String json) { return new Gson().fromJson(json, CustomServer.class); }

}
