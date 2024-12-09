package utilidades;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class InstaladorUtilidades {
	
	public static Font cargarFuente(String rutaFuente) {
        try {
            InputStream is = InstaladorUtilidades.class.getClassLoader().getResourceAsStream(rutaFuente);
            if (is == null) {
                System.err.println("No se encuentra el archivo de fuente: " + rutaFuente);
                return new Font("Arial", Font.PLAIN, 12); // Fuente por defecto
            }
            return Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, 12); // Fuente por defecto
        }
    }

    /**
     * Carga una imagen desde un archivo ubicado en el directorio de recursos.
     * 
     * @param rutaImagen Ruta relativa del archivo de la imagen.
     * @return La imagen cargada o null en caso de error.
     */
    public static Image cargarImagen(String rutaImagen) {
        try {
            URL imgUrl = InstaladorUtilidades.class.getClassLoader().getResource(rutaImagen);
            if (imgUrl == null) {
                System.err.println("No se encuentra el archivo de imagen: " + rutaImagen);
                return null;
            }
            return ImageIO.read(imgUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    
    public static ImageIcon cargarIcono(String nomC, int alto) {
        String rutaImagen = "recursos/logotipos_monedas/" + nomC + ".png";
        URL imgUrl = InstaladorUtilidades.class.getClassLoader().getResource(rutaImagen);
        if (imgUrl == null) {
            System.err.println("No se encuentra el archivo: " + rutaImagen);
            return null;
        } else {
            try {
                Image img = ImageIO.read(imgUrl);
                if (alto > 0) { // Si se especificó un alto, ajusta proporcionalmente
                    int nuevoAncho = (img.getWidth(null) * alto) / img.getHeight(null);
                    Image scaledImg = img.getScaledInstance(nuevoAncho, alto, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaledImg);
                } else {
                    // Devuelve la imagen en su tamaño original
                    return new ImageIcon(img);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
    
    public static class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JLabel) {
                JLabel label = (JLabel) value;
                return label; // Retorna directamente el JLabel con la imagen
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

}
