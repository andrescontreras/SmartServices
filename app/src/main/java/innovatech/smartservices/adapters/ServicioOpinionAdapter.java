package innovatech.smartservices.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.models.Opinion;

public class ServicioOpinionAdapter extends RecyclerView.Adapter<ServicioOpinionAdapter.MyViewHolder>{
    private Context mContext;
    private List<Opinion> mData;

    public ServicioOpinionAdapter(Context mContext , List <Opinion> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_servicio_opiniones,parent,false);
        return new MyViewHolder ( view );
    }


    @Override
    public void onBindViewHolder(ServicioOpinionAdapter.MyViewHolder holder , int position) {

        holder.usu_nombre.setText(mData.get(position).getUsuario());
        holder.servicio_comentario.setText(mData.get(position).getDescripcion());
        holder.fecha.setText(mData.get(position).getFecha());
        //Falta colocarle valor a las estrellitas
    }

    @Override
    public int getItemCount() {
        return mData.size ();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView usu_nombre;
        TextView servicio_comentario;
        RatingBar rating ;
        TextView fecha;

        public MyViewHolder(View itemView) {
            super ( itemView );
            usu_nombre = (TextView) itemView.findViewById ( R.id.it_opinion_usu);
            fecha = (TextView) itemView.findViewById ( R.id.it_opinion_fecha);
            servicio_comentario = (TextView) itemView.findViewById ( R.id.it_opinion_coment);
            rating = (RatingBar) itemView.findViewById(R.id.itemOpRating);
        }
    }
}
