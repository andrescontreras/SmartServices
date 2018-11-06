package innovatech.smartservices.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.adapters.ImagenInformacionServicioAdapter;
import innovatech.smartservices.models.Servicio;

import me.relex.circleindicator.CircleIndicator;

public class ServicioInformacionFragment extends Fragment {
    Button btn_solic_serv;
    Button btn_detalles;
    Button btn_preguntas;
    Button btn_comentarios;
    Button btn_favoritos;
    TextView titulo_serv;
    TextView precio_serv;
    RatingBar ratingBar;
    Servicio serv ;
    ViewPager viewPager;
    ImagenInformacionServicioAdapter imgAnfAdapter;
    String imagenInicial ="";
    CircleIndicator indicator;

    FirebaseAuth mAuth ;
    DatabaseReference mDataBase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_servicio_informacion, container, false);
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference("servicios");
        btn_solic_serv = (Button)view.findViewById(R.id.serv_btn_solicitar);
        btn_detalles = (Button)view.findViewById(R.id.serv_btn_detalles);
        btn_preguntas = (Button)view.findViewById(R.id.serv_btn_preguntas);
        btn_comentarios = (Button)view.findViewById(R.id.serv_btn_comentarios);
        btn_favoritos = (Button)view.findViewById(R.id.serv_btn_favoritos);
        titulo_serv = (TextView)view.findViewById(R.id.serv_txt_titulo);
        precio_serv = (TextView)view.findViewById(R.id.serv_txt_precio);
        ratingBar = (RatingBar)view.findViewById(R.id.ratingBarServ);
        viewPager = (ViewPager)view.findViewById(R.id.viewPagerServ);
        indicator = (CircleIndicator)view.findViewById(R.id.ciImagenesAlojAPA);
        Bundle bundle = getArguments();
        String idServ = bundle.getString("idServicio");
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
                    //int cedula = dataSnapshot.child("cedula").getValue(Integer.class);
                    //String nombre = dataSnapshot.child("nombre").getValue(String.class);
                   titulo_serv.setText(serv.getNombre());
                   precio_serv.setText(String.valueOf(serv.getPrecio()));
                   imagenInicial = serv.getFotos().get(0);
                   //imagenServ.setImageURI(null);
                   //imagenServ.setImageURI(Uri.parse(serv.getFotos().get(0)));
                    imgAnfAdapter = new ImagenInformacionServicioAdapter(getActivity(),serv.getFotos());
                    viewPager.setAdapter(imgAnfAdapter);
                    
                    indicator.setViewPager(viewPager);

                    //Picasso.with(getContext()).load(Uri.parse(serv.getFotos().get(0))).into(imagenServ);
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
        btn_solic_serv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificarSesion()){
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ServicioSolicitarDiaFragment solicitarServ = new ServicioSolicitarDiaFragment();
                    ft.replace(R.id.fragment_container, solicitarServ);
                    ft.addToBackStack(null);
                    Bundle bundle = new Bundle();
                    bundle.putString("nombreServ",titulo_serv.getText().toString());
                    bundle.putString("precioServ",precio_serv.getText().toString());
                    bundle.putString("imagenIni",imagenInicial);
                    bundle.putSerializable("servicio",serv);
                    solicitarServ.setArguments(bundle);
                    ft.commit();
                }else{
                    Toast.makeText(getActivity(), "Para realizar la reserva de un servicio debe haber iniciado sesión", Toast.LENGTH_SHORT).show();
                }

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
}
