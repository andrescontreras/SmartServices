package innovatech.smartservices.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import innovatech.smartservices.R;

public class CrearServicioFragment extends Fragment {
    Button sig;
    Spinner spinner_nv1;
    Spinner spinner_nv2;
    Spinner spinner_nv3;
    Spinner spinner_nv4;

    ConstraintLayout c_layout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_categoria_servicio_cs, container, false);
        inicializar(view);
        accionBotones(view);
        return view;
    }
    private void inicializar(View view){
        sig = (Button)view.findViewById(R.id.btn_sig_infoBasica);
        spinner_nv1 = (Spinner)view.findViewById(R.id.spin_servicio_nivel1);
        spinner_nv2 = view.findViewById(R.id.spin_servicio_nivel2);
        spinner_nv3 = view.findViewById(R.id.spin_servicio_nivel3);
        spinner_nv4 = view.findViewById(R.id.spin_servicio_nivel4);
        c_layout = view.findViewById(R.id.constraint_categoria);
    }
    private void accionBotones(View view){
        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                InfoBasicaCSFragment infoBasica = new InfoBasicaCSFragment();
                ft.replace(R.id.fragment_container, infoBasica);
                ft.addToBackStack(null);
                //notificacion.setArguments(bundle);
                ft.commit();
            }
        });
        spinner_nv1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!adapterView.getItemAtPosition(i).equals("Seleccionar categoria")){
                    String nivel = (String)spinner_nv1.getSelectedItem();
                    Toast.makeText(getActivity(), "Se selecciono el item "+nivel, Toast.LENGTH_SHORT).show();

                    ArrayList<String> elementos = spinnerNv2(nivel);
                    ArrayAdapter<String> adapterElem = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,elementos);
                    spinner_nv2.setAdapter(adapterElem);
                    //Opcion -> Colocar los spinner que necesite en el XML y colocar la propiedad Visible = invisible y cuando los necesite los hago visibles
                    spinner_nv2.setVisibility(View.VISIBLE);
                    spinner_nv3.setVisibility(View.INVISIBLE);
                    spinner_nv4.setVisibility(View.INVISIBLE);
                    //c_layout.addView(spinner_nv2);
                }else{
                    spinner_nv2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_nv2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!adapterView.getItemAtPosition(i).equals("Seleccionar categoria")){
                    spinner_nv3.setVisibility(View.VISIBLE);
                    spinner_nv4.setVisibility(View.INVISIBLE);
                    String nivel = (String)spinner_nv1.getSelectedItem();
                    ArrayList<String> elementos = spinnerNv2(nivel);
                    ArrayAdapter<String> adapterElem = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,elementos);
                    spinner_nv2.setAdapter(adapterElem);
                }
                else{
                    spinner_nv3.setVisibility(View.INVISIBLE);
                    spinner_nv4.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_nv3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!adapterView.getItemAtPosition(i).equals("Seleccionar categoria")){
                    spinner_nv4.setVisibility(View.VISIBLE);
                    String nivel = (String)spinner_nv1.getSelectedItem();
                    ArrayList<String> elementos = spinnerNv2(nivel);
                    ArrayAdapter<String> adapterElem = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,elementos);
                    spinner_nv2.setAdapter(adapterElem);
                }
                else{
                    spinner_nv4.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public ArrayList<String> spinnerNv2(String categoria){
        ArrayList<String> elementos = new ArrayList<String>();
        elementos.add("Seleccionar categoria");
        switch (categoria){
            case "Belleza":
                elementos.add("Centros estéticos");
                elementos.add("Cosmetología");
                elementos.add("Peluquerías");
                elementos.add("Spa y masajes");
                elementos.add("Otros");
            case "Clases":
                elementos.add("Idiomas");
                elementos.add("Cocina");
                elementos.add("Conduccion");
                elementos.add("Deportes");
                elementos.add("Música");
                elementos.add("Yoga");
                elementos.add("Otros");
            case "Fiestas y eventos":
                elementos.add("Carros para eventos");
                elementos.add("Decoración");
                elementos.add("Entretenimiento");
                elementos.add("Moviliario");
                elementos.add("Salones para eventos");
                elementos.add("Servicios audiovisuales");
                elementos.add("Otros");
            case "Hogar":
                elementos.add("Construcción");
                elementos.add("Mantenimiento");
                elementos.add("Otros");
            case "Mantenimiento de vehiculos":
                elementos.add("Pintura");
                elementos.add("Lavado de vehiculos");
                elementos.add("Mecánica");
                elementos.add("Montallantas");
                elementos.add("Otros");
            case "Salud":
                elementos.add("Examenes clínicos");
                elementos.add("Servicios de ortopedia");
                elementos.add("Otros");
            case "Profesionales":
                elementos.add("Uno");
                elementos.add("XD");
                elementos.add("C mamut");
            case "Reparaciones e instalaciones":
                elementos.add("Seleccionar categoria");
                elementos.add("Uno");
                elementos.add("XD");
                elementos.add("C mamut");
            case "Ropa":
                elementos.add("Seleccionar categoria");
                elementos.add("Uno");
                elementos.add("XD");
                elementos.add("C mamut");
            case "Mascotas":
                elementos.add("Seleccionar categoria");
                elementos.add("Uno");
                elementos.add("XD");
                elementos.add("C mamut");
            case "Transporte":
                elementos.add("Seleccionar categoria");
                elementos.add("Uno");
                elementos.add("XD");
                elementos.add("C mamut");
            case "Viajes":
                elementos.add("Seleccionar categoria");
                elementos.add("Uno");
                elementos.add("XD");
                elementos.add("C mamut");
            case "Otros":
                elementos.add("Seleccionar categoria");
                elementos.add("Uno");
                elementos.add("XD");
                elementos.add("C mamut");
             default:
                 elementos.add("No se encontro información");
        }
        return elementos;
    }
}
