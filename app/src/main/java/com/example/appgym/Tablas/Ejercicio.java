package com.example.appgym.Tablas;

public class Ejercicio {
    private int pkEj;
    private String nombreEj;
    private int series;
    private int reps;

    public Ejercicio(int pkEj, String nombreEj, int series, int reps) {
        this.pkEj = pkEj;
        this.nombreEj = nombreEj;
        this.series = series;
        this.reps = reps;
    }

    public Ejercicio(String nombreEj, int series, int reps) {
        this.nombreEj = nombreEj;
        this.series = series;
        this.reps = reps;
    }

    public int getPkEj() {
        return pkEj;
    }

    public String getNombreEj() {
        return nombreEj;
    }

    public void setNombreEj(String nombreEj) {
        this.nombreEj = nombreEj;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
