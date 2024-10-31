package modelo_clases;
import java.util.Comparator;


	public class ComparadorActivo implements Comparator<Activo> {
		
		public enum Criterio {CANTIDAD,NOMENCLATURA}
		
		 private Criterio criterio;
		 // Se le asigna a la clase un criterio de ordenacion el cual se usara en el metodo compare
		 public ComparadorActivo(Criterio criterio) {
		        this.criterio = criterio;
		    }
		 
		 public int compare(Activo a1, Activo a2) {
			 switch(criterio) {
			 case NOMENCLATURA:
				 return a1.getNomenclatura().compareTo(a2.getNomenclatura());
			 case CANTIDAD:
				 return Double.compare(a2.getCantidad(),a1.getCantidad());
			 default:
			 	 return 1;
			 }
		 }
	}