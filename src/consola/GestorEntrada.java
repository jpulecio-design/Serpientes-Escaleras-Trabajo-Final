package consola;

import java.util.Scanner;

public class GestorEntrada {

    private Scanner scanner;

    /*
     * Complejidad : O(1) o O(n) segun la longitud de la entrada
     */
    public GestorEntrada() {
        this.scanner = new Scanner(System.in);
    }
    // metodo para mostrar el mensaje antes de leer y
    //  retornar el texto ingresado por el usuario
    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
    /*
     * Lee un numero entero en el rango [min, max]
     * Repite la solicitud hasta recibir un valor valido
     * Complejidad: O(intentos)
     */
    public int leerEntero(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            try {
                String linea = scanner.nextLine().trim();
                int valor = Integer.parseInt(linea);
                if (valor >= min && valor <= max)
                    return valor;
                System.out.println("  Ingrese un valor entre " + min
                        + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  Entrada invalida. Ingrese un numero.");
            }
        }
    }
    /**
     * Lee una opcion numerica del usuario para responder preguntas
     * Alias de leerEntero para mayor claridad semantica
     * Complejidad: O(intentos)
     */
    public int leerOpcion(String mensaje, int min, int max) {
        return leerEntero(mensaje, min, max);
    }

    /*
     * Espera que el usuario presione Enter para continuar
     * Complejidad: O(1)
     */

    public void esperarEnter(String mensaje) {
        System.out.print(mensaje);
        scanner.nextLine();
    }

    /*
     * Pausa el juego esperando que el usuario presione Enter.
     * Complejidad: O(1)
     */

    public void pausar() {
        System.out.print("[Presione Enter para continuar...]");
        scanner.nextLine();
    }

    /*
     * Lee una confirmacion si/no del usuario.
     * Complejidad: O(intentos)
     */

    public boolean leerConfirmacion(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (respuesta.equals("s"))
                return true;
            if (respuesta.equals("n"))
                return false;
            System.out.println("  Ingrese 's' para si o 'n' para no.");
        }
    }

    public void cerrar() {
        scanner.close();
    }
}
