package modelo_clases;

/*esta clase sirve para almacenar los precios de las criptos cuando se los busca en la URL */
public class PrecioCripto {
    private String nomenclatura;
    private double precio;

    // Constructor para inicializar todos los atributos
    public PrecioCripto(String nomenclatura, double precio) {
        this.nomenclatura = nomenclatura;
        this.precio = precio;
    }

    // Getter y Setter para nombre
    public String getNomenclatura() {
        return nomenclatura;
    }

    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    // Getter y Setter para precio
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
