import consola.GestorEntrada;
import consola.MenuPrincipal;
import consola.RenderizadorTablero;
import estructuras.ArbolBinarioBusqueda;
import estructuras.Grafo;
import estructuras.ListaEnlazada;
import logica.JuegoServicio;
import modelo.Casilla;
import modelo.Jugador;
import servicios.CargadorPreguntas;
import servicios.ConstructorTablero;

/*
 * Responsabilidad iniciar el programa
 */
public class App {

    public static void main(String[] args) {
        GestorEntrada gestorEntrada = new GestorEntrada();
        MenuPrincipal menu = new MenuPrincipal(gestorEntrada);
        RenderizadorTablero renderizador = new RenderizadorTablero();

        menu.mostrarEncabezado();
        menu.mostrarInstrucciones();

        // Seleccion de modo y configuracion de jugadores
        int modo = menu.seleccionarModo();
        Jugador[] jugadores = menu.configurarJugadores(modo);

        // Carga de preguntas
        String rutaPreguntas = menu.solicitarRutaPreguntas();
        ArbolBinarioBusqueda arbolPreguntas = new ArbolBinarioBusqueda();
        CargadorPreguntas cargador = new CargadorPreguntas();
        int preguntasCargadas = cargador.cargar(rutaPreguntas, arbolPreguntas);

        if (preguntasCargadas == 0) {
            System.out.println("  Cargando preguntas por defecto...");
            cargador.cargarPreguntasPorDefecto(arbolPreguntas);
            System.out.println("  Preguntas por defecto cargadas: "
                    + arbolPreguntas.totalPreguntas());
        } else {
            System.out.println("  Preguntas cargadas desde archivo: "
                    + preguntasCargadas);
        }

        // Construccion de tablero y grafo
        ConstructorTablero constructorTablero = new ConstructorTablero();
        ListaEnlazada<Casilla> tablero = constructorTablero.construirTablero();
        Grafo grafoConexiones = constructorTablero.construirGrafo();

        System.out.println();
        System.out.println("Tablero construido: " + tablero.tamano()
                + " casillas.");
        grafoConexiones.imprimirConexiones();
        System.out.println();
        gestorEntrada.pausar();

        // Crear y ejecutar el juego
        JuegoServicio juego = new JuegoServicio(tablero, grafoConexiones,
                arbolPreguntas, renderizador, gestorEntrada);
        juego.registrarJugadores(jugadores);
        juego.ejecutar();

        gestorEntrada.cerrar();
        System.out.println();
        System.out.println("Gracias por jugar.");
    }
}
