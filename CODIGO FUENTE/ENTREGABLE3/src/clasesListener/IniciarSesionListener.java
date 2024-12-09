package clasesListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import clasesDAOjdbc.UsuarioDAOjdbc;
import configuracion.DatosUsuario;
import modelo_clases.Activo;
import vista.VistaDeInicio;
import vista.VistaInicioSesion;

public class IniciarSesionListener implements ActionListener {
    private VistaInicioSesion vistaInicioSesion;

    public IniciarSesionListener(VistaInicioSesion vistaInicioSesion) {
        this.vistaInicioSesion = vistaInicioSesion;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Recuperar datos de la vista
            String email = vistaInicioSesion.getEmail();
            String password = vistaInicioSesion.getPassword();
           

            // Validar datos
            if (email.isEmpty() || password.isEmpty()  ) {
                JOptionPane.showMessageDialog(vistaInicioSesion, "Por favor, complete todos los campos.");
                return;
            }

            // Validar credenciales
            UsuarioDAOjdbc u = new UsuarioDAOjdbc();
            DatosUsuario datosUsuario =  u.validarCredenciales(email, password);
            
            if (datosUsuario != null) { //existe el usuario
            	
                //System.out.println("Sesión iniciada correctamente.");
                // Cambiar a la vista principal de la aplicación
                this.vistaInicioSesion.setVisible(false);
                VistaDeInicio vistaInicio = new VistaDeInicio(datosUsuario);
            } else {
                JOptionPane.showMessageDialog(vistaInicioSesion, "Email o contraseña incorrectos. Intente nuevamente.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vistaInicioSesion, "Error al iniciar sesión: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
