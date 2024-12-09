package configuracion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import modelo_clases.Activo;
import modelo_clases.Transaccion;

/*Esta clase contiene los datos basicos del usuario ACTUAL que se encuentra utilizando la aplicacion, como 
 su nombre y apellido, su ID, y la lista de activos adquiridos. El objetivo de esta clase es tener estos datos
 mas "a mano" para poder ser proporcionados a las vistas */
public class DatosUsuario {

	private int idUsuario;
    private String nombreCompleto;
    private LinkedList<Activo> activos; // Lista para almacenar los activos del usuario
    private LinkedList<Transaccion> transacciones; // Lista para almacenar las transacciones realizadas por el usuario
    private double balanceTotal; //suma de todas los activos en ARS

    // Constructor principal
    public DatosUsuario(int idUsuario, String nombreCompleto, LinkedList<Activo> l, LinkedList<Transaccion> t, double balance) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.activos = new LinkedList<>(); // Inicializa la lista de activos
        this.activos.addAll(l);
        this.transacciones = new LinkedList<>(); // Inicializa la lista de activos
        this.transacciones.addAll(t);
        this.balanceTotal = balance;
    }
    
    public DatosUsuario() {
    	
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    

    public double getBalanceTotal() {
		return balanceTotal;
	}

	public void setBalanceTotal(double balanceTotal) {
		this.balanceTotal = balanceTotal;
	}

	public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public List<Activo> getActivos() {
        return activos;
    }
    
    /*actualiza la lista activos: la reemplaza por la lista de parametro */
    public void  setActivos(List<Activo> l) {
    	//elimino todos los elem de la lista
        activos.clear();
        //agrego elementos d ela lista de parametro
        activos.addAll(l);
    }
    
    /*actualiza la lista transacciones: la reemplaza por la lista de parametro */
    public void  setTransacciones(List<Transaccion> t) {
    	//elimino todos los elem de la lista
        transacciones.clear();
        //agrego elementos d ela lista de parametro
        transacciones.addAll(t);
    }
    
    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    // Métodos para manejar activos
    public void agregarActivo(String nombreMoneda, double cantidad) {
        for (Activo activo : activos) {
            if (activo.getNomenclatura().equals(nombreMoneda)) {
                activo.setCantidad(activo.getCantidad() + cantidad); // Incrementa la cantidad si ya existe
                return;
            }
        }
        activos.add(new Activo(nombreMoneda, cantidad)); // Agrega nuevo activo si no existe
    }

    public void actualizarActivo(String nombreMoneda, double nuevaCantidad) {
        for (Activo activo : activos) {
            if (activo.getNomenclatura().equals(nombreMoneda)) {
                activo.setCantidad(nuevaCantidad); // Actualiza la cantidad si ya existe
                return;
            }
        }
        // Si no existe, se puede decidir si lanzar una excepción o agregar como nuevo activo
        throw new IllegalArgumentException("El activo no existe: " + nombreMoneda);
    }

    public void eliminarActivo(String nombreMoneda) {
        activos.removeIf(activo -> activo.getNomenclatura().equals(nombreMoneda)); // Elimina si coincide
    }

    @Override
    public String toString() {
        return "DatosUsuario{" +
                "idUsuario=" + idUsuario +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", activos=" + activos +
                '}';
    }
}
	
	
	
	

