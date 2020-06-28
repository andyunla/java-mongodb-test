package com.unla.dao;

import org.bson.BSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
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
			System.out.println("No se pudo conectar a la Base de datos: " + e.getMessage());
		}
		return database;
	}
	
	public static BSONObject jsonToBSONObject(String json) {
		return (BSONObject)com.mongodb.util.JSON.parse(json);
	}
	
	public static String executeQuery(String nombreColeccion, String query) {
		// DEBUG
		AggregateIterable<Document> traidos = database.getCollection(nombreColeccion).aggregate(null);
		return null;
	}
}