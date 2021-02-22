package fr.heavenmoon.persistanceapi.managers;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.heavenmoon.persistanceapi.PersistanceManager;
import fr.heavenmoon.persistanceapi.customs.address.CustomAddress;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;

import java.util.HashMap;
import java.util.UUID;

public class AddressManager
{
    //Define attributs
    public final PersistanceManager persistanceManager;

    /**
     * Constructor The Game Service Manager {@link PersistanceManager}
     *
     * @param persistanceManager The Game Service Manager {@link PersistanceManager}
     */
    public AddressManager(PersistanceManager persistanceManager) {
        this.persistanceManager = persistanceManager;
    }

    /**
     * Method to get a CustomAddress from a player
     *
     * @param addresss The ip address of player
     * @return {@link CustomAddress}
     */
    public CustomAddress get(String addresss)
    {
        MongoClient mongoClient = persistanceManager.getDatabaseManager().getAddressClient();
        MongoCollection<Document> mongoCollection = mongoClient.getDatabase(persistanceManager
                .getDatabaseManager().getAddressDatabase()).getCollection("CustomAddress");

        Document query = new Document("address", addresss);
        Document found = mongoCollection.find(query).first();
        CustomAddress customAddress = null;
        if (found != null)
        {
            customAddress = fromJson(found.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED)
                    .build()));
        }
        else
        {
            customAddress = new CustomAddress(UUID.randomUUID(), addresss, new HashMap<String, Integer>());
            add(customAddress);
        }
        return customAddress;
    }

    /**
     * Method to add CustomAddress to database
     *
     * @param customAddress The CustomAddress {@link CustomAddress}
     */
    public void add(CustomAddress customAddress)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getAddressClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getAddressDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomAddress");
        mongoCollection.insertOne(Document.parse(toJson(customAddress)));
    }

    /**
     * Method to remove CustomAddres from database
     *
     * @param customAddress The CustomAddress {@link CustomAddress}
     */
    public void remove(CustomAddress customAddress)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getAddressClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getAddressDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomAddress");
        mongoCollection.findOneAndDelete(Document.parse(toJson(customAddress)));
    }

    /**
     * Method to update CustomAddress in database
     *
     * @param customAddress The CustomAddress {@link CustomAddress}
     */
    public void update(CustomAddress customAddress)
    {
        MongoDatabase mongoDatabase = persistanceManager.getDatabaseManager().getAddressClient()
                                                        .getDatabase(persistanceManager.getDatabaseManager().getAddressDatabase());
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("CustomAddress");
        Document query = new Document("id", customAddress.getId().toString());
        mongoCollection.findOneAndReplace(query, Document.parse(toJson(customAddress)));
    }

    /**
     * Method to transform CustomAddress into JSON String
     *
     * @param customAddress The CustomAddress {@link CustomAddress}
     * @return {@link String}
     */
    public String toJson(CustomAddress customAddress)
    {
        return new Gson().toJson(customAddress);
    }

    /**
     * Method to transform JSON string into CustomAddress
     *
     * @param json the JSON string
     * @return {@link CustomAddress}
     */
    public CustomAddress fromJson(String json)
    {
        return new Gson().fromJson(json, CustomAddress.class);
    }
}
