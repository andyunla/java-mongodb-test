package com.unla.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.BSON;
import org.bson.BSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.MongoException;

import com.unla.datos.Sucursal;

public class SucursalDao {
	private static SucursalDao instancia = null;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	private Gson gson;
	
	protected SucursalDao() {
		gson = new Gson();
		db = MongoUtil.getDatabase();
		collection = db.getCollection("sucursal");
	}
	
	public static SucursalDao getInstance() {
		if(instancia == null) {
			instancia = new SucursalDao();
		}
		return instancia;
	}
	
	public Sucursal deserealizar(String json) {
		return gson.fromJson(json, Sucursal.class);
	}
	
	public Sucursal traer(int id) {
		Sucursal sucursal = null;
		String json = "{id: '"+id+"'}";
		BSONObject bson = (BSONObject)com.mongodb.util.JSON.parse(json);
		FindIterable<Document> traidos = collection.find((Bson) bson);
		if(traidos==null) {
			System.out.println("No hay ninguna sucursal con ese id");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			sucursal = deserealizar(cursor.next().toJson());
			cursor.close();
		}
		return sucursal;
	}
	
	public List<Sucursal> traer() {
		List<Sucursal> lista = new ArrayList<Sucursal>();
		FindIterable<Document> fi = collection.find();
        MongoCursor<Document> cursor = fi.iterator();
        try {
            while(cursor.hasNext()) {
                lista.add(deserealizar(cursor.next().toJson()));
            }
        } finally {
            cursor.close();
        }
		return lista;
	}
	
	public void agregar(Sucursal objeto) {
		String json = gson.toJson(objeto);
		Document doc = new Document().parse(json);
		collection.insertOne(doc);
	}
	
	public void modificar(Sucursal objetoModificado) {
		BasicDBObject query = new BasicDBObject();
		query.put("id", objetoModificado.getId());
		
		BasicDBObject newDocument = new BasicDBObject();
		String json = gson.toJson(objetoModificado);
		BSONObject bson = MongoUtil.jsonToBSONObject(json);
		newDocument.putAll(bson);
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$set", newDocument);

		collection.updateOne(query, updateObject);
	}
	
	public long eliminar(Sucursal objeto) {
		String json = "{id: '" + objeto.getId() + "'}";
		BSONObject bson = (BSONObject)com.mongodb.util.JSON.parse(json);
		DeleteResult result = collection.deleteOne((Bson)bson);
		return result.getDeletedCount();
	}
}
