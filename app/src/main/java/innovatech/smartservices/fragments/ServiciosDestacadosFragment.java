package innovatech.smartservices.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.models.Servicio;

import static android.support.v7.widget.LinearLayoutManager.*;

public class ServiciosDestacadosFragment extends Fragment {
    List<Servicio> lstServicio;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_destacados_servicio, container, false);

        lstServicio = new ArrayList <> ();
        //lstServicio.add(new Servicio(//Nombre Servicio, //Categoria Servicio, //Descripcion Servicio, //Imagen Servicio));
        //RecyclerView myrv = (RecyclerView) findViewById (R.id.recyclerview_id) ;
        //RecyclerViewAdapter myAdapter = new RecyclerViewAdapter (this,lstServicio);
        //myrv.setLayoutManager ( new GridLayoutManager ( this,3 ) );
        //myrv.setAdapter(myAdapter);
        return view;
    }

}
