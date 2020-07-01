package com.unla.funciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.BSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.unla.datos.DetalleVenta;
import com.unla.datos.Empleado;
import com.unla.datos.Producto;
import com.unla.datos.Venta;
import com.unla.negocio.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import org.json.JSONException;
//import org.json.JSONObject;

import com.jayway.jsonpath.JsonPath;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

public class Consultas {
	private static LoggerWrapper logger = LoggerWrapper.getInstance("Consultas");
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Para que el JSON esté formateado
	private static VentaABM ventaABM = VentaABM.getInstance();
	
	public static void consulta1(LocalDate fechaDesde, LocalDate fechaHasta) {
		logger.info("************************************************************************************");
		logger.info("1.Detalle y totales de ventas para la cadena completa y por sucursal, entre fechas. ");
		logger.info("************************************************************************************\n");
		// Recibiremos un objeto del tipo: {'ventasSucursales': [...], 'totalTodo': 34234}
		Document totalDetallesVentas = ventaABM.detalleYTotalVentasSucursalesEntreFechas(fechaDesde, fechaHasta);
		List<Document> ventasSucursales = (List<Document>)totalDetallesVentas.get("ventasSucursales"); // Obtenemos el array de las ventas de cada sucursal
		logger.warning("Ventas entre las fechas " + fechaDesde + " y " + fechaHasta);
		for(Document vs: ventasSucursales) {
			List<Document> detallesVentas = (List<Document>)vs.get("detalles");
			Double totalSucursal = vs.getDouble("totalSucursal");
			Integer nroSucursal = Integer.parseInt(vs.getString("nroSucursal"));
			logger.info("Ventas de la sucursal N°" + nroSucursal);
			logger.info("***************************");
			logger.info("Detalles de las ventas:\n" + gson.toJson(detallesVentas));
			logger.info("El total de la sucursal actual es: $" + totalSucursal + "\n\n");
		}
		logger.info("El total de la cadena completa es: $" + totalDetallesVentas.get("totalTodo") + "\n\n");
	}
	
	public static void consulta2(LocalDate fechaDesde, LocalDate fechaHasta) {
		logger.error("***************************************************************************************************************");
		logger.error("2.Detalle y totales de ventas para la cadena completa y por sucursal, por obra social o privados entre fechas.  ");
		logger.error("***************************************************************************************************************\n");
		logger.warning("Ventas entre las fechas " + fechaDesde + " y " + fechaHasta);
		HashMap<String, Double> totalesSucursales = new HashMap<String, Double>();
		List<Document> detalleVentasPorObraSocial = ventaABM.traerVentasPorObraSocialEntreFechas(fechaDesde, fechaHasta);
		String auxNSucursal = "";
		boolean flag = false;
		for(Document vos: detalleVentasPorObraSocial) {
			List<String> nroSucursales = new ArrayList<String>();
			Document claveObj = (Document)vos.get("_id");
			String nroSucursal = claveObj.getString("nroSucursal");
			String obraSocial = claveObj.getString("obraSocial");
			nroSucursales.add(nroSucursal);
			if(flag == false) {
				auxNSucursal = nroSucursal;
				flag = true;
				logger.info("Ventas de la sucursal N°" + nroSucursal);
				logger.info("***************************");
			}
			if(nroSucursal.equalsIgnoreCase(auxNSucursal) == false) {
				logger.info("Ventas de la sucursal N°" + nroSucursal);
				logger.info("***************************");
				auxNSucursal = nroSucursal;
			}
			logger.info("Obra social: " + obraSocial);
			// Total de venta por obra social y sucursal
			Double totalObraSocialSucursal = vos.getDouble("total");
			logger.info("Detalles de las ventas:\n" + gson.toJson((List<Document>) vos.get("detallesVentas")));
			logger.info("Total obtenido por esta obra social: $" + totalObraSocialSucursal + "\n\n");
			if(totalesSucursales.get(nroSucursal) != null) {
				Double nuevoValor = totalesSucursales.get(nroSucursal) + totalObraSocialSucursal;
				totalesSucursales.put(nroSucursal, nuevoValor);
			} else {
				totalesSucursales.put(nroSucursal, totalObraSocialSucursal);
			}
		}
		for (String i : totalesSucursales.keySet()) {
			logger.warning("Sucursal: " + i + " recaudó: $" + totalesSucursales.get(i));
		}
	}
	
	public static void consulta4(LocalDate fechaDesde, LocalDate fechaHasta) {
		logger.error("****************************************************************************************************************************************");
		logger.error("4.Detalle y totales de ventas de productos, total de la cadena y por sucursal, entre fechas, diferenciados entre farmacia y perfumería. ");
		logger.error("****************************************************************************************************************************************\n");
		List<Document> detalleVentasProductos = ventaABM.detallesVentaEntreFechaPorTipo(fechaDesde, fechaHasta);
		for(Document dv: detalleVentasProductos) {
			List<Double> listaTotalTipo = new ArrayList<Double>();
			logger.info("TIPO: " + dv.getString("tipoProducto") + "\n\n");
			List<Document> productos = (List<Document>) dv.get("productos");
			// Recorriendo lista de productos del tipo seleccionado
			for(Document p: productos) {
				List<Double> listaTotalSucursal = new ArrayList<Double>();
				Document producto = (Document) p.get("producto");
				logger.info("Codigo de producto: " + producto.get("codigo"));
				Double precio = producto.getDouble("precio");
				// Recorriendo lista de locales que vendieron el producto
				for(Document sv: (List<Document>) p.get("sucursalVendio")) {
					logger.info("Sucursal N°" + sv.getString("sucursal"));
					Double totalSucursal = sv.getInteger("cantidad") * precio;
					logger.info("Total de la venta con ese producto " + totalSucursal);
					listaTotalSucursal.add(totalSucursal);
				}
				Double totalProducto = listaTotalSucursal.stream().mapToDouble(f -> f.doubleValue()).sum();
				logger.info("Total de la cadena recaudado por el producto: " + totalProducto + "\n");
				listaTotalTipo.add(totalProducto);
			}
			Double totalTipo = listaTotalTipo.stream().mapToDouble(f -> f.doubleValue()).sum();
			logger.info("Total de la cadena del tipo '" + dv.getString("tipoProducto") + "': $" + totalTipo + "\n");
		}
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
