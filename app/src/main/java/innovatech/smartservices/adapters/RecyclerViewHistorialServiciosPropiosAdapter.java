package innovatech.smartservices.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.activities.MainActivity;
import innovatech.smartservices.fragments.ServicioInformacionEditandoFragment;
import innovatech.smartservices.interfaces.OnItemClickListenerInterface;
import innovatech.smartservices.models.Reserva;
import innovatech.smartservices.models.Servicio;
import innovatech.smartservices.models.Usuario;

public class RecyclerViewHistorialServiciosPropiosAdapter extends RecyclerView.Adapter<RecyclerViewHistorialServiciosPropiosAdapter.MyViewHolder> {

    private Context mContext;
    private List<Reserva> mData;
    private List<Usuario> usuarios;

    public RecyclerViewHistorialServiciosPropiosAdapter(Context mContext , List <Reserva> mData, List<Usuario> user) {
        this.mContext = mContext;
        this.mData = mData;
        this.usuarios=user;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_item_historial,parent,false);
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder , int position) {

        holder.fecha.setText(mData.get(position).getFecha());
        holder.usuario.setText(usuarios.get(position).getNombre());
        holder.estado.setText(mData.get(position).getEstado().toString());

    }

    @Override
    public int getItemCount() {
        return mData.size ();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView fecha;
        TextView usuario;
        TextView estado;


        private OnItemClickListenerInterface itemClickListener;

        public MyViewHolder(View itemView) {
            super ( itemView );
            fecha = (TextView) itemView.findViewById ( R.id.textViewFecha);
            estado = (TextView)itemView.findViewById(R.id.textViewEstado);
            usuario = (TextView) itemView.findViewById ( R.id.textViewUsuario);
            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(OnItemClickListenerInterface itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition());
        }
    }
}
