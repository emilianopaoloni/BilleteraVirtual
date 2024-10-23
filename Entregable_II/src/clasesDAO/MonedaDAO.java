package clasesDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo_clases.Moneda;

public class MonedaDAO {
//esta clase contiene los metodos para acceder a la BD de criptomonedas
	
//PREGUNTAR SI LA CLASE SE LLAMARIA gestorDAO O monedaDAO
	
//PREGUNTAR: NUESTROS ATRIBUTOS NO COINCIDEN DEL TODO
// P/EJ: en Transaccion dice fecha y hora, y nosotros solo pusimos tipo transaccion y una herencia en uml

//CON MONEDA NOS PASA LO MISMO (VER UML Y LAS DIFERENCIAS CON ESTE CODIGO)

	
  //constructor
  public MonedaDAO() {
	  
  }
	
  public static void creacionDeTablasEnBD(Connection connection) throws SQLException {
    Statement stmt;
    stmt = connection.createStatement();
    //creo tabla Moneda:
	String sql = "CREATE TABLE MONEDA " 
	+ "(" 
	+ " TIPO       VARCHAR(1)    NOT NULL, "
	+ " NOMBRE       VARCHAR(50)    NOT NULL, " 
	+ " NOMENCLATURA VARCHAR(10)  PRIMARY KEY   NOT NULL, "
	+ " VALOR_DOLAR	REAL     NOT NULL, " 
	+ " VOLATILIDAD	REAL     NULL, "
	+ " STOCK	REAL     NULL "  + ")";
	stmt.executeUpdate(sql);
	//creo tabla Activo Cripto
	sql = "CREATE TABLE ACTIVO_CRIPTO" 
	+ "(" 
	+ " NOMENCLATURA VARCHAR(10)  PRIMARY KEY     NOT NULL, "
	+ " CANTIDAD	REAL    NOT NULL " + ")";
	stmt.executeUpdate(sql);
	//creo tabla Activo Fiat
	sql = "CREATE TABLE ACTIVO_FIAT" 
	+ "(" 
	+ " NOMENCLATURA VARCHAR(10)  PRIMARY KEY     NOT NULL, "
	+ " CANTIDAD	REAL    NOT NULL " + ")";
	stmt.executeUpdate(sql);
	//creo tabla Transaccion
	sql = "CREATE TABLE TRANSACCION" 
	+ "(" 
	+ " RESUMEN VARCHAR(1000)   NOT NULL, "
	+ " FECHA_HORA		DATETIME  NOT NULL " + ")";
	stmt.executeUpdate(sql);
	stmt.close();
}
  
  public void crearMoneda(Connection connection, Moneda m) throws SQLException {
	  Statement stmt;
	  stmt = connection.createStatement();
	  String sql;
	  //si es de tipo cripto se ingresan todos los datos (contemplando stock y volatilidad)
	  if (m.getTipo().equals("C")) {
	    sql= "INSERT INTO MONEDA (TIPO, NOMBRE, NOMENCLATURA, VALOR_DOLAR, VOLATILIDAD, STOCK)"
	  		+ " VALUES('"+m.getTipo()+"', '"+m.getNombre()+"', '"+m.getNomenclatura()+"', '"+m.getValorEnDolar()+"', '"+m.getVolatilidad()+"', '"+m.getStock()+"');";
	  }
	  else {
		  //si es tipo fiat: stock=volatilidad=null
		  sql= "INSERT INTO MONEDA (TIPO, NOMBRE, NOMENCLATURA, VALOR_DOLAR, VOLATILIDAD, STOCK)"
			  		+ " VALUES('"+m.getTipo()+"', '"+m.getNombre()+"', '"+m.getNomenclatura()+"', '"+m.getValorEnDolar()+"', NULL, NULL);";
	  }
	  stmt.executeUpdate(sql);
	  stmt.close();			  
			  
  }
  
  public void listarMonedas(Connection connection) throws SQLException {
	  Statement stmt;
	  stmt = connection.createStatement();
	  //Muestra en pantalla información de las monedas disponibles, ordenadas por nomenclatura.
      //consulta para obtener las monedas ordenadas por nomeclatura:
	  String sql= "SELECT * FROM moneda ORDER BY nomenclatura";
			  
      //se ejecuta la consulta:
	  ResultSet rs= stmt.executeQuery(sql);
      
      // se recorre el ResultSet y mostramos los datos.
      while (rs.next()) {
          // imprimo datos
    	  System.out.println("Tipo: "+ rs.getString("TIPO")+ 
    			  " | Nombre: "+ rs.getString("NOMBRE")+ 
    			  " | Nomenclatura: "+ rs.getString("NOMENCLATURA")+
    			  " | Valor en dolar: "+ rs.getString("VALOR_DOLAR")+ 
    			  " | Volatilidad: "+ rs.getString("VOLATILIDAD")+ 
    			  " | Stock: "+ rs.getString("STOCK") );
    	  
    	  
    	  
      }
      
	  stmt.close();
  }
  
  
public void generarStock(Connection connection) throws SQLException {
	  // De manera aleatoria genera una cantidad de monedas disponibles para las criptos de la billetera
	
	  Statement stmt;
	  stmt = connection.createStatement();
	  
	  // Genera un stock aleatorio entre 0 y 1000 para cada criptomoneda.
	  //Solo agrega stock a las filas de monedas que son CRIPTOMONEDAS (las fiat no poseen stock)
	  String sql = "UPDATE MONEDA SET STOCK = " + Math.random() * 1000 + " WHERE TIPO = 'C'";
	    
	  stmt.executeUpdate(sql);
	  
	  stmt.close();
}

public void listarStock(Connection connection) throws SQLException {
	  Statement stmt;
	  stmt = connection.createStatement();
	 //muestra en pantalla información del stock disponibles de criptomonedas, ordenadas por nomenclatura.
   
	 //consulta para obtener la tabla moneda SOLO LAS CRIPTOS ordenadas por nomeclatura:
	  String sql= "SELECT * FROM moneda WHERE TIPO = 'C' ORDER BY nomenclatura";
			  
    //se ejecuta la consulta:
	  ResultSet rs= stmt.executeQuery(sql) ;
    
    // se recorre el ResultSet y mostramos los datos.
    while (rs.next()) {
        // imprimo datos
  	  System.out.println( rs.getString("MONEDA")+ rs.getString("nomenclatura")+ " STOCK DISPONIBLE: "+ rs.getString("STOCK") );
    }
    
	  stmt.close();
}
  
  
}
