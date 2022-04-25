package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import conexion.ConexionMySQL;
import modelo.Producto;

public class DAOProducto implements DAO<Producto>{

	@Override
	public void cargar(CSVParser datos, Connection conn) throws SQLException {
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
	public void crearTabla(Connection conn) throws SQLException {
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

	
}
