package com.unla.datos;

public class Empleado extends Persona {
	private String cuil;
	
	public Empleado() {}

	public Empleado(String nombre, String apellido, int dni, String cuil, Domicilio domicilio,  ObraSocial obraSocial) {
		super(nombre, apellido, dni, domicilio, obraSocial);
		this.cuil = cuil;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}
}

