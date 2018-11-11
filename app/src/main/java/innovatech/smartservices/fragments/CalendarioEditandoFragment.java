package innovatech.smartservices.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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

    private CheckBox CsietenueveamEdit;
    private CheckBox CnueveonceamEdit;
    private CheckBox ConceunapmEdit;
    private CheckBox CdoscuatropmEdit;
    private CheckBox CcuatroseispmEdit;
    private CheckBox CseisochopmEdit;

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

        CsietenueveamEdit = (CheckBox) view.findViewById(R.id.checkBox_c_7a9);
        CnueveonceamEdit = view.findViewById(R.id.checkBox_c_9a11);
        ConceunapmEdit = view.findViewById(R.id.checkBox_c_11a1);
        CdoscuatropmEdit = view.findViewById(R.id.checkBox_c_2a4);
        CcuatroseispmEdit = view.findViewById(R.id.checkBox_c_4a6);
        CseisochopmEdit = view.findViewById(R.id.checkBox_c_6a8);
        cargarInfoAnterior(savedInstanceState,view,mAuth);
        botoncito.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!lunes.isChecked() && !martes.isChecked() && !miercoles.isChecked() && !jueves.isChecked() && !viernes.isChecked() && !sabado.isChecked() && !domingo.isChecked()){
                    Toast.makeText(getActivity(), "Debe seleccionar almenos un dia de disponibilidad", Toast.LENGTH_SHORT).show();
                }
                else if(!CsietenueveamEdit.isChecked() && !CnueveonceamEdit.isChecked() && !ConceunapmEdit.isChecked() && !CdoscuatropmEdit.isChecked() && !CcuatroseispmEdit.isChecked() && !CseisochopmEdit.isChecked() ){
                    Toast.makeText(getActivity(), "Debe seleccionar almenos una franja horaria de disponibilidad", Toast.LENGTH_SHORT).show();
                }
                else{
                    Bundle bundle = getArguments();
                    Servicio servAux=new Servicio();
                    if(lunes.isChecked()){
                        servAux.addDias(1);
                    }
                    if(martes.isChecked()){
                        servAux.addDias(2);
                    }
                    if(miercoles.isChecked()){
                        servAux.addDias(3);
                    }
                    if(jueves.isChecked()){
                        servAux.addDias(4);
                    }
                    if(viernes.isChecked()){
                        servAux.addDias(5);
                    }
                    if(sabado.isChecked()){
                        servAux.addDias(6);
                    }
                    if(domingo.isChecked()){
                        servAux.addDias(7);
                    }
//--------------------------------------------------------------------------------------------------

                    if(CsietenueveamEdit.isChecked()){
                        servAux.addHoras(7);
                        servAux.addHoras(8);
                    }
                    if(CnueveonceamEdit.isChecked()){
                        servAux.addHoras(9);
                        servAux.addHoras(10);
                    }
                    if(ConceunapmEdit.isChecked()){
                        servAux.addHoras(11);
                        servAux.addHoras(12);
                    }
                    if(CdoscuatropmEdit.isChecked()){
                        servAux.addHoras(14);
                        servAux.addHoras(15);
                    }
                    if(CcuatroseispmEdit.isChecked()){
                        servAux.addHoras(16);
                        servAux.addHoras(17);
                    }
                    if(CseisochopmEdit.isChecked()){
                        servAux.addHoras(18);
                        servAux.addHoras(19);
                    }

                    FirebaseDatabase.getInstance().getReference("servicios").child(bundle.getString("idServicio")).child("disponibilidadDias").setValue(servAux.getDisponibilidadDias()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //progressbar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Servicio actualizado", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText( getActivity(),"Hubo un error al editar el servicio", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                    FirebaseDatabase.getInstance().getReference("servicios").child(bundle.getString("idServicio")).child("disponibilidadHoras").setValue(servAux.getDisponibilidadHoras()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //progressbar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Servicio actualizado", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText( getActivity(),"Hubo un error al editar el servicio", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
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
        db.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                        CsietenueveamEdit.setChecked(true);
                                    }
                                    if(String.valueOf(lstHoras.get(j)).equals("9")){
                                        CnueveonceamEdit.setChecked(true);
                                    }
                                    if(String.valueOf(lstHoras.get(j)).equals("11")){
                                        ConceunapmEdit.setChecked(true);
                                    }
                                    if(String.valueOf(lstHoras.get(j)).equals("14")){
                                        CdoscuatropmEdit.setChecked(true);
                                    }
                                    if(String.valueOf(lstHoras.get(j)).equals("16")){
                                        CcuatroseispmEdit.setChecked(true);
                                    }
                                    if(String.valueOf(lstHoras.get(j)).equals("18")){
                                        CseisochopmEdit.setChecked(true);
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
