package logica;

import estructuras.ArbolBinarioBusqueda;
import estructuras.Cola;
import estructuras.Grafo;
import estructuras.ListaEnlazada;
import estructuras.Pila;
import estructuras.TablaHash;
import modelo.Casilla;
import modelo.Jugador;
import modelo.Movimiento;
import modelo.Pregunta;
import modelo.TipoCasilla;
import servicios.ConstructorTablero;
import servicios.GestorIA;
import servicios.GestorRanking;
import consola.RenderizadorTablero;
import consola.GestorEntrada;

import java.util.Random;

/**
 * Servicio principal del juego. Contiene el bucle de juego completo.
 * Coordina el tablero, los jugadores, los turnos, las preguntas y el historial.
 *
 * Responsabilidades:
 *   - Gestionar el bucle principal hasta haber un ganador
 *   - Coordinar todas las estructuras de datos
 *   - Aplicar las reglas del juego (incluido el turno extra real)
 *   - Delegar renderizado y entrada a los modulos correspondientes
 */
public class JuegoServicio {

    private static final int META = 50;
    private static final int ULTIMAS_JUGADAS = 5;
    private static final int LIMITE_TURNOS_EXTRA = 10;

    private ListaEnlazada<Casilla> tablero;
    private Grafo grafoConexiones;
    private Cola<Jugador> colaJugadores;
    private Pila<Movimiento> historialMovimientos;
    private TablaHash<String, Jugador> estadoJugadores;
    private ArbolBinarioBusqueda arbolPreguntas;

    private ConstructorTablero constructorTablero;
    private GestorIA gestorIA;
    private GestorRanking gestorRanking;
    private RenderizadorTablero renderizador;
    private GestorEntrada gestorEntrada;

    private Random random;
    private Jugador ganador;

    /**
     * Construye el servicio de juego con todas sus dependencias.
     *
     * @param tablero         lista enlazada del tablero
     * @param grafoConexiones grafo de escaleras y serpientes
     * @param arbolPreguntas  BST de preguntas por dificultad
     * @param renderizador    modulo de renderizado ASCII
     * @param gestorEntrada   modulo de entrada por teclado
     */
    public JuegoServicio(ListaEnlazada<Casilla> tablero, Grafo grafoConexiones,
                        ArbolBinarioBusqueda arbolPreguntas,
                        RenderizadorTablero renderizador,
                        GestorEntrada gestorEntrada) {
        this.tablero = tablero;
        this.grafoConexiones = grafoConexiones;
        this.arbolPreguntas = arbolPreguntas;
        this.renderizador = renderizador;
        this.gestorEntrada = gestorEntrada;

        this.colaJugadores = new Cola<>();
        this.historialMovimientos = new Pila<>();
        this.estadoJugadores = new TablaHash<>();
        this.constructorTablero = new ConstructorTablero();
        this.gestorIA = new GestorIA();
        this.gestorRanking = new GestorRanking();
        this.random = new Random();
        this.ganador = null;
    }

    /**
     * Registra a los jugadores en la cola de turnos y en la tabla hash.
     * Complejidad: O(n) donde n es el numero de jugadores
     *
     * @param jugadores arreglo de jugadores a registrar
     */
    public void registrarJugadores(Jugador[] jugadores) {
        for (Jugador jugador : jugadores) {
            colaJugadores.encolar(jugador);
            estadoJugadores.insertar(jugador.getNombre(), jugador);
        }
    }

    /**
     * Ejecuta el bucle principal del juego hasta que haya un ganador.
     * Complejidad: O(t * n) donde t es el numero de turnos y n el de jugadores
     */
    public void ejecutar() {
        // Validacion de ciclos del grafo (requisito del enunciado).
        if (grafoConexiones.sinCiclos()) {
            System.out.println("Validacion del grafo: sin ciclos absurdos. OK");
        } else {
            System.out.println("Advertencia: el grafo contiene un ciclo. "
                    + "Revise las conexiones del tablero.");
        }

        renderizador.imprimirTablero(tablero, estadoJugadores, META);
        System.out.println();
        System.out.println("El juego comienza. Casilla meta: " + META);
        System.out.println("Regla: se debe llegar EXACTAMENTE a la casilla "
                + META + ".");
        System.out.println();
        gestorEntrada.pausar();

        while (ganador == null) {
            Jugador jugadorActual = colaJugadores.desencolar();

            // Regla: si debe saltar turno (por fallo previo), lo pierde.
            if (jugadorActual.debeSaltarTurno()) {
                jugadorActual.setSaltarTurno(false);
                System.out.println(">>> " + jugadorActual.getNombre()
                        + " pierde su turno por fallo anterior.");
                colaJugadores.encolar(jugadorActual);
                gestorEntrada.pausar();
                continue;
            }

            jugarTurnoCompleto(jugadorActual);

            // Si gano durante el turno, salir del bucle.
            if (ganador != null) {
                break;
            }

            // Reencolar al final (rotacion normal de turnos).
            colaJugadores.encolar(jugadorActual);

            renderizador.imprimirTablero(tablero, estadoJugadores, META);
            imprimirHistorialReciente();
            gestorEntrada.pausar();
        }

        finalizarJuego();
    }

