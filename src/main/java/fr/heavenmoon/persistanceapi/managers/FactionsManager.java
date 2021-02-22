package fr.heavenmoon.persistanceapi.managers;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.heavenmoon.persistanceapi.PersistanceManager;
import fr.heavenmoon.persistanceapi.customs.factions.CustomFaction;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;
import java.util.List;

public class FactionsManager
{
	
	private final PersistanceManager persistanceManager;
	
	public FactionsManager(PersistanceManager persistanceManager)
	{
		this.persistanceManager = persistanceManager;
	}
	
	public CustomFaction get(String id)
	{
		MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getFactionClient()
		                                                .getDatabase(persistanceManager.getDatabaseManager().getFactionDatabase());
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomFaction");
		RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
		RBucket<String> factionRBucket = null;
		factionRBucket = redissonClient.getBucket(persistanceManager.getRedisConfig().getFaction() + id);
		CustomFaction customFaction = null;
		
		if (factionRBucket != null && factionRBucket.isExists())
		{
			customFaction = fromJson(factionRBucket.get());
			return customFaction;
		}
		else
		{
			Document factiondoc = new Document("id", id);
			Document found = mongoCollection.find(factiondoc).first();
			if (found != null)
			{
				customFaction = fromJson(found.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED)
				                                                         .build()));
			}
		}
		return customFaction;
	}
	
	public List<CustomFaction> getAllFactions()
	{
		MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getFactionClient()
		                                                .getDatabase(persistanceManager.getDatabaseManager().getFactionDatabase());
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomFaction");
		FindIterable<Document> cursor = mongoCollection.find();
		List<CustomFaction> factionList = new ArrayList<>();
		for (Document document : cursor)
		{
			CustomFaction customFaction = fromJson(document.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build()));
			factionList.add(customFaction);
		}
		return factionList;
	}
	
	public void add(CustomFaction customFaction)
	{
		MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getFactionClient()
		                                                .getDatabase(persistanceManager.getDatabaseManager().getFactionDatabase());
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomFaction");
		mongoCollection.insertOne(Document.parse(toJson(customFaction)));
	}
	
	public void update(CustomFaction customFaction)
	{
		MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getFactionClient()
		                                                .getDatabase(persistanceManager.getDatabaseManager().getFactionDatabase());
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomFaction");
		Document playerdoc = new Document("id", customFaction.getId());
		Document found = mongoCollection.findOneAndReplace(playerdoc, Document.parse(toJson(customFaction)));
	}
	
	public void commit(CustomFaction customFaction)
	{
		System.out.println("Commit: " + customFaction.getName() + " " + customFaction.getId());
		RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
		RBucket<String> factionPlayerRBucket = redissonClient.getBucket(persistanceManager.getRedisConfig().getFaction() + customFaction.getId());
		factionPlayerRBucket.set(toJson(customFaction));
	}
	
	public void delete(CustomFaction customFaction)
	{
		MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getFactionClient()
		                                                .getDatabase(persistanceManager.getDatabaseManager().getFactionDatabase());
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomFaction");
		mongoCollection.findOneAndDelete(Document.parse(toJson(customFaction)));
	}
	
	public void remove(CustomFaction customFaction)
	{
		RedissonClient redissonClient = persistanceManager.getRedisManager().getRedissonClient();
		RBucket<String> factionPlayerRBucket =
				redissonClient.getBucket(persistanceManager.getRedisConfig().getFaction() + customFaction.getId());
		if (factionPlayerRBucket.isExists()) {
			
			factionPlayerRBucket.delete();
			System.out.println("remove exist player, key: " + persistanceManager.getRedisConfig().getPlayer() + customFaction.getId());
		}
	}
	
	public CustomFaction fromJson(String json)
	{
		return new Gson().fromJson(json, CustomFaction.class);
	}
	
	public String toJson(CustomFaction customFaction) { return new Gson().toJson(customFaction); };
}
