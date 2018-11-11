package innovatech.smartservices.fragments;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.adapters.ImageAdapter;
import innovatech.smartservices.adapters.RecyclerViewAdministrarServiciosAdapter;
import innovatech.smartservices.adapters.RecyclerViewNotificaciones;
import innovatech.smartservices.helpers.EstadoReserva;
import innovatech.smartservices.models.Reserva;
import innovatech.smartservices.models.Servicio;
import innovatech.smartservices.models.Usuario;

import static android.app.Activity.RESULT_OK;

public class NotificacionesFragment extends Fragment {
    private static final int RESULT_LOAD_IMAGE = 1;
    private ProgressDialog nProgressDialog;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;
    List<Servicio> lstServicio =  new ArrayList<Servicio>();
    List<Servicio> lstServiciosPropios=  new ArrayList<Servicio>();
    List<Usuario> lstUsuarios =  new ArrayList<Usuario>();
    List<Reserva> lstReservas = new ArrayList<Reserva>();
    RecyclerView myrv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_notificaciones, container, false);
        mAuth = FirebaseAuth.getInstance();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        myrv = (RecyclerView) view.findViewById(R.id.recyclerview_id);
        myrv.setHasFixedSize(true);
        myrv.setLayoutManager ( new GridLayoutManager( getActivity(),1 ) );
        agregarNotificaciones(mAuth);
        return view;
    }
    public void agregarNotificaciones(final FirebaseAuth mAuth){
        lstServicio = new ArrayList<>();
        lstServiciosPropios=new ArrayList<>();
        lstUsuarios = new ArrayList<>();
        lstReservas = new ArrayList<>();

        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("servicios");
        DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("users");
        DatabaseReference db3 = FirebaseDatabase.getInstance().getReference().child("reservas");
        db.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean seguir = false;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot != null) {
                        Servicio serv = snapshot.getValue(Servicio.class);
                        lstServicio.add(serv);
                        seguir = true;
                    } else {
                        Toast.makeText(getActivity(), "Hubo un problema encontrando los servicios", Toast.LENGTH_SHORT).show();
                    }
                }
                if (seguir) {
                    for (int i = 0; i < lstServicio.size(); i++) {
                        if (lstServicio.get(i).getIdUsuario() != null && mAuth.getCurrentUser().getUid() != null) {
                            if (lstServicio.get(i).getIdUsuario().equals(mAuth.getCurrentUser().getUid())) {
                                System.out.println("se hizo match " + lstServicio.get(i).getIdUsuario());
                                lstServiciosPropios.add(lstServicio.get(i));
                            }
                        }
                    }
                }

               /* RecyclerViewNotificaciones myAdapter = new RecyclerViewNotificaciones(getActivity(), lstServiciosPropios,lstUsuarios);
                myrv.setHasFixedSize(true);
                myrv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                myrv.setAdapter(myAdapter);*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
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
        db3.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Servicio> Servicios=  new ArrayList<Servicio>();
                List<Usuario> Usuarios =  new ArrayList<Usuario>();
                List<Reserva> MisReservas = new ArrayList<Reserva>();
                Usuario myUser = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot != null) {
                        Reserva res = snapshot.getValue(Reserva.class);
                        lstReservas.add(res);
                    } else {
                        Toast.makeText(getActivity(), "Hubo un problema encontrando reservas", Toast.LENGTH_SHORT).show();
                    }
                }
                for (Reserva item:lstReservas){
                    Servicio servicio1 = null;
                    if (item.getEstado().equals(EstadoReserva.PENDIENTE)) {
                        Usuario user1 = null;
                        for (Servicio services : lstServiciosPropios) {
                            if (item.getIdServicio().equals(services.getId())) {
                                servicio1 = services;
                                break;
                            }
                        }
                        for (Usuario u : lstUsuarios) {
                            if (u.getId().equals(item.getIdUsuSolicitante())) {
                                user1 = u;
                                break;
                            }
                            if(u.getId().equals(mAuth.getCurrentUser().getUid()) && myUser == null){
                                myUser = u;
                            }
                        }
                        if (servicio1 != null && user1 != null) {
                            Servicios.add(servicio1);
                            Usuarios.add(user1);
                            MisReservas.add(item);
                        }
                    }else if(item.getEstado().equals(EstadoReserva.ACEPTADO)||item.getEstado().equals(EstadoReserva.RECHAZADO)){

                        if (item.getIdUsuSolicitante().equals(mAuth.getCurrentUser().getUid()) && !item.isVisto()) {
                           // System.out.println("ACEPTADO");
                            for (Servicio services : lstServicio) {
                                if (item.getIdServicio().equals(services.getId())) {
                                    servicio1 = services;
                                    break;
                                }
                            }
                            if(myUser==null){
                                for (Usuario u : lstUsuarios) {
                                    if(u.getId().equals(mAuth.getCurrentUser().getUid()) && myUser == null){
                                        myUser = u;
                                        break;
                                    }
                                }

                            }
                            if (servicio1 != null && myUser != null) {
                                //System.out.println("GUARDADO");
                                Servicios.add(servicio1);
                                Usuarios.add(myUser);
                                MisReservas.add(item);
                            }
                        }
                    }
                }

                RecyclerViewNotificaciones myAdapter = new RecyclerViewNotificaciones(getActivity(), Servicios,Usuarios,MisReservas);
                myrv.setHasFixedSize(true);
                myrv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                myrv.setAdapter(myAdapter);


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

    }

}
