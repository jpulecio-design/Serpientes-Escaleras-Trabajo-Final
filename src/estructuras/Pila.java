package estructuras;

public class Pila<T> {
    //Nodo interno de la pila
    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    private Nodo<T> tope;
    private int tamano;

    public Pila() {
        this.tope = null;
        this.tamano = 0;
    }


    /*
     * Apila un elemento en el tope de la pila
     * Complejidad: O(1)
     */   
    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = tope;
        tope = nuevo;
        tamano++;
    }

    
    public T desapilar() {
        if (estaVacia()) return null;
        T dato = tope.dato;
        tope = tope.siguiente;
        tamano--;
        return dato;
    }

 
    public T tope() {
        if (estaVacia()) return null;
        return tope.dato;
    }

    public boolean estaVacia() {
        return tamano == 0;
    }

    public int tamano() {
        return tamano;
    }


    @SuppressWarnings("unchecked")
    public T[] obtenerUltimos(int cantidad) {
        int limite = Math.min(cantidad, tamano);
        Object[] resultado = new Object[limite];
        Nodo<T> actual = tope;
        for (int i = 0; i < limite; i++) {
            resultado[i] = actual.dato;
            actual = actual.siguiente;
        }
        return (T[]) resultado;
    }
}
