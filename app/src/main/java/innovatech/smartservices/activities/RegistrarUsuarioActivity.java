package innovatech.smartservices.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import innovatech.smartservices.R;
import innovatech.smartservices.models.Usuario;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText nombre;
    EditText cedula;
    EditText email;
    EditText password;
    EditText telefono;
    EditText direccion;
    EditText ciudad;
    EditText barrio;
    Button registrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        mAuth = FirebaseAuth.getInstance();
        nombre = (EditText)findViewById(R.id.nombreRegister);
        password = (EditText)findViewById(R.id.passwordRegister);
        email = (EditText)findViewById(R.id.emailRegister);
        cedula = (EditText)findViewById(R.id.cedulaRegister);
        telefono = (EditText)findViewById(R.id.telefonoRegister);
        direccion = (EditText)findViewById(R.id.direccionRegister);
        ciudad = (EditText)findViewById(R.id.ciudadRegister);
        barrio = (EditText)findViewById(R.id.barrioRegister);
        registrar=(Button)findViewById(R.id.reg_usuario);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }
    private void registrarUsuario(){
        final String emailTxt = email.getText().toString();
        String passwordTxt = password.getText().toString();
        final String nombreTxt = nombre.getText().toString();
        final String cedulaTxt = cedula.getText().toString();
        final String telefonoTxt = telefono.getText().toString();
        final String direccionTxt = direccion.getText().toString();
        final String ciudadTxt = ciudad.getText().toString();
        final String barrioTxt = barrio.getText().toString();
        if(TextUtils.isEmpty(emailTxt) || TextUtils.isEmpty(passwordTxt) || TextUtils.isEmpty(nombreTxt) || TextUtils.isEmpty(cedulaTxt)
                || TextUtils.isEmpty(telefonoTxt) || TextUtils.isEmpty(direccionTxt) || TextUtils.isEmpty(ciudadTxt) || TextUtils.isEmpty(barrioTxt)){
            Toast.makeText(this, "Tiene que ingresar todos los datos para registrarse !", Toast.LENGTH_SHORT).show();
        }
        else{
            //progressbar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(emailTxt, passwordTxt)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // Sign in success, update UI with the signed-in user's information
                                int cedulaInt = Integer.parseInt(cedulaTxt);
                                int telefonoInt =Integer.parseInt(telefonoTxt);
                                Usuario usuario = new Usuario(nombreTxt,cedulaInt,ciudadTxt,direccionTxt,barrioTxt,telefonoInt,emailTxt);
                                FirebaseUser user = mAuth.getCurrentUser();
                                FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //progressbar.setVisibility(View.GONE);
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegistrarUsuarioActivity.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                                            //updateUI(user);

                                        }
                                        else{
                                            Toast.makeText(RegistrarUsuarioActivity.this, "Hubo un error al crear el usuario", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegistrarUsuarioActivity.this, "Fallo la creacion, intente colocar su contraseña de almenos 6 digitos y un email valido",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }
}
