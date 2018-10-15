package innovatech.smartservices.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import innovatech.smartservices.R;
import innovatech.smartservices.adapters.PubUbicacionAdapter;
import innovatech.smartservices.models.Ubicacion;

public class PubUbicacionFragment extends Fragment {

    ArrayList<Ubicacion> listDatos;
    RecyclerView recycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pub_ubicacion, container, false);

        recycler =  view.findViewById(R.id.recyclerUbicaciones);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        listDatos = new ArrayList<Ubicacion>();

        // llenar datos de prueba
        for(int i =0; i< 30 ; i++)
        {
            Ubicacion u = new Ubicacion("gpspos"+i*456, "ubi-"+i*123, "dir"+i*963 );
            listDatos.add(u);
        }

        PubUbicacionAdapter adapter = new PubUbicacionAdapter(listDatos);
        recycler.setAdapter(adapter);

        return view;
    }
}
