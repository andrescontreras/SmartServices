package innovatech.smartservices.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import innovatech.smartservices.R;
import innovatech.smartservices.models.Ubicacion;

public class PubUbicacionAdapter  extends RecyclerView.Adapter<PubUbicacionAdapter.ViewHolderDatos> {

    ArrayList<Ubicacion> listDatos;

    public PubUbicacionAdapter(ArrayList<Ubicacion> listDatos) {
        this.listDatos = listDatos;
    }

    @Override
    public PubUbicacionAdapter.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pub_ubicacion,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(PubUbicacionAdapter.ViewHolderDatos holder, int position) {
        holder.asignarDatos(listDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos  extends RecyclerView.ViewHolder{

        TextView ubicacion;
        TextView direccion;
        public ViewHolderDatos(View itemView) {
            super(itemView);
            ubicacion = itemView.findViewById(R.id.TV_Ubicacion);
            direccion = itemView.findViewById(R.id.TV_Direccion);
        }

        public void asignarDatos(Ubicacion s) {
            ubicacion.setText(s.getUbicacion());
            direccion.setText(s.getDireccion());
        }
    }

    public class itemUbicacion{
        private String Ubicacion;
        private String Direccion;
    }
}
