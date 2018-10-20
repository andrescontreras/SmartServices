package innovatech.smartservices.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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

import java.util.ArrayList;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.adapters.ImageAdapter;
import innovatech.smartservices.models.Servicio;
import innovatech.smartservices.models.Usuario;

public class PruebaImagenFragment extends Fragment {
    RecyclerView recycler;
    private ImageAdapter mAdapter;
    FirebaseAuth mAuth ;
    List<Uri> uri = new ArrayList<Uri>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_prueba_imagen, container, false);
        mAuth = FirebaseAuth.getInstance();
        recycler = (RecyclerView)view.findViewById(R.id.recycler_prueba);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        uri = capturarInfoActual();
        Toast.makeText(getActivity(), "Uri en ADAPTER "+uri.size(), Toast.LENGTH_SHORT).show();

        return view;
    }
    private List<Uri> capturarInfoActual(){
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("servicios");
        db.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot!=null){
                        Servicio serv = snapshot.getValue(Servicio.class);
                        //int cedula = dataSnapshot.child("cedula").getValue(Integer.class);
                        //String nombre = dataSnapshot.child("nombre").getValue(String.class);
                        System.out.println("ESTO ES EL NOMBRE DEL SERVICIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO  "+ serv.getNombre());
                        System.out.println("ESTO ES EL TIPO DEL SERVICIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO  "+ serv.getTipo());
                        List<String> imagenes = serv.getFotos();
                        uri.add(Uri.parse(imagenes.get(0)));
                    }
                    else{
                        Toast.makeText(getActivity(), "Hubo un problema encontrando los servicios", Toast.LENGTH_SHORT).show();
                    }
                }
                recycler.setHasFixedSize(true);
                recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAdapter = new ImageAdapter(getActivity(),uri);
                recycler.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
        System.out.println("Esto es el tamaño de Uri ->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+uri.size());
        return uri;
    }
}
