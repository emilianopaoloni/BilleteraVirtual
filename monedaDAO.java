package clasesDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//PREGUNTAR
import java.util.Scanner;

public class monedaDAO {
//esta clase contiene los metodos para acceder a la BD de criptomonedas
	
//PREGUNTAR SI LA CLASE SE LLAMARIA gestorDAO O monedaDAO
	
//PREGUNTAR: NUESTROS ATRIBUTOS NO COINCIDEN DEL TODO
// P/EJ: en Transaccion dice fecha y hora, y nosotros solo pusimos tipo transaccion y una herencia en uml

//CON MONEDA NOS PASA LO MISMO (VER UML Y LAS DIFERENCIAS CON ESTE CODIGO)

	
	
  private static void creaciónDeTablasEnBD(Connection connection) throws SQLException {
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
  
  public void crearMoneda(Connection connection) throws SQLException {
	  Statement stmt;
	  stmt = connection.createStatement();
	  String sql= "INSERT INTO MONEDA (TIPO, NOMBRE, NOMENCLATURA, VALOR_DOLAR, STOCK)"
	  + " VALUES(m.TIPO, m.NOMBRE, m.NOMENCLATURA, m.VALOR_DOLAR, m.STOCK)";
			 
	  stmt.executeUpdate(sql);
	  stmt.close();	  
			  

  }
  
  public void listarMonedas(Connection connection) throws SQLException {
	  Statement stmt;
	  stmt = connection.createStatement();
	  
	  //Muestra en pantalla información de las monedas disponibles, ordenadas por nomenclatura.
      // consulta para obtener las monedas ordenadas por nomeclatura:
	  String sql= "SELECT * FROM MONEDA ORDER BY NOMENCLATURA";
			  
      //se ejecuta la consulta:
	  ResultSet rs=stmt.executeQuery(sql);
     
      // se recorre el ResultSet y mostramos los datos.
      while (rs.next()) {
          // imprimo datos
    	  System.out.print(rs.getString("TIPO"), rs.getString("NOMBRE"), rs.getString("NOMENCLATURA"), rs.getString("VALOR_DOLAR"), rs.getString("STOCK") );
      }
      
	  stmt.close();
  }

  public void generarMisActivos() {
	  Scanner sc=new Scanner(System.in);
	  System.out.print("Tipo de activo a generar (FIAT/CRIPTO): ");
	  String tipoA = sc.nextLine().toUpperCase();
	  
	  System.out.print("NOMENCLATURA: ");
	  String nom = sc.nextLine().toUpperCase();
	  
	  System.out.print("VALOR: ");
	  double val= sc.nextDouble(); //NO SE SI ERA ASI
	  
	  if(tipoA.equals("CRIPTO")) {		  
		  ActivoCripto ac= new ActivoCripto();
		 
		  ac.setSiglaidentificatoria = nom;
		
		  ac.SetValorEnDolar = val; //NO SE SI ERA ASI
		  
	  }
	  else if(tipoA.equals("FIAT")) {
		  ActivoFiat af= new ActivoFiat();
		  af.siglaidentificatoria = sc.nextLine().toUpperCase(); 
		  
		  af.valorEnDolar = sc.nextDouble(); 		  
	  }
	  
	  sc.close();
  }
  
  
  public void generarActivoFiat(Connection connection, ActivoFiat af) throws SQLException {
	  Statement stmt;
	  stmt = connection.createStatement();
	  String sql= "INSERT INTO ACTIVO_FIAT (NOMENCALTURA, CANTIDAD)"
	  + " VALUES(af.NOMENCLATURA, af.CANTIDAD)";
			 
	  stmt.executeUpdate(sql);
	  stmt.close();	
	  
  
  }
  
  public void generarActivoCripto(Connection connection,ActivoCripto ac) throws SQLException {
	  Statement stmt;
	  stmt = connection.createStatement();
	  String sql= "INSERT INTO ACTIVO_CRIPTO (NOMENCALTURA, CANTIDAD)"
	  + " VALUES(ac.NOMENCLATURA,ac.CANTIDAD)";
			 
	  stmt.executeUpdate(sql);
	  stmt.close();	
	  
  
  }
}

  

