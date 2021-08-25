package com.foh.app.dao;

import java.util.List;

import com.foh.app.models.Producto;
import com.foh.app.models.request.PageRequest;
import com.foh.app.models.response.Error;
import com.foh.app.models.response.Paginacion;

public interface ProductoDAO {
	public boolean registroProducto(Producto requestProducto);
	public boolean updateProducto(Producto requestProducto);
	public boolean eliminarProducto(Integer id);
	public List<Producto> consultarProductos(PageRequest pageRequest);
	public Paginacion getPaginacion();
	public Error getError();
}
