package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Scanner;

import clasesDAO.*;
import modelo_clases.Moneda;


public class aplicacionDeEscritorio {

	// Declaración de Scanner como variable global
     private static Scanner scanner = new Scanner(System.in);
	
	public class Main {
	    public static void main(String[] args) throws SQLException {
	    	
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
	  	   MonedaDAO mDAO = new MonedaDAO();
	  	   ActivoDAO aDAO = new ActivoDAO();
	  	   
	  	 
	       // Scanner scanner = new Scanner(System.in);
            crearTablas();
            
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
	            option = scanner.nextInt();
	            
	            switch (option) {
	                case 1: {
	                	 crearMoneda( connection,  mDAO);
	                	     break;
	                }
	                case 2: {
	                	// Llamar método para listar moneda
	                	listarMonedas(connection,  mDAO);
	                	
	                
	                    break;
	                }
	                case 3: {
	                    // Llamar a metodo para generar stock
	                	generarStock(connection,  mDAO);
	                	break;
	                }
	                case 4:{
	                    // Llamar método listar stock
	                	listarStock(connection,  mDAO);
	                	break;
	                }
	                case 5:{
	                    // Llamar método generar activos
	                	generarActivos( connection,  aDAO);
	                		break;
	                }
	                case 6:{
	                    // Llamar método listar activos
	                   
	                	break;
	                }
	                case 7:{
	                    // Llamar método simular compra
	                	simularCompra( connection,  aDAO);
	                	break;
	                }
	                case 8:{
	                    // Llamar método simular swap
	                	simularSwap( connection,  aDAO);
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
	
private static void crearTablas(Connection connection, ActivoDAO aDAO, MonedaDAO mDAO) {
	
	 
	  //creo las tablas 
	  //mDAO.crearTablaMoneda(connection);
	   //aDAO.crearTablas(connection);
	 
	      }
	
private static void crearMoneda(Connection connection, MonedaDAO mDAO) {
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
	  
	 System.out.println("Ingerese tipo de moneda (C: cripto, F: fiducidiaria)");
    //MODIFICAR --> EL USUARIO SOLO PUEDE PODER INGRESAR DOS VALORES, SINO ERROR --> hacer un while 
	 tipo= scanner.next().toUpperCase();
    System.out.println("Ingerese nombre de moneda");
	 nombre= scanner.next().toUpperCase();
	 System.out.println("Ingerese nomenclatura de moneda");
	 nomenclatura= scanner.next().toUpperCase();
	 System.out.println("Ingerese valor en dolar de la moneda");
	 valorEnDolar= scanner.nextDouble();
	 
	 if (tipo.equals("C")) {
		 //caso moneda cripto, pido los datos volatilidad y stock
		 System.out.println("Ingerese volatilidad de la criptomoneda (valor entre 0 y 100)");
   	 volatilidad= scanner.nextInt();
   	 System.out.println("Ingerese stock de la criptomoneda");
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
   System.out.println("¿Los datos ingresados son correctos? (S/N)");
   confirma= scanner.next().toUpperCase();
  }

  // Llamar método de crearMoneda de monedaDAO enviandole el objeto m
  mDAO.crearMoneda(connection, m);

}

private static void listarMonedas(Connection connection, MonedaDAO mDAO) {
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

private static void generarStock(Connection connection, MonedaDAO mDAO) {
	mDAO.generarStock(connection);
	System.out.println("Stock de criptomonedas generado automaticamente en forma aleatoria");

  }

private static void listarStock(Connection connection, MonedaDAO mDAO) {
	LinkedList<Moneda> monedas = new LinkedList<Moneda>();
	monedas.addAll(mDAO.obtenerDatosMoneda(connection));
	System.out.println("---Stock disponible de criptomonedas:");
	for (Moneda m: monedas) {
		System.out.println("Tipo: "+ m.getTipo()+ 
  			  " | Nombre: "+ m.getNombre()+ 
  			  " | Stock: "+ m.getStock());
	}
  }
private static void listarActivos(Connection connection, ActivoDAO aDAO) {
	//s
  }
private static void generarActivos(Connection connection, ActivoDAO aDAO) {
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
		   System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
		     System.out.println("Activo tipo " + tipoA);
		 System.out.println("Nomenclatura: " + nom);
		 System.out.println("Cantidad: " + cant);
		 System.out.print("CONFIRMACION DE INGRESO (y/n): ");
		 opt=scanner.next().toUpperCase();
        // Llamar método generar activos
	} while(! opt.equals("Y"));
	
	// Si confirma los datos
	aDAO.generarMisActivos(connection,tipoA,nom,cant); // No estoy seguro de este llamado teniendo en cuenta lo que hago dentro de la funcion

  }
private static void simularCompra(Connection connection, ActivoDAO aDAO) {
	String cripto;
	String fiat;
	double monto;
	System.out.println("Ingerese NOMENCLATURA de criptomoneda a comprar");
	cripto= scanner.next().toUpperCase();
	System.out.println("Ingerese NOMENCLATURA de fiat con la que va a comprar");
    fiat= scanner.next().toUpperCase();
    System.out.println("Ingerese cantidad de "+fiat+" con la que va a comprar");
    monto= scanner.nextDouble();
    
    aDAO.comprarCripto(connection, cripto, fiat, monto);

  }

private static void simularSwap(Connection connection, ActivoDAO aDAO) {

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
