package innovatech.smartservices.fragments;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import innovatech.smartservices.adapters.RecyclerViewAdministrarServiciosAdapter;
import innovatech.smartservices.models.Servicio;

public class PubDetallesEditandoFragment extends Fragment {
    private EditText incluye;
    private EditText noIncluye;
    private EditText adicional;
    Button boton;
    List<Servicio> lstServicio =  new ArrayList<Servicio>();
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pub_detalles_servicio_editando, container, false);
        mAuth = FirebaseAuth.getInstance();
        boton = (Button)view.findViewById(R.id.boton_detalles);
        incluye = (EditText)view.findViewById(R.id.texto_incluye);
        noIncluye= (EditText)view.findViewById(R.id.texto_no_incluye);
        adicional= (EditText)view.findViewById(R.id.texto_adicional);
        cargarInfoAnterior(savedInstanceState,view,mAuth);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(incluye.getText()) || TextUtils.isEmpty(noIncluye.getText()) || TextUtils.isEmpty(adicional.getText())){
                    Toast.makeText(getActivity(), "Debe ingresar que incluye, que no incluye, y los datos adicionales del servicio", Toast.LENGTH_SHORT).show();
                }
                else {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    PubUbicacionFragment ubicacionfragm = new PubUbicacionFragment();
                    ft.replace(R.id.fragment_container, ubicacionfragm);
                    ft.addToBackStack(null);
                    Bundle bundle = getArguments();
                    bundle.putString("incluye", incluye.getText().toString());
                    bundle.putString("noIncluye", noIncluye.getText().toString());
                    bundle.putString("adicional", adicional.getText().toString());
                    ubicacionfragm.setArguments(bundle);
                    ft.commit();
                }
            }
        });
        return view;
    }

    public void cargarInfoAnterior(Bundle savedInstanceState, View view, FirebaseAuth mAuth){
        lstServicio = new ArrayList<>();
        System.out.println("si llega");
        Bundle bundle=getArguments();
        final String idServ=bundle.getString("idServicio");

        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("servicios");
        db.addValueEventListener(new ValueEventListener() {
            boolean seguir = false;
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
                    System.out.println("si entra a seguir");
                    for(int i=0;i<lstServicio.size();i++){
                        if(lstServicio.get(i).getId()!=null && idServ!=null){
                            System.out.println("si entra al if de que no son nulos");
                            if(lstServicio.get(i).getId().equals(idServ)){
                                incluye.setText(lstServicio.get(i).getIncluye().toString());
                                noIncluye.setText(lstServicio.get(i).getNoIncluye().toString());
                                adicional.setText(lstServicio.get(i).getAdicional().toString());
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
