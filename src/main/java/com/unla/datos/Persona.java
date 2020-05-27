package com.unla.datos;

import com.unla.funciones.Funciones;

public abstract class Persona {
	private String nombre;
	private String apellido;
	private int dni;
	private Domicilio domicilio;
	private ObraSocial obraSocial;
	
	public Persona() {}

	public Persona(String nombre, String apellido, int dni, Domicilio domicilio, ObraSocial obraSocial) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.domicilio = domicilio;
		this.obraSocial = obraSocial;
		setDni(dni);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		if(Funciones.validarDni(dni)) {
			this.dni = dni;
		} else {
			dni = 0;
		}
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public ObraSocial getObraSocial() {
		return obraSocial;
	}

	public void setObraSocial(ObraSocial obraSocial) {
		this.obraSocial = obraSocial;
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", domicilio="
				+ domicilio + ", obraSocial=" + obraSocial + "]";
	}
	
	
}
