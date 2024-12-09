package clasesListener;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import excepciones.ErrorExportarCSVException;
import utilidades.InstaladorUtilidades;

public class ExportarCSVListener implements ActionListener {
    private JTable tabla;

    public ExportarCSVListener(JTable tabla) {
        this.tabla = tabla;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Cambiar el Look and Feel antes de crear el JFileChooser
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  // Usa el look-and-feel nativo
            // O también puedes probar:
            // UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo CSV");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Personalizar el JFileChooser
        personalizarJFileChooser(fileChooser);
        
        fileChooser.setSelectedFile(new File("datos.csv"));
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Asegúrate de que el archivo tenga la extensión .csv
            if (!fileToSave.getName().toLowerCase().endsWith(".csv")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
            }

            try (FileWriter writer = new FileWriter(fileToSave)) {
                // Escribir los nombres de las columnas (ignorar la primera columna)
                for (int i = 1; i < tabla.getColumnCount(); i++) { // Empieza en 1 para omitir la columna de índice 0
                    writer.write(tabla.getColumnName(i) + (i == tabla.getColumnCount() - 1 ? "\n" : ","));
                }

                // Escribir los datos fila por fila (ignorar la primera columna)
                for (int i = 0; i < tabla.getRowCount(); i++) {
                    for (int j = 1; j < tabla.getColumnCount(); j++) { // Empieza en 1 para omitir la columna de índice 0
                        Object cellValue = tabla.getValueAt(i, j);
                        writer.write(cellValue.toString() + (j == tabla.getColumnCount() - 1 ? "\n" : ","));
                    }
                }

                JOptionPane.showMessageDialog(null, "Datos exportados con éxito.");
            } catch (IOException ex) {
            	// Lanzo excepción personalizada con un mensaje significativo
                try {
                    throw new ErrorExportarCSVException("Error al exportar los datos: " + ex.getMessage());
                } catch (ErrorExportarCSVException customEx) {
                    JOptionPane.showMessageDialog(null, customEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(null, "Error al exportar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
        


    private void personalizarJFileChooser(JFileChooser fileChooser) {
    	
    	Font fuenteTextos = InstaladorUtilidades.cargarFuente("recursos/Conthrax-SemiBold.otf");
        Color colorBase = Color.decode("#00f691");
        // Esto ya no cambia mucho a nivel de "estética" del JFileChooser
        fileChooser.setFont(new Font("Arial", Font.PLAIN, 14));
        fileChooser.setBackground(Color.decode("#171717"));
        UIManager.put("FileChooser.foreground", Color.WHITE);
        UIManager.put("FileChooser.background", Color.decode("#171717"));
        UIManager.put("Button.background", Color.decode("#2E2E2E"));
        UIManager.put("Button.foreground", Color.WHITE);
    }
}
