package com.unla.dao;
import java.time.LocalDate;
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
import com.unla.datos.Venta;

public class VentaDao {
	private static VentaDao instancia = null;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	private Gson gson;
	
	protected VentaDao() {
		gson = new Gson();
		db = MongoUtil.getDatabase();
		collection = db.getCollection("ventas");
	}
	
	public static VentaDao getInstance() {
		if(instancia == null) {
			instancia = new VentaDao();
		}
		return instancia;
	}
	
	public Venta deserealizar(String json) {
		return gson.fromJson(json, Venta.class);
	}
	
	public Venta traer(String nroTicket) {
		Venta venta = null;
		String json = "{nroTicket: '" + nroTicket + "'}";
		BSONObject bson = (BSONObject)com.mongodb.util.JSON.parse(json);
		FindIterable<Document> traidos = collection.find((Bson) bson);
		if(traidos==null) {
			System.out.println("No hay ningun venta con este nroTicket");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			if(cursor.hasNext())
				venta = deserealizar(cursor.next().toJson());
			cursor.close();
		}
		return venta;
	}
	
	public List<Venta> traer() {
		List<Venta> lista = new ArrayList<Venta>();
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
	
	public List<Venta> traerEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
		List<Venta> ventas = new ArrayList<Venta>();
		String json = "{fecha: { $gte:"+ new Gson().toJson(fechaDesde) + ", $lte:" + new Gson().toJson(fechaHasta) + "}}";
		BSONObject bson = (BSONObject)com.mongodb.util.JSON.parse(json);
		FindIterable<Document> traidos = collection.find((Bson) bson);
		if(traidos==null) {
			System.out.println("No hay ningun venta entre las fechas indicadas");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			while(cursor.hasNext()) {
				ventas.add(deserealizar(cursor.next().toJson()));
			}
			cursor.close();
		}
		return ventas;
	}
	
	public void agregar(Venta objeto) {
		String json = gson.toJson(objeto);
		Document doc = Document.parse(json);
		collection.insertOne(doc);
	}
	
	public void modificar(Venta objetoModificado) {
		BasicDBObject query = new BasicDBObject();
		query.put("nroTicket", objetoModificado.getNroTicket());
		
		BasicDBObject newDocument = new BasicDBObject();
		String json = gson.toJson(objetoModificado);
		BSONObject bson = MongoUtil.jsonToBSONObject(json);
		newDocument.putAll(bson);
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$set", newDocument);

		collection.updateOne(query, updateObject);
	}
	
	public long eliminar(Venta objeto) {
		String json = "{nroTicket: '" + objeto.getNroTicket() + "'}";
		BSONObject bson = (BSONObject)com.mongodb.util.JSON.parse(json);
		DeleteResult result = collection.deleteOne((Bson)bson);
		return result.getDeletedCount();
	}
}