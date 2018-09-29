package innovatech.smartservices.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import innovatech.smartservices.R;
import innovatech.smartservices.Usuario;

public class EditarUsuarioFragment extends Fragment {
    EditText nombre;
    EditText cedula;
    EditText email;
    EditText telefono;
    EditText direccion;
    EditText ciudad;
    EditText barrio;
    Button cambiar_usu;
    FirebaseAuth mAuth ;
    DatabaseReference mDataBase;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_edit_usuario, container, false);
        //Cuando se oprima el boton, debo actualizar todos los cambios que hizo el usuario en las casillas y colocarlos en Firebase
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference("users");
        nombre = (EditText)view.findViewById(R.id.nombreEdit);
        cedula = (EditText)view.findViewById(R.id.cedulaEdit);
        email = (EditText)view.findViewById(R.id.emailEdit);
        telefono = (EditText)view.findViewById(R.id.telefonoEdit);
        direccion = (EditText)view.findViewById(R.id.direccionEdit);
        ciudad = (EditText)view.findViewById(R.id.ciudadEdit);
        barrio = (EditText)view.findViewById(R.id.barrioEdit);
        cambiar_usu = (Button)view.findViewById(R.id.cambiar_usuario);
        capturarInfoActual();

        cambiar_usu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarInformacion();
            }
        });

        return view;
    }
    private void capturarInfoActual(){
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        db.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    Usuario usr = dataSnapshot.getValue(Usuario.class);
                    //int cedula = dataSnapshot.child("cedula").getValue(Integer.class);
                    //String nombre = dataSnapshot.child("nombre").getValue(String.class);
                    System.out.println("ESTO ES EL NOMBREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE  "+ usr.getNombre());
                    System.out.println("ESTO ES EL EMAILLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL  "+ usr.getEmail());
                    nombre.setText(usr.getNombre());
                    cedula.setText(String.valueOf(usr.getCedula()));
                    email.setText(usr.getEmail());
                    telefono.setText(String.valueOf(usr.getTelefono()));
                    direccion.setText(usr.getDireccion());
                    ciudad.setText(usr.getCiudad());
                    barrio.setText(usr.getBarrio());
                }
                else{
                    Toast.makeText(getActivity(), "Hubo un problema encontrando el uid", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
    private void cambiarInformacion(){

        Usuario usu  = new Usuario(nombre.getText().toString(),Integer.parseInt(cedula.getText().toString()),ciudad.getText().toString(),
                direccion.getText().toString(),barrio.getText().toString(),Integer.parseInt(telefono.getText().toString()),
                email.getText().toString());
        mDataBase.child(mAuth.getCurrentUser().getUid()).setValue(usu);
        Toast.makeText(getActivity(), "Se realizaron los cambios", Toast.LENGTH_SHORT).show();
    }
}
