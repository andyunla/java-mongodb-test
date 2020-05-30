package com.unla.negocio;

import java.util.List;

import com.unla.dao.SucursalDao;
import com.unla.datos.Sucursal;

public class SucursalABM {
	private static SucursalABM instancia = null;
	private static SucursalDao dao = null;

	protected SucursalABM() {
		dao = SucursalDao.getInstance();
	}

	public static SucursalABM getInstance() {
		if (instancia == null) {
			instancia = new SucursalABM();
		}
		return instancia;
	}

	public Sucursal traer(int id) {
		return dao.traer(id);
	}

	public List<Sucursal> traer() {
		return dao.traer();
	}

	public void agregar(Sucursal objeto) throws Exception {
		Sucursal sucursal = traer(objeto.getId());
		if(sucursal != null) {
			throw new Exception("Ya existe una Sucursal con ese Id");
		}
		dao.agregar(objeto);
	}

	public void modificar(Sucursal objetoModificado) throws Exception {
		Sucursal sucursal = traer(objetoModificado.getId());
		if(sucursal == null) {
			throw new Exception("La Sucursal especificado no existe.");
		}
		dao.modificar(objetoModificado);
	}

	public void eliminar(Sucursal objeto) throws Exception {
		long cant = dao.eliminar(objeto);
		if(cant == 0) throw new Exception("No existe la Sucursal con ese Id.");
	}
}