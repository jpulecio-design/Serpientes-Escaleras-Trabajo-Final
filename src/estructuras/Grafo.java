package estructuras;

public class Grafo {

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

    public void agregarArista(int origen, int destino, String etiqueta) {
        EntradaVertice entrada = obtenerOCrearEntrada(origen);
        NodoAdyacencia nuevaArista = new NodoAdyacencia(destino, etiqueta);
        nuevaArista.siguiente = entrada.listaAdyacencia;
        entrada.listaAdyacencia = nuevaArista;
        cantidadAristas++;
    }

    public int obtenerDestino(int origen) {
        EntradaVertice entrada = buscarEntrada(origen);
        if (entrada == null || entrada.listaAdyacencia == null)
            return -1;
        return entrada.listaAdyacencia.destino;
    }

    public boolean tieneConexion(int origen) {
        return obtenerDestino(origen) != -1;
    }

    public String obtenerEtiqueta(int origen) {
        EntradaVertice entrada = buscarEntrada(origen);
        if (entrada == null || entrada.listaAdyacencia == null)
            return "";
        return entrada.listaAdyacencia.etiqueta;
    }

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

    private EntradaVertice buscarEntrada(int vertice) {
        EntradaVertice actual = cabezaVertices;
        while (actual != null) {
            if (actual.vertice == vertice)
                return actual;
            actual = actual.siguiente;
        }
        return null;
    }

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
