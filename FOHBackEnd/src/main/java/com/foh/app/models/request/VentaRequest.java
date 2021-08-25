package com.foh.app.models.request;

import java.util.Date;

public class VentaRequest {
	private Integer pagina;
	private Integer registros;
	private String orden;
	private Integer id;
	private String fecha;
	public VentaRequest() {
		super();
	}
	public VentaRequest(Integer pagina, Integer registros, String orden, Integer id, String fecha) {
		super();
		this.pagina = pagina;
		this.registros = registros;
		this.orden = orden;
		this.id = id;
		this.fecha = fecha;
	}
	public Integer getPagina() {
		return pagina;
	}
	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}
	public Integer getRegistros() {
		return registros;
	}
	public void setRegistros(Integer registros) {
		this.registros = registros;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
}
