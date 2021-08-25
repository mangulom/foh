package com.foh.app.dao;

import java.util.List;


import com.foh.app.models.Venta;
import com.foh.app.models.request.PageRequest;
import com.foh.app.models.request.VentaRequest;
import com.foh.app.models.response.Error;
import com.foh.app.models.response.Paginacion;

public interface VentaDAO {
	public Integer registroVentaCab(Venta requestVenta);
	public List<Venta> consultarVentas(VentaRequest ventaRequest);
	public Paginacion getPaginacion();
	public Error getError();
}
