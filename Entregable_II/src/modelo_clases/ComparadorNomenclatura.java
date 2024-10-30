package modelo_clases;
import java.util.Comparator;

	public class ComparadorNomenclatura implements Comparator<Activo> {
		
		public int compare(Activo a1, Activo a2) {
			return a1.getNomenclatura().compareTo(a2.getNomenclatura());			
		}

	}


