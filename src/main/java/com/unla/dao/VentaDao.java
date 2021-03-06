package com.unla.dao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.BSONObject;
import org.bson.BsonNull;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
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
			System.out.println("No hay ninguna venta con este nroTicket");
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
	
	public List<Venta> ventasCadaSucursalPorFecha(LocalDate fechaDesde, LocalDate fechaHasta) {
		Document query = new Document();
        query.append("$and", Arrays.asList(
                new Document()
                        .append("fecha", new Document()
							.append("$gt", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaDesde)))
                        ),
                new Document()
					.append("fecha", new Document()
						.append("$lt", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaHasta)))
					)
            )
        );
        List<Venta> ventas = new ArrayList<Venta>();
        FindIterable<Document> traidos = collection.find(query);
        if(traidos==null) {
			System.out.println("No hay ninguna venta entre las fechas indicadas");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			while(cursor.hasNext()) {
				ventas.add(deserealizar(cursor.next().toJson()));
			}
			cursor.close();
		}
        return ventas;
	}
	
	/*
	 * Representa el total de cada venta(la suma de los subtotales de cada detalleVenta)
	 * Retornará una colección de objetos como el ejemplo sig:
	 * [{_id: '0001-0000001', total: 1000}, ...]
	 */
	public List<Document> totalCadaVentaEntreFecha(LocalDate fechaDesde, LocalDate fechaHasta) {
		List<? extends Bson> pipeline = Arrays.asList(
										new Document()
											.append("$match", new Document()
												.append("fecha", new Document()
													.append("$gte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaDesde)))
													.append("$lte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaHasta)))
												)
											), 
										new Document()
											.append("$unwind", "$detalleVentas"), 
										new Document()
											.append("$group", new Document()
												.append("_id", "$nroTicket")
												.append("total", new Document()
														.append("$sum", "$detalleVentas.subTotal")
												)
											),
										new Document()
											.append("$sort", new Document()
												.append("_id", 1.0)
											)
										);
		List<Document> totalesVentas = new ArrayList<Document>();
        AggregateIterable<Document> traidos = collection.aggregate(pipeline);
		if(traidos==null) {
			System.out.println("No hay ninguna venta entre las fechas indicadas");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			while(cursor.hasNext()) {
				totalesVentas.add(cursor.next());
			}
			cursor.close();
		}
		return totalesVentas;
	}

	/* 1ro
	 * Obtenemos una colecci�n de las ventas de cada local junto con sus totales
	 * dado 2 fechas. Tambi�n se obtiene la suma de los totales de cada local
	 * para obtener el 'totalTodo' que es el total de la cadena completa
	 */
	public Document detalleYTotalVentasSucursalesEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
		List<? extends Bson> pipeline = Arrays.asList(
										new Document()
											.append("$match", new Document()
												.append("fecha", new Document()
													.append("$gte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaDesde)))
													.append("$lte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaHasta)))
												)
											), 
										new Document()
			                            	.append("$unwind", "$detalleVentas"), 
			                            new Document()
				                            .append("$group", new Document()
			                                    .append("_id", new Document()
		                                            .append("$substr", Arrays.asList(
		                                                    "$nroTicket",
		                                                    0.0,
		                                                    new Document()
	                                                            .append("$indexOfBytes", Arrays.asList(
	                                                                    "$nroTicket",
	                                                                    "-"
	                                                                )
	                                                            )
		                                            		)
			                                            )
				                                    )
			                                    .append("detalles", new Document()
		                                            .append("$push", "$detalleVentas")
			                                    )
			                                    .append("totalSucursal", new Document()
		                                            .append("$sum", "$detalleVentas.subTotal")
			                                    )
				                            ), 
					                    new Document()
				                            .append("$project", new Document()
			                                    .append("_id", 0.0)
			                                    .append("nroSucursal", "$_id")
			                                    .append("detalles", 1.0)
			                                    .append("totalSucursal", 1.0)
				                            ), 
				                        new Document()
				                            .append("$sort", new Document()
				                                    .append("nroSucursal", 1.0)
				                            ), 
					                    new Document()
				                            .append("$group", new Document()
			                                    .append("_id", new BsonNull())
			                                    .append("ventasSucursales", new Document()
			                                            .append("$push", "$$ROOT")
			                                    )
			                                    .append("totalTodo", new Document()
			                                            .append("$sum", "$totalSucursal")
			                                    )
				                            ), 
					                    new Document()
				                            .append("$project", new Document()
				                                    .append("_id", 0.0)
				                                    .append("ventasSucursales", 1.0)
				                                    .append("totalTodo", 1.0)
				                            )
										);
		Document totalesVentas = null;
        AggregateIterable<Document> traidos = collection.aggregate(pipeline);
		if(traidos==null) {
			System.out.println("No hay ninguna venta entre las fechas indicadas");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			if(cursor.hasNext()) {
				totalesVentas = cursor.next();
			}
			cursor.close();
		}
		return totalesVentas;
	}
	
	// 4to
	public List<Document> detallesVentaEntreFechaPorTipo(LocalDate fechaDesde, LocalDate fechaHasta) {
		List<? extends Bson> pipeline = Arrays.asList(
						                new Document()
						                        .append("$match", new Document()
						                                .append("fecha", new Document()
						                                		.append("$gte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaDesde)))
																.append("$lte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaHasta)))
						                                )
						                        ), 
						                new Document()
						                        .append("$unwind", "$detalleVentas"), 
						                new Document()
						                        .append("$project", new Document()
						                                .append("_id", 0.0)
						                                .append("nroSucursal", new Document()
						                                        .append("$substr", Arrays.asList(
						                                                "$nroTicket",
						                                                0.0,
						                                                new Document()
						                                                        .append("$indexOfBytes", Arrays.asList(
						                                                                "$nroTicket",
						                                                                "-"
						                                                            )
						                                                        )
						                                            )
						                                        )
						                                )
						                                .append("detalleVentas", 1.0)
						                        ), 
						                new Document()
						                        .append("$group", new Document()
						                                .append("_id", "$nroSucursal")
						                                .append("nroSucursal", new Document()
						                                        .append("$first", "$nroSucursal")
						                                )
						                                .append("detalleVentas", new Document()
						                                        .append("$push", "$detalleVentas")
						                                )
						                        ), 
						                new Document()
						                        .append("$unwind", "$detalleVentas"), 
						                new Document()
						                        .append("$group", new Document()
						                                .append("_id", "$detalleVentas.producto.codigo")
						                                .append("producto", new Document()
						                                        .append("$first", "$detalleVentas.producto")
						                                )
						                                .append("sucursalVendio", new Document()
						                                        .append("$push", new Document()
						                                                .append("sucursal", "$nroSucursal")
						                                                .append("cantidad", "$detalleVentas.cantidad")
						                                        )
						                                )
						                        ), 
						                new Document()
						                        .append("$project", new Document()
						                                .append("_id", new BsonNull())
						                                .append("codProducto", "$_id")
						                                .append("producto", 1.0)
						                                .append("sucursalVendio", 1.0)
						                        ), 
						                new Document()
						                        .append("$group", new Document()
						                                .append("_id", "$producto.tipoProducto")
						                                .append("productos", new Document()
						                                        .append("$push", new Document()
						                                                .append("producto", "$producto")
						                                                .append("sucursalVendio", "$sucursalVendio")
						                                        )
						                                )
						                        ), 
				                        new Document()
			                            .append("$project", new Document()
			                                    .append("_id", 0.0)
			                                    .append("tipoProducto", "$_id")
			                                    .append("productos", 1.0)
			                            )
						        );
		List<Document> detalleVentas = new ArrayList<Document>();
        AggregateIterable<Document> traidos = collection.aggregate(pipeline);
		if(traidos==null) {
			System.out.println("No hay ninguna venta entre las fechas indicadas");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			while(cursor.hasNext()) {
				detalleVentas.add(cursor.next());
			}
			cursor.close();
		}
		return detalleVentas;
	}
	
	// 2do
	public List<Document> traerVentasPorObraSocialEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
		List<? extends Bson> pipeline = Arrays.asList(
                new Document()
                        .append("$match", new Document()
                                .append("fecha", new Document()
                                		.append("$gte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaDesde)))
										.append("$lte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaHasta)))
                                )
                        ), 
                new Document()
                        .append("$match", new Document()
                                .append("cliente.obraSocial", new Document()
                                        .append("$exists", true)
                                )
                        ), 
                new Document()
                        .append("$group", new Document()
                                .append("_id", new Document()
                                        .append("nroSucursal", new Document()
                                                .append("$substr", Arrays.asList(
                                                        "$nroTicket",
                                                        0.0,
                                                        new Document()
                                                                .append("$indexOfBytes", Arrays.asList(
                                                                        "$nroTicket",
                                                                        "-"
                                                                    )
                                                                )
                                                    )
                                                )
                                        )
                                        .append("obraSocial", "$cliente.obraSocial.nombre")
                                )
                                .append("detallesVentas", new Document()
                                        .append("$push", "$detalleVentas")
                                )
                                .append("total", new Document()
                                        .append("$sum", "$precioTotal")
                                )
                        ), 
                new Document()
                        .append("$sort", new Document()
                                .append("_id", 1.0)
                        )
        );
		List<Document> detalleVentas = new ArrayList<Document>();
        AggregateIterable<Document> traidos = collection.aggregate(pipeline);
		if(traidos==null) {
			System.out.println("No hay ninguna venta entre las fechas indicadas");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			while(cursor.hasNext()) {
				detalleVentas.add(cursor.next());
			}
			cursor.close();
		}
		return detalleVentas;
	}
	
	// 3ro
	public List<Document> traerCobranzasPorMedioPagoEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
		List<? extends Bson> pipeline = Arrays.asList(
                new Document()
                        .append("$match", new Document()
                                .append("fecha", new Document()
                                		.append("$gte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaDesde)))
										.append("$lte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaHasta)))
                                )
                        ), 
                new Document()
                        .append("$group", new Document()
                                .append("_id", new Document()
                                        .append("nroSucursal", new Document()
                                                .append("$substr", Arrays.asList(
                                                        "$nroTicket",
                                                        0.0,
                                                        new Document()
                                                                .append("$indexOfBytes", Arrays.asList(
                                                                        "$nroTicket",
                                                                        "-"
                                                                    )
                                                                )
                                                    )
                                                )
                                        )
                                        .append("formaPago", "$formaPago")
                                )
                                .append("detallesVentas", new Document()
                                        .append("$push", "$detalleVentas")
                                )
                                .append("total", new Document()
                                        .append("$sum", "$precioTotal")
                                )
                        ), 
                new Document()
                        .append("$sort", new Document()
                                .append("_id", 1.0)
                        )
        );
		List<Document> detalleVentas = new ArrayList<Document>();
        AggregateIterable<Document> traidos = collection.aggregate(pipeline);
		if(traidos==null) {
			System.out.println("No hay ninguna venta entre las fechas indicadas");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			while(cursor.hasNext()) {
				detalleVentas.add(cursor.next());
			}
			cursor.close();
		}
		return detalleVentas;
	}
	
	// 5to
	public List<Document> traerRankingProductosPorMontoEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
		List<? extends Bson> pipeline = Arrays.asList(
                new Document()
                        .append("$match", new Document()
                                .append("fecha", new Document()
                                		.append("$gte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaDesde)))
										.append("$lte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaHasta)))
                                )
                        ), 
                new Document()
                        .append("$group", new Document()
                                .append("_id", new Document()
                                        .append("$substr", Arrays.asList(
                                                "$nroTicket",
                                                0.0,
                                                new Document()
                                                        .append("$indexOfBytes", Arrays.asList(
                                                                "$nroTicket",
                                                                "-"
                                                            )
                                                        )
                                            )
                                        )
                                )
                                .append("detallesVentas", new Document()
                                        .append("$push", "$detalleVentas")
                                )
                                .append("total", new Document()
                                        .append("$sum", "$precioTotal")
                                )
                        ), 
                new Document()
                        .append("$sort", new Document()
                                .append("_id", 1.0)
                        ), 
                new Document()
                        .append("$unwind", "$detallesVentas"), 
                new Document()
                        .append("$unwind", "$detallesVentas"), 
                new Document()
                        .append("$group", new Document()
                                .append("_id", new Document()
                                        .append("nroSucursal", "$_id")
                                        .append("producto", "$detallesVentas.producto.codigo")
                                )
                                .append("total", new Document()
                                        .append("$sum", new Document()
                                                .append("$multiply", Arrays.asList(
                                                        "$detallesVentas.producto.precio",
                                                        "$detallesVentas.cantidad"
                                                    )
                                                )
                                        )
                                )
                        ), 
                new Document()
                        .append("$sort", new Document()
                                .append("_id.nroSucursal", 1.0)
                                .append("total", -1.0)
                        )
        );
		List<Document> detalleVentas = new ArrayList<Document>();
        AggregateIterable<Document> traidos = collection.aggregate(pipeline);
		if(traidos==null) {
			System.out.println("No hay ninguna venta entre las fechas indicadas");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			while(cursor.hasNext()) {
				detalleVentas.add(cursor.next());
			}
			cursor.close();
		}
		return detalleVentas;
	}
	
	// 6to
	public List<Document> traerRankingProductosPorCantidadEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
		List<? extends Bson> pipeline = Arrays.asList(
                new Document()
                        .append("$match", new Document()
                                .append("fecha", new Document()
                                		.append("$gte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaDesde)))
										.append("$lte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaHasta)))
                                )
                        ), 
                new Document()
                        .append("$group", new Document()
                                .append("_id", new Document()
                                        .append("$substr", Arrays.asList(
                                                "$nroTicket",
                                                0.0,
                                                new Document()
                                                        .append("$indexOfBytes", Arrays.asList(
                                                                "$nroTicket",
                                                                "-"
                                                            )
                                                        )
                                            )
                                        )
                                )
                                .append("detallesVentas", new Document()
                                        .append("$push", "$detalleVentas")
                                )
                                .append("total", new Document()
                                        .append("$sum", "$precioTotal")
                                )
                        ), 
                new Document()
                        .append("$sort", new Document()
                                .append("_id", 1.0)
                        ), 
                new Document()
                        .append("$unwind", "$detallesVentas"), 
                new Document()
                        .append("$unwind", "$detallesVentas"), 
                new Document()
                        .append("$group", new Document()
                                .append("_id", new Document()
                                        .append("nroSucursal", "$_id")
                                        .append("producto", "$detallesVentas.producto.codigo")
                                )
                                .append("total", new Document()
                                        .append("$sum", "$detallesVentas.cantidad")
                                )
                        ), 
                new Document()
                        .append("$sort", new Document()
                                .append("_id.nroSucursal", 1.0)
                                .append("total", -1.0)
                        )
        );
        List<Document> detalleVentas = new ArrayList<Document>();
        AggregateIterable<Document> traidos = collection.aggregate(pipeline);
		if(traidos==null) {
			System.out.println("No hay ninguna venta entre las fechas indicadas");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			while(cursor.hasNext()) {
				detalleVentas.add(cursor.next());
			}
			cursor.close();
		}
		return detalleVentas;
	}
	
	// 7mo
	public List<Document> traerRankingClientesPorMontoEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
		List<? extends Bson> pipeline = Arrays.asList(
                new Document()
                        .append("$match", new Document()
                                .append("fecha", new Document()
                                		.append("$gte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaDesde)))
										.append("$lte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaHasta)))
                                )
                        ), 
                new Document()
                        .append("$group", new Document()
                                .append("_id", new Document()
                                        .append("nroSucursal", new Document()
                                                .append("$substr", Arrays.asList(
                                                        "$nroTicket",
                                                        0.0,
                                                        new Document()
                                                                .append("$indexOfBytes", Arrays.asList(
                                                                        "$nroTicket",
                                                                        "-"
                                                                    )
                                                                )
                                                    )
                                                )
                                        )
                                        .append("cliente", "$cliente")
                                )
                                .append("total", new Document()
                                        .append("$sum", "$precioTotal")
                                )
                        ), 
                new Document()
                        .append("$sort", new Document()
                                .append("_id", 1.0)
                        ), 
                new Document()
                        .append("$sort", new Document()
                                .append("_id.nroSucursal", 1.0)
                                .append("total", -1.0)
                        )
        );
        List<Document> rankingClientes = new ArrayList<Document>();
        AggregateIterable<Document> traidos = collection.aggregate(pipeline);
		if(traidos==null) {
			System.out.println("No hay ninguna venta entre las fechas indicadas");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			while(cursor.hasNext()) {
				rankingClientes.add(cursor.next());
			}
			cursor.close();
		}
		return rankingClientes;
	}
	
	// 8vo
	public List<Document> traerRankingClientesPorCantidadEntreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
		List<? extends Bson> pipeline = Arrays.asList(
                new Document()
                        .append("$match", new Document()
                                .append("fecha", new Document()
                                		.append("$gte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaDesde)))
										.append("$lte", MongoUtil.jsonToBSONObject(new Gson().toJson(fechaHasta)))
                                )
                        ), 
                new Document()
                        .append("$unwind", "$detalleVentas"), 
                new Document()
                        .append("$group", new Document()
                                .append("_id", new Document()
                                        .append("nroSucursal", new Document()
                                                .append("$substr", Arrays.asList(
                                                        "$nroTicket",
                                                        0.0,
                                                        new Document()
                                                                .append("$indexOfBytes", Arrays.asList(
                                                                        "$nroTicket",
                                                                        "-"
                                                                    )
                                                                )
                                                    )
                                                )
                                        )
                                        .append("cliente", "$cliente")
                                )
                                .append("total", new Document()
                                        .append("$sum", "$detalleVentas.cantidad")
                                )
                        ), 
                new Document()
                        .append("$sort", new Document()
                                .append("_id", 1.0)
                        ), 
                new Document()
                        .append("$sort", new Document()
                                .append("_id.nroSucursal", 1.0)
                                .append("total", -1.0)
                        )
        );
        List<Document> rankingClientes = new ArrayList<Document>();
        AggregateIterable<Document> traidos = collection.aggregate(pipeline);
		if(traidos==null) {
			System.out.println("No hay ninguna venta entre las fechas indicadas");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			while(cursor.hasNext()) {
				rankingClientes.add(cursor.next());
			}
			cursor.close();
		}
		return rankingClientes;
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