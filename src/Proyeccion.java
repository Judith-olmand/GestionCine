public class Proyeccion {
    private String titulo;
    private double precioBase;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public Proyeccion(String titulo, double precioBase) {
        this.titulo = titulo;
        this.precioBase = precioBase;
    }

    public double calcularPrecio(){
        return precioBase;
    }
}
