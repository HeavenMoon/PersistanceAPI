package fr.heavenmoon.persistanceapi.managers;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.heavenmoon.persistanceapi.PersistanceManager;
import fr.heavenmoon.persistanceapi.customs.factions.FactionPlayer;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.HashMap;
import java.util.UUID;

public class FPlayersManager {
    
    //Define attributs
    public final PersistanceManager persistanceManager;
    private HashMap<UUID, FactionPlayer> cache;
    
    /**
     * Constructor
     *
     * @param persistanceManager The Game Service Manager {@link PersistanceManager}
     */
    public FPlayersManager(PersistanceManager persistanceManager)
    {
        this.persistanceManager = persistanceManager;
        this.cache = new HashMap<>();
    }
    
    /**
     * Method to get a FactionPlayer
     *
     * @param uuid     The uuid of the Player
     * @return {@link FactionPlayer}
     */
    public FactionPlayer getFactionPlayer(UUID uuid)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getFactionClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getFactionDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("FactionPlayer");
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RBucket<String> factionPlayerRBucket = null;
        if (cache.containsKey(uuid))
        {
            return cache.get(uuid);
        }
        factionPlayerRBucket = redissonClient.getBucket(persistanceManager.getRedisConfig().getFactionPlayer() + uuid.toString());
        FactionPlayer factionPlayer = null;
        
        if (factionPlayerRBucket != null && factionPlayerRBucket.isExists())
        {
            factionPlayer = fromJson(factionPlayerRBucket.get());
            return factionPlayer;
        }
        else
        {
            Document playerdoc = new Document("uniqueID", uuid.toString());
            Document found = mongoCollection.find(playerdoc).first();
            if (found != null)
            {
                factionPlayer = fromJson(found.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED)
                                                                       .build()));
                if (factionPlayer.isOnline())
                {
                    cache.put(factionPlayer.getUniqueID(), factionPlayer);
                    commit(factionPlayer);
                }
            }
            else
            {
                factionPlayer = new FactionPlayer(uuid);
                factionPlayer.setFirstLogin(System.currentTimeMillis());
                add(factionPlayer);
            }
            return factionPlayer;
        }
    }
    
    /**
     * Method to add a FactionPlayer to the database
     *
     * @param factionPlayer The FactionPlayer {@link FactionPlayer}
     */
    public void add(FactionPlayer factionPlayer)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getFactionClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getFactionDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("FactionPlayer");
        mongoCollection.insertOne(Document.parse(toJson(factionPlayer)));
    }
    
    /**
     * Method to update a FactionPlayer in the database
     *
     * @param factionPlayer The FactionPlayer {@link FactionPlayer}
     */
    public void update(FactionPlayer factionPlayer)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getFactionClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getFactionDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("FactionPlayer");
        Document playerdoc = new Document("uniqueID", factionPlayer.getUniqueID().toString());
        Document found = mongoCollection.findOneAndReplace(playerdoc, Document.parse(toJson(factionPlayer)));
    }
    
    /**
     * Method to delete a FactionPlayer from the database
     *
     * @param factionPlayer he FactionPlayer {@link FactionPlayer}
     */
    public void delete(FactionPlayer factionPlayer)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getFactionClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getFactionDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("FactionPlayer");
        mongoCollection.findOneAndDelete(Document.parse(toJson(factionPlayer)));
        
    }
    
    /**
     * Method to add a FactionPlayer to cache
     *
     * @param factionPlayer The FactionPlayer {@link FactionPlayer}
     */
    public void cache(FactionPlayer factionPlayer)
    {
        cache.put(factionPlayer.getUniqueID(), factionPlayer);
    }
    
    /**
     * Method to add a FactionPlayer to redis cache
     *
     * @param factionPlayer The FactionPlayer {@link FactionPlayer}
     */
    public void commit(FactionPlayer factionPlayer)
    {
        if (!factionPlayer.isOnline())
        {
            update(factionPlayer);
            return;
        }
        System.out.println("Commit: " + factionPlayer.getName() + " " + factionPlayer.getUniqueID().toString());
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RBucket<String> factionPlayerRBucket = redissonClient.getBucket(persistanceManager.getRedisConfig().getFactionPlayer() + factionPlayer.getUniqueID()
                                                                                                                                     .toString());
        factionPlayerRBucket.set(toJson(factionPlayer));
        if (cache.containsKey(factionPlayer.getUniqueID())) cache(factionPlayer);
    }
    
    /**
     * Method to remove a FactionPlayer from cache
     *
     * @param factionPlayer The FactionPlayer {@link FactionPlayer}
     */
    public void remove(FactionPlayer factionPlayer)
    {
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RBucket<String> factionPlayerRBucket =
                redissonClient.getBucket(persistanceManager.getRedisConfig().getFactionPlayer() + factionPlayer.getUniqueID().toString());
        if (factionPlayerRBucket.isExists()) {
    
            factionPlayerRBucket.delete();
            System.out.println("remove exist player, key: " + persistanceManager.getRedisConfig().getPlayer() + factionPlayer.getUniqueID().toString());
        }
        
    }
    
    /**
     * Method to remove a FactionPlayer from cache
     *
     * @param factionPlayer The FactionPlayer {@link FactionPlayer}
     */
    public void removeFromCache(FactionPlayer factionPlayer)
    {
        cache.remove(factionPlayer.getUniqueID());
    }
    
    /**
     * Method to transform FactionPlayer into JSON
     *
     * @param factionPlayer The FactionPlayer {@link FactionPlayer}
     * @return {@link String}
     */
    public String toJson(FactionPlayer factionPlayer)
    {
        return new Gson().toJson(factionPlayer);
    }
    
    /**
     * Method to transform JSON String into FactionPlayer
     *
     * @param json The FactionPlayer in JSON String {@link String}
     * @return {@link FactionPlayer}
     */
    public FactionPlayer fromJson(String json)
    {
        return new Gson().fromJson(json, FactionPlayer.class);
    }
    
}