package com.softwo.supermercapp.Adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.softwo.supermercapp.Entidades.Pedidos;
import com.softwo.supermercapp.R;
import com.softwo.supermercapp.ViewHolder.ViewHolderPedidos;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPedidos extends RecyclerView.Adapter<ViewHolderPedidos>{

    private List<Pedidos> pedidos;

    public AdaptadorPedidos() {
        this.pedidos = new ArrayList<>(  );
    }

    @NonNull
    @Override
    public ViewHolderPedidos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderPedidos( LayoutInflater.from(viewGroup.getContext())
                .inflate( R.layout.item_pedidos, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPedidos viewHolderPedidos, int i) {
        viewHolderPedidos.setData( pedidos.get( i ) );
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public void addAll(List<Pedidos> pedidos) {
        int initialSize = this.pedidos.size();
        this.pedidos.addAll(pedidos);
        notifyItemRangeInserted(initialSize, pedidos.size());
    }

    public long getLastItemId() {
        return pedidos.get(pedidos.size() - 1).get_Id();
    }
}
