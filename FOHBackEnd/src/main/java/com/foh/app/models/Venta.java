package com.foh.app.models;

import java.util.Date;
import java.util.List;

public class Venta {
	private Integer id;
	private Integer idCliente;
	private Date fecha;
	private String nomCliente;
	
	public Venta() {
		super();
	}
	public Venta(Integer id, Integer idCliente, Date fecha, String nomCliente) {
		super();
		this.id = id;
		this.idCliente = idCliente;
		this.fecha = fecha;
		this.nomCliente = nomCliente;
	}	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getNomCliente() {
		return nomCliente;
	}
	public void setNomCliente(String nomCliente) {
		this.nomCliente = nomCliente;
	}
	
	
}
