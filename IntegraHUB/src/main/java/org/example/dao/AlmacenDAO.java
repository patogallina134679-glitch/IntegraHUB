package org.example.dao;
import org.example.Config.Conexion;
import org.example.modelo.Almacen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlmacenDAO {
    // Extrae todos los datos de la tabla Almacen (Mostrar)
    public ArrayList<Almacen> extraerAlmacen() {
        ArrayList<Almacen> almacenesBD = new ArrayList<Almacen>();
        String sql = "SELECT * FROM almacen"; //()
        
        //Se pone try para que de igual forma lo pruebe 
        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Almacen almacen = new Almacen();

                almacen.setId_Almacen(rs.getInt("id_almacen"));
                almacen.setZona(rs.getString("Zona"));
                almacen.setPiso(rs.getInt("Piso"));
                almacen.setDireccion(rs.getString("Direccion"));

                almacenesBD.add(almacen);
            }
        } catch (SQLException err) {
            System.err.println("Error al extraer los datos " + err.getMessage());
        }
        return almacenesBD;
    }

     // Borrar
            public boolean borrarAlmacen (Almacen almacen){
                boolean eliminado = false;
                String sql = "DELETE FROM `integraHUB`.`almacen` WHERE Id_almacen = ?";
                try (Connection conexion = Conexion.conectar();
                     PreparedStatement stm = conexion.prepareStatement(sql)) {

                    stm.setInt(1, almacen.getId_Almacen());
                    int filasAfectadas = stm.executeUpdate();

                    if (filasAfectadas > 0) {
                        eliminado = true;
                    }

                } catch (SQLException err) {
                    System.out.println("Error al borrar el almacen " + err.getMessage());
                }

                return eliminado;
            }

     // Buscar almacen
         public ArrayList<Almacen> buscarAlmacen(Almacen almacenex) {
             ArrayList<Almacen> almacenesBD = new ArrayList<Almacen>();
             String sql = "SELECT * FROM almacen WHERE Id_almacen = ?";

             try (Connection conexion = Conexion.conectar();
                  PreparedStatement stm = conexion.prepareStatement(sql)) {

                 stm.setInt(1, almacenex.getId_Almacen());

                 try (ResultSet rs = stm.executeQuery()) {
                     while (rs.next()) {
                         Almacen almacen = new Almacen();

                         almacen.setId_Almacen(rs.getInt("Id_almacen"));
                         almacen.setZona(rs.getString("Zona"));
                         almacen.setPiso(rs.getInt("Piso"));
                         almacen.setDireccion(rs.getString("Direccion"));

                         almacenesBD.add(almacen);
                     }
                 }

             } catch (SQLException err) {
                 System.err.println("Error al buscar el almacen: " + err.getMessage());
             }

             return almacenesBD;
         }

    // Modificar almacén por medio de su id
    public boolean modificarAlmacen(Almacen almacen) {
        boolean actualizado = false;
        String sql = "UPDATE almacen SET Zona = ?, Piso = ?, Direccion = ? WHERE Id_almacen = ?";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setString(1, almacen.getZona());
            stm.setInt(2, almacen.getPiso());
            stm.setString(3, almacen.getDireccion());
            stm.setInt(4, almacen.getId_Almacen());

            int registrosAfectados = stm.executeUpdate();
            if (registrosAfectados > 0) {
                System.out.println("Almacún actualizado correctamente");
                actualizado = true;
            } else {
                System.out.println("No se encontró el ID del almacén");
            }
        } catch (SQLException err) {
            System.err.println("Error al actualizar el almacén: " + err.getMessage());
        }

        return actualizado;
    }

    // Insertar almacén
    public boolean insertarAlmacen(Almacen almacen) {
        boolean insertado = false;
        String sql = "INSERT INTO almacen (Id_almacen, Zona, Piso, Direccion) VALUES (?, ?, ?, ?)";

        try (Connection conexion = Conexion.conectar();
             PreparedStatement stm = conexion.prepareStatement(sql)) {

            stm.setInt(1, almacen.getId_Almacen());
            stm.setString(2, almacen.getZona());
            stm.setInt(3, almacen.getPiso());
            stm.setString(4, almacen.getDireccion());

            int registrosAfectados = stm.executeUpdate();
            if (registrosAfectados > 0) {
                System.out.println("Almacén registrado correctamente");
                insertado = true;
            } else {
                System.out.println("No se pudo registrar el almacén");
            }
        } catch (SQLException err) {
            System.err.println("Error al insertar el almacén: " + err.getMessage());
        }

        return insertado;
    }
}
