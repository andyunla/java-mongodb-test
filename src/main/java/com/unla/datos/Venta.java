package com.unla.datos;

import java.time.LocalDate;
import java.util.List;

public class Venta {
	private LocalDate fecha;
	private String nroTicket;
	private String formaPago;
	private List<DetalleVenta> detalleVentas;
	private Empleado vendedor;
	private Empleado cobrador;
	private Cliente cliente;
	private double precioTotal;
	
	public Venta(LocalDate fecha, String nroTicket, String formaPago, List<DetalleVenta> detalleVentas, Empleado vendedor,
			Empleado cobrador, Cliente cliente) {
		super();
		this.fecha = fecha;
		this.nroTicket = nroTicket;
		this.formaPago = formaPago;
		this.detalleVentas = detalleVentas;
		this.vendedor = vendedor;
		this.cobrador = cobrador;
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

	public List<DetalleVenta> getDetalleVentas() {
		return detalleVentas;
	}

	public void setDetalleVentas(List<DetalleVenta> detalleVentas) {
		this.detalleVentas = detalleVentas;
	}

	public Empleado getVendedor() {
		return vendedor;
	}

	public void setVendedor(Empleado vendedor) {
		this.vendedor = vendedor;
	}

	public Empleado getCobrador() {
		return cobrador;
	}

	public void setCobrador(Empleado cobrador) {
		this.cobrador = cobrador;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}
}
