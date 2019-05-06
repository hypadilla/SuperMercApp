package com.softwo.supermercapp.Adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softwo.supermercapp.Entidades.Productos;
import com.softwo.supermercapp.R;
import com.softwo.supermercapp.ViewHolder.ViewHolderProducto;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorProductos extends RecyclerView.Adapter<ViewHolderProducto> {

    private List<Productos> productos;

    public AdaptadorProductos() {
        this.productos = new ArrayList<>(  );
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolderProducto onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
        return new ViewHolderProducto( LayoutInflater.from(parent.getContext())
                .inflate( R.layout.item_productos, parent, false));
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolderProducto holder, final int position) {
        holder.setData( productos.get( position ) );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productos.size();
    }

    public void addAll(List<Productos> newProductos) {
        int initialSize = productos.size();
        productos.addAll(newProductos);
        notifyItemRangeInserted(initialSize, newProductos.size());
    }

    public long getLastItemId() {
        return productos.get(productos.size() - 1).getId();
    }
}
