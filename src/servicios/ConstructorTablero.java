package servicios;

import estructuras.Grafo;
import estructuras.ListaEnlazada;
import modelo.Casilla;
import modelo.TipoCasilla;

public class ConstructorTablero {

    private static final int TOTAL_CASILLAS = 50;

    private final int[] escaleraOrigen = { 4, 9, 20, 28, 40 };
    private final int[] escaleraDestino = { 14, 31, 38, 44, 47 };

    private final int[] serpienteOrigen = { 17, 26, 32, 45, 49 };
    private final int[] serpienteDestino = { 7, 13, 19, 36, 42 };

    private final int[] casillasReto = { 6, 12, 18, 24, 30, 35, 43 };

    public ListaEnlazada<Casilla> construirTablero() {
        ListaEnlazada<Casilla> tablero = new ListaEnlazada<>();

        for (int numero = 1; numero <= TOTAL_CASILLAS; numero++) {
            TipoCasilla tipo = TipoCasilla.NORMAL;
            int destino = -1;

            for (int i = 0; i < escaleraOrigen.length; i++) {
                if (numero == escaleraOrigen[i]) {
                    tipo = TipoCasilla.ESCALERA;
                    destino = escaleraDestino[i];
                    break;
                }
            }

            if (tipo == TipoCasilla.NORMAL) {
                for (int i = 0; i < serpienteOrigen.length; i++) {
                    if (numero == serpienteOrigen[i]) {
                        tipo = TipoCasilla.SERPIENTE;
                        destino = serpienteDestino[i];
                        break;
                    }
                }
            }

            if (tipo == TipoCasilla.NORMAL) {
                for (int casilla : casillasReto) {
                    if (numero == casilla) {
                        tipo = TipoCasilla.RETO;
                        break;
                    }
                }
            }

            tablero.agregar(new Casilla(numero, tipo, destino));
        }
        return tablero;
    }

    public Grafo construirGrafo() {
        Grafo grafo = new Grafo();

        for (int i = 0; i < escaleraOrigen.length; i++) {
            grafo.agregarArista(escaleraOrigen[i], escaleraDestino[i],
                    "ESCALERA");
        }

        for (int i = 0; i < serpienteOrigen.length; i++) {
            grafo.agregarArista(serpienteOrigen[i], serpienteDestino[i],
                    "SERPIENTE");
        }

        return grafo;
    }

    public Casilla obtenerCasilla(ListaEnlazada<Casilla> tablero, int numero) {
        for (int i = 0; i < tablero.tamano(); i++) {
            Casilla casilla = tablero.obtener(i);
            if (casilla != null && casilla.getNumero() == numero) {
                return casilla;
            }
        }
        return null;
    }

    public int getTotalCasillas() {
        return TOTAL_CASILLAS;
    }
}
