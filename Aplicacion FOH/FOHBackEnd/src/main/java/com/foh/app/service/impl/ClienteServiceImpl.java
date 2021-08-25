package com.foh.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foh.app.dao.ClienteDAO;
import com.foh.app.models.response.Paginacion;
import com.foh.app.models.Cliente;
import com.foh.app.service.ClienteService;
import com.foh.app.models.request.PageRequest;
import com.foh.app.models.response.Error;


@Service
public class ClienteServiceImpl implements ClienteService {
	@Autowired
	ClienteDAO clienteDao;
	
	public Paginacion getPaginacion() {
		return this.clienteDao.getPaginacion();
	}
	
	public Error getError() {
		return this.clienteDao.getError();
	}	
	
	@Override
	public List<Cliente> consultarClientes(PageRequest pageRequest) {
		List<Cliente> listaClientes = new ArrayList<>();
		listaClientes = this.clienteDao.consultarClientes(pageRequest);
		if(this.clienteDao.getError() != null) {
			return null;
		}
		return listaClientes;
	}
	
	@Override
	public boolean registroCliente(Cliente requestCliente) {
		boolean resultado = clienteDao.registroCliente(requestCliente);
		if(this.clienteDao.getError() != null) {
			return false;
		} else {
			return resultado;
		}
	}
}
