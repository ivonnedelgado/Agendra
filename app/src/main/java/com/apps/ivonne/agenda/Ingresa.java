package com.apps.ivonne.agenda;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Ingresa extends AppCompatActivity{
    EditText Nom, Num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresa);
        Nom = (EditText) findViewById(R.id.ediNomb);
        Num = (EditText) findViewById(R.id.ediNum);
    }

    public void ingresa (View v) {
        String nomb = Nom.getText().toString();
        int num = Integer.parseInt(Num.getText().toString());

        Base baseHelper = new Base(this, "DEM008", null, 1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        if (db != null) {
            ContentValues registro = new ContentValues();
            registro.put("Nombre", nomb);
            registro.put("Numero", num);

            long i = db.insert("Agenda", null, registro);
            if (i > 0) {
                Toast.makeText(this, "Registro hecho", Toast.LENGTH_SHORT).show();
                Intent ing = new Intent(Ingresa.this, MainActivity.class);
                startActivity(ing);
            }
        }
    }

}
