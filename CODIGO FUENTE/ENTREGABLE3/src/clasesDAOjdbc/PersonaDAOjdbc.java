package clasesDAOjdbc;



import java.sql.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfacesDAO.PersonaDAO;
import modelo_clases.Persona;
import configuracion.MyConnection;

/*Esta clase contiene metodos que acceden a la tabla PERSONA de la BD */
public class PersonaDAOjdbc implements PersonaDAO {
	
	
	
	PersonaDAOjdbc(){
	
	}
	
	
  /*metodo para cargar datos de persona en la tabla PERSONA, retorna el ID asignado por la tabla */	
   public int agregarDatosPersona(Persona persona) throws SQLException {
	   //obtener conexion
	   Connection connection = MyConnection.getCon(); 
	   
	   String insertPersonaSQL = "INSERT INTO PERSONA (NOMBRES, APELLIDOS) VALUES (?, ?)";
       try (PreparedStatement stmt = connection.prepareStatement(insertPersonaSQL, Statement.RETURN_GENERATED_KEYS)) {
           stmt.setString(1, persona.getNombre());
           stmt.setString(2, persona.getApellido());
           stmt.executeUpdate();

           // Obtener el ID generado
           try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
               if (generatedKeys.next()) {
            	   //cierro conexion
            	   //connection.close();
                   return generatedKeys.getInt(1); // Devuelve el ID generado
               } else {
                   throw new SQLException("No se pudo obtener el ID generado para PERSONA.");
               }
           }
       }
       //generatedKeys.close();
       //stmt.close();
   }
   
}
	
	


