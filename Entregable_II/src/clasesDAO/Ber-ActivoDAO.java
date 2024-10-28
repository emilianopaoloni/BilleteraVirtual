package clasesDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import modelo_clases.Activo;

public class ActivoDAO {
	
	
	/* Preguntar si la creacion de las tablas ACTIVO_FIAT y ACTIVO_CRIPTO van aca
	 * o si van directamente en el main
	 */
	
    
    //constructor
	public ActivoDAO() {
		
	}
	
	public static void creacionDeTablasEnBD(Connection connection) throws SQLException {
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
		
	}
	
	/*
	 * El metodo generarMisActivos esta pensado para que tenga un intento para creacion, se le pide que ingrese los datos hasta que confirme
	 * que escribio todo bien, si confirma, se busca la existencia de la moneda en la DB, si existe se procede con el metodo, si no informa
	 * error y se debe volver a seleccionar el metodo en el menu de opciones.
	 */
	
	
	 /*
	  * Si la lectura de datos se hace desde el main, en realidad lo primero que se hace una vez que se llama al metodo es ver si existe, si
	  * existe, se procede con el metodo, si no, informa error y termina el metodo. La confirmacion de los datos por parte del usuario en este
	  * caso tambien se haria en el main y se le pasarian los datos como parametro a la funcion 
	  * 
	  *
	  */
	
	public void generarMisActivos(Connection connection, String tipoA, String nom, double cant) throws SQLException {
		
		 /* Preguntar si todo esta parte de la lectura por pantalla no deberia ir en el main, 
		  * y que desde alli se llame aun metodo que cheque en la DB de monedas EXISTENTES 
		  * si existe o no esa moneda y que al devolver true la cree llamando a los metodos
		  *  para introducirlos en sus respectivas tablas
		  */
		  
		// Abro la DB para chequear si la moneda existe		  
		  	Statement stmt;
		  	String sql= "SELECT NOMENCLATURA, FROM ACTIVO_CRIPTO";
		    ResultSet rs= stmt.executeQuery(sql);
		    //Recorro tabla para ver si existe la moneda a generar como activo
		    boolean existe=false;
		    while ( (rs.next()) | (existe==false) ) {
		    if ( rs.getString("NOMENCLATURA").equals(nom) ) // ??
		    	existe=true;
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
				 System.out.println("Activo no cargado.");
			 }
		 stmt.close();
		 //Tengo que cerrar tambien la connection?
	  }
	
	
	  
	  private void generarActivoFiat(Connection connection, Activo af) throws SQLException {
		  Statement stmt;
		  stmt = connection.createStatement();
		  String sql= "INSERT INTO ACTIVO_FIAT (NOMENCALTURA, CANTIDAD)"
		  + " VALUES(af.nomenclatura, af.cantidad)";				 
		  stmt.executeUpdate(sql);
		  stmt.close();	
		//Tengo que cerrar tambien la connection?
		 }
	  
	  private void generarActivoCripto(Connection connection,Activo ac) throws SQLException {
		  Statement stmt;
		  stmt = connection.createStatement();
		  String sql= "INSERT INTO ACTIVO_CRIPTO (NOMENCALTURA, CANTIDAD)"
		  + " VALUES(ac.nomenclatura,ac.cantidad)";				 
		  stmt.executeUpdate(sql);
		  stmt.close();
		//Tengo que cerrar tambien la connection?
	  }

	  // Este es el que esta haciendo Emi
	  
	public void comprarCripto (Connection connection, String cripto, String fiat, double monto) {
    //cripto es la nomenclatura de la criptomoneda (BTC, etc)
	//fiat es la nomenclatura de la FIAT (ARS, USD)
	
	Statement stmt;
	stmt = connection.createStatement();
	
	//obtengo de la tabla moneda el valor en dolares de la moneda "fiat" parametros
	String sql= "SELECT VALOR_DOLAR, FROM moneda WHERE NOMBRE="fiat" "
	//se ejecuta la consulta:
	ResultSet rs= stmt.executeQuery(sql) ;
    int conversionFiat_Dolar= rs.getInt("VALOR_DOLAR");
    		
    //obtengo de la tabla moneda el valor en dolares de la moneda "cripto" parametros
    sql= "SELECT VALOR_DOLAR, FROM moneda WHERE NOMBRE="cripto" "
    //se ejecuta la consulta:
    ResultSet rs= stmt.executeQuery(sql) ;
    int conversionCripto_Dolar= rs.getInt("VALOR_DOLAR")
    
    //convierto el monto en FIAT en el de la CRIPTO a comprar:
    MontoCripto_a_comprar= conversionCripto_Dolar * conversionFiat_Dolar
    
    //***************EL VALOR EN DOLAR ES UN ATRIBUTO en la TABLA !!!
	
    // Si la criptomoneda a comprar no es aún un activo, se crea el nuevo activo
	
	//Si la criptomoneda a comprar ya existe, simplemente se incrementa la cantidad y se decrementa la cantidad en FIAT. 

    //Se espera que verifique el Stock disponible.
    
    //Se espera que haga uso de las clases de su modelo que le permiten representar “una compra” antes de guardarlo en la Base De Datos
  
    
    
    //***crea una fila en la tabla transaccion?
} 

	
	
	
//preguntar si este metodo esta bien en esta clase, ya que accede a la tabla monedas
	//para verificar los tipos, y a la tabla de activos!!!
swap (Connection connection, String criptoAconvertir, double monto, String criptoEsperada ) {
	Statement stmt;
	stmt = connection.createStatement()
			
	//verifico que ambas criptos pertenecen a activo:
	String sql= "SELECT NOMENCLATURA FROM ACTIVO_CRIPTO"
			  
    //se ejecuta la consulta:
	ResultSet rs= stmt.executeQuery(sql);
		      
    int booleanos=0
    while (rs.next()) {
	  if (rs.getString("NOMENCLATURA") == criptoAconvertir) | (rs.getString("NOMENCLATURA") == criptoEsperada) {
		  
		  booleanos=booleanos + 1;
	  }
    }
    if ( booleanos=2) { //signfica que las dos criptos perteneces a activos
    	System.out.print("se puede realizar la operacion");
    }
    else {
    	System.out.print("ERROR: una de las criptos ingresada no existe");
    }
    	
    //EL VALOR EN DOLAR ES UN ATRIBUTO !!!
    
	stmt.close();
}


