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

public class CalendarioFragment extends Fragment {
    //CalendarView calendar ;
    Button botoncito;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calendario_servicio_cs, container, false);
        botoncito = (Button)view.findViewById(R.id.button_calendario);
        botoncito.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                PubInfoBasicaFragment infoBasica = new PubInfoBasicaFragment();
                ft.replace(R.id.fragment_container, infoBasica);
                ft.addToBackStack(null);
                //notificacion.setArguments(bundle);
                ft.commit();
            }
        });
        return view;
    }
}
