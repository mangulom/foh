package com.foh.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foh.app.dao.DetalleVentaDAO;
import com.foh.app.models.DetalleVenta;
import com.foh.app.models.Venta;
import com.foh.app.models.request.VentaRequest;
import com.foh.app.models.response.Error;
import com.foh.app.models.response.Paginacion;
import com.foh.app.service.DetalleVentaService;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {
	@Autowired
	DetalleVentaDAO detalleVentaDao;
	
	public Paginacion getPaginacion() {
		return this.detalleVentaDao.getPaginacion();
	}
	
	public Error getError() {
		return this.detalleVentaDao.getError();
	}		
	
	@Override
	public List<DetalleVenta> consultarDetalleVenta(Venta ventaRequest) {
	List<DetalleVenta> listaDetalleVenta = new ArrayList<>();
	listaDetalleVenta = this.detalleVentaDao.consultarDetalleVenta(ventaRequest);
	if(this.detalleVentaDao.getError() != null) {
		return null;
	}
	return listaDetalleVenta;
	}
	
	@Override
	public boolean registroDetalleVenta(DetalleVenta detalleVenta) {
		boolean resultado = detalleVentaDao.registroDetalleVenta(detalleVenta);
		if(this.detalleVentaDao.getError() != null) {
			return false;
		} else {
			return resultado;
		}
	}
}
