package com.foh.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foh.app.dao.ClienteDAO;
import com.foh.app.dao.VentaDAO;
import com.foh.app.models.Cliente;
import com.foh.app.models.DetalleVenta;
import com.foh.app.models.Producto;
import com.foh.app.models.Venta;
import com.foh.app.models.request.PageRequest;
import com.foh.app.models.request.VentaRequest;
import com.foh.app.models.response.Error;
import com.foh.app.models.response.Paginacion;
import com.foh.app.service.VentaService;


@Service
public class VentaServiceImpl implements VentaService {
	@Autowired
	VentaDAO ventaDao;
	
	public Paginacion getPaginacion() {
		return this.ventaDao.getPaginacion();
	}
	
	public Error getError() {
		return this.ventaDao.getError();
	}	
	
	@Override
	public List<Venta> consultarVentas(VentaRequest ventaRequest) {
		List<Venta> listaVentas = new ArrayList<>();
		listaVentas = this.ventaDao.consultarVentas(ventaRequest);
		if(this.ventaDao.getError() != null) {
			return null;
		}
		return listaVentas;
	}	
	
	@Override
	public Integer registroVentaCab(Venta requestVenta) {
		Integer resultado = ventaDao.registroVentaCab(requestVenta);
		if(this.ventaDao.getError() != null) {
			return null;
		} else {
			return resultado;
		}
	}	
}
