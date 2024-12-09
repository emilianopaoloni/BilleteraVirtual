package clasesListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clasesDAOjdbc.ActivoDAOjdbc;
import clasesDAOjdbc.MonedaDAOjdbc;
import configuracion.DatosUsuario;
import modelo_clases.Activo;
import vista.VistaDeInicio;

/*esta clase contiene el metodo que genera stock y activo determinado al usuario actual, para que la aplicacion pueda ser probada */
public class GenerarDatosPruebaListener implements ActionListener {
	private DatosUsuario datosUsuario;
	private VistaDeInicio vistaDeInicio;
	private  MonedaDAOjdbc m;
	private  ActivoDAOjdbc a;
	
	public GenerarDatosPruebaListener(DatosUsuario datosUsuario, VistaDeInicio vistaDeInicio) {
		this.datosUsuario = datosUsuario;
		this.vistaDeInicio =  vistaDeInicio;
		this.m = new MonedaDAOjdbc();
		this.a = new ActivoDAOjdbc();
	}
	
	@Override	
	 public void actionPerformed(ActionEvent e) {
		 
		//genero 200000 ARS al usuario actual
		Activo activo1 = new Activo("ARS", 200000);
		try {
			a.generarActivoFiat(activo1, datosUsuario.getIdUsuario());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//genero 1000 USD al usuario actual
		Activo activo2 = new Activo("USD", 1000);
		try {
			a.generarActivoFiat(activo2, datosUsuario.getIdUsuario());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		 //genero stock aleatorio a monedas
		 try {
			m.generarStock();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		 
		 //actualizar vista --> para que aparezcan los activos generados.
		//actualizo lista de activos en DatosUsuario 
	     try {
			datosUsuario.setActivos(a.obtenerDatosActivos(datosUsuario.getIdUsuario()));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//actualizo y balance total en DatosUsuario
	     try {
			datosUsuario.setBalanceTotal(a.calcularBalance(datosUsuario.getIdUsuario()));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	     
		 
		 //MOSTRAR que se generaron datos
	      JOptionPane.showMessageDialog(vistaDeInicio, "Stock y activos fiat generados automaticamente.");
		
	    //"reinicio" la vistaDeInicio"
		 this.vistaDeInicio.setVisible(false);
		 new VistaDeInicio(datosUsuario);
		 
	 
	 }
	 

}
