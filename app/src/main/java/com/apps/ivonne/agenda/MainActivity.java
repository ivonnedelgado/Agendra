package com.apps.ivonne.agenda;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompatBase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    String [] arreglo;
    ListView lista;
    protected Object nActionMode;
    public int selectedItem = -1;

    @Override
    protected void onResume() {
        super.onResume();
        mos();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = (ListView) findViewById(R.id.lisView);
        lista.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(nActionMode!=null){
                    return false;
                }
                selectedItem = position;
                nActionMode = MainActivity.this.startActionMode(amc);
                view.setSelected(true);
                return true;
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int idModificar = Integer.parseInt(arreglo[position].split(" ")[0]);
                Intent intent = new Intent(MainActivity.this, Modificar.class);
                intent.putExtra("idMod", idModificar);
                startActivity(intent);
            }
        });

        Button bing = (Button) findViewById(R.id.bIng);
        bing.setOnClickListener(this);
    }

    public void mos(){
        Base baseHelper = new Base(this, "DEM008", null, 1);
        SQLiteDatabase db = baseHelper.getReadableDatabase();
        if (db!=null){
            Cursor c = db.rawQuery("select*from Agenda", null);
            int can = c.getCount();
            int i=0;
            arreglo = new String[can];
            if(c.moveToFirst()){
                do{
                    String linea = c.getInt(0)+ "      " + c.getString(1)+ "      " + c.getInt(2);
                    arreglo[i] = linea;
                    i++;
                } while (c.moveToNext());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arreglo);
            ListView lis = (ListView) findViewById(R.id.lisView);
            lis.setAdapter(adapter);
        }
    }


    private ActionMode.Callback amc = new ActionMode.Callback(){
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.menu_borrar:
                    Borrar();
                    actionMode.finish();
                    return true;
                default:
                    return false;

            }
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            nActionMode = null;
            selectedItem = -1;
        }
    };

    @Override
    public void onClick(View view) {
        int selec = view.getId();
        try {
            switch (selec){
                case R.id.bIng:
                    Intent ing = new Intent(MainActivity.this, Ingresa.class);
                    startActivity(ing);
                    break;
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void Borrar(){
        Base baseHelper = new Base(this, "DEM008", null, 1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();
        int id = Integer.parseInt(arreglo[selectedItem].split(" ")[0]);

        if (db != null) {
            long res = db.delete("Agenda", "Id=" +id, null);
            if (res>0){
                Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                mos();
            }
        }
    }
}
