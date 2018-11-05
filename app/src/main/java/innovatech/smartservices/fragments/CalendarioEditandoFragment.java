package innovatech.smartservices.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import innovatech.smartservices.models.Servicio;

public class CalendarioEditandoFragment extends Fragment {
    //CalendarView calendar ;
    Button botoncito;
    Boolean sietenueveam;
    Boolean nueveonceam;
    Boolean onceunapm;
    Boolean doscuatropm;
    Boolean cuatroseispm;
    Boolean seisochopm;

    private CheckBox lunes;
    private CheckBox martes;
    private CheckBox miercoles;
    private CheckBox jueves;
    private CheckBox viernes;
    private CheckBox sabado;
    private CheckBox domingo;

    private CheckBox Csietenueveam;
    private CheckBox Cnueveonceam;
    private CheckBox Conceunapm;
    private CheckBox Cdoscuatropm;
    private CheckBox Ccuatroseispm;
    private CheckBox Cseisochopm;

    List<Servicio> lstServicio =  new ArrayList<Servicio>();
    private List lstHoras=new ArrayList();
    private List lstDias=new ArrayList();
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calendario_servicio_editando , container, false);

        mAuth = FirebaseAuth.getInstance();
        botoncito = (Button)view.findViewById(R.id.button_calendario);
        lunes= (CheckBox) view.findViewById(R.id.checkBox_c_lunes);
        martes= (CheckBox) view.findViewById(R.id.checkBox_c_martes);
        miercoles= (CheckBox) view.findViewById(R.id.checkBox_c_miercoles);
        jueves= (CheckBox) view.findViewById(R.id.checkBox_c_jueves);
        viernes= (CheckBox) view.findViewById(R.id.checkBox_c_viernes);
        sabado= (CheckBox) view.findViewById(R.id.checkBox_c_sabado);
        domingo= (CheckBox) view.findViewById(R.id.checkBox_c_domingo);

        Csietenueveam = (CheckBox) view.findViewById(R.id.checkBox_c_7a9);
        Cnueveonceam = view.findViewById(R.id.checkBox_c_9a11);
        Conceunapm = view.findViewById(R.id.checkBox_c_11a1);
        Cdoscuatropm = view.findViewById(R.id.checkBox_c_2a4);
        Ccuatroseispm = view.findViewById(R.id.checkBox_c_4a6);
        Cseisochopm = view.findViewById(R.id.checkBox_c_6a8);
        cargarInfoAnterior(savedInstanceState,view,mAuth);
        botoncito.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!lunes.isChecked() && !martes.isChecked() && !miercoles.isChecked() && !jueves.isChecked() && !viernes.isChecked() && !sabado.isChecked() && !domingo.isChecked()){
                    Toast.makeText(getActivity(), "Debe seleccionar almenos un dia de disponibilidad", Toast.LENGTH_SHORT).show();
                }
                else if(!Csietenueveam.isChecked() && !Cnueveonceam.isChecked() && !Conceunapm.isChecked() && !Cdoscuatropm.isChecked() && !Ccuatroseispm.isChecked() && !Cseisochopm.isChecked() ){
                    Toast.makeText(getActivity(), "Debe seleccionar almenos una franja horaria de disponibilidad", Toast.LENGTH_SHORT).show();
                }
                else{
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    PubPosicionamientoFragment pubPosicionamiento = new PubPosicionamientoFragment();
                    ft.replace(R.id.fragment_container, pubPosicionamiento);
                    ft.addToBackStack(null);
                    Bundle bundle = getArguments();
                    bundle.putBoolean("lunes",lunes.isChecked());
                    bundle.putBoolean("martes",martes.isChecked());
                    bundle.putBoolean("miercoles",miercoles.isChecked());
                    bundle.putBoolean("jueves",jueves.isChecked());
                    bundle.putBoolean("viernes",viernes.isChecked());
                    bundle.putBoolean("sabado",sabado.isChecked());
                    bundle.putBoolean("domingo",domingo.isChecked());

                    bundle.putBoolean("7a9", Csietenueveam.isChecked());
                    bundle.putBoolean("9a11", Cnueveonceam.isChecked());
                    bundle.putBoolean("11a1", Conceunapm.isChecked());
                    bundle.putBoolean("2a4", Cdoscuatropm.isChecked());
                    bundle.putBoolean("4a6", Ccuatroseispm.isChecked());
                    bundle.putBoolean("6a8", Cseisochopm.isChecked());

                    pubPosicionamiento.setArguments(bundle);
                    ft.commit();
                }
            }
        });
        return view;
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
                    for(int i=0;i<lstServicio.size();i++){
                        if(lstServicio.get(i).getId()!=null && idServ!=null){
                            System.out.println("si entra al if de que no son nulos");
                            if(lstServicio.get(i).getId().equals(idServ)){
                                lstHoras=lstServicio.get(i).getDisponibilidadHoras();
                                for(int j=0;j<lstHoras.size();j++){
                                    if(String.valueOf(lstHoras.get(j)).equals("7")){
                                        Csietenueveam.setChecked(true);
                                    }
                                    if(String.valueOf(lstHoras.get(j)).equals("9")){
                                        Cnueveonceam.setChecked(true);
                                    }
                                    if(String.valueOf(lstHoras.get(j)).equals("11")){
                                        Conceunapm.setChecked(true);
                                    }
                                    if(String.valueOf(lstHoras.get(j)).equals("14")){
                                        Cdoscuatropm.setChecked(true);
                                    }
                                    if(String.valueOf(lstHoras.get(j)).equals("16")){
                                        Ccuatroseispm.setChecked(true);
                                    }
                                    if(String.valueOf(lstHoras.get(j)).equals("18")){
                                        Cseisochopm.setChecked(true);
                                    }
                                }
                                lstDias=lstServicio.get(i).getDisponibilidadDias();
                                for(int j=0;j<lstDias.size();j++){
                                    if(String.valueOf(lstDias.get(j)).equals("1")){
                                        lunes.setChecked(true);
                                    }
                                    if(String.valueOf(lstDias.get(j)).equals("2")){
                                        martes.setChecked(true);
                                    }
                                    if(String.valueOf(lstDias.get(j)).equals("3")){
                                        miercoles.setChecked(true);
                                    }
                                    if(String.valueOf(lstDias.get(j)).equals("4")){
                                        jueves.setChecked(true);
                                    }
                                    if(String.valueOf(lstDias.get(j)).equals("5")){
                                        viernes.setChecked(true);
                                    }
                                    if(String.valueOf(lstDias.get(j)).equals("6")){
                                        sabado.setChecked(true);
                                    }
                                    if(String.valueOf(lstDias.get(j)).equals("7")){
                                        domingo.setChecked(true);
                                    }
                                }
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
}
