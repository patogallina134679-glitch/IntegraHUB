package org.example.vista;

import org.example.dao.UsuarioDAO;
import org.example.modelo.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu_Sesion {
    private static final BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));
    private static final UsuarioDAO  usuarioDAO = new UsuarioDAO();
    private static final Usuario usuario = new Usuario();

    public static void validarUsuario() throws IOException {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.print("Usuario: ");
        usuario.setUsuario(leer.readLine().trim());
        System.out.print("Contraseña: ");
        usuario.setContrasena(leer.readLine().trim());

        Usuario usuarioLogueado = usuarioDAO.validarUsuario(usuario);
        if (usuarioLogueado != null) {
            System.out.println("¡Bienvenido, " + usuarioLogueado.getUsuario() + "  /" + usuarioLogueado.getNivel_Pri() + "! Ingresó con éxito.");
            if (usuarioLogueado.getNivel_Pri().equals("administrador")) {
                Menu_Admin.menu();
            }else if (usuarioLogueado.getNivel_Pri().equals("encargado")) {
                Menu_Encargado.menu();
            }else if (usuarioLogueado.getNivel_Pri().equals("empleado") || usuarioLogueado.getNivel_Pri().equals(null)) {
                Menu_Consultas.menu();
            }
        } else {
            System.out.println("Error: Usuario o contraseña incorrectos.");
        }
    }

    public static void sinUsuario() throws IOException {
        System.out.println("Porfavor dile a tu encargado de turno que requieres un usuario para entrar");
    }

    public static void menu() throws IOException {
        int opcion = 0;

        while (opcion != 4) {
            System.out.println("\n--- MENÚ DE SESION ---");
            System.out.println("1. CON MI USUARIO");
            System.out.println("2. NO TENGO USUARIO");
            System.out.println("3. REGRESAR");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                opcion = Integer.parseInt(leer.readLine());
                switch (opcion) {
                    case 1: validarUsuario() ; break;
                    case 2: sinUsuario(); break;
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
