package innovatech.smartservices.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.activities.MainActivity;
import innovatech.smartservices.fragments.CuentaEditarServicio1Fragment;
import innovatech.smartservices.fragments.PubDetallesEditandoFragment;
import innovatech.smartservices.fragments.ServicioInformacionEditandoFragment;
import innovatech.smartservices.fragments.ServicioInformacionFragment;
import innovatech.smartservices.interfaces.OnItemClickListenerInterface;
import innovatech.smartservices.models.Servicio;

public class RecyclerViewAdministrarServiciosAdapter extends RecyclerView.Adapter<RecyclerViewAdministrarServiciosAdapter.MyViewHolder> {

    private Context mContext;
    private List<Servicio> mData;

    public RecyclerViewAdministrarServiciosAdapter(Context mContext , List <Servicio> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_item_admin_servicio,parent,false);
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder , int position) {

        holder.tv_servicio_title.setText(mData.get(position).getNombre());
        holder.tv_servicio_precio.setText("$"+String.valueOf(mData.get(position).getPrecio()));
        holder.setItemClickListener(new OnItemClickListenerInterface() {
            @Override
            public void onClick(View view, int position) {
                MainActivity myActivity = (MainActivity)mContext;
                Toast.makeText(mContext, "Elemento "+mData.get(position).getNombre(), Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = myActivity.getSupportFragmentManager().beginTransaction();
                ServicioInformacionEditandoFragment servicioInfoEditando= new ServicioInformacionEditandoFragment();
                ft.replace(R.id.fragment_container, servicioInfoEditando);
                ft.addToBackStack(null);
                Bundle bundle = new Bundle();
                bundle.putString("idServicio", mData.get(position).getId());
                bundle.putString("nombreServicio",mData.get(position).getNombre());
                servicioInfoEditando.setArguments(bundle);
                ft.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size ();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_servicio_title;
        TextView tv_servicio_precio;
        private OnItemClickListenerInterface itemClickListener;

        public MyViewHolder(View itemView) {
            super ( itemView );
            tv_servicio_title = (TextView) itemView.findViewById ( R.id.servicio_title_id );
            tv_servicio_precio = (TextView)itemView.findViewById(R.id.servicio_precio_id);
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
