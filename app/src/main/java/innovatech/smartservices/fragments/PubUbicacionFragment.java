package innovatech.smartservices.fragments;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;

import innovatech.smartservices.R;
import innovatech.smartservices.adapters.PubUbicacionAdapter;
import innovatech.smartservices.models.Ubicacion;

import static android.app.Activity.RESULT_OK;

public class PubUbicacionFragment extends Fragment {

    private final static int PLACE_PICKER_REQUEST = 999;
    ArrayList<Ubicacion> listDatos;
    RecyclerView recycler;
    public Button agregar1;

    PubUbicacionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pub_ubicacion, container, false);


        agregar1 = view.findViewById(R.id.agregar);
        recycler =  view.findViewById(R.id.recyclerUbicaciones);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        listDatos = new ArrayList<Ubicacion>();

        // llenar datos de prueba
        /*for(int i =0; i< 30 ; i++)
        {
            Ubicacion u = new Ubicacion("gpspos"+i*456, "ubi-"+i*123, "dir"+i*963 );
            listDatos.add(u);
        }*/
        adapter = new PubUbicacionAdapter(listDatos);
        recycler.setAdapter(adapter);
        accionBotones(view);
        return view;


    }

    private  void accionBotones(View view){
        agregar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(getActivity());
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void onActivityResult(int requestCode, int ResultCode, Intent data){

        if (requestCode == PLACE_PICKER_REQUEST)
        {
            if(ResultCode == RESULT_OK){
               Place place = PlacePicker.getPlace(data,getActivity());
               String direccion = String.format("Place: %s",place.getAddress());
               String ubi = place.getName().toString();
                Ubicacion u = new Ubicacion("NUEVO", direccion, "DIR-"+ubi );
                //listDatos.add(u);
                adapter.addItem(u);

            }
        }
    }
}
