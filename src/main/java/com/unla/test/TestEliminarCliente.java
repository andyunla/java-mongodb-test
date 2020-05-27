package com.unla.test;

import com.unla.datos.Cliente;
import com.unla.negocio.ClienteABM;

public class TestEliminarCliente {

	public static void main(String[] args) {
		ClienteABM abm = ClienteABM.getInstance();
		Cliente cliente = new Cliente("Pepe", "Pasdsa", 11222333, null);
		try {
			abm.eliminar(cliente);
			System.out.println("Cliente eliminado exitosamente");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
