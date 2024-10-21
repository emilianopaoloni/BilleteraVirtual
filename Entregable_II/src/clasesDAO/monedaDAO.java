package clasesDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
	  String sql= "INSERT INTO moneda (tipo, nombre, nomenclatura, valorDolar, stock)"
	  		+ " VALUES(m.tipo, m.nombre, m.nomenclatura, m.valorDolar, m.stock);"
			 
	  stm.executeUpdate(sql);
	  stmt.close();			  
			  

  }
  
  public void listarMonedas(Connection connection) throws SQLException {
	  Statement stmt;
	  stmt = connection.createStatement();
	  
	  //Muestra en pantalla información de las monedas disponibles, ordenadas por nomenclatura.
      // consulta para obtener las monedas ordenadas por nomeclatura:
	  String sql= "SELECT * FROM moneda ORDER BY nomenclatura;"
			  
      //se ejecuta la consulta:
      stm.executeQuery( sql) 
      
      // se recorre el ResultSet y mostramos los datos.
      while (rs.next()) {
          // imprimo datos
    	  System.out.print(rs.getString("tipo"), rs.getString("nombre"), rs.getString("nomenclatura"), rs.getString("valorDolar"), rs.getString("stock") );
      }
      
	  stmt.close();
  }

  
  
}
