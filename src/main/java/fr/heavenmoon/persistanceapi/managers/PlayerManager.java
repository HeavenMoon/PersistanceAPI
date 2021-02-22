package fr.heavenmoon.persistanceapi.managers;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.heavenmoon.persistanceapi.PersistanceManager;
import fr.heavenmoon.persistanceapi.customs.player.CustomPlayer;
import jodd.util.BCrypt;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.HashMap;
import java.util.UUID;

/**
 * The type Player manager.
 */
public class PlayerManager
{

    //Define attributs
    public final PersistanceManager persistanceManager;
    private HashMap<UUID, CustomPlayer> cache;

    /**
     * Constructor
     *
     * @param persistanceManager The Game Service Manager {@link PersistanceManager}
     */
    public PlayerManager(PersistanceManager persistanceManager)
    {
        this.persistanceManager = persistanceManager;
        this.cache = new HashMap<>();
    }

    /**
     * Method to get a CustomPlayer
     *
     * @param uuid     The uuid of the Player
     * @return {@link PlayerManager}
     */
    public CustomPlayer getCustomPlayer(UUID uuid)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getPlayerClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getPlayerDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomPlayer");
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RBucket<String> customPlayerRBucket = null;
        if (cache.containsKey(uuid))
        {
            return cache.get(uuid);
        }
        customPlayerRBucket = redissonClient.getBucket(persistanceManager.getRedisConfig().getPlayer() + uuid.toString());
        CustomPlayer customPlayer = null;

        if (customPlayerRBucket != null && customPlayerRBucket.isExists())
        {
            customPlayer = fromJson(customPlayerRBucket.get());
            return customPlayer;
        }
        else
        {
            Document playerdoc = new Document("UniqueID", uuid.toString());
            Document found = mongoCollection.find(playerdoc).first();
            if (found != null)
            {
                customPlayer = fromJson(found.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED)
                        .build()));
                if (customPlayer.isOnline())
                {
                    cache.put(customPlayer.getUniqueID(), customPlayer);
                    commit(customPlayer);
                }
            }
            else
            {
                customPlayer = new CustomPlayer(uuid);
                customPlayer.setFirstLogin(System.currentTimeMillis());
                add(customPlayer);
            }
            return customPlayer;
        }
    }

    /**
     * Method to add a CustomPlayer to the database
     *
     * @param customPlayer The CustomPlayer {@link CustomPlayer}
     */
    public void add(CustomPlayer customPlayer)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getPlayerClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getPlayerDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomPlayer");
        mongoCollection.insertOne(Document.parse(toJson(customPlayer)));
    }

    /**
     * Method to update a CustomPlayer in the database
     *
     * @param customPlayer The CustomPlayer {@link CustomPlayer}
     */
    public void update(CustomPlayer customPlayer)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getPlayerClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getPlayerDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomPlayer");
        Document playerdoc = new Document("UniqueID", customPlayer.getUniqueID().toString());
        Document found = mongoCollection.findOneAndReplace(playerdoc, Document.parse(toJson(customPlayer)));
    }

    /**
     * Method to delete a CustomPlayer from the database
     *
     * @param customPlayer he CustomPlayer {@link CustomPlayer}
     */
    public void delete(CustomPlayer customPlayer)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getPlayerClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getPlayerDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomPlayer");
        mongoCollection.findOneAndDelete(Document.parse(toJson(customPlayer)));

    }
    
    /**
     * Method to add a CustomPlayer to cache
     *
     * @param customPlayer The CustomPlayer {@link CustomPlayer}
     */
    public void cache(CustomPlayer customPlayer)
    {
        cache.put(customPlayer.getUniqueID(), customPlayer);
    }
    
    /**
     * Method to add a CustomPlayer to redis cache
     *
     * @param customPlayer The CustomPlayer {@link CustomPlayer}
     */
    public void commit(CustomPlayer customPlayer)
    {
        if (!customPlayer.isOnline())
        {
            update(customPlayer);
            return;
        }
        System.out.println("Commit: " + customPlayer.getName() + " " + customPlayer.getUniqueID().toString());
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RBucket<String> customPlayerRBucket = redissonClient.getBucket(persistanceManager.getRedisConfig().getPlayer() + customPlayer.getUniqueID()
                .toString());
        customPlayerRBucket.set(toJson(customPlayer));
        if (cache.containsKey(customPlayer.getUniqueID())) cache(customPlayer);
    }

    /**
     * Method to remove a CustomPlayer from cache
     *
     * @param customPlayer The CustomPlayer {@link CustomPlayer}
     */
    public void remove(CustomPlayer customPlayer)
    {
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RBucket<String> customPlayerRBucket = redissonClient.getBucket(persistanceManager.getRedisConfig().getPlayer() + customPlayer.getUniqueID().toString());
        if (customPlayerRBucket.isExists()) {
            
            customPlayerRBucket.delete();
            System.out.println("remove exist player, key: " + persistanceManager.getRedisConfig().getPlayer() + customPlayer.getUniqueID().toString());
        }
        
    }
    
    /**
     * Method to remove a CustomPlayer from cache
     *
     * @param customPlayer The CustomPlayer {@link CustomPlayer}
     */
    public void removeFromCache(CustomPlayer customPlayer)
    {
        cache.remove(customPlayer.getUniqueID());
    }

    /**
     * Method to transform CustomPlayer into JSON
     *
     * @param customPlayer The CustomPlayer {@link CustomPlayer}
     * @return {@link String}
     */
    public String toJson(CustomPlayer customPlayer)
    {
        return new Gson().toJson(customPlayer);
    }

    /**
     * Method to transform JSON String into CustomPlayer
     *
     * @param json The CustomPlayer in JSON String {@link String}
     * @return {@link CustomPlayer}
     */
    public CustomPlayer fromJson(String json)
    {
        return new Gson().fromJson(json, CustomPlayer.class);
    }

    /**
     * Method to check player password
     *
     * @param customPlayer The CustomPlayer {@link CustomPlayer}
     * @param password     The password
     * @return {@link Boolean}
     */
    public boolean checkPassword(CustomPlayer customPlayer, String password)
    {
        return BCrypt.checkpw(password, customPlayer.getPasswordHash());
    }

    /**
     * Method to update password of a player
     *
     * @param customPlayer   The player {@link CustomPlayer}
     * @param password_hash  The hashed password
     */
    public void updatePassword(CustomPlayer customPlayer, String password_hash)
    {
        customPlayer.setPasswordHash(password_hash);
        persistanceManager.getPlayerManager().commit(customPlayer);
        persistanceManager.getPlayerManager().update(customPlayer);
    }
}