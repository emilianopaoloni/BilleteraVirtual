package modelo_clases;
import java.util.Comparator;

	public class ComparadorMoneda implements Comparator<Moneda> {
		
		public enum Criterio {VALOR_EN_DOLAR,CANTIDAD,NOMENCLATURA;}
		
		 private Criterio criterio;
		 
		 public ComparadorMoneda(Criterio criterio) {
		        this.criterio = criterio;
		    }
		 
		 public int compare(Moneda m1, Moneda m2) {
			 switch(criterio) {
			 case NOMENCLATURA:
				 return m1.getNomenclatura().compareTo(m2.getNomenclatura());
			 case VALOR_EN_DOLAR:
				 return Double.compare(m2.getValorEnDolar(),m1.getValorEnDolar());
			 case CANTIDAD:
				 return Double.compare(m2.getStock(),m1.getStock());
			 default:
			 	 return 1;
			 }
		 }
	}