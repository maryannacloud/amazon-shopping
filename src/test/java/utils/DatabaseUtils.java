package utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

public class DatabaseUtils {

    private MongoClient mongoClient;
    private MongoDatabase database;

    public DatabaseUtils(String connectionString, String databaseName) {
        try {
            ConnectionString connectionUrl = new ConnectionString(connectionString);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionUrl)
                    .build();
            mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase(databaseName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize MongoDB connection: " + e.getMessage(), e);
        }
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    public void insertDocument(String collectionName, Document document) {
        MongoCollection<Document> collection = getCollection(collectionName);
        collection.insertOne(document);
    }

    public void insertDocuments(String collectionName, List<Document> documents) {
        MongoCollection<Document> collection = getCollection(collectionName);
        collection.insertMany(documents);
    }

    public List<Document> getAllDocuments(String collectionName) {
        MongoCollection<Document> collection = getCollection(collectionName);
        return collection.find().into(new java.util.ArrayList<>());
    }

    public List<Document> findDocuments(String collectionName, Document filter) {
        MongoCollection<Document> collection = getCollection(collectionName);
        return collection.find(filter).into(new java.util.ArrayList<>());
    }

    public void updateDocuments(String collectionName, Document filter, Document update) {
        MongoCollection<Document> collection = getCollection(collectionName);
        collection.updateMany(filter, new Document("$set", update));
    }

    public void deleteDocuments(String collectionName, Document filter) {
        MongoCollection<Document> collection = getCollection(collectionName);
        collection.deleteMany(filter);
    }

    public void dropCollection(String collectionName) {
        MongoCollection<Document> collection = getCollection(collectionName);
        collection.drop();
    }

    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
