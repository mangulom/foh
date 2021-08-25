package com.foh.app.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.foh.app.dao.ClienteDAO;
import com.foh.app.models.Cliente;
import com.foh.app.models.request.PageRequest;
import com.foh.app.models.response.Error;
import com.foh.app.models.response.Paginacion;
import java.math.BigDecimal;


@Repository
public class ClienteDAOImpl implements ClienteDAO {
	@Autowired
	private JdbcTemplate jdbc;	
	private SimpleJdbcCall jdbcCall;
	private Paginacion paginacion;
	private Error error;
	
	@Override
	public Paginacion getPaginacion() {
		return this.paginacion;
	}

	public Error getError() {
		return this.error;
	}
	
	public JdbcTemplate getJdbc() {
		return jdbc;
	}
	
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	public List<Cliente> mapearCliente(Map<String, Object> resultados) {
    	this.error = null;
        List<Cliente> listaClientes = new ArrayList<>();
        Cliente cliente = null;		
        List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("o_cursor");
        for(Map<String, Object> map : lista) {
            cliente = new Cliente();
            cliente.setId(((BigDecimal)map.get("ID")).intValue());
            cliente.setNombres((String)map.get("NOMBRES"));
            cliente.setApellidos((String)map.get("APELLIDOS"));
            cliente.setDni((String)map.get("DNI"));
            cliente.setTelefono((String)map.get("TELEFONO"));
            cliente.setMail((String)map.get("MAIL"));
            listaClientes.add(cliente);
        }
        return listaClientes;
	}
	
	@Override
	public List<Cliente> consultarClientes(PageRequest pageRequest) {
		Map<String, Object> out = null;
		List<Cliente> lista = new ArrayList<>();
		this.error=null;
		this.paginacion = new Paginacion();
		paginacion.setPagina(pageRequest.getPagina());
		paginacion.setRegistros(pageRequest.getRegistros());
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName("MAURICIO")
				.withCatalogName("PCK_FOH_CLIENTE")
				.withProcedureName("PRC_CONSULTAR_CLIENTES")
				.declareParameters(
						new SqlParameter("i_npagina", oracle.jdbc.OracleTypes.NUMBER),
						new SqlParameter("i_nregistros", oracle.jdbc.OracleTypes.NUMBER),
						new SqlOutParameter("o_cursor", oracle.jdbc.OracleTypes.CURSOR),
						new SqlOutParameter("o_retorno", oracle.jdbc.OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", oracle.jdbc.OracleTypes.VARCHAR));     
				SqlParameterSource in = new MapSqlParameterSource()
				.addValue("i_npagina",pageRequest.getPagina())
				.addValue("i_nregistros",pageRequest.getRegistros());
		
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = (Integer) out.get("o_retorno");			
			if(resultado == 0) {
				lista = mapearCliente(out);
			} else {
				String mensaje = (String)out.get("o_mensaje");
				String mensajeInterno = (String)out.get("o_sqlerrm");
				this.error = new Error(resultado,mensaje,mensajeInterno);
			}
		}catch(Exception e){
			Integer resultado = (Integer)out.get("o_retorno");
			String mensaje = (String)out.get("o_mensaje");
			String mensajeInterno = (String)out.get("o_sqlerrm");
			this.error = new Error(resultado,mensaje,mensajeInterno);
		}
		
		return lista;
	}
	
	public boolean registroCliente(Cliente requestCliente) {
		Map<String, Object> out = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName("MAURICIO")
				.withCatalogName("PCK_FOH_CLIENTE")
				.withProcedureName("PRC_REGISTRAR_CLIENTE")
				.declareParameters(
						new SqlParameter("i_nombres", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlParameter("i_apellidos", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlParameter("i_dni", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlParameter("i_telefono", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlParameter("i_mail", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlOutParameter("o_retorno", oracle.jdbc.OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", oracle.jdbc.OracleTypes.VARCHAR));     
				SqlParameterSource in = new MapSqlParameterSource()
				.addValue("i_nombres",requestCliente.getNombres())
				.addValue("i_apellidos",requestCliente.getApellidos())
				.addValue("i_dni",requestCliente.getDni())
				.addValue("i_telefono",requestCliente.getTelefono())
				.addValue("i_mail",requestCliente.getMail());
		
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = (Integer) out.get("o_retorno");			
			if(resultado == 0) {
				return true;
			} else {
				String mensaje = (String)out.get("o_mensaje");
				String mensajeInterno = (String)out.get("o_sqlerrm");
				this.error = new Error(resultado,mensaje,mensajeInterno);
				return false;
			}
		}catch(Exception e){
			Integer resultado = (Integer)out.get("o_retorno");
			String mensaje = (String)out.get("o_mensaje");
			String mensajeInterno = (String)out.get("o_sqlerrm");
			this.error = new Error(resultado,mensaje,mensajeInterno);
			return false;
		}		
	}	
}
