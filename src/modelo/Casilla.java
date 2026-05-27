package modelo;

public class Casilla {

    private int numero;
    private TipoCasilla tipo;
    private int destino;

    public Casilla(int numero, TipoCasilla tipo, int destino) {
        this.numero = numero;
        this.tipo = tipo;
        this.destino = destino;
    }

    public int getNumero() {
        return numero;
    }

    public TipoCasilla getTipo() {
        return tipo;
    }

    public int getDestino() {
        return destino;
    }

    public boolean tieneDestino() {
        return destino != -1;
    }

    @Override
    public String toString() {
        return "Casilla{numero=" + numero + ", tipo=" + tipo + ", destino=" + destino + "}";
    }
}
