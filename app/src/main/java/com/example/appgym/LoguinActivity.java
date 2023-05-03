package com.example.appgym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appgym.BD.BBDD_Helper;
import com.example.appgym.BD.Estructura_BBDD;

public class LoguinActivity extends AppCompatActivity {

    private BBDD_Helper helper = new BBDD_Helper(this);

    private EditText etPass;
    private EditText etEmail;
    private Button bInicio2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loguin);

        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        bInicio2 = findViewById(R.id.bInicio2);

        bInicio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();

                //Comprueba que los campos de texto del Loguin no esten vacios
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                    Toast.makeText(LoguinActivity.this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
                }else {
                    //En caso de no estarlos comprueba que los campos son correctos
                    Boolean checkEmailPass = comprobarEmailPass(email,pass);
                    if(checkEmailPass==true){
                        Toast.makeText(LoguinActivity.this,"Inicio Satisfactorio",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoguinActivity.this, ListaRutinas.class);
                        Bundle b = new Bundle(); //envia la pk del usuario a la activity siguiente
                        b.putString("pkUsuario",Integer.toString(pkUser(email,pass)) );
                        i.putExtras(b);
                        startActivity(i);

                    }else{
                        Toast.makeText(LoguinActivity.this,"Inicio Fallido",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }
    //Funcion para extraer la pk del usuario que inicia sesion
    public Integer pkUser(String email, String pass){
        SQLiteDatabase db = helper.getWritableDatabase();
        String pk = Estructura_BBDD.ID_USER;
        Cursor c = db.rawQuery("SELECT "+pk+" FROM "+ Estructura_BBDD.TABLE_NAME_USER + " WHERE "+ Estructura_BBDD.EMAIL+"=?"+
                " and " + Estructura_BBDD.PASS+"=?", new String[]{email,pass});

        Integer result = null;
        try {
            if (c.moveToFirst()) {
                result = c.getInt(0);
            }
        } finally {
            c.close();
        }
        return result;

    }
    //metodo que devuelve true o false si el email y pass introducidos coinciden en la base de datos
    public Boolean comprobarEmailPass(String email, String pass){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ Estructura_BBDD.TABLE_NAME_USER +
                " WHERE "+ Estructura_BBDD.EMAIL+"=?"+
                " and " + Estructura_BBDD.PASS+"=?", new String[]{email,pass});
        if(c.getCount()>0){
            return true;
        }else
            return false;
    }
}