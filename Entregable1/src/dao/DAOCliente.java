package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import conexion.ConexionMySQL;
import modelo.Cliente;

public class DAOCliente implements DAO<Cliente> {
	
	//private Connection conn;

	@Override
	public void cargar(CSVParser datos, Connection conn) throws SQLException {
		conn = ConexionMySQL.conectar();
		String insert = "INSERT INTO cliente (idCliente, nombre, email) VALUES (?, ?, ?)"; 
		
		for(CSVRecord fila: datos) {
			
			PreparedStatement ps = conn.prepareStatement(insert);
			//ps.setInt(1, Integer.parseInt(fila.get("idCliente")));
			ps.setString(2, fila.get("nombre"));
			ps.setString(3, fila.get("email"));
			ps.executeUpdate();
			conn.commit();
			ps.close();
		}
		conn.close();
	}

	@Override
	public void crearTabla(Connection conn) throws SQLException {
		conn = ConexionMySQL.conectar();
		
		String cliente = "CREATE TABLE cliente(" +
				"idCliente INT," +
				"nombre VARCHAR(500)," +
				"email VARCHAR(150)," +
				"PRIMARY KEY (idCliente))";
		conn.prepareStatement(cliente).execute();
		conn.commit();
		conn.close();
	}

	

}
