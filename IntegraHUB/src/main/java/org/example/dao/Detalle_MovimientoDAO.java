package org.example.dao;

import org.example.Config.Conexion;
import org.example.modelo.Detalle_Movimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Detalle_MovimientoDAO {

    // Extrae todos los datos de la tabla Detalle_Movimiento
    public ArrayList<Detalle_Movimiento> extraerDetalle_Movimiento() {
        ArrayList<Detalle_Movimiento> detalleMovimientosBD = new ArrayList<>();
        String sql = "SELECT * FROM detalle_movimiento";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Detalle_Movimiento detalle = new Detalle_Movimiento();

                detalle.setId_Detalle(rs.getInt("Id_detalle"));
                detalle.setCod_Movimientos1(rs.getInt("Cod_Movimientos1"));
                detalle.setId_Producto1(rs.getInt("id_producto1"));
                detalle.setCantidad(rs.getFloat("Cantidad"));

                detalleMovimientosBD.add(detalle);
            }
        } catch (SQLException err) {
            System.err.println("Error al extraer los datos: " + err.getMessage());
        }

        return detalleMovimientosBD;
    }

    // Borrar detalle de movimiento usando su id
    public boolean borrarDetalle_Movimiento(Detalle_Movimiento detalle_Movimiento) {
        boolean eliminado = false;
        String sql = "DELETE FROM detalle_movimiento WHERE Id_detalle = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, detalle_Movimiento.getId_Detalle());
            int filasAfectadas = stm.executeUpdate();

            if (filasAfectadas > 0) {
                eliminado = true;
                System.out.println("Detalle de movimiento eliminado correctamente");
            }

        } catch (SQLException err) {
            System.err.println("Error al borrar el detalle de movimiento: " + err.getMessage());
        }

        return eliminado;
    }

    // Buscar detalles de movimiento pidiendo su id
    public ArrayList<Detalle_Movimiento> buscarDetalle_Movimiento(Detalle_Movimiento detalle_Movimiento) {
        ArrayList<Detalle_Movimiento> detalleMovimientosBD = new ArrayList<>();
        String sql = "SELECT * FROM detalle_movimiento WHERE Cod_Movimientos1 = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, detalle_Movimiento.getCod_Movimientos1());

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Detalle_Movimiento detalle = new Detalle_Movimiento();

                    detalle.setId_Detalle(rs.getInt("Id_detalle"));
                    detalle.setCod_Movimientos1(rs.getInt("Cod_Movimientos1"));
                    detalle.setId_Producto1(rs.getInt("id_producto1"));
                    detalle.setCantidad(rs.getFloat("Cantidad"));

                    detalleMovimientosBD.add(detalle);
                }
            }

        } catch (SQLException err) {
            System.err.println("Error al buscar los detalles del movimiento: " + err.getMessage());
        }

        return detalleMovimientosBD;
    }

    // Modificar detalle de movimiento pidiendo su id
    public boolean modificarDetalle_Movimiento(Detalle_Movimiento detalle_Movimiento) {
        boolean actualizado = false;
        String sql = "UPDATE detalle_movimiento SET Cod_Movimientos1 = ?, id_producto1 = ?, Cantidad = ? WHERE Id_detalle = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, detalle_Movimiento.getCod_Movimientos1());
            stm.setInt(2, detalle_Movimiento.getId_Producto1());
            stm.setFloat(3, detalle_Movimiento.getCantidad());
            stm.setInt(4, detalle_Movimiento.getId_Detalle());

            int registrosAfectados = stm.executeUpdate();
            if (registrosAfectados > 0) {
                System.out.println("Detalle de movimiento actualizado correctamente");
                actualizado = true;
            } else {
                System.out.println("No se encontró el ID del detalle de movimiento");
            }
        } catch (SQLException err) {
            System.err.println("Error al actualizar el detalle de movimiento: " + err.getMessage());
        }

        return actualizado;
    }

    // Insertar detalle de movimiento
    public boolean insertarDetalle_Movimiento(Detalle_Movimiento detalle_Movimiento) {
        boolean insertado = false;
        String sql = "INSERT INTO detalle_movimiento (Cod_Movimientos1, id_producto1, Cantidad) VALUES (?, ?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, detalle_Movimiento.getCod_Movimientos1());
            stm.setInt(2, detalle_Movimiento.getId_Producto1());
            stm.setFloat(3, detalle_Movimiento.getCantidad());

            int registrosAfectados = stm.executeUpdate();
            if (registrosAfectados > 0) {
                System.out.println("Detalle de movimiento registrado correctamente");
                insertado = true;
            } else {
                System.out.println("No se pudo registrar el detalle de movimiento");
            }
        } catch (SQLException err) {
            System.err.println("Error al insertar el detalle de movimiento: " + err.getMessage());
        }

        return insertado;
    }
}