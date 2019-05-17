package com.robante15.ordeneseinventario;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelperOrder extends SQLiteOpenHelper {


    private String tabla_ordenes = "CREATE TABLE ordenes(id INTEGER PRIMARY KEY AUTOINCREMENT, _id text, " +
            "fecha int, total real, estado text, client text)";

    private String tabla_pivote = "CREATE TABLE pivote(id INTEGER PRIMARY KEY AUTOINCREMENT, _id_Orden text, _id_producto INTEGER, cantidad INTEGER)";

    private String tabla_client = "CREATE TABLE clientes(id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, _id text, " +
            "nombres text, apellidos text, direccion text, email text, dui int, nit int, telefono int, estado text)";

    public SQLiteHelperOrder(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public List<Client> baseDatosLocalClient() {
        SQLiteDatabase bd = this.getWritableDatabase();
        Cursor filas = bd.rawQuery("select * from clientes", null);
        List<Client> clientList = new ArrayList<>();

        while(filas.moveToNext()) {
            Client client = new Client(Long.parseLong(filas.getString(0)),
                    filas.getString(1),filas.getString(2)
                    ,filas.getString(3),filas.getString(4),
                    Long.parseLong(filas.getString(8)),filas.getString(5),
                    Integer.parseInt(filas.getString(6)),
                    Long.parseLong(filas.getString(7)),filas.getString(9));
            clientList.add(client);
        }

        bd.close();

        return clientList;
    }

    public List<Order> baseDatosLocal() {

        SQLiteDatabase bd = this.getWritableDatabase();

        Cursor filas = bd.rawQuery("select * from ordenes", null);

        List<Product> productList = new ArrayList<>();
        List<Integer> cantidad = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();
        Client client;

        while (filas.moveToNext()){
            /*Cursor filasc = bd.rawQuery("SELECT * FROM clientes WHERE _id = \"" + filas.getString(5) + '\"' , null);
            client = new Client(Long.parseLong(filasc.getString(0)),
                    filasc.getString(1),filasc.getString(2)
                    ,filasc.getString(3),filasc.getString(4),
                    Long.parseLong(filasc.getString(8)),filasc.getString(5),
                    Integer.parseInt(filasc.getString(6)),
                    Long.parseLong(filasc.getString(7)),filasc.getString(9));
*/
            /*
            Cursor filaspv = bd.rawQuery("select * from pivotes WHERE _id_Orden = \"" + filas.getString(1) + "\"" , null);

            while(filaspv.moveToNext()) {
                Cursor filasv = bd.rawQuery("select * from articulos WHERE id = " + Integer.parseInt(filaspv.getString(2)), null);
                Product product = new Product(Integer.parseInt(filasv.getString(0)), filasv.getString(1), filasv.getString(2), Double.parseDouble(filasv.getString(3)), Double.parseDouble(filasv.getString(4)), filasv.getString(5));

                productList.add(product);
            }

            int c = Integer.parseInt(filaspv.getString(2));
            cantidad.add(c);
*/

            Order order = new Order(Integer.parseInt(filas.getString(0)),filas.getString(1),filas.getDouble(2),productList,cantidad,Double.parseDouble((filas.getString(3))),filas.getString(4));
            orderList.add(order);
        }





        bd.close();

        return orderList;
    }

    //deben crearse inicialmente estos 2 metodos  onCreate(SQLiteDatabase db), onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    @Override
    public void onCreate(SQLiteDatabase db) {
        //el metodo  ejecuta una sentencia para crear la tabla con sus campos y tipos
        db.execSQL(tabla_client);

        db.execSQL(tabla_pivote);

        db.execSQL(tabla_ordenes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
