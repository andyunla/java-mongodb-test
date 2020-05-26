package com.unla.dao;

import org.bson.BSONObject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoUtil {
	private static MongoClient mongoClient = null;
	private static MongoDatabase database = null;
	private static final long PORT = 27017;
	private static final String DATABASE = "farmacias_unla";
	
	private static void iniciaOperacion() throws Exception {
		if(mongoClient == null) {
			mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:" + PORT));
			database = mongoClient.getDatabase(DATABASE);
		}
	} 
	
	public static MongoDatabase getDatabase() {
		try {
			iniciaOperacion();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return database;
	}
	
	public static BSONObject jsonToBSONObject(String json) {
		return (BSONObject)com.mongodb.util.JSON.parse(json);
	}
}
