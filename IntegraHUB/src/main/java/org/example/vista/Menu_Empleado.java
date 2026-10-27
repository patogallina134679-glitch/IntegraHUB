package org.example.vista;

import org.example.dao.EmpleadoDAO;
import org.example.modelo.Empleado;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Menu_Empleado {
    private static final BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));
    private static final EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private static final Empleado empleado = new Empleado();

    public static void insertar() throws IOException {
        System.out.println("--- Registrar nuevo empleado ---");
        System.out.println("Nombre: ");
        empleado.setNombre(leer.readLine());
        System.out.println("Teléfono 1: ");
        empleado.setTel1(leer.readLine());
        System.out.println("Teléfono 2 (opcional, presiona Enter si no aplica): ");
        String tel2 = leer.readLine();
        empleado.setTel2(tel2.isEmpty() ? null : tel2);
        System.out.println("Puesto: ");
        empleado.setPuesto(leer.readLine());
        System.out.println("Área: ");
        empleado.setArea(leer.readLine());
        System.out.println("Dirección: ");
        empleado.setDireccion(leer.readLine());
        System.out.println("RFC: ");
        empleado.setRfc(leer.readLine());
        System.out.println("CURP: ");
        empleado.setCurp(leer.readLine());
        System.out.println("ID Almacén: ");
        empleado.setId_Almacen1(Integer.parseInt(leer.readLine()));

        empleadoDAO.insertarEmpleado(empleado);
    }

    public static void mostrar() {
        System.out.println("--- Lista de empleados registrados ---");
        ArrayList<Empleado> empleados = empleadoDAO.extraerEmpleados();
        for (Empleado e : empleados) {
            System.out.println("-------------------------------");
            System.out.println(e);
        }
    }

    public static void modificar() throws IOException {
        System.out.println("--- Modificar empleado por ID ---");
        System.out.println("ID del empleado a modificar: ");
        empleado.setId_Empleado(Integer.parseInt(leer.readLine()));
        System.out.println("Nuevo Nombre: ");
        empleado.setNombre(leer.readLine());
        System.out.println("Nuevo Teléfono 1: ");
        empleado.setTel1(leer.readLine());
        System.out.println("Nuevo Teléfono 2 (opcional): ");
        String tel2 = leer.readLine();
        empleado.setTel2(tel2.isEmpty() ? null : tel2);
        System.out.println("Nuevo Puesto: ");
        empleado.setPuesto(leer.readLine());
        System.out.println("Nueva Área: ");
        empleado.setArea(leer.readLine());
        System.out.println("Nueva Dirección: ");
        empleado.setDireccion(leer.readLine());
        System.out.println("Nuevo RFC: ");
        empleado.setRfc(leer.readLine());
        System.out.println("Nueva CURP: ");
        empleado.setCurp(leer.readLine());
        System.out.println("Nuevo ID Almacén: ");
        empleado.setId_Almacen1(Integer.parseInt(leer.readLine()));

        empleadoDAO.modificarEmpleado(empleado);
    }

    public static void borrar() throws IOException {
        System.out.println("--- Borrar empleado por ID ---");
        System.out.println("ID del empleado a borrar: ");
        empleado.setId_Empleado(Integer.parseInt(leer.readLine()));
        boolean borrado = empleadoDAO.borrarEmpleado(empleado);
        if (borrado) {
            System.out.println("¡Empleado borrado con éxito!");
        } else {
            System.out.println("No se pudo borrar el empleado.");
        }
    }

    public static void buscar() throws IOException {
        System.out.println("--- Buscar empleado por ID ---");
        System.out.println("ID del empleado a buscar: ");
        empleado.setId_Empleado(Integer.parseInt(leer.readLine()));
        ArrayList<Empleado> empleados = empleadoDAO.buscarEmpleado(empleado);

        if (empleados.isEmpty()) {
            System.out.println("No se encontró ningún empleado con ese ID.");
        } else {
            for (Empleado e : empleados) {
                System.out.println("-------------------------------");
                System.out.println(e);
            }
        }
    }

    public static void menu() throws IOException {
        int opcion = 0;

        while (opcion != 7) {
            System.out.println("\n--- MENÚ DE CONTROL EN EMPLEADOS ---");
            System.out.println("1. Registrar nuevo empleado");
            System.out.println("2. Mostrar todos los empleados");
            System.out.println("3. Modificar un empleado");
            System.out.println("4. Borrar un empleado");
            System.out.println("5. Buscar empleado");
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