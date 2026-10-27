package org.example.modelo;

public class Asistencia {
    // asignacion de atributos (Todos se ponen nulos para evitar problemas)
    private int id_Asistencia = 0;
    private int id_Empleado = 0;
    private String fecha = "";
    private String horaEntrada = "";
    private String horaSalida = "";
    private String totalHr = "";

    // Creacion de constructores
    public Asistencia() {
    }

    public Asistencia(int id_Asistencia, int id_Empleado, String fecha, String horaEntrada, String horaSalida, String totalHr) {
        setId_Asistencia(id_Asistencia);
        setId_Empleado(id_Empleado);
        setFecha(fecha);
        setHoraEntrada(horaEntrada);
        setHoraSalida(horaSalida);
        setTotalHr(totalHr);
    }

    // geters (formatos) y setters (condiciones para guardar)
    public int getId_Asistencia() {
        return id_Asistencia;
    }

    public void setId_Asistencia(int id_Asistencia) {
        if (id_Asistencia >= 0) {
            this.id_Asistencia = id_Asistencia;
        } else {
            System.out.println("El id de asistencia tiene que ser mayor a 0");
        }
    }

    public int getId_Empleado() {
        return id_Empleado;
    }

    public void setId_Empleado(int id_Empleado) {
        if (id_Empleado >= 0) {
            this.id_Empleado = id_Empleado;
        } else {
            System.out.println("El id del empleado tiene que ser mayor a 0");
        }
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        if (fecha == null || fecha.isBlank()) {
            System.out.println("La fecha es un requisito");
        } else {
            this.fecha = fecha;
        }
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        if (horaEntrada == null || horaEntrada.isBlank()) {
            System.out.println("La hora de entrada es un requisito");
        } else {
            this.horaEntrada = horaEntrada;
        }
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getTotalHr() {
        return totalHr;
    }

    public void setTotalHr(String totalHr) {
        this.totalHr = totalHr;
    }

    //Generamos el toString para mostrar los datos
    @Override
    public String toString() {
        return "id_Asistencia:   " + getId_Asistencia() + "\n" +
                "id_Empleado:    " + getId_Empleado() + "\n" +
                "fecha:          " + getFecha() + "\n" +
                "horaEntrada:    " + getHoraEntrada() + "\n" +
                "horaSalida:     " + getHoraSalida() + "\n" +
                "totalHr:        " + getTotalHr();
    }
}