package com.foh.app.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

import com.foh.app.dao.VentaDAO;
import com.foh.app.models.Producto;
import com.foh.app.models.Venta;
import com.foh.app.models.request.PageRequest;
import com.foh.app.models.request.VentaRequest;
import com.foh.app.models.response.Error;
import com.foh.app.models.response.Paginacion;

@Repository
public class VentaDAOImpl implements VentaDAO {
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
	
	public List<Venta> mapearVenta(Map<String, Object> resultados) {
    	this.error = null;
        List<Venta> listaVentas = new ArrayList<>();
        Venta venta = null;		
        List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("o_cursor");
        for(Map<String, Object> map : lista) {
            venta = new Venta();
            venta.setId(((BigDecimal)map.get("ID_VENTA_CABECERA")).intValue());
            venta.setIdCliente(((BigDecimal)map.get("ID_CLIENTE")).intValue());
            venta.setNomCliente((String)map.get("NOMBRES") + " " + (String)map.get("APELLIDOS"));
            venta.setFecha((Date)map.get("FECHA"));
            listaVentas.add(venta);
        }
        return listaVentas;
	}	
	
	@Override
	public List<Venta> consultarVentas(VentaRequest ventaRequest) {
		Map<String, Object> out = null;
		List<Venta> lista = new ArrayList<>();
		this.error=null;
		this.paginacion = new Paginacion();
		paginacion.setPagina(ventaRequest.getPagina());
		paginacion.setRegistros(ventaRequest.getRegistros());		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName("MAURICIO")
				.withCatalogName("PCK_FOH_CLIENTE")
				.withProcedureName("PRC_CONSULTAR_VENTAS_CAB")
				.declareParameters(
						new SqlParameter("i_id", oracle.jdbc.OracleTypes.NUMBER),
						new SqlParameter("i_fecha", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlParameter("i_npagina", oracle.jdbc.OracleTypes.NUMBER),
						new SqlParameter("i_nregistros", oracle.jdbc.OracleTypes.NUMBER),
						new SqlOutParameter("o_cursor", oracle.jdbc.OracleTypes.CURSOR),
						new SqlOutParameter("o_retorno", oracle.jdbc.OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", oracle.jdbc.OracleTypes.VARCHAR));     
				SqlParameterSource in = new MapSqlParameterSource()
			    .addValue("i_id",ventaRequest.getId())
				.addValue("i_fecha",ventaRequest.getFecha())
				.addValue("i_npagina",ventaRequest.getPagina())
				.addValue("i_nregistros",ventaRequest.getRegistros());
		
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = (Integer) out.get("o_retorno");			
			if(resultado == 0) {
				lista = mapearVenta(out);
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
	
	@Override
	public Integer registroVentaCab(Venta requestVenta) {
		Map<String, Object> out = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName("MAURICIO")
				.withCatalogName("PCK_FOH_CLIENTE")
				.withProcedureName("PRC_REGISTRAR_VENTA_CAB")
				.declareParameters(
						new SqlParameter("i_id_cliente", oracle.jdbc.OracleTypes.INTEGER),
						new SqlOutParameter("i_id", oracle.jdbc.OracleTypes.INTEGER),
						new SqlOutParameter("o_retorno", oracle.jdbc.OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", oracle.jdbc.OracleTypes.VARCHAR));     
				SqlParameterSource in = new MapSqlParameterSource()
				.addValue("i_id_cliente",requestVenta.getIdCliente());
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = (Integer) out.get("o_retorno");			
			if(resultado == 0) {
				return (Integer) out.get("o_id");
			} else {
				String mensaje = (String)out.get("o_mensaje");
				String mensajeInterno = (String)out.get("o_sqlerrm");
				this.error = new Error(resultado,mensaje,mensajeInterno);
				return null;
			}
		}catch(Exception e){
			Integer resultado = (Integer)out.get("o_retorno");
			String mensaje = (String)out.get("o_mensaje");
			String mensajeInterno = (String)out.get("o_sqlerrm");
			this.error = new Error(resultado,mensaje,mensajeInterno);
			return null;
		}		
	
	}
}
