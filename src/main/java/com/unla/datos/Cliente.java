package com.unla.datos;

public class Cliente extends Persona {
	private int id;
	
	public Cliente() {}

	public Cliente(String nombre, String apellido, String dni, Domicilio domicilio, ObraSocial obraSocial) {
		super(nombre, apellido, dni, domicilio, obraSocial);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

