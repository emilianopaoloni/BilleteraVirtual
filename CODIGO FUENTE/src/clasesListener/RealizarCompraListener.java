package clasesListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import clasesDAOjdbc.ActivoDAOjdbc;
import clasesDAOjdbc.TransaccionDAOjdbc;
import configuracion.DatosUsuario;
import excepciones.SaldoFiatInsuficienteException;
import excepciones.StockInsuficienteException;
import vista.VistaCompra;
import vista.VistaCotizaciones;

/*esta clase se encarga de la funcion del boton realizar compra en VistaCompra. Llama a los metodos DAO encargados de modificar la BD segun la compra */
public class RealizarCompraListener implements ActionListener {
 
	private VistaCompra vistaCompra;
	private DatosUsuario datosUsuario;
	private ActivoDAOjdbc a;
	private TransaccionDAOjdbc t;
	
	public RealizarCompraListener(VistaCompra vistaCompra, DatosUsuario datosUsuario) {
		this.vistaCompra = vistaCompra;
		this.datosUsuario = datosUsuario;
		this.a = new ActivoDAOjdbc();
		this.t = new TransaccionDAOjdbc();
	}


	public void actionPerformed(ActionEvent e) {
		
		
		
			
			if ( vistaCompra.getMonedaSeleccionada().isEmpty()) {
                JOptionPane.showMessageDialog(vistaCompra, "Por favor, complete todos los campos.");
                return;
            }
			
			
			
	
		//Aplico excepciones
		try {
			try {
				a.comprarCripto(vistaCompra.getCriptoAComprar(),vistaCompra.getMonedaSeleccionada(), vistaCompra.getMontoField(), datosUsuario);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//si la compra sale bien:
			JOptionPane.showMessageDialog(vistaCompra, "Compra realizada con éxito.");
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
			//actualizo lista de transacciones en DatosUsuario
			try {
				datosUsuario.setTransacciones(t.obtenerDatosTransaccion(datosUsuario.getIdUsuario()));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		catch (StockInsuficienteException e1) {
			//muestro mensaje de error al usuario
			JOptionPane.showMessageDialog(vistaCompra, e1.getMessage());
		}
			
		
        catch (SaldoFiatInsuficienteException e2) {
        	//muestro mensaje de error al usuario
        	JOptionPane.showMessageDialog(vistaCompra, e2.getMessage());
		}
				
			
		
		//silencio vista actual
		vistaCompra.setVisible(false);
		//vuelvo a vista cotizaciones
		new VistaCotizaciones(datosUsuario);
		
	
		
	
		
  }
	
	
}



