package clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import modelo_clases.Activo;
import modelo_clases.Transaccion;

public class ActivoDAO {
	
	
    
    //constructor
	public ActivoDAO() {
		
	}
 
	public static void creacionTablas(Connection connection) throws SQLException {
	    Statement stmt;
	    stmt = connection.createStatement();
	  
		//Creo tabla ActivoCripto
		String sql = "CREATE TABLE ACTIVO_CRIPTO"
		+ "("
		+ " NOMENCLATURA VARCHAR(10)  PRIMARY KEY     NOT NULL, "
		+ " CANTIDAD	REAL    NOT NULL " + ")";
		stmt.executeUpdate(sql);
		
		//Creo tabla ActivoFiat
		sql = "CREATE TABLE ACTIVO_FIAT"
		+ "("
		+ " NOMENCLATURA VARCHAR(10)  PRIMARY KEY     NOT NULL, "
		+ " CANTIDAD	REAL    NOT NULL " + ")";
		stmt.executeUpdate(sql);
		
		//Creo tabla Transaccion
		sql = "CREATE TABLE TRANSACCION"
		+ "("
		+ " RESUMEN VARCHAR(1000)   NOT NULL, "
		+ " FECHA_HORA		DATETIME  NOT NULL " + ")";
		stmt.executeUpdate(sql);
		stmt.close();
		
	}

	
	public void generarMisActivos(Connection connection, String tipoA, String nom, double cant) throws SQLException {
		
		 /* Preguntar si todo esta parte de la lectura por pantalla no deberia ir en el main, 
		  * y que desde alli se llame aun metodo que cheque en la DB de monedas EXISTENTES 
		  * si existe o no esa moneda y que al devolver true la cree llamando a los metodos
		  *  para introducirlos en sus respectivas tablas
		  */
		  
		// Abro la DB para chequear si la moneda existe		  
		  	Statement stmt;
		  	stmt = connection.createStatement();
		  	String sql= "SELECT NOMENCLATURA FROM MONEDA";
		    ResultSet rs= stmt.executeQuery(sql);
		    //Recorro tabla para ver si existe la moneda a generar como activo
		    boolean existe=false;
		    while (rs.next() && !existe) { // corregido para salir del bucle cuando se encuentra la moneda
		        String nomenclatura = rs.getString("NOMENCLATURA");
		        if (nomenclatura != null && nomenclatura.equals(nom)) { // comprobación de null
		            existe = true;
		        }
		    }
	
		    
		  //Si confirmo que existe, cargo el activo en la tabla DB correspondiente  
		 if(existe) {
				 System.out.println("Cargando activo...");
		  		  if(tipoA.equals("CRIPTO")) {				  
					  Activo ac= new Activo();					 
					  ac.setNomenclatura(nom);					
					  ac.setCantidad(cant);
					  generarActivoCripto(connection, ac); // Esta bien hacer esto un metodo privado?
		  		  }
		  		  else if(tipoA.equals("FIAT")) {				  
					  Activo af= new Activo();					  
					  af.setNomenclatura(nom);						
					  af.setCantidad(cant); 
					  generarActivoFiat(connection, af); // Esta bien hacer esto un metodo privado?
		  		  }
		  		System.out.println("Activo cargado.");  
		 }
			 else {
				 System.out.println("Activo "+nom+ " no existe, no se realizo la carga.");
			 }
		 stmt.close();
		 //Tengo que cerrar tambien la connection?
	  }
	
	
	  
	 private void generarActivoFiat(Connection connection, Activo af) throws SQLException {	 
			
		  PreparedStatement p_sent;
		  String sql= "INSERT INTO ACTIVO_FIAT (NOMENCLATURA, CANTIDAD)"
		  + " VALUES(?, ?)";				
		  p_sent = connection.prepareStatement(sql);
		  p_sent.clearParameters(); // Opcional chat
		  p_sent.setString(1, af.getNomenclatura());
		  p_sent.setDouble(2, af.getCantidad());
		  p_sent.executeUpdate();
		 
		 }
	 
