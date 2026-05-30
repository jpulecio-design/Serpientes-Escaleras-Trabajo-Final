package consola;

import java.util.Scanner;

public class GestorEntrada {

    private Scanner scanner;

    public GestorEntrada() {
        this.scanner = new Scanner(System.in);
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

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

    public int leerOpcion(String mensaje, int min, int max) {
        return leerEntero(mensaje, min, max);
    }

    public void esperarEnter(String mensaje) {
        System.out.print(mensaje);
        scanner.nextLine();
    }

    public void pausar() {
        System.out.print("[Presione Enter para continuar...]");
        scanner.nextLine();
    }

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
