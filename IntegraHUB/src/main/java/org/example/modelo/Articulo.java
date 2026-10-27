package org.example.modelo;

public class Articulo {

    // asignacion de atributos (Todos se ponen nulos para evitar problemas)
    private int id_Producto = 0;
    private String tipoProducto = "";
    private String nombre = "";
    private float cantidad = 0;
    private String modelo = "";
    private String color = "";
    private String producto_De = "";
    private int id_Proveedor1 = 0;
    private int id_Almacen5 = 0;

    // constructores (vacio / con atributos )
    public Articulo() {
    }

    public Articulo(int id_Producto, String tipoProducto, String nombre, float cantidad, String modelo, String color, String producto_De, int id_Proveedor1, int id_Almacen5) {
        setId_Producto(id_Producto);
        setTipoProducto(tipoProducto);
        setNombre(nombre);
        setCantidad(cantidad);
        setModelo(modelo);
        setColor(color);
        setProducto_De(producto_De);
        setId_Proveedor1(id_Proveedor1);
        setId_Almacen5(id_Almacen5);
    }

    // geters (formatos) y setters (condiciones para guardar)

    public int getId_Producto() {
        return id_Producto;
    }

    public void setId_Producto(int id_Producto) {
        if (id_Producto > 0) {
            this.id_Producto = id_Producto;
        } else {
            System.out.println("El id no debe ser menor a 0");
        }
    }

    public String getTipoProducto() {
        String tipoProductoFormato = "";
        if (this.tipoProducto != null) {
            tipoProductoFormato = this.tipoProducto.toLowerCase();
        }
        return tipoProductoFormato;
    }

    public void setTipoProducto(String tipoProducto) {
        if (tipoProducto == null || tipoProducto.isBlank()) {
            System.out.println("El tipo de producto es un requisito");
        } else {
            this.tipoProducto = tipoProducto;
        }
    }

    public String getNombre() {
        String nombreFormato = "";
        if (this.nombre != null) {
            nombreFormato = this.nombre.toLowerCase();
        }
        return nombreFormato;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            System.out.println("El nombre es un requisito");
        } else {
            this.nombre = nombre;
        }
    }

    public float getCantidad() {
        String precioFormato = String.format("%.2f" ,this.cantidad);
        return (float) Double.parseDouble(precioFormato);
    }

    public void setCantidad(float cantidad) {
        if (cantidad >= 0) {
            this.cantidad = cantidad;
        } else {
            System.out.println("La cantidad no puede ser menor a 0");
        }
    }

    public String getModelo() {
        String modeloFormato = "";
        if (this.modelo != null) {
            modeloFormato = this.modelo.toLowerCase();
        }
        return modeloFormato;
    }

    public void setModelo(String modelo) {
        if (modelo == null || modelo.isBlank()) {
            System.out.println("El modelo es un requisito");
        } else {
            this.modelo = modelo;
        }
    }

    public String getColor() {
        String colorFormato = "";
        if (this.color != null) {
            colorFormato = this.color.toLowerCase();
        }
        return colorFormato;
    }

    public void setColor(String color) {
        // Permite valores nulos o vacios
        this.color = color;
    }

    public String getProducto_De() {
        String producto_DeFormato = "";
        if (this.producto_De != null) {
            producto_DeFormato = this.producto_De.toLowerCase();
        }
        return producto_DeFormato;
    }

    public void setProducto_De(String producto_De) {
        if (producto_De == null || producto_De.isBlank()) {
            System.out.println("El origen del producto es un requisito");
        } else {
            this.producto_De = producto_De;
        }
    }

    public int getId_Proveedor1() {
        return id_Proveedor1;
    }

    public void setId_Proveedor1(int id_Proveedor1) {
        this.id_Proveedor1 = id_Proveedor1;
    }

    public int getId_Almacen5() {
        return id_Almacen5;
    }

    public void setId_Almacen5(int id_Almacen5) {
        this.id_Almacen5 = id_Almacen5;
    }

    // Agregamos toString para mostrar datos
        @Override
        public String toString() {
            return  "id del Producto:     " + getId_Producto() + "\n" +
                    "tipo de Producto:    " + getTipoProducto() + '\n' +
                    "nombre:              " + getNombre() + '\n' +
                    "cantidad:            " + getCantidad() + '\n' +
                    "modelo:              " + getModelo() + '\n' +
                    "color:               " + getColor() + '\n' +
                    "producto de:         " + getProducto_De() + '\n' +
                    "id del Proveedor:    " + getId_Proveedor1() + "\n" +
                    "id del Almacen:      " + getId_Almacen5();
        }
}
