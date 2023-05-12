package com.example.appgym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appgym.BD.BBDD_Helper;
import com.example.appgym.BD.Estructura_BBDD;
import com.example.appgym.Tablas.Rutina;

import java.util.Arrays;

public class NuevaRutina1 extends AppCompatActivity {
    private BBDD_Helper helper = new BBDD_Helper(this);
    private EditText etNameRutina;

    private int pkUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_rutina1);

        etNameRutina= findViewById(R.id.etNameRutina);

        Bundle b= getIntent().getExtras();
        if(b !=null){
            pkUsuario= Integer.parseInt(b.getString("pkUsuario"));

        }else{
            Toast.makeText(getApplicationContext(),"El Bundel es Null",Toast.LENGTH_SHORT).show();
        }

    }

    //Metodo insertar en la BD

    public void insertarRutina(View vista){
        int idRutina = 0;

        //verifica que los campos no esten vacios
        if(etNameRutina.getText().toString().isEmpty() ){

            Toast.makeText(getApplicationContext(),"Añade nombre a tu rutina", Toast.LENGTH_LONG).show();

        }else {

            //declara helper
            SQLiteDatabase db = helper.getWritableDatabase();

            //Valores que se van a insertar
            ContentValues values = new ContentValues();
            values.put(Estructura_BBDD.NOMBRE_RUTINA, etNameRutina.getText().toString());
            values.put(Estructura_BBDD.FK_PKUSER, pkUsuario );

            //insert
            db.insert(Estructura_BBDD.TABLE_NAME_RUTINA, null, values);

            Toast.makeText(getApplicationContext(), "Registro guardado correctamente", Toast.LENGTH_LONG).show();




                //Datos que extraemos de cada elemento
                String[] projection = {
                        Estructura_BBDD.ID_RUTINA,
                };


                //Dato que extraemos para el select
                String selection = Estructura_BBDD.ID_RUTINA ;
                String orderBy = Estructura_BBDD.ID_RUTINA + " desc";

                try {
                    //Cursos
                    Cursor c = db.query(
                            Estructura_BBDD.TABLE_NAME_RUTINA,
                            projection,
                            selection,
                            null,
                            null,
                            null,
                            orderBy
                    );

                    while(!c.isLast()){
                        c.moveToNext();
                        idRutina= c.getInt(0);
                        break;

                    }


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "No se encontró registro", Toast.LENGTH_LONG).show();
                }



            Rutina rutina = new Rutina(idRutina,etNameRutina.getText().toString(),pkUsuario);
            Toast.makeText(this, Integer.toString(idRutina), Toast.LENGTH_SHORT).show();

            etNameRutina.setText("");


            String[] ArrayDias = {"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"};

            for (String array:ArrayDias) {
                values = new ContentValues();
                values.put(Estructura_BBDD.NOMBRE_DIA, array);
                values.put(Estructura_BBDD.GRUPO_MUSCULAR, " ");
                values.put(Estructura_BBDD.DESCANSO, 0);
                values.put(Estructura_BBDD.FK_PKS, idRutina);
                db.insert(Estructura_BBDD.TABLE_NAME_DIA, null, values);
            }



            Toast.makeText(getApplicationContext(), "AÑADIDO 7 DIAS", Toast.LENGTH_LONG).show();

            Intent i =  new Intent(this,ListaRutinas.class);
            Bundle b = new Bundle(); //envia la pk del usuario a la activity siguiente
            b.putString("pkUsuario",Integer.toString(pkUsuario) );
            i.putExtras(b);
            startActivity(i);
            finish();

        }
    }

    public void Volver(View v){
        finish();
        Intent i =  new Intent(this,ListaRutinas.class);
        Bundle b = new Bundle();
        b.putString("pkUsuario",Integer.toString(pkUsuario) );
        i.putExtras(b);
        startActivity(i);
    }



}