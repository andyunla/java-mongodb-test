package com.unla.datos;

import java.util.List;

public class sucursal {
	private int id;
	private Empleado supervisor;
	private List<Empleado> listaEmpleados;
	private Domicilio domicilio;
	
	public sucursal(int id, Empleado supervisor, List<Empleado> listaEmpleados, Domicilio domicilio) {
		super();
		this.id = id;
		this.supervisor = supervisor;
		this.listaEmpleados = listaEmpleados;
		this.domicilio = domicilio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Empleado getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Empleado supervisor) {
		this.supervisor = supervisor;
	}

	public List<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(List<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
}