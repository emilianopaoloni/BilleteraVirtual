package interfacesDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import modelo_clases.Moneda;
import modelo_clases.PrecioCripto;

public interface MonedaDAO {

	
	
	void generarStock() throws SQLException;
	public double obtenerPrecio(String nomenclaturaCripto);
	 public double obtenerStock(String nomenclaturaCripto);
	 public void actualizarPreciosEnBD(List<PrecioCripto> precios) throws SQLException;
	
}
