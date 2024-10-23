package entregable1;

import java.sql.Date;

/**
 * La clase Usuario representa a la persona unica que hace uso de la aplicacion, con sus caracteristicas personales
 * e identificatorias 
 * @author [Emiliano Paoloni y Bernardo Etcheto]
 * @version 1.0
 * @since 2024
 */

public class Usuario {
	
	    private String nombre;
		private String apellido;
	    private String pais;
	    private String email;
	    private String contrasenia;
	    private int dni;
	    private int telefono;
	    private Date fechaNacimiento;
	    private boolean aceptoTyC;
	    
	    
	    /**
		  * @return Nombre: es el nombre completo de la persona usuario.
		  */
	    public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		/**
		  * @return apellido: Es el apellido  de la persona usuario.
		  */
		public String getApellido() {
			return apellido;
		}
		public void setApellido(String apellido) {
			this.apellido = apellido;
		}
		/**
		  * @return pais: Es el pais de nacimiento de la persona usuario.
		  */
		public String getPais() {
			return pais;
		}
		public void setPais(String pais) {
			this.pais = pais;
		}
		/**
		  * @return email: Es la direccion de email la persona usuario.
		  */
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		/**
		  * @return contrasenia: Contraseña seleccionada por el usuario para la app.
		  */
		public String getContrasenia() {
			return contrasenia;
		}
		public void setContrasenia(String contrasenia) {
			this.contrasenia = contrasenia;
		}
		/**
		  * @return dni: Numero de DNI de la persona usuario.
		  */
		public int getDni() {
			return dni;
		}
		public void setDni(int dni) {
			this.dni = dni;
		}
		/**
		  * @return telefono: Numero de telefono movil de la persona usuario.
		  */
		public int getTelefono() {
			return telefono;
		}
		public void setTelefono(int telefono) {
			this.telefono = telefono;
		}
		/**
		  * @return fechaNacimiento: Fecha de nacimiento de la persona usuario.
		  */
		public Date getFechaNacimiento() {
			return fechaNacimiento;
		}
		public void setFechaNacimiento(Date fechaNacimiento) {
			this.fechaNacimiento = fechaNacimiento;
		}
		/**
		  * @return aceptoTyC: Tiene la informacion de si el usuario acepto los terminos y condiciones propuestos para hacer uso de la aplicacion (true/false).
		  */
		public boolean isAceptoTyC() {
			return aceptoTyC;
		}
		public void setAceptoTyC(boolean aceptoTyC) {
			this.aceptoTyC = aceptoTyC;
		}
	    
	    

}
