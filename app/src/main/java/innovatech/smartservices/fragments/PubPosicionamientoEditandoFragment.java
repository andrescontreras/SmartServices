package innovatech.smartservices.fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.Calendar;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.models.Servicio;
import innovatech.smartservices.models.Ubicacion;
import innovatech.smartservices.models.Usuario;

public class PubPosicionamientoEditandoFragment extends Fragment {
    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private ProgressDialog nProgressDialog;
    DatabaseReference mDataBase;
    List<String> imgUri = new ArrayList<String>();
    String referenciaUrl = "";
    Bundle bundle ;
    Servicio servicio = new Servicio();
    Usuario usr = new Usuario();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pub_posicionamiento_editando, container, false);
        mAuth = FirebaseAuth.getInstance();
        Button botonSI = (Button)view.findViewById(R.id.buttonSIPosicion);
        Button botonNO= (Button)view.findViewById(R.id.buttonNOPosicion);
        bundle=getArguments();
        System.out.println("el id es este "+bundle.getString("idServicio"));

        botonSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("servicios").child(bundle.getString("idServicio")).child("posicionamiento").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Servicio actualizado", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText( getActivity(),"Hubo un error al editar el servicio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        botonNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("servicios").child(bundle.getString("idServicio")).child("posicionamiento").setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Servicio actualizado", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText( getActivity(),"Hubo un error al editar el servicio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return  view;
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


