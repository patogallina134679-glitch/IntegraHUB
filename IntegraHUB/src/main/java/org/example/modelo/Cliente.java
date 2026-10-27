package org.example.modelo;

public class Cliente extends Persona implements Calificador {
    
    // asignacion de atributos (Todos se ponen nulos para evitar problemas)
    private int id_Cliente = 0;
    private int id_Almacen3 = 0;

    // constructores (vacio / con atributos )
    public Cliente() {
    }

    public Cliente(String nombre, String direccion, String tel1, String tel2, int id_Cliente, int id_Almacen3) {
        super(nombre, direccion, tel1, tel2);
        setId_Cliente(id_Cliente);
        setId_Almacen3(id_Almacen3);
    }

    // geters (formatos) y setters (condiciones para guardar)

    public int getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(int id_Cliente) {
        if (id_Cliente > 0) {
            this.id_Cliente = id_Cliente;
        } else {
            System.out.println("El id del cliente es un requisito y debe ser mayor a 0");
        }
    }

    public int getId_Almacen3() {
        return id_Almacen3;
    }

    public void setId_Almacen3(int id_Almacen3) {
        this.id_Almacen3 = id_Almacen3;
    }

    // Utilizo las interfaces (Calificador) ("evaluar Movimiento")
    @Override
    public void evaluarMovimiento(int estrellas, String comentario) {
        System.out.println("El cliente " + getNombre() + " calificó la compra recibida con "
                + estrellas + " estrellas. Nota: " + comentario);
    }

    // Llamar el metodo abstracto
    @Override
    public String obtenerRol() {
        return "Cliente registrado en el sistema";
    }

    //Agregamos toString para mostrar los datos
        @Override
        public String toString() {
            return  "id del Cliente: " + getId_Cliente() + "\n" +
                    super.toString() +
                    "id del Almacen: " + getId_Almacen3();
        }
}