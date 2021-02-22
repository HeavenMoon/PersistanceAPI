package fr.heavenmoon.persistanceapi.managers;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.heavenmoon.persistanceapi.PersistanceManager;
import fr.heavenmoon.persistanceapi.customs.player.CustomPlayer;
import fr.heavenmoon.persistanceapi.customs.player.CustomSanction;
import fr.heavenmoon.persistanceapi.customs.player.SanctionType;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SanctionManager {

    //Define attributs
    public final PersistanceManager persistanceManager;

    /**
     * Contrustor
     *
     * @param persistanceManager The Game Service Manager {@link PersistanceManager}
     */
    public SanctionManager(PersistanceManager persistanceManager) {
        this.persistanceManager = persistanceManager;
    }

    /**
     * Method to get a CustomSanction from UUID
     *
     * @param uuid     The Unique ID
     * @return {@link CustomSanction}
     */
    public CustomSanction getCustomSanction(UUID uuid)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getPlayerClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getSanctionDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomSanction");
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RBucket<String> customSanctionRBucket = null;

        customSanctionRBucket = redissonClient.getBucket(persistanceManager.getRedisConfig().getMute() + uuid.toString());
        CustomSanction customSanction = null;

        if (customSanctionRBucket != null && customSanctionRBucket.isExists())
        {
            customSanction = fromJson(customSanctionRBucket.get());
            return customSanction;
        }
        else
        {
            Document sanctiondoc = new Document("sanctionId", uuid);
            Document found = mongoCollection.find(sanctiondoc).first();
            if (found != null)
            {
                customSanction = fromJson(found.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED)
                        .build()));
            }
            return customSanction;
        }
    }

    /**
     * Method to get the current sanction of a player
     *
     * @param customPlayer The CustomPlayer {@link CustomPlayer}
     * @return {@link CustomSanction}
     */
    public CustomSanction getCurrentCustomSanction(CustomPlayer customPlayer)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getSanctionClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getSanctionDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomSanction");
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();

        RBucket<String> customSanctionRBucket = null;
        customSanctionRBucket =
                redissonClient.getBucket(persistanceManager.getRedisConfig().getMute() + customPlayer.getModerationData().getCurrentSanctionId());
        CustomSanction customSanction = null;

        if (customSanctionRBucket != null && customSanctionRBucket.isExists())
        {
            customSanction = fromJson(customSanctionRBucket.get());
            return customSanction;
        }
        else
        {
            Document query = new Document("sanctionId", UUID.fromString(customPlayer.getModerationData().getCurrentSanctionId()));
            Document found = mongoCollection.find(query).first();
            if (found != null)
            {
                customSanction = fromJson(found.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED)
                        .build()));
            }
            return customSanction;
        }
    }

    /**
     * Method to get all bans of player
     *
     * @param customPlayer The CustomPlayer {@link CustomPlayer}
     * @return {@link List}
     */
    public List<CustomSanction> getAllBans(CustomPlayer customPlayer)
    {
        List<CustomSanction> banList = new ArrayList<>();
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getSanctionClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getSanctionDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomSanction");

        FindIterable<Document> cursor = mongoCollection.find();
        for (Document document : cursor)
        {
            CustomSanction customSanction = fromJson(document.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build()));
            if (customSanction.getType().equals(SanctionType.BAN) && !customSanction.isRevoqued())
                banList.add(customSanction);
        }
        return banList;
    }

    /**
     * Method to get all mutes of player
     *
     * @param customPlayer The CustomPlayer {@link CustomPlayer}
     * @return {@link List}
     */
    public List<CustomSanction> getAllMutes(CustomPlayer customPlayer)
    {
        List<CustomSanction> muteList = new ArrayList<>();
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getSanctionClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getSanctionDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomSanction");

        FindIterable<Document> cursor = mongoCollection.find();
        for (Document document : cursor)
        {
            CustomSanction customSanction = fromJson(document.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED)
                                                                                                    .build()));
            if (customSanction.getType().equals(SanctionType.MUTE) && !customSanction.isRevoqued())
                muteList.add(customSanction);
        }
        return muteList;
    }

    /**
     * Method to check if player is banned
     *
     * @param customPlayer The CustomPlayer {@link CustomPlayer}
     * @return {@link Boolean}
     */
    public boolean isBanned(CustomPlayer customPlayer)
    {
        if (customPlayer.getModerationData().getCurrentSanctionId().equalsIgnoreCase("null"))
        {
            return false;
        }
        CustomSanction sanction = getCustomSanction(UUID.fromString(customPlayer.getModerationData().getCurrentSanctionId()));
        if (sanction.getType().equals(SanctionType.BAN) && sanction.isValid() && !sanction.isRevoqued()) return true;
        else return false;
    }

    /**
     * Method to check if player is banned
     *
     * @param customPlayer The CustomPlayer {@link CustomPlayer}
     * @return {@link Boolean}
     */
    public boolean isMuted(CustomPlayer customPlayer)
    {
        if (customPlayer.getModerationData().getCurrentSanctionId().equals("null"))
        {
            return false;
        }
        CustomSanction sanction = getCurrentCustomSanction(customPlayer);
        if (sanction.getType().equals(SanctionType.MUTE) && sanction.isValid() && !sanction.isRevoqued()) return true;
        else return false;

    }

    /**
     * Method to add a sanction in database
     *
     * @param type           The type of snaction {@link SanctionType}
     * @param customSanction The CustomSanction {@link CustomSanction}
     * @return
     */
    public boolean applySanction(SanctionType type, CustomSanction customSanction) {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getSanctionClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getSanctionDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomSanction");
        
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RBucket<String> customSanctionRBucket = null;
        customSanctionRBucket = redissonClient.getBucket(persistanceManager.getRedisConfig().getMute() + customSanction
                .getSanctionId().toString());

        switch (type) {
            case KICK:
                mongoCollection.insertOne(Document.parse(toJson(customSanction)));
                return true;
            case MUTE:
                if (customSanctionRBucket != null)customSanctionRBucket.set(toJson(customSanction));
                mongoCollection.insertOne(Document.parse(toJson(customSanction)));
                return true;
            case BAN:
                mongoCollection.insertOne(Document.parse(toJson(customSanction)));
                return true;
            default:
                return false;
        }
    }

    /**
     * Mehtod to cancel a sanction
     *
     * @param customSanction The CustomSanction {@link CustomSanction}
     * @return {@link Boolean}
     */
    public boolean cancelSanction(CustomSanction customSanction, boolean revoqued)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getSanctionClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getSanctionDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomSanction");
        
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RBucket<String> customSanctionRBucket = redissonClient.getBucket(persistanceManager.getRedisConfig().getMute() + customSanction
                .getSanctionId().toString());
        
        switch (customSanction.getType())
        {
            case MUTE:
                Document muteQuery = new Document("sanctionId", customSanction.getSanctionId().toString());
                Document muteFound = mongoCollection.find(muteQuery).first();
                if (muteFound == null) return false;
                if (!customSanctionRBucket.isExists()) return false;
                customSanction.setRevoqued(revoqued);
                update(customSanction);
                remove(customSanction);
                return true;
            case BAN:
                Document banQuery = new Document("sanctionId", customSanction.getSanctionId().toString());
                Document banFound = mongoCollection.find(banQuery).first();
                if (banFound == null) return false;
                customSanction.setRevoqued(revoqued);
                update(customSanction);
                return true;
            default:
                return false;
        }
    }
    
    /**
     * Method to remove a CustomSanction from cache
     *
     * @param customSanction The CustomSanction {@link CustomSanction}
     */
    public void remove(CustomSanction customSanction)
    {
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RBucket<String> customPlayerRBucket =
                redissonClient.getBucket(persistanceManager.getRedisConfig().getMute() + customSanction.getSanctionId().toString());
        if (customPlayerRBucket.isExists()) {
            
            customPlayerRBucket.delete();
        }
        
    }

    /**
     * Method to delete a sanction from database
     *
     * @param customSanction The CustomSanction {@link CustomSanction}
     * @return {@link Boolean}
     */
    public boolean delete(CustomSanction customSanction)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getSanctionClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getSanctionDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomSanction");
        
        RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
        RBucket<String> customSanctionRBucket = redissonClient.getBucket(persistanceManager.getRedisConfig().getMute() + customSanction
                .getSanctionId().toString());
        switch (customSanction.getType())
        {
            case MUTE:
                Document muteQuery = new Document("sanctionId", customSanction.getSanctionId().toString());
                Document muteFound = mongoCollection.find(muteQuery).first();
                if (muteFound == null) return false;
                mongoCollection.deleteOne(muteFound);
                if (!customSanctionRBucket.isExists()) return false;
                customSanctionRBucket.delete();
                return true;
            case BAN:
                Document banQuery = new Document("sanctionId", customSanction.getSanctionId().toString());
                Document banFound = mongoCollection.find(banQuery).first();
                if (banFound == null) return false;
                mongoCollection.deleteOne(banFound);
                return true;
            case KICK:
                Document kickQuery = new Document("sanctionId", customSanction.getSanctionId().toString());
                Document kickFound = mongoCollection.find(kickQuery).first();
                if (kickFound == null) return false;
                mongoCollection.deleteOne(kickFound);
                return true;
            default:
                return false;
        }
    }

    /**
     * Method to update CustomSanction in database
     *
     * @param customSanction The CustomSanction {@link CustomSanction}
     * @return {@link Boolean}
     */
    public boolean update(CustomSanction customSanction)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getSanctionClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getSanctionDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomSanction");
        
        switch (customSanction.getType())
        {
            case MUTE:
                Document muteQuery = new Document("sanctionId", customSanction.getSanctionId().toString());
                Document muteFound = mongoCollection.find(muteQuery).first();
                if (muteFound == null) return false;
                mongoCollection.findOneAndReplace(muteQuery, Document.parse(toJson(customSanction)));
                return true;
            case BAN:
                Document banQuery = new Document("sanctionId", customSanction.getSanctionId().toString());
                Document banFound = mongoCollection.find(banQuery).first();
                if (banFound == null) return false;
                mongoCollection.findOneAndReplace(banQuery, Document.parse(toJson(customSanction)));
                return true;
            default:
                return false;
        }
    }

    /**
     * Method to transform CustomSanction into JSON
     *
     * @param customSanction The customServer {@link CustomSanction}
     * @return {@link String}
     */
    public String toJson(CustomSanction customSanction) { return new Gson().toJson(customSanction); }

    /**
     * Method to transform JSON String into CustomSanction
     *
     * @param json The customSanction in JSON String {@link String}
     * @return {@link CustomSanction}
     */
    public CustomSanction fromJson(String json) { return new Gson().fromJson(json, CustomSanction.class); }
}