    /**
     * Juega el turno completo de un jugador, incluyendo los turnos extra que
     * gane por acertar retos. El jugador vuelve a tirar inmediatamente mientras
     * acierte, hasta un limite de seguridad o hasta llegar a la meta.
     * Complejidad: O(k * n) donde k es el numero de turnos extra encadenados
     *
     * @param jugador jugador en turno
     */
    private void jugarTurnoCompleto(Jugador jugador) {
        boolean turnoExtra = true;
        int turnosJugados = 0;

        while (turnoExtra && ganador == null
                && turnosJugados < LIMITE_TURNOS_EXTRA) {
            System.out.println("--------------------------------------------");
            System.out.println("Turno de: " + jugador.getNombre()
                    + " | Posicion actual: " + jugador.getPosicion());

            turnoExtra = procesarTurno(jugador);
            turnosJugados++;

            if (jugador.getPosicion() == META) {
                ganador = jugador;
                System.out.println();
                System.out.println("*** " + ganador.getNombre()
                        + " llego a la meta. ***");
                return;
            }

            if (turnoExtra && ganador == null) {
                System.out.println("[Turno extra] " + jugador.getNombre()
                        + " juega de nuevo por acertar el reto.");
            }
        }
    }

    /**
     * Procesa un turno individual (un lanzamiento de dado) de un jugador.
     * Complejidad: O(n) por consultas al tablero y grafo
     *
     * @param jugador jugador cuyo turno se procesa
     * @return true si el jugador gana un turno extra (acerto un reto)
     */
    private boolean procesarTurno(Jugador jugador) {
        int valorDado = lanzarDado(jugador);
        int posicionAnterior = jugador.getPosicion();
        int posicionNueva = posicionAnterior + valorDado;
        String descripcion = "Dado: " + valorDado;
        boolean turnoExtra = false;

        // Regla meta exacta
        if (posicionNueva > META) {
            System.out.println("  El dado (" + valorDado
                    + ") haria pasar la meta. No se mueve.");
            descripcion += " | Supera meta, no se mueve";
            registrarMovimiento(jugador, posicionAnterior,
                    posicionAnterior, valorDado, descripcion);
            return false;
        }

        jugador.setPosicion(posicionNueva);
        estadoJugadores.insertar(jugador.getNombre(), jugador);
        System.out.println("  Se mueve de " + posicionAnterior
                + " a " + posicionNueva);

        // Verificar conexion especial via grafo
        if (grafoConexiones.tieneConexion(posicionNueva)) {
            int destinoEspecial = grafoConexiones.obtenerDestino(posicionNueva);
            String etiqueta = grafoConexiones.obtenerEtiqueta(posicionNueva);
            if ("ESCALERA".equals(etiqueta)) {
                System.out.println("  Escalera! Sube de " + posicionNueva
                        + " a " + destinoEspecial);
            } else {
                System.out.println("  Serpiente! Baja de " + posicionNueva
                        + " a " + destinoEspecial);
            }
            descripcion += " | " + etiqueta + " -> " + destinoEspecial;
            jugador.setPosicion(destinoEspecial);
            estadoJugadores.insertar(jugador.getNombre(), jugador);
            posicionNueva = destinoEspecial;
        }

        // Verificar si es casilla de reto
        Casilla casilla = constructorTablero.obtenerCasilla(
                tablero, posicionNueva);
        if (casilla != null && casilla.getTipo() == TipoCasilla.RETO) {
            System.out.println("  Casilla RETO en " + posicionNueva + ".");
            turnoExtra = procesarReto(jugador, posicionNueva);
            descripcion += turnoExtra ? " | RETO ACERTADO"
                    : " | RETO FALLADO";
        }

        registrarMovimiento(jugador, posicionAnterior,
                jugador.getPosicion(), valorDado, descripcion);
        return turnoExtra;
    }

    /**
     * Lanza el dado para un jugador (humano o maquina).
     * Complejidad: O(1)
     *
     * @param jugador jugador que lanza el dado
     * @return valor del dado (1-6)
     */
    private int lanzarDado(Jugador jugador) {
        int valor;
        if (jugador.esMaquina()) {
            valor = gestorIA.lanzarDado();
            System.out.println("  " + gestorIA.generarMensajeDado(
                    jugador.getNombre(), valor));
        } else {
            gestorEntrada.esperarEnter(jugador.getNombre()
                    + ", presione Enter para lanzar el dado...");
            valor = random.nextInt(6) + 1;
            System.out.println("  Dado: " + valor);
        }
        return valor;
    }

