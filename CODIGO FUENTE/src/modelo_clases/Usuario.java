package modelo_clases;

public class Usuario {

    // Atributos
    
	private Persona persona;
    private String email;
    private String password;
    private boolean aceptaTerminos;

    // Constructor
    public Usuario (String email, String password, boolean aceptaTerminos, Persona persona) {
        this.email = email;
        this.password = password;
        this.aceptaTerminos = aceptaTerminos;
        this.setPersona(persona);
    }

    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAceptaTerminos() {
        return aceptaTerminos;
    }

    public void setAceptaTerminos(boolean aceptaTerminos) {
        this.aceptaTerminos = aceptaTerminos;
    }

    public Persona getPersona() {
		return persona;
	}



	public void setPersona(Persona persona) {
		this.persona = persona;
	}
    
    // Método toString para mostrar información del usuario
    @Override
    public String toString() {
        return "Usuario{" +
                ", email='" + email + '\'' +
                ", aceptaTerminos=" + aceptaTerminos +
                '}';
    }



	
}

