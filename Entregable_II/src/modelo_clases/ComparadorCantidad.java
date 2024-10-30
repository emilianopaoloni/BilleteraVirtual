package modelo_clases;

import java.util.Comparator;
public class ComparadorCantidad implements Comparator<Activo> {
	
	public int compare(Activo a1, Activo a2) {
		return Double.compare(a2.getCantidad(), a1.getCantidad());			
	}

}