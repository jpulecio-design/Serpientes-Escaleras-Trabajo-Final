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

    public int lanzarDado() {
        return random.nextInt(6) + 1;
    }

    public int responderPregunta(Pregunta pregunta, Jugador jugador) {
        boolean acierta = random.nextDouble() < PROBABILIDAD_ACIERTO;
        if (acierta) {
            return pregunta.getRespuestaCorrecta();
        } else {
            return elegirOpcionIncorrecta(pregunta);
        }
    }

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

    public String generarMensajeDado(String nombre, int valorDado) {
        return nombre + " (Maquina) lanza el dado automaticamente... obtuvo: "
                + valorDado;
    }
}
