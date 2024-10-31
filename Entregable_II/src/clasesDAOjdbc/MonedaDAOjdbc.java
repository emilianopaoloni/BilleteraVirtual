package clasesDAOjdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import interfacesDAO.MonedaDAO;
import modelo_clases.Moneda;



public class MonedaDAOjdbc implements MonedaDAO {
//esta clase contiene los metodos para acceder a la BD de criptomonedas
	
//PREGUNTAR SI LA CLASE SE LLAMARIA gestorDAO O monedaDAO
	
//PREGUNTAR: NUESTROS ATRIBUTOS NO COINCIDEN DEL TODO
// P/EJ: en Transaccion dice fecha y hora, y nosotros solo pusimos tipo transaccion y una herencia en uml

//CON MONEDA NOS PASA LO MISMO (VER UML Y LAS DIFERENCIAS CON ESTE CODIGO)

	
  //constructor
  public MonedaDAOjdbc() {
	  
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
			  		+ " VALUES('"+m.getTipo()+"', '"+m.getNombre()+"', '"+m.getNomenclatura()+"', '"+m.getValorEnDolar()+"', NULL , NULL);";
	  }
	  stmt.executeUpdate(sql);
	  stmt.close();			  
			  
  }
  
  public LinkedList<Moneda> obtenerDatosMoneda(Connection connection) throws SQLException {
	  Statement stmt;
	  stmt = connection.createStatement();
	  
	  String sql= "SELECT * FROM moneda ORDER BY nomenclatura";
      //se ejecuta la consulta:
	  ResultSet rs= stmt.executeQuery(sql);
	  
	  //creo lista a retornar:
	  LinkedList<Moneda> listaMonedas = new LinkedList<Moneda>();
	  
	  while (rs.next()) {
          // cargo datos
		  Moneda m = new Moneda(rs.getString("TIPO"), rs.getString("NOMBRE"), rs.getString("NOMENCLATURA"), rs.getDouble("VALOR_DOLAR"), rs.getDouble("STOCK"), rs.getInt("VOLATILIDAD") );
    	  //agrego moneda a la lista:
		  listaMonedas.add(m);
      }
	  
	  return listaMonedas;
  }
  
  
   
  
public void generarStock(Connection connection) throws SQLException {
	  // De manera aleatoria genera una cantidad de monedas disponibles para las criptos de la billetera
	
	  PreparedStatement p_sent;
	  String sql = "UPDATE MONEDA SET STOCK=? WHERE NOMENCLATURA = ?";
	  p_sent = connection.prepareStatement(sql);

	  //obtengo lista de nomenclaturas de cripto para ITERAR sobre esta:
	  Statement stmt = connection.createStatement();
	  ResultSet rs = stmt.executeQuery("SELECT NOMENCLATURA FROM MONEDA WHERE TIPO = 'C'");
	  
	  String nom;
	  //itero sobre las distintas nomenclaturas para modificar su STOCK:
	  while(rs.next()) {
		 
		  nom = rs.getString("NOMENCLATURA");
		  
		  //genero random  
		  double randomStock = Math.random() * 1000; // Genera un valor aleatorio distinto
		  
		  //agrego el valor random al STOCK de NOMENCLATURA (nom):
		  p_sent.setDouble(1, randomStock);
		  p_sent.setString(2, nom);
		  p_sent.executeUpdate();
		  
	  }
	  
	// Cierra los recursos
	  rs.close();
	  stmt.close();
	  p_sent.close();
		
	  
}

  
  
}
