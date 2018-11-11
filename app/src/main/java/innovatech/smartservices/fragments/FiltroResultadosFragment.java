package innovatech.smartservices.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.ListIterator;

import innovatech.smartservices.R;
import innovatech.smartservices.adapters.RecyclerViewAdapter;
import innovatech.smartservices.models.Servicio;

public class FiltroResultadosFragment extends Fragment {
    ArrayList<Servicio> lstServicio =  new ArrayList<Servicio>(); //Se guardaran primero los servicios con posicionamiento para mostrarlos de primero
    List<Servicio> listaSinPrioridad = new ArrayList<Servicio>();
    RecyclerView myrv;
    FirebaseAuth mAuth ;
    RecyclerViewAdapter myAdapter;
    Bundle bundle ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtro_resultados, container, false);

        mAuth = FirebaseAuth.getInstance();
        myrv = (RecyclerView) view.findViewById(R.id.rv_resultados_busqueda);
        myrv.setHasFixedSize(true);
        myrv.setLayoutManager ( new GridLayoutManager( getActivity(),2 ) );
        bundle = getArguments();
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
                double filtroPrecio;
                boolean filtroCategoria ;
                boolean filtroDia ;
                boolean filtroHora;
                boolean cumpleCategoria ; //Para verificar si un servicio contiene la categoria con la que se filtra
                boolean cumpleDia ;
                boolean cumpleHora ;
                boolean cumple ;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot!=null){
                        filtroPrecio=Double.POSITIVE_INFINITY;
                        filtroCategoria=false;
                        filtroDia=false;
                        filtroHora=false;
                        cumpleCategoria=false;
                        cumpleDia=false;
                        cumpleHora=false;
                        cumple=true;
                        if(bundle.getInt("precio")!=-1)
                            filtroPrecio = bundle.getInt("precio");
                        if(!bundle.get("categoria").equals("Seleccionar categoria"))
                            filtroCategoria=true;
                        if(!bundle.get("dia").equals("Seleccionar dia"))
                            filtroDia=true;
                        if(!bundle.get("hora").equals("Seleccionar hora"))
                            filtroHora=true;
                        Servicio serv = snapshot.getValue(Servicio.class);
                        if(filtroCategoria){
                            String categ = bundle.getString("categoria");
                            if (serv.getTipo().contains(bundle.getString("categoria"))){
                                cumpleCategoria=true;
                            }
                        }
                        if(filtroDia){
                            if(cumpleFiltroDia(serv.getDisponibilidadDias(),bundle.getString("dia"))){
                                cumpleDia=true;
                            }
                            System.out.println("Esto es cumpleDia despues de hallar el valor ----------------------------------> "+cumpleDia);
                        }
                        if(filtroHora){
                            if(cumpleFiltroHora(serv.getDisponibilidadHoras(),bundle.getString("hora"))){
                                cumpleHora=true;
                            }

                        }
                        if(serv.getPrecio()<filtroPrecio ){
                            System.out.println("Esto es cumpleeeeeeeeeeeeeeeeeeeeeeeeeeeee 1111 -----------> "+cumple);
                            if(filtroCategoria && !cumpleCategoria)
                                cumple=false;
                            System.out.println("Esto es cumpleeeeeeeeeeeeeeeeeeeeeeeeeeeee 22222 -----------> "+cumple);
                            if(filtroDia && !cumpleDia)
                                cumple=false;
                            System.out.println("Esto es cumpleeeeeeeeeeeeeeeeeeeeeeeeeeeee 3333 -----------> "+cumple);
                            if(filtroHora && !cumpleHora)
                                cumple=false;
                            System.out.println("Esto es cumpleeeeeeeeeeeeeeeeeeeeeeeeeeeee 4444 -----------> "+cumple);
                            if(cumple){
                                if(serv.getPosicionamiento()){
                                    lstServicio.add(serv);
                                }
                                else{
                                    listaSinPrioridad.add(serv);
                                }
                            }
                        }

                    }
                    else{
                        Toast.makeText(getActivity(), "Hubo un problema encontrando los servicios", Toast.LENGTH_SHORT).show();
                    }
                    cumple=true;
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
    /*
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
    */
    public boolean cumpleFiltroDia(List<Integer> diasDisponibles, String dia){
        int diaInt;
        switch (dia){
            case "Lunes":
                diaInt=1;
                break;
            case "Martes":
                diaInt=2;
                break;
            case "Miercoles":
                diaInt=3;
                break;
            case "Jueves":
                diaInt=4;
                break;
            case "Viernes":
                diaInt=5;
                break;
            case "Sabado":
                diaInt=6;
                break;
            case "Domingo":
                diaInt=7;
                break;
            default:
                diaInt=0;
                break;
        }
        System.out.println("Dia que buscoooooooooooooooooooooooooooooooooo "+ dia + " "+diaInt);
        for(int i=0;i<diasDisponibles.size();i++){
            System.out.println("Lo comparo con el dia ----------------------> " +diasDisponibles.get(i));
            if(diaInt==diasDisponibles.get(i)){
                System.out.println("Entro, son dias iguales ");
                return true;
            }
        }
        System.out.println("Envio false de validarDia ---------------------------->");
        return false;
    }
    public boolean cumpleFiltroHora(List<Integer> horasDisponibles, String hora){
        List<Integer> listaHoras= new ArrayList<>();
        //System.out.println("Me llega en hora ----------------------------> "+hora );
        if(hora.equals("7:00 am - 9:00 am")){
            //System.out.println("7:00 am - 9:00 am");
            listaHoras.add(7);
            listaHoras.add(8);
            listaHoras.add(9);
        }
        if(hora.equals("9:00 am - 11:00 am")){
            //System.out.println("9:00 am - 11:00 am");
            listaHoras.add(9);
            listaHoras.add(10);
            listaHoras.add(11);
        }
        if(hora.equals("11:00 am - 1:00 pm")){
            //System.out.println("11:00 am - 1:00 pm");
            listaHoras.add(11);
            listaHoras.add(12);
            listaHoras.add(1);
        }
        if(hora.equals("2:00 pm - 4:00 pm")){
            //System.out.println("2:00 pm - 4:00 pm");
            listaHoras.add(14);
            listaHoras.add(15);
            listaHoras.add(16);
        }
        if(hora.equals("4:00 pm - 6:00 pm")){
            //System.out.println("4:00 pm - 6:00 pm");
            listaHoras.add(16);
            listaHoras.add(17);
            listaHoras.add(18);
        }
        if(hora.equals("6:00 pm - 8:00 pm")){
            //System.out.println("6:00 pm - 8:00 pm");
            listaHoras.add(18);
            listaHoras.add(19);
            listaHoras.add(20);
        }
        for(int j=0;j<listaHoras.size();j++){
            //System.out.println("Analizo la horaaaaaaaaaaaaaaaaaaaaa ->>>>>>>>>>>>>>>>>>>>>>>>>>> "+listaHoras.get(j));
            for(int i=0;i<horasDisponibles.size();i++){
                //System.out.println("Comparo con  la horaaaaaaaaaaaaa ->>>>>>>>>>>>>>>>>>> "+horasDisponibles.get(i));
                if(listaHoras.get(j)==horasDisponibles.get(i))
                    return true;
            }
        }
        return false;
    }
}
