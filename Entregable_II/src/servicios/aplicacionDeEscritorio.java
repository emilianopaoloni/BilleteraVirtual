package servicios;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

import modelo_clases.*;
import clasesDAOjdbc.*;

public class aplicacionDeEscritorio {

	
	
	public class Main {
	    public static void main(String[] args) throws SQLException {
	    	
	    	// Declaración de Scanner como variable global
	         Scanner scanner = new Scanner(System.in);
	    	
	    	
	    	//crear objeto Connection
	    	//Connection connection=null;
	    	
	    	//Creo objeto configuracion
	    	Configuracion configuracion = new Configuracion();
	    	
	    	//creo la conexion con la BD
	    	Connection connection =  configuracion.abrirConexion();
	    	
	    	//creo las tablas
	    	configuracion.crearTablas(connection);

	    	//creo objetos DAOjdbc
	  	   MonedaDAOjdbc mDAOjdbc = new MonedaDAOjdbc();
	  	   ActivoDAOjdbc aDAOjdbc = new ActivoDAOjdbc();
	  	   
	  	 
            
	        int option=1;
	        do {
	        	System.out.println("_______________________________________");
	            System.out.println("Menu:");
	            System.out.println("1. Crear moneda");
	            System.out.println("2. Listar monedas");
	            System.out.println("3. Generar stock");
	            System.out.println("4. Listar stock");
	            System.out.println("5. Generar activos");
	            System.out.println("6. Listar activos");
	            System.out.println("7. Simular compra ");
	            System.out.println("8. Simular swap ");
	            System.out.println("0. Salir");
	            System.out.print("Opcion seleccionada: ");
	            option = scanner.nextInt();
	            
	            switch (option) {
	                case 1: {
	                	 crearMoneda( connection,  mDAOjdbc,  scanner);
	                	     break;
	                }
	                case 2: {
	                	// Llamar método para listar moneda
	                	listarMonedas(connection,  mDAOjdbc, scanner);
	                	
	                
	                    break;
	                }
	                case 3: {
	                    // Llamar a metodo para generar stock
	                	generarStock(connection,  mDAOjdbc);
	                	break;
	                }
	                case 4:{
	                    // Llamar método listar stock
	                	listarStock(connection,  mDAOjdbc, scanner);
	                	break;
	                }
	                case 5:{
	                    // Llamar método generar activos
	                	generarActivos( connection,  aDAOjdbc, scanner);
	                		break;
	                }
	                case 6:{
	                    // Llamar método listar activos
	                   listarActivos(connection, aDAOjdbc, scanner);
	                	break;
	                }
	                case 7:{
	                    // Llamar método simular compra
	                	simularCompra( connection,  aDAOjdbc, scanner);
	                	break;
	                }
	                case 8:{
	                    // Llamar método simular swap
	                	simularSwap( connection,  aDAOjdbc, scanner);
	                	break;
	                }
	                case 0:
	                    System.out.println("Saliendo...");
	                    break;
	                default:
	                    System.out.println("Opción no válida");
	            }
	        } while (option != 0);
	    
	   //cierro la conexion:
	   connection.close();
	    }

	}

	
//--------------------------METODOS
	
	
private static void crearMoneda(Connection connection, MonedaDAOjdbc mDAO, Scanner scanner) throws SQLException {
	Moneda m=null;
	 String confirma="N";
	 String tipo;
	 String nombre;
	 String nomenclatura;
	 double valorEnDolar;
	 int volatilidad;
	 double stock;
	 
	 //lectura de datos
	 System.out.println("Crear moneda");
	 
 while (confirma.equals("N")) {
	  
	 System.out.print("Ingerese tipo de moneda (C: cripto, F: fiducidiaria): ");
    //MODIFICAR --> EL USUARIO SOLO PUEDE PODER INGRESAR DOS VALORES, SINO ERROR --> hacer un while 
	 tipo= scanner.next().toUpperCase();
    System.out.print("Ingerese nombre de moneda:");
	 nombre= scanner.next().toUpperCase();
	 System.out.print("Ingerese nomenclatura de moneda:");
	 nomenclatura= scanner.next().toUpperCase();
	 System.out.print("Ingerese valor en dolar de la moneda:");
	 valorEnDolar= scanner.nextDouble();
	 
	 if (tipo.equals("C")) {
		 //caso moneda cripto, pido los datos volatilidad y stock
		 System.out.print("Ingerese volatilidad de la criptomoneda (valor entre 0 y 100): ");
   	 volatilidad= scanner.nextInt();
   	 System.out.print("Ingerese stock de la criptomoneda: ");
   	 stock= scanner.nextDouble();
   	// creo el tipo moneda con los datos ingresados
   	  m = new Moneda(tipo, nombre, nomenclatura, valorEnDolar, stock, volatilidad);
	 }
	 else if (tipo.equals("F")) {
		 //para caso Fiat, no se pide volatilidad ni stock
		// creo el tipo moneda con los datos ingresados
   	  m = new Moneda(tipo, nombre, nomenclatura, valorEnDolar);
	 }

    
	 // CONFIRMAR DATOS ANTES DE MANDAR A LA BD
	 System.out.println("Confirme los datos ingresados");
	 System.out.println("Tipo moneda: " +  m.getTipo());
	 System.out.println("Nombre moneda: " +  m.getNombre());
	 System.out.println("Nomenclatura moneda: " +  m.getNomenclatura());
	 System.out.println("Valor en dolar: " +  m.getValorEnDolar());
    if (tipo.equals("C")) {
   	//para cripto imprime tambien stock y valorEnDolares:
   	 System.out.println("Stock: " +  m.getStock());
   	 System.out.println("Volatilidad: " +  m.getVolatilidad());
     }
   System.out.print("¿Los datos ingresados son correctos? (S/N): ");
   confirma= scanner.next().toUpperCase();
  }

  // Llamar método de crearMoneda de monedaDAO enviandole el objeto m
  mDAO.crearMoneda(connection, m);

}

private static void listarMonedas(Connection connection, MonedaDAOjdbc mDAO, Scanner scanner) throws SQLException {
	LinkedList<Moneda> monedas = new LinkedList<Moneda>();
	monedas.addAll(mDAO.obtenerDatosMoneda(connection));
	System.out.println("Ingrese como quisiera ver el listado de monedas");
	System.out.println("- Por nomenclatura -> N");
	System.out.println("- Por valor en dolar -> D");
	System.out.print("Su opción: "); // El cursor se queda en esta línea
	char opt = scanner.next().toUpperCase().charAt(0); //Me quedo con el primer caracter
	if(opt=='N') { // Ordeno por NOMENCLATURA
		Collections.sort(monedas, new ComparadorMoneda(ComparadorMoneda.Criterio.NOMENCLATURA));
		
	}
	else { // Ordeno por VALOR EN DOLAR, si no elige opcion o responde incorrectamente, se ordenara por defecto por VALOR EN DOLAR
		Collections.sort(monedas, new ComparadorMoneda(ComparadorMoneda.Criterio.VALOR_EN_DOLAR));
	}
	System.out.println("---Listado de activos:");
	System.out.printf("%-10s | %-15s | %-15s | %-10s | %-10s%n", "TIPO", "NOMBRE", "NOMENCLATURA", "VALOR EN DOLARES", "VOLATILIDAD", "STOCK");

	for (Moneda m : monedas) {
	    System.out.printf(" %-5s | %-15s | %-15s | %-15.2f | %-10d | %-10.2f%n",
	            m.getTipo(),
	            m.getNombre(),
	            m.getNomenclatura(),
	            m.getValorEnDolar(),
	            m.getVolatilidad(),
	            m.getStock()
	    );
	}
}

private static void generarStock(Connection connection, MonedaDAOjdbc mDAO) throws SQLException {
	mDAO.generarStock(connection);
	System.out.println("Stock de criptomonedas generado automaticamente en forma aleatoria");

  }

private static void listarStock(Connection connection, MonedaDAOjdbc mDAO,Scanner scanner) throws SQLException {
	LinkedList<Moneda> monedas = new LinkedList<Moneda>();
	monedas.addAll(mDAO.obtenerDatosMoneda(connection));
	System.out.println("Ingrese como quisiera ver el listado de Criptomonedas");
	System.out.println("- Por nomenclatura -> N");
	System.out.println("- Por cantidad -> C");
	System.out.print("Su opción: "); // El cursor se queda en esta línea
	char opt = scanner.next().toUpperCase().charAt(0); //Me quedo con el primer caracter
	if(opt=='N') { // Ordeno por NOMENCLATURA
		Collections.sort(monedas, new ComparadorMoneda(ComparadorMoneda.Criterio.NOMENCLATURA));
		
	}
	else { // Ordeno por CANTIDAD, si no elige opcion o responde incorrectamente, se ordenara por defecto por CANTIDAD
		Collections.sort(monedas, new ComparadorMoneda(ComparadorMoneda.Criterio.CANTIDAD));
	}
	System.out.println("-----Listado de stock:");
	System.out.printf("%-20s | %-15s | %-10s%n", "NOMBRE", "NOMENCLATURA", "STOCK");

	for (Moneda m : monedas) {
			if (m.getTipo().equals("C")) { //solo se imprimen criptomonedas (ya que el stock es solo para criptos)
				    System.out.printf("%-20s | %-15s | %-10.2f%n",
				            m.getNombre(),
				            m.getNomenclatura(),
				            m.getStock()
				    );
		}
	}
  }

private static void listarActivos(Connection connection, ActivoDAOjdbc aDAO, Scanner scanner) throws SQLException {
	
	LinkedList<Activo> activos = new LinkedList<Activo>();
	activos.addAll(aDAO.obtenerDatosActivos(connection));
	System.out.println("Ingrese como quisiera ver el listado");
	System.out.println("- Por nomenclatura -> N");
	System.out.println("- Por cantidad -> C");
	System.out.print("Su opción: "); // El cursor se queda en esta línea
	char opt = scanner.next().toUpperCase().charAt(0); //Me quedo con el primer caracter si es que el usuario escribe nom,NOM,cant, Canti, etc
	if(opt=='N') { // Ordeno por nomenclatura
		Collections.sort(activos, new ComparadorActivo(ComparadorActivo.Criterio.NOMENCLATURA));
		
	}
	else { // Ordeno por cantidad, si no elige opcion o responde incorrectamente, se ordenara por defecto por cantidad
		Collections.sort(activos, new ComparadorActivo(ComparadorActivo.Criterio.CANTIDAD));
	}
	System.out.println("---Listado de activos:");
	System.out.println("NOMENCLATURA\t|\tCANTIDAD");
	for(Activo a : activos) {
		System.out.println(a.getNomenclatura()+"\t|\t"+a.getCantidad());		
	}
	
  }
private static void generarActivos(Connection connection, ActivoDAOjdbc aDAO, Scanner scanner) throws SQLException {
	String opt;
	// Si las declaraba dentro del do-while me salia error
	String tipoA;
	String nom;

	double cant;
	do {
		    System.out.print("Tipo de activo a generar (FIAT/CRIPTO): ");
		    tipoA = scanner.next().toUpperCase();	
		    while (( ! tipoA.equals("FIAT")) && (! tipoA.equals("CRIPTO")) ) {
		    	System.out.println("ERROR: el tipo solo puede ser FIAT o CRIPTO");
		    	System.out.print("Tipo de activo a generar (FIAT/CRIPTO): ");
			    tipoA = scanner.next().toUpperCase();	
		    }
		    System.out.print("NOMENCLATURA: ");
		    nom = scanner.next().toUpperCase();	              		  
		    System.out.print("CANTIDAD: ");
		    cant= scanner.nextDouble();
		   System.out.println("-------------------------");
		     System.out.println("Activo tipo " + tipoA);
		 System.out.println("Nomenclatura: " + nom);
		 System.out.println("Cantidad: " + cant);
		 System.out.println("-------------------------");
		 System.out.print("CONFIRMACION DE INGRESO (S/N): ");
		 opt=scanner.next().toUpperCase();
        // Llamar método generar activos
	} while(! opt.equals("S"));
	Activo act = new Activo(nom,cant);
	// Si confirma los datos
	if ( aDAO.generarMisActivos(connection,tipoA,act) == 7 ) {
		 System.out.println("ERROR: la moneda "+nom+ " no existe, no se realizo la carga.");
	} 
	else {
		System.out.println("Activo "+nom+ " cargado con exito"); 
	}

  }
private static void simularCompra(Connection connection, ActivoDAOjdbc aDAO, Scanner scanner) throws SQLException {
	String cripto="";
	String fiat="";
	double monto=0;
	String opcion="N";
	while (! opcion.equals("S") ) {
		System.out.print("Ingerese NOMENCLATURA de criptomoneda a comprar: ");
		cripto= scanner.next().toUpperCase();
		System.out.print("Ingerese NOMENCLATURA de fiat con la que va a comprar: ");
	    fiat= scanner.next().toUpperCase();
	    System.out.print("Ingerese cantidad de "+fiat+" con la que va a comprar: ");
	    monto= scanner.nextDouble();
	    System.out.println("---------------------------------------------");
	    System.out.println("Confirmacion de la operacion");
		System.out.println("Usted va a comprar "+cripto+" con "+monto+fiat);
		System.out.println("¿Confirma la operacion? (S/N)");
		opcion= scanner.next().toUpperCase();
		if (opcion.equals("N")) {
			System.out.println("---------------Realice la operacion nuevamente");
		}
	}
    switch ( aDAO.comprarCripto(connection, cripto, fiat, monto) ) {
    case 1: 
    	System.out.println("ERROR: la moneda "+cripto+" ingresada no existe en la BD");
        break;
    case 2: 
    	System.out.println("ERROR: la moneda "+fiat+" ingresada no existe en la BD");
        break;
    case 3: 
    	System.out.println("ERROR: el monto ingresado a comprar es mayor al stock disponible para "+cripto);
        break;
    case 4: 
		System.out.println("ERROR: usted no posee saldo suficiente de la moneda "+fiat+" para realizar la operacion");
    	break;
    	
    default:
    	System.out.println("------------------------------------------------------------ ");
    	System.out.println("operacion realizada con exito ");
    	System.out.println("Usted compro "+ aDAO.criptoAComprar(connection, cripto, fiat, monto) +" "+cripto+" con "+monto+" "+fiat );
    	System.out.println("------------------------------------------------------------ ");
    	break;
    }

  }

private static void simularSwap(Connection connection, ActivoDAOjdbc aDAO, Scanner scanner) throws SQLException {

	String criptoA=" ";
	String criptoB=" ";
	double cant=0;
	String opcion="N";
	while (! opcion.equals("S") ) {
		System.out.print("Ingerese NOMENCLATURA de criptomoneda a convertir: ");
		criptoA= scanner.next().toUpperCase();
		System.out.print("Ingerese CANTIDAD de "+criptoA+" para swapear: ");
	    cant= scanner.nextDouble();
		System.out.print("Ingerese NOMENCLATURA de criptomoneda esperada: ");
		criptoB= scanner.next().toUpperCase();
		System.out.println("---------------------------------------------");
	    System.out.println("Confirmacion de la operacion");
		System.out.println("Usted va a intercambiar "+cant+" "+criptoA+" por "+aDAO.intercambioCriptos (connection,  criptoA,  criptoB,  cant)+" "+criptoB);
		System.out.print("¿Confirma la operacion? (S/N) ");
		opcion= scanner.next().toUpperCase();
		if (opcion.equals("N")) {
			System.out.println("---------------Realice la operacion nuevamente");
	    }
	}	
	switch ( aDAO.transaccionSwap(connection, criptoA, cant, criptoB) ) {
    case 1: 
    	System.out.println("ERROR: la moneda "+criptoA+" ingresada no existe en la BD");
        break;
    case 2: 
    	System.out.println("ERROR: la moneda "+criptoB+" ingresada no existe en la BD");
        break;
    case 3: 
    	System.out.println("ERROR: el monto "+aDAO.intercambioCriptos (connection,  criptoA,  criptoB,  cant)+"  es mayor al stock del activo  "+criptoB);
        break;
        
    default:
    	System.out.println("------------------------------------------------------------ ");
    	System.out.println("OPERACION REALIZADA CON EXITO ");
    	System.out.println("Usted intercambio "+ cant+ " " + criptoA+ " por "+ aDAO.intercambioCriptos (connection,  criptoA,  criptoB,  cant)+" "+criptoB);
    	System.out.println("------------------------------------------------------------ ");
    	break;
    }
  }
}
