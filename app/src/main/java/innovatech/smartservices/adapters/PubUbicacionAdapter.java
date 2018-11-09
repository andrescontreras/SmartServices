package innovatech.smartservices.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.models.Ubicacion;

public class PubUbicacionAdapter  extends RecyclerView.Adapter<PubUbicacionAdapter.ViewHolderDatos> {

    List<Ubicacion> listDatos;

    public PubUbicacionAdapter(List<Ubicacion> listDatos) {
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

    public void addItem(Ubicacion u) {
        listDatos.add(0, u);
        notifyItemInserted(0);
    }

    public class ViewHolderDatos  extends RecyclerView.ViewHolder{

        TextView ubicacion;
        TextView nombre;
        public ViewHolderDatos(View itemView) {
            super(itemView);
            ubicacion = itemView.findViewById(R.id.TV_Ubicacion);
            nombre = itemView.findViewById(R.id.TV_Nombre);

        }

        public void asignarDatos(Ubicacion s) {
            System.out.println("-----------------ASIGNAR: "+s.getDireccion());
            ubicacion.setText(s.getDireccion());
            nombre.setText(s.getNombre());
        }


    }

   /* public class itemUbicacion{
        private String Ubicacion;
        private String Direccion;
    }*/
}
