package innovatech.smartservices.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.fragments.ServiciosDestacadosFragment;
import innovatech.smartservices.models.Servicio;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Servicio> mData;

    public RecyclerViewAdapter(Context mContext , List <Servicio> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_item_servicio,parent,false);
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder , int position) {

        holder.tv_servicio_title.setText(mData.get(position).getNombre());
        if((mData.get(position).getFotos().size()>0)){
            Picasso.with(mContext).load(Uri.parse(mData.get(position).getFotos().get(0))).into(holder.img_servicio);
        }

        holder.tv_servicio_precio.setText("$"+String.valueOf(mData.get(position).getPrecio()));

    }

    @Override
    public int getItemCount() {
        return mData.size ();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_servicio_title;
        ImageView img_servicio;
        TextView tv_servicio_precio;

        public MyViewHolder(View itemView) {
            super ( itemView );
            tv_servicio_title = (TextView) itemView.findViewById ( R.id.servicio_title_id );
            img_servicio = (ImageView) itemView.findViewById ( R.id.servicio_img_id );
            tv_servicio_precio = (TextView)itemView.findViewById(R.id.servicio_precio_id);
        }
    }
}
