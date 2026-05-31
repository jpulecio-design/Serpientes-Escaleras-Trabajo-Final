# Proyecto 3: Juego de Escalera, Serpientes y Retos

Juego de tablero por consola con 50 casillas para 2 a 4 jugadores, o un
jugador humano contra la maquina. En cada turno el jugador lanza un dado
y avanza; puede caer en escaleras, serpientes o casillas de reto con
preguntas. Gana quien llegue exactamente a la casilla 50.

Todas las estructuras de datos estan implementadas manualmente, sin usar
las clases nativas de Java (ArrayList, LinkedList, Stack, Queue, HashMap,
Collections, etc.).

---

## Participantes

- Juan Andrés Pulecio
- Diego Fernando Muñoz
- Juan Esteban Garcia
- Daniel Estiven Lopez
---

## Asignatura

- Estructura de datos

---

## Estructuras de datos implementadas manualmente


| Estructura       | Uso en el juego                                        | Clase                  |
|------------------|--------------------------------------------------------|------------------------|
| Lista enlazada   | Tablero de 50 casillas                                 | `ListaEnlazada<T>`     |
| Cola             | Turnos rotativos de jugadores                          | `Cola<T>`              |
| Pila             | Historial de movimientos (ultimas 5 jugadas)           | `Pila<T>`              |
| Tabla hash       | Estado rapido de jugadores por nombre                  | `TablaHash<K,V>`       |
| Arbol BST        | Preguntas organizadas por dificultad (1-3)             | `ArbolBinarioBusqueda` |
| Grafo dirigido   | Conexiones escaleras/serpientes y validacion de ciclos | `Grafo`                |


## Estructura del proyecto

```
SerpienteEscalera/
├──     src/
|        ├── consola/    
|        |      ├── GestorEntrada.java
|        |      ├── MenuPrincipal.java
|        |      └── RenderizadorTablero.java
|        ├── estructuras/
|        |      ├── ArbolBinarioBusqueda.java
|        |      ├── Cola.java
|        |      ├── Grafo.java
|        |      ├── ListaEnlazada.java
|        |      ├── Pila.java
|        |      └── TablaHash.java
|        ├── logica/
|        |      └── JuegoServicio.java
|        ├── modelo/ 
|        |      ├── Casilla.java
|        |      ├── Jugador.java    
|        |      ├── Movimiento.java
|        |      ├── Pregunta.java
|        |      └── TipoCasilla.java
|        ├── servicios/
|        |      ├── CargadorPreguntas.java
|        |      ├── ConstructorTablero.java
|        |      ├── GestorIA.java
|        |      └── GestorRanking.java     
|        └──App.java                 
├── preguntas.txt
└── README.md
```

## Reglas del juego

- El tablero tiene 50 casillas y los jugadores inician en la casilla 1.
- En cada turno el jugador lanza un dado (1-6).
- Escalera [E]: sube a una casilla superior.
- Serpiente [S]: baja a una casilla inferior.
- Reto [R]: responde una pregunta de trivia.
  ├── Acierto: gana un turno extra (vuelve a lanzar de inmediato).
  └── Fallo: pierde su proximo turno.
- Meta exacta: si el dado haria pasar la casilla 50, el jugador no se mueve.
- Gana el primer jugador que llega exactamente a la casilla 50.

## Configuracion del tablero

Escaleras (suben): 4->14, 9->31, 20->38, 28->44, 40->47
Serpientes (bajan): 17->7, 26->13, 32->19, 45->36, 49->42
Casillas de reto: 6, 12, 18, 24, 30, 35, 43

Dificultad de las preguntas segun casilla: 1-17 facil (1), 18-34 medio
(2), 35-50 dificil (3).

Al iniciar, el juego valida que las conexiones del grafo no formen ciclos
absurdos.

## Formato del archivo preguntas.txt

```
ID|enunciado|opcion1|opcion2|opcion3|opcion4|respuesta_correcta|categoria|dificultad
```

- `respuesta_correcta`: numero del 1 al 4.
- `dificultad`: 1, 2 o 3.
- Las lineas vacias o que comienzan con `#` se ignoran.

Ejemplo:

```
1|Cuanto es 5 + 3?|6|8|10|7|2|Matematicas|1
```

## Modo Maquina (IA)

La maquina lanza el dado automaticamente y responde las preguntas con una
probabilidad de acierto del 60%. Se integra con la cola de turnos, el
historial, el hash de estado y el ranking igual que un jugador humano. La
logica es manual; no se usan librerias de inteligencia artificial.

## Principios aplicados

SRP; una responsabilidad por clase, DRY; sin duplicacion, KISS; soluciones simples y YAGNI. Cada metodo relevante
documenta su complejidad Big O. La recursividad se usa en el arbol binario y en el ranking, con casos base comentados.

## Complejidades principales

| Operacion                        | Complejidad       |
|----------------------------------|-------------------|
| Mover jugador en tablero         | O(n)              |
| Verificar conexion en grafo      | O(v)              |
| Validar ciclos del grafo         | O(v * a)          |
| Obtener pregunta aleatoria (BST) | O(log n)          |
| Encolar / desencolar jugador     | O(1)              |
| Apilar / desapilar movimiento    | O(1)              |
| Insertar / buscar en hash        | O(1) promedio     |
| Insertar en ranking (BST)        | O(log n) promedio |
| Renderizar tablero               | O(n * j)          |

---

## Instrucciones para ejecutar

### Requisitos
- Java JDK 11 o superior
- VS Code con Extension Pack for Java
### Pasos

1. Clona el repositorio:

   git clone https://github.com/jpulecio-design/Serpientes-Escaleras-Trabajo-Final

2. Abre el proyecto en tu IDE.

3. Verifica que los archivos .java esten dentro del paquete Recursividad
   en la carpeta src/.
### Desde VS Code
1. Abre la carpeta del proyecto en VS Code
2. Abre `App.java`
3. Presiona **Run** o `Ctrl + F5`
4. Los resultados de cada ejercicio se imprimen en consola.
### Desde consola
```bash
javac -d bin src/**/*.java src/App.java
java -cp bin App
```
El programa pide la ruta del archivo de preguntas (Enter usa
`preguntas.txt`). Si no encuentra el archivo, carga un conjunto de
preguntas por defecto, de modo que el juego siempre es jugable.
---

