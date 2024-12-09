package clasesDAOjdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import interfacesDAO.UsuarioDAO;
import modelo_clases.Activo;
import modelo_clases.Persona;
import modelo_clases.Transaccion;
import modelo_clases.Usuario;
import configuracion.DatosUsuario;
import configuracion.MyConnection;


public class UsuarioDAOjdbc implements UsuarioDAO {

	
	public UsuarioDAOjdbc() {
		
	}
	
	public boolean yaExisteUsuario(Usuario u) throws   SQLException {
	    String sql;
	    Connection connection = MyConnection.getCon();

	    // Verificar si el email ya está registrado
	    sql = "SELECT COUNT(*) FROM USUARIO WHERE EMAIL = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, u.getEmail());
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next() && rs.getInt(1) > 0) {
	            // Si ya existe el usuario con ese email
	            return true;
	        }
	        else {
	        return false; }
	    }
	}
	
	
	public void registrarUsuario( Usuario u) throws SQLException {
	 
		 	
		  String sql;
		  
		  //primero ejecuto el metodo que carga a persona:
		  PersonaDAOjdbc p = new PersonaDAOjdbc();
		  int IDpersona= p.agregarDatosPersona(u.getPersona());
		  
		   //obtengo conexion, va aca
		  Connection connection = MyConnection.getCon();	
		  //agrego datos a tabla USUARIO
		   sql = "INSERT INTO USUARIO (ID_PERSONA, EMAIL, PASSWORD, ACEPTA_TERMINOS) VALUES (?, ?, ?, ?)";
		       try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
		           stmt.setInt(1, IDpersona);
		           stmt.setString(2, u.getEmail());
		           stmt.setString(3, u.getPassword());
		           stmt.setBoolean(4, u.isAceptaTerminos());
		           stmt.executeUpdate();
		     
		   
		   stmt.close();
		   
	}

   }
	
	
	 
	
	
	/*este metodo recibe email y password de un usuario que intenta ingresar a la app y accede a la BD para
	  validar que exista el usuario, es decir que se encuentre registrado.
	  -Si el usuario es valido, devuelve un objeto DatosUsuario con sus datos basicos
	  -Si el usuario es invalido, devuelve DatosUsuario=null   */
	public DatosUsuario validarCredenciales(String email, String password) throws SQLException {
	    Connection connection = MyConnection.getCon();
	    DatosUsuario datosUsuario = null; // Inicialmente, el usuario es nulo (credenciales inválidas)

	    String sql = "SELECT u.ID AS usuario_id, " +
	             "p.NOMBRES || ' ' || p.APELLIDOS AS nombre_completo " +
	             "FROM USUARIO u " +
	             "JOIN PERSONA p ON u.ID_PERSONA = p.ID " +
	             "WHERE u.EMAIL = ? AND u.PASSWORD = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
		    stmt.setString(1, email);
		    stmt.setString(2, password);
	
		    try (ResultSet rs = stmt.executeQuery()) {
		    	
		        if (rs.next()) { //si existe usuario con esas credenciales
		        	
		        	//obtengo ID y nombre del usuario
		            int id = rs.getInt("usuario_id");
		            String nombreCompleto = rs.getString("nombre_completo");
	                stmt.close();
		            
		            //obtengo lista de activos del usuario y balance
		            ActivoDAOjdbc a = new ActivoDAOjdbc();
		            LinkedList<Activo> listaActivos = new LinkedList<Activo>();
		            listaActivos.addAll(a.obtenerDatosActivos(id));
		            double balance = a.calcularBalance(id);
		            
		            //obtengo lista de transacciones del usuario
		            TransaccionDAOjdbc t = new TransaccionDAOjdbc();
		            LinkedList<Transaccion> listaTransaccion = new LinkedList<Transaccion>();
		            listaTransaccion.addAll(t.obtenerDatosTransaccion(id));
		            
		            datosUsuario = new DatosUsuario(id, nombreCompleto, listaActivos, listaTransaccion, balance);
		            
		       
		            
		            return datosUsuario;
		        }
		    }
		}

	    return datosUsuario; // Retorna null si las credenciales son inválidas
	}
  }

