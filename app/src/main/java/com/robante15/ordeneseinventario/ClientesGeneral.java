package com.robante15.ordeneseinventario;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

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
 * {@link ClientesGeneral.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientesGeneral#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientesGeneral extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ClientesGeneral() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientesGeneral.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientesGeneral newInstance(String param1, String param2) {
        ClientesGeneral fragment = new ClientesGeneral();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    List<Client> clientList;
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


    public void sincronizar() {
        Toast.makeText(getContext(),"Intentando conectar a Servidor "+MainActivity.IP_SERVERJS,Toast.LENGTH_LONG).show();
        loadClientsToLocalDB();
    }

    private void loadClientsToLocalDB() {
        /*
         * Crear  un String Request
         * El tipo de peticion es GET definido como primer parametro
         * La URL  es definida como primer parametro
         * Entonces tenemos  un Response Listener y un Error Listener
         * En el response listener obtenemos el  JSON como un String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.0.18:3800/client/listarClientes",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                           // Toast.makeText(getContext(),"conectando ",Toast.LENGTH_LONG).show();
                            //convertir  el string a json array object
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("clientes");
                            clientList = Arrays.asList(gson.fromJson(object.getString("clientes"), Client[].class));

                            SQLiteHelperClient sQLiteHelperClient = new SQLiteHelperClient(getActivity().getBaseContext(), "clientes", null, 1);
                            //establece el metod para hacer que podamos escribir sobre la bd creada
                            SQLiteDatabase bd = sQLiteHelperClient.getWritableDatabase();

                            ContentValues registro = new ContentValues();
                            String cadena = "";


                            for(int i = 0; i < clientList.size(); i++) {
                                boolean sinc = false;
                                registro.put("_id",clientList.get(i).get_id());
                                registro.put("nombres", clientList.get(i).getNombres());
                                registro.put("apellidos", clientList.get(i).getApellidos());
                                registro.put("telefono", clientList.get(i).getTelefono());
                                registro.put("email", clientList.get(i).getEmail());
                                registro.put("dui", clientList.get(i).getDui());
                                registro.put("nit", clientList.get(i).getNit());
                                registro.put("estado", "Sincronizado");

                                Cursor filas = bd.rawQuery("select _id from clientes", null);

                                while(filas.moveToNext()) {
                                    if(filas.getString(0) == clientList.get(i).get_id()){
                                        sinc = true;
                                    }
                                }

                                if (!sinc){
                                    bd.insert("clientes", null, registro);
                                }

                                cadena += clientList.get(i).get_id() + " : " + clientList.get(i).getNombres() + "\n";

                                registro.clear();
                            }


                            //crear el  adaptador y asignarlo al  recyclerview
                            ClientsAdapter adapterc = new ClientsAdapter(getActivity().getBaseContext(), sQLiteHelperClient.baseDatosLocal());
                            recyclerView.setAdapter(adapterc);
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
        View vista = inflater.inflate(R.layout.fragment_clientes_general, container, false);

        recyclerView = vista.findViewById(R.id.recyclerViewC);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        clientList = new ArrayList<>();
        sincronizar();


        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/
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
