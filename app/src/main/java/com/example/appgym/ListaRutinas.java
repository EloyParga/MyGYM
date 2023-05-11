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
import android.view.View;
import android.widget.Toast;

import com.example.appgym.Adapter.Adapter_Rutina;
import com.example.appgym.BD.BBDD_Helper;
import com.example.appgym.BD.Estructura_BBDD;
import com.example.appgym.Tablas.Rutina;

import java.util.ArrayList;

public class ListaRutinas extends AppCompatActivity {
    private BBDD_Helper helper = new BBDD_Helper(this);
    private ArrayList<Rutina> listaRutinas;
    private RecyclerView rvRutinas;
    private Adapter_Rutina adapter_rutina;
    private int id_rutina;

    private String pkUsuario;

    private String fkPkUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_rutinas);

        Bundle b= getIntent().getExtras();
        if(b !=null){
            pkUsuario= b.getString("pkUsuario");
        }else{
            Toast.makeText(getApplicationContext(),"El Bundel es Null",Toast.LENGTH_SHORT).show();
        }

        rvRutinas = findViewById(R.id.rvRutinas);

        rvRutinas.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        listaRutinas = new ArrayList<Rutina>();


        listarRutinasDB();

        adapter_rutina = new Adapter_Rutina(listaRutinas, new Adapter_Rutina.ItemClickListener() {
            @Override
            public void onItemClick(Rutina details) {
                Toast(details.getNombreS() + " Clicked");
                id_rutina = details.getPkRutina();
                Alert();
            }
        });

        rvRutinas.setAdapter(adapter_rutina);

    }
    private void Toast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void Alert(){

        //Formato de alerta
        new AlertDialog.Builder(this)
                .setTitle("Editar")
                //Se pone el logo de la App a la ventana de alerta
               .setIcon(R.drawable.logo)

                //Boton cancelar
                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int posicion) {
                        dialog.dismiss();
                    }
                })
                //boton Inspeccionar
                .setPositiveButton("Inspeccionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int posicion) {
                        //Lanza la activity donde se Inspeccionan los dias
                        ActivityDias();

                    }
                })
                //Boton eliminar
                .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int posicion) {

                        SQLiteDatabase db = helper.getWritableDatabase();

                        String selection = Estructura_BBDD.ID_RUTINA + " LIKE ?";

                        String[] selectionArgs = {Integer.toString(id_rutina)};

                        //elimina de la BD
                        db.delete(Estructura_BBDD.TABLE_NAME_RUTINA, selection, selectionArgs);
                        Toast(id_rutina + " Clicked");
                        //refresca la lista
                        refres();
                        // cierra BD
                        db.close();

                    }
                }).show();
    }
    public  void refres(){
        //muestra la lista de productos del arrayList
        listaRutinas = new ArrayList<Rutina>();

                listarRutinasDB();


        adapter_rutina = new Adapter_Rutina(listaRutinas, new Adapter_Rutina.ItemClickListener() {
            @Override
            public void onItemClick(Rutina details) {
                Toast(details.getNombreS() + " Clicked");
                id_rutina = details.getPkRutina();
                Alert();

            }


        });

        rvRutinas.setAdapter(adapter_rutina);
    }
    //Metodo Para listar todas las Rutinas de la BD
    public void listarRutinasDB(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Estructura_BBDD.TABLE_NAME_RUTINA +
                        " WHERE " + Estructura_BBDD.FK_PKUSER + " = ?",
                new String[] {pkUsuario});
        if(c.moveToFirst()){
            do{
                Rutina rutina = new Rutina(c.getInt(0),c.getString(1),c.getInt(2));
                listaRutinas.add(rutina);
            }while (c.moveToNext());
        }
    }

    public void Volver(View view){
        finish();
    }

    //boton que lleva a la activity añadir rutina
    public void AñadirRutina(View view){
        Intent i =new Intent(this, NuevaRutina1.class);
        Bundle b = new Bundle();
        b.putString("pkUsuario",pkUsuario  );
        i.putExtras(b);

        startActivity(i);
        finish();

    }
    public void ActivityDias(){
        Intent i =new Intent(this, DiaSemanaRutina.class);
        Bundle b = new Bundle();
        b.putString("pkRutina",Integer.toString(id_rutina));
        i.putExtras(b);
        startActivity(i);

    }


}