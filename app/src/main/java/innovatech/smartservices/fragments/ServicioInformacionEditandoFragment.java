package innovatech.smartservices.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
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

import innovatech.smartservices.R;
import innovatech.smartservices.adapters.ImagenInformacionServicioAdapter;
import innovatech.smartservices.models.Servicio;
import me.relex.circleindicator.CircleIndicator;

public class ServicioInformacionEditandoFragment extends Fragment {
    Button btn_elim_serv;
    Button btn_edit_serv;
    Button btn_hist_prop;
    Button btn_detalles;
    Button btn_preguntas;
    Button btn_comentarios;
    TextView titulo_serv;
    TextView precio_serv;
    RatingBar ratingBar;
    Servicio serv ;
    ViewPager viewPager;
    ImagenInformacionServicioAdapter imgAnfAdapter;
    String imagenInicial ="";
    CircleIndicator indicator;
    String idServ;
    String nombre;
    FirebaseAuth mAuth ;
    DatabaseReference mDataBase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_servicio_informacion_editando, container, false);
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference("servicios");
        btn_elim_serv = (Button)view.findViewById(R.id.serv_btn_eliminar);
        btn_edit_serv= (Button)view.findViewById(R.id.serv_btn_editar);
        btn_hist_prop= (Button)view.findViewById(R.id.serv_btn_historial);
        btn_detalles = (Button)view.findViewById(R.id.serv_btn_detalles);
        btn_preguntas = (Button)view.findViewById(R.id.serv_btn_preguntas);
        btn_comentarios = (Button)view.findViewById(R.id.serv_btn_comentarios);
        titulo_serv = (TextView)view.findViewById(R.id.serv_txt_titulo);
        precio_serv = (TextView)view.findViewById(R.id.serv_txt_precio);
        ratingBar = (RatingBar)view.findViewById(R.id.ratingBarServ);
        viewPager = (ViewPager)view.findViewById(R.id.viewPagerServ);
        indicator = (CircleIndicator)view.findViewById(R.id.ciImagenesAlojAPA);
        Bundle bundle = getArguments();
        idServ = bundle.getString("idServicio");
        nombre = bundle.getString("nombreServicio");
        capturarInfoActual(idServ);
        accionBotones(idServ);
        return view;
    }
    private void capturarInfoActual(String idServ){
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("servicios").child(idServ);
        db.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    serv = dataSnapshot.getValue(Servicio.class);
                   titulo_serv.setText(serv.getNombre());
                   precio_serv.setText(String.valueOf(serv.getPrecio()));
                   imagenInicial = serv.getFotos().get(0);
                    imgAnfAdapter = new ImagenInformacionServicioAdapter(getActivity(),serv.getFotos());
                    viewPager.setAdapter(imgAnfAdapter);
                    
                    indicator.setViewPager(viewPager);
                }
                else{
                    Toast.makeText(getActivity(), "Hubo un problema encontrando el uid", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
    public void accionBotones(final String idServ){
       /* btn_comentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ServicioOpinionesFragment opinionesServ = new ServicioOpinionesFragment();
                ft.replace(R.id.fragment_container, opinionesServ);
                ft.addToBackStack(null);
                Bundle bundle = getArguments();
                bundle.putString("idServicio",idServ);
                opinionesServ.setArguments(bundle);
                ft.commit();
            }
        });*/
        btn_detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ServicioDetallesFragment detallesServ = new ServicioDetallesFragment();
                ft.replace(R.id.fragment_container, detallesServ);
                ft.addToBackStack(null);
                Bundle bundle = getArguments();
                bundle.putString("idServicio",idServ);
                detallesServ.setArguments(bundle);
                ft.commit();
            }
        });
        btn_elim_serv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificarSesion()){
                    FirebaseDatabase.getInstance().getReference("servicios").child(idServ).child("estado").setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                System.out.println("se jugó y se gano");
                                Toast.makeText( getActivity(), "Servicio eliminado", Toast.LENGTH_SHORT).show();
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                if(fm.getBackStackEntryCount()>0){
                                    fm.popBackStack();
                                }
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                CuentaAdministrarServiciosFragment adminCuenta= new CuentaAdministrarServiciosFragment();
                                ft.replace(R.id.fragment_container, adminCuenta);
                                ft.commit();
                            }
                            else{
                                Toast.makeText( getActivity(),"Hubo un error al eliminar el servicio", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getActivity(), "Para eliminar un servicio debe haber iniciado sesión", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_edit_serv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificarSesion()){
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    CuentaEditarServicio1Fragment editServ1= new CuentaEditarServicio1Fragment();
                    ft.replace(R.id.fragment_container, editServ1);
                    ft.addToBackStack(null);
                    Bundle bundle = getArguments();
                    bundle.putString("idServicio",idServ);
                    editServ1.setArguments(bundle);
                    ft.commit();
                }else{
                    Toast.makeText(getActivity(), "Para editar un servicio debe haber iniciado sesión", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_hist_prop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                CuentaHistorialServiciosPropiosFragment servProp= new CuentaHistorialServiciosPropiosFragment();
                ft.replace(R.id.fragment_container, servProp);
                ft.addToBackStack(null);
                Bundle bundle = getArguments();
                bundle.putString("idServicio",idServ);
                bundle.putString("nombreServicio",nombre);
                servProp.setArguments(bundle);
                ft.commit();
            }
        });
    }
   public boolean verificarSesion(){
       FirebaseUser currentUser = mAuth.getCurrentUser();
       if(currentUser==null){ //Cuando el usuario ya esta logeado, mandarlo a la actividad principal
           return false;
       }else{
           return true;
       }
    }
    public void cambiarEstadoServicio(){

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("servicios").child(idServ);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    Servicio servicio = dataSnapshot.getValue(Servicio.class);
                    servicio.setEstado(false);
                    FirebaseDatabase.getInstance().getReference().child("servicios").child(idServ).setValue(servicio);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ServiciosDestacadosFragment servDest= new ServiciosDestacadosFragment();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    ft.replace(R.id.fragment_container, servDest);
                    ft.commit();
                }
                else{
                    Toast.makeText(getActivity(), "Hubo un problema encontrando el uid", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
}

