package com.company;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;


public class App 
{
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017")); // Puerto por defecto
        MongoDatabase db = mongoClient.getDatabase("test");
        System.out.println("La base de datos ha sido cargada correctamente.");
    }
}
