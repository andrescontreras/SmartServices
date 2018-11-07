package innovatech.smartservices.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.activities.MainActivity;
import innovatech.smartservices.fragments.ServicioInformacionFragment;
import innovatech.smartservices.interfaces.OnItemClickListenerInterface;
import innovatech.smartservices.models.Reserva;
import innovatech.smartservices.models.Servicio;
import innovatech.smartservices.models.Usuario;

public class RecyclerViewNotificaciones extends RecyclerView.Adapter<RecyclerViewNotificaciones.MyViewHolder> {

    private Context mContext;
    private List<Usuario> usuarios;
    private List<Servicio> mData;
   // private List<Reserva> reservas;

    public RecyclerViewNotificaciones(Context mContext , List <Servicio> mData, List<Usuario> usuarios) {
        this.mContext = mContext;
        this.mData = mData;
        this.usuarios = usuarios;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_item_notificaciones,parent,false);
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder , int position) {


        holder.tv_notificacion.setText("Usuario "+usuarios.get(position).getNombre()+" pidio el servicio "+ mData.get(position).getNombre());

    }

    @Override
    public int getItemCount() {
        return mData.size ();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_notificacion;
        public MyViewHolder(View itemView) {
            super ( itemView );
            tv_notificacion = (TextView) itemView.findViewById ( R.id.notificacion);

        }
    }
}

