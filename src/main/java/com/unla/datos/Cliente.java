package com.unla.datos;

public class Cliente extends Persona {
	// private int id;
	
	public Cliente() {}
	
	public Cliente(String nombre, String apellido, String dni, Domicilio domicilio) {
		super(nombre, apellido, dni, domicilio, null);
	}
	
	public Cliente(String nombre, String apellido, String dni, Domicilio domicilio, ObraSocial obraSocial) {
		super(nombre, apellido, dni, domicilio, obraSocial);
	}

	@Override
	public String toString() {
		return "Cliente [toString()=" + super.toString() + "]";
	}
	
	
}