	  private void generarActivoCripto(Connection connection,Activo ac) throws SQLException {
		  PreparedStatement p_sent;
		  String sql= "INSERT INTO ACTIVO_CRIPTO (NOMENCLATURA, CANTIDAD)"
		  + " VALUES(?, ?)";				
		  p_sent = connection.prepareStatement(sql);
		  p_sent.clearParameters(); // Opcional chat
		  p_sent.setString(1, ac.getNomenclatura());
		  p_sent.setDouble(2, ac.getCantidad());
		  p_sent.executeUpdate();
	  }


	private boolean existeActivoCripto (Connection connection, String nomenclaturaCripto ) throws SQLException {
		  //verifica si existe el activo cripto pasado como parametro en la BD
	        String sql;
	        ResultSet rs;
	        Statement stmt = connection.createStatement();
		    sql= "SELECT NOMENCLATURA, FROM ACTIVO_CRIPTO";
		    rs= stmt.executeQuery(sql) ;
		    //recorro tabla para ver si existe "cripto" parametro
		    boolean existe=false;
		    while ( (rs.next()) || (existe==false) ) {
		    if ( rs.getString("NOMENCLATURA").equals(nomenclaturaCripto) )
		    	existe=true;
		    }
		    return existe;
	}
	
	private double montoActual (Connection connection, String nomenclatura, String tipoMoneda) throws SQLException {
		//devuelve el monto actual del activo pasado como parametro "nomenclatura" --> puede ser cripto o fiat
		//tipoMoneda es "C" para cripto y "F" para fiat --> para saber a que tabla de activo accedo
		String sql;
        ResultSet rs;
        PreparedStatement p_sent;
        double montoDisponible;
        if ( tipoMoneda.equals("F")) {
            //accedo a tabla ACTIVO_FIAT
        	sql = "SELECT STOCK FROM ACTIVO_FIAT WHERE NOMENCLATURA = ? ";
        	p_sent=  connection.prepareStatement(sql);
            p_sent.clearParameters();
            p_sent.setString(1,nomenclatura);
        	//se ejecuta la consulta:
            rs= p_sent.executeQuery() ;
    	    montoDisponible= rs.getDouble("CANTIDAD");
        }
        else {
        	//accedo a tabla ACTIVO_CRIPTO
        	sql = "SELECT STOCK FROM ACTIVO_CRIPTO WHERE NOMENCLATURA = ? ";
        	p_sent=  connection.prepareStatement(sql);
            p_sent.clearParameters();
            p_sent.setString(1,nomenclatura);
        	//se ejecuta la consulta:
            rs= p_sent.executeQuery() ;
    	    montoDisponible= rs.getDouble("CANTIDAD");
        }
		return montoDisponible;
	}
	
	private double valorEnDolares ( Connection connection, String moneda) throws SQLException {
		// recibe NOMENCLATURA de moneda (no nombre)
		//devuelve el valor en dolares de la moneda pasada como parametro
		String sql;
        ResultSet rs;
        //Statement stmt;
        sql="SELECT VALOR_DOLAR FROM MONEDA WHERE NOMENCLATURA=? ";
        PreparedStatement p_sent=  connection.prepareStatement(sql);
        p_sent.clearParameters();
        p_sent.setString(1,moneda);
    	//se ejecuta la consulta:
        rs= p_sent.executeQuery() ;
        double valorEnDolar= rs.getDouble("VALOR_DOLAR");
        
        // Cerrar ResultSet y PreparedStatement
        rs.close();
        p_sent.close();
        
        //retornar valor
        return valorEnDolar;	
	}
	  
