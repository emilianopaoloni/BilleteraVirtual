package clasesDAOjdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import configuracion.MyConnection;
import interfacesDAO.MonedaDAO;
import modelo_clases.Moneda;
import modelo_clases.PrecioCripto;



public class MonedaDAOjdbc implements MonedaDAO {
//esta clase contiene los metodos para acceder a la BD de criptomonedas
	


	
  //constructor
  public MonedaDAOjdbc() {
	  
  }
  
  /*obtiene precio en dolares de la cripto pasada como parametro */
  public double obtenerPrecio(String nomenclaturaCripto) {
      String sql = "SELECT VALOR_DOLAR FROM MONEDA WHERE NOMENCLATURA = ?";
      try (  Connection connection = MyConnection.getCon() ;
    		  PreparedStatement stmt = connection.prepareStatement(sql)) {
          stmt.setString(1, nomenclaturaCripto);
          try (ResultSet rs = stmt.executeQuery()) {
              if (rs.next()) {
                  return rs.getDouble("VALOR_DOLAR");
              }
          }
      } catch (SQLException e) {
          System.err.println("Error al obtener el valor en dolares: " + e.getMessage());
      }
      return -1; // Retorna -1 si no se encuentra la criptomoneda o hay un error
  }
  
  
  public double obtenerStock(String nomenclaturaCripto) {
      String sql = "SELECT STOCK FROM MONEDA WHERE NOMENCLATURA = ?";
      try (  Connection connection = MyConnection.getCon() ;
    		  PreparedStatement stmt = connection.prepareStatement(sql)) {
          stmt.setString(1, nomenclaturaCripto);
          try (ResultSet rs = stmt.executeQuery()) {
              if (rs.next()) {
                  return rs.getDouble("STOCK");
              }
          }
      } catch (SQLException e) {
          System.err.println("Error al obtener el stock: " + e.getMessage());
      }
      return -1; // Retorna -1 si no se encuentra la criptomoneda o hay un error
  }

   
  /*
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
			  
  } */
  
  
  /*
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
  } */
  
  
   
  
  /*  De manera aleatoria genera una cantidad de monedas disponibles para las criptos de la billetera */
public void generarStock () throws SQLException {
	  
      Connection connection = MyConnection.getCon();	
	
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


/*actualiza precios en dolares de las criptomonedas, segun la lista de precios que recibe como parametro */
public void actualizarPreciosEnBD(List<PrecioCripto> precios) throws SQLException {
	  
	 String sql = "UPDATE MONEDA SET VALOR_DOLAR = ? WHERE NOMENCLATURA = ?";
	
	// Conexión a la base de datos
	
	try(
	Connection connection = MyConnection.getCon();
    PreparedStatement statement = connection.prepareStatement(sql); ) {

    // Itera sobre la lista de precios
    for (PrecioCripto precioCripto : precios) {
            // Configura los valores en el PreparedStatement
            statement.setDouble(1, precioCripto.getPrecio());  // VALOR_DOLAR
            statement.setString(2, precioCripto.getNomenclatura()); // NOMENCLATURA
            
            // Ejecuta la actualización
            int filasActualizadas = statement.executeUpdate();

            //  imprimir información para depurar
            //System.out.println("Actualizadas " + filasActualizadas + " filas para " + precioCripto.getNomenclatura());
        }
	}
	 catch (SQLException e) {
	    e.printStackTrace();
	    throw e; // Re-lanzar la excepción para manejarla en otro lugar
	}
    
   
	
}
  
  
}
