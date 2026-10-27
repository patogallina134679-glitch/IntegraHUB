package org.example.vista;

import org.example.dao.ArticuloDAO;
import org.example.dao.AsistenciaDAO;
import org.example.dao.Detalle_MovimientoDAO;
import org.example.dao.MovimientoDAO;
import org.example.modelo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;

public class Menu_Consultas {
    private static final BufferedReader leer = new BufferedReader(new InputStreamReader(System.in));
    private static final ArticuloDAO articuloDAO = new ArticuloDAO();
    private static final MovimientoDAO movimientoDAO = new MovimientoDAO();
    private static final Detalle_MovimientoDAO detalle_MovimientoDAO = new Detalle_MovimientoDAO();
    private static final AsistenciaDAO asistenciaDAO = new AsistenciaDAO();

    // Formas de consultar un articulo
    public static void consultaArticuloNo() throws IOException {
        System.out.println("\n--- BUSCAR ARTÍCULO POR NOMBRE ---");
        System.out.print("Nombre del artículo: ");
        Articulo articulo = new Articulo(); // Instancia local para evitar errores en multiusu
        articulo.setNombre(leer.readLine().trim());
        ArrayList<Articulo> articulos = articuloDAO.buscarArticulo(articulo);
        for (Articulo art : articulos) {
            System.out.println("-------------------------------");
            System.out.println(art);
        }
    }

    public static void consultaArticuloId() throws IOException {
        System.out.println("\n--- BUSCAR ARTÍCULO POR ID ---");
        System.out.print("ID del artículo: ");
        Articulo articulo = new Articulo(); // Instancia local para evitar errores en multiusu
        articulo.setId_Producto(Integer.parseInt(leer.readLine().trim()));
        ArrayList<Articulo> articulos = articuloDAO.buscarArticulo(articulo);
        for (Articulo art : articulos) {
            System.out.println("-------------------------------");
            System.out.println(art);
        }
    }

    // Formas de consultar un movimiento y sus detalles
    public static void consultaMovimientoCod() throws IOException {
        System.out.println("\n--- BUSCAR MOVIMIENTO POR CODIGO ---");
        System.out.print("CODIGO del movimiento: ");
        Movimiento movimiento = new Movimiento(); // Instancia local para evitar errores en multiusu
        movimiento.setCod_Movimiento(Integer.parseInt(leer.readLine().trim()));
        ArrayList<Movimiento> movimientos = movimientoDAO.buscarMovimiento(movimiento);
        for (Movimiento mov : movimientos) {
            System.out.println("-------------------------------");
            System.out.println(mov);
        }
    }

    public static void consultaMovimientoFech() throws IOException {
        System.out.println("\n--- BUSCAR MOVIMIENTOS POR FECHA ---");
        System.out.print("Fecha a buscar (YYYY-MM-DD): ");
        LocalDate fechareg = LocalDate.parse(leer.readLine().trim());

        // Llamada directa pasando el LocalDate
        ArrayList<Movimiento> movimientos = movimientoDAO.buscarMovimientoPorFecha(fechareg);
        for (Movimiento mov : movimientos) {
            System.out.println("-------------------------------");
            System.out.println(mov);
        }
    }

    public static void consultaDetallesCod() throws IOException {
        System.out.println("\n--- BUSCAR DETALLES DE MOVIMIENTO POR COD ---");
        System.out.print("CODIGO del movimiento: ");
        Detalle_Movimiento detalle_Movimiento = new Detalle_Movimiento(); // Instancia local para evitar errores en multiusu
        detalle_Movimiento.setCod_Movimientos1(Integer.parseInt(leer.readLine().trim()));
        ArrayList<Detalle_Movimiento> de_Movimientos = detalle_MovimientoDAO.buscarDetalle_Movimiento(detalle_Movimiento);
        for (Detalle_Movimiento dmov : de_Movimientos) {
            System.out.println("-------------------------------------------");
            System.out.println(dmov);
        }
    }

