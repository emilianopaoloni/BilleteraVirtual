package clasesDAOjdbc;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.Scanner;


import configuracion.DatosUsuario;
import configuracion.MyConnection;
import excepciones.StockInsuficienteException;
import excepciones.SaldoFiatInsuficienteException;
import interfacesDAO.ActivoDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import modelo_clases.Activo;
import modelo_clases.Swap;
import modelo_clases.Transaccion;
import configuracion.DatosUsuario;

public class ActivoDAOjdbc implements ActivoDAO {
	
	
    
    //constructor
	public ActivoDAOjdbc() {
		
	}
 
	 /* verifica si existe el activo cripto en usuario con idUsuario pasado como parametro en la BD */
		private boolean existeActivoCripto ( String nomenclaturaCripto, int idUsuario ) throws SQLException {
	            Connection connection = MyConnection.getCon();		  
		        
		        
		        String sql = """
		                SELECT 1
		                FROM ACTIVO_CRIPTO ac
		                JOIN MONEDA m ON ac.ID_MONEDA = m.ID
		                WHERE ac.ID_USUARIO = ? AND m.NOMENCLATURA = ?
		                LIMIT 1
		            """;
		      
	                PreparedStatement pstmt = connection.prepareStatement(sql);
	               // Establecer los parámetros de la consulta
	               pstmt.setInt(1, idUsuario);
	               pstmt.setString(2, nomenclaturaCripto);
	               
	               // Ejecutar la consulta y verificar si existe un resultado
	               try (ResultSet rs = pstmt.executeQuery()) {
	                   return rs.next();
	               }
		     
		}
		
		
		/* verifica si existe el activo fiat en usuario con idUsuario pasado como parametro en la BD 
		*/
		
		private boolean existeActivoFiat ( String nomenclaturaFiat, int idUsuario ) throws SQLException {
			 Connection connection = MyConnection.getCon();		  
		        
		        
		        String sql = """
		                SELECT 1
		                FROM ACTIVO_FIAT ac
		                JOIN MONEDA m ON ac.ID_MONEDA = m.ID
		                WHERE ac.ID_USUARIO = ? AND m.NOMENCLATURA = ?
		                LIMIT 1
		            """;
		      
	                PreparedStatement pstmt = connection.prepareStatement(sql);
	               // Establecer los parámetros de la consulta
	               pstmt.setInt(1, idUsuario);
	               pstmt.setString(2, nomenclaturaFiat);
	               
	               // Ejecutar la consulta y verificar si existe un resultado
	               try (ResultSet rs = pstmt.executeQuery()) {
	            	   //
	            	   
	            	   //
	                   return rs.next();
	               }
		}
		
	
	 
	 /*este metodo retorna el id de la moneda cargada en BD a partir de su nomenclatura */
	  private int obtenerIdMoneda(String nomenclatura) throws SQLException{
		  Connection connection = MyConnection.getCon();
		  
		  String sqlObtenerId = "SELECT ID FROM MONEDA WHERE NOMENCLATURA = ?";
		  PreparedStatement p_sentId = connection.prepareStatement(sqlObtenerId);
		  p_sentId.setString(1, nomenclatura); // NOMENCLATURA de la moneda
		  ResultSet rs = p_sentId.executeQuery();

		  int idMoneda = -1;
		  if (rs.next()) {
		      idMoneda = rs.getInt("ID");
		  } else {
		      throw new IllegalArgumentException("Moneda no encontrada: " + nomenclatura);
		  }
		  rs.close();
		  p_sentId.close();
		  
		  return idMoneda;
	  }
	 
	 
	 
	 
	 
