package innovatech.smartservices.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.activities.MainActivity;
import innovatech.smartservices.fragments.InformacionServicioFragment;
import innovatech.smartservices.fragments.ServiciosDestacadosFragment;
import innovatech.smartservices.fragments.SolicitudServicioFragment;
import innovatech.smartservices.helpers.EstadoReserva;
import innovatech.smartservices.interfaces.OnItemClickListenerInterface;
import innovatech.smartservices.models.Reserva;
import innovatech.smartservices.models.Servicio;
import innovatech.smartservices.models.Usuario;

public class RecyclerViewServiciosSolicitados extends RecyclerView.Adapter<RecyclerViewServiciosSolicitados.MyViewHolder> {

    private Context mContext;
    private List<Usuario> usuarios;
    private List<Servicio> mData;
    private List<Reserva> reserva;

    public RecyclerViewServiciosSolicitados(Context mContext , List <Servicio> mData, List<Usuario> usuarios, List<Reserva> reserva) {
        this.mContext = mContext;
        this.mData = mData;
        this.usuarios = usuarios;
        this.reserva = reserva;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_item_servicio_solicitado,parent,false);
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder , int position) {
        if (reserva.get(position).getEstado().equals(EstadoReserva.ACEPTADO)){
            String notficiacion ="Servicio " + mData.get(position).getNombre() + " \n Día " + reserva.get(position).getFecha() + " Hora: " + reserva.get(position).getHora()+":00" + "\n ACEPTADO";
            holder.tv_notificacion.setText(notficiacion);
        }else if(reserva.get(position).getEstado().equals(EstadoReserva.RECHAZADO)){
            String notficiacion ="Servicio " + mData.get(position).getNombre() + " \n Día " + reserva.get(position).getFecha() + " Hora: " + reserva.get(position).getHora()+":00 " + "\n RECHAZADO";
            holder.tv_notificacion.setText(notficiacion);
        }
        holder.setItemClickListener(new OnItemClickListenerInterface() {
            @Override
            public void onClick(View view, int position) {
                MainActivity myActivity = (MainActivity)mContext;

                FirebaseDatabase.getInstance().getReference("reservas").child(reserva.get(position).getId()).child("visto").setValue(true);
                Bundle bundle = new Bundle();
                bundle.putString("idServicio", mData.get(position).getId());
                bundle.putString("idUsuario", mData.get(position).getIdUsuario());
                bundle.putString("reserva", reserva.get(position).getId());
                FragmentTransaction ft = myActivity.getSupportFragmentManager().beginTransaction();
                InformacionServicioFragment principal = new InformacionServicioFragment();
                principal.setArguments(bundle);
                ft.replace(R.id.fragment_container,principal);
                ft.commit();

            }
        });
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
            tv_notificacion = (TextView) itemView.findViewById ( R.id.textView_serv_sol);
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

