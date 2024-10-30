package clasesDAOjdbc;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.Scanner;

import interfacesDAO.ActivoDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import modelo_clases.Activo;
import modelo_clases.Transaccion;


public class ActivoDAOjdbc implements ActivoDAO {
	
	
    
    //constructor
	public ActivoDAOjdbc() {
		
	}
 
	public void crearTablas(Connection connection) throws SQLException {
	    Statement stmt;
	    stmt = connection.createStatement();
	  
		//Creo tabla ActivoCripto
		String sql = "CREATE TABLE ACTIVO_CRIPTO IF NOT EXIST"
		+ "("
		+ " NOMENCLATURA VARCHAR(10)  PRIMARY KEY     NOT NULL, "
		+ " CANTIDAD	REAL    NOT NULL " + ")";
		stmt.executeUpdate(sql);
		
		//Creo tabla ActivoFiat
		sql = "CREATE TABLE ACTIVO_FIAT "
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

	
	public int generarMisActivos(Connection connection, String tipoA, Activo act) throws SQLException {
		
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
		        if (nomenclatura != null && nomenclatura.equals(act.getNomenclatura())) { // comprobación de null
		            existe = true;
		        }
		    }
	
		  stmt.close();
		  //Si confirmo que existe, cargo el activo en la tabla DB correspondiente  
		 if(existe) {
				 System.out.println("Cargando activo...");
		  		  if(tipoA.equals("CRIPTO")) {				  
					  generarActivoCripto(connection, act); // Esta bien hacer esto un metodo privado?
		  		  }
		  		  else if(tipoA.equals("FIAT")) {				  
					  generarActivoFiat(connection, act); // Esta bien hacer esto un metodo privado?
		  		  }
		  		
		  		return 0;
		 }
			 else {
			     return 7;		 
			 }
		 
		 //Tengo que cerrar tambien la connection?
	  }
	
	
	  
	 private void generarActivoFiat(Connection connection, Activo af) throws SQLException {	 
			
		  PreparedStatement p_sent;
          //verifico si ya existe el activo
		  if( ! existeActivoFiat(connection, af.getNomenclatura() ) ) {
				  String sql= "INSERT INTO ACTIVO_FIAT (NOMENCLATURA, CANTIDAD)"
				  + " VALUES(?, ?)";				
				  p_sent = connection.prepareStatement(sql);
				  p_sent.clearParameters(); // Opcional chat
				  p_sent.setString(1, af.getNomenclatura());
				  p_sent.setDouble(2, af.getCantidad());
				  p_sent.executeUpdate();
		  }
		  else {
			  String sql= "UPDATE ACTIVO_FIAT SET CANTIDAD = CANTIDAD + ? WHERE NOMENCLATURA = ?";
			  p_sent = connection.prepareStatement(sql);
		      p_sent.setDouble(1, af.getCantidad());           // El monto a sumar
		      p_sent.setString(2, af.getNomenclatura());      
		      p_sent.executeUpdate();
		  }
		  p_sent.close();
		 }
	 
	  private void generarActivoCripto(Connection connection,Activo ac) throws SQLException {
		  PreparedStatement p_sent;
		  //verifico si ya existe el activo
		  if( ! existeActivoCripto(connection, ac.getNomenclatura() ) ) {
				  String sql= "INSERT INTO ACTIVO_CRIPTO (NOMENCLATURA, CANTIDAD)"
				  + " VALUES(?, ?)";				
				  p_sent = connection.prepareStatement(sql);
				  p_sent.clearParameters(); // Opcional chat
				  p_sent.setString(1, ac.getNomenclatura());
				  p_sent.setDouble(2, ac.getCantidad());
				  p_sent.executeUpdate();
		  }
		  else {
			  String sql= "UPDATE ACTIVO_CRIPTO SET CANTIDAD = CANTIDAD + ? WHERE NOMENCLATURA = ?";
			  p_sent = connection.prepareStatement(sql);
		      p_sent.setDouble(1, ac.getCantidad());           // El monto a sumar
		      p_sent.setString(2, ac.getNomenclatura());      
		      p_sent.executeUpdate();
		  }
		  
		  p_sent.close();
	  }
	 

