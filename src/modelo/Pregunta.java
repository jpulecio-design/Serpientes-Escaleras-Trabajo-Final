package modelo;

public class Pregunta {

    private int id;
    private String enunciado;
    private String[] opciones;
    private int respuestaCorrecta;
    private String categoria;
    private int dificultad;

    public Pregunta(int id, String enunciado, String[] opciones, int respuestaCorrecta,
            String categoria, int dificultad) {
        this.id = id;
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
        this.categoria = categoria;
        this.dificultad = dificultad;
    }

    public int getId() {
        return id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public int getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getDificultad() {
        return dificultad;
    }

    public boolean esCorrecta(int respuesta) {
        return respuesta == respuestaCorrecta;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(categoria).append(" - Dificultad ").append(dificultad).append("]\n");
        sb.append(enunciado).append("\n");
        for (int i = 0; i < opciones.length; i++) {
            sb.append("  ").append(i + 1).append(") ").append(opciones[i]).append("\n");
        }
        return sb.toString();
    }
}
