package com.unla.funciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bson.BSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.unla.datos.Cliente;
import com.unla.datos.DetalleVenta;
import com.unla.datos.Empleado;
import com.unla.datos.Producto;
import com.unla.datos.Venta;
import com.unla.negocio.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;
//import org.json.JSONObject;

import com.jayway.jsonpath.JsonPath;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

public class Consultas {
	private static LoggerWrapper logger = LoggerWrapper.getInstance("Consultas");
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Para que el JSON esté formateado
	private static VentaABM ventaABM = VentaABM.getInstance();
	private static ProductoABM productoABM = ProductoABM.getInstance();
	private static ClienteABM clienteABM = ClienteABM.getInstance();
	
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
	
	public static void consulta3(LocalDate fechaDesde, LocalDate fechaHasta) {
		logger.error("***************************************************************************************************************");
		logger.error("3.Detalle y totales de cobranza para la cadena completa y por sucursal, por medio de pago y entre fechas. ");
		logger.error("***************************************************************************************************************\n");
		logger.warning("Ventas entre las fechas " + fechaDesde + " y " + fechaHasta);
		HashMap<String, Double> totalesSucursales = new HashMap<String, Double>();
		List<Document> cobranzasPorMedioPago = ventaABM.traerCobranzasPorMedioPagoEntreFechas(fechaDesde, fechaHasta);
		String auxNSucursal = "";
		boolean flag = false;
		for(Document cmp: cobranzasPorMedioPago) {
			Document claveObj = (Document)cmp.get("_id");
			String nroSucursal = claveObj.getString("nroSucursal");
			String formaPago = claveObj.getString("formaPago");
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
			logger.info("Medio de pago: " + formaPago);
			// Total de venta por medio de pago y sucursal
			Double totalMedioPagoSucursal = cmp.getDouble("total");
			//logger.info("Detalles de las ventas:\n" + gson.toJson((List<Document>) cmp.get("detallesVentas")));
			logger.info("Total obtenido por la sucursal(" + formaPago + "): $" + totalMedioPagoSucursal + "\n\n");
			if(totalesSucursales.get(nroSucursal) != null) {
				Double nuevoValor = totalesSucursales.get(nroSucursal) + totalMedioPagoSucursal;
				totalesSucursales.put(nroSucursal, nuevoValor);
			} else {
				totalesSucursales.put(nroSucursal, totalMedioPagoSucursal);
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
	
	public static void consulta5(LocalDate fechaDesde, LocalDate fechaHasta) {
		logger.error("****************************************************************************************************************************************");
		logger.error("5.Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por monto. ");
		logger.error("****************************************************************************************************************************************\n");
		logger.warning("Ranking entre las fechas " + fechaDesde + " y " + fechaHasta);
		HashMap<Integer, Double> totalesProductos = new HashMap<Integer, Double>();
		String auxNSucursal = "";
		boolean flag = false;
		List<Document> rankingProductos = ventaABM.traerRankingProductosPorMontoEntreFechas(fechaDesde, fechaHasta);
		for(Document rp: rankingProductos) {
			Document claveObj = (Document) rp.get("_id");
			String nroSucursal = claveObj.getString("nroSucursal");
			Integer codigoProducto = claveObj.getInteger("producto");
			if(flag == false || nroSucursal.equalsIgnoreCase(auxNSucursal) == false) {
				auxNSucursal = nroSucursal;
				flag = true;
				logger.info("Ranking de la sucursal N°" + nroSucursal);
				logger.info("****************************");
			}
			// Total de venta por medio de pago y sucursal
			Double totalProductoSucursal = rp.getDouble("total");
			logger.info("Producto: " + productoABM.traer(codigoProducto).getDescripcion() + " - codigo " + codigoProducto + " - Monto: " + totalProductoSucursal);
			if(totalesProductos.get(codigoProducto) != null) {
				Double nuevoValor = totalesProductos.get(codigoProducto) + totalProductoSucursal;
				totalesProductos.put(codigoProducto, nuevoValor);
			} else {
				totalesProductos.put(codigoProducto, totalProductoSucursal);
			}
		}
		// let's sort this map by values first
	    Map<Integer, Double> ordenado = totalesProductos
							            .entrySet()
							            .stream()
							            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
							            .collect(
							                toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
							                    LinkedHashMap::new));
		
		logger.warning("\nRANKING DE PRODUCTOS DE LA CADENA COMPLETA");
		for (Integer i : ordenado.keySet()) {
			logger.warning("Producto: " + i + " recaudó: $" + ordenado.get(i));
		}
	}
	
	public static void consulta6(LocalDate fechaDesde, LocalDate fechaHasta) {
		logger.error("****************************************************************************************************************************************");
		logger.error("6.Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por cantidad vendida. ");
		logger.error("****************************************************************************************************************************************\n");
		logger.warning("Ranking entre las fechas " + fechaDesde + " y " + fechaHasta);
		HashMap<Integer, Integer> totalesProductos = new HashMap<Integer, Integer>();
		String auxNSucursal = "";
		boolean flag = false;
		List<Document> rankingProductos = ventaABM.traerRankingProductosPorCantidadEntreFechas(fechaDesde, fechaHasta);
		for(Document rp: rankingProductos) {
			Document claveObj = (Document) rp.get("_id");
			String nroSucursal = claveObj.getString("nroSucursal");
			Integer codigoProducto = claveObj.getInteger("producto");
			if(flag == false || nroSucursal.equalsIgnoreCase(auxNSucursal) == false) {
				auxNSucursal = nroSucursal;
				flag = true;
				logger.info("Ranking de la sucursal N°" + nroSucursal);
				logger.info("****************************");
			}
			// Total de venta por medio de pago y sucursal
			Integer totalProductoSucursal = rp.getInteger("total");
			logger.info("Producto: " + productoABM.traer(codigoProducto).getDescripcion() + " - codigo " + codigoProducto + " - Cantidad: " + totalProductoSucursal);
			if(totalesProductos.get(codigoProducto) != null) {
				Integer nuevoValor = totalesProductos.get(codigoProducto) + totalProductoSucursal;
				totalesProductos.put(codigoProducto, nuevoValor);
			} else {
				totalesProductos.put(codigoProducto, totalProductoSucursal);
			}
		}
		System.out.print("\n");
		// let's sort this map by values first
	    Map<Integer, Integer> ordenado = totalesProductos
							            .entrySet()
							            .stream()
							            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
							            .collect(
							                toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
							                    LinkedHashMap::new));
		logger.warning("RANKING DE PRODUCTOS DE LA CADENA COMPLETA");
		for (Integer i : ordenado.keySet()) {
			logger.warning("Producto: " + i + " cantidad vendida: " + totalesProductos.get(i));
		}
	}
	
	public static void consulta7(LocalDate fechaDesde, LocalDate fechaHasta) {
		logger.error("****************************************************************************************************************************************");
		logger.error("7.Ranking de clientes por compras, total de la cadena y por sucursal, entre fechas, por monto. ");
		logger.error("****************************************************************************************************************************************\n");
		logger.warning("Ranking entre las fechas " + fechaDesde + " y " + fechaHasta);
		HashMap<Integer, Double> totalesDeCliente = new HashMap<Integer, Double>();
		String auxNSucursal = "";
		boolean flag = false;
		List<Document> rankingProductos = ventaABM.traerRankingClientesPorMontoEntreFechas(fechaDesde, fechaHasta);
		for(Document rp: rankingProductos) {
			Document claveObj = (Document) rp.get("_id");
			String nroSucursal = claveObj.getString("nroSucursal");
			Cliente cliente = clienteABM.deserealizar(gson.toJson(claveObj.get("cliente")));
			if(flag == false || nroSucursal.equalsIgnoreCase(auxNSucursal) == false) {
				auxNSucursal = nroSucursal;
				flag = true;
				logger.info("Ranking de la sucursal N°" + nroSucursal);
				logger.info("****************************");
			}
			// Total de venta pagado por el cliente
			Double totalClienteSucursal = rp.getDouble("total");
			logger.info("Cliente: " + cliente.getNombre() + " " + cliente.getApellido() + ", DNI: " + cliente.getDni() + ", monto gastado: $" + totalClienteSucursal);
			if(totalesDeCliente.get(cliente.getDni()) != null) {
				Double nuevoValor = totalesDeCliente.get(cliente.getDni()) + totalClienteSucursal;
				totalesDeCliente.put(cliente.getDni(), nuevoValor);
			} else {
				totalesDeCliente.put(cliente.getDni(), totalClienteSucursal);
			}
		}
		// let's sort this map by values first
	    Map<Integer, Double> ordenado = totalesDeCliente
							            .entrySet()
							            .stream()
							            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
							            .collect(
							                toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
							                    LinkedHashMap::new));
	    System.out.print("\n");
		logger.warning("RANKING DE VENTAS DE CLIENTES DE LA CADENA COMPLETA");
		for (Integer i : ordenado.keySet()) {
			logger.warning("Cliente-DNI:" + i + " recaudó: $" + ordenado.get(i));
		}
	}
	
