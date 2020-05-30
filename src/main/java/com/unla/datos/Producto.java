package com.unla.datos;

public class Producto {
	private int codigo;
	private String tipoProducto;
	private String descripcion;
	private String laboratorio;
	private double precio;
	
	public Producto(int codigo, String tipoProducto, String descripcion, String laboratorio, double precio) {
		super();
		this.codigo = codigo;
		this.tipoProducto = tipoProducto;
		this.descripcion = descripcion;
		this.laboratorio = laboratorio;
		this.precio = precio;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto [codigo=" + codigo + ", tipoProducto=" + tipoProducto + ", descripcion=" + descripcion
				+ ", laboratorio=" + laboratorio + ", precio=" + precio + "]";
	}

}