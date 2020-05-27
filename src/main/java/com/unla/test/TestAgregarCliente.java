package com.unla.test;

import com.unla.datos.Cliente;
import com.unla.negocio.ClienteABM;

public class TestAgregarCliente {
	
	public static void main(String[] args) {
		ClienteABM abm = ClienteABM.getInstance();
		Cliente cliente = new Cliente("Juan", "Rodriguez", "11222333", null);
		try {
			abm.agregar(cliente);
			System.out.println("Agregado exitosamente");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
