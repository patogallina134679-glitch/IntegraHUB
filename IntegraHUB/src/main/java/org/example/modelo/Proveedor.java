package org.example.modelo;

public class Proveedor extends Persona {

    // asignacion de atributos
    private int id_Proveedor = 0;
    private String especialidad = "";
    private String tipo_Material = "";
    private int id_Almacen4 = 0;

    // constructores (vacio / con atributos )
    public Proveedor() {
    }

    public Proveedor(String nombre, String direccion, String tel1, String tel2, int id_Proveedor, String especialidad, String tipo_Material, int id_Almacen4) {
        super(nombre, direccion, tel1, tel2);
        setId_Proveedor(id_Proveedor);
        setEspecialidad(especialidad);
        setTipo_Material(tipo_Material);
        setId_Almacen4(id_Almacen4);
    }

    // geters (formatos) y setters (condiciones para guardar)

    public int getId_Proveedor() {
        return id_Proveedor;
    }

    public void setId_Proveedor(int id_Proveedor) {
        if (id_Proveedor > 0) {
            this.id_Proveedor = id_Proveedor;
        } else {
            System.out.println("El id no debe ser menor a 0");
        }
    }

    public String getEspecialidad() {
        String especialidadFormato = "";
        if (this.especialidad != null) {
            especialidadFormato = this.especialidad.toLowerCase();
        }
        return especialidadFormato;
    }

    public void setEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.isBlank()) {
            System.out.println("La especialidad del proveedor es un requisito");
        } else {
            this.especialidad = especialidad;
        }
    }



    public String getTipo_Material() {
        String tipo_MaterialFormato = "";
        if (this.tipo_Material != null) {
            tipo_MaterialFormato = this.tipo_Material.toLowerCase();
        }
        return tipo_MaterialFormato;
    }

    public void setTipo_Material(String tipo_Material) {
        if (tipo_Material == null || tipo_Material.isBlank()) {
            System.out.println("El tipo de material es un requisito");
        } else {
            this.tipo_Material = tipo_Material;
        }
    }

    public int getId_Almacen4() {
        return id_Almacen4;
    }

    public void setId_Almacen4(int id_Almacen4) {
        if (id_Almacen4 > 0) {
            this.id_Almacen4 = id_Almacen4;
        } else {
            System.out.println("El id no debe ser menor a 0");
        }
    }

    // llamando al metodo abstracto
    @Override
    public String obtenerRol() {
        return "Proveedor especialista en " + getEspecialidad();
    }

    // toString que muestra los datos de cada Proveedor
    @Override
    public String toString() {
        return "Id Proveedor: " + getId_Proveedor() + '\n' +
                super.toString() +
                "Especialidad: " + getEspecialidad() + '\n' +
                "Tipo de Material: " + getTipo_Material() + '\n' +
                "Id Almacen: " + getId_Almacen4();
    }
}
