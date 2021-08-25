package com.foh.app.models;

public class DetalleVenta {
	private Integer id;
	private Integer idVenta;
	private Integer idProducto;
	private Integer cantidad;
	private String nomProducto;
	private double precio;
	public DetalleVenta() {
		super();
	}
	public DetalleVenta(Integer id, Integer idVenta, Integer idProducto, Integer cantidad, String nomProducto, double precio) {
		super();
		this.id = id;
		this.idVenta = idVenta;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.nomProducto = nomProducto;
		this.precio = precio;
	}
	public Integer getId() {
		return id;
	}
	
	
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdVenta() {
		return idVenta;
	}
	public void setIdVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}
	public Integer getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getNomProducto() {
		return nomProducto;
	}
	public void setNomProducto(String nomProducto) {
		this.nomProducto = nomProducto;
	}
}
