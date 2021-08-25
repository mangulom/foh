package com.foh.app.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.foh.app.models.response.Error;

@JsonInclude(Include.NON_NULL)
public class ResponseObject {

	private Estado estado;
	private Paginacion paginacion;
	private Error error;
	private Object resultado;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Paginacion getPaginacion() {
		return paginacion;
	}

	public void setPaginacion(Paginacion paginacion) {
		this.paginacion = paginacion;
	}

	public com.foh.app.models.response.Error getError() {
		return error;
	}

	public void setError(com.foh.app.models.response.Error error) {
		this.error = error;
	}

	public void setError(Integer codigo, String mensaje, String mensajeInterno) {
		this.error = new com.foh.app.models.response.Error(codigo, mensaje, mensajeInterno);
	}

	public Object getResultado() {
		return resultado;
	}

	public void setResultado(Object resultado) {
		this.resultado = resultado;
	}
}
