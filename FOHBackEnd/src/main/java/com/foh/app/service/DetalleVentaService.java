package com.foh.app.service;

import java.util.List;

import com.foh.app.models.DetalleVenta;
import com.foh.app.models.Venta;
import com.foh.app.models.request.VentaRequest;
import com.foh.app.models.response.Error;
import com.foh.app.models.response.Paginacion;

public interface DetalleVentaService {
	public List<DetalleVenta> consultarDetalleVenta(Venta ventaRequest);
	public boolean registroDetalleVenta(DetalleVenta detalleVenta);
	public Paginacion getPaginacion();
	public Error getError();
}
