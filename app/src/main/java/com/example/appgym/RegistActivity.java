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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistActivity extends AppCompatActivity {
    private BBDD_Helper helper = new BBDD_Helper(this);
    private EditText etPersonName;
    private EditText etApellidos;
    private EditText etEmailAddress;
    private EditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        etPersonName=findViewById(R.id.etPersonName);
        etApellidos=findViewById(R.id.etApellidos);
        etEmailAddress=findViewById(R.id.etEmailAddress);
        etPassword=findViewById(R.id.etPassword);

    }

    public void ReagistrarUsuarios(View vista){
        int idRutina = 0;

        //verifica que los campos no esten vacios
        if(etPersonName.getText().toString().isEmpty() ||
                etApellidos.getText().toString().isEmpty() ||
                etEmailAddress.getText().toString().isEmpty() ||
                etPassword.getText().toString().isEmpty() ) {

            Toast.makeText(getApplicationContext(), "No puedes dejar campos vacios", Toast.LENGTH_LONG).show();
        }else if(!validarCorreoElectronico(etEmailAddress.getText().toString())){
            Toast.makeText(getApplicationContext(),"Correo electrónico inválido", Toast.LENGTH_LONG).show();
        }else {

            //declara helper
            SQLiteDatabase db = helper.getWritableDatabase();

            //Valores que se van a insertar
            ContentValues values = new ContentValues();
            values.put(Estructura_BBDD.NOMBRE_USER, etPersonName.getText().toString());
            values.put(Estructura_BBDD.APELLIDOS, etApellidos.getText().toString());
            values.put(Estructura_BBDD.EMAIL, etEmailAddress.getText().toString());
            values.put(Estructura_BBDD.PASS, etPassword.getText().toString());

            //insert
            db.insert(Estructura_BBDD.TABLE_NAME_USER, null, values);

            Toast.makeText(getApplicationContext(), "Usuario Registrado", Toast.LENGTH_LONG).show();


            etPersonName.setText("");
            etApellidos.setText("");
            etEmailAddress.setText("");
            etPassword.setText("");

        };



    }

    private boolean validarCorreoElectronico(String correo) {
        // Expresión regular para validar el formato del correo electrónico
        String patron = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // Compila la expresión regular en un patrón
        Pattern pattern = Pattern.compile(patron);

        // Crea un objeto Matcher para el correo proporcionado
        Matcher matcher = pattern.matcher(correo);

        // Retorna true si el correo coincide con el patrón, de lo contrario, retorna false
        return matcher.matches();
    }
}