package servicios;

import estructuras.ArbolBinarioBusqueda;
import modelo.Pregunta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CargadorPreguntas {

    private static final String SEPARADOR = "\\|";

    public int cargar(String rutaArchivo, ArbolBinarioBusqueda arbol) {
        int contador = 0;
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("#"))
                    continue;
                Pregunta pregunta = parsearLinea(linea);
                if (pregunta != null) {
                    arbol.insertar(pregunta);
                    contador++;
                }
            }
        } catch (IOException e) {
            System.out.println("[ADVERTENCIA] No se pudo leer el archivo: "
                    + rutaArchivo);
            System.out.println("  Se usaran preguntas por defecto.");
        }
        return contador;
    }

    private Pregunta parsearLinea(String linea) {
        try {
            String[] partes = linea.split(SEPARADOR);
            if (partes.length < 9)
                return null;

            int id = Integer.parseInt(partes[0].trim());
            String enunciado = partes[1].trim();
            String[] opciones = {
                    partes[2].trim(),
                    partes[3].trim(),
                    partes[4].trim(),
                    partes[5].trim()
            };
            int respuesta = Integer.parseInt(partes[6].trim());
            String categoria = partes[7].trim();
            int dificultad = Integer.parseInt(partes[8].trim());

            if (dificultad < 1 || dificultad > 3)
                return null;
            if (respuesta < 1 || respuesta > 4)
                return null;

            return new Pregunta(id, enunciado, opciones, respuesta,
                    categoria, dificultad);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public void cargarPreguntasPorDefecto(ArbolBinarioBusqueda arbol) {
        Pregunta[] preguntas = {
                new Pregunta(1, "Cuanto es 5 + 3?",
                        new String[] { "6", "8", "10", "7" }, 2, "Matematicas", 1),
                new Pregunta(2, "Cual es la capital de Colombia?",
                        new String[] { "Medellin", "Cali", "Bogota", "Barranquilla" },
                        3, "Geografia", 1),
                new Pregunta(3, "Cuantos lados tiene un triangulo?",
                        new String[] { "2", "3", "4", "5" }, 2, "Geometria", 1),
                new Pregunta(4, "Cual es el color del cielo despejado?",
                        new String[] { "Verde", "Rojo", "Azul", "Amarillo" },
                        3, "General", 1),
                new Pregunta(5, "En que continente esta Brasil?",
                        new String[] { "Africa", "Europa", "Asia", "America" },
                        4, "Geografia", 1),
                new Pregunta(6, "Cuanto es 12 x 12?",
                        new String[] { "124", "144", "122", "132" }, 2, "Matematicas", 2),
                new Pregunta(7, "Que gas producen las plantas al fotosintetizar?",
                        new String[] { "CO2", "Nitrogeno", "Oxigeno", "Hidrogeno" },
                        3, "Ciencias", 2),
                new Pregunta(8, "Cuantos planetas tiene el sistema solar?",
                        new String[] { "7", "8", "9", "10" }, 2, "Ciencias", 2),
                new Pregunta(9, "Quien escribio Cien anos de soledad?",
                        new String[] { "Pablo Neruda", "Cesar Vallejo",
                                "Gabriel Garcia Marquez", "Mario Vargas Llosa" },
                        3, "Literatura", 2),
                new Pregunta(10, "Elemento mas abundante en la corteza terrestre?",
                        new String[] { "Hierro", "Silicio", "Oxigeno", "Aluminio" },
                        3, "Ciencias", 2),
                new Pregunta(11, "Cuanto es la raiz cuadrada de 144?",
                        new String[] { "11", "12", "13", "14" }, 2, "Matematicas", 3),
                new Pregunta(12, "En que ano se fundo la ONU?",
                        new String[] { "1940", "1945", "1950", "1955" },
                        2, "Historia", 3),
                new Pregunta(13, "Cual es la constante de Planck aproximada?",
                        new String[] { "6.626e-34", "3.14e-10", "1.38e-23", "9.11e-31" },
                        1, "Fisica", 3),
                new Pregunta(14, "Que algoritmo es O(n log n) en promedio?",
                        new String[] { "Burbuja", "Insercion", "QuickSort", "Seleccion" },
                        3, "Computacion", 3),
                new Pregunta(15, "Cuantos huesos tiene el cuerpo humano adulto?",
                        new String[] { "196", "206", "216", "226" }, 2, "Biologia", 3)
        };
        for (Pregunta p : preguntas) {
            arbol.insertar(p);
        }
    }
}
