package com.example.appgym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appgym.BD.BBDD_Helper;
import com.example.appgym.BD.Estructura_BBDD;
import com.example.appgym.Tablas.Ejercicio;
import com.example.appgym.Tablas.Rutina;

public class AddEj extends AppCompatActivity {

    private BBDD_Helper helper = new BBDD_Helper(this);

    private EditText etNameEj;
    private EditText etSeries;
    private EditText etReps;

    private String pkDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ej);

        etNameEj = findViewById(R.id.etNameEj);
        etSeries = findViewById(R.id.etSeries);
        etReps = findViewById(R.id.etReps);


        Bundle b= getIntent().getExtras();
        pkDia= b.getString("pkDia");

    }

    public void AddEjercicio(View v){
        int idEj = 0 ;

        //verificar que los campos no estan vacios
        if(etNameEj.getText().toString().isEmpty() ||
            etSeries.getText().toString().isEmpty() ||
            etReps.getText().toString().isEmpty() ){

            Toast.makeText(getApplicationContext(),"No se pueden dejar campos vacios", Toast.LENGTH_SHORT).show();

        }else{
            SQLiteDatabase db = helper.getWritableDatabase();

            //Valores que se van a insertar
            ContentValues values = new ContentValues();
            values.put(Estructura_BBDD.NOMBRE_EJERCICIO, etNameEj.getText().toString());
            values.put(Estructura_BBDD.SERIES, etSeries.getText().toString());
            values.put(Estructura_BBDD.REPS, etReps.getText().toString());
            values.put(Estructura_BBDD.FK_PKDIA, pkDia );

            //insert
            db.insert(Estructura_BBDD.TABLE_NAME_EJERCICIO, null, values);

            Toast.makeText(getApplicationContext(), "Ejercicio Añadido", Toast.LENGTH_SHORT).show();

            //Datos que extraemos de cada elemento
            String[] projection = {
                    Estructura_BBDD.ID_EJERCICIO,
            };


            //Dato que extraemos para el select
            String selection = Estructura_BBDD.ID_EJERCICIO ;
            String orderBy = Estructura_BBDD.ID_EJERCICIO + " desc";


            try {
                //Cursos
                Cursor c = db.query(
                        Estructura_BBDD.TABLE_NAME_EJERCICIO,
                        projection,
                        selection,
                        null,
                        null,
                        null,
                        orderBy
                );

                while(!c.isLast()){
                    c.moveToNext();
                    idEj= c.getInt(0);
                    break;

                }


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "No se encontró registro", Toast.LENGTH_LONG).show();
            }

            String nameEj=etNameEj.getText().toString();
            int series=Integer.parseInt(etSeries.getText().toString());
            int reps=Integer.parseInt(etReps.getText().toString());

            Ejercicio ej = new Ejercicio(idEj,nameEj,series,reps);
            Toast.makeText(this, Integer.toString(idEj), Toast.LENGTH_SHORT).show();

            etNameEj.setText("");
            etSeries.setText("");
            etReps.setText("");

        }

    }
}