    /**
     * Procesa la casilla de reto: busca pregunta, solicita respuesta y aplica
     * el resultado. Acierto concede turno extra; fallo hace perder el proximo
     * turno (una sola penalizacion, segun el enunciado).
     * Complejidad: O(log n) por busqueda en BST
     *
     * @param jugador       jugador que debe responder
     * @param numeroCasilla numero de la casilla de reto
     * @return true si el jugador acerto la pregunta (turno extra)
     */
    private boolean procesarReto(Jugador jugador, int numeroCasilla) {
        int dificultad = calcularDificultadSegunCasilla(numeroCasilla);
        Pregunta pregunta = arbolPreguntas.obtenerPreguntaAleatoria(dificultad);

        if (pregunta == null) {
            System.out.println("  No hay preguntas para esta dificultad. "
                    + "Se omite el reto.");
            return false;
        }

        System.out.println();
        System.out.println("  === RETO - Dificultad " + dificultad + " ===");
        System.out.println("  " + pregunta.getEnunciado());
        String[] opciones = pregunta.getOpciones();
        for (int i = 0; i < opciones.length; i++) {
            System.out.println("    " + (i + 1) + ") " + opciones[i]);
        }

        int respuesta;
        if (jugador.esMaquina()) {
            respuesta = gestorIA.responderPregunta(pregunta, jugador);
            System.out.println("  Maquina responde: opcion " + respuesta
                    + " (" + opciones[respuesta - 1] + ")");
        } else {
            respuesta = gestorEntrada.leerOpcion("  Su respuesta (1-4): ", 1, 4);
        }

        if (pregunta.esCorrecta(respuesta)) {
            System.out.println("  Respuesta CORRECTA. Gana turno extra.");
            jugador.registrarAcierto();
            estadoJugadores.insertar(jugador.getNombre(), jugador);
            return true;
        } else {
            System.out.println("  Respuesta INCORRECTA. La correcta era: "
                    + pregunta.getRespuestaCorrecta() + ") "
                    + opciones[pregunta.getRespuestaCorrecta() - 1]);
            System.out.println("  Pierde su proximo turno.");
            jugador.registrarFallo();
            jugador.setSaltarTurno(true);
            estadoJugadores.insertar(jugador.getNombre(), jugador);
            return false;
        }
    }

    /**
     * Determina la dificultad de la pregunta segun el numero de casilla.
     * Complejidad: O(1)
     *
     * @param casilla numero de la casilla de reto
     * @return nivel de dificultad (1, 2 o 3)
     */
    private int calcularDificultadSegunCasilla(int casilla) {
        if (casilla <= 17) return 1;
        if (casilla <= 34) return 2;
        return 3;
    }

    /**
     * Registra un movimiento en la pila de historial.
     * Complejidad: O(1)
     *
     * @param jugador          jugador que realizo el movimiento
     * @param posicionAnterior casilla de origen
     * @param posicionNueva    casilla de destino final
     * @param valorDado        resultado del dado
     * @param descripcion      descripcion del evento
     */
    private void registrarMovimiento(Jugador jugador, int posicionAnterior,
            int posicionNueva, int valorDado, String descripcion) {
        Movimiento movimiento = new Movimiento(jugador.getNombre(),
                posicionAnterior, posicionNueva, valorDado, descripcion);
        historialMovimientos.apilar(movimiento);
    }

    /**
     * Imprime las ultimas N jugadas del historial desde la pila.
     * Complejidad: O(k) donde k es ULTIMAS_JUGADAS
     */
    private void imprimirHistorialReciente() {
        System.out.println();
        System.out.println("--- Ultimas " + ULTIMAS_JUGADAS + " jugadas ---");
        if (historialMovimientos.estaVacia()) {
            System.out.println("  Aun no hay jugadas registradas.");
            return;
        }
        Object[] ultimas = historialMovimientos.obtenerUltimos(ULTIMAS_JUGADAS);
        for (Object obj : ultimas) {
            if (obj instanceof Movimiento) {
                System.out.println("  " + obj);
            }
        }
    }

    /**
     * Finaliza el juego mostrando el resultado y el ranking.
     * Complejidad: O(n log n) por insercion en BST de ranking
     */
    private void finalizarJuego() {
        renderizador.imprimirTablero(tablero, estadoJugadores, META);
        System.out.println();
        System.out.println("============================================");
        System.out.println("              FIN DEL JUEGO                 ");
        System.out.println("============================================");
        System.out.println("Ganador: " + ganador.getNombre());
        System.out.println();

        // Insertar todos los jugadores en el ranking
        Object[] jugadores = estadoJugadores.obtenerTodosLosValores();
        for (Object obj : jugadores) {
            if (obj instanceof Jugador) {
                gestorRanking.insertarJugador((Jugador) obj);
            }
        }

        gestorRanking.imprimirRanking();
        imprimirHistorialReciente();
    }

    public Jugador getGanador() {
        return ganador;
    }
}
