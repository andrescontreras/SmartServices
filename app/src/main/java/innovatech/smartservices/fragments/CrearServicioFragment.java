package innovatech.smartservices.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import innovatech.smartservices.R;

public class CrearServicioFragment extends Fragment {
    Button sig;
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
    }
}
