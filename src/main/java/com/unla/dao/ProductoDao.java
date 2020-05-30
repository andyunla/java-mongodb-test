package com.unla.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.BSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.unla.datos.Producto;

public class ProductoDao {
	private static ProductoDao instancia = null;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	private Gson gson;
	
	protected ProductoDao() {
		gson = new Gson();
		db = MongoUtil.getDatabase();
		collection = db.getCollection("productos");
	}
	
	public static ProductoDao getInstance() {
		if(instancia == null) {
			instancia = new ProductoDao();
		}
		return instancia;
	}
	
	public Producto deserealizar(String json) {
		return gson.fromJson(json, Producto.class);
	}
	
	public Producto traer(int codigo) {
		Producto producto = null;
		String json = "{codigo: "+ codigo +"}";
		BSONObject bson = (BSONObject)com.mongodb.util.JSON.parse(json);
		FindIterable<Document> traidos = collection.find((Bson) bson);
		if(traidos==null) {
			System.out.println("No hay ningun producto con ese codigo");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			if(cursor.hasNext())
				producto = deserealizar(cursor.next().toJson());
			cursor.close();
		}
		return producto;
	}
	
	public List<Producto> traer() {
		List<Producto> lista = new ArrayList<Producto>();
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
	
	public void agregar(Producto objeto) {
		String json = gson.toJson(objeto);
		Document doc = Document.parse(json);
		collection.insertOne(doc);
	}
	
	public void modificar(Producto objetoModificado) {
		BasicDBObject query = new BasicDBObject();
		query.put("codigo", objetoModificado.getCodigo());
		
		BasicDBObject newDocument = new BasicDBObject();
		String json = gson.toJson(objetoModificado);
		BSONObject bson = MongoUtil.jsonToBSONObject(json);
		newDocument.putAll(bson);
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$set", newDocument);

		collection.updateOne(query, updateObject);
	}
	
	public long eliminar(Producto objeto) {
		String json = "{codigo: '" + objeto.getCodigo() + "'}";
		BSONObject bson = (BSONObject)com.mongodb.util.JSON.parse(json);
		DeleteResult result = collection.deleteOne((Bson)bson);
		return result.getDeletedCount();
	}
}
