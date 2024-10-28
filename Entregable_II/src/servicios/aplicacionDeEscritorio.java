package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import clasesDAO.*;
import modelo_clases.*;


public class aplicacionDeEscritorio {

	
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
			 
			 
			//creo objetos DAO
			 MonedaDAO mDAO = new MonedaDAO();
			 ActivoDAO aDAO = new ActivoDAO();
			 
			//creo las tablas -------------> preguntar esto (le cambie el metodo a publico, en la filmina estaba privado) nose donde iria es metodo, en que clase
			 //mDAO.creacionDeTablasEnBD(connection);
			 
	        Scanner scanner = new Scanner(System.in);
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
	                	 tipo= scanner.next();
	                     System.out.println("Ingerese nombre de moneda");
                    	 nombre= scanner.next();
                    	 System.out.println("Ingerese nomenclatura de moneda");
                    	 nomenclatura= scanner.next();
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
                        confirma= scanner.next();
	                  }
	                
	                  // Llamar método de crearMoneda de monedaDAO enviandole el objeto m
                      mDAO.crearMoneda(connection, m);
	                  break;
	                }
	                case 2: {
	                	// Llamar método para listar moneda
	                	mDAO.listarMonedas(connection);
	                
	                    break;
	                }
	                case 3: {
	                    // Llamar a metodo para generar stock
	                	mDAO.generarStock(connection);
	                	System.out.println("Stock de criptomonedas generado automaticamente en forma aleatoria");
	                    break;
	                }
	                case 4:{
	                    // Llamar método listar stock
	                	mDAO.listarStock(connection);
	                	break;
	                }
	                case 5:{
	                	char opt;
	                	// Si las declaraba dentro del do-while me salia error
	                	String tipoA;
	                	String nom;
	                	double cant;
	                	do {
	                		System.out.print("Tipo de activo a generar (FIAT/CRIPTO): ");
	              		    tipoA = scanner.nextLine().toUpperCase();	              		  
	              		    System.out.print("NOMENCLATURA: ");
	              		    nom = scanner.nextLine().toUpperCase();	              		  
	              		    System.out.print("CANTIDAD: ");
	              		    cant= scanner.nextDouble();
	              		   System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
	              		     System.out.println("Activo tipo " + tipoA);
		        			 System.out.println("Nomenclatura: " + nom);
		        			 System.out.println("Cantidad: " + cant);
		        			 System.out.println("Activo "+tipoA);
		        			 System.out.print("CONFIRMACION DE INGRESO (y/n): ");
		        			 opt=scanner.next().charAt(0);
		                    // Llamar método generar activos
	                	} while(opt!='Y');
	                	
	                	// Si confirma los datos
	                	aDAO.generarMisActivos(connection,tipoA,nom,cant); // No estoy seguro de este llamado teniendo en cuenta lo que hago dentro de la funcion
	                	break;
	                }
	                case 6:{
	                    // Llamar método listar activos
	                   
	                	break;
	                }
	                case 7:{
	                    // Llamar método simular compra
	                   
	                	break;
	                }
	                case 8:{
	                    // Llamar método simular stock
	                   
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
	   scanner.close();	    }

	}

}
