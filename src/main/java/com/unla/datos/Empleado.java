package com.unla.datos;

public class Empleado extends Persona {
	private int id;
	private String cuil;
	
	public Empleado() {}

	public Empleado(String nombre, String apellido, String dni, String cuil, Domicilio domicilio,  ObraSocial obraSocial) {
		super(nombre, apellido, dni, domicilio, obraSocial);
		this.cuil = cuil;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}
}

