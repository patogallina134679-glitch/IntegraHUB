package org.example.vista;

import org.example.dao.MovimientoDAO;
import org.example.modelo.Movimiento;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Menu_Movimiento {
    private static final BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));
    private static final MovimientoDAO movimientoDAO = new MovimientoDAO();

    public static void insertar() throws IOException {
        System.out.println("--- Registrar nuevo movimiento ---");
        Movimiento movimiento = new Movimiento();

        System.out.println("Código de Movimiento: ");
        movimiento.setCod_Movimiento(Integer.parseInt(leer.readLine()));
        System.out.println("ID Almacén: ");
        movimiento.setId_Almacen6(Integer.parseInt(leer.readLine()));
        System.out.println("Movimiento de: ");
        movimiento.setMovimiento_de_(leer.readLine());

        System.out.println("ID Cliente (opcional, presiona Enter si no aplica): ");
        String clienteStr = leer.readLine();
        movimiento.setId_Cliente1(clienteStr.isEmpty() ? null : Integer.parseInt(clienteStr));

        System.out.println("ID Proveedor (opcional, presiona Enter si no aplica): ");
        String proveedorStr = leer.readLine();
        movimiento.setId_Proveedor2(proveedorStr.isEmpty() ? null : Integer.parseInt(proveedorStr));

        System.out.println("ID Empleado: ");
        movimiento.setId_Empleado3(Integer.parseInt(leer.readLine()));
        System.out.println("Descripción: ");
        movimiento.setDescripcion(leer.readLine());

        System.out.println("Fecha de Registro (YYYY-MM-DD): ");
        String regStr = leer.readLine();
        movimiento.setFech_Registro(regStr.isEmpty() ? null : LocalDate.parse(regStr));

        System.out.println("Fecha de Orden (YYYY-MM-DD): ");
        String ordenStr = leer.readLine();
        movimiento.setFech_Orden(ordenStr.isEmpty() ? null : LocalDate.parse(ordenStr));

        System.out.println("Fecha Concluido (YYYY-MM-DD, opcional): ");
        String concluidoStr = leer.readLine();
        movimiento.setFech_Concluido(concluidoStr.isEmpty() ? null : LocalDate.parse(concluidoStr));

        System.out.println("Calificación: ");
        movimiento.setCalificacion(Integer.parseInt(leer.readLine()));
        System.out.println("Descripción de Calificación: ");
        movimiento.setDesc_Calificacion(leer.readLine());

        movimientoDAO.insertarMovimiento(movimiento);
    }

    public static void mostrar() {
        System.out.println("--- Lista de movimientos registrados ---");
        ArrayList<Movimiento> movimientos = movimientoDAO.extraerMovimientos();
        for (Movimiento m : movimientos) {
            System.out.println("-------------------------------");
            System.out.println(m);
        }
    }

    public static void modificar() throws IOException {
        System.out.println("--- Modificar movimiento por Código ---");
        Movimiento movimiento = new Movimiento();

        System.out.println("Código del movimiento a modificar: ");
        movimiento.setCod_Movimiento(Integer.parseInt(leer.readLine()));
        System.out.println("Nuevo ID Almacén: ");
        movimiento.setId_Almacen6(Integer.parseInt(leer.readLine()));
        System.out.println("Nuevo Movimiento de: ");
        movimiento.setMovimiento_de_(leer.readLine());

        System.out.println("Nuevo ID Cliente (opcional): ");
        String clienteStr = leer.readLine();
        movimiento.setId_Cliente1(clienteStr.isEmpty() ? null : Integer.parseInt(clienteStr));

        System.out.println("Nuevo ID Proveedor (opcional): ");
        String proveedorStr = leer.readLine();
        movimiento.setId_Proveedor2(proveedorStr.isEmpty() ? null : Integer.parseInt(proveedorStr));

        System.out.println("Nuevo ID Empleado: ");
        movimiento.setId_Empleado3(Integer.parseInt(leer.readLine()));
        System.out.println("Nueva Descripción: ");
        movimiento.setDescripcion(leer.readLine());

        System.out.println("Nueva Fecha de Registro (YYYY-MM-DD): ");
        String regStr = leer.readLine();
        movimiento.setFech_Registro(regStr.isEmpty() ? null : LocalDate.parse(regStr));

        System.out.println("Nueva Fecha de Orden (YYYY-MM-DD): ");
        String ordenStr = leer.readLine();
        movimiento.setFech_Orden(ordenStr.isEmpty() ? null : LocalDate.parse(ordenStr));

        System.out.println("Nueva Fecha Concluido (YYYY-MM-DD, opcional): ");
        String concluidoStr = leer.readLine();
        movimiento.setFech_Concluido(concluidoStr.isEmpty() ? null : LocalDate.parse(concluidoStr));

        System.out.println("Nueva Calificación: ");
        movimiento.setCalificacion(Integer.parseInt(leer.readLine()));
        System.out.println("Nueva Descripción de Calificación: ");
        movimiento.setDesc_Calificacion(leer.readLine());

        movimientoDAO.modificarMovimiento(movimiento);
    }

    public static void borrar() throws IOException {
        System.out.println("--- Borrar movimiento por Código ---");
        Movimiento movimiento = new Movimiento();

        System.out.println("Código del movimiento a borrar: ");
        movimiento.setCod_Movimiento(Integer.parseInt(leer.readLine()));
        boolean borrado = movimientoDAO.borrarMovimiento(movimiento);
        if (borrado) {
            System.out.println("¡Movimiento borrado con éxito!");
        } else {
            System.out.println("No se pudo borrar el movimiento.");
        }
    }

    public static void buscar() throws IOException {
        System.out.println("--- Buscar movimiento por Código ---");
        Movimiento movimiento = new Movimiento();

        System.out.println("Código del movimiento a buscar: ");
        movimiento.setCod_Movimiento(Integer.parseInt(leer.readLine()));
        ArrayList<Movimiento> movimientos = movimientoDAO.buscarMovimiento(movimiento);

        if (movimientos.isEmpty()) {
            System.out.println("No se encontró ningún movimiento con ese código.");
        } else {
            for (Movimiento m : movimientos) {
                System.out.println("-------------------------------");
                System.out.println(m);
            }
        }
    }

    public static void buscarPorFecha() throws IOException {
        System.out.println("--- Buscar movimientos por Fecha de Registro ---");
        System.out.println("Fecha de Registro (YYYY-MM-DD): ");
        try {
            LocalDate fechaBusqueda = LocalDate.parse(leer.readLine());
            ArrayList<Movimiento> movimientos = movimientoDAO.buscarMovimientoPorFecha(fechaBusqueda);

            if (movimientos.isEmpty()) {
                System.out.println("No se encontraron movimientos en esa fecha.");
            } else {
                for (Movimiento m : movimientos) {
                    System.out.println("-------------------------------");
                    System.out.println(m);
                }
            }
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Utiliza YYYY-MM-DD.");
        }
    }

    public static void menu() throws IOException {
        int opcion = 0;

        while (opcion != 8) {
            System.out.println("\n--- MENÚ DE CONTROL EN MOVIMIENTOS ---");
            System.out.println("1. Registrar nuevo movimiento");
            System.out.println("2. Mostrar todos los movimientos");
            System.out.println("3. Modificar un movimiento");
            System.out.println("4. Borrar un movimiento");
            System.out.println("5. Buscar movimiento por Código");
            System.out.println("6. Buscar movimientos por Fecha");
            System.out.println("7. Regresar");
            System.out.println("8. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                opcion = Integer.parseInt(leer.readLine());
                switch (opcion) {
                    case 1: insertar(); break;
                    case 2: mostrar(); break;
                    case 3: modificar(); break;
                    case 4: borrar(); break;
                    case 5: buscar(); break;
                    case 6: buscarPorFecha(); break;
                    case 7: opcion = 8; break;
                    case 8: System.out.println("Saliendo del sistema..."); break;
                    default: System.out.println("Opción no válida"); break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
                opcion = 0;
            }
        }
    }
}