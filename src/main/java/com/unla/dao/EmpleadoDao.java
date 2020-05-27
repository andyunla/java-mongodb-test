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

import com.unla.datos.Empleado;

public class EmpleadoDao {
	private static EmpleadoDao instancia = null;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	private Gson gson;
	
	protected EmpleadoDao() {
		gson = new Gson();
		db = MongoUtil.getDatabase();
		collection = db.getCollection("empleados");
	}
	
	public static EmpleadoDao getInstance() {
		if(instancia == null) {
			instancia = new EmpleadoDao();
		}
		return instancia;
	}
	
	public Empleado deserealizar(String json) {
		return gson.fromJson(json, Empleado.class);
	}
	
	public Empleado traer(String cuil) {
		Empleado empleado = null;
		String json = "{cuil: " + cuil + "}";
		BSONObject bson = (BSONObject)com.mongodb.util.JSON.parse(json);
		FindIterable<Document> traidos = collection.find((Bson) bson);
		if(traidos==null) {
			System.out.println("No hay ningun empleado con ese cuil");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			empleado = deserealizar(cursor.next().toJson());
			cursor.close();
		}
		return empleado;
	}
	
	public List<Empleado> traer() {
		List<Empleado> lista = new ArrayList<Empleado>();
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
	
	public void agregar(Empleado objeto) {
		String json = gson.toJson(objeto);
		Document doc = Document.parse(json);
		collection.insertOne(doc);
	}
	
	public void modificar(Empleado objetoModificado) {
		BasicDBObject query = new BasicDBObject();
		query.put("cuil", objetoModificado.getCuil());
		
		BasicDBObject newDocument = new BasicDBObject();
		String json = gson.toJson(objetoModificado);
		BSONObject bson = MongoUtil.jsonToBSONObject(json);
		newDocument.putAll(bson);
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$set", newDocument);

		collection.updateOne(query, updateObject);
	}
	
	public long eliminar(Empleado objeto) {
		String json = "{cuil: '" + objeto.getCuil() + "'}";
		BSONObject bson = (BSONObject)com.mongodb.util.JSON.parse(json);
		DeleteResult result = collection.deleteOne((Bson)bson);
		return result.getDeletedCount();
	}
}
