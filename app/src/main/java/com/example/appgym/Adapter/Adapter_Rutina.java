package com.example.appgym.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appgym.R;
import com.example.appgym.Tablas.Rutina;

import java.util.ArrayList;

public class Adapter_Rutina extends  RecyclerView.Adapter<Adapter_Rutina.ViewHolderDatos> {

    //Declarar Array
    public ArrayList<Rutina> listaRutinas;
    private ItemClickListener itemListener;

    public Adapter_Rutina(ArrayList<Rutina> listaRutinas, ItemClickListener itemListener){
        this.listaRutinas=listaRutinas;
        this.itemListener=itemListener;
    }

    @Override
    public Adapter_Rutina.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_rutina, null, false);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(Adapter_Rutina.ViewHolderDatos holder, int position) {
        holder.asignarDatos(listaRutinas.get(position));

        holder.itemView.setOnClickListener(view -> {
            itemListener.onItemClick(listaRutinas.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return listaRutinas.size();
    }

    // crear Interface para poder hacer click
    public interface ItemClickListener{
        void onItemClick(Rutina details);
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder{
        private TextView tvNombreRutina;

        public ViewHolderDatos( View itemView) {
            super(itemView);
            tvNombreRutina = itemView.findViewById(R.id.tvNomRutina);
        }
        public void asignarDatos(Rutina rutina) {

            tvNombreRutina.setText(rutina.getNombreS());
        }
    }
}
