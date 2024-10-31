package interfacesDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

import modelo_clases.Moneda;

public interface MonedaDAO {

	void crearTablaMoneda(Connection connection) throws SQLException;
	void crearMoneda(Connection connection, Moneda m) throws SQLException;
	LinkedList<Moneda> obtenerDatosMoneda(Connection connection) throws SQLException;
	void generarStock(Connection connection) throws SQLException;
	
}
