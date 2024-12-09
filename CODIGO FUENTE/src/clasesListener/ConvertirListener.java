package clasesListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import java.text.DecimalFormatSymbols;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import clasesDAOjdbc.ActivoDAOjdbc;
import vista.VistaCompra;

/*esta clase listener se encarga de realizar la conversion de cripto a comprar con el monto de fiat configurado cuando se aprieta el boton Convertir */
public class ConvertirListener implements ActionListener{

	private VistaCompra vistaCompra;
	
	public ConvertirListener (VistaCompra vistaCompra) {
		this.vistaCompra = vistaCompra;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String nomenclaturaCriptoAComprar = vistaCompra.getCriptoAComprar();
		double montoFiat = (double) vistaCompra.getMontoField();
		String fiat = vistaCompra.getMonedaSeleccionada();
		double precioCripto = vistaCompra.getPrecioCripto(); 
		
		
		
		ActivoDAOjdbc a = new ActivoDAOjdbc();
		try {
			
			double cantCriptoAComprar = a.criptoAComprar(nomenclaturaCriptoAComprar, fiat , montoFiat );
			
			
			vistaCompra.setResultadoLabel(cantCriptoAComprar);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		
		
	}
}
