package org.example.vista;

import org.example.dao.AsistenciaDAO;
import org.example.modelo.Asistencia;
import org.example.modelo.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu_Asistencias {
    private static final BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));
    private static final AsistenciaDAO asistenciaDAO = new AsistenciaDAO();
    private static final Asistencia asistencia = new Asistencia();
    private static final Usuario usuario = new Usuario();

    public static void marcarEntrada() throws IOException {
        System.out.println("\n--- MARCAR ASISTENCIA ---");
        System.out.print("Identificacion del empleado (id): ");
        asistencia.setId_Empleado(Integer.parseInt(leer.readLine().trim()));

        boolean usuarioLlego = asistenciaDAO.marcarEntrada(asistencia);
        if (usuarioLlego != false) {
            System.out.println("¡Bienvenido, " + usuario.getUsuario() );
            Menu_Inicio.menu();
        } else {
            System.out.println("Error: Usuario o contraseña incorrectos.");
        }
    }

    public static void marcarSalida() throws IOException {
        System.out.println("\n--- MARCAR SALIDA ---");
        System.out.print("Identificacion del empleado (id): ");
        asistencia.setId_Empleado(Integer.parseInt(leer.readLine().trim()));

        boolean usuarioLlego = asistenciaDAO.marcarSalida(asistencia);
        if (usuarioLlego != false) {
            System.out.println("¡Asta luego, " + usuario.getUsuario() );
            Menu_Inicio.menu();
        } else {
            System.out.println("Error: Usuario o contraseña incorrectos.");
        }
    }

    public static void menu() throws IOException {
        int opcion = 0;

        while (opcion != 4) {
            System.out.println("\n--- MENÚ DE SESION ---");
            System.out.println("1. MARCAR ENTRADA");
            System.out.println("2. MARCAR SALIDA");
            System.out.println("3. REGRESAR");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                opcion = Integer.parseInt(leer.readLine());
                switch (opcion) {
                    case 1: marcarEntrada() ; break;
                    case 2: marcarSalida(); break;
                    case 3: Menu_Inicio.menu(); break;
                    case 4: System.exit(0); System.out.println("Saliendo del sistema..."); break;
                    default: System.out.println("Opción no válida"); break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
                opcion = 0;
            }
        }
    }
}
