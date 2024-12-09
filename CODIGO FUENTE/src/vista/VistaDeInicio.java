package vista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import clasesListener.ExportarCSVListener;
import clasesListener.GenerarDatosPruebaListener;
import configuracion.DatosUsuario;
import modelo_clases.Activo;
import modelo_clases.ComparadorActivo;
import modelo_clases.Transaccion;
import utilidades.InstaladorUtilidades;
import java.util.List;
import java.util.LinkedList;

public class VistaDeInicio extends JFrame {

    private JLabel balanceLabel1, balanceLabel2;
    private JTable Activostable;
    private JButton exportButton;
    private JButton misOperacionesButton;
    private JButton cotizacionesButton;
    private JButton generarDatosPruebaButton;
    private JLabel userName;
    private JButton logoutButton;
    private DatosUsuario datosUsuario; // Objeto con datos del usuario ACTUAL
    private Font fuenteTextos; // Fuente personalizada
    

    public VistaDeInicio(DatosUsuario datosUsuario) {
    	//super("Billetera Virtual - Mis Activos");
        this.datosUsuario = datosUsuario;

        // Cargar la fuente personalizada
        fuenteTextos = InstaladorUtilidades.cargarFuente("recursos/Conthrax-SemiBold.otf");

        // Crear ventana principal
        setTitle("Billetera Virtual - Mis Activos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(null);
        setResizable(false);
        
        // Reutilizar cargarImagen para establecer el ícono de la ventana
        ImageIcon icono = InstaladorUtilidades.cargarIcono("logoapp1",60); // Ajusta el nombre según corresponda
        if (icono != null) {
            setIconImage(icono.getImage()); // Usar la imagen del ImageIcon
        }

        // Establecer colores
        Color colorFondo = new Color(0, 10, 10); // Fondo negro un poco más claro
        Color colorBotones = Color.decode("#00f691");; // Verde brillante
        //Color colorTexto = new Color(255, 255, 255); // Blanco para el texto

        getContentPane().setBackground(colorFondo);

        // Etiqueta de balance
        balanceLabel1 = new JLabel("Balance:");
        balanceLabel1.setFont(fuenteTextos.deriveFont(Font.BOLD, 20));
        balanceLabel1.setForeground(Color.YELLOW);
        balanceLabel1.setBounds(20, 40, 350, 30);
        add(balanceLabel1);
        
        balanceLabel2 = new JLabel("AR$ " + datosUsuario.getBalanceTotal());
        balanceLabel2.setFont(fuenteTextos.deriveFont(Font.BOLD, 20));
        balanceLabel2.setForeground(Color.YELLOW);
        balanceLabel2.setBounds(20, 70, 400, 30);
        add(balanceLabel2);

        
     // Panel de usuario
        JPanel userPanel = new JPanel();
        userPanel.setLayout(null);
        userPanel.setBounds(580, 20, 300, 80);
        userPanel.setBackground(colorFondo);
        add(userPanel);

        //JLabel userIcon = new JLabel(new ImageIcon("user_icon.png")); // Imagen de ejemplo
        //userIcon.setBounds(10, 10, 50, 50);
        //userPanel.add(userIcon);

        String nombre = datosUsuario.getNombreCompleto();
        userName = new JLabel(nombre);
        userName.setFont(fuenteTextos.deriveFont(Font.PLAIN, 13));
        userName.setForeground(colorBotones);
        userName.setBounds(0, 5, 180, 40);  // Ajusta la posición dentro del panel
        userPanel.add(userName);
        
        logoutButton = new JButton("Cerrar sesión");
        logoutButton.setFont(fuenteTextos.deriveFont(Font.PLAIN, 12));
        logoutButton.setBackground(colorBotones);
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setOpaque(true);
        logoutButton.setBorder(BorderFactory.createLineBorder(colorFondo, 2, true));
        logoutButton.setFocusPainted(false);
        logoutButton.setBounds(0, 50, 130, 20);
        userPanel.add(logoutButton);
        
        //boton generar datos de prueba
        generarDatosPruebaButton = new JButton("Generar datos de prueba");
        generarDatosPruebaButton.setFont(fuenteTextos.deriveFont(Font.PLAIN, 10));
        generarDatosPruebaButton.setBackground(colorBotones);
        generarDatosPruebaButton.setForeground(Color.BLACK);
        generarDatosPruebaButton.setOpaque(true);
        generarDatosPruebaButton.setBorder(BorderFactory.createLineBorder(colorFondo, 2, true));
        generarDatosPruebaButton.setFocusPainted(false);
        generarDatosPruebaButton.setBounds(550, 480, 200, 25);
        add(generarDatosPruebaButton);
        
        
     // Tabla
        String[] columnNames = {"", "MONEDA", "CANTIDAD"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        Activostable = new JTable(tableModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        Activostable.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(Activostable);
        scrollPane.setBounds(20, 130, 750, 300); // Ajustada la tabla para que no esté tan arriba ni demasiado grande
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(colorBotones, 2));
        scrollPane.getViewport().setBackground(Color.BLACK);
        add(scrollPane);

        // Cambiar la tipografía y color de las celdas
        Activostable.setFont(fuenteTextos.deriveFont(Font.PLAIN, 12)); // Cambiar la fuente de las celdas
        Activostable.setBackground(Color.decode("#171717")); // Cambiar el color de fondo de las celdas NEGRO CLARO
        Activostable.setForeground(Color.decode("#00f691")); // Cambiar el color del texto dentro de las celdas VERDEAGUA
        // Cambiar el co-lor de fondo de las cabeceras
        Activostable.getTableHeader().setBackground(colorBotones);
        Activostable.getTableHeader().setForeground(Color.decode("#171717")); // Cambiar el color del texto en las cabeceras

        // Cambiar la tipografía de las cabeceras
        Activostable.getTableHeader().setFont(fuenteTextos.deriveFont(Font.PLAIN, 14)); // Cambiar la fuente de las cabeceras

        // Deshabilitar el movimiento de las cabeceras
        Activostable.getTableHeader().setReorderingAllowed(false);

        // Agregar datos a la tabla
        List<Activo> activos = new LinkedList<>(datosUsuario.getActivos());

     // Crear el ComparadorActivo con el criterio CANTIDAD (o NOMENCLATURA si prefieres)
        ComparadorActivo comparador = new ComparadorActivo(ComparadorActivo.Criterio.CANTIDAD);

        // Ordenar la lista de activos
        activos.sort(comparador);

        // Ahora agregar los activos ordenados al modelo de la tabla
        for (Activo activo : activos) { String nomC = activo.getNomenclatura(); // Nombre del activo (ej., "Bitcoin") 
        double monto = activo.getCantidad(); // Monto del activo 
        ImageIcon imagen = InstaladorUtilidades.cargarIcono(nomC,38); // Cargar la imagen correspondiente 
        tableModel.addRow(new Object[]{new JLabel(imagen), nomC, monto}); // Agregar la imagen y los datos a la tabla }
        }
        

        // Cambiar la alineación de la columna "Monto" a centrado
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        Activostable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        Activostable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        Activostable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        Activostable.getColumnModel().getColumn(0).setCellRenderer(new InstaladorUtilidades.ImageRenderer());


        // Ajustar el tamaño de las columnas
        Activostable.getColumnModel().getColumn(0).setPreferredWidth(150); // Columna Logo (ajusta según el tamaño de tus imágenes)
        Activostable.getColumnModel().getColumn(1).setPreferredWidth(300); // Columna Cripto
        Activostable.getColumnModel().getColumn(2).setPreferredWidth(300); // Columna Monto

        // Quitar el relieve de las cabeceras
        Activostable.getTableHeader().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Elimina el borde y relieve
        Activostable.getTableHeader().setOpaque(false); // Fondo transparente

        // Eliminar que se pueda cambiar el tamaño de las columnas en la ventana
        Activostable.getTableHeader().setResizingAllowed(false);

        // Quitar el relieve de las celdas
        Activostable.setBorder(BorderFactory.createEmptyBorder()); // Elimina el borde del JTable

        // Cambiar el borde de las cabeceras a algo más moderno (opcional)
        Activostable.getTableHeader().setBorder(BorderFactory.createLineBorder(colorBotones, 2, true)); // Borde redondeado

        // Ajustar el tamaño de las filas
        Activostable.setRowHeight(40); // Ajusta la al



        
        // Botón "Exportar como CSV"
        exportButton = new JButton("Exportar como CSV");
        exportButton.setFont(fuenteTextos.deriveFont(Font.PLAIN, 13));
        exportButton.setBackground(colorBotones);
        exportButton.setForeground(Color.BLACK);
        exportButton.setOpaque(true);
        exportButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        exportButton.setFocusPainted(false);
        exportButton.setBounds(300, 525, 200, 25);
        add(exportButton);

        // Botones inferiores
        misOperacionesButton = new JButton("Mis Operaciones");
        misOperacionesButton.setFont(fuenteTextos.deriveFont(Font.PLAIN, 13));
        misOperacionesButton.setBackground(colorBotones);
        misOperacionesButton.setForeground(Color.BLACK);
        misOperacionesButton.setOpaque(true);
        misOperacionesButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        misOperacionesButton.setFocusPainted(false);
        misOperacionesButton.setBounds(50, 525, 200, 25);
        add(misOperacionesButton);

        cotizacionesButton = new JButton("Cotizaciones");
        cotizacionesButton.setFont(fuenteTextos.deriveFont(Font.PLAIN, 13));
        cotizacionesButton.setBackground(colorBotones);
        cotizacionesButton.setForeground(Color.BLACK);
        cotizacionesButton.setOpaque(true);
        cotizacionesButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        cotizacionesButton.setFocusPainted(false);
        cotizacionesButton.setBounds(550, 525, 200, 25);
        add(cotizacionesButton);

        

        // Mostrar ventana
        setVisible(true);

        // Asociar listeners a los botones
        this.exportButton.addActionListener(new ExportarCSVListener(Activostable));
        this.generarDatosPruebaButton.addActionListener(new GenerarDatosPruebaListener(datosUsuario, this));
        this.misOperacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irMisOperaciones();
            }
        });

        this.cotizacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irCotizaciones();
            }
        });

        this.logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
    }
    



    private void irMisOperaciones() {
        this.setVisible(false);
        VistaMisOperaciones vistaMisOperaciones = new VistaMisOperaciones(this.datosUsuario);
    }

    private void irCotizaciones() {
        this.setVisible(false);
        VistaCotizaciones vistaCotizaciones = new VistaCotizaciones(this.datosUsuario);
    }

    private void cerrarSesion() {
        this.setVisible(false);
        VistaInicioSesion vistaInicioSesion = new VistaInicioSesion();
        vistaInicioSesion.setVisible(true);
    }

    
    
}
