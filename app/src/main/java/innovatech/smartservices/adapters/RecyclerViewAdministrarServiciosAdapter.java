package innovatech.smartservices.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import innovatech.smartservices.R;
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

    }

    @Override
    public int getItemCount() {
        return mData.size ();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_servicio_title;
        TextView tv_servicio_precio;

        public MyViewHolder(View itemView) {
            super ( itemView );
            tv_servicio_title = (TextView) itemView.findViewById ( R.id.servicio_title_id );
            tv_servicio_precio = (TextView)itemView.findViewById(R.id.servicio_precio_id);
        }
    }
}
