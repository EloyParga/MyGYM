package com.example.appgym.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appgym.R;
import com.example.appgym.Tablas.Ejercicio;

import java.util.ArrayList;

public class Adapter_Ejercicio extends RecyclerView.Adapter<Adapter_Ejercicio.ViewHolderDatos> {

    /**
     * Esta clase es el Adapter que expecifica como van a funcionar los Items dentro del RecyclerView
     * en la activity lista_ejercicios_dia.xml
     */

    public ArrayList<Ejercicio> listaEj;
    private ItemClickListener itemListener;

    public Adapter_Ejercicio(ArrayList<Ejercicio> listaEj, ItemClickListener itemListener) {
        this.listaEj = listaEj;
        this.itemListener = itemListener;
    }

    public Adapter_Ejercicio.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_ejercicios, null, false);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(Adapter_Ejercicio.ViewHolderDatos holder, int position) {
        holder.asignarDatos(listaEj.get(position));

        holder.itemView.setOnClickListener(view -> {
            itemListener.onItemClick(listaEj.get(position));
        });
    }

    @Override
    public int getItemCount() {return listaEj.size();}

    public interface ItemClickListener{
        void onItemClick(Ejercicio details);
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder{
        private TextView tvNombreEj;
        private TextView tvReps;
        private TextView tvSeries;

        public ViewHolderDatos( View itemView) {
            super(itemView);
            tvNombreEj = itemView.findViewById(R.id.tvNombreEj);
            tvSeries = itemView.findViewById(R.id.tvSeries);
            tvReps = itemView.findViewById(R.id.tvReps);
        }
        public void asignarDatos(Ejercicio ejercicio) {

            tvNombreEj.setText(ejercicio.getNombreEj());
            tvSeries.setText(Integer.toString(ejercicio.getSeries()));
            tvReps.setText(Integer.toString(ejercicio.getReps()));
        }
    }
}
