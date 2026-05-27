package estructuras;

import modelo.Pregunta;
import java.util.Random;

public class ArbolBinarioBusqueda {

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

    public void insertar(Pregunta pregunta) {
        raiz = insertarRecursivo(raiz, pregunta.getDificultad(), pregunta);
    }

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

    private void agregarPreguntaANodo(NodoArbol nodo, Pregunta pregunta) {
        NodoPregunta nuevo = new NodoPregunta(pregunta);
        nuevo.siguiente = nodo.listaPreguntasInicio;
        nodo.listaPreguntasInicio = nuevo;
        nodo.cantidadPreguntas++;
    }

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

    private NodoArbol buscarNodo(NodoArbol nodo, int clave) {
        if (nodo == null)
            return null;
        if (clave == nodo.clave)
            return nodo;
        if (clave < nodo.clave)
            return buscarNodo(nodo.izquierdo, clave);
        return buscarNodo(nodo.derecho, clave);
    }

    public boolean existenPreguntas(int dificultad) {
        NodoArbol nodo = buscarNodo(raiz, dificultad);
        return nodo != null && nodo.cantidadPreguntas > 0;
    }

    public int totalPreguntas() {
        return contarRecursivo(raiz);
    }

    private int contarRecursivo(NodoArbol nodo) {
        if (nodo == null)
            return 0;
        return nodo.cantidadPreguntas + contarRecursivo(nodo.izquierdo)
                + contarRecursivo(nodo.derecho);
    }
}
