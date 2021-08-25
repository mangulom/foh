package com.foh.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foh.app.dao.ProductoDAO;
import com.foh.app.models.Cliente;
import com.foh.app.models.Producto;
import com.foh.app.models.request.PageRequest;
import com.foh.app.models.response.Error;
import com.foh.app.models.response.Paginacion;
import com.foh.app.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {
	@Autowired
	ProductoDAO productoDao;
	
	public Paginacion getPaginacion() {
		return this.productoDao.getPaginacion();
	}
	
	public Error getError() {
		return this.productoDao.getError();
	}	
	
	@Override
	public List<Producto> consultarProductos(PageRequest pageRequest) {
		List<Producto> listaProductos = new ArrayList<>();
		listaProductos = this.productoDao.consultarProductos(pageRequest);
		if(this.productoDao.getError() != null) {
			return null;
		}
		return listaProductos;
	}
	
	@Override
	public boolean registroProducto(Producto requestProducto) {
		boolean resultado = productoDao.registroProducto(requestProducto);
		if(this.productoDao.getError() != null) {
			return false;
		} else {
			return resultado;
		}
	}
	
	@Override
	public boolean updateProducto(Producto requestProducto) {
		boolean resultado = productoDao.updateProducto(requestProducto);
		if(this.productoDao.getError() != null) {
			return false;
		} else {
			return resultado;
		}
	}	
	
	@Override
	public boolean eliminarProducto(Integer id) {
		boolean resultado = productoDao.eliminarProducto(id);
		if(this.productoDao.getError() != null) {
			return false;
		} else {
			return resultado;
		}
	}	
}
