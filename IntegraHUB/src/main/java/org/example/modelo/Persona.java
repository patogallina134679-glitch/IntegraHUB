package org.example.modelo;

public abstract class Persona {


    // asignacion de atributos (Todos se ponen nulos para evitar problemas)
    private String nombre = "";
    private String direccion = "";
    private String tel1 = "";
    private String tel2 = "";

    public Persona() {
    }

    public Persona(String nombre, String direccion, String tel1, String tel2) {
        setDireccion(direccion);
        setTel1(tel1);
        setTel2(tel2);
        setNombre(nombre);
    }

    // geters (formatos) y setters (condiciones para guardar)

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

    public String getTel1() {
        String tel1Formato = "";
        if (this.tel1 != null) {
            tel1Formato = this.tel1.toLowerCase();
        }
        return tel1Formato;
    }

    public void setTel1(String tel1) {
        if (tel1 == null || tel1.isBlank()) {
            System.out.println("El teléfono 1 es un requisito");
        } else {
            this.tel1 = tel1;
        }
    }

    public String getTel2() {
        String tel2Formato = "";
        if (this.tel2 != null) {
            tel2Formato = this.tel2.toLowerCase();
        }
        return tel2Formato;
    }

    public void setTel2(String tel2) {
        // Permite nulos o cadenas vacías sin lanzar mensaje de error
        this.tel2 = tel2;
    }

    // Metodo abstracto
    public abstract String obtenerRol();

    // toString que muestra los datos personales de cada persona
    @Override
    public String toString() {
        return "Nombre: " + getNombre() + '\n' +
                "Dirección: " + getDireccion() + '\n' +
                "Teléfono 1: " + getTel1() + '\n' +
                "Teléfono 2: " + getTel2() +  '\n';
    }
}
