package innovatech.smartservices.activities;

import android.app.ProgressDialog;
import android.content.Intent;
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
import innovatech.smartservices.fragments.ServiciosDestacadosFragment;
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
    Button registrar;
    private ProgressDialog nProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        mAuth = FirebaseAuth.getInstance();
        nProgressDialog = new ProgressDialog(this);
        nombre = (EditText)findViewById(R.id.nombreRegister);
        password = (EditText)findViewById(R.id.passwordRegister);
        email = (EditText)findViewById(R.id.emailRegister);
        cedula = (EditText)findViewById(R.id.cedulaRegister);
        telefono = (EditText)findViewById(R.id.telefonoRegister);
        direccion = (EditText)findViewById(R.id.direccionRegister);
        ciudad = (EditText)findViewById(R.id.ciudadRegister);
        registrar=(Button)findViewById(R.id.reg_usuario);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }
    private void registrarUsuario(){
        final String emailTxt = email.getText().toString().trim();
        String passwordTxt = password.getText().toString().trim();
        final String nombreTxt = nombre.getText().toString().trim();
        final String cedulaTxt = cedula.getText().toString().trim();
        final String telefonoTxt = telefono.getText().toString().trim();
        final String direccionTxt = direccion.getText().toString().trim();
        final String ciudadTxt = ciudad.getText().toString().trim();
        if(TextUtils.isEmpty(emailTxt) || TextUtils.isEmpty(passwordTxt) || TextUtils.isEmpty(nombreTxt) || TextUtils.isEmpty(cedulaTxt)
                || TextUtils.isEmpty(telefonoTxt) || TextUtils.isEmpty(direccionTxt) || TextUtils.isEmpty(ciudadTxt)){
            Toast.makeText(this, "Tiene que ingresar todos los datos para registrarse !", Toast.LENGTH_SHORT).show();
        }
        else{
            nProgressDialog.setMessage("Registrando usuario...");
            nProgressDialog.show();
            System.out.println("Este es el email ------------------------------> "+emailTxt);
            System.out.println("Esta es el password ---------------------------------> "+passwordTxt);
            mAuth.createUserWithEmailAndPassword(emailTxt, passwordTxt)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                String idUsu = user.getUid();
                                Usuario usuario = new Usuario(idUsu,nombreTxt,cedulaTxt,ciudadTxt,direccionTxt,telefonoTxt,emailTxt);
                                FirebaseDatabase.getInstance().getReference("users").child(idUsu).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //progressbar.setVisibility(View.GONE);
                                        if(task.isSuccessful()){
                                            nProgressDialog.dismiss();
                                            Toast.makeText(RegistrarUsuarioActivity.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                                            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                                            for(int i=0;i<fm.getBackStackEntryCount();i++){
                                                fm.popBackStack();
                                            }
                                            Intent intent = new Intent(RegistrarUsuarioActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            //updateUI(user);

                                        }
                                        else{
                                            nProgressDialog.dismiss();
                                            Toast.makeText(RegistrarUsuarioActivity.this, "Hubo un error al crear el usuario", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                nProgressDialog.dismiss();
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegistrarUsuarioActivity.this, "Fallo la creacion, intente colocar su contraseña de almenos 6 digitos y un email valido",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }


                        }
                    });
        }
    }
}
