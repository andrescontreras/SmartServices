package innovatech.smartservices.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import innovatech.smartservices.R;
import innovatech.smartservices.models.Servicio;

public class ServicioSolicitarFinalFragment extends Fragment {
    TextView titulo;
    TextView precio;
    ImageView imagen ;
    Servicio serv ;
    Bundle bundle;
    Spinner horas;
    Spinner pagos;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_servicio_solicitar_final, container, false);
        titulo = (TextView)view.findViewById(R.id.txtSolicFinalTitulo);
        precio = (TextView) view.findViewById(R.id.txtSolicFinalPrecio);
        imagen = (ImageView) view.findViewById(R.id.txtSolicFinalImagen);
        horas = (Spinner)view.findViewById(R.id.spinSolicFinalHoras);
        pagos = (Spinner)view.findViewById(R.id.spinSolicFinalPagos);
        bundle = getArguments();
        serv = (Servicio) bundle.getSerializable("servicio");
        titulo.setText(serv.getNombre());
        precio.setText(String.valueOf(serv.getPrecio()));
        Picasso.with(getContext()).load(Uri.parse(bundle.getString("imagenIni"))).into(imagen);

        ArrayAdapter<Integer> adapterElem = new ArrayAdapter<Integer>(getActivity(),android.R.layout.simple_spinner_dropdown_item,serv.getDisponibilidadHoras());
        horas.setAdapter(adapterElem);
        return view;
    }
}
