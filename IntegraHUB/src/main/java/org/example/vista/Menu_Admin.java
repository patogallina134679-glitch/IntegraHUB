package org.example.vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu_Admin {
    private static final BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));

    public static void menu() throws IOException {
        int opcion = 0;

        while (opcion != 11) {
            System.out.println("\n--- MENÚ DE ADMINISTRADOR ---");
            System.out.println("1. MENU DE ALMACEN");
            System.out.println("2. MENU DE ARTICULOS");
            System.out.println("3. MENU DE ASISTENCIAS");
            System.out.println("4. MENU DE CLIENTES");
            System.out.println("5. MENU DE DETALLES DE MOVIMIENTOS");
            System.out.println("6. MENU DE EMPLEADOS");
            System.out.println("7. MENU DE MOVIMIENTOS");
            System.out.println("8. MENU DE PROVEEDORES");
            System.out.println("9. MENU DE USUARIOS");
            System.out.println("10. CERRAR SESION");
            System.out.println("11. SALIR");
            System.out.print("Selecciona una opción: ");

            try {
                opcion = Integer.parseInt(leer.readLine());
                switch (opcion) {
                    case 1:
                        Menu_Almacen.menu();
                        break;
                    case 2:
                        Menu_Articulo.menu();
                        break;
                    case 3:
                        Menu_Asistencia.menu();
                        break;
                    case 4:
                        Menu_Cliente.menu();
                        break;
                    case 5:
                        Menu_Detalles.menu();
                        break;
                    case 6:
                        Menu_Empleado.menu();
                        break;
                    case 7:
                        Menu_Movimiento.menu();
                        break;
                    case 8:
                        Menu_Proveedor.menu();
                        break;
                    case 9:
                        Menu_Usuario.menu();
                        break;
                    case 10:
                        Menu_Sesion.menu();
                        break;
                    case 11:
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
