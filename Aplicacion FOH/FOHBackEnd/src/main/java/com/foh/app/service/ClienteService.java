package com.foh.app.service;

import java.util.List;

import com.foh.app.models.Cliente;
import com.foh.app.models.request.PageRequest;
import com.foh.app.models.response.Error;
import com.foh.app.models.response.Paginacion;

public interface ClienteService {
	public boolean registroCliente(Cliente requestCliente);
	public List<Cliente> consultarClientes(PageRequest pageRequest);
	public Paginacion getPaginacion();
	public Error getError();
}
