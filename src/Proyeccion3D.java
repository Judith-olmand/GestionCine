public class Proyeccion3D extends Proyeccion{
    private double recargo = 1.30;

    public double getRecargo() {
        return recargo;
    }

    public Proyeccion3D(String titulo, double precioBase) {
        super(titulo, precioBase);
    }

    public double calcularPrecio(){
        return super.calcularPrecio() * recargo;
    }
}
