package modelo;

public class Jugador {

    private String nombre;
    private int posicion;
    private int aciertos;
    private int fallos;
    private boolean esMaquina;
    private boolean saltarTurno;

    public Jugador(String nombre, boolean esMaquina) {
        this.nombre = nombre;
        this.posicion = 1;
        this.aciertos = 0;
        this.fallos = 0;
        this.esMaquina = esMaquina;
        this.saltarTurno = false;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getAciertos() {
        return aciertos;
    }

    public int getFallos() {
        return fallos;
    }

    public boolean esMaquina() {
        return esMaquina;
    }

    public boolean debeSaltarTurno() {
        return saltarTurno;
    }

    public void setSaltarTurno(boolean saltarTurno) {
        this.saltarTurno = saltarTurno;
    }

    public void registrarAcierto() {
        aciertos++;
    }

    public void registrarFallo() {
        fallos++;
    }

    public int calcularPuntaje() {
        return (aciertos * 10) - (fallos * 5);
    }

    @Override
    public String toString() {
        return nombre + " [pos=" + posicion + ", aciertos=" + aciertos + ", fallos=" + fallos + "]";
    }
}
