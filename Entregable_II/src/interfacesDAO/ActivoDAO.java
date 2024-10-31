
package interfacesDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

import modelo_clases.Activo;

public interface ActivoDAO {
	
	void crearTablas(Connection connection) throws SQLException;
	int generarMisActivos(Connection connection, String tipoA, Activo act) throws SQLException;
	//void listarMisActivos(Connection connection) throws SQLException;
	LinkedList<Activo> obtenerDatosActivos(Connection connection) throws SQLException;
	int comprarCripto (Connection connection, String cripto, String fiat, double monto ) throws SQLException;
	int transaccionSwap (Connection connection, String criptoAconvertir, double monto, String criptoEsperada ) throws SQLException;
	double criptoAComprar (Connection connection, String cripto, String fiat, double monto) throws SQLException;

}

