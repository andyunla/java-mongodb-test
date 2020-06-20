package com.unla.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.BSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;

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
	public static void main(String ... args) {
		VentaABM ventaABM = VentaABM.getInstance();
		List<Venta> listaVentas = ventaABM.traer();
		String jsonVentas = new Gson().toJson(listaVentas);
		System.out.println("\n\n\nLISTA DETALLEVENTAS: " + JsonPath.read(jsonVentas, "$..[*].detalleVentas"));
		/*
		List<String> cantidades = JsonPath.read(jsonVentas, "$..ventas[*].cantidad");
		System.out.println(cantidades);
		*/
		
		/*

		
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
4.Detalle y totales de ventas de productos, total de la cadena y por sucursal, entre fechas, diferenciados entre farmacia y perfumería. 
5.Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por monto. 
6.Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por cantidad vendida.
7.Ranking de clientes por compras, total de la cadena y por sucursal, entre fechas, por monto. 
8.Ranking de clientes por compras, total de la cadena y por sucursal, entre fechas, por cantidad vendida. 
*/