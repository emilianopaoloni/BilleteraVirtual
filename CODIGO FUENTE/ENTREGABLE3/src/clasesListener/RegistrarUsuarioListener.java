package clasesListener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import clasesDAOjdbc.UsuarioDAOjdbc;
import modelo_clases.Persona;
import modelo_clases.Usuario;
import vista.VistaInicioSesion;
import vista.VistaRegistroUsuario;

public class RegistrarUsuarioListener implements ActionListener {
    private VistaRegistroUsuario vistaRegistroUsuario;
    //private VistaInicioSesion vistaInicioSesion;
    private UsuarioDAOjdbc usuarioDAOjdbc;

    public RegistrarUsuarioListener(VistaRegistroUsuario vistaRegistroUsuario) {
        this.vistaRegistroUsuario = vistaRegistroUsuario;
        this.usuarioDAOjdbc =  new UsuarioDAOjdbc(); //esta bien esto?
    }

    @Override
    public void actionPerformed  (ActionEvent e) {
        try {
            // Recuperar datos cargados en la vista
            String nombre = vistaRegistroUsuario.getNombre();
            String apellido = vistaRegistroUsuario.getApellido();
            String email = vistaRegistroUsuario.getEmail();
            String password = vistaRegistroUsuario.getPassword();
            boolean terminosAceptados = vistaRegistroUsuario.isTerminosAceptados();

            // Validar datos
            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || !terminosAceptados ) {
                JOptionPane.showMessageDialog(vistaRegistroUsuario, "Por favor, complete todos los campos.");
                return;
            }

            // Crear objetos Persona y Usuario
            Persona persona = new Persona(nombre, apellido);
            Usuario usuario = new Usuario(email, password, terminosAceptados, persona);

            
            // Registrar usuario en la base de datos
            if (!usuarioDAOjdbc.yaExisteUsuario(usuario)) { //si no existe el usuario a registrar (verifica el mail)
	            usuarioDAOjdbc.registrarUsuario(usuario);
	
	            JOptionPane.showMessageDialog(vistaRegistroUsuario, "Usuario registrado con éxito.");
	            vistaRegistroUsuario.setVisible(false);
	            //hago visible la vista de LOGIN
	            VistaInicioSesion vistaInicioSesion = new VistaInicioSesion();
            }
            else 
            	JOptionPane.showMessageDialog(vistaRegistroUsuario, "Ya existe un usuario con email "+"'"+email+"'");
            	

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vistaRegistroUsuario, "Error al registrar usuario: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
