package innovatech.smartservices.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.activities.MainActivity;
import innovatech.smartservices.fragments.CuentaEditarServicio1Fragment;
import innovatech.smartservices.fragments.ServicioInformacionFragment;
import innovatech.smartservices.fragments.ServiciosDestacadosFragment;
import innovatech.smartservices.fragments.SolicitudServicioFragment;
import innovatech.smartservices.helpers.EstadoReserva;
import innovatech.smartservices.interfaces.OnItemClickListenerInterface;
import innovatech.smartservices.models.Reserva;
import innovatech.smartservices.models.Servicio;
import innovatech.smartservices.models.Usuario;

public class RecyclerViewNotificaciones extends RecyclerView.Adapter<RecyclerViewNotificaciones.MyViewHolder> {

    private Context mContext;
    private List<Usuario> usuarios;
    private List<Servicio> mData;
    private List<Reserva> reserva;

    public RecyclerViewNotificaciones(Context mContext , List <Servicio> mData, List<Usuario> usuarios, List<Reserva> reserva) {
        this.mContext = mContext;
        this.mData = mData;
        this.usuarios = usuarios;
        this.reserva = reserva;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_item_notificaciones,parent,false);
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder , int position) {
        if (reserva.get(position).getEstado().equals(EstadoReserva.ACEPTADO)){
            String notficiacion ="Servicio " + mData.get(position).getNombre() + " para el día " + reserva.get(position).getFecha() + " a las " + reserva.get(position).getHora()+":00" + " ACEPTADO";
            holder.tv_notificacion.setText(notficiacion);
        }else if(reserva.get(position).getEstado().equals(EstadoReserva.RECHAZADO)){
            String notficiacion ="Servicio " + mData.get(position).getNombre() + " para el día " + reserva.get(position).getFecha() + " a las " + reserva.get(position).getHora()+":00 " + " RECHAZADO";
            holder.tv_notificacion.setText(notficiacion);
        }else {
            holder.tv_notificacion.setText(usuarios.get(position).getNombre() + " solicito el servicio " + mData.get(position).getNombre());
        }

        holder.setItemClickListener(new OnItemClickListenerInterface() {
            @Override
            public void onClick(View view, int position) {
                MainActivity myActivity = (MainActivity)mContext;
                if (reserva.get(position).getEstado().equals(EstadoReserva.PENDIENTE)) {
                    //  Toast.makeText(mContext, "Elemento "+mData.get(position).getNombre(), Toast.LENGTH_SHORT).show();
                    FragmentTransaction ft = myActivity.getSupportFragmentManager().beginTransaction();
                    //   CuentaEditarServicio1Fragment pubDetallesEdit= new CuentaEditarServicio1Fragment();
                    SolicitudServicioFragment solicitudFragment = new SolicitudServicioFragment();
                    ft.replace(R.id.fragment_container, solicitudFragment);
                    ft.addToBackStack(null);
                    Bundle bundle = new Bundle();
                    bundle.putString("idServicio", mData.get(position).getId());
                    bundle.putString("idUsuario", usuarios.get(position).getId());
                    bundle.putString("reserva", reserva.get(position).getId());
                    solicitudFragment.setArguments(bundle);
                    ft.commit();
                }else{
                    if (reserva.get(position).getEstado().equals(EstadoReserva.ACEPTADO)){
                        String texto = "Servicio "+mData.get(position).getNombre()+ " fue aceptado para el día " + reserva.get(position).getFecha()+
                                " a la hora "+ reserva.get(position).getHora()+ ":00, incluye "+ mData.get(position).getIncluye();
                        String titulo = "Solicitud aceptada";
                        android.support.v7.app.AlertDialog builder = createSimpleDialog(myActivity, texto, titulo,reserva.get(position).getId());
                        builder.show();

                    }else{
                        String texto = "Servicio "+mData.get(position).getNombre() +" fue rechazado para el día " + reserva.get(position).getFecha()+
                                " a la hora "+ reserva.get(position).getHora()+ ":00, motivo: "+ reserva.get(position).getRazon();
                        String titulo = "Solicitud rechazada";
                        android.support.v7.app.AlertDialog builder = createSimpleDialog(myActivity, texto, titulo,reserva.get(position).getId());
                        builder.show();

                    }
                    FirebaseDatabase.getInstance().getReference("reservas").child(reserva.get(position).getId()).child("visto").setValue(true);
                    FragmentTransaction ft = myActivity.getSupportFragmentManager().beginTransaction();
                    ServiciosDestacadosFragment principal = new ServiciosDestacadosFragment();
                    ft.replace(R.id.fragment_container,principal);
                    ft.commit();
                }
            }
        });
    }
    public android.support.v7.app.AlertDialog createSimpleDialog(MainActivity myActivity, String texto, String titulo, String idRes) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(myActivity);

        builder.setTitle(titulo)
                .setMessage(texto)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        return builder.create();
    }

    @Override
    public int getItemCount() {
        return mData.size ();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_notificacion;
        private OnItemClickListenerInterface itemClickListener;

        public MyViewHolder(View itemView) {
            super ( itemView );
            tv_notificacion = (TextView) itemView.findViewById ( R.id.notificacion);
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

