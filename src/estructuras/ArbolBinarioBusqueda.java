package estructuras;

import modelo.Pregunta;
import java.util.Random;

public class ArbolBinarioBusqueda {
    /**
     * Nodo interno del arbol
     * Contiene una lista de preguntas para una dificultad
     */
    private static class NodoArbol {
        int clave;
        NodoPregunta listaPreguntasInicio;
        int cantidadPreguntas;
        NodoArbol izquierdo;
        NodoArbol derecho;

        NodoArbol(int clave) {
            this.clave = clave;
            this.listaPreguntasInicio = null;
            this.cantidadPreguntas = 0;
            this.izquierdo = null;
            this.derecho = null;
        }
    }
    /*
     * Nodo de la lista interna de preguntas por dificultad.
     */
    private static class NodoPregunta {
        Pregunta pregunta;
        NodoPregunta siguiente;

        NodoPregunta(Pregunta pregunta) {
            this.pregunta = pregunta;
            this.siguiente = null;
        }
    }

    private NodoArbol raiz;
    private Random random;

    public ArbolBinarioBusqueda() {
        this.raiz = null;
        this.random = new Random();
    }

    /*
     * Inserta una pregunta en el arbol segun su nivel de dificultad
     * Complejidad: O(log n) promedio
     */
    public void insertar(Pregunta pregunta) {
        raiz = insertarRecursivo(raiz, pregunta.getDificultad(), pregunta);
    }
    /*
     * Caso base: nodo nulo, crear nuevo nodo
     * Caso recursivo: ir al hijo izquierdo o derecho segun la clave
     * Complejidad: O(log n) promedio, O(n) peor caso
     */
    private NodoArbol insertarRecursivo(NodoArbol nodo, int clave,
            Pregunta pregunta) {
        if (nodo == null) {
            NodoArbol nuevo = new NodoArbol(clave);
            agregarPreguntaANodo(nuevo, pregunta);
            return nuevo;
        }
        if (clave < nodo.clave) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, clave, pregunta);
        } else if (clave > nodo.clave) {
            nodo.derecho = insertarRecursivo(nodo.derecho, clave, pregunta);
        } else {
            agregarPreguntaANodo(nodo, pregunta);
        }
        return nodo;
    }

    /*
     * Agrega una pregunta a la lista enlazada interna del nodo
     * Complejidad: O(1)
     */    
    private void agregarPreguntaANodo(NodoArbol nodo, Pregunta pregunta) {
        NodoPregunta nuevo = new NodoPregunta(pregunta);
        nuevo.siguiente = nodo.listaPreguntasInicio;
        nodo.listaPreguntasInicio = nuevo;
        nodo.cantidadPreguntas++;
    }

    /*
     * Obtiene una pregunta aleatoria de la dificultad indicada
     * Si no hay preguntas de ese nivel, retorna null
     * Complejidad: O(log n) promedio + O(k) para recorrer lista
     */
    public Pregunta obtenerPreguntaAleatoria(int dificultad) {
        NodoArbol nodo = buscarNodo(raiz, dificultad);
        if (nodo == null || nodo.cantidadPreguntas == 0)
            return null;
        int indice = random.nextInt(nodo.cantidadPreguntas);
        NodoPregunta actual = nodo.listaPreguntasInicio;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.pregunta;
    }

    /**
     * Busca recursivamente un nodo por clave
     * Caso base: nodo nulo o clave encontrada
     * Caso recursivo: buscar en hijo izquierdo o derecho
     * Complejidad: O(log n) promedio
     */
    private NodoArbol buscarNodo(NodoArbol nodo, int clave) {
        if (nodo == null)
            return null;
        if (clave == nodo.clave)
            return nodo;
        if (clave < nodo.clave)
            return buscarNodo(nodo.izquierdo, clave);
        return buscarNodo(nodo.derecho, clave);
    }

    /*
     * Verifica si existen preguntas de una dificultad dada
     * Complejidad: O(log n) promedio
     */
    public boolean existenPreguntas(int dificultad) {
        NodoArbol nodo = buscarNodo(raiz, dificultad);
        return nodo != null && nodo.cantidadPreguntas > 0;
    }

    /*
     * Retorna la cantidad total de preguntas en el arbol.
     * Complejidad: O(n)
     */
    public int totalPreguntas() {
        return contarRecursivo(raiz);
    }

    /*
     * Cuenta recursivamente todas las preguntas.
     * Caso base: nodo nulo retorna 0.
     * Caso recursivo: sumar preguntas del nodo + subarboles.
     * Complejidad: O(n)
     */
    private int contarRecursivo(NodoArbol nodo) {
        if (nodo == null)
            return 0;
        return nodo.cantidadPreguntas + contarRecursivo(nodo.izquierdo)
                + contarRecursivo(nodo.derecho);
    }
}
