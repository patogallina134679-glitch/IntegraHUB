package org.example.dao;

import org.example.Config.Conexion;
import org.example.modelo.Proveedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProveedorDAO {

    // Extrae todos los datos de la tabla Proveedores
    public ArrayList<Proveedor> extraerProveedores() {
        ArrayList<Proveedor> proveedoresBD = new ArrayList<Proveedor>();
        String sql = "SELECT * FROM proveedores";
        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Proveedor proveedor = new Proveedor();

                proveedor.setId_Proveedor(rs.getInt("Id_proveedor"));
                proveedor.setNombre(rs.getString("Nombre"));
                proveedor.setDireccion(rs.getString("Direccion"));
                proveedor.setEspecialidad(rs.getString("Especialidad"));
                proveedor.setTel1(rs.getString("Tel1"));
                proveedor.setTel2(rs.getString("Tel2"));
                proveedor.setTipo_Material(rs.getString("Tipo_material"));
                proveedor.setId_Almacen4(rs.getInt("Id_almacen4"));

                proveedoresBD.add(proveedor);
            }
        } catch (SQLException err) {
            System.err.println("Error al extraer los datos " + err.getMessage());
        }
        return proveedoresBD;
    }

    //Borrando el proveedor pidiendo su id
    public boolean borrarProveedor(Proveedor proveedor) {
        boolean eliminado = false;
        String sql = "DELETE FROM `integraHUB`.`proveedor` WHERE Id_proveedor = ?";
        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, proveedor.getId_Proveedor());
            int filasAfectadas = stm.executeUpdate();

            if (filasAfectadas > 0) {
                eliminado = true;
            }

        } catch (SQLException err) {
            System.out.println("Error al borrar al proveedor " + err.getMessage());
        }

        return eliminado;
    }

    //Buscar proveedor por id o Nombre
    public ArrayList<Proveedor> buscarProveedor(Proveedor proveedor) {
        ArrayList<Proveedor> proveedoresBD = new ArrayList<Proveedor>();
        String sql = "SELECT * FROM proveedor WHERE Id_proveedor = ? OR Nombre LIKE ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, proveedor.getId_Proveedor());
            stm.setString(2, "%" + proveedor.getNombre() + "%");

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Proveedor prov = new Proveedor();

                    prov.setId_Proveedor(rs.getInt("Id_proveedor"));
                    prov.setNombre(rs.getString("Nombre"));
                    prov.setDireccion(rs.getString("Direccion"));
                    prov.setEspecialidad(rs.getString("Especialidad"));
                    prov.setTel1(rs.getString("Tel1"));
                    prov.setTel2(rs.getString("Tel2"));
                    prov.setTipo_Material(rs.getString("Tipo_material"));
                    prov.setId_Almacen4(rs.getInt("Id_almacen4"));

                    proveedoresBD.add(prov);
                }
            }

        } catch (SQLException err) {
            System.err.println("Error al buscar el proveedor " + err.getMessage());
        }

        return proveedoresBD;
    }

    // Modificar proveedor por medio de su id
    public boolean modificarProveedor(Proveedor proveedor) {
        boolean actualizado = false;
        String sql = "UPDATE proveedor SET Nombre = ?, Direccion = ?, Especialidad = ?, Tel1 = ?, Tel2 = ?, Tipo_material = ?, Id_almacen4 = ? WHERE Id_proveedor = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setString(1, proveedor.getNombre());
            stm.setString(2, proveedor.getDireccion());
            stm.setString(3, proveedor.getEspecialidad());
            stm.setString(4, proveedor.getTel1());
            stm.setString(5, proveedor.getTel2());
            stm.setString(6, proveedor.getTipo_Material());
            stm.setInt(7, proveedor.getId_Almacen4());
            stm.setInt(8, proveedor.getId_Proveedor());

            int registrosAfectados = stm.executeUpdate();
            if (registrosAfectados > 0) {
                System.out.println("Proveedor actualizado correctamente");
                actualizado = true;
            } else {
                System.out.println("No se encontró el ID del proveedor");
            }
        } catch (SQLException err) {
            System.out.println("Error al actualizar el proveedor " + err.getMessage());
        }

        return actualizado;
    }

    // Insertar proveedor
    public boolean insertarProveedor(Proveedor proveedor) {
        boolean insertado = false;
        String sql = "INSERT INTO proveedor (Id_proveedor, Nombre, Direccion, Especialidad, Tel1, Tel2, Tipo_material, Id_almacen4) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, proveedor.getId_Proveedor());
            stm.setString(2, proveedor.getNombre());
            stm.setString(3, proveedor.getDireccion());
            stm.setString(4, proveedor.getEspecialidad());
            stm.setString(5, proveedor.getTel1());
            stm.setString(6, proveedor.getTel2()); // Permite nulo
            stm.setString(7, proveedor.getTipo_Material());
            stm.setInt(8, proveedor.getId_Almacen4());

            int registrosAfectados = stm.executeUpdate();
            if (registrosAfectados > 0) {
                System.out.println("Proveedor registrado correctamente");
                insertado = true;
            } else {
                System.out.println("No se pudo registrar el proveedor");
            }
        } catch (SQLException err) {
            System.err.println("Error al insertar el proveedor: " + err.getMessage());
        }

        return insertado;
    }
}