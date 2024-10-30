package modelo_clases;
import java.time.LocalDateTime;

public class Transaccion {
	
	    private String resumen;
	    private LocalDateTime fechaYhora;

	    // Constructor para inicializar todos los atributos
	    public Transaccion(String resumen, LocalDateTime fechaYhora) {
	        this.resumen = resumen;
	        this.fechaYhora = fechaYhora;
	    }

	    // Getter y Setter para resumen
	    public String getResumen() {
	        return resumen;
	    }

	    public void setResumen(String resumen) {
	        this.resumen = resumen;
	    }

	    // Getter y Setter para fechaYhora
	    public LocalDateTime getFechaYhora() {
	        return fechaYhora;
	    }

	    public void setFechaYhora(LocalDateTime fechaYhora) {
	        this.fechaYhora = fechaYhora;
	    }

	    // Método para obtener la fecha y hora en un formato legible
	    public String obtenerFechaFormateada() {
	        return fechaYhora.toString();  // Devuelve en formato ISO
	    }

	}


