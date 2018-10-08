package innovatech.smartservices.fragments;
import innovatech.smartservices.R;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import javax.xml.transform.Result;

import static android.app.Activity.RESULT_OK;


public class CuentaFragment extends Fragment {
    Button nueva_publi;
    Button admin_serv;
    Button edit_usu;
    Button serv_solic;
    Button buzon_p;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cuenta, container, false);
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
    }
    //METODO QUE CONTIENE TODAS LAS ACCIONES DE LOS BOTONES
    private  void accionBotones(View view){

        nueva_publi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                CrearServicioFragment servicio = new CrearServicioFragment();
                ft.replace(R.id.fragment_container,servicio);
                ft.addToBackStack(null);
                //notificacion.setArguments(bundle);
                ft.commit();
            }
        });
        admin_serv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
}

