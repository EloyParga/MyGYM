package com.example.appgym;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


import com.example.appgym.Adapter.Adapter_Ejercicio;
import com.example.appgym.Adapter.Adapter_Rutina;
import com.example.appgym.BD.BBDD_Helper;
import com.example.appgym.BD.Estructura_BBDD;
import com.example.appgym.Tablas.Dia;
import com.example.appgym.Tablas.Ejercicio;
import com.example.appgym.Tablas.Rutina;

import java.util.ArrayList;

public class ListaEjerciciosDia extends AppCompatActivity {

    private BBDD_Helper helper = new BBDD_Helper(this);
    private ArrayList<Ejercicio> listaEj;
    private RecyclerView rvEj;
    private Adapter_Ejercicio adapter_ejercicio;
    private EditText etGrupoMusc;
    private Switch swDescanso;
    private String pkEj;
    private String pkDia;
    private int id_ej;

    private String pkRutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ejercicios_dia);

        rvEj = findViewById(R.id.rvEj);

        etGrupoMusc = findViewById(R.id.etGrupoMusc);

        swDescanso = findViewById(R.id.swDescanso);

        Bundle b = getIntent().getExtras();

        pkRutina = b.getString("pkRutina");
        pkDia = b.getString("pkDia");

        if(etGrupoMusc.getText().toString().toUpperCase()=="Descanso".toUpperCase()){
            swDescanso.setChecked(true);
        }


        rvEj.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        listaEj = new ArrayList<Ejercicio>();

        Dia dia = obtenerDiaDeLaBaseDeDatos();
        addDatosDia(dia);
        listarEjDB();

        adapter_ejercicio = new Adapter_Ejercicio(listaEj, new Adapter_Ejercicio.ItemClickListener() {
            @Override
            public void onItemClick(Ejercicio details) {
                Toast(details.getNombreEj() + " Clicked");
                id_ej = details.getPkEj();
                Alert();
            }
        });

        rvEj.setAdapter(adapter_ejercicio);
    }
    private void Toast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void Alert(){

        //Formato de alerta
        new AlertDialog.Builder(this)
                .setTitle("¿ Desea Eliminar el ejercicio?")
                //Se pone un icono a la ventana de alerta
                .setIcon(R.drawable.logo)

                //boton Si para eliminar
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int posicion) {
                        SQLiteDatabase db = helper.getWritableDatabase();

                        String selection = Estructura_BBDD.ID_EJERCICIO + " LIKE ?";

                        String[] selectionArgs = {Integer.toString(id_ej)};

                        //elimina de la BD
                        db.delete(Estructura_BBDD.TABLE_NAME_EJERCICIO, selection, selectionArgs);
                        Toast(id_ej + " Clicked");
                        //refresca la lista
                        refres();
                        // cierra BD
                        db.close();
                    }
                })
                //Boton No
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int posicion) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public  void refres(){
        //muestra la lista de productos del arrayList
        listaEj = new ArrayList<Ejercicio>();

        listarEjDB();
        adapter_ejercicio = new Adapter_Ejercicio(listaEj, new Adapter_Ejercicio.ItemClickListener() {
            @Override
            public void onItemClick(Ejercicio details) {
                id_ej = details.getPkEj();
                Alert();
            }


        });

        rvEj.setAdapter(adapter_ejercicio);
    }
    public void listarEjDB(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Estructura_BBDD.TABLE_NAME_EJERCICIO +
                        " WHERE " + Estructura_BBDD.FK_PKDIA + " = ?",
                new String[] {pkDia});
        if(c.moveToFirst()){
            do{
                Ejercicio ej = new Ejercicio(c.getInt(0),c.getString(1),c.getInt(2),c.getInt(3));
                listaEj.add(ej);
            }while (c.moveToNext());
        }
    }

    public void ActivityEj(View view){
        Intent i = new Intent(this, AddEj.class);
        Bundle b = new Bundle();
        b.putString("pkDia",pkDia);
        b.putString("pkRutina",pkRutina);
        i.putExtras(b);
        finish();
        startActivity(i);
    }

    public void GuardarDatos(View view){

            int idEj = 0;

            if(swDescanso.isChecked()){
            etGrupoMusc.setText("Descanso".toUpperCase());

            }

            //verifica que los campos no esten vacios
            if(etGrupoMusc.getText().toString().isEmpty() ){

                Toast.makeText(getApplicationContext(),"Añade nombre a tu rutina", Toast.LENGTH_LONG).show();

            }else {

                //declara helper
                SQLiteDatabase db = helper.getWritableDatabase();

                int descanso = swDescanso.isChecked() ? 1 : 0;

                //Valores que se van a Actualizar
                ContentValues values = new ContentValues();
                values.put(Estructura_BBDD.GRUPO_MUSCULAR, etGrupoMusc.getText().toString());
                values.put(Estructura_BBDD.DESCANSO, descanso);

                String selection = Estructura_BBDD.ID_DIA + " LIKE ?";
                String[] selectionArgs = { pkDia };

                //Update

                db.update(Estructura_BBDD.TABLE_NAME_DIA, values, selection, selectionArgs);

                Toast.makeText(getApplicationContext(), "Registro guardado correctamente", Toast.LENGTH_LONG).show();

                Intent i = new Intent(this, DiaSemanaRutina.class);
                Bundle b = new Bundle();
                b.putString("pkRutina",pkRutina);
                i.putExtras(b);
                startActivity(i);
                finish();

            }


    }

    private Dia obtenerDiaDeLaBaseDeDatos() {
        SQLiteDatabase db = helper.getReadableDatabase();

        // Define las columnas que deseas recuperar
        String[] projection = {
                Estructura_BBDD.GRUPO_MUSCULAR,
                Estructura_BBDD.DESCANSO
        };

        // Define la cláusula WHERE para filtrar los resultados si es necesario
        String selection = Estructura_BBDD.ID_DIA + " = ?";
        String[] selectionArgs = {pkDia};

        // Realiza la consulta a la base de datos
        Cursor cursor = db.query(
                Estructura_BBDD.TABLE_NAME_DIA, // Nombre de la tabla
                projection, // Columnas a recuperar
                selection, // Cláusula WHERE
                selectionArgs, // Argumentos de la cláusula WHERE
                null, // GROUP BY
                null, // HAVING
                null // ORDER BY
        );

        Dia dia = null;

        // Verifica si se encontraron resultados
        if (cursor.moveToFirst()) {
            // Recupera los valores de las columnas
            String grupoMuscular = cursor.getString(cursor.getColumnIndexOrThrow(Estructura_BBDD.GRUPO_MUSCULAR));
            int descanso =  cursor.getInt(cursor.getColumnIndexOrThrow(Estructura_BBDD.DESCANSO));

            boolean descans;

            if (descanso==1){
                descans=true;
            }else {
                descans = false;
            }

            // Crea un objeto Dia con los valores recuperados
            dia = new Dia(grupoMuscular, descans);
        }

        // Cierra el cursor y la base de datos
        cursor.close();
        db.close();

        return dia;
    }

    public void addDatosDia(Dia dia){
        if(dia.isDescanso()==true){
            swDescanso.setChecked(true);
        }else{
            swDescanso.setChecked(false);
        }
        etGrupoMusc.setText(dia.getGrupoMuscular());
    }

    public void atras(View v){
        finish();
        Intent i = new Intent(this, DiaSemanaRutina.class);
        Bundle b = new Bundle();
        b.putString("pkRutina",pkRutina);
        i.putExtras(b);
        startActivity(i);
    }



}