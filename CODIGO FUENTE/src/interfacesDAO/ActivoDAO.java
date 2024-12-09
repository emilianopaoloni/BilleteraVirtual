
package interfacesDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

import configuracion.DatosUsuario;
import excepciones.SaldoFiatInsuficienteException;
import excepciones.StockInsuficienteException;
import modelo_clases.Activo;

public interface ActivoDAO {
	
	public void generarActivoFiat( Activo af, int idUsuario) throws SQLException ;
	public LinkedList<Activo> obtenerDatosActivos(int idUsuario) throws SQLException;
	public double calcularBalance (int idUsuario) throws SQLException;
	public double criptoAComprar ( String nomenclaturaCripto, String nomenclaturaFiat, double monto) throws SQLException;
	public void  comprarCripto ( String cripto, String fiat, double montoFiat, DatosUsuario datosUsuario ) throws   SQLException, StockInsuficienteException, SaldoFiatInsuficienteException;
		
	
	
	
	

}

