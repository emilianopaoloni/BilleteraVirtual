package modelo_clases;

public class Activo {
		private String nomenclatura;
		private double cantidad;
		
		public Activo() {
			
		}
		
		public Activo(String nomenclatura, double cantidad) {
			super();
			this.nomenclatura = nomenclatura;
			this.cantidad = cantidad;
		}
		public String getNomenclatura() {
			return nomenclatura;
		}
		public void setNomenclatura(String nomenclatura) {
			this.nomenclatura = nomenclatura;
		}
		public double getCantidad() {
			return cantidad;
		}
		public void setCantidad(double cantidad) {
			this.cantidad = cantidad;
		}
}
