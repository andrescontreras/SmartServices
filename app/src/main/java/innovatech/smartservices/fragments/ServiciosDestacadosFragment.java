package innovatech.smartservices.fragments;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.SearchView;
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
import innovatech.smartservices.models.Servicio;

import static android.support.v7.widget.LinearLayoutManager.*;

public class ServiciosDestacadosFragment extends Fragment{
    ArrayList<Servicio> lstServicio =  new ArrayList<Servicio>(); //Se guardaran primero los servicios con posicionamiento para mostrarlos de primero
    List<Servicio> listaSinPrioridad = new ArrayList<Servicio>();
    RecyclerView myrv;
    FirebaseAuth mAuth ;
    RecyclerViewAdapter myAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final View view = inflater.inflate(R.layout.fragment_destacados_servicio, container, false);
        mAuth = FirebaseAuth.getInstance();

        //Limpio la pila de fragmentos
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        myrv = (RecyclerView) view.findViewById(R.id.recyclerview_id);
        myrv.setHasFixedSize(true);
        myrv.setLayoutManager ( new GridLayoutManager ( getActivity(),2 ) );
        agregarServicios();
        return view;
    }
    public void agregarServicios(){
        lstServicio = new ArrayList <Servicio> ();
        listaSinPrioridad = new ArrayList<Servicio>();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("servicios");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot!=null){
                        Servicio serv = snapshot.getValue(Servicio.class);
                        if(serv.getEstado()){
                            if(serv.getPosicionamiento()){
                                lstServicio.add(serv);
                            }
                            else{
                                listaSinPrioridad.add(serv);
                            }
                        }

                    }
                    else{
                        Toast.makeText(getActivity(), "Hubo un problema encontrando los servicios", Toast.LENGTH_SHORT).show();
                    }
                }
                if(listaSinPrioridad.size()>0){
                    lstServicio.addAll(listaSinPrioridad);
                }
                myAdapter = new RecyclerViewAdapter(getActivity(),lstServicio);
                myrv.setHasFixedSize(true);
                myrv.setLayoutManager ( new GridLayoutManager ( getActivity(),2 ) );
                myrv.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        //getActivity().getMenuInflater().inflate(R.menu.menu_buscador,menu);
        inflater.inflate(R.menu.menu_buscador,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if(itemClicked == R.id.action_filter){
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            FiltroBusquedaFragment busqueda_avanzada = new FiltroBusquedaFragment();
            ft.replace(R.id.fragment_container, busqueda_avanzada);
            ft.addToBackStack(null);
            ft.commit();
        }
        return super.onOptionsItemSelected(item);
    }
}
