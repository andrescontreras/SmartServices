package innovatech.smartservices.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import innovatech.smartservices.R;
import innovatech.smartservices.adapters.PubUbicacionAdapter;
import innovatech.smartservices.models.Ubicacion;

import static android.app.Activity.RESULT_OK;

public class PubUbicacionEditandoFragment extends Fragment {

    private final static int PLACE_PICKER_REQUEST = 999;
    ArrayList<Ubicacion> listDatos;
    FirebaseAuth mAuth ;
    RecyclerView recycler;
    public Button agregar1;
    Button boton;
    PubUbicacionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pub_ubicacion_editando, container, false);
        mAuth = FirebaseAuth.getInstance();
        boton = (Button)view.findViewById(R.id.button4);
        adapter = new PubUbicacionAdapter(listDatos);
        recycler.setAdapter(adapter);
        //listDatos= view.findViewById(R.id.recyclerUbicaciones);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listDatos.isEmpty()){
                    Toast.makeText(getActivity(), "Debe ingresar almenos una ubicación", Toast.LENGTH_SHORT).show();
                }
                else {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    CalendarioFragment ubicacionfragm = new CalendarioFragment();
                    ft.replace(R.id.fragment_container, ubicacionfragm);
                    ft.addToBackStack(null);
                    Bundle bundle = getArguments();
                    System.out.println("los datos son "+listDatos.get(0).getDireccion());
                    bundle.putString("ubicacion", listDatos.toString());
                    System.out.println("lo que se guarda es XXXXXXXXXX-X-X-X-X-X-X-X  "+listDatos.toString());
                    ubicacionfragm.setArguments(bundle);
                    //notificacion.setArguments(bundle);
                    ft.commit();
                }
            }
        });

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
               String direccion = String.format("%s",place.getAddress());
               String nombre = String.format("%s",place.getName());
                System.out.println("---------------------Name:"+ nombre);
               System.out.println("---------------------Ess:"+ direccion);
               double latitud = place.getLatLng().latitude;
               double longitud = place.getLatLng().longitude;
                Ubicacion u = new Ubicacion( direccion, nombre, latitud, longitud);
                //listDatos.add(u);
                System.out.println("---------------------AAAAAA:"+ u.getDireccion());
                adapter.addItem(u);

            }
        }
    }


    
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){ //Cuando el usuario ya esta logeado, mandarlo a la actividad principal
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ServiciosDestacadosFragment servDest= new ServiciosDestacadosFragment();
            ft.replace(R.id.fragment_container, servDest);
            ft.commit();
        }
    }
}