	public LinkedList<Activo> obtenerDatosActivos(Connection connection) throws SQLException {
		  
		//Creo la lista a retornar
		  LinkedList<Activo> listaActivos = new LinkedList<Activo>();
		  
		  //Hago el proceso para agregar los ACTIVOS FIAT a la lista
		  Statement stmt;
		  stmt = connection.createStatement();		  
		  String sql= "SELECT * FROM ACTIVO_FIAT";
		  ResultSet rs= stmt.executeQuery(sql);		  
		  
		  while (rs.next()) {
	          // cargo datos de los ACTIVOS FIAT
			  Activo af = new Activo(rs.getString("NOMENCLATURA"), rs.getDouble("CANTIDAD"));
	    	  //agrego moneda a la lista:
			  listaActivos.add(af);
	      }
		  //Hago el proceso para agregar los ACTIVOS CRIPTO a la lista
		  Statement stmt1;
		  stmt1 = connection.createStatement();		  
		  String sql1= "SELECT * FROM ACTIVO_CRIPTO";
		  ResultSet rs1= stmt1.executeQuery(sql1);		  
		  
		  while (rs1.next()) {
	          // cargo datos de los ACTIVOS FIAT
			  Activo ac = new Activo(rs1.getString("NOMENCLATURA"), rs1.getDouble("CANTIDAD"));
	    	  //agrego moneda a la lista:
			  listaActivos.add(ac);
	      }
		  
		  stmt.close();
		  stmt1.close();
		  
		  return listaActivos;
	  }
	
	private boolean existeActivoCripto (Connection connection, String nomenclaturaCripto ) throws SQLException {
		  //verifica si existe el activo cripto pasado como parametro en la BD
	        String sql;
	        ResultSet rs;
	        Statement stmt = connection.createStatement();
		    sql= "SELECT NOMENCLATURA FROM ACTIVO_CRIPTO";
		    rs= stmt.executeQuery(sql) ;
		    //recorro tabla para ver si existe "cripto" parametro
		    boolean existe=false;
		    String n;
		    while ( rs.next() ) {
		      n = rs.getString("NOMENCLATURA");
		      if ( (n != null) && n.equals(nomenclaturaCripto) ) {
		    	existe=true;
		        break; //sale del bucle si se encuentra
		      }
		    }
		    
		    stmt.close();
		    rs.close();
		    return existe;
	}
	
	private boolean existeActivoFiat (Connection connection, String nomenclaturaFiat ) throws SQLException {
		  //verifica si existe el activo fiat pasado como parametro en la BD
	        String sql;
	        ResultSet rs;
	        Statement stmt = connection.createStatement();
		    sql= "SELECT NOMENCLATURA FROM ACTIVO_FIAT";
		    rs= stmt.executeQuery(sql) ;
		    //recorro tabla para ver si existe "cripto" parametro
		    boolean existe=false;
		    String n;
		    while ( rs.next() ) {
		      n = rs.getString("NOMENCLATURA");
		      if ( (n != null) && n.equals(nomenclaturaFiat) ) {
		    	existe=true;
		        break; //sale del bucle si se encuentra
		      }
		    }
		    
		    stmt.close();
		    rs.close();
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
        	sql = "SELECT CANTIDAD FROM ACTIVO_FIAT WHERE NOMENCLATURA = ? ";
        	p_sent=  connection.prepareStatement(sql);
            p_sent.clearParameters();
            p_sent.setString(1,nomenclatura);
        	//se ejecuta la consulta:
            rs= p_sent.executeQuery() ;
    	    montoDisponible= rs.getDouble("CANTIDAD");
        }
        else {
        	//accedo a tabla ACTIVO_CRIPTO
        	sql = "SELECT CANTIDAD FROM ACTIVO_CRIPTO WHERE NOMENCLATURA = ? ";
        	p_sent=  connection.prepareStatement(sql);
            p_sent.clearParameters();
            p_sent.setString(1,nomenclatura);
        	//se ejecuta la consulta:
            rs= p_sent.executeQuery() ;
    	    montoDisponible= rs.getDouble("CANTIDAD");
        }
		
		
		p_sent.close();
		
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
	  
	
    //metodo que verifica si una moneda existe en la BD, la busca por nomenclatura:
    private boolean existeMoneda (Connection connection, String nomenclatura) throws SQLException {
    	Statement stmt;
	  	stmt = connection.createStatement();
	  	String sql= "SELECT NOMENCLATURA FROM MONEDA";
	    ResultSet rs= stmt.executeQuery(sql);
	    //Recorro tabla para ver si existe la moneda a generar como activo
	    boolean existe=false;
	    String n;
	    while (rs.next() && !existe) { // corregido para salir del bucle cuando se encuentra la moneda
	         n = rs.getString("NOMENCLATURA");
	        if (n != null && n.equals(nomenclatura)) { // comprobación de null
	            existe = true;
	        }
	    }
	    return existe;
    }
    
