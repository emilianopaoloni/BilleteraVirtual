package configuracion;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import clasesDAOjdbc.MonedaDAOjdbc;
import modelo_clases.PrecioCripto;
import vista.VistaCotizaciones;

/*esta clase gestiona la repeticion de la tarea de ConsultarPrecioCripto cada 5 segundos */
public class GestorDeTarea {
	private Timer timer;
    private ConsultarPrecioCripto consultarPrecioCripto;
    private VistaCotizaciones vista;
    private MonedaDAOjdbc m;

    public GestorDeTarea(VistaCotizaciones vista) {
        this.timer = new Timer();
        this.consultarPrecioCripto = new ConsultarPrecioCripto();
        this.vista = vista;
        this.m = new MonedaDAOjdbc();

        iniciarTarea();
    }

    private void iniciarTarea() {
        // Programar el Timer para que se ejecute cada 5 segundos
    	//utilizo una clase anonima para el TimerTask
           timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Consultar los precios
                List<PrecioCripto> precios = consultarPrecioCripto.obtenerPrecios();
                //actualizar precios en la BD
                
                try {
					m.actualizarPreciosEnBD(precios);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                vista.actualizarPrecios(precios);
               
                
             }
            
           }, 0, 60000); // delay inicial = 0, intervalo = 60000 ms (60 segundos)
    }

    public void detenerTarea() {
        timer.cancel(); // Detiene el Timer cuando ya no se necesita
    }
}


