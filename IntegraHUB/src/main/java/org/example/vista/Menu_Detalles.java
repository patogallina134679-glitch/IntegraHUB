package org.example.vista;

import org.example.dao.Detalle_MovimientoDAO;
import org.example.modelo.Detalle_Movimiento;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Menu_Detalles {
    private static final BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));
    private static final Detalle_MovimientoDAO detalleMovimientoDAO = new Detalle_MovimientoDAO();
    private static final Detalle_Movimiento detalleMovimiento = new Detalle_Movimiento();

    public static void insertar() throws IOException {
        System.out.println("--- Registrar nuevo detalle de movimiento ---");
        System.out.println("Código de Movimiento (Cod_Movimientos1): ");
        detalleMovimiento.setCod_Movimientos1(Integer.parseInt(leer.readLine()));
        System.out.println("ID de Producto (id_producto1): ");
        detalleMovimiento.setId_Producto1(Integer.parseInt(leer.readLine()));
        System.out.println("Cantidad: ");
        detalleMovimiento.setCantidad(Float.parseFloat(leer.readLine()));

        detalleMovimientoDAO.insertarDetalle_Movimiento(detalleMovimiento);
    }

    public static void mostrar() {
        System.out.println("--- Lista de detalles de movimiento registrados ---");
        ArrayList<Detalle_Movimiento> detalles = detalleMovimientoDAO.extraerDetalle_Movimiento();
        for (Detalle_Movimiento d : detalles) {
            System.out.println("-------------------------------");
            System.out.println(d);
        }
    }

    public static void modificar() throws IOException {
        System.out.println("--- Modificar detalle de movimiento por ID ---");
        System.out.println("ID del detalle a modificar (Id_detalle): ");
        detalleMovimiento.setId_Detalle(Integer.parseInt(leer.readLine()));
        System.out.println("Nuevo Código de Movimiento (Cod_Movimientos1): ");
        detalleMovimiento.setCod_Movimientos1(Integer.parseInt(leer.readLine()));
        System.out.println("Nuevo ID de Producto (id_producto1): ");
        detalleMovimiento.setId_Producto1(Integer.parseInt(leer.readLine()));
        System.out.println("Nueva Cantidad: ");
        detalleMovimiento.setCantidad(Float.parseFloat(leer.readLine()));

        detalleMovimientoDAO.modificarDetalle_Movimiento(detalleMovimiento);
    }

    public static void borrar() throws IOException {
        System.out.println("--- Borrar detalle de movimiento por ID ---");
        System.out.println("ID del detalle a borrar (Id_detalle): ");
        detalleMovimiento.setId_Detalle(Integer.parseInt(leer.readLine()));
        boolean borrado = detalleMovimientoDAO.borrarDetalle_Movimiento(detalleMovimiento);
        if (borrado) {
            System.out.println("¡Detalle de movimiento borrado con éxito!");
        } else {
            System.out.println("No se pudo borrar el detalle de movimiento.");
        }
    }

    public static void buscar() throws IOException {
        System.out.println("--- Buscar detalles por Código de Movimiento ---");
        System.out.println("Código de Movimiento a buscar (Cod_Movimientos1): ");
        detalleMovimiento.setCod_Movimientos1(Integer.parseInt(leer.readLine()));
        ArrayList<Detalle_Movimiento> detalles = detalleMovimientoDAO.buscarDetalle_Movimiento(detalleMovimiento);

        if (detalles.isEmpty()) {
            System.out.println("No se encontraron detalles para ese código de movimiento.");
        } else {
            for (Detalle_Movimiento d : detalles) {
                System.out.println("-------------------------------");
                System.out.println(d);
            }
        }
    }

    public static void menu() throws IOException {
        int opcion = 0;

        while (opcion != 7) {
            System.out.println("\n--- MENÚ DE CONTROL EN DETALLE MOVIMIENTOS ---");
            System.out.println("1. Registrar nuevo detalle de movimiento");
            System.out.println("2. Mostrar todos los detalles de movimiento");
            System.out.println("3. Modificar un detalle de movimiento");
            System.out.println("4. Borrar un detalle de movimiento");
            System.out.println("5. Buscar detalle de movimiento");
            System.out.println("6. Regresar");
            System.out.println("7. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                opcion = Integer.parseInt(leer.readLine());
                switch (opcion) {
                    case 1: insertar(); break;
                    case 2: mostrar(); break;
                    case 3: modificar(); break;
                    case 4: borrar(); break;
                    case 5: buscar(); break;
                    case 6: opcion = 7; break;
                    case 7: System.out.println("Saliendo del sistema..."); break;
                    default: System.out.println("Opción no válida"); break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
                opcion = 0;
            }
        }
    }
}