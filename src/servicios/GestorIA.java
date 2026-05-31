package servicios;

import modelo.Jugador;
import modelo.Pregunta;

import java.util.Random;

public class GestorIA {

    private static final double PROBABILIDAD_ACIERTO = 0.60;
    private Random random;

    public GestorIA() {
        this.random = new Random();
    }

    /*
     * Simula el lanzamiento del dado por parte de la IA
     * Genera un valor aleatorio entre 1 y 6
     * Complejidad: O(1)
     */
    public int lanzarDado() {
        return random.nextInt(6) + 1;
    }

    /*
     * La IA responde una pregunta con una probabilidad de acierto del 60%
     * Si acierta, retorna la opcion correcta; si falla, retorna una incorrecta
     * Complejidad: O(1)
     */
    public int responderPregunta(Pregunta pregunta, Jugador jugador) {
        boolean acierta = random.nextDouble() < PROBABILIDAD_ACIERTO;
        if (acierta) {
            return pregunta.getRespuestaCorrecta();
        } else {
            return elegirOpcionIncorrecta(pregunta);
        }
    }

    /*
     * Elige una opcion incorrecta aleatoria de la pregunta
     * Complejidad: O(1)
     */
    private int elegirOpcionIncorrecta(Pregunta pregunta) {
        int correcta = pregunta.getRespuestaCorrecta();
        int[] incorrectas = new int[3];
        int contador = 0;
        for (int i = 1; i <= 4; i++) {
            if (i != correcta) {
                incorrectas[contador++] = i;
            }
        }
        return incorrectas[random.nextInt(3)];
    }

    /*
     * Genera un mensaje descriptivo de la decision tomada por la IA
     * Complejidad: O(1)
     */
    public String generarMensajeDado(String nombre, int valorDado) {
        return nombre + " (Maquina) lanza el dado automaticamente... obtuvo: "
                + valorDado;
    }
}
