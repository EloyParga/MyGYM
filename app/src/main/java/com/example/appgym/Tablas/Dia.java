package com.example.appgym.Tablas;

public class Dia {
    private int pkDia;
    private String nombreDia;
    private String grupoMuscular;
    private boolean descanso;

    private int fkPkRutina;


    public Dia(int pkDia,String nombreDia,String grupoMuscular, int fkPkRutina) {
        this.pkDia=pkDia;
        this.grupoMuscular = grupoMuscular;
        this.nombreDia = nombreDia;
        this.fkPkRutina=fkPkRutina;
    }
    public Dia(String grupoMuscular, boolean descanso) {
        this.grupoMuscular = grupoMuscular;
        this.descanso = descanso;
    }

    public int getPkDia() {
        return pkDia;
    }

    public String getNombreDia() {
        return nombreDia;
    }

    public String getGrupoMuscular() {
        return grupoMuscular;
    }


    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public boolean isDescanso() {
        return descanso;
    }

    public void setDescanso(boolean descanso) {
        this.descanso = descanso;
    }
}
