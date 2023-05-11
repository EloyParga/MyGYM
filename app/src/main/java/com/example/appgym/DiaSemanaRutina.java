package com.example.appgym;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.example.appgym.Adapter.Adapter_Dia;
import com.example.appgym.Adapter.Adapter_Rutina;
import com.example.appgym.BD.BBDD_Helper;
import com.example.appgym.BD.Estructura_BBDD;
import com.example.appgym.Tablas.Dia;
import com.example.appgym.Tablas.Rutina;

import java.util.ArrayList;

public class DiaSemanaRutina extends AppCompatActivity {

    private BBDD_Helper helper = new BBDD_Helper(this);
    private ArrayList<Dia> listaDiasSemana;
    private RecyclerView rvDias;
    private Adapter_Dia adapter_dia;

    private String pkRutina;
    private int id_dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_semana_rutina);

        Bundle b= getIntent().getExtras();
        if(b !=null){
            pkRutina= b.getString("pkRutina");
        }else{

            Toast.makeText(getApplicationContext(),"El Bundel es Null",Toast.LENGTH_SHORT).show();
        }


        rvDias = findViewById(R.id.rvDias);

        rvDias.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        listaDiasSemana = new ArrayList<Dia>();

        listarDiasDB();

        adapter_dia = new Adapter_Dia(listaDiasSemana, new Adapter_Dia.ItemClickListener() {
            @Override
            public void onItemClick(Dia details) {
                Toast.makeText(DiaSemanaRutina.this,details.getNombreDia() + " Clicked",Toast.LENGTH_SHORT).show();
                id_dia = details.getPkDia();
                Alert(details);
            }
        });

        rvDias.setAdapter(adapter_dia);
    }
    private void Toast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void Alert(Dia details){

        //Formato de alerta
        new AlertDialog.Builder(this)
                .setTitle("¿ Desea seleccionar el "+details.getNombreDia()+ " ?")
                //Se pone un icono a la ventana de alerta
                .setIcon(R.drawable.logo)

                //boton Si
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int posicion) {
                        //Lanza la activity donde se Inspeccionan los ejercicios de cada dia
                        ActivityListaEj();


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

    public void listarDiasDB(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Estructura_BBDD.TABLE_NAME_DIA +
                        " WHERE " + Estructura_BBDD.FK_PKS + " = ?",
                new String[] {pkRutina});
        if(c.moveToFirst()){
            do{
                Dia dia = new Dia(c.getInt(0),c.getString(1),c.getString(2),c.getInt(3));
                listaDiasSemana.add(dia);
            }while (c.moveToNext());
        }
    }

    public void ActivityListaEj(){
        Intent i = new Intent(this, ListaEjerciciosDia.class);
        Bundle b = new Bundle();
        b.putString("pkRutina",pkRutina);
        b.putString("pkDia", Integer.toString(id_dia));
        i.putExtras(b);
        startActivity(i);
        finish();

    }


}