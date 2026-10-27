package org.example.vista;

import org.example.dao.UsuarioDAO;
import org.example.modelo.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Menu_Usuario {
    private static final BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final Usuario usuario = new Usuario();

    public static void insertar() throws IOException {
        System.out.println("--- Registrar nuevo usuario ---");
        System.out.println("Nombre de Usuario: ");
        usuario.setUsuario(leer.readLine());
        System.out.println("Contraseña: ");
        usuario.setContrasena(leer.readLine());
        System.out.println("Nivel de Privilegio (nivel_pri): ");
        usuario.setNivel_Pri(leer.readLine());
        System.out.println("ID del Empleado (Id_empleado1): ");
        usuario.setId_Empleado1(Integer.parseInt(leer.readLine()));

        usuarioDAO.insertarUsuario(usuario);
    }

    public static void mostrar() {
        System.out.println("--- Lista de usuarios registrados ---");
        ArrayList<Usuario> usuarios = usuarioDAO.extraerUsuarios();
        for (Usuario u : usuarios) {
            System.out.println("-------------------------------");
            System.out.println(u);
        }
    }

    public static void modificar() throws IOException {
        System.out.println("--- Modificar usuario por ID ---");
        System.out.println("ID del usuario a modificar (Id_User): ");
        usuario.setId_User(Integer.parseInt(leer.readLine()));
        System.out.println("Nuevo Nombre de Usuario: ");
        usuario.setUsuario(leer.readLine());
        System.out.println("Nueva Contraseña: ");
        usuario.setContrasena(leer.readLine());
        System.out.println("Nuevo Nivel de Privilegio: ");
        usuario.setNivel_Pri(leer.readLine());
        System.out.println("Nuevo ID de Empleado: ");
        usuario.setId_Empleado1(Integer.parseInt(leer.readLine()));

        usuarioDAO.modificarUsuario(usuario);
    }

    public static void borrar() throws IOException {
        System.out.println("--- Borrar usuario por ID ---");
        System.out.println("ID del usuario a borrar (Id_User): ");
        usuario.setId_User(Integer.parseInt(leer.readLine()));
        boolean borrado = usuarioDAO.borrarUsuario(usuario);
        if (borrado) {
            System.out.println("¡Usuario borrado con éxito!");
        } else {
            System.out.println("No se pudo borrar el usuario.");
        }
    }

    public static void buscar() throws IOException {
        System.out.println("--- Buscar usuario por ID o Nombre ---");
        System.out.println("ID del usuario (o pon 0 si solo buscas por nombre): ");
        usuario.setId_User(Integer.parseInt(leer.readLine()));
        System.out.println("Nombre de Usuario (o parte de él) a buscar: ");
        usuario.setUsuario(leer.readLine());

        ArrayList<Usuario> usuarios = usuarioDAO.buscarUsuario(usuario);

        if (usuarios.isEmpty()) {
            System.out.println("No se encontró ningún usuario con esos criterios.");
        } else {
            for (Usuario u : usuarios) {
                System.out.println("-------------------------------");
                System.out.println(u);
            }
        }
    }

    public static void menu() throws IOException {
        int opcion = 0;

        while (opcion != 7) {
            System.out.println("\n--- MENÚ DE CONTROL EN USUARIOS ---");
            System.out.println("1. Registrar nuevo usuario");
            System.out.println("2. Mostrar todos los usuarios");
            System.out.println("3. Modificar un usuario");
            System.out.println("4. Borrar un usuario");
            System.out.println("5. Buscar usuario");
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