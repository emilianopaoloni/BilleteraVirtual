package clasesDAO;

import java.sql.ResultSet;
import java.sql.Statement;

public class ActivoDAO {
	
	
    
    //constructor
	public ActivoDAO() {
		
	}


	public void comprarCripto (Connection connection, String cripto, String fiat, double monto, ) {
    //cripto es la nomenclatura de la criptomoneda (BTC, etc)
	//fiat es la nomenclatura de la FIAT (ARS, USD)
	
	Statement stmt;
	stmt = connection.createStatement()
	
	//obtengo de la tabla moneda el valor en dolares de la moneda "fiat" parametros
	String sql= "SELECT VALOR_DOLAR, FROM moneda WHERE NOMBRE="fiat" "
	//se ejecuta la consulta:
	ResultSet rs= stmt.executeQuery(sql) ;
    int conversionFiat_Dolar= rs.getInt("VALOR_DOLAR")
    		
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


