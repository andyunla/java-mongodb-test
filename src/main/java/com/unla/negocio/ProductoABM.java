package com.unla.negocio;

import java.util.List;

import com.unla.dao.ProductoDao;
import com.unla.datos.Producto;

public class ProductoABM {

	private static ProductoABM instancia = null;
	private static ProductoDao dao = null;

	protected ProductoABM() {
		dao = ProductoDao.getInstance();
	}

	public static ProductoABM getInstance() {
		if (instancia == null) {
			instancia = new ProductoABM();
		}
		return instancia;
	}

	public Producto traer(int codigo) {
		return dao.traer(codigo);
	}

	public List<Producto> traer() {
		return dao.traer();
	}

	public void agregar(Producto objeto) throws Exception {
		Producto producto = traer(objeto.getCodigo());
		if(producto != null) {
			throw new Exception("Ya existe un producto con ese codigo.");
		}/*
		if(Funciones.validarDni(objeto.getDni()) == false)
			throw new Exception("El codigo ingresado no es valido.");*/
		dao.agregar(objeto);
	}

	public void modificar(Producto objetoModificado) throws Exception {
		Producto producto = traer(objetoModificado.getCodigo());
		if(producto == null) {
			throw new Exception("El producto especificado no existe.");
		}
		dao.modificar(objetoModificado);
	}

	public void eliminar(Producto objeto) throws Exception {
		long cant = dao.eliminar(objeto);
		if(cant == 0) throw new Exception("No existe un producto con ese codigo.");
	}

}
