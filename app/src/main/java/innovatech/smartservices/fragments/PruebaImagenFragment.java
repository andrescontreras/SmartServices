package innovatech.smartservices.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.adapters.ImageAdapter;

public class PruebaImagenFragment extends Fragment {
    RecyclerView recycler;
    private ImageAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_prueba_imagen, container, false);
        recycler = (RecyclerView)view.findViewById(R.id.recycler_prueba);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        Bundle bundle = getArguments();
        List<String> listaImagenes = bundle.getStringArrayList("imagenes");
        List<Uri> uri = new ArrayList<Uri>();
        System.out.println("Imprimo primer elemento de la lista de imagenes -------------------------------------->>>>>>>>>>>>> "+listaImagenes.get(0));
        uri.add(Uri.parse(listaImagenes.get(0)));
        mAdapter = new ImageAdapter(getActivity(),uri);
        recycler.setAdapter(mAdapter);
        return view;
    }
}
