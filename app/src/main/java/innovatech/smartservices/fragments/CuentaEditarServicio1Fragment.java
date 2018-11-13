package innovatech.smartservices.fragments;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.adapters.ImageAdapter;
import innovatech.smartservices.models.Servicio;

import static android.app.Activity.RESULT_OK;

public class CuentaEditarServicio1Fragment extends Fragment {
    Button eliminar;
    Button nombre;
    Button categorias;
    Button incluye;
    Button ubicacion;
    Button disponibilidad;
    Button posicionamiento;
    private TextView editando;
    List<Servicio> lstServicio =  new ArrayList<Servicio>();

    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 2;
    private static final int RESULT_LOAD_IMAGE = 1;
    private ProgressDialog nProgressDialog;
    private RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cuenta_editar_servicio_1, container, false);
        mAuth = FirebaseAuth.getInstance();
        Bundle bundle = getArguments();
        inicializarBotones(view,savedInstanceState);
        accionBotones(view);
        return view;
    }

    private void inicializarBotones(View view,Bundle savedInstanceState){
        Bundle bundle=getArguments();
        final String idServ=bundle.getString("idServicio");
        editando=(TextView)view.findViewById(R.id.textEditando);
        //eliminar= (Button)view.findViewById(R.id.btn_eliminar);
        categorias= (Button)view.findViewById(R.id.btn_categorias);
        nombre= (Button)view.findViewById(R.id.btn_nombre);
        incluye= (Button)view.findViewById(R.id.btn_incluye);
        ubicacion= (Button)view.findViewById(R.id.btn_ubicacion);
        disponibilidad= (Button)view.findViewById(R.id.btn_disponibilidad);
        posicionamiento= (Button)view.findViewById(R.id.btn_posicionamiento);
        //CARGA DEL NOMBRE--------------------------------------------------------------
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("servicios");
        db.addValueEventListener(new ValueEventListener() {
        boolean seguir=false;
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                if (snapshot != null) {
                    Servicio serv = snapshot.getValue(Servicio.class);
                    lstServicio.add(serv);
                    seguir = true;
                } else {
                    Toast.makeText(getActivity(), "Hubo un problema encontrando los servicios", Toast.LENGTH_SHORT).show();
                }
            }
            if(seguir){
                for(int i=0;i<lstServicio.size();i++){
                    if(lstServicio.get(i).getId()!=null && idServ!=null){
                        System.out.println("si entra al if de que no son nulos");
                        if(lstServicio.get(i).getId().equals(idServ)){
                            editando.setText("Estás editando: "+lstServicio.get(i).getNombre().toString());
                        }
                    }

                }
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            throw databaseError.toException();
        }
        });
    }

    private  void accionBotones(View view){
/*
        eliminar.setOnClickListener(new View.OnClickListener() {
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
*/
        categorias.setOnClickListener(new View.OnClickListener() {
            Bundle bundle=getArguments();
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                PubCrearServicioEditandoFragment bas = new PubCrearServicioEditandoFragment();
                ft.replace(R.id.fragment_container,bas);
                ft.addToBackStack(null);
                bas.setArguments(bundle);
                ft.commit();
            }
        });


        nombre.setOnClickListener(new View.OnClickListener() {
            Bundle bundle = getArguments();
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                PubInfoBasicaEditandoFragment bas = new PubInfoBasicaEditandoFragment();
                ft.replace(R.id.fragment_container,bas);
                ft.addToBackStack(null);
                bas.setArguments(bundle);
                ft.commit();
            }
        });


        incluye.setOnClickListener(new View.OnClickListener() {
            Bundle bundle = getArguments();
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                PubDetallesEditandoFragment bas = new PubDetallesEditandoFragment();
                ft.replace(R.id.fragment_container,bas);
                ft.addToBackStack(null);
                bas.setArguments(bundle);
                ft.commit();
            }
        });


        disponibilidad.setOnClickListener(new View.OnClickListener() {
            Bundle bundle=getArguments();
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                CalendarioEditandoFragment bas = new CalendarioEditandoFragment();
                ft.replace(R.id.fragment_container,bas);
                ft.addToBackStack(null);
                bas.setArguments(bundle);
                ft.commit();
            }
        });


        ubicacion.setOnClickListener(new View.OnClickListener() {
            Bundle bundle=getArguments();
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                PubUbicacionEditandoFragment bas = new PubUbicacionEditandoFragment();
                ft.replace(R.id.fragment_container,bas);
                ft.addToBackStack(null);
                bas.setArguments(bundle);
                ft.commit();
            }
        });


        posicionamiento.setOnClickListener(new View.OnClickListener() {
            Bundle bundle=getArguments();
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                PubPosicionamientoEditandoFragment bas = new PubPosicionamientoEditandoFragment();
                ft.replace(R.id.fragment_container,bas);
                ft.addToBackStack(null);
                bas.setArguments(bundle);
                ft.commit();
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

