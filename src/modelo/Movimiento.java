package modelo;

public class Movimiento {

    private String nombreJugador;
    private int posicionAnterior;
    private int posicionNueva;
    private int valorDado;
    private String descripcion;

    public Movimiento(String nombreJugador, int posicionAnterior, int posicionNueva,
            int valorDado, String descripcion) {
        this.nombreJugador = nombreJugador;
        this.posicionAnterior = posicionAnterior;
        this.posicionNueva = posicionNueva;
        this.valorDado = valorDado;
        this.descripcion = descripcion;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public int getPosicionAnterior() {
        return posicionAnterior;
    }

    public int getPosicionNueva() {
        return posicionNueva;
    }

    public int getValorDado() {
        return valorDado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return nombreJugador + ": casilla " + posicionAnterior + " -> " + posicionNueva
                + " (dado=" + valorDado + ") | " + descripcion;
    }
}
