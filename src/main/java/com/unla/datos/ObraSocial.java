package com.unla.datos;

public class ObraSocial {
	private int id;
	private String nombre;
	private int nroAfiliado;
	
	public ObraSocial(int id, String nombre, int nroAfiliado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nroAfiliado = nroAfiliado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNroAfiliado() {
		return nroAfiliado;
	}

	public void setNroAfiliado(int nroAfiliado) {
		this.nroAfiliado = nroAfiliado;
	}
}
