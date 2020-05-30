package com.unla.datos;

public class ObraSocial {
	private String nombre;
	private int nroAfiliado;
	
	public ObraSocial(String nombre, int nroAfiliado) {
		super();
		this.nombre = nombre;
		this.nroAfiliado = nroAfiliado;
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

	@Override
	public String toString() {
		return "ObraSocial [nombre=" + nombre + ", nroAfiliado=" + nroAfiliado + "]";
	}
}