	public void comprarCripto (Connection connection, String cripto, String fiat, double monto ) throws SQLException {
    //cripto es la nomenclatura de la criptomoneda (BTC, etc)
	//fiat es la nomenclatura de la FIAT (ARS, USD)
    Scanner scanner = new Scanner(System.in);
	ResultSet rs;
    String sql1,sql2,sql3;
	Statement stmt;
	PreparedStatement p_sent1, p_sent2, p_sent3;
	stmt = connection.createStatement();
	
	//obtengo de la tabla moneda el valor en dolares de la moneda "fiat" parametro
    double conversionFiat_Dolar= valorEnDolares(connection, fiat);
    		
    //obtengo de la tabla moneda el valor en dolares de la moneda "cripto" parametros
    double conversionCripto_Dolar= valorEnDolares(connection, cripto);
    
    //convierto el monto en FIAT en CRIPTO a comprar:
    double dolaresDisponibles= monto / conversionFiat_Dolar;
    double cripto_A_Comprar= dolaresDisponibles / conversionCripto_Dolar;
   					
    System.out.println("HOLA Q ONDA GENTE");
    
	// verifico stock	
	sql1 = "SELECT STOCK FROM MONEDA WHERE NOMENCLATURA = ? ";
	p_sent1 = connection.prepareStatement(sql1);
	p_sent1.clearParameters();
	p_sent1.setString(1,cripto);
	rs= stmt.executeQuery(sql1) ;
	double stockDisponible= rs.getDouble("STOCK");
	if ( monto > stockDisponible) {
		System.out.println("ERROR: el monto ingresado a comprar es mayor al stock disponible para "+cripto);
	    return;
	}
	
	// verifico si la cantidad de FIAT ingresado es suficiente
	if ( conversionFiat_Dolar < conversionCripto_Dolar) {
		System.out.println("ERROR: usted no posee saldo suficiente de la moneda "+fiat+" para realizar la operacion"); 
	    return;
	}
	 System.out.println("HOLA 2");
	 
	//confirmar la operacion
	System.out.println("Confirmacion de la operacion");
	System.out.println("Usted va a comprar "+ cripto_A_Comprar+" "+cripto+" con "+monto);
	System.out.println("¿Confirma la operacion? (S/N)");
	String opcion= scanner.next().toUpperCase();
	if (opcion.equals("N")) {
		System.out.println("Operacion sin confirmar: Realice la operacion nuevamente en el menu de opciones");
	    return;
	}
			
    // Si la criptomoneda a comprar no es aún un activo, se crea el nuevo activo		
    boolean existe= existeActivoCripto(connection, cripto);
    if ( existe == false ) {
    //la cripto a comprar no existe en la tabla ACTIVO_CRIPTO
    //la creo:
    Activo a = new Activo(cripto, monto);
    generarActivoCripto(connection, a);
    }
    
   //se genera la "transaccion"
    String resumen= "Se compro "+ cripto_A_Comprar+" "+ cripto + " con " + monto ;
    LocalDateTime fechaYhoraActual = LocalDateTime.now();
    Transaccion T = new Transaccion(resumen, fechaYhoraActual);
   //FALTA AGREGAR LA INFO DE TRANSACCION A LA BD
    
    
   //Se decrementa la cantidad en FIAT en la BD
    sql2 = "UPDATE ACTIVO_FIAT SET CANTIDAD = ? - ? WHERE NOMENCLATURA = ?";
    p_sent2=  connection.prepareStatement(sql2);
    p_sent2.setDouble(1, montoActual(connection, fiat, "F")); // El monto disponible actual
    p_sent2.setDouble(2, monto);           // El monto a restar
    p_sent2.setString(3, cripto);           // La nomenclatura de la moneda
    // Ejecutar la actualización
    int rowsAffected = p_sent2.executeUpdate();
    System.out.println("Filas actualizadas: " + rowsAffected);
    
    //Se incrementa la cantidad de cripto en la BD
    sql3 = "UPDATE ACTIVO_CRIPTO SET CANTIDAD = ? + ? WHERE NOMENCLATURA = ?";
    p_sent3 = connection.prepareStatement(sql3);
    p_sent3.setDouble(1, montoActual(connection, cripto, "C")); // El monto disponible actual
    p_sent3.setDouble(2, cripto_A_Comprar);           // El monto a sumar
    p_sent3.setString(3, cripto);           // La nomenclatura de la moneda
    // Ejecutar la actualización
    rowsAffected = p_sent3.executeUpdate();
    System.out.println("Filas actualizadas: " + rowsAffected);
    
    
} 

	
	
	
//preguntar si este metodo esta bien en esta clase, ya que accede a la tabla monedas
	//para verificar los tipos, y a la tabla de activos!!!
public void swap (Connection connection, String criptoAconvertir, double monto, String criptoEsperada ) throws SQLException {
	Scanner scanner = new Scanner(System.in);
	PreparedStatement p_sent ;
	ResultSet rs;
	Statement stmt;
	String sql;
	stmt = connection.createStatement();
			
	//verifico que ambas criptos pertenecen a la BD:
	boolean existeCriptoAConvertir = existeActivoCripto(connection, criptoAconvertir);
	boolean existeCriptoEsperada = existeActivoCripto(connection, criptoEsperada);
	if ( ! ( (existeCriptoAConvertir == true) && (existeCriptoEsperada == true) ) ) {
	   System.out.print("ERROR: una de las cripto ingresada no existe en su tabla de activos");
	   return;
	}
	
	//realizo la conversion
	double valorEnDolares_criptoA= valorEnDolares(connection, criptoAconvertir);
	double valorEnDolares_criptoE= valorEnDolares(connection, criptoEsperada);
	
	//calculo cuanto en dolares se tiene de la criptoAConvertir
	double montoDolaresDisponible_criptoA=  valorEnDolares_criptoA * monto;
	
	//calculo cuanto se puede comprar de criptoEsperada con el monto en dolares disponible
	double montoACambiar= montoDolaresDisponible_criptoA / valorEnDolares_criptoE;
	
	
    //verifico stock de criptoEsperada
	sql = "SELECT STOCK FROM MONEDA WHERE NOMENCLATURA = ? ";
	p_sent = connection.prepareStatement(sql);
	p_sent.setString(1, criptoEsperada);
	rs= p_sent.executeQuery(sql) ;
	double stockDisponible= rs.getDouble("STOCK");
	if ( montoACambiar > stockDisponible) {
		System.out.println("ERROR: el monto "+montoACambiar+"  es mayor al stock disponible para "+criptoEsperada);
	    return;
	}
    	
	//confirmar la operacion
		System.out.println("Confirmacion de la operacion");
		System.out.println("Usted va a intercambiar "+ montoACambiar+" "+criptoEsperada+" con "+monto+" "+criptoAconvertir);
		System.out.println("¿Confirma la operacion? (S/N)");
		String opcion= scanner.next().toUpperCase();
		if (opcion.equals("N")) {
			System.out.println("Operacion sin confirmar: Realice la operacion nuevamente en el menu de opciones");
		    return;
		}
		
		//se genera la "transaccion"
	    String resumen= "Se intercambio "+ montoACambiar+" "+ criptoEsperada + " con " + monto +" "+criptoAconvertir ;
	    LocalDateTime fechaYhoraActual = LocalDateTime.now();
	    Transaccion T = new Transaccion(resumen, fechaYhoraActual);
	    //FALTA AGREGAR LA INFO DE TRANSACCION A LA BD
	    
	    
	   //Se decrementa la cantidad "monto" en "criptoAconvertir" en la BD
	    sql = "UPDATE ACTIVO_CRIPTO SET CANTIDAD = ? - ? WHERE NOMENCLATURA = ?";
	    p_sent.setDouble(1, montoActual(connection, criptoAconvertir ,"C")); // El monto disponible actual
	    p_sent.setDouble(2, monto);           // El monto a restar
	    p_sent.setString(3, criptoAconvertir);           // La nomenclatura de la moneda
	    // Ejecutar la actualización
	    int rowsAffected = p_sent.executeUpdate();
	    System.out.println("Filas actualizadas: " + rowsAffected);
	    
	    //Se incrementa la cantidad de "criptoEsperada" en la BD
	    sql = "UPDATE ACTIVO_CRIPTO SET CANTIDAD = ? + ? WHERE NOMENCLATURA = ?";
	    p_sent.setDouble(1, montoActual(connection, criptoEsperada ,"C")); // El monto disponible actual
	    p_sent.setDouble(2, montoACambiar);           // El monto a sumar
	    p_sent.setString(3, criptoEsperada);           // La nomenclatura de la moneda
	    // Ejecutar la actualización
	    rowsAffected = p_sent.executeUpdate();
	    System.out.println("Filas actualizadas: " + rowsAffected);
	
		
	    stmt.close();
  }

}


