package org.example.modelo;

import java.time.LocalDate;

public class Movimiento {

    // Asignación de atributos (Todos se inicializan para evitar problemas)
    private int cod_Movimiento = 0;
    private int id_Almacen6 = 0;
    private String movimiento_de_ = "";
    private Integer id_Cliente1 = null;
    private Integer id_Proveedor2 = null;
    private int id_Empleado3 = 0;
    private String descripcion = "";
    private LocalDate fech_Registro;
    private LocalDate fech_Orden;
    private LocalDate fech_Concluido;
    private int calificacion = 0;
    private String desc_Calificacion = "";

    // Creación de constructores
    public Movimiento() {
    }

    public Movimiento(int cod_Movimiento, int id_Almacen6, String movimiento_de_, Integer id_Cliente1, Integer id_Proveedor2, int id_Empleado3, String descripcion, LocalDate fech_Registro, LocalDate fech_Orden, LocalDate fech_Concluido, int calificacion, String desc_Calificacion) {
        setCod_Movimiento(cod_Movimiento);
        setId_Almacen6(id_Almacen6);
        setMovimiento_de_(movimiento_de_);
        setId_Cliente1(id_Cliente1);
        setId_Proveedor2(id_Proveedor2);
        setId_Empleado3(id_Empleado3);
        setDescripcion(descripcion);
        setFech_Registro(fech_Registro);
        setFech_Orden(fech_Orden);
        setFech_Concluido(fech_Concluido);
        setCalificacion(calificacion);
        setDesc_Calificacion(desc_Calificacion);
    }

    // Getters (con formato en minúsculas) y Setters (con condiciones para guardar)
    public int getCod_Movimiento() {
        return cod_Movimiento;
    }

    public void setCod_Movimiento(int cod_Movimiento) {
        if (cod_Movimiento >= 0) {
            this.cod_Movimiento = cod_Movimiento;
        } else {
            System.out.println("El código de movimiento tiene que ser mayor o igual a 0");
        }
    }

    public int getId_Almacen6() {
        return id_Almacen6;
    }

    public void setId_Almacen6(int id_Almacen6) {
        if (id_Almacen6 >= 0) {
            this.id_Almacen6 = id_Almacen6;
        } else {
            System.out.println("El id del almacén tiene que ser mayor o igual a 0");
        }
    }

    public String getMovimiento_de_() {
        String movimiento_de_Formato = "";
        if (this.movimiento_de_ != null) {
            movimiento_de_Formato = this.movimiento_de_.toLowerCase();
        }
        return movimiento_de_Formato;
    }

    public void setMovimiento_de_(String movimiento_de_) {
        if (movimiento_de_ == null || movimiento_de_.isBlank()) {
            System.out.println("El tipo de movimiento es un requisito");
        } else {
            this.movimiento_de_ = movimiento_de_;
        }
    }

    public Integer getId_Cliente1() {
        return id_Cliente1;
    }

    public void setId_Cliente1(Integer id_Cliente1) {
        this.id_Cliente1 = id_Cliente1;
    }

    public Integer getId_Proveedor2() {
        return id_Proveedor2;
    }

    public void setId_Proveedor2(Integer id_Proveedor2) {
        this.id_Proveedor2 = id_Proveedor2;
    }

    public int getId_Empleado3() {
        return id_Empleado3;
    }

    public void setId_Empleado3(int id_Empleado3) {
        if (id_Empleado3 >= 0) {
            this.id_Empleado3 = id_Empleado3;
        } else {
            System.out.println("El id del empleado tiene que ser mayor o igual a 0");
        }
    }

    public String getDescripcion() {
        String descripcionFormato = "";
        if (this.descripcion != null) {
            descripcionFormato = this.descripcion.toLowerCase();
        }
        return descripcionFormato;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            System.out.println("La descripción es un requisito");
        } else {
            this.descripcion = descripcion;
        }
    }

    public LocalDate getFech_Registro() {
        return fech_Registro;
    }

    public void setFech_Registro(LocalDate fech_Registro) {
        if (fech_Registro == null) {
            System.out.println("La fecha de registro es un requisito");
        } else {
            this.fech_Registro = fech_Registro;
        }
    }

    public LocalDate getFech_Orden() {
        return fech_Orden;
    }

    public void setFech_Orden(LocalDate fech_Orden) {
        if (fech_Orden == null) {
            System.out.println("La fecha de orden es un requisito");
        } else {
            this.fech_Orden = fech_Orden;
        }
    }

    public LocalDate getFech_Concluido() {
        return fech_Concluido;
    }

    public void setFech_Concluido(LocalDate fech_Concluido) {
        this.fech_Concluido = fech_Concluido;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getDesc_Calificacion() {
        String desc_CalificacionFormato = "";
        if (this.desc_Calificacion != null) {
            desc_CalificacionFormato = this.desc_Calificacion.toLowerCase();
        }
        return desc_CalificacionFormato;
    }

    public void setDesc_Calificacion(String desc_Calificacion) {
        this.desc_Calificacion = desc_Calificacion;
    }

    // toString que muestra los datos de cada Movimiento
    @Override
    public String toString() {
        return "Código Movimiento: " + getCod_Movimiento() + '\n' +
                "Id Almacén: " + getId_Almacen6() + '\n' +
                "Movimiento De: " + getMovimiento_de_() + '\n' +
                "Id Cliente: " + getId_Cliente1() + '\n' +
                "Id Proveedor: " + getId_Proveedor2() + '\n' +
                "Id Empleado: " + getId_Empleado3() + '\n' +
                "Descripción: " + getDescripcion() + '\n' +
                "Fecha Registro: " + getFech_Registro() + '\n' +
                "Fecha Orden: " + getFech_Orden() + '\n' +
                "Fecha Concluido: " + getFech_Concluido() + '\n' +
                "Calificación: " + getCalificacion() + '\n' +
                "Descripción Calificación: " + getDesc_Calificacion();
    }
}