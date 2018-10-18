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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import innovatech.smartservices.R;

public class CalendarioFragment extends Fragment {
    //CalendarView calendar ;
    Button botoncito;


    Boolean sietenueveam;
    Boolean nueveonceam;
    Boolean onceunapm;
    Boolean doscuatropm;
    Boolean cuatroseispm;
    Boolean seisochopm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calendario_servicio_cs, container, false);
        botoncito = (Button)view.findViewById(R.id.button_calendario);

        final CheckBox lunes= (CheckBox) view.findViewById(R.id.checkBox_c_lunes);
        final CheckBox martes= (CheckBox) view.findViewById(R.id.checkBox_c_martes);
        final CheckBox miercoles= (CheckBox) view.findViewById(R.id.checkBox_c_miercoles);
        final CheckBox jueves= (CheckBox) view.findViewById(R.id.checkBox_c_jueves);
        final CheckBox viernes= (CheckBox) view.findViewById(R.id.checkBox_c_viernes);
        final CheckBox sabado= (CheckBox) view.findViewById(R.id.checkBox_c_sabado);
        final CheckBox domingo= (CheckBox) view.findViewById(R.id.checkBox_c_domingo);

        final CheckBox sietenueveam= (CheckBox) view.findViewById(R.id.checkBox_c_7a9);
        final CheckBox nueveonceam= view.findViewById(R.id.checkBox_c_9a11);
        final CheckBox onceunapm= view.findViewById(R.id.checkBox_c_11a1);
        final CheckBox doscuatropm= view.findViewById(R.id.checkBox_c_2a4);
        final CheckBox cuatroseispm= view.findViewById(R.id.checkBox_c_4a6);
        final CheckBox seisochopm= view.findViewById(R.id.checkBox_c_6a8);
        botoncito.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!lunes.isChecked() && !martes.isChecked() && !miercoles.isChecked() && !jueves.isChecked() && !viernes.isChecked() && !sabado.isChecked() && !domingo.isChecked()){
                    Toast.makeText(getActivity(), "Debe seleccionar almenos un dia de disponibilidad", Toast.LENGTH_SHORT).show();
                }
                else if(!sietenueveam.isChecked() && !nueveonceam.isChecked() && !onceunapm.isChecked() && !doscuatropm.isChecked() && !cuatroseispm.isChecked() && !seisochopm.isChecked() ){
                    Toast.makeText(getActivity(), "Debe seleccionar almenos una franja horaria de disponibilidad", Toast.LENGTH_SHORT).show();
                }
                else{
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    PubPosicionamientoFragment pubPosicionamiento = new PubPosicionamientoFragment();
                    ft.replace(R.id.fragment_container, pubPosicionamiento);
                    ft.addToBackStack(null);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("lunes",lunes.isChecked());
                    bundle.putBoolean("martes",martes.isChecked());
                    bundle.putBoolean("miercoles",miercoles.isChecked());
                    bundle.putBoolean("jueves",jueves.isChecked());
                    bundle.putBoolean("viernes",viernes.isChecked());
                    bundle.putBoolean("sabado",sabado.isChecked());
                    bundle.putBoolean("domingo",domingo.isChecked());
                    
                    bundle.putBoolean("7a9",sietenueveam.isChecked());
                    bundle.putBoolean("9a11",nueveonceam.isChecked());
                    bundle.putBoolean("11a1",onceunapm.isChecked());
                    bundle.putBoolean("2a4",doscuatropm.isChecked());
                    bundle.putBoolean("4a6",cuatroseispm.isChecked());
                    bundle.putBoolean("6a8",seisochopm.isChecked());

                    pubPosicionamiento.setArguments(bundle);
                    ft.commit();
                }
            }
        });
        return view;
    }
}
