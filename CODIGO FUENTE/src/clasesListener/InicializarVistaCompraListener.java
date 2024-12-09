package clasesListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clasesDAOjdbc.MonedaDAOjdbc;
import configuracion.DatosUsuario;
import configuracion.GestorDeTarea;
import vista.VistaCompra;
import vista.VistaCotizaciones;
import vista.VistaInicioSesion;

public class InicializarVistaCompraListener implements ActionListener {
	private String nomenclaturaCripto;
	private double precioCripto;
	private GestorDeTarea gestor;
	private DatosUsuario datosUsuario;
	private VistaCotizaciones vistaCotizaciones;
	private MonedaDAOjdbc m;

	public InicializarVistaCompraListener(String nomenclaturaCripto, GestorDeTarea gestor, DatosUsuario datosUsuario, VistaCotizaciones vistaCotizaciones) {
        this.nomenclaturaCripto = nomenclaturaCripto;
        this.gestor = gestor;
        this.datosUsuario = datosUsuario;
        this.vistaCotizaciones =  vistaCotizaciones;
        this.m = new MonedaDAOjdbc();
    }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//detengo los pedidos a la API
    	gestor.detenerTarea();
    	
    	// Cerrar o "silenciar" la vista actual (vistaCotizaciones)
    	vistaCotizaciones.setVisible(false);
        
        //obtener el STOCK de la cripto a comprar
        double stockCripto = m.obtenerStock(nomenclaturaCripto);
        
        //obtener el PRECIO de la cripto a comprar
        precioCripto = m.obtenerPrecio(nomenclaturaCripto);
        
        // Crear una nueva instancia de compra y mostrarla
        VistaCompra vistaComprar = new VistaCompra(nomenclaturaCripto,  precioCripto, stockCripto,  datosUsuario);
	}

}
