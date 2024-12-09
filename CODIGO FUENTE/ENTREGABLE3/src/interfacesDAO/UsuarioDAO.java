package interfacesDAO;

import java.sql.Connection;
import java.sql.SQLException;

import configuracion.DatosUsuario;
import modelo_clases.Usuario;

public interface UsuarioDAO {
	
	public void registrarUsuario( Usuario u) throws SQLException;	
	public boolean yaExisteUsuario(Usuario u) throws   SQLException;
	public DatosUsuario validarCredenciales(String email, String password) throws SQLException;
}
