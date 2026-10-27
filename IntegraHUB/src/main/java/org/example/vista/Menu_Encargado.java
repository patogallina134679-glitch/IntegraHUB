package org.example.vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu_Encargado {
    private static final BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));

    public static void menu() throws IOException {
        int opcion = 0;

        while (opcion != 8) {
            System.out.println("\n--- MENÚ DE ENCARGADO ---");
            System.out.println("1. MENU DE ARTICULOS");
            System.out.println("2. MENU DE ASISTENCIAS");
            System.out.println("3. MENU DE CLIENTES");
            System.out.println("4. MENU DE DETALLES DE MOVIMIENTOS");
            System.out.println("5. MENU DE MOVIMIENTOS");
            System.out.println("6. MENU DE PROVEEDORES");
            System.out.println("7. CERRAR SESION");
            System.out.println("8. SALIR");
            System.out.print("Selecciona una opción: ");

            try {
                opcion = Integer.parseInt(leer.readLine());
                switch (opcion) {
                    case 1:
                        Menu_Articulo.menu();
                        break;
                    case 2:
                        Menu_Asistencia.menu();
                        break;
                    case 3:
                        Menu_Cliente.menu();
                        break;
                    case 4:
                        Menu_Detalles.menu();
                        break;
                    case 5:
                        Menu_Movimiento.menu();
                        break;
                    case 6:
                        Menu_Proveedor.menu();
                        break;
                    case 7:
                        Menu_Sesion.menu();
                        break;
                    case 8:
                        System.exit(0);
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
                opcion = 0;
            }
        }
    }
}
