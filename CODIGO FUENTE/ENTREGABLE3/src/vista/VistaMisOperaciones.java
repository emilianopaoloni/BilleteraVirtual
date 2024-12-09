package vista;

import configuracion.DatosUsuario;

import modelo_clases.Activo;
import modelo_clases.Transaccion;
import utilidades.InstaladorUtilidades;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class VistaMisOperaciones {

    private JFrame frame;
    private JTable transaccionesTable;
    private JButton volverButton;
    private DatosUsuario datosUsuario;

    public VistaMisOperaciones(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;

        // Crear el marco principal
        frame = new JFrame("Historial de Transacciones");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600); // Más alto que ancho
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.BLACK); // Fondo negro
        frame.setResizable(false);
        
       // Reutilizar cargarImagen para establecer el ícono de la ventana
        ImageIcon icono = InstaladorUtilidades.cargarIcono("logoapp1",60); // Ajusta el nombre según corresponda
        if (icono != null) {
            frame.setIconImage(icono.getImage()); // Usar la imagen del ImageIcon
        }


        // Cargar la fuente personalizada
        Font fuenteTextos = InstaladorUtilidades.cargarFuente("recursos/Conthrax-SemiBold.otf");
        Color colorBase = Color.decode("#00f691");
        
        // Configurar la tabla
        String[] columnNames = {"Resumen", "Fecha"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        transaccionesTable = new JTable(tableModel);
        transaccionesTable.setFont(fuenteTextos.deriveFont(Font.PLAIN, 9));
        transaccionesTable.setBackground(Color.decode("#171717")); // Fondo de la tabla negro
        transaccionesTable.setForeground(Color.WHITE); // Texto blanco
        transaccionesTable.getTableHeader().setFont(fuenteTextos.deriveFont(Font.BOLD, 12));
        transaccionesTable.getTableHeader().setBackground(colorBase);
        transaccionesTable.getTableHeader().setForeground(Color.decode("#171717")); // Cambiar el color del texto en las cabeceras
        transaccionesTable.getTableHeader().setBorder(BorderFactory.createLineBorder(colorBase, 2, true));
        transaccionesTable.setRowHeight(30);
     // Deshabilitar el movimiento de las cabeceras
        transaccionesTable.getTableHeader().setReorderingAllowed(false);
        transaccionesTable.getTableHeader().setResizingAllowed(false);
        transaccionesTable.setBorder(BorderFactory.createEmptyBorder());
        transaccionesTable.getTableHeader().setBorder(BorderFactory.createLineBorder(colorBase, 2, true));
        transaccionesTable.getColumnModel().getColumn(0).setPreferredWidth(250); // "Resumen"
        transaccionesTable.getColumnModel().getColumn(1).setPreferredWidth(100); // "Fecha"
        // Llenar la tabla con datos
        for (Transaccion transaccion : datosUsuario.getTransacciones()) {
            String resumen = transaccion.getResumen();
            String fecha = transaccion.getFechaYhora();
            tableModel.addRow(new Object[]{resumen, fecha});
        }

        // Alinear el texto al centro en las celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        transaccionesTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        // Agregar la tabla a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(transaccionesTable);
        scrollPane.setBounds(20, 20, 545, 470);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setBorder(BorderFactory.createLineBorder(colorBase, 2));
        scrollPane.getViewport().setBackground(Color.BLACK);

        // Botón "Volver"
        volverButton = new JButton("Volver");
        volverButton.setFont(fuenteTextos.deriveFont(Font.PLAIN, 13));
        volverButton.setBackground(colorBase);
        volverButton.setForeground(Color.BLACK);
        volverButton.setBounds(240, 500, 120, 30);
        volverButton.addActionListener(e -> volverAInicio());

        // Agregar componentes al marco
        frame.add(scrollPane);
        frame.add(volverButton);

        // Hacer visible el marco
        frame.setVisible(true);
    }

    private void volverAInicio() {
        frame.setVisible(false);
        new VistaDeInicio(this.datosUsuario);
    }
	    
 /*public static void main(String[] args) {
    	// Crear los activos
        Activo activo1 = new Activo("Bitcoin", 1.5);  // Nombre: "Bitcoin", Cantidad: 1.5
        Activo activo2 = new Activo("Ethereum", 600.3); // Nombre: "Ethereum", Cantidad: 6.3
        Activo activo3 = new Activo("Dogecoin", 24.0);  // Nombre: "Dogecoin", Cantidad: 24.0
        Activo activo4 = new Activo("Peso Argentino", 34500.0);  // Nombre: "Tether", Cantidad: 34.0
        //Activo activo5 = new Activo("Litecoin", 10.2); // Nombre: "Litecoin", Cantidad: 10.2
        //Activo activo6 = new Activo("Ripple", 5000);   // Nombre: "Ripple", Cantidad: 5000
        //Activo activo7 = new Activo("Cardano", 150);   // Nombre: "Cardano", Cantidad: 150

        // Crear la lista de activos
        LinkedList<Activo> listaActivos = new LinkedList<>();
        listaActivos.add(activo1);
        listaActivos.add(activo2);
        listaActivos.add(activo3);
        listaActivos.add(activo4);
        //listaActivos.add(activo5);
        //listaActivos.add(activo6);
        //listaActivos.add(activo7);

        
        Transaccion t1 = new Transaccion("Compra 0.123 BTC con 500 ARS", "6/12/2024 21:31"); 
        Transaccion t2 = new Transaccion("Compra 500 ETH", "17/8/2015 15:33"); 
        // Crear los otros parámetros requeridos (puedes dejar los valores según tus necesidades)
        LinkedList<Transaccion> listaTransacciones = new LinkedList<>();  // Suponiendo que no tienes transacciones para este ejemplo
        listaTransacciones.add(t1);
        listaTransacciones.add(t2);
        
        double sumaActivos = activo1.getCantidad() + activo2.getCantidad() + activo3.getCantidad() + activo4.getCantidad();// + 
                             //activo5.getCantidad() + activo6.getCantidad() + activo7.getCantidad(); // Suma de activos como ejemplo

        // Crear el objeto DatosUsuario con la lista de activos y otros parámetros
        DatosUsuario datosUsuario = new DatosUsuario(7, "Usuario nombre", listaActivos, listaTransacciones, sumaActivos);

        // Pasar los datos al constructor de VistaDeInicio
        new VistaMisOperaciones(datosUsuario);
        				
    }
*/
	

	 
	
}
