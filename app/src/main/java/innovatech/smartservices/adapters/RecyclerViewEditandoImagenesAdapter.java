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
import innovatech.smartservices.models.Servicio;

public class RecyclerViewEditandoImagenesAdapter extends RecyclerView.Adapter<RecyclerViewEditandoImagenesAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<Servicio> mData;
    private List<Servicio> mDataFull;

    public RecyclerViewEditandoImagenesAdapter(Context mContext , List <Servicio> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mDataFull = new ArrayList<Servicio>(mData);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_editando_fotos_servicio,parent,false);
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder , final int position) {

        if((mData.get(position).getFotos().size()>0)){
            Picasso.with(mContext).load(Uri.parse(mData.get(position).getFotos().get(0))).into(holder.img_servicio);
        }
        holder.setItemClickListener(new OnItemClickListenerInterface() {
            @Override
            public void onClick(View view, int position) {
                MainActivity myActivity = (MainActivity)mContext;
                Toast.makeText(mContext, "Elemento "+mData.get(position).getNombre(), Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = myActivity.getSupportFragmentManager().beginTransaction();
                ServicioInformacionFragment infoServFragm = new ServicioInformacionFragment();
                ft.replace(R.id.fragment_container, infoServFragm);
                ft.addToBackStack(null);
                Bundle bundle = new Bundle();
                bundle.putString("idServicio", mData.get(position).getId());
                infoServFragm.setArguments(bundle);
                //notificacion.setArguments(bundle);
                ft.commit();
            }
        });
        holder.img_servicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("tocando la "+position);
                mData.remove(position);
                notifyItemRemoved(position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                System.out.println("current es "+position);
                mData.remove(position);
                notifyItemRemoved(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size ();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageButton img_servicio;
        private OnItemClickListenerInterface itemClickListener;

        public MyViewHolder(View itemView) {
            super ( itemView );
            img_servicio = (ImageButton) itemView.findViewById ( R.id.servicio_img_id );
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
    /*
    public void setFilter(ArrayList<Servicio> listaServicios){
        this.mData = new ArrayList<>();
        this.mData = listaServicios;
        notifyDataSetChanged();
    }
    */

    @Override
    public Filter getFilter() {
        return serviceFilter;
    }
    private Filter serviceFilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Servicio>filteredResults = new ArrayList<Servicio>();
            if(constraint == null || constraint.length() == 0){
                filteredResults.addAll(mDataFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Servicio servicio : mDataFull){
                    if(servicio.getNombre().toLowerCase().contains(filterPattern)){
                        filteredResults.add(servicio);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredResults;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mData.clear();
            mData.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };
}
