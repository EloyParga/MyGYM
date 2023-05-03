package com.example.appgym.Tablas;

public class Rutina {

    private  int pkRutina;
    private String nombreS;
    private int fkPkUser;

    //constructores Con ID y sin ID
    public Rutina(  String nombre) {
        this.nombreS = nombre;
    }

    public Rutina( int ID,String nombre,int fkPkUser) {
        this.pkRutina = ID;
        this.nombreS = nombre;
        this.fkPkUser=fkPkUser;

    }


    //get y set
    public int getPkRutina() {
        return pkRutina;
    }

    public String getNombreS() {return nombreS;}

}
