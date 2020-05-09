package com.unla.test;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;


public class App {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017")); // Puerto por defecto        
        MongoDatabase db = mongoClient.getDatabase("farmacias");
        
        MongoCollection<Document> collection = db.getCollection("clientes");
        Document doc = new Document("nombre", "Juan")
                			.append("apellido", "Perez")
                			.append("edad", 40);
        collection.insertOne(doc);
        System.out.println("The bomb has been planted!");
    }
}
