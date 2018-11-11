package innovatech.smartservices.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.activities.MainActivity;
import innovatech.smartservices.adapters.RecyclerViewNotificaciones;
import innovatech.smartservices.helpers.EstadoReserva;
import innovatech.smartservices.models.Reserva;
import innovatech.smartservices.models.Servicio;
import innovatech.smartservices.models.Usuario;

public class SolicitudServicioFragment extends Fragment {
    private static final int RESULT_LOAD_IMAGE = 1;
    private ProgressDialog nProgressDialog;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;
    private TextView NombreUsuario;
    private TextView CorreoUsuario;
    private TextView CelUsuario;
    private TextView NombreServicio;
    private TextView DiaServicio;
    private TextView HoraServicio;
    Button aceptarServicio;
    Button rechazarServicio;

    List<Servicio> lstServicio =  new ArrayList<Servicio>();
    List<Servicio> lstServiciosPropios=  new ArrayList<Servicio>();
    List<Usuario> lstUsuarios =  new ArrayList<Usuario>();
    List<Reserva> lstReservas = new ArrayList<Reserva>();
    RecyclerView myrv;
    Servicio servicio;
    Usuario usr;
    Reserva res;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_notificaciones_aceptarrechazar, container, false);
        mAuth = FirebaseAuth.getInstance();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        NombreUsuario=(TextView)view.findViewById(R.id.textViewNombreUsuario);
        CorreoUsuario=(TextView)view.findViewById(R.id.textViewCorreoUsuario);
        CelUsuario=(TextView)view.findViewById(R.id.textViewNumeroUsuario);
        NombreServicio=(TextView)view.findViewById(R.id.textViewNombreServicio);
        DiaServicio=(TextView)view.findViewById(R.id.textViewDia);
        HoraServicio=(TextView)view.findViewById(R.id.textViewHora);
        cargarInformacion(savedInstanceState,view,mAuth);
        aceptarServicio = (Button)view.findViewById(R.id.btn_aceptarServicio);
        rechazarServicio = (Button)view.findViewById(R.id.btn_rechazarServicio);
        aceptarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = getArguments();
                final String idRes=bundle.getString("reserva");

                FirebaseDatabase.getInstance().getReference("reservas").child(bundle.getString("reserva")).child("estado").setValue(EstadoReserva.ACEPTADO).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Servicio aceptado", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText( getActivity(),"Error al aceptar el servicio", Toast.LENGTH_SHORT).show();
                        }
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ServiciosDestacadosFragment principal = new ServiciosDestacadosFragment();
                        ft.replace(R.id.fragment_container,principal);
                        ft.addToBackStack(null);
                        ft.commit();
                    }

                    });

            }

        });
        rechazarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = getArguments();
                final String idRes=bundle.getString("reserva");
                android.support.v7.app.AlertDialog builder = createSimpleDialog(getActivity(),idRes);
                builder.show();

            }

        });


//        NombreUsuario.setText(usr.getNombre());
/*        CorreoUsuario.setText(usr.getEmail());
        CelUsuario.setText(usr.getTelefono());
        NombreServicio.setText(servicio.getNombre());
        DiaServicio.setText(res.getFecha());
        HoraServicio.setText(res.getHora());*/


        //agregarNotificaciones(view,mAuth);
        return view;
    }
    public android.support.v7.app.AlertDialog createSimpleDialog(FragmentActivity myActivity, final String idRes) {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(myActivity);

       /* builder.setTitle("Titulo")
                .setMessage("El Mensaje para el usuario")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //   listener.onPossitiveButtonClick();
                            }
                        });*/
        LayoutInflater inflater = myActivity.getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_rechazar, null);
        final EditText texto = (EditText) v.findViewById(R.id.editText_motivo) ;
        builder.setView(v);
        builder.setPositiveButton("ACEPTAR",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            // Crear Cuenta...
                            if(TextUtils.isEmpty(texto.getText())){
                                Toast.makeText(getActivity(), "Debe ingresar el motivo del rechazo de servicio", Toast.LENGTH_SHORT).show();
                            }else{
                                FirebaseDatabase.getInstance().getReference("reservas").child(idRes).child("estado").setValue(EstadoReserva.RECHAZADO);
                                FirebaseDatabase.getInstance().getReference("reservas").child(idRes).child("razon").setValue(texto.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getActivity(), "Servicio rechazado", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText( getActivity(),"Error al rechazar el servicio", Toast.LENGTH_SHORT).show();
                                        }
                                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                        ServiciosDestacadosFragment principal = new ServiciosDestacadosFragment();
                                        ft.replace(R.id.fragment_container,principal);
                                        ft.addToBackStack(null);
                                        ft.commit();

                                        //finish();

                                    }

                                });
                                //dismiss();

                            }


                            //dismiss();
                    }
                });


        return builder.create();
    }
    public void cargarInformacion(Bundle savedInstanceState, View view, FirebaseAuth mAuth){
        Bundle bundle=getArguments();
        final String idServ=bundle.getString("idServicio");
        final String idUsu=bundle.getString("idUsuario");
        final String idRes=bundle.getString("reserva");
        servicio = new Servicio();
        usr = new Usuario();
        res = new Reserva();

        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("servicios").child(idServ);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    servicio = dataSnapshot.getValue(Servicio.class);
                    NombreServicio.setText(servicio.getNombre());
                }
                else{
                    Toast.makeText(getActivity(), "Hubo un problema encontrando el servicio", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
        DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("users").child(idUsu);
        db2.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    usr = dataSnapshot.getValue(Usuario.class);
                    NombreUsuario.setText(usr.getNombre());
                    CorreoUsuario.setText(usr.getEmail());
                    CelUsuario.setText(String.valueOf(usr.getTelefono()));
                    //System.out.println("Usuario: "+ usr.getNombre());

                }
                else{
                    Toast.makeText(getActivity(), "Hubo un problema encontrando el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
        DatabaseReference db3 = FirebaseDatabase.getInstance().getReference().child("reservas").child(idRes);
        db3.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    res = dataSnapshot.getValue(Reserva.class);
                    DiaServicio.setText(res.getFecha());
                    HoraServicio.setText(String.valueOf(res.getHora())+":00");

                }
                else{
                    Toast.makeText(getActivity(), "Hubo un problema encontrando el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

    }

}
