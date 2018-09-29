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
    private static final int RESULT_LOAD_IMAGE = 1;
    TextView texto;
    Button boton;
    Button sec_imagen;
    Button cambiar_usu;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog nProgressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cuenta, container, false);
        texto= (TextView)view.findViewById(R.id.textoPrueba1);
        nProgressDialog = new ProgressDialog(getActivity());
        boton = (Button)view.findViewById(R.id.boton_prueba1);
        sec_imagen = (Button)view.findViewById(R.id.selec_imagen);
        cambiar_usu = (Button)view.findViewById(R.id.change_usu);
        mStorage = FirebaseStorage.getInstance().getReference();
        sec_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccionar imagenes"),RESULT_LOAD_IMAGE);
                //startActivityForResult(intent,RESULT_LOAD_IMAGE);
            }
        });
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoxd = "CAMBIA EL TEXTO WOW !";
                texto.setText(textoxd);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                NotificacionesFragment notificacion = new NotificacionesFragment ();
                ft.replace(R.id.fragment_container, notificacion);
                ft.addToBackStack(null);
                //notificacion.setArguments(bundle);
                ft.commit();
            }
        });
        cambiar_usu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                EditarUsuarioFragment editarUsuario = new EditarUsuarioFragment();
                ft.replace(R.id.fragment_container, editarUsuario);
                ft.addToBackStack(null);
                //editarUsuario.setArguments(bundle);
                ft.commit();
            }
        });
        return view;
        //return inflater.inflate(R.layout.fragment_cuenta,container,false);
    }

    //TODO Para que se guardara el texto que tenia en un fragment anterior despues de ir a otro, coloque
    //TODO en la parte de xml, dentro del TextView el atributo freezesText=true

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode== RESULT_OK){ // TODO Probar si funciona sin verificar el GALLERY INTENT
            if(data.getClipData()!=null){
                nProgressDialog.setMessage("Subiendo los archivos...");
                nProgressDialog.show();
                ClipData imagenes = data.getClipData();
                int totalItemsSelected = data.getClipData().getItemCount();
                for(int i=0 ; i<totalItemsSelected;i++){
                    ClipData.Item item = imagenes.getItemAt(i);
                    Uri uri = item.getUri();
                    StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
                    filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
                nProgressDialog.dismiss();
                Toast.makeText(getActivity(), "Se subieron los archivos", Toast.LENGTH_SHORT).show();
            }else if(data.getData()!=null){
                nProgressDialog.setMessage("Subiendo archivo...");
                nProgressDialog.show();
                Uri uri = data.getData();
                StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "Se subio el archivo", Toast.LENGTH_SHORT).show();
                        nProgressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        }
    }
}

