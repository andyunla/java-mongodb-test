package com.unla.negocio;

import java.util.List;

import com.unla.dao.ClienteDao;
import com.unla.datos.Cliente;
import com.unla.funciones.Funciones;

public class ClienteABM {
	private static ClienteABM instancia = null;
	private static ClienteDao dao = null;

	protected ClienteABM() {
		dao = ClienteDao.getInstance();
	}

	public static ClienteABM getInstance() {
		if (instancia == null) {
			instancia = new ClienteABM();
		}
		return instancia;
	}

	public Cliente traer(int dni) {
		return dao.traer(dni);
	}

	public List<Cliente> traer() {
		return dao.traer();
	}

	public void agregar(Cliente objeto) throws Exception {
		Cliente cliente = traer(objeto.getDni());
		if(cliente != null) {
			throw new Exception("Ya existe un cliente con ese DNI.");
		}
		if(Funciones.validarDni(objeto.getDni()) == false)
			throw new Exception("El DNI ingresado no es válido.");
		dao.agregar(objeto);
	}

	public void modificar(Cliente objetoModificado) throws Exception {
		Cliente cliente = traer(objetoModificado.getDni());
		if(cliente == null) {
			throw new Exception("El cliente especificado no existe.");
		}
		dao.modificar(objetoModificado);
	}

	public void eliminar(Cliente objeto) throws Exception {
		long cant = dao.eliminar(objeto);
		if(cant == 0) throw new Exception("No existe el cliente con ese DNI.");
	}
}
