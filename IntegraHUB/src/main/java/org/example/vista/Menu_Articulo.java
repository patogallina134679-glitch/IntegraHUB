package org.example.vista;

import org.example.dao.ArticuloDAO;
import org.example.modelo.Articulo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Menu_Articulo {
    private static final BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));
    private static final ArticuloDAO articuloDAO = new ArticuloDAO();
    private static final Articulo articulo = new Articulo();

    public static void insertar() throws IOException {
        System.out.println("--- Registrar nuevo artículo ---");
        System.out.println("ID Producto: ");
        articulo.setId_Producto(Integer.parseInt(leer.readLine()));
        System.out.println("Tipo de Producto: ");
        articulo.setTipoProducto(leer.readLine());
        System.out.println("Nombre: ");
        articulo.setNombre(leer.readLine());
        System.out.println("Cantidad: ");
        articulo.setCantidad(Float.parseFloat(leer.readLine()));
        System.out.println("Modelo: ");
        articulo.setModelo(leer.readLine());
        System.out.println("Color: ");
        articulo.setColor(leer.readLine());
        System.out.println("Producto de: ");
        articulo.setProducto_De(leer.readLine());
        System.out.println("ID Proveedor: ");
        articulo.setId_Proveedor1(Integer.parseInt(leer.readLine()));
        System.out.println("ID Almacén: ");
        articulo.setId_Almacen5(Integer.parseInt(leer.readLine()));

        articuloDAO.insertarArticulo(articulo);
    }

    public static void mostrar() {
        System.out.println("--- Lista de artículos registrados ---");
        ArrayList<Articulo> articulos = articuloDAO.extraerArticulo();
        for (Articulo a : articulos) {
            System.out.println("-------------------------------");
            System.out.println(a);
        }
    }

    public static void modificar() throws IOException {
        System.out.println("--- Modificar artículo por ID ---");
        System.out.println("ID del producto a modificar: ");
        articulo.setId_Producto(Integer.parseInt(leer.readLine()));
        System.out.println("Nuevo Tipo de Producto: ");
        articulo.setTipoProducto(leer.readLine());
        System.out.println("Nuevo Nombre: ");
        articulo.setNombre(leer.readLine());
        System.out.println("Nueva Cantidad: ");
        articulo.setCantidad(Float.parseFloat(leer.readLine()));
        System.out.println("Nuevo Modelo: ");
        articulo.setModelo(leer.readLine());
        System.out.println("Nuevo Color: ");
        articulo.setColor(leer.readLine());
        System.out.println("Nuevo Producto de: ");
        articulo.setProducto_De(leer.readLine());
        System.out.println("Nuevo ID Proveedor: ");
        articulo.setId_Proveedor1(Integer.parseInt(leer.readLine()));
        System.out.println("Nuevo ID Almacén: ");
        articulo.setId_Almacen5(Integer.parseInt(leer.readLine()));

        articuloDAO.modificarArticulo(articulo);
    }

    public static void borrar() throws IOException {
        System.out.println("--- Borrar artículo por ID ---");
        System.out.println("ID del producto a borrar: ");
        articulo.setId_Producto(Integer.parseInt(leer.readLine()));
        boolean borrado = articuloDAO.borrarArticulo(articulo);
        if (borrado) {
            System.out.println("¡Artículo borrado con éxito!");
        } else {
            System.out.println("No se pudo borrar el artículo.");
        }
    }

    public static void buscar() throws IOException {
        System.out.println("--- Buscar artículo por ID o Nombre ---");
        System.out.println("Introduce el ID (o pon 0 si solo buscas por nombre) / Introduce el Nombre (o parte de él): ");

        // Nota: El DAO busca por ID exacto o por coincidencia parcial en el nombre.
        System.out.println("ID del producto a buscar: ");
        articulo.setId_Producto(Integer.parseInt(leer.readLine()));
        System.out.println("Nombre (o parte del nombre) a buscar: ");
        articulo.setNombre(leer.readLine());

        ArrayList<Articulo> articulos = articuloDAO.buscarArticulo(articulo);

        if (articulos.isEmpty()) {
            System.out.println("No se encontró ningún artículo con esos criterios.");
        } else {
            for (Articulo a : articulos) {
                System.out.println("-------------------------------");
                System.out.println(a);
            }
        }
    }

    public static void menu() throws IOException {
        int opcion = 0;

        while (opcion != 7) {
            System.out.println("\n--- MENÚ DE CONTROL EN ARTÍCULOS ---");
            System.out.println("1. Registrar nuevo artículo");
            System.out.println("2. Mostrar todos los artículos");
            System.out.println("3. Modificar un artículo");
            System.out.println("4. Borrar un artículo");
            System.out.println("5. Buscar artículo");
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
                    case 6: opcion = 7; /* Menu.menu(); */ break;
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