package com.unla.test;

import com.unla.datos.Cliente;
import com.unla.negocio.ClienteABM;

public class TestModificarCliente {
	public static void main(String[] args) {
		ClienteABM abm = ClienteABM.getInstance();
		Cliente cliente = new Cliente("Pepe", "Pasdsa", "11222333743374773747347367467", null);
		try {
			abm.eliminar(cliente);
			System.out.println("Cliente modificado exitosamente");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
