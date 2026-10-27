package org.example.dao;

import org.example.Config.Conexion;
import org.example.modelo.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpleadoDAO {

    // Extrae todos los datos de la tabla Empleados
    public ArrayList<Empleado> extraerEmpleados() {
        ArrayList<Empleado> empleadosBD = new ArrayList<Empleado>();
        String sql = "SELECT * FROM empleados";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Empleado empleado = new Empleado();

                empleado.setId_Empleado(rs.getInt("Id_empleado"));
                empleado.setNombre(rs.getString("Nombre"));
                empleado.setTel1(rs.getString("Tel1"));
                empleado.setTel2(rs.getString("Tel2")); // Puede recibir nulo directamente
                empleado.setPuesto(rs.getString("Puesto"));
                empleado.setArea(rs.getString("Area"));
                empleado.setDireccion(rs.getString("Direccion"));
                empleado.setRfc(rs.getString("Rfc"));
                empleado.setCurp(rs.getString("Curp"));
                empleado.setId_Almacen1(rs.getInt("Id_almacen1"));

                empleadosBD.add(empleado);
            }
        } catch (SQLException err) {
            System.err.println("Error al extraer los datos " + err.getMessage());
        }

        return empleadosBD;
    }

    //Borrar el empleado pidiendo su id
    public boolean borrarEmpleado(Empleado empleado) {
        boolean eliminado = false;
        String sql = "DELETE FROM `integraHUB`.`empleados` WHERE Id_empleado = ?";
        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, empleado.getId_Empleado());
            int filasAfectadas = stm.executeUpdate();

            if (filasAfectadas > 0) {
                eliminado = true;
            }

        } catch (SQLException err) {
            System.out.println("Error al borrar al empleado " + err.getMessage());
        }

        return eliminado;
    }

    // Buscar empleados
    public ArrayList<Empleado> buscarEmpleado(Empleado empleado) {
        ArrayList<Empleado> empleadosBD = new ArrayList<Empleado>();
        String sql = "SELECT * FROM empleados WHERE Id_empleado = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, empleado.getId_Empleado());

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Empleado emp = new Empleado();

                    emp.setId_Empleado(rs.getInt("Id_empleado"));
                    emp.setNombre(rs.getString("Nombre"));
                    emp.setTel1(rs.getString("Tel1"));
                    emp.setTel2(rs.getString("Tel2")); // Puede recibir nulo directamente
                    emp.setPuesto(rs.getString("Puesto"));
                    emp.setArea(rs.getString("Area"));
                    emp.setDireccion(rs.getString("Direccion"));
                    emp.setRfc(rs.getString("Rfc")); // Nota: Se estandarizó 'Rfc' para coincidir con la base de datos
                    emp.setCurp(rs.getString("Curp"));
                    emp.setId_Almacen1(rs.getInt("Id_almacen1"));

                    empleadosBD.add(emp);
                }
            }

        } catch (SQLException err) {
            // Nota: Se corrigió el texto del error de "cliente" a "empleado"
            System.err.println("Error al buscar al empleado: " + err.getMessage());
        }

        return empleadosBD;
    }

    // Modificar empleado por medio de su id
    public boolean modificarEmpleado(Empleado empleado) {
        boolean actualizado = false;
        String sql = "UPDATE empleados SET Nombre = ?, Tel1 = ?, Tel2 = ?, Puesto = ?, Area = ?, Direccion = ?, Rfc = ?, Curp = ?, Id_almacen1 = ? WHERE Id_empleado = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setString(1, empleado.getNombre());
            stm.setString(2, empleado.getTel1());
            stm.setString(3, empleado.getTel2()); // Acepta nulo si la variable lo es
            stm.setString(4, empleado.getPuesto());
            stm.setString(5, empleado.getArea());
            stm.setString(6, empleado.getDireccion());
            stm.setString(7, empleado.getRfc());
            stm.setString(8, empleado.getCurp());
            stm.setInt(9, empleado.getId_Almacen1());
            stm.setInt(10, empleado.getId_Empleado());

            int registrosAfectados = stm.executeUpdate();
            if (registrosAfectados > 0) {
                System.out.println("Empleado actualizado correctamente");
                actualizado = true;
            } else {
                System.out.println("No se encontró el ID del empleado");
            }
        } catch (SQLException err) {
            System.out.println("Error al actualizar el empleado " + err.getMessage());
        }

        return actualizado;
    }

    // Insertar empleado
    public boolean insertarEmpleado(Empleado empleado) {
        boolean insertado = false;
        String sql = "INSERT INTO empleados (Nombre, Tel1, Tel2, Puesto, Area, Direccion, Rfc, Curp, Id_almacen1) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setString(1, empleado.getNombre());
            stm.setString(2, empleado.getTel1());
            stm.setString(3, empleado.getTel2()); // Acepta nulo directamente si viene como null
            stm.setString(4, empleado.getPuesto());
            stm.setString(5, empleado.getArea());
            stm.setString(6, empleado.getDireccion());
            stm.setString(7, empleado.getRfc());
            stm.setString(8, empleado.getCurp());
            stm.setInt(9, empleado.getId_Almacen1());

            int registrosAfectados = stm.executeUpdate();
            if (registrosAfectados > 0) {
                System.out.println("Empleado registrado correctamente");
                insertado = true;
            } else {
                System.out.println("No se pudo registrar el empleado");
            }
        } catch (SQLException err) {
            System.err.println("Error al insertar el empleado: " + err.getMessage());
        }

        return insertado;
    }
}