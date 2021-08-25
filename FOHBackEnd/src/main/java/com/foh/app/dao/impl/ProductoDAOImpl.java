package com.foh.app.dao.impl;

import java.math.BigDecimal;
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

import com.foh.app.dao.ProductoDAO;
import com.foh.app.models.Producto;
import com.foh.app.models.request.PageRequest;
import com.foh.app.models.response.Error;
import com.foh.app.models.response.Paginacion;

@Repository
public class ProductoDAOImpl implements ProductoDAO{
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
	
	public List<Producto> mapearProducto(Map<String, Object> resultados) {
    	this.error = null;
        List<Producto> listaProductos = new ArrayList<>();
        Producto producto = null;		
        List<Map<String, Object>> lista = (List<Map<String, Object>>)resultados.get("o_cursor");
        for(Map<String, Object> map : lista) {
            producto = new Producto();
            producto.setId(((BigDecimal)map.get("ID")).intValue());
            producto.setNombre((String)map.get("NOMBRE"));
            producto.setPrecio(((BigDecimal)map.get("PRECIO")).doubleValue());
            listaProductos.add(producto);
        }
        return listaProductos;
	}
	
	@Override
	public List<Producto> consultarProductos(PageRequest pageRequest) {
		Map<String, Object> out = null;
		List<Producto> lista = new ArrayList<>();
		this.error=null;
		this.paginacion = new Paginacion();
		paginacion.setPagina(pageRequest.getPagina());
		paginacion.setRegistros(pageRequest.getRegistros());
		
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName("MAURICIO")
				.withCatalogName("PCK_FOH_CLIENTE")
				.withProcedureName("PRC_CONSULTAR_PRODUCTOS")
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
				lista = mapearProducto(out);
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
	public boolean registroProducto(Producto requestProducto) {
		Map<String, Object> out = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName("MAURICIO")
				.withCatalogName("PCK_FOH_CLIENTE")
				.withProcedureName("PRC_REGISTRAR_PRODUCTO")
				.declareParameters(
						new SqlParameter("i_nombre", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlParameter("i_precio", oracle.jdbc.OracleTypes.NUMBER),
						new SqlOutParameter("o_retorno", oracle.jdbc.OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", oracle.jdbc.OracleTypes.VARCHAR));     
				SqlParameterSource in = new MapSqlParameterSource()
				.addValue("i_nombre",requestProducto.getNombre())
				.addValue("i_precio",requestProducto.getPrecio());
		
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

	@Override
	public boolean updateProducto(Producto requestProducto) {
		Map<String, Object> out = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName("MAURICIO")
				.withCatalogName("PCK_FOH_CLIENTE")
				.withProcedureName("PRC_UPDATE_PRODUCTO")
				.declareParameters(
						new SqlParameter("i_id", oracle.jdbc.OracleTypes.INTEGER),
						new SqlParameter("i_nombre", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlParameter("i_precio", oracle.jdbc.OracleTypes.NUMBER),
						new SqlOutParameter("o_retorno", oracle.jdbc.OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", oracle.jdbc.OracleTypes.VARCHAR));     
				SqlParameterSource in = new MapSqlParameterSource()
				.addValue("i_id",requestProducto.getId())
				.addValue("i_nombre",requestProducto.getNombre())
				.addValue("i_precio",requestProducto.getPrecio());
		
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

	@Override
	public boolean eliminarProducto(Integer id) {
		Map<String, Object> out = null;
		this.jdbcCall = new SimpleJdbcCall(this.jdbc)
				.withSchemaName("MAURICIO")
				.withCatalogName("PCK_FOH_CLIENTE")
				.withProcedureName("PRC_ELIMINAR_PRODUCTO")
				.declareParameters(
						new SqlParameter("i_id", oracle.jdbc.OracleTypes.INTEGER),
						new SqlOutParameter("o_retorno", oracle.jdbc.OracleTypes.INTEGER),
						new SqlOutParameter("o_mensaje", oracle.jdbc.OracleTypes.VARCHAR),
						new SqlOutParameter("o_sqlerrm", oracle.jdbc.OracleTypes.VARCHAR));     
				SqlParameterSource in = new MapSqlParameterSource()
				.addValue("i_id",id);
		
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