	 /*este metodo genera un nuevo activo cripto al usuario con idUsuario 
	   Y si el activo ya existe en la tabla del usuario, solamente le suma la cantidad de ac.getCantidad */
	  private void generarActivoCripto(Activo ac, int idUsuario) throws SQLException {
		  Connection connection = MyConnection.getCon();
		  PreparedStatement p_sent;
		  
		  //primero obtengo el id de la moneda del activo a generar
		  int idMoneda = obtenerIdMoneda(ac.getNomenclatura());
		  
		  
		  
		  //verifico si ya existe el activo
		  if( ! existeActivoCripto( ac.getNomenclatura(), idUsuario ) ) {
			  String sql = """
					    INSERT INTO ACTIVO_CRIPTO (ID_USUARIO, ID_MONEDA, CANTIDAD)
					    VALUES (?, ?, ?)
					""";

					p_sent = connection.prepareStatement(sql);
					p_sent.clearParameters();
					p_sent.setInt(1, idUsuario);  
					p_sent.setInt(2, idMoneda);    
					p_sent.setDouble(3, ac.getCantidad()) ;   
					p_sent.executeUpdate();   }  
		  else {
			  //si existe, le sumo la cantidad de ac.getCantidad() 
			  String sql = """
					    UPDATE ACTIVO_CRIPTO
					    SET CANTIDAD = CANTIDAD + ?
					    WHERE ID_USUARIO = ? 
					    AND ID_MONEDA =  ?
					""";
			  
			  p_sent = connection.prepareStatement(sql);
			  p_sent.clearParameters();
		      p_sent.setDouble(1, ac.getCantidad());           // El monto a sumar
		      p_sent.setInt(2, idUsuario);
		      p_sent.setInt(3, idMoneda);      
		      p_sent.executeUpdate();
		  }
		  
		  p_sent.close();
	  }
	  
	  
	  /*es public ya que se utiliza para generarle activos fiat al usuario para probar la app*/
	  public void generarActivoFiat( Activo af, int idUsuario) throws SQLException {	 
		
		  Connection connection = MyConnection.getCon();
		  PreparedStatement p_sent;
		  
		//primero obtengo el id de la moneda del activo a generar
		  int idMoneda = obtenerIdMoneda(af.getNomenclatura());
		  
          //verifico si ya existe el activo
		  if( ! existeActivoFiat(af.getNomenclatura(), idUsuario ) ) {
			  String sql = """
					    INSERT INTO ACTIVO_FIAT (ID_USUARIO, ID_MONEDA, CANTIDAD)
					    VALUES (?, ?, ?)
					""";

					p_sent = connection.prepareStatement(sql);
					p_sent.clearParameters();
					p_sent.setInt(1, idUsuario);  
					p_sent.setInt(2, idMoneda);    
					p_sent.setDouble(3, af.getCantidad()) ; 
					p_sent.executeUpdate(); }
		  else {
			 
			  //si existe, le sumo la cantidad de ac.getCantidad() 
			  String sql = """
					    UPDATE ACTIVO_FIAT
					    SET CANTIDAD = CANTIDAD + ?
					    WHERE ID_USUARIO = ? 
					    AND ID_MONEDA =  ?
					""";
			  
			  p_sent = connection.prepareStatement(sql);
			  p_sent.clearParameters();
		      p_sent.setDouble(1, af.getCantidad());           // El monto a sumar
		      p_sent.setInt(2, idUsuario);
		      p_sent.setInt(3, idMoneda);      
		      p_sent.executeUpdate();
		  }
		  
		  p_sent.close();
		 }
	 
    
	  
	  /*Este metodo retorna una lista de ACTIVOS CRIPTOS y FIAT del usuario con idUsuario*/
	public LinkedList<Activo> obtenerDatosActivos(int idUsuario) throws SQLException {
		  
		System.out.println(idUsuario);
		LinkedList<Activo> listaActivos = new LinkedList<Activo>();
	    Connection connection = MyConnection.getCon();

	    if (connection == null) {
	        throw new SQLException("Error al obtener la conexión a la base de datos.");
	    }

	    // Consultas para obtener activos cripto y fiat
	    String[] queries = {
	        "SELECT m.NOMBRE, ac.CANTIDAD FROM ACTIVO_CRIPTO ac JOIN MONEDA m ON ac.ID_MONEDA = m.ID WHERE ac.ID_USUARIO = ?",
	        "SELECT m.NOMBRE, ac.CANTIDAD FROM ACTIVO_FIAT ac JOIN MONEDA m ON ac.ID_MONEDA = m.ID WHERE ac.ID_USUARIO = ?"
	    		
	    };

	    // Itera sobre las consultas y procesa los resultados
	    for (String sql : queries) {
	        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setInt(1, idUsuario); // Establecer el ID del usuario
	            ResultSet rs = stmt.executeQuery();
	            
	            while (rs.next())  {
	                

	                String nombre = rs.getString("nombre");
	                double cantidad = rs.getDouble("cantidad");

	                // Crear un objeto Activo y agregarlo a la lista
	                Activo activo = new Activo(nombre, cantidad);
	                //agrego Activo a la lista
	                listaActivos.add(activo);
	            }
	             
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error al obtener los activos del usuario: " + e.getMessage());
	        }
	    }
	    

