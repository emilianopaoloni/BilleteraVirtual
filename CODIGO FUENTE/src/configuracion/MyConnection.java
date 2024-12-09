package configuracion;
/* Patron Singleton con una conexion:  Este código obtiene una conexión a la base de datos y la guarda para ser utilizada desde 
cualquier lugar. Solo aca se necesitan conocer los datos del driver usuario, contraseña.
*/
	


import java.sql.*;
/**
Clase para conexion con DB, Usa patron singleton (Unica instanciacion de la conexion)
*/
public class MyConnection {
	
	private static Connection con=null;
	
	
	
	 static {
		    try {
		        con = DriverManager.getConnection("jdbc:sqlite:WALLET.db");
		    } catch (java.sql.SQLException e) {
		    System.out.println("Error de SQL: "+e.getMessage());
		    }
	     }
		    
	         
	       /*si esta cerrada la conexion , la reabro */
		   public static Connection getCon()  {
			  
			   //return con;
		  
   
		   
			   try {
			        if (con == null || con.isClosed()) {
			            con = DriverManager.getConnection("jdbc:sqlite:WALLET.db");
			        }
			    } catch (SQLException e) {
			        System.out.println("Error al obtener la conexión: " + e.getMessage());
			    }
			    return con;
			} 
			   
			   
			   
			   
		  
		   private MyConnection() {
			   
		   } 
}