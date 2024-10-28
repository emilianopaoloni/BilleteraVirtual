package modelo_clases;


public class Moneda {

	     private String tipo;
	     private String nombre;
		 private String nomenclatura;
		 private double valorEnDolar;
		 private double stock;
		 private int volatilidad;
		 
		//constructor para moneda cripto
		public Moneda(String tipo, String nombre, String nomenclatura, double valorEnDolar, double stock, int volatilidad) {
			this.tipo = tipo;
			this.nombre = nombre;
			this.nomenclatura = nomenclatura;
			this.valorEnDolar = valorEnDolar;
			this.stock = stock;
			this.volatilidad= volatilidad;
		}
		
		//constructor para moneda fiat (no lleva volatilidad, ni stock)
		public Moneda(String tipo, String nombre, String nomenclatura, double valorEnDolar) {
			this.tipo = tipo;
			this.nombre = nombre;
			this.nomenclatura = nomenclatura;
			this.valorEnDolar = valorEnDolar;
			this.stock = -1;
			this.volatilidad= -1;
		}
		
	
		
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getNomenclatura() {
			return nomenclatura;
		}
		public void setNomenclatura(String nomenclatura) {
			this.nomenclatura = nomenclatura;
		}
		public double getValorEnDolar() {
			return valorEnDolar;
		}
		public void setValorEnDolar(double valorEnDolar) {
			this.valorEnDolar = valorEnDolar;
		}
		public double getStock() {
			return stock;
		}
		public void setStock(double stock) {
			this.stock = stock;
		}
		public int getVolatilidad() {
			return volatilidad;
		}
		public void setVolatilidad(int volatilidad) {
			this.volatilidad = volatilidad;
		}

		 
		 
}
