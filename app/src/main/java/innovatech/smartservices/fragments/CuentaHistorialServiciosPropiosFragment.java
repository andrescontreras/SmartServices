package innovatech.smartservices.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.adapters.RecyclerViewAdministrarServiciosAdapter;
import innovatech.smartservices.adapters.RecyclerViewHistorialServiciosPropiosAdapter;
import innovatech.smartservices.adapters.RecyclerViewServiciosSolicitados;
import innovatech.smartservices.models.Reserva;
import innovatech.smartservices.models.Servicio;
import innovatech.smartservices.models.Usuario;

public class CuentaHistorialServiciosPropiosFragment extends Fragment {
    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private ProgressDialog nProgressDialog;
    List<Reserva> lstServicio =  new ArrayList<Reserva>();
    List<Reserva> lstReservas =  new ArrayList<Reserva>();
    List<Usuario> lstUsuarios = new ArrayList<Usuario>();
    List<Usuario> users = new ArrayList<Usuario>();
    RecyclerView myrv;
    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_historial_servicios_propios, container, false);
        mAuth = FirebaseAuth.getInstance();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        myrv = (RecyclerView) view.findViewById(R.id.recyclerview_id);
        myrv.setHasFixedSize(true);
        myrv.setLayoutManager ( new GridLayoutManager ( getActivity(),1 ) );
        agregarServicios(mAuth,getArguments());
        return view;
    }



    public void agregarServicios(final FirebaseAuth mAuth,Bundle bundle){
        FirebaseUser user = mAuth.getCurrentUser();
        lstServicio =  new ArrayList<Reserva>();
        lstReservas =  new ArrayList<Reserva>();
        lstUsuarios = new ArrayList<Usuario>();
        users = new ArrayList<Usuario>();


        final String idServ =bundle.getString("idServicio");
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("reservas");
        DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("users");
        db2.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot != null) {
                        Usuario user = snapshot.getValue(Usuario.class);
                        lstUsuarios.add(user);
                    } else {
                        Toast.makeText(getActivity(), "Hubo un problema encontrando usuarios", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
        db.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean seguir=false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot!=null){
                        Reserva serv = snapshot.getValue(Reserva.class);
                        lstServicio.add(serv);
                        seguir=true;
                    }
                    else{
                        Toast.makeText(getActivity(), "Hubo un problema encontrando los servicios", Toast.LENGTH_SHORT).show();
                    }
                }
                if(seguir) {
                    for (int i = 0; i < lstServicio.size(); i++) {
                        if (lstServicio.get(i).getIdServicio().equals(idServ)) {
                            lstReservas.add(lstServicio.get(i));
                            //System.out.println(" Reserva" + lstReservas.size());
                            for (Usuario item: lstUsuarios){
                                if (lstServicio.get(i).getIdUsuSolicitante().equals(item.getId())){
                                    users.add(item);
                                    break;
                                }
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
        System.out.println("SIZE " + lstReservas.size()+ " " +users.size());
        RecyclerViewHistorialServiciosPropiosAdapter adapter = new RecyclerViewHistorialServiciosPropiosAdapter(getActivity(),lstReservas,users);
        myrv.setHasFixedSize(true);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        myrv.setAdapter(adapter);


    }


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

