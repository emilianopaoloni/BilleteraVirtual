package configuracion;

import java.sql.Connection;
import java.sql.SQLException;

import clasesDAOjdbc.UsuarioDAOjdbc;
import vista.VistaInicioSesion;



public class Main {
    public static void main(String[] args) throws SQLException {
    	Connection connection = null; // Inicializa fuera del try para poder cerrarla después.
    	
        try {
            // Obtén la conexión
            connection = MyConnection.getCon();
            if (connection == null) {
                throw new SQLException("No se pudo obtener la conexión a la base de datos");
            }
	    	
	    	//creo las tablas y las monedas a utilizar
	    	Configuracion.creaciónDeTablasEnBD(connection);
	    	
	    
            
	  	    // Crear vista de inicio
	        VistaInicioSesion vistaInicioSesion = new VistaInicioSesion();
            
      
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cierra la conexión manualmente
            if (connection != null && !connection.isClosed()) {
                //connection.close();
                //System.out.println("Conexión cerrada manualmente en el bloque finally.");
            }
        }
    }
}