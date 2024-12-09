package excepciones;



/*esta excepcion es lanzada en cualquier inconveniente con la compra de una cripto: saldo fiat insuficiente o
 * stock de cripto insuficiente */
public class SaldoFiatInsuficienteException extends Exception {

	 public SaldoFiatInsuficienteException(String mensaje) {
	        super(mensaje);
	    }

}
