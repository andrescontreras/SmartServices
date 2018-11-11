package innovatech.smartservices.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import innovatech.smartservices.R;

public class PubDetallesFragment extends Fragment {
    EditText incluye;
    EditText noIncluye;
    EditText adicional;
    Button boton;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pub_detalles_servicio, container, false);
        mAuth = FirebaseAuth.getInstance();
        boton = (Button)view.findViewById(R.id.boton_detalles);
        incluye = (EditText)view.findViewById(R.id.t_Incluye);
        noIncluye= (EditText)view.findViewById(R.id.t_noIncluye);
        adicional= (EditText)view.findViewById(R.id.t_Adicional);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(incluye.getText()) || TextUtils.isEmpty(noIncluye.getText()) || TextUtils.isEmpty(adicional.getText())){
                    Toast.makeText(getActivity(), "Debe ingresar que incluye, que no incluye, y los datos adicionales del servicio", Toast.LENGTH_SHORT).show();
                }
                else {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    PubUbicacionFragment ubicacionfragm = new PubUbicacionFragment();
                    ft.replace(R.id.fragment_container, ubicacionfragm);
                    ft.addToBackStack(null);
                    Bundle bundle = getArguments();
                    bundle.putString("incluye", incluye.getText().toString());
                    bundle.putString("noIncluye", noIncluye.getText().toString());
                    bundle.putString("adicional", adicional.getText().toString());
                    ubicacionfragm.setArguments(bundle);
                    //notificacion.setArguments(bundle);
                    ft.commit();
                }
            }
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){ //Cuando el usuario ya esta logeado, mandarlo a la actividad principal
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ServiciosDestacadosFragment servDest= new ServiciosDestacadosFragment();
            ft.replace(R.id.fragment_container, servDest);
            ft.commit();
        }
    }
}
