package com.unla.test;

import java.time.LocalDate;
import java.util.List;

import org.bson.BSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.unla.datos.DetalleVenta;
import com.unla.datos.Empleado;
import com.unla.datos.Venta;
import com.unla.negocio.*;
import com.google.gson.Gson;

//import org.json.JSONException;
//import org.json.JSONObject;

import com.jayway.jsonpath.JsonPath;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

public class Consultas {
	private static VentaABM ventaABM;
	public static void main(String ... args) {
		ventaABM = VentaABM.getInstance();
		
		System.out.println("1.Detalle y totales de ventas para la cadena completa y por sucursal, entre fechas. ");
		System.out.println("************************************************************************************\n");
		LocalDate fechaDesde = LocalDate.of(2020, 1, 1);
		LocalDate fechaHasta = LocalDate.of(2020, 6, 1);
		List<Venta> ventasEntreFechas = ventaABM.traerEntreFechas(fechaDesde, fechaHasta);
		String jsonVentas = new Gson().toJson(ventasEntreFechas);
		System.out.println("\n\n\nLISTA precioTotales: " + JsonPath.read(jsonVentas, "$..[*].precioTotal"));
		String jsonDetallesVentas = "";
		int totales = 0;
		for(Venta venta: ventasEntreFechas) {
			totales += venta.getPrecioTotal();
			List<DetalleVenta> detalles = venta.getDetalleVentas();
			jsonDetallesVentas += new Gson().toJson(detalles) + ",";
			System.out.println("Venta nro: " + venta.getNroTicket() + "\nDetalles de la venta: " + new Gson().toJson(detalles));
		
		}
		
		System.out.println("\n\n\nTOTALES: " + totales + "\nDESDE FUNCION");
		System.out.println("VENTAS ENTRE FECHAS -->:\t" + ventaABM.traerEntreFechas(fechaDesde, fechaHasta));
		
		/*
		// CONSULTAS POR FECHA Y PARA QUE RETORNE TOTALES POR CADA SUCURSAL
		db.collection.find({"fecha":{ $gte:{"year": 2020, "month": 1, "day": 27}, $lte:{"year":2020, "month": 6, "day":1}}}).count();
		db.getCollection("ventas").aggregate([
			{ $match: {
				"fecha":{ $gte:{"year": 2020, "month": 1, "day": 27}, $lte:{"year":2020, "month": 6, "day":1}}}
			},
			{ $unwind: "$detalleVentas" },
			{ $group : {
				_id : "$nroTicket",
				"Total Venta(suma de todos los detalleVentas)" : {$sum : "$detalleVentas.subTotal"}
			}},
			{ $sort : { _id : 1 } }
		])

		*/
		/*
		// Para agregar una coma al final de cada array
		StringBuilder build = new StringBuilder(jsonDetallesVentas);
		build.deleteCharAt(jsonDetallesVentas.length() - 1);
		String newDetalle = new String(build);
		
		//System.out.println("DETALLES JSOn" + jsonDetallesVentas);
		//System.out.println(totales);
		

		
		String json = "{nroTicket: '" +  + "'}";
		BSONObject bson = (BSONObject)com.mongodb.util.JSON.parse(json);
		FindIterable<Document> traidos = collection.find((Bson) bson);
		if(traidos==null) {
			System.out.println("No hay ningun empleado con ese cuil");
		} else {
			MongoCursor<Document> cursor = traidos.iterator();
			if(cursor.hasNext())
				empleado = deserealizar(cursor.next().toJson());
			cursor.close();
		}
		*/
	}
}

/*
1.Detalle y totales de ventas para la cadena completa y por sucursal, entre fechas. 
2.Detalle y totales de ventas para la cadena completa y por sucursal, por obra social o privados entre fechas. 
3.Detalle y totales de cobranza para la cadena completa y por sucursal, por medio de pago y entre fechas. 
4.Detalle y totales de ventas de productos, total de la cadena y por sucursal, entre fechas, diferenciados entre farmacia y perfumer�a. 
5.Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por monto. 
6.Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por cantidad vendida.
7.Ranking de clientes por compras, total de la cadena y por sucursal, entre fechas, por monto. 
8.Ranking de clientes por compras, total de la cadena y por sucursal, entre fechas, por cantidad vendida. 
*/