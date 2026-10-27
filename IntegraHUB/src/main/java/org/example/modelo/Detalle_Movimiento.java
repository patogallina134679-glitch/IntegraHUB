package org.example.modelo;

public class Detalle_Movimiento {

    // asignacion de atributos (Todos se ponen nulos para evitar problemas)
    private int id_Detalle = 0;
    private int cod_Movimientos1 = 0;
    private int id_Producto1 = 0;
    private float cantidad = 0;
    
    // constructores (vacio / con atributos )
    public Detalle_Movimiento() {
    }
 
        public Detalle_Movimiento(int id_Detalle, int cod_Movimientos1, int id_Producto1, float cantidad) {
        setId_Detalle(id_Detalle);
        setCod_Movimientos1(cod_Movimientos1);
        setId_Producto1(id_Producto1);
        setCantidad(cantidad);
    }

    // geters (formatos) y setters (condiciones para guardar)

    public int getId_Detalle() {
        return id_Detalle;
    }

    public void setId_Detalle(int id_Detalle) {
        if (id_Detalle > 0) {
            this.id_Detalle = id_Detalle;
        } else {
            System.out.println("El id no debe ser menor a 0");
        }
    }

    public int getCod_Movimientos1() {
        return cod_Movimientos1;
    }

    public void setCod_Movimientos1(int cod_Movimientos1) {
        this.cod_Movimientos1 = cod_Movimientos1;
    }

    public int getId_Producto1() {
        return id_Producto1;
    }

    public void setId_Producto1(int id_Producto1) {
        this.id_Producto1 = id_Producto1;
    }

    public float getCantidad() {
        String precioFormato = String.format("%.2f", this.cantidad);
        return (float) Double.parseDouble(precioFormato);
    }

    public void setCantidad(float cantidad) {
        if (cantidad >= 0) {
            this.cantidad = cantidad;
        } else {
            System.out.println("La cantidad no puede ser menor a 0");
        }
    }
    
    // usamos el toString para mostrar los datos del detalle movimiento
    @Override
        public String toString() {
            return  "id de detalle:            " + getId_Detalle() + "\n" +
                    "codigo del Movimientos:   " + getCod_Movimientos1() + "\n" +
                    "id del Producto:          " + getId_Producto1() + "\n" +
                    "cantidad:                 " + getCantidad();
        }
}