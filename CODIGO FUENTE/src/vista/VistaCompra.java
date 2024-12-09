package vista;

import javax.swing.*;

import configuracion.DatosUsuario;
import modelo_clases.Activo;
import modelo_clases.Transaccion;
import utilidades.InstaladorUtilidades;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.LinkedList;

import clasesListener.ConvertirListener;
import clasesListener.RealizarCompraListener;

public class VistaCompra extends JFrame {

    private JFrame frame;
    private JLabel stockLabel;
    private JLabel precioLabel;
    private JTextField montoField;
    private JComboBox<String> monedaComboBox;
    private JButton convertirButton;
    private JLabel resultadoLabel;
    private JButton realizarCompraButton;
    private JButton cancelarButton;
    private String nomenclaturaCriptoAComprar;
    private double cantCriptoAComprar;
    private double precioCripto;
    private double stockCripto;
    private DatosUsuario datosUsuario;
    private JLabel nuevaEtiqueta;
 
    private Font fuenteTextos; 

    public VistaCompra   (String criptoAComprar, double precioCripto, double stockCripto,  DatosUsuario datosUsuario) {
        this.precioCripto = precioCripto;
        this.nomenclaturaCriptoAComprar = criptoAComprar ;   
        this.datosUsuario = datosUsuario;
        this.stockCripto = stockCripto;
        // Cargar la fuente personalizada
        Font fuenteTextos = InstaladorUtilidades.cargarFuente("recursos/Conthrax-SemiBold.otf");
    	
       

        // Configuración de la ventana
        setTitle("Billetera Virtual - Compra");
        setSize(600, 400); // Ventana más grande para que todo entre correctamente
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Usamos null para manejar posiciones personalizadas de manera más precisa
        
     // Reutilizar cargarImagen para establecer el ícono de la ventana
        ImageIcon icono = InstaladorUtilidades.cargarIcono("logoapp1",60); // Ajusta el nombre según corresponda
        if (icono != null) {
            setIconImage(icono.getImage()); // Usar la imagen del ImageIcon
        }

        // Establecer el fondo y los colores
        Color colorFondo = new Color(0, 10, 10); // Fondo oscuro
        Color colorBotones = Color.decode("#00f691"); // Verde brillante
        getContentPane().setBackground(colorFondo);

        // Etiqueta de stock disponible
        stockLabel = new JLabel("Stock disponible: " + stockCripto + " " + criptoAComprar);
        stockLabel.setFont(fuenteTextos.deriveFont(Font.BOLD, 16)); // Aplicar fuente personalizada
        stockLabel.setForeground(Color.WHITE);
        stockLabel.setBounds(20, 20, 550, 30); // Ajustamos el tamaño y la posición
        add(stockLabel);

        // Etiqueta del precio de compra
        
        precioLabel = new JLabel("Precio de Compra: $" +  String.format("%.6f", precioCripto));
        precioLabel.setFont(fuenteTextos.deriveFont(Font.PLAIN, 14)); // Aplicar fuente personalizada
        precioLabel.setForeground(Color.WHITE);
        precioLabel.setBounds(20, 60, 550, 30);
        add(precioLabel);

     // Etiqueta para "Quiero comprar con"
        JLabel quieroComprarLabel = new JLabel("Quiero comprar con");
        quieroComprarLabel.setFont(fuenteTextos.deriveFont(Font.PLAIN, 14)); // Aplicar fuente personalizada
        quieroComprarLabel.setForeground(Color.WHITE);
        quieroComprarLabel.setBounds(20, 100, 200, 30); // Ajustar tamaño para dar más espacio
        add(quieroComprarLabel);

        // Campo de texto para ingresar el monto de FIAT
        montoField = new JTextField(" ");
        montoField.setBounds(210, 100, 100, 30); // Reducir el ancho para que no ocupe tanto espacio
        add(montoField);

        // ComboBox para seleccionar la moneda FIAT
        String[] monedas = {"ARS", "USD"};
        monedaComboBox = new JComboBox<>(monedas);
        monedaComboBox.setBounds(315, 100, 80, 30); // Ajustar la posición para que no se solape
        add(monedaComboBox);

        // Botón "Convertir"
        convertirButton = new JButton("Convertir");
        convertirButton.setFont(fuenteTextos.deriveFont(Font.PLAIN, 14)); // Aplicar fuente personalizada
        convertirButton.setBackground(colorBotones);
        convertirButton.setForeground(Color.BLACK);
        convertirButton.setOpaque(true);
        convertirButton.setBorder(BorderFactory.createLineBorder(colorFondo, 2, true));
        convertirButton.setFocusPainted(false);
        convertirButton.setBounds(400, 100, 120, 30); // Ajustar la posición para que se vea completamente
        add(convertirButton);

        // Etiqueta de resultado de conversión
        resultadoLabel = new JLabel("Equivale a... ");
        resultadoLabel.setFont(fuenteTextos.deriveFont(Font.PLAIN, 14)); // Aplicar fuente personalizada
        resultadoLabel.setForeground(Color.WHITE);
        resultadoLabel.setBounds(80, 140, 500, 30);
        add(resultadoLabel);
        
        

        // Botón "Realizar Compra"
        realizarCompraButton = new JButton("Realizar Compra");
        realizarCompraButton.setFont(fuenteTextos.deriveFont(Font.PLAIN, 14)); // Aplicar fuente personalizada
        realizarCompraButton.setBackground(colorBotones);
        realizarCompraButton.setForeground(Color.BLACK);
        realizarCompraButton.setOpaque(true);
        realizarCompraButton.setBorder(BorderFactory.createLineBorder(colorFondo, 2, true));
        realizarCompraButton.setFocusPainted(false);
        realizarCompraButton.setBounds(100, 250, 180, 40);
        add(realizarCompraButton);

        // Botón "Cancelar"
        cancelarButton = new JButton("Cancelar");
        cancelarButton.setFont(fuenteTextos.deriveFont(Font.PLAIN, 14)); // Aplicar fuente personalizada
        cancelarButton.setBackground(colorBotones);
        cancelarButton.setForeground(Color.BLACK);
        cancelarButton.setOpaque(true);
        cancelarButton.setBorder(BorderFactory.createLineBorder(colorFondo, 2, true));
        cancelarButton.setFocusPainted(false);
        cancelarButton.setBounds(300, 250, 150, 40);
        add(cancelarButton);
        
        //-------------------------

        // Mostrar ventana
        this.setVisible(true);
        
        //agrego funcion al boton convertir
        convertirButton.addActionListener(new ConvertirListener(this)) ;
        
        //agrego funcion al boton realizac compra
        realizarCompraButton.addActionListener(new RealizarCompraListener(this, datosUsuario));
        
       //boton cancelar --> clase anonima
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelar();  // Llamada al método volver
            }

        });
    }

    public String getCriptoAComprar() {
		return nomenclaturaCriptoAComprar;
	}

	public void setCriptoAComprar(String criptoAComprar) {
		this.nomenclaturaCriptoAComprar = criptoAComprar;
	}

	public double getPrecioCripto() {
		return precioCripto;
	}

	public void setPrecioCripto(double precioCripto) {
		this.precioCripto = precioCripto;
	}

	// Getters y setters
  

    public JLabel getStockLabel() {
        return stockLabel;
    }

    public void setStockLabel(String text) {
        stockLabel.setText(text);
    }

    public JLabel getPrecioLabel() {
        return precioLabel;
    }

    public void setPrecioLabel(String text) {
        precioLabel.setText(text);
    }

  
    
  // Getter para obtener el monto como double 
   public double getMontoField() { 
   String textoMonto = montoField.getText(); 
   try { 
	   return Double.parseDouble(textoMonto); 
	} catch (NumberFormatException e) { 
		System.err.println("Error al convertir el monto a double: " + e.getMessage()); 
		return 0.0; // Valor predeterminado en caso de error 
		}
   }
   

    public void setMontoField(String text) {
        montoField.setText(text);
    }

 // Getter para obtener la opción seleccionada moneda FIAT como String
    public String getMonedaSeleccionada() { 
    	return (String) monedaComboBox.getSelectedItem();
    }

    public JButton getConvertirButton() {
        return convertirButton;
    }

    public JLabel getResultadoLabel() {
        return resultadoLabel;
    }

    /*recibe la cantidad de cripto a comprar (conversion) y la muestra en la vista */    
    public void setResultadoLabel(double cantCriptoAComprar ) {
    	
        this.cantCriptoAComprar = cantCriptoAComprar;
       
    	
    	String text = "Equivale a... "+ cantCriptoAComprar +" "+ nomenclaturaCriptoAComprar ;
    	
        resultadoLabel.setText(text);
        resultadoLabel.setForeground(Color.YELLOW);        
        
        
    }

    public JButton getRealizarCompraButton() {
        return realizarCompraButton;
    }

    public JButton getCancelarButton() {
        return cancelarButton;
    }
    
    private void cancelar() {
    	//silencio vista actual
    	this.setVisible(false);
        //vuelvo a vista de cotizaciones
    	VistaCotizaciones vistaCotizaciones = new VistaCotizaciones(datosUsuario);
    	
    }
    
   

    
    
    
    
}
