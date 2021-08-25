package com.foh.app.service;

import java.util.List;

import com.foh.app.models.DetalleVenta;
import com.foh.app.models.Venta;
import com.foh.app.models.request.PageRequest;
import com.foh.app.models.request.VentaRequest;
import com.foh.app.models.response.Error;
import com.foh.app.models.response.Paginacion;

public interface VentaService {
	public List<Venta> consultarVentas(VentaRequest ventaRequest);
	public Integer registroVentaCab(Venta requestVenta);
	public Paginacion getPaginacion();
	public Error getError();
}
