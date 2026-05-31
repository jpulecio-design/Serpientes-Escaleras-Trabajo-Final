package estructuras;
//Nodo de la carpeta en cada cubeta
public class TablaHash<K, V> {

    private static class NodoHash<K, V> {
        K clave;
        V valor;
        NodoHash<K, V> siguiente;

        NodoHash(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
            this.siguiente = null;
        }
    }

    private static final int CAPACIDAD_INICIAL = 16;
    private Object[] cubetas;
    private int tamano;
    private int capacidad;

    public TablaHash() {
        this.capacidad = CAPACIDAD_INICIAL;
        this.cubetas = new Object[capacidad];
        this.tamano = 0;
    }

    public TablaHash(int capacidad) {
        this.capacidad = capacidad;
        this.cubetas = new Object[capacidad];
        this.tamano = 0;
    }

    /*
     * Calcula el indice de cubeta para una clave dada
     * Complejidad: O(1)
     */
    private int calcularIndice(K clave) {
        if (clave == null)
            return 0;
        int hash = clave.hashCode();
        if (hash < 0)
            hash = -hash;
        return hash % capacidad;
    }

    /*
     * Inserta o actualiza un par clave-valor
     * Complejidad: O(1) promedio
     */
    @SuppressWarnings("unchecked")
    public void insertar(K clave, V valor) {
        int indice = calcularIndice(clave);
        NodoHash<K, V> actual = (NodoHash<K, V>) cubetas[indice];
        while (actual != null) {
            if (actual.clave.equals(clave)) {
                actual.valor = valor;
                return;
            }
            actual = actual.siguiente;
        }
        NodoHash<K, V> nuevo = new NodoHash<>(clave, valor);
        nuevo.siguiente = (NodoHash<K, V>) cubetas[indice];
        cubetas[indice] = nuevo;
        tamano++;
    }

    /*
     * Busca y retorna el valor asociado a una clave
     * Complejidad: O(1) promedio
     */
    @SuppressWarnings("unchecked")
    public V buscar(K clave) {
        int indice = calcularIndice(clave);
        NodoHash<K, V> actual = (NodoHash<K, V>) cubetas[indice];
        while (actual != null) {
            if (actual.clave.equals(clave)) {
                return actual.valor;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    /*
     * Verifica si existe una clave en la tabla
     * Complejidad: O(1) promedio
     */
    public boolean contiene(K clave) {
        return buscar(clave) != null;
    }

    /*
     * Retorna todos los valores almacenados en la tabla
     * Complejidad: O(n + capacidad)
     */
    @SuppressWarnings("unchecked")
    public Object[] obtenerTodosLosValores() {
        Object[] valores = new Object[tamano];
        int indiceValor = 0;
        for (int i = 0; i < capacidad; i++) {
            NodoHash<K, V> actual = (NodoHash<K, V>) cubetas[i];
            while (actual != null) {
                valores[indiceValor++] = actual.valor;
                actual = actual.siguiente;
            }
        }
        return valores;
    }

    public int tamano() {
        return tamano;
    }

    public boolean estaVacia() {
        return tamano == 0;
    }
}