    public double criptoAComprar (Connection connection, String cripto, String fiat, double monto) throws SQLException {
    	//este metodo recibe un monto de fiat, y calcula cuanta cripto "cripto" se puede comprar con ese monto
    
    	//obtengo de la tabla moneda el valor en dolares de la moneda "fiat" parametro
        double conversionFiat_Dolar= valorEnDolares(connection, fiat);
     
        //obtengo de la tabla moneda el valor en dolares de la moneda "cripto" parametros
        double conversionCripto_Dolar= valorEnDolares(connection, cripto);


        //convierto el monto en FIAT en CRIPTO a comprar:
        double dolaresDisponibles= monto / conversionFiat_Dolar;
        double cripto_A_Comprar= dolaresDisponibles / conversionCripto_Dolar;
    	
        return cripto_A_Comprar;
    }
    
    
	public int comprarCripto (Connection connection, String cripto, String fiat, double monto ) throws SQLException {
    //cripto es la nomenclatura de la criptomoneda (BTC, etc)
	//fiat es la nomenclatura de la FIAT (ARS, USD)
    Scanner scanner = new Scanner(System.in);
	ResultSet rs;
    String sql1,sql2,sql3, sql4;
	Statement stmt;
	PreparedStatement p_sent1, p_sent2, p_sent3, p_sent4;
	stmt = connection.createStatement();
	
	//verifico que la moneda "cripto" exista:
	if (! existeMoneda(connection, cripto))
		return 1;
	
	//verifico que la moneda "fiat" exista:
    if (! existeMoneda(connection, fiat))
		return 2;
	
	double cripto_A_Comprar = criptoAComprar(connection, cripto, fiat, monto );
    
    
	// verifico stock	
	sql1 = "SELECT STOCK FROM MONEDA WHERE NOMENCLATURA = ? ";
	p_sent1 = connection.prepareStatement(sql1);
	p_sent1.clearParameters();
	p_sent1.setString(1,cripto);
	//ejecuto el comando
	rs= p_sent1.executeQuery() ; 
	double stockDisponible= rs.getDouble("STOCK");
	if ( cripto_A_Comprar > stockDisponible) {
	    p_sent1.close();
	    stmt.close();
		return 3;
	}
	
	// verifico si la cantidad de FIAT ingresado es suficiente
	if ( montoActual(connection, fiat, "F") < monto) { 
		p_sent1.close();
	    stmt.close();
		return 4;
	}
	
	
    // Si la criptomoneda a comprar no es aún un activo, se crea el nuevo activo		
    if ( ! existeActivoCripto(connection, cripto) ) {
      //la cripto a comprar no existe en la tabla ACTIVO_CRIPTO
      //la creo:
      Activo a = new Activo(cripto, cripto_A_Comprar);
      generarActivoCripto(connection, a);
    }
    else { //si ya existe, se incrementa el activo existente
    	//Se incrementa la cantidad de cripto en la BD
        sql4 = "UPDATE ACTIVO_CRIPTO SET CANTIDAD = CANTIDAD + ? WHERE NOMENCLATURA = ?";
        p_sent4 = connection.prepareStatement(sql4);
        //p_sent3.setDouble(1, montoActual(connection, cripto, "C")); // El monto disponible actual
        p_sent4.setDouble(1, cripto_A_Comprar);           // El monto a sumar
        p_sent4.setString(2, cripto);           // La nomenclatura de la moneda
        // Ejecutar la actualización
        p_sent4.executeUpdate();
        p_sent4.close();
        //System.out.println("Filas actualizadas: " + rowsAffected);
    }
    
   
    
   //Se decrementa la cantidad en FIAT en la BD
    sql3 = "UPDATE ACTIVO_FIAT SET CANTIDAD = CANTIDAD - ? WHERE NOMENCLATURA = ?";
    p_sent3=  connection.prepareStatement(sql3);
    //p_sent2.setDouble(1, montoActual(connection, fiat, "F")); // El monto disponible actual
    p_sent3.setDouble(1, monto);           // El monto a restar
    p_sent3.setString(2, fiat);           // La nomenclatura de la moneda
    // Ejecutar la actualización
    p_sent3.executeUpdate();
   
    
    
    
    //Se decrementa el stock de la cantidad de cripto comprada
    sql2 = "UPDATE MONEDA SET STOCK = STOCK - ? WHERE NOMENCLATURA = ?";
    p_sent2 = connection.prepareStatement(sql2);
    //p_sent3.setDouble(1, montoActual(connection, cripto, "C")); // El monto disponible actual
    p_sent2.setDouble(1, cripto_A_Comprar);           // El monto a sumar
    p_sent2.setString(2, cripto);           // La nomenclatura de la moneda
    // Ejecutar la actualización
    p_sent2.executeUpdate();
    
    
    
   //se genera la TRANSACCION y se agrega a la tabla de transacciones
    String resumen= "Se compro "+ cripto_A_Comprar+" "+ cripto + " con " + monto+ " "+ fiat ;
    LocalDateTime fechaYhoraActual = LocalDateTime.now();
    Transaccion T = new Transaccion(resumen, fechaYhoraActual);
   //agrego la transaccion a la BD
    sql2 = " INSERT INTO TRANSACCION (RESUMEN, FECHA_HORA)"
    		+ " VALUES(? , ?)";
    p_sent2 = connection.prepareStatement(sql2);
    p_sent2.setString(1, resumen);
    // Convierte LocalDateTime a Timestamp para que SQLite lo acepte
    p_sent2.setTimestamp(2, Timestamp.valueOf(fechaYhoraActual));
    // Ejecuta la inserción en la base de datos
    p_sent2.executeUpdate(); // Realiza la inserción
    
 
    
    p_sent1.close();
    p_sent2.close();
    p_sent3.close();
    stmt.close();
    
    return 0;
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
		p_sent.close();
		stmt.close();
		return;
	}
    	
	//confirmar la operacion
		System.out.println("Confirmacion de la operacion");
		System.out.println("Usted va a intercambiar "+ montoACambiar+" "+criptoEsperada+" con "+monto+" "+criptoAconvertir);
		System.out.println("¿Confirma la operacion? (S/N)");
		String opcion= scanner.next().toUpperCase();
		if (opcion.equals("N")) {
			System.out.println("Operacion sin confirmar: Realice la operacion nuevamente en el menu de opciones");
			p_sent.close();
			stmt.close();
			return;
		}
		
		//se genera la "transaccion"
	    String resumen= "Se intercambio "+ montoACambiar+" "+ criptoEsperada + " con " + monto +" "+criptoAconvertir ;
	    LocalDateTime fechaYhoraActual = LocalDateTime.now();
	    Transaccion t = new Transaccion(resumen, fechaYhoraActual);
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
	
		//AGREGAR TRANSACCION A LA BD!!!!!!!!!!!!!!!!!
	    
	    stmt.close();
	    p_sent.close();
		
  }

}


