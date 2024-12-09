package interfacesDAO;

import java.sql.SQLException;

import modelo_clases.Usuario;

public interface TransaccionDAO {
		
		public void obtenerDatosTransaccion( int idUsuario) throws SQLException;	

	}
