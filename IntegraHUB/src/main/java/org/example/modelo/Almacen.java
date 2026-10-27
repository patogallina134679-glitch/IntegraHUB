package org.example.modelo;

public class Almacen {
    // asignacion de atributos (Todos se ponen nulos para evitar problemas)
    private int id_Almacen = 0;
    private String zona = "";
    private int piso = 0;
    private String direccion = "";

    // Creacion de constructores
    public Almacen() {
    }

    public Almacen(int id_Almacen, String zona, int piso, String direccion) {
        setId_Almacen(id_Almacen);
        setZona(zona);
        setPiso(piso);
        setDireccion(direccion);
    }

    // geters (formatos) y setters (condiciones para guardar)
    public int getId_Almacen() {
        return id_Almacen;
    }

    public void setId_Almacen(int id_Almacen) {
        if (id_Almacen >= 0) {
            this.id_Almacen = id_Almacen;
        } else {
            System.out.println("El id tiene que ser mayor a 0");
        }
    }

    public String getZona() {
        String zonaFormato = "";
        if (this.zona != null) {
            zonaFormato = this.zona.toLowerCase();
        }
        return zonaFormato;
    }

    public void setZona(String zona) {
        if (zona == null || zona.isBlank()) {
            System.out.println("El nombre de la zona es un requisito");
        } else {
            this.zona = zona;
        }
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        if (piso >= -1) {
            this.piso = piso;
        } else {
            System.out.println("El numero de piso en invalido");
        }
    }

    public String getDireccion() {
        String direccionFormato = "";
        if (this.direccion != null) {
            direccionFormato = this.direccion.toLowerCase();
        }
        return direccionFormato;
    }

    public void setDireccion(String direccion) {
        if (direccion == null || direccion.isBlank()) {
            System.out.println("La direccion es un requisito");
        } else {
            this.direccion = direccion;
        }
    }

    //Generamos el toString para mostrar los datos

    @Override
    public String toString() {
        return "id_Almacen: " + getId_Almacen() + "\n" +
                "zona: " + getZona() + "\n" +
                "piso: " + getPiso() + "\n" +
                "direccion: " + getDireccion();
    }

}