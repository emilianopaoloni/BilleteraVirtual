package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//La conexión entre la aplicación Java y la base de datos se gestiona mediante un objeto 
// Connection que representa una conexión activa con la base de datos y es a través de este que
// se puede enviar consultas SQL desde la aplicación a la base de datos.
// el objeto connection pertenece a la interfaz Connection de la API JDBC. Esta interfaz forma parte
// del paquete java.sql.
// el objto Statement es usado para ejecutar sentencias SQL y recuperar resultados
//   En JDBC, cuando se ejecuta una consulta SQL que extrae datos de la base de datos
//   (como un SELECT), el resultado se almacena en un objeto de tipo ResultSet
// si se extraen datos --> ResultSet()
// si se modifican datos --> executeUpdate()


public class Main {

	public static void main(String[] args){
		
		//crear objeto Connection
		Connection con=null;
		 try { 
         //establecer conexion con la BD
		 con=DriverManager.getConnection("jdbc:sqlite:test.db"); 
		 Statement st = con.createStatement();
		
		 
		 //creo objeto Statement
		 Statement sent = con.createStatement();
		 
		 //comando SQL que voy a ejecutar: creacion de tabla
		 String sql= "CREATE TABLE IF NOT EXISTS 'usuario' ('id' INTEGER PRIMARY KEY AUTOINCREMENT,"+
		 "'nombre' varchar (50) NOT NULL,"+
		 "'apellido' varchar (50) NOT NULL,"+
		 "'edad' INT NOT NULL, 'nacionalidad' varchar (50) NOT NULL )";
		
		 //ejecuto comando a travez de executeUpdate (porque no estoy extrayendo datos)
		 st.executeUpdate(sql);
		    
		 System.out.println("Tabla 'usuario' creada exitosamente.");
		 
		 //cierro objetos?
		 st.close();
		 con.close();
		 
		 //el catch se pone por si hubo algun error al ingresar en la BD
		 } catch (SQLException e) {
			 System.out.println("No se pudo conectar a la BD: " + e.getMessage());
			 e.printStackTrace();  // Esto mostrará más detalles en la consola.
		 }
		
	}
	
	//#clase manager que abra todo
	
	//#paquete servicios --> los gestores
	//#pquete DAO --> para clases dao
	
}
