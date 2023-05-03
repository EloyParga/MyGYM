package com.example.appgym.Tablas;

public class Usuario {
    private int pkUser;
    private String nombre;
    private String apellidos;
    private String email;
    private String Pass;

    public Usuario(int pkUser, String nombre, String apellidos, String email, String pass) {
        this.pkUser = pkUser;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        Pass = pass;
    }
    public Usuario( String nombre, String apellidos, String email, String pass) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        Pass = pass;
    }

    public int getPkUser() {
        return pkUser;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
