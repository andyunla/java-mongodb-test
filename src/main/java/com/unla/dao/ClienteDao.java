package com.unla.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.BSON;
import org.bson.BSONObject;
import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.unla.datos.Cliente;

public class ClienteDao {
	private static ClienteDao instancia = null;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	private Gson gson;
	
	protected ClienteDao() {
		gson = new Gson();
		db = MongoUtil.getDatabase();
		collection = db.getCollection("clientes");
	}
	
	public static ClienteDao getInstance() {
		if(instancia == null) {
			instancia = new ClienteDao();
		}
		return instancia;
	}
	
	public Cliente deserealizar(String json) {
		return gson.fromJson(json, Cliente.class);
	}
	
	public Cliente traer(int dni) {
		String json = "";
		Cliente cliente = (Cliente) gson.fromJson(json, Cliente.class);
		return cliente;
	}
	
	public List<Cliente> traer() {
		List<Cliente> lista = new ArrayList<Cliente>();
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
	
	public List<Cliente> traerV2() {
		List<Cliente> lista = new ArrayList<Cliente>();
		MongoCollection<Cliente> colleccion = db.getCollection("clientes", Cliente.class);
		FindIterable<Cliente> fi = colleccion.find();
        MongoCursor<Cliente> cursor = fi.iterator();
        try {
            while(cursor.hasNext()) {
                lista.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
		return lista;
	}
	
	public boolean agregar(Cliente objeto) {
		boolean estado = false;
		// String json = gson.toJson(objeto);
		String json = gson.toJsonTree(objeto).getAsJsonObject().remove("id").toString();
		Document doc = new Document().parse(json);
		try { // DEBUG: Agregar excepcion
			collection.insertOne(doc);
			estado = true;
		} catch(Exception e) {
			estado = false;
		}
		return estado;
	}
	
	public boolean modificar(Cliente objetoModificado) {
		boolean estado = false;
		BasicDBObject query = new BasicDBObject();
		query.put("dni", objetoModificado.getDni());
		
		BasicDBObject newDocument = new BasicDBObject();
		String json = gson.toJson(objetoModificado);
		BSONObject bson = MongoUtil.jsonToBSONObject(json);
		newDocument.putAll(bson);
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$set", newDocument);

		collection.updateOne(query, updateObject);
		return estado;
	}
	
	public boolean eliminar(Cliente objeto) {
		boolean estado = false;
		
		
		return estado;
	}
}
