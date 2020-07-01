package com.unla.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.unla.datos.Cliente;
import com.unla.datos.DetalleVenta;
import com.unla.datos.Domicilio;
import com.unla.datos.Empleado;
import com.unla.datos.ObraSocial;
import com.unla.datos.Producto;
import com.unla.datos.Sucursal;
import com.unla.datos.Venta;
import com.unla.negocio.ClienteABM;
import com.unla.negocio.EmpleadoABM;
import com.unla.negocio.ProductoABM;
import com.unla.negocio.SucursalABM;
import com.unla.negocio.VentaABM;

public class TestCargaDatos {

	public static void main(String[] args) {
		ClienteABM clienteABM = ClienteABM.getInstance();
		EmpleadoABM empleadoABM = EmpleadoABM.getInstance();
		ProductoABM productoABM = ProductoABM.getInstance();
		SucursalABM sucursalABM = SucursalABM.getInstance();
		VentaABM ventaABM = VentaABM.getInstance();
		try {
			clienteABM.agregar(new Cliente("Damian", "Orzi", 10000001, new Domicilio("Calle 1", 1232, "Lanús", "Buenos Aires"), new ObraSocial("OSDE 210", 1320)));
			clienteABM.agregar(new Cliente("Juan", "Perez", 10000002, new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires")));
			clienteABM.agregar(new Cliente("Matias", "Rivero", 10000003, new Domicilio("Calle 3", 231, "Adrogué", "Buenos Aires"), new ObraSocial("SWISS MEDICAL", 1000)));
			clienteABM.agregar(new Cliente("Federico", "Ribeiro", 10000004, new Domicilio("Calle 4", 43, "Varela", "Buenos Aires")));
			clienteABM.agregar(new Cliente("Gaston", "Pauls", 10000005, new Domicilio("Calle 5", 2312, "Temperley", "Buenos Aires"), new ObraSocial("PAMI", 8843)));
			clienteABM.agregar(new Cliente("Roberto", "García", 10000006, new Domicilio("Calle 6", 344, "Gerli", "Buenos Aires")));
			clienteABM.agregar(new Cliente("Lorena", "Batallan", 10000007, new Domicilio("Calle 7", 122, "Lanús", "Buenos Aires")));
			clienteABM.agregar(new Cliente("Leonel", "Rivero", 10000008, new Domicilio("Calle 8", 6656, "Lanús", "Buenos Aires")));
			clienteABM.agregar(new Cliente("Marta", "Martinez", 10000009, new Domicilio("Calle 9", 8770, "Banfield", "Buenos Aires")));
			clienteABM.agregar(new Cliente("Andrés", "Segovia", 10000010, new Domicilio("Calle 10", 344, "Longchamps", "Buenos Aires"), new ObraSocial("OSDE 210", 1734)));
			System.out.println("Clientes cargados correctamente");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			empleadoABM.agregar(new Empleado("Manuel", "Sanchez", 20000001, "20-20000001-1", new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires"), new ObraSocial("IOMA", 1254)));
			empleadoABM.agregar(new Empleado("Nahuel", "Huapi", 20000002, "20-20000002-1", new Domicilio("Calle 5", 718, "Banfield", "Buenos Aires"), new ObraSocial("PAMI", 1255)));
			empleadoABM.agregar(new Empleado("Vladimir", "Putin", 20000003, "20-20000003-1", new Domicilio("Calle 2", 312, "Bulnes", "Ciudad de Buenos Aires"), new ObraSocial("PAMI", 1256)));
			empleadoABM.agregar(new Empleado("Mariana", "Sanchez", 20000004, "20-20000004-2", new Domicilio("Calle 22", 323, "Once", "Ciudad de Buenos Aires"), new ObraSocial("PAMI", 1257)));
			empleadoABM.agregar(new Empleado("Cristian", "Torres", 20000005, "20-20000005-2", new Domicilio("Calle 12", 954, "Monte Grande", "Buenos Aires"), new ObraSocial("PAMI", 1258)));
			empleadoABM.agregar(new Empleado("Eusebio", "Lopez", 20000006, "20-20000006-2", new Domicilio("Calle 5", 1124, "Lomas de Zamora", "Buenos Aires"), new ObraSocial("IOMA", 1259)));
			empleadoABM.agregar(new Empleado("Matias", "Illia", 20000007, "20-20000007-2", new Domicilio("Calle 9", 412, "José Marmol", "Buenos Aires"), new ObraSocial("SWISS MEDICAL", 1260)));
			empleadoABM.agregar(new Empleado("Luciana", "Ramirez", 20000008, "20-20000008-2", new Domicilio("Calle 2", 785, "Banfield", "Buenos Aires"), new ObraSocial("PAMI", 1261)));
			empleadoABM.agregar(new Empleado("Alfredo", "Constantini", 20000009, "20-20000009-2", new Domicilio("Calle 1", 25, "Avellaneda", "Buenos Aires"), new ObraSocial("SWISS MEDICAL", 1262)));
			System.out.println("Empleados cargados correctamente");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			productoABM.agregar(new Producto(1, "perfumeria", "perfume", "Antonio Banderas", 500.0));
			productoABM.agregar(new Producto(2, "perfumeria", "perfume", "Paco Rabanne", 200.0));
			productoABM.agregar(new Producto(3, "perfumeria", "perfume", "Axe", 90.0));
			productoABM.agregar(new Producto(4, "medicamento", "Actron", "Bayer", 100.0));
			productoABM.agregar(new Producto(5, "medicamento", "Ibuprofeno", "Baggó", 80.0));
			productoABM.agregar(new Producto(6, "medicamento", "Buscapina", "Bayer", 250.0));
			productoABM.agregar(new Producto(7, "medicamento", "Vic", "Bayer", 75.0));
			productoABM.agregar(new Producto(8, "medicamento", "Novalgina", "Bayer", 100.0));
			productoABM.agregar(new Producto(9, "medicamento", "Bayaspirina", "Bayer", 110.0));
			productoABM.agregar(new Producto(10, "medicamento", "Curitas", "BEIERSDORS", 70.0));
			System.out.println("Productos cargados correctamente");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		// Creamos una lista de empleado
		List<Empleado> listaEmp1 = new ArrayList<Empleado>();
		listaEmp1.add(empleadoABM.traer("20-20000004-2"));
		listaEmp1.add(empleadoABM.traer("20-20000005-2"));
		List<Empleado> listaEmp2 = new ArrayList<Empleado>();
		listaEmp2.add(empleadoABM.traer("20-20000006-2"));
		listaEmp2.add(empleadoABM.traer("20-20000007-2"));
		List<Empleado> listaEmp3 = new ArrayList<Empleado>();
		listaEmp3.add(empleadoABM.traer("20-20000008-2"));
		listaEmp3.add(empleadoABM.traer("20-20000009-2"));
		List<Producto> listaProd1 = new ArrayList<Producto>();
		listaProd1.add(productoABM.traer(1));
		listaProd1.add(productoABM.traer(4));
		listaProd1.add(productoABM.traer(5));
		List<Producto> listaProd2 = new ArrayList<Producto>();
		listaProd2.add(productoABM.traer(2));
		listaProd2.add(productoABM.traer(6));
		listaProd2.add(productoABM.traer(7));
		List<Producto> listaProd3 = new ArrayList<Producto>();
		listaProd3.add(productoABM.traer(3));
		listaProd3.add(productoABM.traer(8));
		listaProd3.add(productoABM.traer(9));
		listaProd3.add(productoABM.traer(10));
		try {
			sucursalABM.agregar(new Sucursal(1000, empleadoABM.traer("20-20000001-1"), listaEmp1, listaProd1, new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires")));
			sucursalABM.agregar(new Sucursal(1001, empleadoABM.traer("20-20000002-1"), listaEmp2, listaProd2, new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires")));
			sucursalABM.agregar(new Sucursal(1002, empleadoABM.traer("20-20000003-1"), listaEmp3, listaProd3, new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires")));
			System.out.println("Sucursales cargadas correctamente");
		} catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		// Ventas para la sucursal 1
		List<DetalleVenta> ventas1 = new ArrayList<DetalleVenta>();
		List<DetalleVenta> ventas2 = new ArrayList<DetalleVenta>();
		List<DetalleVenta> ventas3 = new ArrayList<DetalleVenta>();
		List<DetalleVenta> ventas4 = new ArrayList<DetalleVenta>();
		List<DetalleVenta> ventas5 = new ArrayList<DetalleVenta>();
		ventas1.add(new DetalleVenta(productoABM.traer(1), 7));
		ventas1.add(new DetalleVenta(productoABM.traer(4), 2));
		ventas1.add(new DetalleVenta(productoABM.traer(5), 6));
		ventas2.add(new DetalleVenta(productoABM.traer(3), 3));
		ventas2.add(new DetalleVenta(productoABM.traer(5), 3));
		ventas3.add(new DetalleVenta(productoABM.traer(3), 4));
		//ventas3.add(new DetalleVenta(productoABM.traer(5), 5));
		ventas4.add(new DetalleVenta(productoABM.traer(2), 5));
		ventas4.add(new DetalleVenta(productoABM.traer(4), 3));
		ventas4.add(new DetalleVenta(productoABM.traer(7), 1));
		ventas5.add(new DetalleVenta(productoABM.traer(7), 2));
		ventas5.add(new DetalleVenta(productoABM.traer(8), 1));
		ventas5.add(new DetalleVenta(productoABM.traer(9), 7));
		ventas5.add(new DetalleVenta(productoABM.traer(1), 4));
		ventas5.add(new DetalleVenta(productoABM.traer(5), 3));
		// Ventas para la sucursal 2
		List<DetalleVenta> ventas6 = new ArrayList<DetalleVenta>();
		List<DetalleVenta> ventas7 = new ArrayList<DetalleVenta>();
		List<DetalleVenta> ventas8 = new ArrayList<DetalleVenta>();
		ventas6.add(new DetalleVenta(productoABM.traer(1), 3));
		ventas6.add(new DetalleVenta(productoABM.traer(5), 8));
		ventas6.add(new DetalleVenta(productoABM.traer(4), 2));
		ventas6.add(new DetalleVenta(productoABM.traer(2), 3));
		ventas7.add(new DetalleVenta(productoABM.traer(6), 3));
		ventas7.add(new DetalleVenta(productoABM.traer(4), 3));
		ventas7.add(new DetalleVenta(productoABM.traer(2), 3));
		ventas8.add(new DetalleVenta(productoABM.traer(3), 5));
		// Ventas para la sucursal 3
		List<DetalleVenta> ventas9 = new ArrayList<DetalleVenta>();
		List<DetalleVenta> ventas10 = new ArrayList<DetalleVenta>();
		List<DetalleVenta> ventas11 = new ArrayList<DetalleVenta>();
		List<DetalleVenta> ventas12 = new ArrayList<DetalleVenta>();
		ventas9.add(new DetalleVenta(productoABM.traer(5), 4));
		ventas9.add(new DetalleVenta(productoABM.traer(8), 4));
		ventas10.add(new DetalleVenta(productoABM.traer(3), 4));
		ventas11.add(new DetalleVenta(productoABM.traer(1), 4));
		ventas11.add(new DetalleVenta(productoABM.traer(6), 1));
		ventas11.add(new DetalleVenta(productoABM.traer(2), 2));
		ventas11.add(new DetalleVenta(productoABM.traer(4), 7));
		ventas12.add(new DetalleVenta(productoABM.traer(1), 5));
		ventas12.add(new DetalleVenta(productoABM.traer(2), 5));
		ventas12.add(new DetalleVenta(productoABM.traer(3), 4));
		try {
			ventaABM.agregar(new Venta(LocalDate.of(2020, 3, 5), "1000-00000001", "CREDITO", ventas1, empleadoABM.traer("20-20000004-2"), empleadoABM.traer("20-20000005-2"), clienteABM.traer(10000001)));
			ventaABM.agregar(new Venta(LocalDate.of(2020, 3, 5), "1001-00000002", "DEBITO", ventas2, empleadoABM.traer("20-20000005-2"), empleadoABM.traer("20-20000004-2"), clienteABM.traer(10000004)));
			ventaABM.agregar(new Venta(LocalDate.of(2020, 3, 6), "1002-00000003", "DEBITO", ventas3, empleadoABM.traer("20-20000005-2"), empleadoABM.traer("20-20000004-2"), clienteABM.traer(10000005)));
			ventaABM.agregar(new Venta(LocalDate.of(2020, 4, 25), "1002-00000004", "DEBITO", ventas4, empleadoABM.traer("20-20000004-2"), empleadoABM.traer("20-20000005-2"), clienteABM.traer(10000007)));
			ventaABM.agregar(new Venta(LocalDate.of(2020, 4, 26), "1002-00000005", "CREDITO", ventas5, empleadoABM.traer("20-20000004-2"), empleadoABM.traer("20-20000005-2"), clienteABM.traer(10000004)));
			ventaABM.agregar(new Venta(LocalDate.of(2020, 5, 15), "1000-00000006", "CREDITO", ventas6, empleadoABM.traer("20-20000006-2"), empleadoABM.traer("20-20000007-2"), clienteABM.traer(10000006)));
			ventaABM.agregar(new Venta(LocalDate.of(2020, 5, 25), "1000-00000007", "DEBITO", ventas7, empleadoABM.traer("20-20000007-2"), empleadoABM.traer("20-20000006-2"), clienteABM.traer(10000001)));
			ventaABM.agregar(new Venta(LocalDate.of(2020, 6, 7), "1000-00000008", "DEBITO", ventas8, empleadoABM.traer("20-20000007-2"), empleadoABM.traer("20-20000006-2"), clienteABM.traer(10000007)));
			ventaABM.agregar(new Venta(LocalDate.of(2020, 6, 24), "1001-00000009", "DEBITO", ventas9, empleadoABM.traer("20-20000008-2"), empleadoABM.traer("20-20000009-2"), clienteABM.traer(10000003)));
			ventaABM.agregar(new Venta(LocalDate.of(2020, 7, 12), "1001-00000010", "CREDITO", ventas10, empleadoABM.traer("20-20000008-2"), empleadoABM.traer("20-20000009-2"), clienteABM.traer(10000002)));
			ventaABM.agregar(new Venta(LocalDate.of(2020, 7, 13), "1001-00000011", "CREDITO", ventas11, empleadoABM.traer("20-20000009-2"), empleadoABM.traer("20-20000008-2"), clienteABM.traer(10000009)));
			ventaABM.agregar(new Venta(LocalDate.of(2020, 8, 5), "1002-00000012", "DEBITO", ventas12, empleadoABM.traer("20-20000008-2"), empleadoABM.traer("20-20000009-2"), clienteABM.traer(10000010)));
			System.out.println("Ventas cargadas correctamente");
		} catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