	    return listaActivos;
	}
	  
	
	
	/*este metodo retorna el balance total en peso argentino del usuario con Id usuario pasado como parametro */
	public double calcularBalance (int idUsuario) throws SQLException {
		
		    double balanceTotal = 0.0;
		    //se obtiene el valor en dolares de 1 peso argentino:
		    double valorDolarARS = valorEnDolaresARS();
		    
		    //obtengo conexion
		    Connection connection = MyConnection.getCon();

		    // Consulta para criptoactivos: selecciona filas de ACTIVO_CRIPTO donde ID_USUARIO= id_usuario
		    String sqlCripto = """
		        SELECT ac.CANTIDAD, m.VALOR_DOLAR
		        FROM ACTIVO_CRIPTO ac
		        JOIN MONEDA m ON ac.ID_MONEDA = m.ID
		        WHERE ac.ID_USUARIO = ?
		    """;

		    // Consulta para activos fiat
		    String sqlFiat = """
		        SELECT ac.CANTIDAD, m.VALOR_DOLAR
		        FROM ACTIVO_FIAT ac
		        JOIN MONEDA m ON ac.ID_MONEDA = m.ID
		        WHERE ac.ID_USUARIO = ?
		    """;

		    try (PreparedStatement stmtCripto = connection.prepareStatement(sqlCripto);
		         PreparedStatement stmtFiat = connection.prepareStatement(sqlFiat)) {

		        // Establece el ID del usuario en ambas consultas
		        stmtCripto.setInt(1, idUsuario);
		        stmtFiat.setInt(1, idUsuario);

		        // Procesa los activos cripto
		        try (ResultSet rsCripto = stmtCripto.executeQuery()) {
		            while (rsCripto.next()) {
		                double cantidad = rsCripto.getDouble("CANTIDAD");
		                double valorDolar = rsCripto.getDouble("VALOR_DOLAR");
		                balanceTotal += ( (cantidad * valorDolar) / valorDolarARS );
		            }
		        }

		        // Procesa los activos fiat
		        try (ResultSet rsFiat = stmtFiat.executeQuery()) {
		            while (rsFiat.next()) {
		                double cantidad = rsFiat.getDouble("CANTIDAD");
		                double valorDolar = rsFiat.getDouble("VALOR_DOLAR");
		                balanceTotal += ( (cantidad * valorDolar) / valorDolarARS );
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw new RuntimeException("Error al calcular el balance en pesos", e);
		    }

		    return balanceTotal;
		}

	

   /*este metodo retorna el valor en dolares de 1 peso argentino */
   private double valorEnDolaresARS () throws SQLException {
	  Connection connection = MyConnection.getCon();  
	   
	// Obtener el valor en dólares del peso argentino
	   String sqlValorDolarARS = """
	       SELECT VALOR_DOLAR 
	       FROM MONEDA 
	       WHERE NOMBRE = 'Peso Argentino'
	   """;

	   double valorDolarARS = 0.0;
	   try (PreparedStatement stmtARS = connection.prepareStatement(sqlValorDolarARS)) {
	       ResultSet rsARS = stmtARS.executeQuery();
	       if (rsARS.next()) {
	           valorDolarARS = rsARS.getDouble("VALOR_DOLAR");
	       }
	   }

	   // Validar que se haya obtenido el valor
	   if (valorDolarARS == 0.0) {
	       throw new RuntimeException("No se pudo obtener el valor en dólares del peso argentino.");
	   }
	   
	   return valorDolarARS;
   }
	
	
  
	
	/*devuelve el monto actual del activo pasado como parametro "nomenclatura" para el usuario con idUsuario --> puede ser cripto o fiat
     tipoMoneda es "C" para cripto y "F" para fiat --> para saber a que tabla de activo accedo
			*/
	private double montoActual ( String nomenclatura, String tipoMoneda, int idUsuario) throws SQLException {
		Connection connection = MyConnection.getCon();
		
		String sql;
        ResultSet rs;
        PreparedStatement p_sent;
        double montoDisponible;
        if ( tipoMoneda.equals("F")) {
            //accedo a tabla ACTIVO_FIAT
        	 sql = """
        		    SELECT ac.CANTIDAD 
        		    FROM ACTIVO_FIAT ac
        		    JOIN MONEDA m ON ac.ID_MONEDA = m.ID
        		    WHERE m.NOMENCLATURA = ? AND ac.ID_USUARIO = ?
        		""";

        		p_sent = connection.prepareStatement(sql);
        		p_sent.clearParameters();
        		p_sent.setString(1, nomenclatura); // NOMENCLATURA de la moneda
        		p_sent.setInt(2, idUsuario);      // ID del usuario
        	//se ejecuta la consulta:
            rs= p_sent.executeQuery() ;
    	    montoDisponible= rs.getDouble("CANTIDAD");
        }
        else {
        	//accedo a tabla ACTIVO_CRIPTO
        	sql = """
        		    SELECT ac.CANTIDAD 
        		    FROM ACTIVO_CRIPTO ac
        		    JOIN MONEDA m ON ac.ID_MONEDA = m.ID
        		    WHERE m.NOMENCLATURA = ? AND ac.ID_USUARIO = ?
        		""";

        		p_sent = connection.prepareStatement(sql);
        		p_sent.clearParameters();
        		p_sent.setString(1, nomenclatura); // NOMENCLATURA de la moneda
        		p_sent.setInt(2, idUsuario);      // ID del usuario
        	//se ejecuta la consulta:
            rs= p_sent.executeQuery() ;
    	    montoDisponible= rs.getDouble("CANTIDAD");
        }
		
		
		p_sent.close();
		
		return montoDisponible;
	}
	
	/* recibe NOMENCLATURA de moneda (no nombre)
	  devuelve el valor en dolares de la moneda pasada como parametro */
	private double valorEnDolares ( String moneda) throws SQLException {
		Connection connection = MyConnection.getCon();
		
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
    private boolean existeMoneda ( String nomenclatura) throws SQLException {
    	
    	Connection connection = MyConnection.getCon();
    	Statement stmt; stmt = connection.createStatement();
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
    
    

	/*este metodo recibe un monto de fiat, y calcula cuanta cripto "cripto" se puede comprar con ese monto (monto fiat)*/
    public double criptoAComprar ( String nomenclaturaCripto, String nomenclaturaFiat, double monto) throws SQLException {
    	 
    	Connection connection = MyConnection.getCon();
    	//obtengo de la tabla moneda el valor en dolares de la moneda "fiat" parametro
        double conversionFiat_Dolar= valorEnDolares( nomenclaturaFiat);
     
        //obtengo de la tabla moneda el valor en dolares de la moneda "cripto" parametros
        double conversionCripto_Dolar= valorEnDolares( nomenclaturaCripto);


        //convierto el monto en FIAT en CRIPTO a comprar:
        double dolaresDisponibles= monto * conversionFiat_Dolar;
        double cripto_A_Comprar= dolaresDisponibles / conversionCripto_Dolar;
        
     
    	
        return cripto_A_Comprar;
    }
   
    
    
    
    
    /*metodo que se ejcuta cuando se aprieta el boton "realizar compra" 
      cripto es la nomenclatura de la criptomoneda a comprar (BTC, etc)
      montoCripto es el monto de cripto a comprar
	  fiat es la nomenclatura de la FIAT con la que se compra (ARS, USD)
	  montoFiat es el monto de fiat con el que se compra
	  */
    
	public void  comprarCripto ( String cripto, String fiat, double montoFiat, DatosUsuario datosUsuario ) throws   SQLException, StockInsuficienteException, SaldoFiatInsuficienteException {

    Connection connection = MyConnection.getCon();
	
    Scanner scanner = new Scanner(System.in);
	ResultSet rs;
    String sql1,sql2,sql3, sql4;
	Statement stmt;
	PreparedStatement p_sent1, p_sent2, p_sent3, p_sent4;
	stmt = connection.createStatement();
	
	
	
	double cripto_A_Comprar = criptoAComprar( cripto, fiat, montoFiat );
    
    
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
	    //ERROR: NO HAY SUFICIENTE STOCK DE CRIPTOMONEDA A COMPRAR
	    throw new StockInsuficienteException("No hay suficiente stock de "+cripto+".");
	    
	}
	
	// verifico si la cantidad de FIAT ingresado es suficiente
	//busco en la lista de activos del usuario el Activo FIAT:
	
	double montoActual = montoActual(fiat, "F", datosUsuario.getIdUsuario());
	if ( montoActual < montoFiat) { 
	    //ERROR: EL USUARIO NO POSEE SUFICIENTE SALDO DE FIAT PARA REALIZAR LA COMPRA
		throw new SaldoFiatInsuficienteException( "No posee suficiente saldo de "+fiat+".");
	}
	
	
	//genero el activo a comprar
	Activo a = new Activo(cripto, cripto_A_Comprar);
	//ejecuto metodo que genera el activo
	generarActivoCripto(a, datosUsuario.getIdUsuario()); //si el usuario no posee el activo, el metodo lo crea. Y si ya lo tiene, le agrega la cantidad comprada
	
	
	
    
   //Se decrementa la cantidad en FIAT en la BD
	
	//primero obtengo el id de la moneda FIAT 
	int idFiat = obtenerIdMoneda(fiat);
	
    sql3 ="""
  	      UPDATE ACTIVO_FIAT
            SET CANTIDAD = CANTIDAD - ?
             WHERE ID_USUARIO = ?
             AND ID_MONEDA = ?
  	""";

	  p_sent3 = connection.prepareStatement(sql3);
	  p_sent3.setDouble(1, montoFiat);           // El monto a restar
	  p_sent3.setInt(2, datosUsuario.getIdUsuario());  //id usuario
	  p_sent3.setInt(3, idFiat);           // id de la moneda
	  // Ejecutar la actualización
	  p_sent3.executeUpdate();
	  p_sent3.close();
      
    
    
    
    
    //Se decrementa el stock de la cantidad de cripto comprada
    sql2 = "UPDATE MONEDA SET STOCK = STOCK - ? WHERE NOMENCLATURA = ?";
    p_sent2 = connection.prepareStatement(sql2);
    p_sent2.setDouble(1, cripto_A_Comprar);           // El monto a restar
    p_sent2.setString(2, cripto);           // La nomenclatura de la moneda
    // Ejecutar la actualización
    p_sent2.executeUpdate();
    
    
    
   //se genera la TRANSACCION y se agrega a la tabla de transacciones
    String resumen= "Se compro "+ cripto_A_Comprar+" "+ cripto + " con " + montoFiat+ " "+ fiat ;
    LocalDateTime fechaYhoraActual = LocalDateTime.now();
   // Definir el formato deseado 
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
    // Convertir el LocalDateTime a String 
    String fechaYhoraFormateada = fechaYhoraActual.format(formatter);
    Transaccion T = new Transaccion(resumen, fechaYhoraFormateada);
    
   //agrego la transaccion a la BD
    sql2 = " INSERT INTO TRANSACCION (RESUMEN, FECHA_HORA, ID_USUARIO) VALUES (?, ?, ?);";
    p_sent2 = connection.prepareStatement(sql2);
    p_sent2.setString(1, resumen);
    p_sent2.setString(2, fechaYhoraFormateada);
    p_sent2.setInt(3, datosUsuario.getIdUsuario());
    // Ejecuta la inserción en la base de datos
    p_sent2.executeUpdate(); // Realiza la inserción

 
    
    p_sent1.close();
    p_sent2.close();
    p_sent3.close();
    stmt.close();
    
   
} 

	
	/*
	
 public double intercambioCriptos (Connection connection, String cripto1, String cripto2, double monto) throws SQLException {
    	//este metodo recibe un monto de cripto1 a convertir en cripto2 y devuelve el equivalente en cripto2 del monto de cripto2
    	
    	double valorEnDolares_cripto1= valorEnDolares(connection, cripto1);
    	double valorEnDolares_cripto2= valorEnDolares(connection, cripto2);
    	
    	//calculo cuanto en dolares se tiene de la cripto2
    	double montoDolaresDisponible_cripto1=  valorEnDolares_cripto1 * monto;
    	
    	//calculo cuanto se puede comprar de cripto2 con el monto en dolares disponible
    	double montoACambiar= montoDolaresDisponible_cripto1 / valorEnDolares_cripto2;
    	
    	return montoACambiar;
    }
	
//preguntar si este metodo esta bien en esta clase, ya que accede a la tabla monedas
	//para verificar los tipos, y a la tabla de activos!!!
public int transaccionSwap (Connection connection, String criptoAconvertir, double monto, String criptoEsperada ) throws SQLException {
	Scanner scanner = new Scanner(System.in);
	PreparedStatement  p_sent2, p_sent3, p_sent4 ;
	String  sql2, sql3, sql4;
			
	//verifico que ambas criptos pertenecen a la BD:
	if( !existeActivoCripto(connection, criptoAconvertir) ){
		return 1;
	}
	
	if( !existeActivoCripto(connection, criptoEsperada) ){
		return 2;
	}
	
	//calculo el equivalente a cambiar:
	double cantCriptoAcambiar = intercambioCriptos(connection, criptoAconvertir, criptoEsperada, monto);
	
	
    //verifico stock de criptoEsperada
	// verifico si la cantCriptoAcambiar es suficiente
	if ( montoActual(connection, criptoEsperada, "C") < cantCriptoAcambiar) { 
		return 3;
		}

	
	//creo el objeto "Swap"
	Swap s = new Swap(criptoAconvertir,monto,criptoEsperada);
  
	
	/*
	 //se genera la TRANSACCION y se agrega a la tabla de transacciones
    String resumen= "Se intercambio "+monto+" "+criptoAconvertir+" con "+criptoEsperada+" EQUIVALENTE: "+cantCriptoAcambiar ;
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
    
	
   //Se decrementa la cantidad en criptoAcambiar en la BD
    sql3 = "UPDATE ACTIVO_CRIPTO SET CANTIDAD = CANTIDAD - ? WHERE NOMENCLATURA = ?";
    p_sent3=  connection.prepareStatement(sql3);
    p_sent3.setDouble(1, monto);           // El monto a restar
    p_sent3.setString(2, criptoAconvertir);    // La nomenclatura de la moneda
    // Ejecutar la actualización
    p_sent3.executeUpdate();
    
   //Se incrementa la cantidad en criptoEsperada en la BD
    sql4 = "UPDATE ACTIVO_CRIPTO SET CANTIDAD = CANTIDAD + ? WHERE NOMENCLATURA = ?";
    p_sent4=  connection.prepareStatement(sql4);
    p_sent4.setDouble(1, cantCriptoAcambiar);           // El monto a sumar
    p_sent4.setString(2, criptoEsperada);           // La nomenclatura de la moneda
    // Ejecutar la actualización
    p_sent4.executeUpdate();
	    
	  
	
	//p_sent2.close();
	p_sent3.close();
	p_sent4.close();
	return 0;
		
  }
*/




	


}


