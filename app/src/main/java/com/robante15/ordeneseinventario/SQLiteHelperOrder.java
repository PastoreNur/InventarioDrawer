package com.robante15.ordeneseinventario;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelperOrder extends SQLiteOpenHelper {

    //String tabla_articulo="CREATE TABLE articulos(codigo"+ID_PK+",descripcion text, precio real )";

    private String tabla_client = "CREATE TABLE ordenes(id INTEGER PRIMARY KEY AUTOINCREMENT, _id text, " +
            "fecha int, total real, estado text)";

    public SQLiteHelperOrder(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public List<Client> baseDatosLocal() {
        SQLiteDatabase bd = this.getWritableDatabase();
        Cursor filas = bd.rawQuery("select * from ordenes", null);
        List<Order> orderList = new ArrayList<>();

        while(filas.moveToNext()) {
            Order order = new Order());
            orderList.add(order);
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
