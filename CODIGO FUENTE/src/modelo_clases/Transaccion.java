package modelo_clases;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Transaccion {
    private String resumen;
    private String fechaYhora;

    // Constructor para inicializar todos los atributos
    public Transaccion(String resumen, String fechaYhora) {
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
    public String getFechaYhora() {
        return fechaYhora;
    }

    public void setFechaYhora(String fechaYhora) {
        this.fechaYhora = fechaYhora;
    }

    
}