	public static void consulta8(LocalDate fechaDesde, LocalDate fechaHasta) {
		logger.error("****************************************************************************************************************************************");
		logger.error("8.Ranking de clientes por compras, total de la cadena y por sucursal, entre fechas, por cantidad vendida.");
		logger.error("****************************************************************************************************************************************\n");
		logger.warning("Ranking entre las fechas " + fechaDesde + " y " + fechaHasta);
		HashMap<Integer, Integer> totalesDeCliente = new HashMap<Integer, Integer>();
		String auxNSucursal = "";
		boolean flag = false;
		List<Document> rankingProductos = ventaABM.traerRankingClientesPorCantidadEntreFechas(fechaDesde, fechaHasta);
		for(Document rp: rankingProductos) {
			Document claveObj = (Document) rp.get("_id");
			String nroSucursal = claveObj.getString("nroSucursal");
			Cliente cliente = clienteABM.deserealizar(gson.toJson(claveObj.get("cliente")));
			if(flag == false || nroSucursal.equalsIgnoreCase(auxNSucursal) == false) {
				auxNSucursal = nroSucursal;
				flag = true;
				logger.info("Ranking de la sucursal N°" + nroSucursal);
				logger.info("****************************");
			}
			// Total de venta pagado por el cliente
			Integer totalClienteSucursal = rp.getInteger("total");
			logger.info("Cliente: " + cliente.getNombre() + " " + cliente.getApellido() + ", DNI: " + cliente.getDni() + ", cantidad comprada " + totalClienteSucursal);
			if(totalesDeCliente.get(cliente.getDni()) != null) {
				Integer nuevoValor = totalesDeCliente.get(cliente.getDni()) + totalClienteSucursal;
				totalesDeCliente.put(cliente.getDni(), nuevoValor);
			} else {
				totalesDeCliente.put(cliente.getDni(), totalClienteSucursal);
			}
		}
		// let's sort this map by values first
	    Map<Integer, Integer> ordenado = totalesDeCliente
							            .entrySet()
							            .stream()
							            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
							            .collect(
							                toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
							                    LinkedHashMap::new));
		System.out.print("\n");
		logger.warning("RANKING DE VENTAS DE CLIENTES DE LA CADENA COMPLETA");
		for (Integer i : ordenado.keySet()) {
			logger.warning("Cliente-DNI:" + i + " cantidad comprada: " + ordenado.get(i));
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
