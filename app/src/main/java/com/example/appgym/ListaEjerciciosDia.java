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

        etGrupoMusc= findViewById(R.id.etGrupoMusc);

        swDescanso = findViewById(R.id.swDescanso);

        Bundle b= getIntent().getExtras();
        pkRutina= b.getString("pkRutina");
        pkDia = b.getString("pkDia");

        rvEj.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        listaEj = new ArrayList<Ejercicio>();

        if(swDescanso.isSelected()){
            etGrupoMusc.setText("Descanso");

        }
        if(!swDescanso.isSelected()){
            etGrupoMusc.setText("");
        }
       // listarEjDB();

        adapter_ejercicio = new Adapter_Ejercicio(listaEj, new Adapter_Ejercicio.ItemClickListener() {
            @Override
            public void onItemClick(Ejercicio details) {
                Toast(details.getNombreEj() + " Clicked");
                id_ej = details.getPkEj();
                Alert(details);
            }
        });

        rvEj.setAdapter(adapter_ejercicio);
    }
    private void Toast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void Alert(Ejercicio details){

        //Formato de alerta
        new AlertDialog.Builder(this)
                .setTitle("¿ Desea Eliminar "+details.getNombreEj()+ " ?")
                // .setIcon(R.drawable. ...)

                //boton Si para eliminar
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int posicion) {

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
    public void listarEjDB(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Estructura_BBDD.TABLE_NAME_EJERCICIO +
                        " WHERE " + Estructura_BBDD.FK_PKDIA + " = ?",
                new String[] {pkEj});
        if(c.moveToFirst()){
            do{
                Ejercicio ej = new Ejercicio(c.getInt(0),c.getString(1),c.getInt(2),c.getInt(3));
                listaEj.add(ej);
            }while (c.moveToNext());
        }
    }

    public void ActivityEj(View view){
        Intent i = new Intent(this, AddEj.class);
        startActivity(i);
    }

    public void GuardarDatos(View view){

            int idEj = 0;

            //verifica que los campos no esten vacios
            if(etGrupoMusc.getText().toString().isEmpty() ){

                Toast.makeText(getApplicationContext(),"Añade nombre a tu rutina", Toast.LENGTH_LONG).show();

            }else {

                //declara helper
                SQLiteDatabase db = helper.getWritableDatabase();

                int descanso = swDescanso.isSelected() ? 1 : 0;

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

}