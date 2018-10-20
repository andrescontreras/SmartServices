package innovatech.smartservices.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import innovatech.smartservices.R;

public class ServiciosDestacadosFragment extends Fragment {
    Button prueba;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_destacados_servicio, container, false);
        prueba= view.findViewById(R.id.prueba);
        prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                PruebaImagenFragment detallesServ = new PruebaImagenFragment();
                ft.replace(R.id.fragment_container, detallesServ);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }
}
