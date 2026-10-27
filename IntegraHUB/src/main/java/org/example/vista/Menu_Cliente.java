package org.example.vista;

import org.example.dao.ClienteDAO;
import org.example.modelo.Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Menu_Cliente {
    private static final BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));
    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final Cliente cliente = new Cliente();

    public static void insertar() throws IOException {
        System.out.println("--- Registrar nuevo cliente ---");
        System.out.println("ID Cliente: ");
        cliente.setId_Cliente(Integer.parseInt(leer.readLine()));
        System.out.println("Nombre: ");
        cliente.setNombre(leer.readLine());
        System.out.println("Dirección: ");
        cliente.setDireccion(leer.readLine());
        System.out.println("Teléfono 1: ");
        cliente.setTel1(leer.readLine());
        System.out.println("Teléfono 2 (opcional, presiona Enter si no aplica): ");
        String tel2 = leer.readLine();
        cliente.setTel2(tel2.isEmpty() ? null : tel2);
        System.out.println("ID Almacén: ");
        cliente.setId_Almacen3(Integer.parseInt(leer.readLine()));

        clienteDAO.insertarCliente(cliente);
    }

    public static void mostrar() {
        System.out.println("--- Lista de clientes registrados ---");
        ArrayList<Cliente> clientes = clienteDAO.extraerCliente();
        for (Cliente c : clientes) {
            System.out.println("-------------------------------");
            System.out.println(c);
        }
    }

    public static void modificar() throws IOException {
        System.out.println("--- Modificar cliente por ID ---");
        System.out.println("ID del cliente a modificar: ");
        cliente.setId_Cliente(Integer.parseInt(leer.readLine()));
        System.out.println("Nuevo Nombre: ");
        cliente.setNombre(leer.readLine());
        System.out.println("Nueva Dirección: ");
        cliente.setDireccion(leer.readLine());
        System.out.println("Nuevo Teléfono 1: ");
        cliente.setTel1(leer.readLine());
        System.out.println("Nuevo Teléfono 2 (opcional): ");
        String tel2 = leer.readLine();
        cliente.setTel2(tel2.isEmpty() ? null : tel2);
        System.out.println("Nuevo ID Almacén: ");
        cliente.setId_Almacen3(Integer.parseInt(leer.readLine()));

        clienteDAO.modificarCliente(cliente);
    }

    public static void borrar() throws IOException {
        System.out.println("--- Borrar cliente por ID ---");
        System.out.println("ID del cliente a borrar: ");
        cliente.setId_Cliente(Integer.parseInt(leer.readLine()));
        boolean borrado = clienteDAO.borrarCliente(cliente);
        if (borrado) {
            System.out.println("¡Cliente borrado con éxito!");
        } else {
            System.out.println("No se pudo borrar el cliente.");
        }
    }

    public static void buscar() throws IOException {
        System.out.println("--- Buscar cliente por ID ---");
        System.out.println("ID del cliente a buscar: ");
        cliente.setId_Cliente(Integer.parseInt(leer.readLine()));
        ArrayList<Cliente> clientes = clienteDAO.buscarCliente(cliente);

        if (clientes.isEmpty()) {
            System.out.println("No se encontró ningún cliente con ese ID.");
        } else {
            for (Cliente c : clientes) {
                System.out.println("-------------------------------");
                System.out.println(c);
            }
        }
    }

    public static void menu() throws IOException {
        int opcion = 0;

        while (opcion != 7) {
            System.out.println("\n--- MENÚ DE CONTROL EN CLIENTES ---");
            System.out.println("1. Registrar nuevo cliente");
            System.out.println("2. Mostrar todos los clientes");
            System.out.println("3. Modificar un cliente");
            System.out.println("4. Borrar un cliente");
            System.out.println("5. Buscar cliente");
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