package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import DAOFactory.ConexionMySQL;
import modelo.Producto;

public class DAOProducto implements DAO<Producto>{
	
	private Connection conn;
	

	@Override
	public void cargar(CSVParser datos) throws SQLException {
		conn = ConexionMySQL.conectar();
		String insert = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?, ?, ?)"; 
		
		for(CSVRecord fila: datos) {
			
			PreparedStatement ps = conn.prepareStatement(insert);
			ps.setInt(1, Integer.parseInt(fila.get("idProducto")));
			ps.setString(2, fila.get("nombre"));
			ps.setFloat(3, Float.parseFloat(fila.get("valor")));
			ps.executeUpdate();
			conn.commit();
			ps.close();
		}
		conn.close();
		
	}

	@Override
	public void crearTabla() throws SQLException {
		conn = ConexionMySQL.conectar();
		
		String producto = "CREATE TABLE producto(" +
				"idProducto INT," +
				"nombre VARCHAR(45)," +
				"valor FLOAT," +
				"PRIMARY KEY (idProducto))";
		conn.prepareStatement(producto).execute();
		conn.commit();
		conn.close();
		
	}
	
	public Producto productoMasRecaudo() throws SQLException {
		conn = ConexionMySQL.conectar();
		Producto productoMasRecaudo;
		
		String producto = "SELECT p.*, SUM(p.valor * fp.cantidad) AS totalRecaudado" +
				"FROM producto p NATURAL JOIN facturaProducto fp" +
				"GROUP BY idProducto " + 
				"ORDER BY `totalRecaudado` DESC " + 
				"LIMIT 1";
		
		PreparedStatement ps = conn.prepareStatement(producto);
		ResultSet rs = ps.executeQuery();
		productoMasRecaudo = new Producto(rs.getInt(1), rs.getString(2), rs.getFloat(3));
		ps.close();
		rs.close();
		conn.close();
		return productoMasRecaudo;
	}

	
}
