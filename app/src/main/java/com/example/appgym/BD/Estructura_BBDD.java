package com.example.appgym.BD;

public class Estructura_BBDD {

    //USUARIO
    //Nombre tabla
    public static final String TABLE_NAME_USER="USUARIOS";
    //Campos tabla
    public static final String ID_USER = "IDUser";
    public static final String NOMBRE_USER = "NombreUser";
    public static final String APELLIDOS = "Apellidos";
    public static final String EMAIL = "Email";
    public static final String PASS = "Pass";

    //RUTINAS
    //Nombre tabla
    public static final String TABLE_NAME_RUTINA="RUTINAS";
    //Campos tabla
    public static final String ID_RUTINA = "IDRutina";
    public static final String NOMBRE_RUTINA = "NombreRutina";
    public static final String FK_PKUSER = "FKpkUser";


    //DIA SEMANA
    //Nombre tabla
    public static final String TABLE_NAME_DIA="DIA";
    //Campos tabla
    public static final String ID_DIA = "IDdia";
    public static final String NOMBRE_DIA = "NombreDia";
    public static final String GRUPO_MUSCULAR = "GrupoMuscular";
    public static final String DESCANSO = "Descanso";
    public static final String FK_PKS = "FKpkS";


    //EJERCICIO
    //Nombre tabla
    public static final String TABLE_NAME_EJERCICIO="EJERCICIO";
    //Campos tabla
    public static final String ID_EJERCICIO = "IDEjercicio";
    public static final String NOMBRE_EJERCICIO = "NombreEjercicio";
    public static final String SERIES = "Series";
    public static final String REPS = "Reps";
    public static final String FK_PKDIA = "FKpkDia";



    //Crear Tabla USUARIO
    public static final String SQL_CREATE_ENTRIES_USER =
            "CREATE TABLE " + Estructura_BBDD.TABLE_NAME_USER + " (" +
                    Estructura_BBDD.ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Estructura_BBDD.NOMBRE_USER + " TEXT ," +
                    Estructura_BBDD.APELLIDOS + " TEXT ," +
                    Estructura_BBDD.EMAIL + " TEXT ," +
                    Estructura_BBDD.PASS + " TEXT" + ");";

    //Crear Tabla RUTINAS
    public static final String SQL_CREATE_ENTRIES_RUTINA =
            "CREATE TABLE " + Estructura_BBDD.TABLE_NAME_RUTINA + " (" +
                    Estructura_BBDD.ID_RUTINA + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                    Estructura_BBDD.NOMBRE_RUTINA + " TEXT ," +
                    Estructura_BBDD.FK_PKUSER + " INTEGER NOT NULL ,"+
                    "FOREIGN KEY ("+Estructura_BBDD.FK_PKUSER+") "+
                        "REFERENCES "+Estructura_BBDD.TABLE_NAME_USER+" ("+Estructura_BBDD.ID_USER+") "+
                    "ON DELETE CASCADE"+
                    ");";

    //Crear Tabla DIA SEMANA
    public static final String SQL_CREATE_ENTRIES_DIA=
            "CREATE TABLE " + Estructura_BBDD.TABLE_NAME_DIA + " (" +
                    Estructura_BBDD.ID_DIA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Estructura_BBDD.NOMBRE_DIA + " TEXT ,"+
                    Estructura_BBDD.GRUPO_MUSCULAR + " TEXT ,"+
                    Estructura_BBDD.DESCANSO + " BIT,"+
                    Estructura_BBDD.FK_PKS + " INTEGER NOT NULL ,"+
                    "FOREIGN KEY ("+Estructura_BBDD.FK_PKS+") "+
                    "REFERENCES "+Estructura_BBDD.TABLE_NAME_RUTINA+" ("+Estructura_BBDD.ID_RUTINA+") "+
                    "ON DELETE CASCADE"+
                    ");";

    //Crear Tabla EJERCICIOS
    public static final String SQL_CREATE_ENTRIES_EJERCICIO=
            "CREATE TABLE " + Estructura_BBDD.TABLE_NAME_EJERCICIO + " (" +
                    Estructura_BBDD.ID_EJERCICIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Estructura_BBDD.NOMBRE_EJERCICIO + " TEXT ,"+
                    Estructura_BBDD.SERIES + " INTEGER ," +
                    Estructura_BBDD.REPS + " INTEGER,"+
                    Estructura_BBDD.FK_PKDIA + " INTEGER NOT NULL ,"+
                    "FOREIGN KEY ("+Estructura_BBDD.FK_PKDIA+") "+
                    "REFERENCES "+Estructura_BBDD.TABLE_NAME_DIA+" ("+Estructura_BBDD.ID_DIA+") "+
                    "ON DELETE CASCADE"+
                    ");";


    //Borrar Tabla
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estructura_BBDD.TABLE_NAME_USER +
                    "DROP TABLE IF EXISTS " + Estructura_BBDD.TABLE_NAME_RUTINA +
                    "DROP TABLE IF EXISTS " + Estructura_BBDD.TABLE_NAME_DIA +
                    "DROP TABLE IF EXISTS " + Estructura_BBDD.TABLE_NAME_EJERCICIO ;



}
