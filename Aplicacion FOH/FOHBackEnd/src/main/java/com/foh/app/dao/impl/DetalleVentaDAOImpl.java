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

import com.foh.app.dao.DetalleVentaDAO;
import com.foh.app.models.DetalleVenta;
import com.foh.app.models.Venta;
import com.foh.app.models.request.VentaRequest;
import com.foh.app.models.response.Error;
import com.foh.app.models.response.Paginacion;

@Repository
public class DetalleVentaDAOImpl implements DetalleVentaDAO{
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
	
	public List<DetalleVenta> mapearDetalleVenta(Map<String, Object> resultados) {
    	this.error = null;
        List<DetalleVenta> listaDetalleVentas = new ArrayList<>();
        DetalleVenta detalleVenta = null;		
        List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("o_cursor");
        for(Map<String, Object> map : lista) {
        	detalleVenta = new DetalleVenta();
        	detalleVenta.setId(((BigDecimal) map.get("ID_DETALLE_VENTA")).intValue());
        	detalleVenta.setIdProducto(((BigDecimal) map.get("ID_PRODUCTO")).intValue());
        	detalleVenta.setCantidad(((BigDecimal) map.get("CANTIDAD")).intValue());
        	detalleVenta.setPrecio(((BigDecimal) map.get("PRECIO")).doubleValue());
        	detalleVenta.setNomProducto((String) map.get("NOMBRE"));
            listaDetalleVentas.add(detalleVenta);
        }
        return listaDetalleVentas;
	}	
	
	@Override
	public List<DetalleVenta> consultarDetalleVenta(Venta ventaRequest) {
		Map<String, Object> out = null;
		List<DetalleVenta> lista = new ArrayList<>();
		this.error=null;
		this.paginacion = new Paginacion();
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName("MAURICIO")
				.withCatalogName("PCK_FOH_CLIENTE")
				.withProcedureName("PRC_CONSULTAR_VENTAS_DET")
				.declareParameters(
						new SqlParameter("i_id_venta", oracle.jdbc.OracleTypes.NUMBER),
						new SqlParameter("i_npagina", oracle.jdbc.OracleTypes.NUMBER),
						new SqlParameter("i_nregistros", oracle.jdbc.OracleTypes.NUMBER),
						new SqlOutParameter("o_cursor", oracle.jdbc.OracleTypes.CURSOR),
						new SqlOutParameter("o_retorno", oracle.jdbc.OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", oracle.jdbc.OracleTypes.VARCHAR));     
				SqlParameterSource in = new MapSqlParameterSource()
			    .addValue("i_id_venta",ventaRequest.getId())
				.addValue("i_npagina", 1)
				.addValue("i_nregistros", 100);		
		try {
			out = this.jdbcCall.execute(in);
			Integer resultado = (Integer) out.get("o_retorno");			
			if(resultado == 0) {
				lista = mapearDetalleVenta(out);
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
	public boolean registroDetalleVenta(DetalleVenta detalleVenta) {
		Map<String, Object> out = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName("MAURICIO")
				.withCatalogName("PCK_FOH_CLIENTE")
				.withProcedureName("PRC_REGISTRAR_VENTA_DET")
				.declareParameters(
						new SqlParameter("i_id_venta", oracle.jdbc.OracleTypes.INTEGER),
						new SqlParameter("i_id_producto", oracle.jdbc.OracleTypes.INTEGER),
						new SqlParameter("i_cantidad", oracle.jdbc.OracleTypes.INTEGER),
						new SqlOutParameter("o_retorno", oracle.jdbc.OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", oracle.jdbc.OracleTypes.VARCHAR));     
				SqlParameterSource in = new MapSqlParameterSource()
				.addValue("i_id_venta",detalleVenta.getIdVenta())
				.addValue("i_id_producto",detalleVenta.getIdProducto())
				.addValue("i_cantidad",detalleVenta.getCantidad())
				;
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
