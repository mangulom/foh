package com.foh.app.dao;

import java.util.List;

import com.foh.app.models.DetalleVenta;
import com.foh.app.models.Venta;
import com.foh.app.models.request.VentaRequest;
import com.foh.app.models.response.Error;
import com.foh.app.models.response.Paginacion;

public interface DetalleVentaDAO {
	public List<DetalleVenta> consultarDetalleVenta(Venta ventaRequest);
	public boolean registroDetalleVenta(DetalleVenta requestDetalleVenta);
	public Paginacion getPaginacion();
	public Error getError();
}
