package estructuras;

public class Grafo {

    //arista
    private static class NodoAdyacencia {
        int destino;
        String etiqueta;
        NodoAdyacencia siguiente;

        NodoAdyacencia(int destino, String etiqueta) {
            this.destino = destino;
            this.etiqueta = etiqueta;
            this.siguiente = null;
        }
    }
    //lista de vertices
    private static class EntradaVertice {
        int vertice;
        NodoAdyacencia listaAdyacencia;
        EntradaVertice siguiente;

        EntradaVertice(int vertice) {
            this.vertice = vertice;
            this.listaAdyacencia = null;
            this.siguiente = null;
        }
    }

    private EntradaVertice cabezaVertices;
    private int cantidadVertices;
    private int cantidadAristas;

    public Grafo() {
        this.cabezaVertices = null;
        this.cantidadVertices = 0;
        this.cantidadAristas = 0;
    }

    /*
     * Agrega una arista dirigida de origen a destino con etiqueta
     * Si el vertice de origen no existe, lo crea automaticamente
     * Complejidad: O(v) donde v es la cantidad de vertices
     */
    public void agregarArista(int origen, int destino, String etiqueta) {
        EntradaVertice entrada = obtenerOCrearEntrada(origen);
        NodoAdyacencia nuevaArista = new NodoAdyacencia(destino, etiqueta);
        nuevaArista.siguiente = entrada.listaAdyacencia;
        entrada.listaAdyacencia = nuevaArista;
        cantidadAristas++;
    }

    /*
     * Obtiene la casilla de destino para una casilla de origen
     * Retorna -1 si no hay conexion especial desde esa casilla
     * Complejidad: O(v)
     */
    public int obtenerDestino(int origen) {
        EntradaVertice entrada = buscarEntrada(origen);
        if (entrada == null || entrada.listaAdyacencia == null)
            return -1;
        return entrada.listaAdyacencia.destino;
    }

    /*
     * Verifica si existe una conexion especial desde una casilla
     * Complejidad: O(v)
     */
    public boolean tieneConexion(int origen) {
        return obtenerDestino(origen) != -1;
    }

    /*
     * Retorna la etiqueta de la conexion desde una casilla
     * Complejidad: O(v)
     */
    public String obtenerEtiqueta(int origen) {
        EntradaVertice entrada = buscarEntrada(origen);
        if (entrada == null || entrada.listaAdyacencia == null)
            return "";
        return entrada.listaAdyacencia.etiqueta;
    }

    /*
     * Valida que las conexiones no formen ciclos absurdos. Un ciclo seria
     * que, al seguir los saltos en cadena desde una casilla, se regrese a
     * una casilla ya visitada, lo cual no tiene sentido en el juego
     * Complejidad: O(v * a) por recorrer la cadena de saltos de cada vertice
     */
    public boolean sinCiclos() {
        EntradaVertice actual = cabezaVertices;
        while (actual != null) {
            if (tieneCicloDesde(actual.vertice)) {
                return false;
            }
            actual = actual.siguiente;
        }
        return true;
    }

    /*
     * Sigue la cadena de saltos desde un origen y detecta si vuelve a pasar
     * por el origen, lo que indicaria un ciclo
     * Complejidad: O(v) por la longitud maxima de la cadena
     */
    private boolean tieneCicloDesde(int origen) {
        int pasos = 0;
        int actual = obtenerDestino(origen);
        while (actual != -1 && actual != origen) {
            actual = obtenerDestino(actual);
            pasos++;
            if (pasos > cantidadVertices) {
                return true;
            }
        }
        return actual == origen;
    }

    /*
     * Busca la entrada de un vertice sin crearlo
     * Complejidad: O(v)
     */
    private EntradaVertice buscarEntrada(int vertice) {
        EntradaVertice actual = cabezaVertices;
        while (actual != null) {
            if (actual.vertice == vertice)
                return actual;
            actual = actual.siguiente;
        }
        return null;
    }

    /*
     * Obtiene la entrada de un vertice o la crea si no existe
     * Complejidad: O(v)
     */
    private EntradaVertice obtenerOCrearEntrada(int vertice) {
        EntradaVertice encontrada = buscarEntrada(vertice);
        if (encontrada != null)
            return encontrada;
        EntradaVertice nueva = new EntradaVertice(vertice);
        nueva.siguiente = cabezaVertices;
        cabezaVertices = nueva;
        cantidadVertices++;
        return nueva;
    }

    /*
     * Imprime todas las conexiones del grafo para depuracion
     * Complejidad: O(v + a)
     */
    public void imprimirConexiones() {
        System.out.println("--- Conexiones especiales del tablero ---");
        EntradaVertice actual = cabezaVertices;
        while (actual != null) {
            NodoAdyacencia arista = actual.listaAdyacencia;
            while (arista != null) {
                System.out.println("  Casilla " + actual.vertice + " -> "
                        + arista.destino + " [" + arista.etiqueta + "]");
                arista = arista.siguiente;
            }
            actual = actual.siguiente;
        }
    }

    public int getCantidadVertices() {
        return cantidadVertices;
    }

    public int getCantidadAristas() {
        return cantidadAristas;
    }
}
