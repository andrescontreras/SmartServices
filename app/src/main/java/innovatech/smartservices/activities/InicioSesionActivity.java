package innovatech.smartservices.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import innovatech.smartservices.R;
import innovatech.smartservices.fragments.ServiciosDestacadosFragment;

public class InicioSesionActivity extends AppCompatActivity {

    private ProgressDialog nProgressDialog;

    Button registrar;
    EditText nombre;
    EditText cedula;
    private DatabaseReference Database;

    Button registrar_usuario;
    Button ingresar;
    EditText email;
    EditText password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        nProgressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        registrar_usuario = (Button)findViewById(R.id.botonRUsuario);
        ingresar = (Button)findViewById(R.id.botonIngresar);
        email= (EditText)findViewById(R.id.emailTxt);
        password= (EditText)findViewById(R.id.passwordTxt);
        registrar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (InicioSesionActivity.this,RegistrarUsuarioActivity.class);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){ //Cuando el usuario ya esta logeado, mandarlo a la actividad principal
            //updateUI(currentUser);
        }
        //Si no mandarlo a la pagina de login
    }


    private void ingresar(){
        String emailTxt = email.getText().toString().trim();
        String passwordTxt = password.getText().toString().trim();
        if(TextUtils.isEmpty(emailTxt) && TextUtils.isEmpty(passwordTxt)){
            Toast.makeText(this, "Ingrese un email y contraseña", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(emailTxt)){
            Toast.makeText(this, "Ingrese un email", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(passwordTxt)){
            Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
        }else{
            nProgressDialog.setMessage("Iniciando sesión...");
            nProgressDialog.show();
            mAuth.signInWithEmailAndPassword(emailTxt, passwordTxt)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //FirebaseUser user = mAuth.getCurrentUser();
                                nProgressDialog.dismiss();
                                Toast.makeText(InicioSesionActivity.this, "Inició sesión exitosamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(InicioSesionActivity.this,MainActivity.class);
                                startActivity(intent);
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                nProgressDialog.dismiss();
                                Toast.makeText(InicioSesionActivity.this, "Ingreso mal sus datos o no tiene existe una cuenta con los datos ingresados",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }
}
