package org.example.modelo;

public class Usuario {

    // asignacion de atributos
    private int id_User = 0;
    private String usuario = "";
    private String contrasena = "";
    private String nivel_Pri = "";
    private int id_Empleado1 = 0;

    // validar niver de privilegios
    private static final String[] PRIVILEGIOS_VALIDOS = {"administrador", "encargado", "empleado", null};


    // constructores (vacio / con atributos )
    public Usuario() {
    }

    public Usuario(int id_User, String usuario, String contrasena, String nivel_Pri, int id_Empleado1) {
        setId_User(id_User);
        setUsuario(usuario);
        setContrasena(contrasena);
        setNivel_Pri(nivel_Pri);
        setId_Empleado1(id_Empleado1);
    }

    // geters (formatos) y setters (condiciones para guardar)


    public int getId_User() {
        return id_User;
    }

    public void setId_User(int id_User) {
        if (id_User > 0) {
            this.id_User = id_User;
        } else {
            System.out.println("El id no debe ser menor a 0");
        }
    }

    public String getUsuario() {
        String usuarioFormato = "";
        if (this.usuario != null) {
            usuarioFormato = this.usuario.toLowerCase();
        }
        return usuarioFormato;
    }

    public void setUsuario(String usuario) {
        if (usuario == null || usuario.isBlank()) {
            System.out.println("El nombre del usuario es un requisito");
        } else {
            this.usuario = usuario;
        }
    }

    public String getContrasena() {
        String contrasenaFormato = "";
        if (this.contrasena != null) {
            contrasenaFormato = this.contrasena;
        }
        return contrasenaFormato;
    }

    public void setContrasena(String contrasena) {
        if (contrasena == null || contrasena.isBlank()) {
            System.out.println("La contraseña del usuario es un requisito");
        } else {
            this.contrasena = contrasena;
        }
    }

    public String getNivel_Pri() {
        String nivel_PriFormato = "";
        if (this.nivel_Pri != null) {
            nivel_PriFormato = this.nivel_Pri.toLowerCase();
        }
        return nivel_PriFormato;

    }

    public void setNivel_Pri(String nivel_Pri) {
        if (ValidarPrivilegio(nivel_Pri)) {
            this.nivel_Pri = nivel_Pri;
        } else {
            System.out.println("Nivel de privilegios no valido");
        }

    }

    public int getId_Empleado1() {
        return id_Empleado1;
    }

    public void setId_Empleado1(int id_Empleado1) {
        if (id_Empleado1 > 0) {
            this.id_Empleado1 = id_Empleado1;
        } else {
            System.out.println("El id no debe ser menor a 0");
        }
    }

    // toString que muestra los datos de cada Usuario 
    @Override
    public String toString() {
        return "Id Usuario: " + getId_User() + '\n' +
                "Usuario: " + getUsuario() + '\n' +
                "Contraseña: " + getContrasena() + '\n' +
                "Nivel de privilegios: " + getNivel_Pri() + '\n' +
                "Id Empleado: " + getId_Empleado1();
    }

    // Validar nivel de privilegios
    public boolean ValidarPrivilegio(String privilegio) {
        if (privilegio == null) {
            return false;
        }
        for (String validar : PRIVILEGIOS_VALIDOS) {
            if (validar != null && privilegio.equalsIgnoreCase(validar)) {
                return true;
            }
        }
        return false;
    }
}



