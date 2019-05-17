package com.robante15.ordeneseinventario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrdenesGeneral.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrdenesGeneral#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdenesGeneral extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OrdenesGeneral() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdenesGeneral.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdenesGeneral newInstance(String param1, String param2) {
        OrdenesGeneral fragment = new OrdenesGeneral();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    List<Order> OrderList;
    RecyclerView recyclerView;
    Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void sincronizar(View view) {
        Toast.makeText(getContext(),"Intentando conectar a Servidor "+MainActivity.IP_SERVERJS,Toast.LENGTH_LONG).show();
        loadOrdersToLocalDB();
    }


    private void loadOrdersToLocalDB() {
        /*
         * Crear  un String Request
         * El tipo de peticion es GET definido como primer parametro
         * La URL  es definida como primer parametro
         * Entonces tenemos  un Response Listener y un Error Listener
         * En el response listener obtenemos el  JSON como un String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.0.18:3800/order/listarOrdenes",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Toast.makeText(getContext(),"conectando ",Toast.LENGTH_LONG).show();
                            //convertir  el string a json array object
                            JSONObject object = new JSONObject(response);
                            OrderList = Arrays.asList(gson.fromJson(object.getString("orders"), Order[].class));

                            SQLiteHelperOrder sqLiteHelperOrder = new SQLiteHelperOrder(getActivity().getBaseContext(), "ordenes", null, 1);

                            //establece el metod para hacer que podamos escribir sobre la bd creada
                            SQLiteDatabase bd = sqLiteHelperOrder.getWritableDatabase();


                            ContentValues registro = new ContentValues();
                            ContentValues registrop = new ContentValues();
                            String cadena = OrderList.get(0).getCliente().get_id();


                            for(int i = 0; i < OrderList.size(); i++) {
                                boolean sinc = false;
                                registro.put("_id",OrderList.get(i).get_id());
                                registro.put("fecha", OrderList.get(i).getFecha());
                                registro.put("total", OrderList.get(i).getTotal());
                                registro.put("client","5cdd6245ea96442024b263e5");
                                registro.put("estado", "Sincronizado");

                          /*      for (int u = 0; u < OrderList.get(i).getProducts().size(); u++){
                                    registrop.put("_id_Orden", OrderList.get(i).get_id());
                                    registrop.put("_id_producto",OrderList.get(i).getProducts().get(u).getId());
                                    registro.put("cantidad",OrderList.get(i).getCantidades().get(u));
                                    bd.insert("pivote",null,registrop);
                                }


*/

                                Cursor filas = bd.rawQuery("select _id from ordenes", null);

                                while(filas.moveToNext()) {
                                    if(filas.getString(0) == OrderList.get(i).get_id()){
                                        sinc = true;
                                    }
                                }

                                if (!sinc){
                                    bd.insert("ordenes", null, registro);

                                }

                                cadena += OrderList.get(i).get_id() + " : " + OrderList.get(i).get_id() + "\n";

                                registro.clear();
                            }


                            //crear el  adaptador y asignarlo al  recyclerview
                            OrdersAdapter adapterc = new OrdersAdapter((getActivity().getBaseContext()), sqLiteHelperOrder.baseDatosLocal());
                           // recyclerView.setAdapter(adapterc);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(stringRequest);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_ordenes_general, container, false);

        recyclerView = vista.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        OrderList = new ArrayList<>();
        sincronizar(recyclerView);
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
