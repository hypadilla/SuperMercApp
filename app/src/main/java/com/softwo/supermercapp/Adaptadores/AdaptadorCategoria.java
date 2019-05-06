package com.softwo.supermercapp.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.softwo.supermercapp.Entidades.Categorias;
import com.softwo.supermercapp.R;
import com.softwo.supermercapp.ViewHolder.ViewHolderCategoria;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorCategoria extends RecyclerView.Adapter<ViewHolderCategoria> {

    private List<Categorias> categorias;

    public AdaptadorCategoria( ) {
        this.categorias = new ArrayList<>(  );
    }

    @NonNull
    @Override
    public ViewHolderCategoria onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderCategoria( LayoutInflater.from(viewGroup.getContext())
                .inflate( R.layout.item_categoria, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategoria viewHolderCategoria, int i) {
        viewHolderCategoria.setData( categorias.get( i ) );
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public void addAll(List<Categorias> newCategorias) {
        int initialSize = categorias.size();
        categorias.addAll(newCategorias);
        notifyItemRangeInserted(initialSize, newCategorias.size());
    }

    public long getLastItemId() {
        return categorias.get(categorias.size() - 1).getId();
    }
}
