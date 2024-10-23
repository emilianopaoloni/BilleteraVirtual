package entregable1;

/**
 * La clase Criptomoneda representa el objeto "criptomoneda" generico
 * @author [Emiliano Paoloni y Bernardo Etcheto]
 * @version 1.1
 * @since 2024
 */
public class Criptomoneda extends Moneda {
		
	 private String nombre;
	 private String siglaIdentificatoria;
	 private String direccionDeCripto;

	 /**
	  * @return nombre: Es el nombre generico de la criptomoneda.
	  */
	 public String getNombre() {
			return nombre;
		}
	 
     public void setNombre(String nombre) {
			this.nombre = nombre;
		}
     /**
	  * @return siglaIdentificatoria: Son las siglas con la que se identifica a la cripto, por ejemplo Bitcoin (BTC).
	  */
	  public String getSiglaIdentificatoria() {
			return siglaIdentificatoria;
		}
	  
	  public void setSiglaIdentificatoria(String siglaIdentificatoria) {
			this.siglaIdentificatoria = siglaIdentificatoria;
		}
	/**
	 * @return direccionDeCripto: Es la direccion que genera el sistema, la cual queda asociada a la critomoneda moneda para el usuario.
	 */
	  public String getDireccionDeCripto() {
			return direccionDeCripto;
		}
	  
	  public void setDireccionDeCripto(String direccionDeCripto) {
			this.direccionDeCripto = direccionDeCripto;
		}
	  /**
		  * @return saldo: Es el monto actual que posee el usuario de la criptomoneda.
		  */
	  public double getValor() {
			return super.getValor();
		}
	
	 
	 
	 
}
