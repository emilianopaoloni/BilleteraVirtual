package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Configuracion {
	
	//esta clase contiene los metodos para abrir la conexion y crear tablas
	
	public Configuracion() {
		
	}
	
	public Connection abrirConexion() {
		
				Connection c = null;
		
		 try { 
	    	 //establecer conexion con la BD
	    	 c=DriverManager.getConnection("jdbc:sqlite:test.db");
	    	 } catch (SQLException e) {
	    		 System.out.println("No se pudo conectar a la BD: " + e.getMessage());
	    		 e.printStackTrace();  // Esto mostrará más detalles en la consola.
	    		 
	    	 }
		 return c;
	}
	
	public void crearTablas (Connection connection) throws SQLException {
		
		    Statement stmt;
		    stmt = connection.createStatement();
		  
			//Creo tabla ActivoCripto
			String sql = "CREATE TABLE IF NOT EXISTS ACTIVO_CRIPTO"
			+ "("
			+ " NOMENCLATURA VARCHAR(10)  PRIMARY KEY     NOT NULL, "
			+ " CANTIDAD	REAL    NOT NULL " + ")";
			stmt.executeUpdate(sql);
			
			//Creo tabla ActivoFiat
			sql = "CREATE TABLE IF NOT EXISTS ACTIVO_FIAT"
			+ "("
			+ " NOMENCLATURA VARCHAR(10)  PRIMARY KEY     NOT NULL, "
			+ " CANTIDAD	REAL    NOT NULL " + ")";
			stmt.executeUpdate(sql);
			
			//Creo tabla Transaccion
			sql = "CREATE TABLE IF NOT EXISTS TRANSACCION "
			+ "("
			+ " RESUMEN VARCHAR(1000)   NOT NULL, "
			+ " FECHA_HORA		DATETIME  NOT NULL " + ")";
			stmt.executeUpdate(sql);
			
			
			 //creo tabla Moneda:
			sql = "CREATE TABLE IF NOT EXISTS MONEDA" 
			+ "(" 
			+ " TIPO       VARCHAR(1)    NOT NULL, "
			+ " NOMBRE       VARCHAR(50)    NOT NULL, " 
			+ " NOMENCLATURA VARCHAR(10)  PRIMARY KEY   NOT NULL, "
			+ " VALOR_DOLAR	REAL     NOT NULL, " 
			+ " VOLATILIDAD	REAL     NULL, "
			+ " STOCK	REAL     NULL "  + ")";
			stmt.executeUpdate(sql);
			
			stmt.close();
	}

}
