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
	    	Connection connection=null;
	    	 try { 
	    	 //establecer conexion con la BD
	    	 connection=DriverManager.getConnection("jdbc:sqlite:test.db");
	    	 } catch (SQLException e) {
	    		 System.out.println("No se pudo conectar a la BD: " + e.getMessage());
	    		 e.printStackTrace();  // Esto mostrará más detalles en la consola.
	    	 }

	    	//creo objetos DAO --> lo hago aca afuera para q queden como variables globales
	  	   MonedaDAOjdbc mDAOjdbc = new MonedaDAOjdbc();
	  	   ActivoDAOjdbc aDAOjdbc = new ActivoDAOjdbc();
	  	   
	  	 
	       // Scanner scanner = new Scanner(System.in);
            crearTablas (connection,  aDAOjdbc, mDAOjdbc);
            
	        int option=1;
	        do {
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
	                	listarMonedas(connection,  mDAOjdbc);
	                	
	                
	                    break;
	                }
	                case 3: {
	                    // Llamar a metodo para generar stock
	                	generarStock(connection,  mDAOjdbc);
	                	break;
	                }
	                case 4:{
	                    // Llamar método listar stock
	                	listarStock(connection,  mDAOjdbc);
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
	
private static void crearTablas(Connection connection, ActivoDAOjdbc aDAO, MonedaDAOjdbc mDAO) {
	
	 
	  //creo las tablas 
	  //mDAO.crearTablaMoneda(connection);
	   //aDAO.crearTablas(connection);
	System.out.print("Tablas 'MONEDA', 'ACTIVO_FIAT', 'ACTIVO_CRIPTO' Y 'TRANSACCION' creadas correctamente");
	 
	      }
	
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

private static void listarMonedas(Connection connection, MonedaDAOjdbc mDAO) throws SQLException {
	LinkedList<Moneda> monedas = new LinkedList<Moneda>();
	monedas.addAll(mDAO.obtenerDatosMoneda(connection));
	for (Moneda m: monedas) {
		System.out.println("Tipo: "+ m.getTipo()+ 
  			  " | Nombre: "+ m.getNombre()+ 
  			  " | Nomenclatura: "+ m.getNomenclatura()+
  			  " | Valor en dolar: "+ m.getValorEnDolar()+ 
  			  " | Volatilidad: "+ m.getVolatilidad()+ 
  			  " | Stock: "+ m.getStock());
	}
  }

private static void generarStock(Connection connection, MonedaDAOjdbc mDAO) throws SQLException {
	mDAO.generarStock(connection);
	System.out.println("Stock de criptomonedas generado automaticamente en forma aleatoria");

  }

private static void listarStock(Connection connection, MonedaDAOjdbc mDAO) throws SQLException {
	LinkedList<Moneda> monedas = new LinkedList<Moneda>();
	monedas.addAll(mDAO.obtenerDatosMoneda(connection));
	System.out.println("---Stock disponible de criptomonedas:");
	for (Moneda m: monedas) {
		//solo imprime stock de criptomonedas (son las unicas monedas que tienen STOCK)
		if (m.getTipo().equals("C")) {
		System.out.println("Tipo: "+ m.getTipo()+ 
  			  " | Nombre: "+ m.getNombre()+ 
  			  " | Stock: "+ m.getStock());
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
		Collections.sort(activos, new ComparadorNomenclatura());
		
	}
	else { // Ordeno por cantidad, si no elige opcion o responde incorrectamente, se ordenara por defecto por cantidad
		Collections.sort(activos, new ComparadorCantidad());
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
		    System.out.print("NOMENCLATURA: ");
		    nom = scanner.next().toUpperCase();	              		  
		    System.out.print("CANTIDAD: ");
		    cant= scanner.nextDouble();
		   System.out.println("---------------");
		     System.out.println("Activo tipo " + tipoA);
		 System.out.println("Nomenclatura: " + nom);
		 System.out.println("Cantidad: " + cant);
		 System.out.print("CONFIRMACION DE INGRESO (y/n): ");
		 opt=scanner.next().toUpperCase();
        // Llamar método generar activos
	} while(! opt.equals("Y"));
	Activo act = new Activo(nom,cant);
	// Si confirma los datos
	aDAO.generarMisActivos(connection,tipoA,act); // No estoy seguro de este llamado teniendo en cuenta lo que hago dentro de la funcion

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

	String criptoA;
	String criptoB;
	double cant;
	System.out.println("Ingerese NOMENCLATURA de criptomoneda a convertir");
	criptoA= scanner.next().toUpperCase();
	System.out.println("Ingerese cantidad de "+criptoA+" para swapear");
    cant= scanner.nextDouble();
	System.out.println("Ingerese NOMENCLATURA de criptomoneda esperada");
	criptoB= scanner.next().toUpperCase();
    
    aDAO.swap(connection, criptoA, cant, criptoB);

  }
}
