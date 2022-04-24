package dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.csv.CSVParser;

public interface DAO<T> {
	
	public void cargar(CSVParser datos, Connection conn) throws SQLException;
	public void crearTabla(Connection conn) throws SQLException;

}
