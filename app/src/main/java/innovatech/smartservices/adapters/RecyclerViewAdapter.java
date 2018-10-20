package innovatech.smartservices.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.fragments.ServiciosDestacadosFragment;
import innovatech.smartservices.models.Servicio;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private ServiciosDestacadosFragment mContext;
    private List<Servicio> mData;

    public RecyclerViewAdapter(ServiciosDestacadosFragment nContext , List <Servicio> mData) {
        this.mContext = nContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
    /*
        View view;
        LayoutInflater mInflater = LayoutInflater.from ( mContext );
        view = mInflater.inflate ( R.layout.cardview_item_servicio,parent,false );
        return new MyViewHolder ( view );
        */
    return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder , int position) {

        holder.tv_servicio_title.setText(mData.get(position).getNombre());
        //holder.img_servicio_thumbnail.setImageResource ( mData.get(position).getThumbnail () );

    }

    @Override
    public int getItemCount() {
        return mData.size ();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_servicio_title;
        ImageView img_servicio_thumbnail;

        public MyViewHolder(View itemView) {
            super ( itemView );

            tv_servicio_title = (TextView) itemView.findViewById ( R.id.servicio_title_id );
            img_servicio_thumbnail = (ImageView) itemView.findViewById ( R.id.servicio_img_id );

        }
    }

}
