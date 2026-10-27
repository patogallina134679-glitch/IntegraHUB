package org.example.vista;

import org.example.dao.ProveedorDAO;
import org.example.modelo.Proveedor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Menu_Proveedor {
    private static final BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));
    private static final ProveedorDAO proveedorDAO = new ProveedorDAO();
    private static final Proveedor proveedor = new Proveedor();

    public static void insertar() throws IOException {
        System.out.println("--- Registrar nuevo proveedor ---");
        System.out.println("ID Proveedor: ");
        proveedor.setId_Proveedor(Integer.parseInt(leer.readLine()));
        System.out.println("Nombre: ");
        proveedor.setNombre(leer.readLine());
        System.out.println("Dirección: ");
        proveedor.setDireccion(leer.readLine());
        System.out.println("Especialidad: ");
        proveedor.setEspecialidad(leer.readLine());
        System.out.println("Teléfono 1: ");
        proveedor.setTel1(leer.readLine());
        System.out.println("Teléfono 2 (opcional, presiona Enter si no aplica): ");
        String tel2 = leer.readLine();
        proveedor.setTel2(tel2.isEmpty() ? null : tel2);
        System.out.println("Tipo de Material: ");
        proveedor.setTipo_Material(leer.readLine());
        System.out.println("ID Almacén: ");
        proveedor.setId_Almacen4(Integer.parseInt(leer.readLine()));

        proveedorDAO.insertarProveedor(proveedor);
    }

    public static void mostrar() {
        System.out.println("--- Lista de proveedores registrados ---");
        ArrayList<Proveedor> proveedores = proveedorDAO.extraerProveedores();
        for (Proveedor p : proveedores) {
            System.out.println("-------------------------------");
            System.out.println(p);
        }
    }

    public static void modificar() throws IOException {
        System.out.println("--- Modificar proveedor por ID ---");
        System.out.println("ID del proveedor a modificar: ");
        proveedor.setId_Proveedor(Integer.parseInt(leer.readLine()));
        System.out.println("Nuevo Nombre: ");
        proveedor.setNombre(leer.readLine());
        System.out.println("Nueva Dirección: ");
        proveedor.setDireccion(leer.readLine());
        System.out.println("Nueva Especialidad: ");
        proveedor.setEspecialidad(leer.readLine());
        System.out.println("Nuevo Teléfono 1: ");
        proveedor.setTel1(leer.readLine());
        System.out.println("Nuevo Teléfono 2 (opcional): ");
        String tel2 = leer.readLine();
        proveedor.setTel2(tel2.isEmpty() ? null : tel2);
        System.out.println("Nuevo Tipo de Material: ");
        proveedor.setTipo_Material(leer.readLine());
        System.out.println("Nuevo ID Almacén: ");
        proveedor.setId_Almacen4(Integer.parseInt(leer.readLine()));

        proveedorDAO.modificarProveedor(proveedor);
    }

    public static void borrar() throws IOException {
        System.out.println("--- Borrar proveedor por ID ---");
        System.out.println("ID del proveedor a borrar: ");
        proveedor.setId_Proveedor(Integer.parseInt(leer.readLine()));
        boolean borrado = proveedorDAO.borrarProveedor(proveedor);
        if (borrado) {
            System.out.println("¡Proveedor borrado con éxito!");
        } else {
            System.out.println("No se pudo borrar el proveedor.");
        }
    }

    public static void buscar() throws IOException {
        System.out.println("--- Buscar proveedor por ID o Nombre ---");
        System.out.println("ID del proveedor a buscar (o pon 0 si solo buscas por nombre): ");
        proveedor.setId_Proveedor(Integer.parseInt(leer.readLine()));
        System.out.println("Nombre (o parte del nombre) a buscar: ");
        proveedor.setNombre(leer.readLine());

        ArrayList<Proveedor> proveedores = proveedorDAO.buscarProveedor(proveedor);

        if (proveedores.isEmpty()) {
            System.out.println("No se encontró ningún proveedor con esos criterios.");
        } else {
            for (Proveedor p : proveedores) {
                System.out.println("-------------------------------");
                System.out.println(p);
            }
        }
    }

    public static void menu() throws IOException {
        int opcion = 0;

        while (opcion != 7) {
            System.out.println("\n--- MENÚ DE CONTROL EN PROVEEDORES ---");
            System.out.println("1. Registrar nuevo proveedor");
            System.out.println("2. Mostrar todos los proveedores");
            System.out.println("3. Modificar un proveedor");
            System.out.println("4. Borrar un proveedor");
            System.out.println("5. Buscar proveedor");
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