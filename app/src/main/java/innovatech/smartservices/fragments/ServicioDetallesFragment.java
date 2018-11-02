package innovatech.smartservices.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.w3c.dom.Text;

import innovatech.smartservices.R;
import innovatech.smartservices.models.Servicio;

public class ServicioDetallesFragment extends Fragment {
    TextView basica;
    TextView incluye;
    TextView no_incluye;
    TextView adicional;

    FirebaseAuth mAuth ;
    DatabaseReference mDataBase;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_servicio_detalles, container, false);
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference("servicios");
        basica = (TextView)view.findViewById(R.id.txtDetBasica);
        incluye = (TextView)view.findViewById(R.id.txtDetIncl);
        no_incluye = (TextView)view.findViewById(R.id.txtDetNoIncl);
        adicional = (TextView)view.findViewById(R.id.txtDetAdic);
        Bundle bundle = getArguments();
        String idServ = bundle.getString("idServicio");
        capturarInfoActual(idServ);
        return view;
    }
    private void capturarInfoActual(final String idServ){
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("servicios").child(idServ);
        db.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    Servicio serv = dataSnapshot.getValue(Servicio.class);
                    if(serv.getId().equals(idServ)){
                        basica.setText(serv.getNombre());
                        incluye.setText(serv.getIncluye());
                        no_incluye.setText(serv.getNoIncluye());
                        adicional.setText(serv.getAdicional());
                    }
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
