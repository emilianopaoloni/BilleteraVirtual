package interfacesDAO;


import java.sql.SQLException;

import modelo_clases.Persona;

public interface PersonaDAO {

	
	   public int agregarDatosPersona( Persona persona) throws SQLException;
}
