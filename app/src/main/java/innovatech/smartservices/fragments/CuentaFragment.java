package innovatech.smartservices.fragments;
import innovatech.smartservices.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class CuentaFragment extends Fragment {
    Button nueva_publi;
    Button admin_serv;
    Button edit_usu;
    Button serv_solic;
    Button buzon_p;
    Button ranking;
    // boton de prueba
    Button ubicacion;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cuenta, container, false);
        mAuth = FirebaseAuth.getInstance();
        inicializarBotones(view);
        accionBotones(view);

        return view;
        //return inflater.inflate(R.layout.fragment_cuenta,container,false);
        /*
        texto= (TextView)view.findViewById(R.id.textoPrueba1);
        boton = (Button)view.findViewById(R.id.boton_prueba1);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoxd = "CAMBIA EL TEXTO WOW !";
                texto.setText(textoxd);

     //TODO Para que se guardara el texto que tenia en un fragment anterior despues de ir a otro, coloque
    //TODO en la parte de xml, dentro del TextView el atributo freezesText=true
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                NotificacionesFragment notificacion = new NotificacionesFragment ();
                ft.replace(R.id.fragment_container, notificacion);
                ft.addToBackStack(null);
                //notificacion.setArguments(bundle);
                ft.commit();
            }
        });
        */
    }

    //METODO QUE CONTIENE TODAS LAS INICIALIZACION DE LOS BOTONES
    private void inicializarBotones(View view){
        nueva_publi = (Button)view.findViewById(R.id.btn_nueva_publicacion);
        admin_serv = (Button)view.findViewById(R.id.btn_admin_servicio);
        edit_usu = (Button)view.findViewById(R.id.btn_edit_usu);
        serv_solic = (Button)view.findViewById(R.id.btn_histo_servicios);
        buzon_p = (Button)view.findViewById(R.id.btn_buzon);
        ubicacion =  view.findViewById(R.id.ubicacion);
        ranking =  (Button)view.findViewById(R.id.btn_ranking);
    }
    //METODO QUE CONTIENE TODAS LAS ACCIONES DE LOS BOTONES
    private  void accionBotones(View view){

        nueva_publi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                PubCrearServicioFragment servicio = new PubCrearServicioFragment();
                ft.replace(R.id.fragment_container,servicio);
                ft.addToBackStack(null);
                //notificacion.setArguments(bundle);
                ft.commit();
            }
        });


        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                PubUbicacionFragment ubi = new PubUbicacionFragment();
                ft.replace(R.id.fragment_container,ubi);
                ft.addToBackStack(null);
                //notificacion.setArguments(bundle);
                ft.commit();
            }
        });



        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                CuentaRankingFragment rank = new CuentaRankingFragment();
                ft.replace(R.id.fragment_container,rank);
                ft.addToBackStack(null);
                //notificacion.setArguments(bundle);
                ft.commit();
            }
        });


        admin_serv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                CuentaAdministrarServiciosFragment editUsu = new CuentaAdministrarServiciosFragment();
                ft.replace(R.id.fragment_container, editUsu);
                ft.addToBackStack(null);
                //notificacion.setArguments(bundle);
                ft.commit();
            }
        });




        edit_usu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                EditarUsuarioFragment editUsu = new EditarUsuarioFragment();
                ft.replace(R.id.fragment_container, editUsu);
                ft.addToBackStack(null);
                //notificacion.setArguments(bundle);
                ft.commit();
            }
        });
        serv_solic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        buzon_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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

