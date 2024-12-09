package excepciones;

/*esta excepcion es lanzada cuando un usuario quiere registrarse con un mail ya existente */
public class StockInsuficienteException extends Exception {
	
	
    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}

