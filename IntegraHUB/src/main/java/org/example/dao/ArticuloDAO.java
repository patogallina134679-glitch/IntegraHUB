package org.example.dao;


import org.example.Config.Conexion;
import org.example.modelo.Articulo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArticuloDAO {

      // Extrae todos los datos de la tabla Articulos
        public ArrayList<Articulo> extraerArticulo() {
            ArrayList<Articulo> articulosBD = new ArrayList<Articulo>();
            String sql = "SELECT * FROM articulo";
            try (Connection conexion = Conexion.conectar();
                 PreparedStatement stm = conexion.prepareStatement(sql);
                 ResultSet rs = stm.executeQuery()) {

                while (rs.next()) {
                    Articulo articulo = new Articulo();

                    articulo.setId_Producto(rs.getInt("Id_producto"));
                    articulo.setTipoProducto(rs.getString("Tipo_producto"));
                    articulo.setNombre(rs.getString("Nombre"));
                    articulo.setCantidad(rs.getFloat("Cantidad"));
             articulo.setModelo(rs.getString("Modelo"));


    articulo.setModelo(rs.getString("Modelo"));                    articulo.setColor(rs.getString("Color"));
                    articulo.setProducto_De(rs.getString("Producto_de_"));
                   articulo.setId_Proveedor1(rs.getInt("Id_proveedor1"));
                    articulo.setId_Almacen5(rs.getInt("Id_almacen5"));

                    articulosBD.add(articulo);
                }
            } catch (SQLException err) {
                System.err.println("Error al extraer los datos " + err.getMessage());
            }
            return articulosBD;
        }

       // Borrar
              public boolean borrarArticulo (Articulo articulo){
                  boolean eliminado = false;
                  String sql = "DELETE FROM `integraHUB`.`articulo` WHERE Id_producto = ?";
                  try (Connection conexion = Conexion.conectar();
                       PreparedStatement stm = conexion.prepareStatement(sql)) {

                      stm.setInt(1, articulo.getId_Producto());
                      int filasAfectadas = stm.executeUpdate();

                      if (filasAfectadas > 0) {
                          eliminado = true;
                      }

                  } catch (SQLException err) {
                      System.out.println("Error al borrar el articulo " + err.getMessage());
                  }

                  return eliminado;
              }
    // Buscar articulo por ID o por Nombre
    public ArrayList<Articulo> buscarArticulo(Articulo articuloex) {
        ArrayList<Articulo> articuloBD = new ArrayList<Articulo>();
        // Permite buscar si coincide el ID exacto o si el nombre contiene el texto buscado
        String sql = "SELECT * FROM articulo WHERE Id_producto = ? OR Nombre LIKE ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, articuloex.getId_Producto());
            stm.setString(2, "%" + articuloex.getNombre() + "%");

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Articulo articulo = new Articulo();

                    articulo.setId_Producto(rs.getInt("Id_producto"));
                    articulo.setTipoProducto(rs.getString("Tipo_producto"));
                    articulo.setNombre(rs.getString("Nombre"));
                    articulo.setCantidad(rs.getFloat("Cantidad"));
                    articulo.setModelo(rs.getString("Modelo"));
                    articulo.setColor(rs.getString("Color"));
                    articulo.setProducto_De(rs.getString("Producto_de_"));
                    articulo.setId_Proveedor1(rs.getInt("Id_proveedor1"));
                    articulo.setId_Almacen5(rs.getInt("Id_almacen5"));

                    articuloBD.add(articulo);
                }
            }

        } catch (SQLException err) {
            System.err.println("Error al buscar el articulo: " + err.getMessage());
        }

        return articuloBD;
    }

    // Modificar articulo por medio de su id
    public boolean modificarArticulo(Articulo articulo) {
        boolean actualizado = false;
        String sql = "UPDATE articulo SET Tipo_producto = ?, Nombre = ?, Cantidad = ?, Modelo = ?, Color = ?, Producto_de_ = ?, Id_proveedor1 = ?, Id_almacen5 = ? WHERE Id_producto = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setString(1, articulo.getTipoProducto());
            stm.setString(2, articulo.getNombre());
            stm.setFloat(3, articulo.getCantidad());
            stm.setString(4, articulo.getModelo());
            stm.setString(5, articulo.getColor());
            stm.setString(6, articulo.getProducto_De());
            stm.setInt(7, articulo.getId_Proveedor1());
            stm.setInt(8, articulo.getId_Almacen5());
            stm.setInt(9, articulo.getId_Producto());

            int registrosAfectados = stm.executeUpdate();
            if (registrosAfectados > 0) {
                System.out.println("Artículo actualizado correctamente");
                actualizado = true;
            } else {
                System.out.println("No se encontró el ID del artículo");
            }
        } catch (SQLException err) {
            System.err.println("Error al actualizar el artículo: " + err.getMessage());
        }

        return actualizado;
    }

    // Insertar artículo
    public boolean insertarArticulo(Articulo articulo) {
        boolean insertado = false;
        String sql = "INSERT INTO articulo (Id_producto, Tipo_producto, Nombre, Cantidad, Modelo, Color, Producto_de_, Id_proveedor1, Id_almacen5) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, articulo.getId_Producto());
            stm.setString(2, articulo.getTipoProducto());
            stm.setString(3, articulo.getNombre());
            stm.setFloat(4, articulo.getCantidad());
            stm.setString(5, articulo.getModelo());
            stm.setString(6, articulo.getColor()); // Acepta nulo si la variable es null
            stm.setString(7, articulo.getProducto_De());
            stm.setInt(8, articulo.getId_Proveedor1());
            stm.setInt(9, articulo.getId_Almacen5());

            int registrosAfectados = stm.executeUpdate();
            if (registrosAfectados > 0) {
                System.out.println("Artículo registrado correctamente");
                insertado = true;
            } else {
                System.out.println("No se pudo registrar el artículo");
            }
        } catch (SQLException err) {
            System.err.println("Error al insertar el artículo: " + err.getMessage());
        }

        return insertado;
    }
}