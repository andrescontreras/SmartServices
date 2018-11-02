package innovatech.smartservices.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.adapters.RecyclerViewAdapter;
import innovatech.smartservices.adapters.ServicioOpinionAdapter;
import innovatech.smartservices.models.Opinion;

public class ServicioOpinionesFragment extends Fragment {
    List<Opinion> lstOpinion =  new ArrayList<Opinion>();
    RecyclerView myrv;
    FirebaseAuth mAuth ;
    RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_servicio_informacion, container, false);
        mAuth = FirebaseAuth.getInstance();
        Bundle bundle = getArguments();
        String idServ = bundle.getString("idServicio");
        mRecyclerView= view.findViewById(R.id.recyclerview_opiniones);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        agregarServicios(idServ);
        return view;
    }
    public void agregarServicios(final String idServ){
        lstOpinion = new ArrayList<>();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("opiniones");
        db.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot!=null){
                        Opinion opinion = snapshot.getValue(Opinion.class);
                        if(opinion.getIdServ().equals(idServ)){
                            lstOpinion.add(opinion);
                        }

                    }
                    else{
                        Toast.makeText(getActivity(), "Hubo un problema encontrando los servicios", Toast.LENGTH_SHORT).show();
                    }
                }
                ServicioOpinionAdapter myAdapter = new ServicioOpinionAdapter(getActivity(),lstOpinion);
                myrv.setHasFixedSize(true);
                myrv.setLayoutManager ( new GridLayoutManager( getActivity(),2 ) );
                myrv.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
}
