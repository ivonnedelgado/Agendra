package com.apps.ivonne.agenda;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Modificar extends AppCompatActivity {
    EditText Nombre, Numero;
    int Id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        Nombre = (EditText) findViewById(R.id.ediNombre);
        Numero = (EditText) findViewById(R.id.ediNum);

        Bundle b = this.getIntent().getExtras();
        if (b!=null){
            Informacion(b.getInt("idMod"));
        }
    }

    public void modificarInfo (View v) {
        String nomb = Nombre.getText().toString();
        int num = Integer.parseInt(Numero.getText().toString());

        Base baseHelper = new Base(this, "DEM008", null, 1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        if (db != null) {
            ContentValues regModificado = new ContentValues();
            regModificado.put("Nombre", nomb);
            regModificado.put("Numero", num);

            long i = db.update("Agenda", regModificado, "Id=" +Id, null);
            if (i > 0) {
                Toast.makeText(this, "Registro modificado", Toast.LENGTH_SHORT).show();
                super.onBackPressed();
            }
        }
    }

    public void Informacion (int id){
        Base baseHelper = new Base(this, "DEM008", null, 1);
        SQLiteDatabase db = baseHelper.getReadableDatabase();
        if (db!=null){
            Cursor c = db.rawQuery("select Id, Nombre, Numero from Agenda where Id=" +id, null);
            try {
                if(c.moveToFirst()){
                    Id = c.getInt(0);
                    Nombre.setText(c.getString(1));
                    Numero.setText(String.valueOf(c.getInt(2)));
                }

            } finally {
                c.close();
            }
            }
    }
}
