package innovatech.smartservices.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import innovatech.smartservices.R;

public class ServicioSolicitarDiaFragment extends Fragment {
    TextView titulo;
    TextView precio;
    ImageView imagen ;
    CalendarView calendario;
    Button siguiente;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_servicio_solicitar_dia, container, false);
        titulo = (TextView)view.findViewById(R.id.txtSolicDiaTitulo);
        precio = (TextView) view.findViewById(R.id.txtSolicDiaPrecio);
        imagen = (ImageView) view.findViewById(R.id.txtSolicDiaImagen);
        calendario = (CalendarView)view.findViewById(R.id.calendarSolicDia);
        siguiente = (Button) view.findViewById(R.id.btnSolicDiaSig);
        accionBotones();
        return view;
    }
    public void accionBotones(){
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
