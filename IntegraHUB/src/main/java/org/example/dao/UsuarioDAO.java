package org.example.dao;

import org.example.Config.Conexion;
import org.example.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {

    // Extrae todos los datos de la tabla Usuarios
    public ArrayList<Usuario> extraerUsuarios() {
        ArrayList<Usuario> usuariosBD = new ArrayList<Usuario>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();

                usuario.setId_User(rs.getInt("Id_User"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setNivel_Pri(rs.getString("nivel_pri"));
                usuario.setId_Empleado1(rs.getInt("Id_empleado1"));
                usuariosBD.add(usuario);
            }
        } catch (SQLException err) {
            System.err.println("Error al extraer los datos " + err.getMessage());
        }
        return usuariosBD;
    }

    // Borrar al usuario pidiendo el id
    public boolean borrarUsuario(Usuario usuario) {
        boolean eliminado = false;
        String sql = "DELETE FROM `integraHUB`.`usuarios` WHERE Id_User = ?";
        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, usuario.getId_User());
            int filasAfectadas = stm.executeUpdate();

            if (filasAfectadas > 0) {
                eliminado = true;
            }

        } catch (SQLException err) {
            System.out.println("Error al borrar al usuario " + err.getMessage());
        }

        return eliminado;
    }

    // Buscar usuario por nombre o id
    public ArrayList<Usuario> buscarUsuario(Usuario usuario) {
        ArrayList<Usuario> usuariosBD = new ArrayList<Usuario>();
        String sql = "SELECT * FROM usuarios WHERE Id_User = ? OR usuario LIKE ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, usuario.getId_User());
            stm.setString(2, "%" + usuario.getUsuario() + "%");

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Usuario usu = new Usuario();

                    usu.setId_User(rs.getInt("Id_User"));
                    usu.setUsuario(rs.getString("usuario"));
                    usu.setContrasena(rs.getString("contrasena"));
                    usu.setNivel_Pri(rs.getString("nivel_pri"));
                    usu.setId_Empleado1(rs.getInt("Id_empleado1"));

                    usuariosBD.add(usu);
                }
            }

        } catch (SQLException err) {
            System.err.println("Error al buscar el usuario " + err.getMessage());
        }

        return usuariosBD;
    }

    // Validar Usuario (Inicio de Sesión)
    public Usuario validarUsuario(Usuario usuario) {
        Usuario usuarioValido = null;
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setString(1, usuario.getUsuario());
            stm.setString(2, usuario.getContrasena());

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    usuarioValido = new Usuario();
                    usuarioValido.setId_User(rs.getInt("Id_User"));
                    usuarioValido.setUsuario(rs.getString("usuario"));
                    usuarioValido.setContrasena(rs.getString("contrasena"));
                    usuarioValido.setNivel_Pri(rs.getString("nivel_pri"));
                    usuarioValido.setId_Empleado1(rs.getInt("Id_empleado1"));
                } else {
                    System.out.println("El ResultSet no devolvió ningún registro.");
                }
            }

        } catch (SQLException err) {
            System.err.println("Error al validar el usuario: " + err.getMessage());
        }

        return usuarioValido;
    }

    // modificar Usuarios por medio de su id
    public boolean modificarUsuario(Usuario usuario) {
        boolean actualizado = false;
        String sql = "UPDATE usuarios SET usuario = ?, contrasena = ?, nivel_pri = ?, Id_empleado1 = ? WHERE Id_User = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setString(1, usuario.getUsuario());
            stm.setString(2, usuario.getContrasena());
            stm.setString(3, usuario.getNivel_Pri());
            stm.setInt(4, usuario.getId_Empleado1()); // Corregido el índice de parámetros
            stm.setInt(5, usuario.getId_User());     // Corregido el índice de parámetros

            int registrosAfectados = stm.executeUpdate();
            if (registrosAfectados > 0) {
                System.out.println("Usuario actualizado correctamente");
                actualizado = true;
            } else {
                System.out.println("No se encontró el ID de usuario");
            }
        } catch (SQLException err) {
            System.out.println("Error al actualizar el usuario " + err.getMessage());
        }

        return actualizado;
    }

    // Insertar usuario
    public boolean insertarUsuario(Usuario usuario) {
        boolean insertado = false;
        String sql = "INSERT INTO usuarios (usuario, contrasena, nivel_pri, Id_empleado1) VALUES (?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setString(1, usuario.getUsuario());
            stm.setString(2, usuario.getContrasena());
            stm.setString(3, usuario.getNivel_Pri());
            stm.setInt(4, usuario.getId_Empleado1());

            int registrosAfectados = stm.executeUpdate();
            if (registrosAfectados > 0) {
                System.out.println("Usuario registrado correctamente en la base de datos");
                insertado = true;
            } else {
                System.out.println("No se pudo registrar el usuario");
            }
        } catch (SQLException err) {
            System.err.println("Error al insertar el usuario: " + err.getMessage());
        }

        return insertado;
    }
}