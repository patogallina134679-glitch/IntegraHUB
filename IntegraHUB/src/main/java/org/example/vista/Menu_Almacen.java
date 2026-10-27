package org.example.vista;

import org.example.dao.AlmacenDAO;
import org.example.modelo.Almacen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

    public class Menu_Almacen {
        private static final BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));
        private static final AlmacenDAO almacenDAO = new AlmacenDAO();
        private static final Almacen almacen = new Almacen();

        public static void insertar() throws IOException {
            System.out.println("--- Registrar nuevo almacén ---");
            System.out.println("ID Almacén: ");
            almacen.setId_Almacen(Integer.parseInt(leer.readLine()));
            System.out.println("Zona: ");
            almacen.setZona(leer.readLine());
            System.out.println("Piso: ");
            almacen.setPiso(Integer.parseInt(leer.readLine()));
            System.out.println("Dirección: ");
            almacen.setDireccion(leer.readLine());

            almacenDAO.insertarAlmacen(almacen);
        }

        public static void mostrar() {
            System.out.println("--- Lista de almacenes registrados ---");
            ArrayList<Almacen> almacenes = almacenDAO.extraerAlmacen();
            for (Almacen a : almacenes) {
                System.out.println("-------------------------------");
                System.out.println(a);
            }
        }

        public static void modificar() throws IOException {
            System.out.println("--- Modificar almacén por ID ---");
            System.out.println("ID del almacén a modificar: ");
            almacen.setId_Almacen(Integer.parseInt(leer.readLine()));
            System.out.println("Nueva Zona: ");
            almacen.setZona(leer.readLine());
            System.out.println("Nuevo Piso: ");
            almacen.setPiso(Integer.parseInt(leer.readLine()));
            System.out.println("Nueva Dirección: ");
            almacen.setDireccion(leer.readLine());

            almacenDAO.modificarAlmacen(almacen);
        }

        public static void borrar() throws IOException {
            System.out.println("--- Borrar almacén por ID ---");
            System.out.println("ID del almacén a borrar: ");
            almacen.setId_Almacen(Integer.parseInt(leer.readLine()));
            boolean borrado = almacenDAO.borrarAlmacen(almacen);
            if (borrado) {
                System.out.println("¡Almacén borrado con éxito!");
            } else {
                System.out.println("No se pudo borrar el almacén.");
            }
        }

        public static void buscar() throws IOException {
            System.out.println("--- Buscar almacén por ID ---");
            System.out.println("ID del almacén a buscar: ");
            almacen.setId_Almacen(Integer.parseInt(leer.readLine()));
            ArrayList<Almacen> almacenes = almacenDAO.buscarAlmacen(almacen);

            if (almacenes.isEmpty()) {
                System.out.println("No se encontró ningún almacén con ese ID.");
            } else {
                for (Almacen a : almacenes) {
                    System.out.println(a);
                }
            }
        }

        public static void menu() throws IOException {
            int opcion = 0;

            while (opcion != 7) {
                System.out.println("\n--- MENÚ DE CONTROL EN ALMACENES ---");
                System.out.println("1. Registrar nuevo almacén");
                System.out.println("2. Mostrar todos los almacenes");
                System.out.println("3. Modificar un almacén");
                System.out.println("4. Borrar un almacén");
                System.out.println("5. Buscar almacén");
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
                        case 7:
                            System.out.println("Saliendo del sistema...");
                            System.exit(0);
                            break;
                        default: System.out.println("Opción no válida"); break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Por favor, introduce un número válido.");
                    opcion = 0;
                }
            }
        }
    }

