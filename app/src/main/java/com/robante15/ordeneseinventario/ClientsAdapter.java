package com.robante15.ordeneseinventario;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ClientViewHolder>{
    private Context mCtx;
    private List<Client> clientList;

    public ClientsAdapter(Context mCtx, List<Client> clientList) {
        this.mCtx = mCtx;
        this.clientList = clientList;
    }


    public ClientsAdapter.ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.client_list, null);
        return new ClientsAdapter.ClientViewHolder(view);
    }


    public void onBindViewHolder(ClientsAdapter.ClientViewHolder holder, int position) {
        Client client = clientList.get(position);


        String nombre = client.getNombres();
        String apellidos = client.getApellidos();
        String Telefono = String.valueOf(client.getTelefono());

        holder.textViewNombre.setText("Nombre: "+nombre);
        holder.textViewApellido.setText(apellidos);
        holder.textViewTelefono.setText("Teléfono: "+Telefono);
        holder.textViewDUI.setText("DUI: " + String.valueOf(client.getDui()));
        holder.textViewEstado.setText("Estado: " + client.getEstado());
    }

    public int getItemCount() {
        return clientList.size();
    }

    class ClientViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNombre, textViewApellido, textViewTelefono, textViewDUI, textViewEstado;


        public ClientViewHolder(View itemView) {
            super(itemView);

            textViewNombre = itemView.findViewById(R.id.lbl_nombre);
            textViewApellido = itemView.findViewById(R.id.lbl_apellido);
            textViewTelefono = itemView.findViewById(R.id.lbl_telefono);
            textViewDUI = itemView.findViewById(R.id.lbl_dui);
            textViewEstado = itemView.findViewById(R.id.lbl_estado);

        }
    }
}
