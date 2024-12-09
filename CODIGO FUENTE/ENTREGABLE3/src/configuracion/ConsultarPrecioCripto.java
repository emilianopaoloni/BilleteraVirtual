package configuracion;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


import org.json.JSONObject; // Necesita agregar la librería org.json para trabajar con JSON

import modelo_clases.PrecioCripto;

/*esta clase contiene el "task" que debe ejecutarse cada 5 segundos, que es acceder al servidor y consultar precios de criptomonedas */
public class ConsultarPrecioCripto   {
   private static final String URL_API = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum,usd-coin,tether,dogecoin&vs_currencies=usd";
   
   
   /*metodo run: se ejecutara cada 60 segundos*/
   public  List<PrecioCripto> obtenerPrecios() {
	  
       HttpClient cliente = HttpClient.newHttpClient();
       HttpRequest solicitud = HttpRequest.newBuilder()
               .uri(URI.create(URL_API))
               .GET()
               .build();
       try {
           HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
           if (respuesta.statusCode() == 200) {
        	   return parsearYMostrarPrecios(respuesta.body());
           } else {
               System.out.println("Error: " + respuesta.statusCode());
           }
       } catch (IOException | InterruptedException e) {
           e.printStackTrace();
       }
       
       return new ArrayList<>(); // Retorna una lista vacía en caso de error
       
   }
   
   private static List<PrecioCripto> parsearYMostrarPrecios(String cuerpoRespuesta) {
	   

	   
	   List<PrecioCripto> listaPrecios = new ArrayList<>();
	   JSONObject json = new JSONObject(cuerpoRespuesta);
       
       
	   double precioBTC = json.getJSONObject("bitcoin").getDouble("usd");
       listaPrecios.add(new PrecioCripto("BTC", precioBTC));

       double precioETH = json.getJSONObject("ethereum").getDouble("usd");
       listaPrecios.add(new PrecioCripto("ETH", precioETH));

       double precioUSDC = json.getJSONObject("usd-coin").getDouble("usd");
       listaPrecios.add(new PrecioCripto("USDC", precioUSDC));

       double precioUSDT = json.getJSONObject("tether").getDouble("usd");
       listaPrecios.add(new PrecioCripto("USDT", precioUSDT));

       double precioDOGE = json.getJSONObject("dogecoin").getDouble("usd");
       listaPrecios.add(new PrecioCripto("DOGE", precioDOGE));

       return listaPrecios;
   }

       
   }


