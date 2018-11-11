package innovatech.smartservices.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import innovatech.smartservices.R;

public class FiltroBusquedaFragment extends Fragment {
    Spinner spin_categoria;
    Spinner spin_dia;
    Spinner spin_hora;
    EditText precio;
    EditText distancia_km;
    Button buscar;
    Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtro_busqueda, container, false);
        spin_categoria = (Spinner)view.findViewById(R.id.spin_busqueda_categoria);
        spin_dia = (Spinner)view.findViewById(R.id.spin_busqueda_dia);
        spin_hora = (Spinner)view.findViewById(R.id.spin_busqueda_hora);
        precio = (EditText)view.findViewById(R.id.txt_busqueda_precio);
        distancia_km = (EditText)view.findViewById(R.id.txt_busqueda_rango);
        buscar = (Button)view.findViewById(R.id.btn_busqueda_buscar);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoria = (String)spin_categoria.getSelectedItem();
                String dia= (String)spin_dia.getSelectedItem();
                String hora = (String)spin_hora.getSelectedItem();
                String precioStr = precio.getText().toString();
                String distanciaStr = distancia_km.getText().toString();
                int precioInt;
                int distancia;
                if(precioStr.equals(""))
                    precioInt=-1;
                else
                    precioInt = Integer.parseInt(precio.getText().toString());
                if(distanciaStr.equals(""))
                    distancia=-1;
                else
                    distancia = Integer.parseInt(distancia_km.getText().toString());
                bundle = new Bundle();
                bundle.putString("categoria",categoria);
                bundle.putString("dia",dia);
                bundle.putString("hora",hora);
                bundle.putInt("precio",precioInt);
                bundle.putInt("distancia",distancia);
                if(verificarDatos(categoria,dia,hora,precioStr,distanciaStr)){
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    FiltroResultadosFragment resultados = new FiltroResultadosFragment();
                    ft.replace(R.id.fragment_container, resultados);
                    resultados.setArguments(bundle);
                    ft.addToBackStack(null);
                    ft.commit();
                }else{
                    Toast.makeText(getActivity(), "Debe colocar al menos un filtro para la busqueda", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    public boolean verificarDatos(String categoria, String dia, String hora, String precio, String distancia){
        if(categoria.equals("Seleccionar categoria") && dia.equals("Seleccionar dia") && hora.equals("Seleccionar hora") &&
                precio.equals("") && distancia.equals("")){
            return false;
        }else{
            return true;
        }
    }
}
