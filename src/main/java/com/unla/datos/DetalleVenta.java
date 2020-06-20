package com.unla.datos;

public class DetalleVenta {
	private Producto producto;
	private int cantidad;
	private double subTotal;
	public DetalleVenta() {
		super();
	}
	public DetalleVenta(Producto producto, int cantidad, double subTotal) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
		this.subTotal = subTotal;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
}