    // Se confirma si marcaste asistencia o ya saliste
    public static void consultaMiAsistencia() throws IOException {
        System.out.println("\n--- CONSULTAR MI ASISTENCIA DE HOY ---");
        System.out.print("Introduce el ID del empleado: ");
        Asistencia asistencia = new Asistencia(); // Instancia local para evitar errores en multiusu
        asistencia.setId_Empleado(Integer.parseInt(leer.readLine().trim()));

        // Llamamos al DAO para verificar el registro de hoy
        Asistencia asistenciaHoy = asistenciaDAO.obtenerAsistenciaHoyPorEmpleado(asistencia);

        if (asistenciaHoy.getFecha().isEmpty() || asistenciaHoy.getFecha() == null) {
            System.out.println("Aún no tienes ningún registro de asistencia el día de hoy.");
        } else {
            System.out.println("\n--- ESTADO DE ASISTENCIA ---");
            System.out.println("Fecha: " + asistenciaHoy.getFecha());
            System.out.println("Hora de Entrada: " + asistenciaHoy.getHoraEntrada());

            if (asistenciaHoy.getHoraSalida() == null || asistenciaHoy.getHoraSalida().isEmpty()) {
                System.out.println("Estado: ¡Ya marcaste entrada y te encuentras laborando! (Falta marcar salida)");
            } else {
                System.out.println("Hora de Salida: " + asistenciaHoy.getHoraSalida());
                System.out.println("Estado: Ya registraste tu salida del día de hoy. ¡Buen trabajo!");
            }
        }
    }

    public static void consultasArticulo() throws IOException {
        int opcionA = 0;

        while (opcionA != 3) {
            System.out.println("\n--- MENÚ DE ARTICULO ---");
            System.out.println("1. BUSCAR POR NOMBRE ");
            System.out.println("2. BUSCAR POR (ID)");
            System.out.println("3. REGRESAR");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                opcionA = Integer.parseInt(leer.readLine().trim());
                switch (opcionA) {
                    case 1:
                        consultaArticuloNo();
                        break;
                    case 2:
                        consultaArticuloId();
                        break;
                    case 3:
                        // Regresar al menú anterior
                        break;
                    case 4:
                        System.out.println("Saliendo del sistema...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
                opcionA = 0;
            }
        }
    }

    public static void consultasMovimiento() throws IOException {
        int opcionM = 0;

        while (opcionM != 4) {
            System.out.println("\n--- MENÚ DE MOVIMIENTOS ---");
            System.out.println("1. BUSCAR MOVIMIENTO POR CÓDIGO");
            System.out.println("2. DETALLES DE MOVIMIENTO");
            System.out.println("3. BUSCAR MOVIMIENTOS POR FECHA");
            System.out.println("4. REGRESAR");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                opcionM = Integer.parseInt(leer.readLine().trim());
                switch (opcionM) {
                    case 1:
                        consultaMovimientoCod();
                        break;
                    case 2:
                        consultaDetallesCod();
                        break;
                    case 3:
                        consultaMovimientoFech();
                        break;
                    case 4:
                        // Sale del while y regresa
                        break;
                    case 5:
                        System.out.println("Saliendo del sistema...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
                opcionM = 0;
            }
        }
    }

    public static void menu() throws IOException {
        int opcionMenu = 0;

        while (opcionMenu != 4) {
            System.out.println("\n--- MENÚ DE CONSULTAS ---");
            System.out.println("1. CONSULTAR ARTICULO");
            System.out.println("2. CONSULTAR MOVIMIENTO");
            System.out.println("3. CONSULTAR MI ASISTENCIA");
            System.out.println("4. REGRESAR");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                opcionMenu = Integer.parseInt(leer.readLine().trim());
                switch (opcionMenu) {
                    case 1:
                        consultasArticulo();
                        break;
                    case 2:
                        consultasMovimiento();
                        break;
                    case 3:
                        consultaMiAsistencia();
                        break;
                    case 4:
                        return;
                    case 5:
                        System.out.println("Saliendo del sistema...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
                opcionMenu = 0;
            }
        }
    }
}