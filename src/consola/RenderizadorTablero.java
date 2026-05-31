package consola;

import estructuras.ListaEnlazada;
import estructuras.TablaHash;
import modelo.Casilla;
import modelo.Jugador;

public class RenderizadorTablero {

    private static final int COLUMNAS = 10;

    /**
     * Dibuja el tablero completo en consola con posiciones de todos los jugadores.
     * Complejidad: O(n * j)
     */
    public void imprimirTablero(ListaEnlazada<Casilla> tablero,
            TablaHash<String, Jugador> estadoJugadores,
            int meta) {
        int totalCasillas = tablero.tamano();
        int filas = (int) Math.ceil((double) totalCasillas / COLUMNAS);

        System.out.println();
        System.out.println("==================== TABLERO ====================");
        System.out.println("  [E]=Escalera  [S]=Serpiente  [R]=Reto  [M]=Meta");
        System.out.println("=================================================");

        // Recorrer de arriba hacia abajo
        for (int fila = filas - 1; fila >= 0; fila--) {
            boolean filaInversa = (fila % 2 == 0);

            // Primera linea: numeros de casilla
            System.out.print("|");
            for (int col = 0; col < COLUMNAS; col++) {
                int indiceColumna = filaInversa ? (COLUMNAS - 1 - col) : col;
                int numeroCasilla = fila * COLUMNAS + indiceColumna + 1;
                if (numeroCasilla > totalCasillas) {
                    System.out.print("          |");
                } else {
                    Casilla casilla = tablero.obtener(numeroCasilla - 1);
                    String tipo = obtenerSimboloTipo(casilla, meta);
                    System.out.printf(" %2d%-3s    |", numeroCasilla, tipo);
                }
            }
            System.out.println();

            // Segunda linea: jugadores en cada casilla
            System.out.print("|");
            for (int col = 0; col < COLUMNAS; col++) {
                int indiceColumna = filaInversa ? (COLUMNAS - 1 - col) : col;
                int numeroCasilla = fila * COLUMNAS + indiceColumna + 1;
                if (numeroCasilla > totalCasillas) {
                    System.out.print("          |");
                } else {
                    String jugadoresEnCasilla = obtenerJugadoresEnCasilla(
                            numeroCasilla, estadoJugadores);
                    System.out.printf(" %-8s |", jugadoresEnCasilla);
                }
            }
            System.out.println();
            System.out.println("|----------|----------|----------|----------|"
                    + "----------|----------|----------|----------|"
                    + "----------|----------|");
        }
        System.out.println();

        imprimirEstadoJugadores(estadoJugadores);
    }

    /**
     * Obtiene el simbolo de tipo de una casilla
     * Complejidad: O(1)
     */
    private String obtenerSimboloTipo(Casilla casilla, int meta) {
        if (casilla == null)
            return "";
        if (casilla.getNumero() == meta)
            return "[M]";
        switch (casilla.getTipo()) {
            case ESCALERA:
                return "[E]";
            case SERPIENTE:
                return "[S]";
            case RETO:
                return "[R]";
            default:
                return "   ";
        }
    }

    private String obtenerJugadoresEnCasilla(int numeroCasilla,
            TablaHash<String, Jugador> estadoJugadores) {
        Object[] jugadores = estadoJugadores.obtenerTodosLosValores();
        StringBuilder stringbuilder = new StringBuilder();
        for (Object obj : jugadores) {
            if (obj instanceof Jugador) {
                Jugador j = (Jugador) obj;
                if (j.getPosicion() == numeroCasilla) {
                    if (stringbuilder.length() > 0)
                        stringbuilder.append(",");
                    stringbuilder.append(j.getNombre().substring(0,
                            Math.min(2, j.getNombre().length())).toUpperCase());
                }
            }
        }
        return stringbuilder.toString();
    }

    private void imprimirEstadoJugadores(
            TablaHash<String, Jugador> estadoJugadores) {
        System.out.println("--- Estado de jugadores ---");
        Object[] jugadores = estadoJugadores.obtenerTodosLosValores();
        for (Object obj : jugadores) {
            if (obj instanceof Jugador) {
                Jugador j = (Jugador) obj;
                String tipo = j.esMaquina() ? "[Maquina]" : "[Humano] ";
                System.out.printf("  %s %-12s | Casilla: %2d | Aciertos: %d "
                        + "| Fallos: %d | Puntaje: %d%n",
                        tipo, j.getNombre(), j.getPosicion(),
                        j.getAciertos(), j.getFallos(), j.calcularPuntaje());
            }
        }
        System.out.println();
    }
}
