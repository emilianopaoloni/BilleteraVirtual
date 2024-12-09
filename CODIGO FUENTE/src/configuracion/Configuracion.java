package configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Configuracion {

	
	/**
	* Este método se encarga de la creación de las tablas.
	*
	* @param connection objeto conexion a la base de datos SQLite
	* @throws SQLException
	*/
	//PUBLICO O PRIVADO?
	public static void creaciónDeTablasEnBD(Connection connection) throws SQLException {
		Statement stmt;
		stmt = connection.createStatement();
		String sql = "CREATE TABLE  IF NOT EXISTS PERSONA "
				+ "("
				+ " ID       INTEGER   PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ " NOMBRES       VARCHAR(50)    NOT NULL, "
				+ " APELLIDOS       VARCHAR(50)    NOT NULL "
				+ ")";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE  IF NOT EXISTS USUARIO " + "(" + " ID       INTEGER   PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ " ID_PERSONA       INTEGER   NOT NULL, "
				+ " EMAIL       VARCHAR(50)    NOT NULL, "
				+ " PASSWORD       VARCHAR(50)    NOT NULL, "
				+ " ACEPTA_TERMINOS       BOOLEAN    NOT NULL, "
				+ " FOREIGN KEY(ID_PERSONA) REFERENCES PERSONA(ID)"
				+ ")";
		stmt.executeUpdate(sql);
				
		sql = "CREATE TABLE  IF NOT EXISTS MONEDA "
				+ "("
				+ " ID       INTEGER   PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ " TIPO       VARCHAR(1)    NOT NULL, "
				+ " NOMBRE       VARCHAR(50)    NOT NULL, "
				+ " NOMENCLATURA VARCHAR(10)  NOT NULL, "
				+ " VALOR_DOLAR	REAL     NOT NULL, "
				+ " VOLATILIDAD	REAL     NULL, "
				+ " STOCK	REAL     NULL, "
				+ " NOMBRE_ICONO       VARCHAR(50)    NOT NULL "
				+ ")";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE  IF NOT EXISTS ACTIVO_CRIPTO"
				+ "("
				+ " ID       INTEGER   PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ " ID_USUARIO INTEGER    NOT NULL, "
				+ " ID_MONEDA INTEGER    NOT NULL, "
				+ " CANTIDAD	REAL    NOT NULL, "
				+ " FOREIGN KEY(ID_USUARIO) REFERENCES USUARIO(ID),"
				+ " FOREIGN KEY(ID_MONEDA) REFERENCES MONEDA(ID) "
				+ ")";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE  IF NOT EXISTS ACTIVO_FIAT"
				+ "("
				+ " ID       INTEGER   PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ " ID_USUARIO INTEGER    NOT NULL, "
				+ " ID_MONEDA INTEGER    NOT NULL, "
				+ " CANTIDAD	REAL    NOT NULL, "
				+ " FOREIGN KEY(ID_USUARIO) REFERENCES USUARIO(ID),"
				+ " FOREIGN KEY(ID_MONEDA) REFERENCES MONEDA(ID)"
				+ ")";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE  IF NOT EXISTS TRANSACCION"
				+ "("
				+ " ID     INTEGER   PRIMARY KEY AUTOINCREMENT NOT NULL , "
				+ " RESUMEN VARCHAR(1000)   NOT NULL, "
				+ " FECHA_HORA		DATETIME  NOT NULL, "
				+ " ID_USUARIO INTEGER    NOT NULL, "
				+ " FOREIGN KEY(ID_USUARIO) REFERENCES USUARIO(ID)"
				+ ")";
		stmt.executeUpdate(sql);
		
		stmt.close();
		
		//se crean las monedas que se pueden utilizar en la aplicacion (si ya existen no se crean):
		
		String checkSql = "SELECT COUNT(*) FROM MONEDA WHERE NOMENCLATURA = ?";
		String insertSql = "INSERT INTO MONEDA (TIPO, NOMBRE, NOMENCLATURA, VALOR_DOLAR, VOLATILIDAD, NOMBRE_ICONO) VALUES (?, ?, ?, ?, ?, ?)";


		//bitcoin
		
		    // Comprobar si ya existe la nomenclatura
		    try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
		        checkStmt.setString(1, "BTC"); // NOMENCLATURA que estás verificando
		        try (ResultSet rs = checkStmt.executeQuery()) {
		            if (rs.next() && rs.getInt(1) > 0) {
		                //System.out.println("La nomenclatura ya existe: " + "BTC");
		            }
		            else {
		            	
		            	 // Insertar si no existe
		    		    try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
		    		    	insertStmt.setString(1, "C");
		    		    	insertStmt.setString(2, "Bitcoin");
		    		    	insertStmt.setString(3, "BTC");
		    			    insertStmt.setDouble(4, 1); // VALOR_DOLAR
		    			    insertStmt.setInt(5, 0); // VOLATILIDAD
		    			    insertStmt.setString(6, "btc.img"); // NOMBRE_ICONO
		    			    insertStmt.executeUpdate();
		    		        
		    		        //System.out.println("Moneda insertada con éxito: " + "BTC");
		    		    }
		    		 catch (SQLException e) {
		    		    e.printStackTrace();
		    		}
		          }
		        }
		    }
		    
		  //etherium
			
			    // Comprobar si ya existe la nomenclatura
			    try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
			        checkStmt.setString(1, "ETH"); // NOMENCLATURA que estás verificando
			        try (ResultSet rs = checkStmt.executeQuery()) {
			            if (rs.next() && rs.getInt(1) > 0) {
			                //System.out.println("La nomenclatura ya existe: " + "ETH");
			            }
			            else {
			            	
			            	 // Insertar si no existe
			    		    try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
			    		    	insertStmt.setString(1, "C");
			    		    	insertStmt.setString(2, "Etherium");
			    		    	insertStmt.setString(3, "ETH");
			    			    insertStmt.setDouble(4, 1); // VALOR_DOLAR
			    			    insertStmt.setInt(5, 0); // VOLATILIDAD
			    			    insertStmt.setString(6, "eth.img"); // NOMBRE_ICONO
			    			    insertStmt.executeUpdate();
			    		      
			    		        //System.out.println("Moneda insertada con éxito: " + "ETH");
			    		    }
			    		 catch (SQLException e) {
			    		    e.printStackTrace();
			    		}
			          }
			        }
			    }
			    
			  //dogecoin
				
				    // Comprobar si ya existe la nomenclatura
				    try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
				        checkStmt.setString(1, "DOGE"); // NOMENCLATURA que estás verificando
				        try (ResultSet rs = checkStmt.executeQuery()) {
				            if (rs.next() && rs.getInt(1) > 0) {
				                //System.out.println("La nomenclatura ya existe: " + "DOGE");
				            }
				            else {
				            	
				            	 // Insertar si no existe
				    		    try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
				    		    	insertStmt.setString(1, "C");
				    		    	insertStmt.setString(2, "Dogecoin");
				    		    	insertStmt.setString(3, "DOGE");
				    			    insertStmt.setDouble(4, 1); // VALOR_DOLAR
				    			    insertStmt.setInt(5, 0); // VOLATILIDAD
				    			    insertStmt.setString(6, "doge.img"); // NOMBRE_ICONO
				    			    insertStmt.executeUpdate();
				    		        
				    		       // System.out.println("Moneda insertada con éxito: " + "DOGE");
				    		    }
				    		 catch (SQLException e) {
				    		    e.printStackTrace();
				    		}
				          }
				        }
				    }

				    
				  //peso argentino
					
					    // Comprobar si ya existe la nomenclatura
					    try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
					        checkStmt.setString(1, "ARS"); // NOMENCLATURA que estás verificando
					        try (ResultSet rs = checkStmt.executeQuery()) {
					            if (rs.next() && rs.getInt(1) > 0) {
					                //System.out.println("La nomenclatura ya existe: " + "ARS");
					            }
					            else {
					            	
					            	 // Insertar si no existe
					    		    try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
					    		    	insertStmt.setString(1, "F");
					    		    	insertStmt.setString(2, "Peso Argentino");
					    		    	insertStmt.setString(3, "ARS");
					    			    insertStmt.setDouble(4, 0.001); // VALOR_DOLAR
					    			    insertStmt.setInt(5, 0); // VOLATILIDAD
					    			    insertStmt.setString(6, "ars.img"); // NOMBRE_ICONO
					    			    insertStmt.executeUpdate();
					    		      
					    		       // System.out.println("Moneda insertada con éxito: " + "ARS");
					    		    }
					    		 catch (SQLException e) {
					    		    e.printStackTrace();
					    		}
					          }
					        }
					    }
					    
						  //dolar
						
						    // Comprobar si ya existe la nomenclatura
						    try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
						        checkStmt.setString(1, "USD"); // NOMENCLATURA que estás verificando
						        try (ResultSet rs = checkStmt.executeQuery()) {
						            if (rs.next() && rs.getInt(1) > 0) {
						                //System.out.println("La nomenclatura ya existe: " + "USD");
						            }
						            else {
						            	
						            	 // Insertar si no existe
						    		    try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
						    		    	insertStmt.setString(1, "F");
						    		    	insertStmt.setString(2, "Dolar");
						    		    	insertStmt.setString(3, "USD");
						    			    insertStmt.setDouble(4, 1); // VALOR_DOLAR
						    			    insertStmt.setInt(5, 0); // VOLATILIDAD
						    			    insertStmt.setString(6, "usd.img"); // NOMBRE_ICONO
						    			    insertStmt.executeUpdate();
						    		        
						    		        //System.out.println("Moneda insertada con éxito: " + "USD");
						    		    }
						    		 catch (SQLException e) {
						    		    e.printStackTrace();
						    		}
						          }
						        }
						    }
						    
						    
						    //Tether
							
							    // Comprobar si ya existe la nomenclatura
							    try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
							        checkStmt.setString(1, "USDT"); // NOMENCLATURA que estás verificando
							        try (ResultSet rs = checkStmt.executeQuery()) {
							            if (rs.next() && rs.getInt(1) > 0) {
							                //System.out.println("La nomenclatura ya existe: " + "USDT");
							            }
							            else {
							            	
							            	 // Insertar si no existe
							    		    try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
							    		    	insertStmt.setString(1, "C");
							    		    	insertStmt.setString(2, "Tether");
							    		    	insertStmt.setString(3, "USDT");
							    			    insertStmt.setDouble(4, 1); // VALOR_DOLAR
							    			    insertStmt.setInt(5, 0); // VOLATILIDAD
							    			    insertStmt.setString(6, "usdt.img"); // NOMBRE_ICONO
							    			    insertStmt.executeUpdate();
							    		        
							    		        //System.out.println("Moneda insertada con éxito: " + "USDT");
							    		    }
							    		 catch (SQLException e) {
							    		    e.printStackTrace();
							    		}
							          }
							        }
							    }
							    
							    //usdc-coin
								
								    // Comprobar si ya existe la nomenclatura
								    try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
								        checkStmt.setString(1, "USDC"); // NOMENCLATURA que estás verificando
								        try (ResultSet rs = checkStmt.executeQuery()) {
								            if (rs.next() && rs.getInt(1) > 0) {
								                //System.out.println("La nomenclatura ya existe: " + "USDC");
								            }
								            else {
								            	
								            	 // Insertar si no existe
								    		    try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
								    		    	insertStmt.setString(1, "C");
								    		    	insertStmt.setString(2, "Usdc-coin");
								    		    	insertStmt.setString(3, "USDC");
								    			    insertStmt.setDouble(4, 1); // VALOR_DOLAR
								    			    insertStmt.setInt(5, 0); // VOLATILIDAD
								    			    insertStmt.setString(6, "usdc.img"); // NOMBRE_ICONO
								    			    insertStmt.executeUpdate();
								    		        
								    		        //System.out.println("Moneda insertada con éxito: " + "USDC");
								    		    }
								    		 catch (SQLException e) {
								    		    e.printStackTrace();
								    		}
								          }
								        }
								    }
	}
}
								    
								
							
								    
								    
						    
						    
						    
		   
		
		
	




