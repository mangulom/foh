package com.foh.app.controller;

import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foh.app.models.DetalleVenta;
import com.foh.app.models.Producto;
import com.foh.app.models.Venta;
import com.foh.app.models.request.PageRequest;
import com.foh.app.models.request.VentaRequest;
import com.foh.app.models.response.Estado;
import com.foh.app.models.response.ResponseObject;
import com.foh.app.service.DetalleVentaService;
import com.foh.app.service.VentaService;

@RestController
@RequestMapping("/foh")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VentaAPI {
	@Autowired
	private VentaService serviceCab;
	@Autowired
	private DetalleVentaService serviceDet;
	
	@PostMapping(path = "/ventas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> consultarVentas(@RequestBody VentaRequest ventaRequest ) {
		ResponseObject response = new ResponseObject();
		try {
			List<Venta> lista = this.serviceCab.consultarVentas(ventaRequest);
			response.setResultado(lista);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch(BadRequestException e) {
			response.setError(this.serviceCab.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		} catch(InternalServerErrorException e) {
			response.setError(this.serviceCab.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(path = "/detalle-venta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> consultarDetalleVenta(@RequestBody Venta ventaRequest ) {
		ResponseObject response = new ResponseObject();
		try {
			List<DetalleVenta> lista = this.serviceDet.consultarDetalleVenta(ventaRequest);
			response.setResultado(lista);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch(BadRequestException e) {
			response.setError(this.serviceCab.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		} catch(InternalServerErrorException e) {
			response.setError(this.serviceCab.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	@PutMapping(path = "/detalle-venta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> registroDetalleVenta(@RequestBody DetalleVenta detalleVenta ) {
		ResponseObject response = new ResponseObject();
		try {
			boolean resultado = this.serviceDet.registroDetalleVenta(detalleVenta);
			response.setResultado(resultado);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch(BadRequestException e) {
			response.setError(this.serviceCab.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		} catch(InternalServerErrorException e) {
			response.setError(this.serviceCab.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	@PutMapping(path = "/venta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> registroVenta(@RequestBody Venta venta ) {
		ResponseObject response = new ResponseObject();
		try {
			Integer resultado = this.serviceCab.registroVentaCab(venta);
			response.setResultado(resultado);
			response.setEstado(Estado.OK);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} catch(BadRequestException e) {
			response.setError(this.serviceCab.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		} catch(InternalServerErrorException e) {
			response.setError(this.serviceCab.getError());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(Exception e) {
			response.setError(1, "Error Interno", e.getMessage());
			response.setEstado(Estado.ERROR);
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	

	
}
