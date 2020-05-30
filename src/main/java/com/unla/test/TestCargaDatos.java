package com.unla.test;

import java.util.ArrayList;
import java.util.List;

import com.unla.datos.Cliente;
import com.unla.datos.Domicilio;
import com.unla.datos.Empleado;
import com.unla.datos.ObraSocial;
import com.unla.datos.Producto;
import com.unla.datos.Sucursal;
import com.unla.negocio.ClienteABM;
import com.unla.negocio.EmpleadoABM;
import com.unla.negocio.ProductoABM;
import com.unla.negocio.SucursalABM;

public class TestCargaDatos {

	public static void main(String[] args) {
		ClienteABM clienteABM = ClienteABM.getInstance();
		EmpleadoABM empleadoABM = EmpleadoABM.getInstance();
		ProductoABM productoABM = ProductoABM.getInstance();
		SucursalABM sucursalABM = SucursalABM.getInstance();
		try {
			clienteABM.agregar(new Cliente("Damian", "Orzi", 10000001, new Domicilio("Calle 1", 1232, "Lanús", "Buenos Aires")));
			clienteABM.agregar(new Cliente("Damian", "Orzi", 10000002, new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires")));
			clienteABM.agregar(new Cliente("Damian", "Orzi", 10000003, new Domicilio("Calle 3", 231, "Adrogué", "Buenos Aires")));
			clienteABM.agregar(new Cliente("Damian", "Orzi", 10000004, new Domicilio("Calle 4", 43, "Varela", "Buenos Aires")));
			clienteABM.agregar(new Cliente("Damian", "Orzi", 10000005, new Domicilio("Calle 5", 2312, "Temperley", "Buenos Aires")));
			clienteABM.agregar(new Cliente("Damian", "Orzi", 10000006, new Domicilio("Calle 6", 344, "Gerli", "Buenos Aires")));
			clienteABM.agregar(new Cliente("Damian", "Orzi", 10000007, new Domicilio("Calle 7", 122, "Lanús", "Buenos Aires")));
			clienteABM.agregar(new Cliente("Damian", "Orzi", 10000008, new Domicilio("Calle 8", 6656, "Lanús", "Buenos Aires")));
			clienteABM.agregar(new Cliente("Damian", "Orzi", 10000009, new Domicilio("Calle 9", 8770, "Banfield", "Buenos Aires")));
			clienteABM.agregar(new Cliente("Damian", "Orzi", 10000010, new Domicilio("Calle 10", 344, "Longchamps", "Buenos Aires")));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			empleadoABM.agregar(new Empleado("Manuel", "Sanchez", 20000001, "20-20000001-2", new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires"), new ObraSocial("PAMI", 1254)));
			empleadoABM.agregar(new Empleado("Manuel", "Sanchez", 20000002, "20-20000002-2", new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires"), new ObraSocial("PAMI", 1254)));
			empleadoABM.agregar(new Empleado("Manuel", "Sanchez", 20000003, "20-20000003-2", new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires"), new ObraSocial("PAMI", 1254)));
			empleadoABM.agregar(new Empleado("Manuel", "Sanchez", 20000004, "20-20000004-2", new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires"), new ObraSocial("PAMI", 1254)));
			empleadoABM.agregar(new Empleado("Manuel", "Sanchez", 20000005, "20-20000005-2", new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires"), new ObraSocial("PAMI", 1254)));
			empleadoABM.agregar(new Empleado("Manuel", "Sanchez", 20000006, "20-20000006-2", new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires"), new ObraSocial("PAMI", 1254)));
			empleadoABM.agregar(new Empleado("Manuel", "Sanchez", 20000007, "20-20000007-2", new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires"), new ObraSocial("PAMI", 1254)));
			empleadoABM.agregar(new Empleado("Manuel", "Sanchez", 20000008, "20-20000008-2", new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires"), new ObraSocial("PAMI", 1254)));
			empleadoABM.agregar(new Empleado("Manuel", "Sanchez", 20000009, "20-20000009-2", new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires"), new ObraSocial("PAMI", 1254)));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			productoABM.agregar(new Producto(1, "perfumeria", "perfume", "Antonio Banderas", 50.0));
			productoABM.agregar(new Producto(2, "perfumeria", "perfume", "Antonio Banderas", 50.0));
			productoABM.agregar(new Producto(3, "perfumeria", "perfume", "Antonio Banderas", 50.0));
			productoABM.agregar(new Producto(4, "medicamento", "Actron", "Bayer", 100.0));
			productoABM.agregar(new Producto(5, "medicamento", "Actron", "Bayer", 100.0));
			productoABM.agregar(new Producto(6, "medicamento", "Actron", "Bayer", 100.0));
			productoABM.agregar(new Producto(7, "medicamento", "Actron", "Bayer", 100.0));
			productoABM.agregar(new Producto(8, "medicamento", "Actron", "Bayer", 100.0));
			productoABM.agregar(new Producto(9, "medicamento", "Actron", "Bayer", 100.0));
			productoABM.agregar(new Producto(10, "medicamento", "Actron", "Bayer", 100.0));
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
			sucursalABM.agregar(new Sucursal(1000, empleadoABM.traer("20-20000001-2"), listaEmp1, listaProd1, new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires")));
			sucursalABM.agregar(new Sucursal(1001, empleadoABM.traer("20-20000002-2"), listaEmp2, listaProd2, new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires")));
			sucursalABM.agregar(new Sucursal(1002, empleadoABM.traer("20-20000003-2"), listaEmp3, listaProd3, new Domicilio("Calle 2", 3412, "José Marmol", "Buenos Aires")));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
