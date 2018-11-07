package innovatech.smartservices.fragments;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.adapters.ImageAdapter;
import innovatech.smartservices.adapters.RecyclerViewEditandoImagenesAdapter;
import innovatech.smartservices.models.Servicio;
import innovatech.smartservices.models.Ubicacion;

import static android.app.Activity.RESULT_OK;

public class PubInfoBasicaEditandoFragment extends Fragment {
    Button selecImagen ;
    Button sig;
    private EditText nombre;
    private EditText precio;
    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 2;
    private static final int RESULT_LOAD_IMAGE = 1;
    private ProgressDialog nProgressDialog;
    private ImageAdapter mAdapter;
    private RecyclerView myrv;
    private List<Uri> listaImagenes = new ArrayList<Uri>();
    List<Servicio> lstServicio =  new ArrayList<Servicio>();
    RecyclerViewEditandoImagenesAdapter myAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pub_info_basica_servicio_editando, container, false);
        Bundle bundle = getArguments();
        nProgressDialog = new ProgressDialog(getActivity());
        mStorage = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        nombre = (EditText)view.findViewById(R.id.txtNombre);
        precio = (EditText)view.findViewById(R.id.txtPrecio);
        selecImagen = (Button)view.findViewById(R.id.btn_agregarImagenes);
        sig = (Button)view.findViewById(R.id.btn_sig_detalles);
        myrv = view.findViewById(R.id.recycler_view);
        myrv.setHasFixedSize(true);
        myrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        cargarInfoAnterior(savedInstanceState,view,mAuth);
        accionBotones(view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode== RESULT_OK){ // TODO Probar si funciona sin verificar el GALLERY INTENT
            if(data.getClipData()!=null){
                ClipData imagenes = data.getClipData();
                int totalItemsSelected = data.getClipData().getItemCount();

                for(int i=0 ; i<totalItemsSelected;i++){
                    ClipData.Item item = imagenes.getItemAt(i);
                    Uri uri = item.getUri();
                    listaImagenes.add(uri);

                }
                mAdapter = new ImageAdapter(getActivity(),listaImagenes);
                myrv.setAdapter(mAdapter);
                Toast.makeText(getActivity(), "Se subieron los archivos", Toast.LENGTH_SHORT).show();
            }else if(data.getData()!=null){
                Uri uri = data.getData();
                listaImagenes.add(uri);
                mAdapter = new ImageAdapter(getActivity(),listaImagenes);
                myrv.setAdapter(mAdapter);

            }
        }
    }
    public void accionBotones(View view){
        selecImagen.setOnClickListener(new View.OnClickListener() {
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
        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(nombre.getText()) || TextUtils.isEmpty(precio.getText()) || listaImagenes.size()==0){
                    Toast.makeText(getActivity(), "Rellene los campos: Nombre, Precio. Además, agregue por lo menos una imagen", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        PubDetallesFragment detallesServ = new PubDetallesFragment();
                        ft.replace(R.id.fragment_container, detallesServ);
                        ft.addToBackStack(null);
                        Bundle bundle= getArguments();
                        ArrayList<String>listaImagenesStr= new ArrayList<String>();
                        for(int i=0;i<listaImagenes.size();i++){
                            listaImagenesStr.add(listaImagenes.get(i).toString());
                        }
                        bundle.putString("nombre",nombre.getText().toString());
                        int precioInt = Integer.parseInt(precio.getText().toString());
                        bundle.putInt("precio",precioInt);
                        bundle.putStringArrayList("imagenes",listaImagenesStr);
                        detallesServ.setArguments(bundle);
                        ft.commit();
                    }catch(NumberFormatException excepcion){
                        Toast.makeText(getActivity(), "Debe ingresar un numero en la casilla de precio", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
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
                    List<Servicio>aux=new ArrayList<Servicio>();
                    for(int i=0;i<lstServicio.size();i++){
                        if(lstServicio.get(i).getId()!=null && idServ!=null){
                            System.out.println("si entra al if de que no son nulos");
                            if(lstServicio.get(i).getId().equals(idServ)){
                                nombre.setText(lstServicio.get(i).getNombre().toString());
                                precio.setText(String.valueOf(lstServicio.get(i).getPrecio()));
                                aux.add(lstServicio.get(i));
                                myAdapter=new RecyclerViewEditandoImagenesAdapter(getActivity(),aux);
                                myrv.setHasFixedSize(true);
                                myrv.setLayoutManager(new LinearLayoutManager(getActivity()));
                                myrv.setAdapter(myAdapter);

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
