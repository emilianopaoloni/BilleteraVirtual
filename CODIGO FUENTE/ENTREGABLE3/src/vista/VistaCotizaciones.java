package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedList;

import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import clasesListener.InicializarVistaCompraListener;
import configuracion.GestorDeTarea;
import modelo_clases.Activo;
import modelo_clases.PrecioCripto;
import modelo_clases.Transaccion;
import utilidades.InstaladorUtilidades;
import configuracion.DatosUsuario;

	public class VistaCotizaciones extends JFrame {

		 private JFrame frame;
		    private JTable table;
		    private DefaultTableModel tableModel;
		    private JButton logoutButton;
		    private JButton volverButton;
		    JButton comprarBTCButton;
		    JButton comprarETHButton;
		    JButton comprarDOGEButton;
		    JButton comprarUSDCButton;
		    JButton comprarUSDTButton;
		    private JLabel userLabel;
		    private DatosUsuario datosUsuario;
		    private GestorDeTarea gestor;
		    private JPanel buttonPanel; // Panel para los botones "Comprar"
		    private double precioBTC, precioUSDC, precioUSDT, precioDOGE, precioETH ;

		    public VistaCotizaciones(DatosUsuario datosUsuario) {
		        this.datosUsuario = datosUsuario;
		        
		        // Configuración de la ventana
		        setTitle("Cotizaciones de Criptomonedas");
		        setSize(800, 600); // Ajusta el tamaño según sea necesario
		        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        setLocationRelativeTo(null); // Centra la ventana en la pantalla
		        setResizable(false); // Evita que el usuario pueda agrandarla
		        setLayout(null); //

		     // Reutilizar cargarImagen para establecer el ícono de la ventana
		        ImageIcon icono = InstaladorUtilidades.cargarIcono("logoapp1",60); // Ajusta el nombre según corresponda
		        if (icono != null) {
		            setIconImage(icono.getImage()); // Usar la imagen del ImageIcon
		        }
		        
		     // Establecer colores
		        Color colorFondo = new Color(0, 10, 10); // Fondo negro un poco más claro
		        Color colorBotones = Color.decode("#00f691");; // Verde brillante
		     // Cargar la fuente personalizada
		        Font fuenteTextos = InstaladorUtilidades.cargarFuente("recursos/Conthrax-SemiBold.otf");

		        
		        getContentPane().setBackground(colorFondo);
		        
		        //Panel lateral de botones COMPRAR
		        JPanel panelBotones = new JPanel();
		        panelBotones.setLayout(null); // Colocar los botones en columna
		        panelBotones.setBounds(570, 160, 200, 300); // Ajustar la posición y tamaño
		        panelBotones.setBackground(colorFondo); // Para diferenciarlo, puedes elegir el color que prefieras
		        add(panelBotones);
		        
		        //Agrego botones de compra al panel

		        comprarBTCButton = new JButton("Comprar BTC");
		        comprarBTCButton.setFont(fuenteTextos.deriveFont(Font.BOLD, 13));
		        comprarBTCButton.setBackground(colorBotones);
		        comprarBTCButton.setForeground(Color.BLACK);
		        comprarBTCButton.setOpaque(true);
		        comprarBTCButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		        comprarBTCButton.setFocusPainted(false);
		        comprarBTCButton.setBounds(20, 25, 150, 50);
		        panelBotones.add(comprarBTCButton);
	        
		        comprarETHButton = new JButton("Comprar ETH");
		        comprarETHButton.setFont(fuenteTextos.deriveFont(Font.BOLD, 13));
		        comprarETHButton.setBackground(colorBotones);
		        comprarETHButton.setForeground(Color.BLACK);
		        comprarETHButton.setOpaque(true);
		        comprarETHButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		        comprarETHButton.setFocusPainted(false);
		        comprarETHButton.setBounds(20, 80, 150, 50);
		        panelBotones.add(comprarETHButton);
		        
		        comprarDOGEButton = new JButton("Comprar DOGE");
		        comprarDOGEButton.setFont(fuenteTextos.deriveFont(Font.BOLD, 13));
		        comprarDOGEButton.setBackground(colorBotones);
		        comprarDOGEButton.setForeground(Color.BLACK);
		        comprarDOGEButton.setOpaque(true);
		        comprarDOGEButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		        comprarDOGEButton.setFocusPainted(false);
		        comprarDOGEButton.setBounds(20, 245, 150, 50);
		        panelBotones.add(comprarDOGEButton);
		        
		        comprarUSDTButton = new JButton("Comprar USDT");
		        comprarUSDTButton.setFont(fuenteTextos.deriveFont(Font.BOLD, 13));
		        comprarUSDTButton.setBackground(colorBotones);
		        comprarUSDTButton.setForeground(Color.BLACK);
		        comprarUSDTButton.setOpaque(true);
		        comprarUSDTButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		        comprarUSDTButton.setFocusPainted(false);
		        comprarUSDTButton.setBounds(20, 190, 150, 50);
		        panelBotones.add(comprarUSDTButton);
		        
		        comprarUSDCButton = new JButton("Comprar USDC");
		        comprarUSDCButton.setFont(fuenteTextos.deriveFont(Font.BOLD, 13));
		        comprarUSDCButton.setBackground(colorBotones);
		        comprarUSDCButton.setForeground(Color.BLACK);
		        comprarUSDCButton.setOpaque(true);
		        comprarUSDCButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		        comprarUSDCButton.setFocusPainted(false);
		        comprarUSDCButton.setBounds(20, 135, 150, 50);
		        panelBotones.add(comprarUSDCButton);
		        
		        // Panel de usuario
		        JPanel userPanel = new JPanel();
		        userPanel.setLayout(null);
		        userPanel.setBounds(580, 20, 300, 80);
		        userPanel.setBackground(colorFondo);
		        add(userPanel);
		        
		        
		        String nombre = datosUsuario.getNombreCompleto();
		        userLabel = new JLabel(nombre);	        
		        userLabel.setFont(fuenteTextos.deriveFont(Font.PLAIN, 13));
		        userLabel.setForeground(colorBotones);
		        userLabel.setBounds(0, 5, 180, 40);
		        userPanel.add(userLabel);
		        
		        // Panel de saldo
		        JPanel saldoPanel = new JPanel();
		        saldoPanel.setLayout(null);
		        saldoPanel.setBounds(20, 20, 300, 120);
		        saldoPanel.setBackground(colorFondo);
		        add(saldoPanel);
		              
		        JLabel saldoLabel = new JLabel("Saldo disponible");	        
		        saldoLabel.setFont(fuenteTextos.deriveFont(Font.PLAIN, 16));
		        saldoLabel.setForeground(Color.YELLOW);
		        saldoLabel.setBounds(0, 5, 180, 30);
		        saldoPanel.add(saldoLabel);
		        
		        
		        double arsDisp = obtenerSaldoActivo("Peso Argentino");//Aqui me traigo de la lista de activos el monto de PESO ARGENTINO
		        double usdDisp = obtenerSaldoActivo("Dolar");//Aqui me traigo de la lista de activos el monto de DOLAR
		        JLabel saldoARS = new JLabel("AR$ " + arsDisp);
		        saldoARS.setFont(fuenteTextos.deriveFont(Font.BOLD, 14));
		        saldoARS.setForeground(Color.YELLOW);
		        saldoARS.setBounds(0, 40, 180, 30);
		        saldoPanel.add(saldoARS);
		        
		        JLabel saldoUSD = new JLabel("U$D " + usdDisp);
		        saldoUSD.setFont(fuenteTextos.deriveFont(Font.BOLD, 14));
		        saldoUSD.setForeground(Color.YELLOW);
		        saldoUSD.setBounds(0, 80, 180, 30);
		        saldoPanel.add(saldoUSD);

		        

		        // Modelo de la tabla

		        String[] columnNames = {"", "CRYPTO", "PRECIO (USD)"};
		     
		        tableModel = new DefaultTableModel(columnNames, 0) {
		            @Override
		            public boolean isCellEditable(int row, int column) {
		                return false; // Hacer las celdas no editables
		            }
		        };
		        table = new JTable(tableModel);
		        
		        

		       
		        

		        table.setRowHeight(55); // Ajustar altura de las filas
		        
		        JScrollPane scrollPane = new JScrollPane(table);
		        scrollPane.setBounds(20, 160, 550, 300); // Ajustada la tabla para que no esté tan arriba ni demasiado grande
		        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		        scrollPane.setBorder(BorderFactory.createLineBorder(colorBotones, 2));
		        scrollPane.getViewport().setBackground(Color.BLACK);
		        add(scrollPane);
		     // Cambiar la tipografía y color de las celdas
		        table.setFont(fuenteTextos.deriveFont(Font.PLAIN, 12)); // Cambiar la fuente de las celdas
		        table.setBackground(Color.decode("#171717")); // Cambiar el color de fondo de las celdas NEGRO CLARO
		        table.setForeground(Color.decode("#00f691")); // Cambiar el color del texto dentro de las celdas VERDEAGUA
		        // Cambiar el color de fondo de las cabeceras
		        table.getTableHeader().setBackground(colorBotones);
		        table.getTableHeader().setForeground(Color.decode("#171717")); // Cambiar el color del texto en las cabeceras

		        // Cambiar la tipografía de las cabeceras
		        table.getTableHeader().setFont(fuenteTextos.deriveFont(Font.PLAIN, 14)); // Cambiar la fuente de las cabeceras

		        // Deshabilitar el movimiento de las cabeceras
		        table.getTableHeader().setReorderingAllowed(false);
		        // Cambiar la alineación de la columna "Monto" a centrado
		        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		        table.getColumnModel().getColumn(0).setCellRenderer(new InstaladorUtilidades.ImageRenderer());


		        // Ajustar el tamaño de las columnas
		        table.getColumnModel().getColumn(0).setPreferredWidth(100); // Columna Logo (ajusta según el tamaño de tus imágenes)
		        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Columna Cripto
		        table.getColumnModel().getColumn(2).setPreferredWidth(300); // Columna Monto
		        // Quitar el relieve de las cabeceras
		        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Elimina el borde y relieve
		        table.getTableHeader().setOpaque(false); // Fondo transparente

		        // Eliminar que se pueda cambiar el tamaño de las columnas en la ventana
		        table.getTableHeader().setResizingAllowed(false);

		        // Quitar el relieve de las celdas
		        table.setBorder(BorderFactory.createEmptyBorder()); // Elimina el borde del JTable

		        // Cambiar el borde de las cabeceras a algo más moderno (opcional)
		        table.getTableHeader().setBorder(BorderFactory.createLineBorder(colorBotones, 2, true)); // Borde redondeado

		        
		     // Configurar renderers personalizados para las columnas correspondientes
		        table.getColumnModel().getColumn(0).setCellRenderer(new ComponentRenderer()); // Logos
		        

		           
		        // Panel para ubicar el botón (debajo de la tabla
	        
		        volverButton = new JButton("Volver");
		        volverButton.setFont(fuenteTextos.deriveFont(Font.PLAIN, 13));
		        volverButton.setBackground(colorBotones);
		        volverButton.setForeground(Color.BLACK);
		        volverButton.setOpaque(true);
		        volverButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		        volverButton.setFocusPainted(false);
		        volverButton.setBounds(350, 525, 100, 25);
		        add(volverButton);
		       
		        
		        
		        
		        
		        
		        
		        // Mostrar ventana
		        this.setVisible(true);

		        // Configurar el Timer para actualizar cada 5 segundos
		        this.gestor = new GestorDeTarea(this);
 

		      //botton volver--> utilizo CLASE ANONIMA
		        volverButton.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                volver();  // Llamada al método volver
		            }

		        });
		        
		        
		     
		        
		       //agrego funcion a los botones comprar
		        comprarBTCButton.addActionListener(new InicializarVistaCompraListener("BTC", gestor, datosUsuario, this));
		        comprarETHButton.addActionListener(new InicializarVistaCompraListener("ETH", gestor, datosUsuario, this));	
		        comprarUSDTButton.addActionListener(new InicializarVistaCompraListener("USDT", gestor, datosUsuario, this));	
		        comprarUSDCButton.addActionListener(new InicializarVistaCompraListener("USDC", gestor, datosUsuario, this));
		        comprarDOGEButton.addActionListener(new InicializarVistaCompraListener("DOGE", gestor, datosUsuario, this));
		    }

	    
      
	    
		    
	    /*este metodo permite silenciar vista actual y volver a login de usuario (cerrar sesion) */
	    private void cerrarSesion() {
	        // Cerrar o "silenciar" la vista actual
	        this.frame.setVisible(false);

	        // Crear una nueva instancia de la vista de inicio de sesión y mostrarla
	        VistaInicioSesion vistaInicioSesion = new VistaInicioSesion();
	        vistaInicioSesion.setVisible(true);
	    }

	    /*este metodo permite silenciar vista actual y volver a vistaDeInicio */
	    private void volver() {
	    	//detengo los pedidos a la API
	    	gestor.detenerTarea();
	    	
	        // Cerrar o "silenciar" la vista actual
	        this.setVisible(false);

	        // Crear una nueva instancia de la vista de inicio de sesión y mostrarla
	        VistaDeInicio vistaDeInicio = new VistaDeInicio(datosUsuario);
	        
	    }
	    
	    
	    
	    private double obtenerSaldoActivo(String nombreActivo) {
	    	
	    	List<Activo> activos = datosUsuario.getActivos();
	        for (Activo activo : activos) {
	            if (activo.getNomenclatura().equalsIgnoreCase(nombreActivo)) {
	                return activo.getCantidad(); // Retorna la cantidad si encuentra el activo
	            }
	        }
	        return 0.0; // Si no encuentra el activo, devuelve 0
	    }
	    
	 // Renderer para celdas que contengan componentes como JLabel y JButton
	    class ComponentRenderer implements TableCellRenderer {
	        @Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            if (value instanceof Component) {
	                return (Component) value; // Retorna el componente directamente
	            }
	            return new JLabel(value.toString()); // Maneja cualquier otro caso
	        }
	    }
	    
	    
	 //*Método que actualizará la tabla con los nuevos precios*/
 public void actualizarPrecios(List<PrecioCripto> precios) {
    // Limpiar la tabla antes de agregar los nuevos datos
    tableModel.setRowCount(0);

    // Agregar los datos a la tabla
    for (PrecioCripto precio : precios) {
        // Cargar el ícono de la criptomoneda
        ImageIcon imagen = InstaladorUtilidades.cargarIcono(precio.getNomenclatura(), 38); // Tamaño ajustado
        JLabel logoLabel = new JLabel(imagen); // Crear un JLabel con el ícono

        

        // Agregar la fila a la tabla
        tableModel.addRow(new Object[]{logoLabel, precio.getNomenclatura(), "$ " + precio.getPrecio()});
    }
}


       
	   
		
	    
	}

	

