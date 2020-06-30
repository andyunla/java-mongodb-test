package com.unla.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.BSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.unla.datos.DetalleVenta;
import com.unla.datos.Empleado;
import com.unla.datos.Producto;
import com.unla.datos.Venta;
import com.unla.funciones.LoggerWrapper;
import com.unla.negocio.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import org.json.JSONException;
//import org.json.JSONObject;

import com.jayway.jsonpath.JsonPath;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

public class Consultas {
	private static VentaABM ventaABM;
	public static void main(String ... args) {
        LoggerWrapper logger = LoggerWrapper.getInstance("Consultas");
		Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Para que el JSON esté formateado
		ventaABM = VentaABM.getInstance();
		logger.info("************************************************************************************");
		logger.info("1.Detalle y totales de ventas para la cadena completa y por sucursal, entre fechas. ");
		logger.info("************************************************************************************\n");
		LocalDate fechaDesde = LocalDate.of(2020, 1, 1);
		LocalDate fechaHasta = LocalDate.of(2020, 4, 6);
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
			logger.info("El total de la sucursal actual es: " + totalSucursal + "\n\n");
		}
		logger.info("El total de la cadena completa es: $" + totalDetallesVentas.get("totalTodo") + "\n\n");
		
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
			logger.info("Total de la cadena por tipo: " + totalTipo + "\n");
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
List<Double> totalCadaVenta = JsonPath.read(new Gson().toJson(totalesVentas), "$sucursal[*].cantidad");
double totalTodaVentas = totalCadaVenta.stream().mapToDouble(f -> f.doubleValue()).sum();
System.out.println("\nEl total de la cadena completa es: " + totalTodaVentas);
*/