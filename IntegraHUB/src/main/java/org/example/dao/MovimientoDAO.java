package org.example.dao;

import org.example.Config.Conexion;
import org.example.modelo.Movimiento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MovimientoDAO {

    // Extrae todos los datos de la tabla Movimientos
    public ArrayList<Movimiento> extraerMovimientos() {
        ArrayList<Movimiento> movimientosBD = new ArrayList<Movimiento>();
        String sql = "SELECT * FROM movimientos";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Movimiento movimiento = new Movimiento();

                movimiento.setCod_Movimiento(rs.getInt("Cod_Movimientos"));
                movimiento.setId_Almacen6(rs.getInt("Id_almacen6"));
                movimiento.setMovimiento_de_(rs.getString("Movimiento_de_"));

                // Manejo de id_Cliente1 (puede ser nulo en la BD)
                int idCliente = rs.getInt("Id_cliente1");
                movimiento.setId_Cliente1(rs.wasNull() ? null : idCliente);

                // Manejo de id_Proveedor2 (puede ser nulo en la BD)
                int idProveedor = rs.getInt("Id_proveedor2");
                movimiento.setId_Proveedor2(rs.wasNull() ? null : idProveedor);

                movimiento.setId_Empleado3(rs.getInt("Id_empleado3"));
                movimiento.setDescripcion(rs.getString("descripcion"));

                Date fechRegistro = rs.getDate("Fech_Registro");
                if (fechRegistro != null) {
                    movimiento.setFech_Registro(fechRegistro.toLocalDate());
                }

                Date fechOrden = rs.getDate("Fech_Orden");
                if (fechOrden != null) {
                    movimiento.setFech_Orden(fechOrden.toLocalDate());
                }

                Date fechConcluido = rs.getDate("Fech_Concluido");
                movimiento.setFech_Concluido(fechConcluido != null ? fechConcluido.toLocalDate() : null);

                movimiento.setCalificacion(rs.getInt("Calificacion"));
                movimiento.setDesc_Calificacion(rs.getString("desc_Calificacion"));

                movimientosBD.add(movimiento);
            }
        } catch (SQLException err) {
            System.err.println("Error al extraer los datos " + err.getMessage());
        }

        return movimientosBD;
    }

    // Borrar Movimiento pidiendo su codigo
    public boolean borrarMovimiento(Movimiento movimiento) {
        boolean eliminado = false;
        String sql = "DELETE FROM `integraHUB`.`movimientos` WHERE Cod_Movimientos = ?";
        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, movimiento.getCod_Movimiento());
            int filasAfectadas = stm.executeUpdate();

            if (filasAfectadas > 0) {
                eliminado = true;
            }

        } catch (SQLException err) {
            System.out.println("Error al borrar el movimiento " + err.getMessage());
        }

        return eliminado;
    }

    // Buscar movimientos
    public ArrayList<Movimiento> buscarMovimiento(Movimiento movimiento) {
        ArrayList<Movimiento> movimientosBD = new ArrayList<Movimiento>();
        String sql = "SELECT * FROM movimientos WHERE Cod_movimientos = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, movimiento.getCod_Movimiento());

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Movimiento mov = new Movimiento();

                    mov.setCod_Movimiento(rs.getInt("Cod_Movimientos"));
                    mov.setId_Almacen6(rs.getInt("Id_almacen6"));
                    mov.setMovimiento_de_(rs.getString("Movimiento_de_"));

                    // Manejo de id_Cliente1 (puede ser nulo en la BD)
                    int idCliente = rs.getInt("Id_cliente1");
                    mov.setId_Cliente1(rs.wasNull() ? null : idCliente);

                    // Manejo de id_Proveedor2 (puede ser nulo en la BD)
                    int idProveedor = rs.getInt("Id_proveedor2");
                    mov.setId_Proveedor2(rs.wasNull() ? null : idProveedor);

                    mov.setId_Empleado3(rs.getInt("Id_empleado3"));
                    mov.setDescripcion(rs.getString("descripcion"));

                    Date fechRegistro = rs.getDate("Fech_Registro");
                    if (fechRegistro != null) {
                        mov.setFech_Registro(fechRegistro.toLocalDate());
                    }

                    Date fechOrden = rs.getDate("Fech_Orden");
                    if (fechOrden != null) {
                        mov.setFech_Orden(fechOrden.toLocalDate());
                    }

                    Date fechConcluido = rs.getDate("Fech_Concluido");
                    mov.setFech_Concluido(fechConcluido != null ? fechConcluido.toLocalDate() : null);

                    mov.setCalificacion(rs.getInt("Calificacion"));
                    mov.setDesc_Calificacion(rs.getString("desc_Calificacion"));

                    movimientosBD.add(mov);
                }
            }

        } catch (SQLException err) {
            System.err.println("Error al buscar el movimiento: " + err.getMessage());
        }

        return movimientosBD;
    }

    // Buscar movimientos por Fecha de Registro recibiendo LocalDate directamente
    public ArrayList<Movimiento> buscarMovimientoPorFecha(LocalDate fechaBusqueda) {
        ArrayList<Movimiento> movimientosBD = new ArrayList<Movimiento>();
        String sql = "SELECT * FROM movimientos WHERE Fech_Registro = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setDate(1, java.sql.Date.valueOf(fechaBusqueda));

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Movimiento mov = new Movimiento();
                    mov.setCod_Movimiento(rs.getInt("Cod_Movimientos"));
                    mov.setId_Almacen6(rs.getInt("Id_almacen6"));
                    mov.setMovimiento_de_(rs.getString("Movimiento_de_"));

                    int idCliente = rs.getInt("Id_cliente1");
                    mov.setId_Cliente1(rs.wasNull() ? null : idCliente);

                    int idProveedor = rs.getInt("Id_proveedor2");
                    mov.setId_Proveedor2(rs.wasNull() ? null : idProveedor);

                    mov.setId_Empleado3(rs.getInt("Id_empleado3"));
                    mov.setDescripcion(rs.getString("descripcion"));

                    Date fechRegistro = rs.getDate("Fech_Registro");
                    if (fechRegistro != null) {
                        mov.setFech_Registro(fechRegistro.toLocalDate());
                    }

                    Date fechOrden = rs.getDate("Fech_Orden");
                    if (fechOrden != null) {
                        mov.setFech_Orden(fechOrden.toLocalDate());
                    }

                    Date fechConcluido = rs.getDate("Fech_Concluido");
                    mov.setFech_Concluido(fechConcluido != null ? fechConcluido.toLocalDate() : null);

                    mov.setCalificacion(rs.getInt("Calificacion"));
                    mov.setDesc_Calificacion(rs.getString("desc_Calificacion"));

                    movimientosBD.add(mov);
                }
            }

        } catch (SQLException err) {
            System.err.println("Error al buscar los movimientos por fecha: " + err.getMessage());
        }

        return movimientosBD;
    }

    // Modificar movimiento por medio de su Codigo
    public boolean modificarMovimiento(Movimiento movimiento) {
        boolean actualizado = false;
        String sql = "UPDATE movimientos SET Id_almacen6 = ?, Movimiento_de_ = ?, Id_cliente1 = ?, Id_proveedor2 = ?, " +
                "Id_empleado3 = ?, descripcion = ?, Fech_Registro = ?, Fech_Orden = ?, Fech_Concluido = ?, " +
                "Calificacion = ?, desc_Calificacion = ? WHERE Cod_Movimientos = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, movimiento.getId_Almacen6());
            stm.setString(2, movimiento.getMovimiento_de_());

            // Manejo de id_Cliente1 (puede ser nulo en la BD)
            if (movimiento.getId_Cliente1() != null) {
                stm.setInt(3, movimiento.getId_Cliente1());
            } else {
                stm.setNull(3, java.sql.Types.INTEGER);
            }

            // Manejo de id_Proveedor2 (puede ser nulo en la BD)
            if (movimiento.getId_Proveedor2() != null) {
                stm.setInt(4, movimiento.getId_Proveedor2());
            } else {
                stm.setNull(4, java.sql.Types.INTEGER);
            }

            stm.setInt(5, movimiento.getId_Empleado3());
            stm.setString(6, movimiento.getDescripcion());

            // Manejo de Fech_Registro
            if (movimiento.getFech_Registro() != null) {
                stm.setDate(7, java.sql.Date.valueOf(movimiento.getFech_Registro()));
            } else {
                stm.setNull(7, java.sql.Types.DATE);
            }

            // Manejo de Fech_Orden
            if (movimiento.getFech_Orden() != null) {
                stm.setDate(8, java.sql.Date.valueOf(movimiento.getFech_Orden()));
            } else {
                stm.setNull(8, java.sql.Types.DATE);
            }

            // Manejo de Fech_Concluido
            if (movimiento.getFech_Concluido() != null) {
                stm.setDate(9, java.sql.Date.valueOf(movimiento.getFech_Concluido()));
            } else {
                stm.setNull(9, java.sql.Types.DATE);
            }

            stm.setInt(10, movimiento.getCalificacion());
            stm.setString(11, movimiento.getDesc_Calificacion());

            // WHERE Cod_Movimientos
            stm.setInt(12, movimiento.getCod_Movimiento());

            int registrosAfectados = stm.executeUpdate();
            if (registrosAfectados > 0) {
                System.out.println("Movimiento actualizado correctamente");
                actualizado = true;
            } else {
                System.out.println("No se encontró el código del movimiento");
            }
        } catch (SQLException err) {
            System.out.println("Error al actualizar el movimiento " + err.getMessage());
        }

        return actualizado;
    }

    // Insertar movimiento
    public boolean insertarMovimiento(Movimiento movimiento) {
        boolean insertado = false;
        String sql = "INSERT INTO movimientos (Cod_Movimientos, Id_almacen6, Movimiento_de_, Id_cliente1, Id_proveedor2, Id_empleado3, descripcion, Fech_Registro, Fech_Orden, Fech_Concluido, Calificacion, desc_Calificacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, movimiento.getCod_Movimiento());
            stm.setInt(2, movimiento.getId_Almacen6());
            stm.setString(3, movimiento.getMovimiento_de_());

            // Manejo de id_Cliente1 (puede ser nulo en la BD)
            if (movimiento.getId_Cliente1() != null) {
                stm.setInt(4, movimiento.getId_Cliente1());
            } else {
                stm.setNull(4, java.sql.Types.INTEGER);
            }

            // Manejo de id_Proveedor2 (puede ser nulo en la BD)
            if (movimiento.getId_Proveedor2() != null) {
                stm.setInt(5, movimiento.getId_Proveedor2());
            } else {
                stm.setNull(5, java.sql.Types.INTEGER);
            }

            stm.setInt(6, movimiento.getId_Empleado3());
            stm.setString(7, movimiento.getDescripcion());

            // Manejo de Fech_Registro
            if (movimiento.getFech_Registro() != null) {
                stm.setDate(8, java.sql.Date.valueOf(movimiento.getFech_Registro()));
            } else {
                stm.setNull(8, java.sql.Types.DATE);
            }

            // Manejo de Fech_Orden
            if (movimiento.getFech_Orden() != null) {
                stm.setDate(9, java.sql.Date.valueOf(movimiento.getFech_Orden()));
            } else {
                stm.setNull(9, java.sql.Types.DATE);
            }

            // Manejo de Fech_Concluido
            if (movimiento.getFech_Concluido() != null) {
                stm.setDate(10, java.sql.Date.valueOf(movimiento.getFech_Concluido()));
            } else {
                stm.setNull(10, java.sql.Types.DATE);
            }

            stm.setInt(11, movimiento.getCalificacion());
            stm.setString(12, movimiento.getDesc_Calificacion());

            int registrosAfectados = stm.executeUpdate();
            if (registrosAfectados > 0) {
                System.out.println("Movimiento registrado correctamente");
                insertado = true;
            } else {
                System.out.println("No se pudo registrar el movimiento");
            }
        } catch (SQLException err) {
            System.err.println("Error al insertar el movimiento: " + err.getMessage());
        }

        return insertado;
    }
}