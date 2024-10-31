package modelo_clases;

public class Swap {

	 private String monedaAcambiar;
	    private double montoAcambiar;
	    private String monedaEsperada;

	    // Constructor
	    public Swap(String monedaAcambiar, double montoAcambiar, String monedaEsperada) {
	        this.monedaAcambiar = monedaAcambiar;
	        this.montoAcambiar = montoAcambiar;
	        this.monedaEsperada = monedaEsperada;
	    }

	    // Getters
	    public String getMonedaAcambiar() {
	        return monedaAcambiar;
	    }

	    public double getMontoAcambiar() {
	        return montoAcambiar;
	    }

	    public String getMonedaEsperada() {
	        return monedaEsperada;
	    }

	    // Setters
	    public void setMonedaAcambiar(String monedaAcambiar) {
	        this.monedaAcambiar = monedaAcambiar;
	    }

	    public void setMontoAcambiar(double montoAcambiar) {
	        this.montoAcambiar = montoAcambiar;
	    }

	    public void setMonedaEsperada(String monedaEsperada) {
	        this.monedaEsperada = monedaEsperada;
	    }
}
