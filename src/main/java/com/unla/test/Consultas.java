package com.unla.test;

import java.time.LocalDate;
import java.util.ArrayList;
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
		System.out.println("************************************************************************************");
		System.out.println("1.Detalle y totales de ventas para la cadena completa y por sucursal, entre fechas. ");
		System.out.println("************************************************************************************\n");
		LocalDate fechaDesde = LocalDate.of(2020, 1, 1);
		LocalDate fechaHasta = LocalDate.of(2020, 6, 30);
		/* VERSION VIEJA
		List<Venta> ventasEntreFechas = ventaABM.traerEntreFechas(fechaDesde, fechaHasta);
		String jsonVentas = new Gson().toJson(ventasEntreFechas);
		String jsonDetallesVentas = "";
		System.out.println("Detalles de todas las ventas: ");
		System.out.println("********************************************");
		for(Venta venta: ventasEntreFechas) {
			List<DetalleVenta> detalles = venta.getDetalleVentas();
			String[] parts = venta.getNroTicket().split("-");
			Long nroSucursal = Long.parseLong(parts[0]);
			Long nroVenta =  Long.parseLong(parts[1]);
			System.out.println("Venta nro: " + nroVenta + "\nDetalles de la venta(Sucursal "+ nroSucursal + "): " + new Gson().toJson(detalles));
		}
		// Lista de cada venta con sus totales
		// Ej -> [{_id: "0001-00000001", total: 4000}, ...]
		List<Document> totalesVentas = ventaABM.totalCadaVentaEntreFecha(fechaDesde, fechaHasta);
		// Metemos los valores de 'total' de cada venta en una lista
		List<Double> totalCadaVenta = JsonPath.read(new Gson().toJson(totalesVentas), "$..[*].total");
		double totalTodaVentas = totalCadaVenta.stream().mapToDouble(f -> f.doubleValue()).sum();
		System.out.println("\nEl total de la cadena completa es: " + totalTodaVentas);
		*/
		// Recibiremos un objeto del tipo: {'ventasSucursales': [...], 'totalTodo': 34234}
		Document totalDetallesVentas = ventaABM.detalleYTotalVentasSucursalesEntreFechas(fechaDesde, fechaHasta);
		List<Document> ventasSucursales = (List<Document>)totalDetallesVentas.get("ventasSucursales"); // Obtenemos el array de las ventas de cada sucursal
		System.out.println("Ventas entre las fechas " + fechaDesde + " y " + fechaHasta);
		for(Document vs: ventasSucursales) {
			List<Document> detallesVentas = (List<Document>)vs.get("detalles");
			Double totalSucursal = vs.getDouble("totalSucursal");
			Integer nroSucursal = Integer.parseInt(vs.getString("nroSucursal"));
			System.out.println("\n\nVentas de la sucursal N°" + nroSucursal);
			System.out.println("***************************");
			System.out.println("Ventas de la sucursal:\n" + detallesVentas);
			System.out.println("\nEl total de la sucursal actual es: " + totalSucursal);
		}
		System.out.println("\n\nEl total de la cadena completa es: $" + totalDetallesVentas.get("totalTodo"));
		
		System.out.println("\n\n\n\n***************************************************************************************************************");
		System.out.println("2.Detalle y totales de ventas para la cadena completa y por sucursal, por obra social o privados entre fechas. ");
		System.out.println("***************************************************************************************************************\n");
	}
}

/*
1.Detalle y totales de ventas para la cadena completa y por sucursal, entre fechas. 
2.Detalle y totales de ventas para la cadena completa y por sucursal, por obra social o privados entre fechas. 
3.Detalle y totales de cobranza para la cadena completa y por sucursal, por medio de pago y entre fechas. 
4.Detalle y totales de ventas de productos, total de la cadena y por sucursal, entre fechas, diferenciados entre farmacia y perfumerï¿½a. 
5.Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por monto. 
6.Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por cantidad vendida.
7.Ranking de clientes por compras, total de la cadena y por sucursal, entre fechas, por monto. 
8.Ranking de clientes por compras, total de la cadena y por sucursal, entre fechas, por cantidad vendida. 
*/