package com.apps.ivonne.agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by IVONNE on 19/04/2017.
 */

public class Base extends SQLiteOpenHelper {
    String tabla = "CREATE TABLE Agenda (Id INTEGER PRIMARY KEY AUTOINCREMENT, Nombre Text, Numero INTEGER)";

    public Base(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
