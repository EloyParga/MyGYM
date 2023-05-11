package com.example.appgym.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.appgym.R;
import com.example.appgym.Tablas.Dia;

import java.util.ArrayList;

public class Adapter_Dia extends RecyclerView.Adapter<Adapter_Dia.ViewHolderDatos> {

    public ArrayList<Dia>listaDias;
    private ItemClickListener itemListener;
    private LinearLayout lyColor;

    public Adapter_Dia(ArrayList<Dia> listaDias, ItemClickListener itemListener) {
        this.listaDias = listaDias;
        this.itemListener = itemListener;
    }

    @Override
    public Adapter_Dia.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_dia, null, false);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder( Adapter_Dia.ViewHolderDatos holder, int position) {
        holder.asignarDatos(listaDias.get(position));

        holder.itemView.setOnClickListener(view -> {
            itemListener.onItemClick(listaDias.get(position));
        });
    }

    @Override
    public int getItemCount() {return listaDias.size();}

    // crear Interface para poder hacer click
    public interface ItemClickListener{
        void onItemClick(Dia details);
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder{
        private TextView tvNombreDia;
        private TextView tvGrupoMusc;

        public ViewHolderDatos( View itemView) {
            super(itemView);
            lyColor = itemView.findViewById(R.id.ly);
            tvNombreDia = itemView.findViewById(R.id.tvNombreDia);
            tvGrupoMusc = itemView.findViewById(R.id.tvGrupoMusc);
        }
        public void asignarDatos(Dia dia) {

            tvNombreDia.setText(dia.getNombreDia());
            tvGrupoMusc.setText(dia.getGrupoMuscular().toUpperCase());

            if(tvGrupoMusc.getText().toString().contains("Descanso".toUpperCase())){
                lyColor.setBackgroundColor(Color.argb(204, 70, 248, 69));
            }
            if(!tvGrupoMusc.getText().toString().contains("Descanso".toUpperCase())){
                lyColor.setBackgroundColor(Color.argb(255, 255, 255, 255));
            }
        }
    }
}
