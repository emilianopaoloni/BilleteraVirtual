package entregable1;

/**
 * La clase Billetera representa el nucleo de la aplicacion de criptomonedas.
 * 
 * Es el componente principal que gestiona todas las operaciones y aspectos
 * relacionados con el usuario, criptomonedas, soporte, 
 * y cumplimiento de normativas legales dentro de la plataforma.
 * 
 * Esta clase integra todas las funcionalidades y permite la interacción entre los diferentes 
 * modulos de la misma.
 * 
 * @author [Emiliano Paoloni y Bernardo Etcheto]
 * @version 1.0
 * @since 2024
 */

public class Billetera {
	
	/**
	 * usuario: Es la persona que hace uso de la aplicacion.
	 */
	  private Usuario usuario;
	  /**
		 * administradorUsuario: Se encarga de realizar acciones relacionadas a la cuenta del usuario y su configuracion.
		 */
	  private AdministradorDeUsuario administradorUsuario;
	  /**
		 * gestorDeCripto: Es quien lleva a cabo las transacciones de criptomoneda.
		 */
	  private GestorDeCriptomoneda gestorDeCripto;
	  /**
		 * stockDeCripto: Almacena el stock de criptomonedas que tiene el mercado actual.
		 */
	  private StockDeCriptomonedas stockDeCripto;
	  /**
		 * deFI: Almacena los protocolos disponibles y realizados por el usuario.
		 */
	  private DeFI deFI;
	  /**
		 * legales: Almacena los terminos y condiciones legales que debe aceptar el usuario para hacer uso de la aplicacion, y el metodo correspondiente.
		 */
	  private Legales legales;
	  /**
		 * soporte: Guarda los contactos de soporte al usuario.
		 */
	  private SoporteAlUsuario soporte;
	  /**
		 * reporteAltasBajas: Guarda informacion de las altas y bajas de los usuarios.
		 */
	  private ReporteDeAltasyBajas reporteAltasBajas;
	  /**
		 * reporteSaldos: Guarda informacion sobre los saldos de las cuentas de los usuarios que tengan activos por encima de cierto umbral.
		 */
	  private ReporteSaldoDeCuentas reporteSaldos;
	  /**
		 * historialTransacciones: Guarda los comprobantes de todas los movimientos de las criptomonedas realizados por el usuario.
		 */
	  private HistorialDeTransacciones historialTransacciones;
	  /**
		 * precios: Tiene los valores de las criptomonedas en tiempo real.
		 */
	  private PreciosEnTiempoReal precios;
	  
	 
	  
	  
	  
	  
	 // no posee getters y setters

}
