package innovatech.smartservices.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import innovatech.smartservices.R;
import innovatech.smartservices.models.Servicio;
import innovatech.smartservices.models.Ubicacion;

public class PubPosicionamientoFragment extends Fragment {
    private FirebaseAuth mAuth;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pub_posicionamiento, container, false);
        mAuth = FirebaseAuth.getInstance();
        Button botonSI = (Button)view.findViewById(R.id.buttonSIPosicion);
        Button botonNO= (Button)view.findViewById(R.id.buttonNOPosicion);
        botonSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                guardarEnFirebase(true);
                //notificacion.setArguments(bundle);
                ft.commit();

            }
        });
        botonNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                guardarEnFirebase(false);
                //notificacion.setArguments(bundle);
                ft.commit();

            }
        });

        return  view;
    }

    public void guardarEnFirebase(Boolean posicion){
        Servicio servicio=new Servicio();
        Bundle bundle=getArguments();

        servicio.setIncluye(bundle.getString("incluye"));
        servicio.setNoIncluye(bundle.getString("noIncluye"));
        servicio.setAdicional(bundle.getString("adicional"));

        servicio.setTipo(bundle.getString("categorias"));

        servicio.setNombre(bundle.getString("nombre"));
        servicio.setPrecio(bundle.getInt("precio"));


        System.out.println("ubicacion ---------- "+(bundle.getString("ubicacion")));



        if(bundle.getBoolean("lunes")){
            System.out.println("si llega");
            servicio.addDias(1);
        }
        if(bundle.getBoolean("martes")){
            servicio.addDias(2);
        }
        if(bundle.getBoolean("miercoles")){
            servicio.addDias(3);
        }
        if(bundle.getBoolean("jueves")){
            servicio.addDias(4);
        }
        if(bundle.getBoolean("viernes")){
            servicio.addDias(5);
        }
        if(bundle.getBoolean("sabado")){
            servicio.addDias(6);
        }
        if(bundle.getBoolean("domingo")){
            servicio.addDias(7);
        }
        //---------------------------------------------------------
        if(bundle.getBoolean("7a9")){
            servicio.addHoras(7);
            servicio.addHoras(8);
        }

        if(bundle.getBoolean("9a11")){
            servicio.addHoras(9);
            servicio.addHoras(10);
        }
        if(bundle.getBoolean("11a1")){
            servicio.addHoras(11);
            servicio.addHoras(12);
        }
        if(bundle.getBoolean("2a4")){
            servicio.addHoras(14);
            servicio.addHoras(15);
        }
        if(bundle.getBoolean("4a6")){
            servicio.addHoras(16);
            servicio.addHoras(17);
        }
        if(bundle.getBoolean("6a8")){
            servicio.addHoras(18);
            servicio.addHoras(19);
        }
        //---------------------------------------------------------
            servicio.setPosicionamiento(posicion);
/*
        ArrayList<String> listaImagenes= new ArrayList<String>();
        listaImagenes=bundle.getStringArrayList("imagenes");


        for(int i=0;i<listaImagenes.size();i++){
            servicio.addImagen(Uri.parse(listaImagenes.get(i)));
        }
        */

        servicio.setFechaActivacion(Calendar.getInstance().getTime().toString());

        servicio.setPromedioCalificacion(1000);

        String auxUbicaciones;
        auxUbicaciones=bundle.getString("ubicacion");
        System.out.println("Las ubicaciones son");

        System.out.println(auxUbicaciones);

        /*
        System.out.println("Las direcicones son ");
        for(int i=0;i<bundle.getString("ubicacion")){

        }*/
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseDatabase.getInstance().getReference("servicios").child(user.getUid()).setValue(servicio).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //progressbar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                    //updateUI(user);

                }
                else{
                    Toast.makeText( getActivity(),"Hubo un error al crear el usuario", Toast.LENGTH_SHORT).show();
                }

            }

            });
    }
}


