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

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>{
    private Context mCtx;
    private List<Order> orderList;

    public OrdersAdapter(Context mCtx, List<Order> orderList) {
        this.mCtx = mCtx;
        this.orderList = orderList;
    }

    @Override
    public OrdersAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.order_list, null);
        return new OrdersAdapter.OrderViewHolder(view);
    }


    public void onBindViewHolder(OrdersAdapter.OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        String fecha = Double.toString(order.getFecha());
        String total = Double.toString(order.getTotal());
        //String nProductos = Integer.toString(order.getProducts().size());

        holder.textViewID.setText(order.getId());
        holder.textViewFecha.setText(fecha);
       // holder.textViewNProd.setText(nProductos);
        holder.textViewTotal.setText("$" + total);
        holder.textViewEstado.setText("");
    }


    public int getItemCount() {
        return orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView textViewID, textViewFecha, textViewNProd, textViewTotal,textViewEstado;


        public OrderViewHolder(View itemView) {
            super(itemView);

            textViewID = itemView.findViewById(R.id.lbl_id);
            textViewFecha = itemView.findViewById(R.id.lbl_fecha);
            textViewNProd = itemView.findViewById(R.id.lbl_productos);
            textViewTotal = itemView.findViewById(R.id.lbl_total);
            textViewEstado = itemView.findViewById(R.id.lbl_estado);
        }
    }
}
