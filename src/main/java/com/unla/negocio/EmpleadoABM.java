package com.unla.negocio;

import java.util.List;

import com.unla.dao.EmpleadoDao;
import com.unla.datos.Empleado;

public class EmpleadoABM {
	private static EmpleadoABM instancia = null;
	private static EmpleadoDao dao = null;

	protected EmpleadoABM() {
		dao = EmpleadoDao.getInstance();
	}

	public static EmpleadoABM getInstance() {
		if (instancia == null) {
			instancia = new EmpleadoABM();
		}
		return instancia;
	}

	public Empleado traer(String cuil) {
		return dao.traer(cuil);
	}

	public List<Empleado> traer() {
		return dao.traer();
	}

	public void agregar(Empleado objeto) throws Exception {
		Empleado empleado = traer(objeto.getCuil());
		if(empleado != null) {
			throw new Exception("Ya existe un empleado con ese CUIL.");
		}
		dao.agregar(objeto);
	}

	public void modificar(Empleado objetoModificado) throws Exception {
		Empleado empleado = traer(objetoModificado.getCuil());
		if(empleado == null) {
			throw new Exception("El empleado especificado no existe.");
		}
		dao.modificar(objetoModificado);
	}

	public void eliminar(Empleado objeto) throws Exception {
		long cant = dao.eliminar(objeto);
		if(cant == 0) throw new Exception("No existe el empleado con ese CUIL.");
	}
}
