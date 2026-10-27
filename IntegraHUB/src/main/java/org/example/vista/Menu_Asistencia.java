package org.example.vista;

import org.example.dao.AsistenciaDAO;
import org.example.modelo.Asistencia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Menu_Asistencia {
    private static final BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));
    private static final AsistenciaDAO asistenciaDAO = new AsistenciaDAO();
    private static final Asistencia asistencia = new Asistencia();

    public static void marcarEntrada() throws IOException {
        System.out.println("--- Marcar Entrada de Empleado ---");
        System.out.println("ID del Empleado: ");
        asistencia.setId_Empleado(Integer.parseInt(leer.readLine()));

        asistenciaDAO.marcarEntrada(asistencia);
    }

    public static void marcarSalida() throws IOException {
        System.out.println("--- Marcar Salida de Empleado ---");
        System.out.println("ID del Empleado: ");
        asistencia.setId_Empleado(Integer.parseInt(leer.readLine()));

        asistenciaDAO.marcarSalida(asistencia);
    }

    public static void mostrar() {
        System.out.println("--- Lista de todas las asistencias ---");
        ArrayList<Asistencia> asistencias = asistenciaDAO.extraerAsistencia();
        for (Asistencia a : asistencias) {
            System.out.println("-------------------------------");
            System.out.println(a);
        }
    }

    public static void buscarPorId() throws IOException {
        System.out.println("--- Buscar asistencia por ID ---");
        System.out.println("ID de la asistencia: ");
        asistencia.setId_Asistencia(Integer.parseInt(leer.readLine()));

        ArrayList<Asistencia> asistencias = asistenciaDAO.buscarAsistenciaPorId(asistencia);
        if (asistencias.isEmpty()) {
            System.out.println("No se encontró ninguna asistencia con ese ID.");
        } else {
            for (Asistencia a : asistencias) {
                System.out.println("-------------------------------");
                System.out.println(a);
            }
        }
    }

    public static void buscarPorFecha() throws IOException {
        System.out.println("--- Buscar asistencia por Fecha ---");
        System.out.println("Fecha (formato YYYY-MM-DD): ");
        asistencia.setFecha(leer.readLine());

        ArrayList<Asistencia> asistencias = asistenciaDAO.buscarAsistenciaPorFecha(asistencia);
        if (asistencias.isEmpty()) {
            System.out.println("No se encontraron asistencias en esa fecha.");
        } else {
            for (Asistencia a : asistencias) {
                System.out.println("-------------------------------");
                System.out.println(a);
            }
        }
    }

    public static void modificar() throws IOException {
        System.out.println("--- Modificar registro de asistencia manualmente ---");
        System.out.println("ID de la asistencia a modificar: ");
        asistencia.setId_Asistencia(Integer.parseInt(leer.readLine()));
        System.out.println("Nueva Fecha (YYYY-MM-DD): ");
        asistencia.setFecha(leer.readLine());
        System.out.println("Nueva Hora de Entrada (HH:MM:SS): ");
        asistencia.setHoraEntrada(leer.readLine());
        System.out.println("Nueva Hora de Salida (HH:MM:SS): ");
        asistencia.setHoraSalida(leer.readLine());
        System.out.println("Total de horas (HH:MM:SS): ");
        asistencia.setTotalHr(leer.readLine());
        System.out.println("Nuevo ID del Empleado: ");
        asistencia.setId_Empleado(Integer.parseInt(leer.readLine()));

        asistenciaDAO.modificarAsistencia(asistencia);
    }

    public static void borrar() throws IOException {
        System.out.println("--- Borrar asistencia por ID ---");
        System.out.println("ID de la asistencia a eliminar: ");
        asistencia.setId_Asistencia(Integer.parseInt(leer.readLine()));

        asistenciaDAO.eliminarAsistencia(asistencia);
    }

    public static void menu() throws IOException {
        int opcion = 0;

        while (opcion != 9) {
            System.out.println("\n--- MENÚ DE CONTROL EN ASISTENCIAS ---");
            System.out.println("1. Marcar entrada");
            System.out.println("2. Marcar salida");
            System.out.println("3. Mostrar todas las asistencias");
            System.out.println("4. Buscar asistencia por ID");
            System.out.println("5. Buscar asistencia por Fecha");
            System.out.println("6. Modificar asistencia");
            System.out.println("7. Borrar asistencia");
            System.out.println("8. Regresar");
            System.out.println("9. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                opcion = Integer.parseInt(leer.readLine());
                switch (opcion) {
                    case 1: marcarEntrada(); break;
                    case 2: marcarSalida(); break;
                    case 3: mostrar(); break;
                    case 4: buscarPorId(); break;
                    case 5: buscarPorFecha(); break;
                    case 6: modificar(); break;
                    case 7: borrar(); break;
                    case 8: opcion = 9; break;
                    case 9: System.out.println("Saliendo del sistema..."); break;
                    default: System.out.println("Opción no válida"); break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
                opcion = 0;
            }
        }
    }
}
