package servicios;

import modelo.Jugador;

public class GestorRanking {

    private static class NodoRanking {
        int puntaje;
        String nombre;
        int aciertos;
        int fallos;
        int posicionFinal;
        NodoRanking izquierdo;
        NodoRanking derecho;

        NodoRanking(Jugador jugador) {
            this.puntaje = jugador.calcularPuntaje();
            this.nombre = jugador.getNombre();
            this.aciertos = jugador.getAciertos();
            this.fallos = jugador.getFallos();
            this.posicionFinal = jugador.getPosicion();
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    private NodoRanking raiz;

    public GestorRanking() {
        this.raiz = null;
    }

    public void insertarJugador(Jugador jugador) {
        raiz = insertarRecursivo(raiz, jugador);
    }

    private NodoRanking insertarRecursivo(NodoRanking nodo, Jugador jugador) {
        if (nodo == null)
            return new NodoRanking(jugador);
        int puntaje = jugador.calcularPuntaje();
        if (puntaje <= nodo.puntaje) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, jugador);
        } else {
            nodo.derecho = insertarRecursivo(nodo.derecho, jugador);
        }
        return nodo;
    }

    public void imprimirRanking() {
        System.out.println();
        System.out.println("===========================================");
        System.out.println("         TABLA DE POSICIONES FINAL         ");
        System.out.println("===========================================");
        System.out.printf("%-3s %-15s %-10s %-10s %-10s%n",
                "#", "Jugador", "Puntaje", "Aciertos", "Fallos");
        System.out.println("-------------------------------------------");
        int[] contador = { 1 };
        recorrerDescendente(raiz, contador);
        System.out.println("===========================================");
    }

    private void recorrerDescendente(NodoRanking nodo, int[] posicion) {
        if (nodo == null)
            return;
        recorrerDescendente(nodo.derecho, posicion);
        System.out.printf("%-3d %-15s %-10d %-10d %-10d%n",
                posicion[0]++, nodo.nombre, nodo.puntaje,
                nodo.aciertos, nodo.fallos);
        recorrerDescendente(nodo.izquierdo, posicion);
    }
}
