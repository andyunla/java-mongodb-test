package com.unla.test;

import com.unla.datos.Cliente;
import com.unla.datos.Empleado;
import com.unla.negocio.EmpleadoABM;

public class TestEmpleado {
	public static void main(String[] args) {
		EmpleadoABM abm = EmpleadoABM.getInstance();

		// Agregar empleado
		// ***************************
		Empleado empleado = new Empleado("Juan", "Rodriguez", 11222333, "20-11111111-9", null, null);
		try {
			abm.agregar(empleado);
			System.out.println("Agregado exitosamente");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// Traer empleado
		// ***************************
		empleado = abm.traer("20-11111111-9");
		if (empleado == null) {
			System.out.println("Datos del empleado:\n" + empleado);
		} else {
			System.out.println("No existe empleado con ese CUIL");
		}

		// Modificar empleado dado su CUIL
		// **********************************************
		try {
			abm.eliminar(new Empleado("Mario", "Marquez", 11222333, "20-11111111-9", null, null));
			System.out.println("Cliente modificado exitosamente");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// Eliminar empleado dado su CUIL
		// **********************************************
		try {
			abm.eliminar(new Empleado("Mario", "Marquez", 11222333, "20-11111111-9", null, null));
			System.out.println("Cliente modificado exitosamente");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
