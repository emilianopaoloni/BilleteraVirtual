package clasesDAOjdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;

import configuracion.MyConnection;
import modelo_clases.Activo;
import modelo_clases.Transaccion;

public class TransaccionDAOjdbc {
	
	public TransaccionDAOjdbc(){
		
	}
	
	public LinkedList<Transaccion> obtenerDatosTransaccion( int idUsuario) throws SQLException{
		  
				LinkedList<Transaccion> listaTransaccion = new LinkedList<Transaccion>();
			    Connection connection = MyConnection.getCon();

			    if (connection == null) {
			        throw new SQLException("Error al obtener la conexión a la base de datos.");
			    }

			    // Consultas para obtener transaccion
			    String sql = "SELECT RESUMEN, FECHA_HORA FROM TRANSACCION WHERE ID_USUARIO = ?";
			  
			        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			            stmt.setInt(1, idUsuario); // Establecer el ID del usuario
			            ResultSet rs = stmt.executeQuery();

			            while (rs.next()) {
			                String resumen = rs.getString("RESUMEN");
			                String fechaYhora = rs.getString("FECHA_HORA"); //los recupero como un string
			                
			             
			                // Crear un objeto Activo y agregarlo a la lista
			                Transaccion transaccion = new Transaccion(resumen, fechaYhora);
			                listaTransaccion.add(transaccion);
			            }
			            stmt.close();
			        } catch (SQLException e) {
			            e.printStackTrace();
			            throw new RuntimeException("Error al obtener los activos del usuario: " + e.getMessage());
			        }
			    

			    return listaTransaccion;
	}

}
