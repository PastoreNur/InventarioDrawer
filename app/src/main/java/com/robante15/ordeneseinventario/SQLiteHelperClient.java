package com.robante15.ordeneseinventario;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class SQLiteHelperClient extends SQLiteOpenHelper {

    //String tabla_articulo="CREATE TABLE articulos(codigo"+ID_PK+",descripcion text, precio real )";

    private String tabla_client = "CREATE TABLE clientes(id INTEGER PRIMARY KEY AUTOINCREMENT, _id text, " +
            "nombres text, apellidos text, direccion text, email text, dui int, nit int, telefono int)";

    public SQLiteHelperClient(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public List<Client> baseDatosLocal() {
        SQLiteDatabase bd = this.getWritableDatabase();
        Cursor filas = bd.rawQuery("select * from clientes", null);
        List<Client> clientList = new ArrayList<>();

        while(filas.moveToNext()) {
            Client client = new Client(Long.parseLong(filas.getString(0)),
                    filas.getString(1),filas.getString(2)
                    ,filas.getString(3),filas.getString(4),
                    Long.parseLong(filas.getString(8)),filas.getString(5),
                    Integer.parseInt(filas.getString(6)),
                    Long.parseLong(filas.getString(7)));
            clientList.add(client);
        }

        bd.close();

        return clientList;
    }

    //deben crearse inicialmente estos 2 metodos  onCreate(SQLiteDatabase db), onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    @Override
    public void onCreate(SQLiteDatabase db) {
        //el metodo  ejecuta una sentencia para crear la tabla con sus campos y tipos
        db.execSQL(tabla_client);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}