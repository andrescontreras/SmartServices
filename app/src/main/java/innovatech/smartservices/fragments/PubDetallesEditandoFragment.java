package innovatech.smartservices.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import innovatech.smartservices.models.Servicio;

public class PubDetallesEditandoFragment extends Fragment {
    private EditText incluyeEditando;
    private EditText noIncluyeEditando;
    private EditText adicional;
    Button boton;
    List<Servicio> lstServicio =  new ArrayList<Servicio>();
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pub_detalles_servicio_editando, container, false);
        mAuth = FirebaseAuth.getInstance();
        boton = (Button)view.findViewById(R.id.boton_detalles);
        incluyeEditando = (EditText)view.findViewById(R.id.t_Incluye);
        noIncluyeEditando = (EditText)view.findViewById(R.id.t_noIncluye);
        adicional= (EditText)view.findViewById(R.id.texto_adicional);
        cargarInfoAnterior(savedInstanceState,view,mAuth);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(incluyeEditando.getText()) || TextUtils.isEmpty(noIncluyeEditando.getText()) || TextUtils.isEmpty(adicional.getText())){
                    Toast.makeText(getActivity(), "Debe ingresar que incluyeEditando, que no incluyeEditando, y los datos adicionales del servicio", Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle bundle = getArguments();
                    System.out.println("lo actual  incluyeEditando es "+ incluyeEditando.getText().toString());
                    System.out.println("la info del bundle es "+bundle.getString("idServicio"));
                    FirebaseDatabase.getInstance().getReference("servicios").child(bundle.getString("idServicio")).child("incluye").setValue(incluyeEditando.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //progressbar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Servicio actualizado", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText( getActivity(),"Hubo un error al editar el servicio", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                    FirebaseDatabase.getInstance().getReference("servicios").child(bundle.getString("idServicio")).child("noIncluye").setValue(noIncluyeEditando.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //progressbar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Servicio actualizado", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText( getActivity(),"Hubo un error al editar el servicio", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
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
        db.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                incluyeEditando.setText(lstServicio.get(i).getIncluye().toString());
                                noIncluyeEditando.setText(lstServicio.get(i).getNoIncluye().toString());
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
