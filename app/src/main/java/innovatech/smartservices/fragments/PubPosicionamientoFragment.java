package innovatech.smartservices.fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import innovatech.smartservices.R;
import innovatech.smartservices.models.Servicio;
import innovatech.smartservices.models.Ubicacion;

public class PubPosicionamientoFragment extends Fragment {
    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private ProgressDialog nProgressDialog;
    List<String> imgUri = new ArrayList<String>();
    String referenciaUrl = "";
    Bundle bundle ;
    Servicio servicio = new Servicio();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pub_posicionamiento, container, false);
        mAuth = FirebaseAuth.getInstance();
        nProgressDialog = new ProgressDialog(getActivity());



        Button botonSI = (Button)view.findViewById(R.id.buttonSIPosicion);
        Button botonNO= (Button)view.findViewById(R.id.buttonNOPosicion);
        bundle=getArguments();
        mStorage =FirebaseStorage.getInstance().getReference("Uploads");

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
        nProgressDialog.setMessage("Publicando el servicio...");
        nProgressDialog.show();

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
        //----------------------------------------------------------------------------------
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

            
        }
        if(bundle.getBoolean("6a8")){
            servicio.addHoras(18);
            servicio.addHoras(19);
        }
        //----------------------------------------------------------------------------------
            servicio.setPosicionamiento(posicion);

        servicio.setFechaActivacion(Calendar.getInstance().getTime().toString());

        servicio.setPromedioCalificacion(5);
        //----------------------------------------------------------------------------------
        String auxUbicaciones;
        auxUbicaciones=bundle.getString("ubicacion");
        //System.out.println("sacando ubicaciones "+auxUbicaciones.toString());
        String[] parts = auxUbicaciones.split("Place");
        Ubicacion ubiAux=new Ubicacion("","",0,0);

        for(int i=1;i<parts.length;i++){
            System.out.println("lo del split "+parts[i]);
            ubiAux= new Ubicacion(parts[i],"",0,0);
            servicio.addUbicacion(ubiAux);
        }
        //----------------------------------------------------------------------------------
        subirImagenesStorage();

    }
    private String extensionImagen(Uri uri){
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    private void subirImagenesStorage(){
        Uri uri;
        List<String> listaImagenes = new ArrayList<String>(); //Si hay error, cambiar el List por ArrayList
        listaImagenes=bundle.getStringArrayList("imagenes");
        String imagenUrl="";
        uri=Uri.parse(listaImagenes.get(0));
        StorageReference fileReference = mStorage.child(System.currentTimeMillis()+"."+extensionImagen(uri));
        fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                referenciaUrl = taskSnapshot.getDownloadUrl().toString();
                imgUri.add(referenciaUrl);
                subirServicio();
                System.out.println("ESTO ES TASK SNAPSHOT DONWLOAD URL ANTESSSSSSSSSSSS-------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+referenciaUrl);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                referenciaUrl="";
                Toast.makeText(getActivity(), "Falló la subida de una imagen", Toast.LENGTH_SHORT).show();
            }
        });
        System.out.println("ESTO ES TASK SNAPSHOT DONWLOAD URL -------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+referenciaUrl);
    }
    public void subirServicio(){
        servicio.setFotos(imgUri);
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseDatabase.getInstance().getReference("servicios").child(user.getUid()+String.valueOf(System.currentTimeMillis())).setValue(servicio).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //progressbar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Se ha publicado el servicio", Toast.LENGTH_SHORT).show();

                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ServiciosDestacadosFragment servDest= new ServiciosDestacadosFragment();
                    ft.replace(R.id.fragment_container, servDest);
                    nProgressDialog.dismiss();

                    ft.commit();

                    //updateUI(user);

                }
                else{
                    Toast.makeText( getActivity(),"Hubo un error al crear un servicio", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
}


