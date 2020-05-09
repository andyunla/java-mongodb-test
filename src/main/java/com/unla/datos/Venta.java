package com.unla.datos;

import java.time.LocalDate;
import java.util.List;

public class Venta {
	private LocalDate fecha;
	private String nroTicket;
	private String formaPago;
	private List<Producto> listaProductos;
	private Empleado atentido;
	private Empleado cobrado;
	private Cliente cliente;
	
	public Venta(LocalDate fecha, String nroTicket, String formaPago, List<Producto> listaProductos, Empleado atentido,
			Empleado cobrado, Cliente cliente) {
		super();
		this.fecha = fecha;
		this.nroTicket = nroTicket;
		this.formaPago = formaPago;
		this.listaProductos = listaProductos;
		this.atentido = atentido;
		this.cobrado = cobrado;
		this.cliente = cliente;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getNroTicket() {
		return nroTicket;
	}

	public void setNroTicket(String nroTicket) {
		this.nroTicket = nroTicket;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public Empleado getAtentido() {
		return atentido;
	}

	public void setAtentido(Empleado atentido) {
		this.atentido = atentido;
	}

	public Empleado getCobrado() {
		return cobrado;
	}

	public void setCobrado(Empleado cobrado) {
		this.cobrado = cobrado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
