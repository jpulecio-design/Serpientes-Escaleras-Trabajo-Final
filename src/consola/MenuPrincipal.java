package consola;

import modelo.Jugador;

public class MenuPrincipal {

    private GestorEntrada gestorEntrada;

    public MenuPrincipal(GestorEntrada gestorEntrada) {
        this.gestorEntrada = gestorEntrada;
    }

    public void mostrarEncabezado() {
        System.out.println();
        System.out.println("=================================================");
        System.out.println("|     JUEGO DE ESCALERA, SERPIENTES Y RETOS     |");
        System.out.println("|      Estructuras de Datos - Proyecto Final    |");
        System.out.println("=================================================");
        System.out.println("|  Tablero: 50 casillas                         |");
        System.out.println("|  Escaleras: 5  |  Serpientes: 5  |  Retos: 7  |");
        System.out.println("=================================================");
        System.out.println();
    }

    public int seleccionarModo() {
        System.out.println("--- Modo de juego ---");
        System.out.println("  1) Multijugador (2-4 humanos)");
        System.out.println("  2) Jugador vs Maquina");
        return gestorEntrada.leerEntero("Seleccione modo: ", 1, 2);
    }

    public Jugador[] configurarJugadores(int modo) {
        if (modo == 2) {
            return configurarModoVsMaquina();
        } else {
            return configurarModoMultijugador();
        }
    }

    private Jugador[] configurarModoMultijugador() {
        System.out.println();
        int cantidad = gestorEntrada.leerEntero(
                "Numero de jugadores (2-4): ", 2, 4);
        Jugador[] jugadores = new Jugador[cantidad];
        for (int i = 0; i < cantidad; i++) {
            String nombre = gestorEntrada.leerTexto(
                    "Nombre del jugador " + (i + 1) + ": ");
            if (nombre.isEmpty())
                nombre = "Jugador" + (i + 1);
            jugadores[i] = new Jugador(nombre, false);
        }
        return jugadores;
    }

    private Jugador[] configurarModoVsMaquina() {
        System.out.println();
        String nombre = gestorEntrada.leerTexto("Nombre del jugador: ");
        if (nombre.isEmpty())
            nombre = "Jugador1";
        Jugador humano = new Jugador(nombre, false);
        Jugador maquina = new Jugador("Maquina", true);
        return new Jugador[] { humano, maquina };
    }

    public String solicitarRutaPreguntas() {
        System.out.println();
        System.out.println("Archivo de preguntas:");
        System.out.println("  Ruta por defecto: preguntas.txt");
        String ruta = gestorEntrada.leerTexto(
                "Ingrese ruta (Enter para usar la por defecto): ");
        return ruta.isEmpty() ? "preguntas.txt" : ruta;
    }

    public void mostrarInstrucciones() {
        System.out.println();
        System.out.println("--- Instrucciones ---");
        System.out.println("  Cada jugador lanza un dado (1-6) en su turno.");
        System.out.println("  [E] Escalera: avanza a una casilla superior.");
        System.out.println("  [S] Serpiente: retrocede a una casilla inferior.");
        System.out.println("  [R] Reto: responde una pregunta.");
        System.out.println("       Acierto -> turno extra.");
        System.out.println("       Fallo   -> pierde el proximo turno.");
        System.out.println("  [M] Meta: se debe llegar EXACTAMENTE a la casilla 50.");
        System.out.println("       Si el dado supera la meta, no se mueve.");
        System.out.println();
    }
}
