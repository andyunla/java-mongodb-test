package com.unla.test;

import com.unla.datos.Cliente;
import com.google.gson.Gson;
import com.unla.dao.ClienteDao;

public class AgregarCliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClienteDao dao = ClienteDao.getInstance();
		Cliente cliente = new Cliente("Juan", "Perez", "11222333", null);
		dao.agregar(cliente);
		System.out.println("Ejecutado");
	}
}
