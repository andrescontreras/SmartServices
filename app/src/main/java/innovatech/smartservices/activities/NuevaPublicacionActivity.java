package innovatech.smartservices.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import innovatech.smartservices.R;

public class NuevaPublicacionActivity extends AppCompatActivity {

    EditText descripcion;
    EditText fechaActivacion;
    EditText imagenes;
    EditText nombre;
    EditText posicionamiento;
    EditText precio;
    EditText promedioCalificaciones;
    EditText tipo;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pub_categoria_servicio);
        //tipo =(EditText)findViewById(R.layout.);

    }
/*
    private void crearPublicacion(){
        final String descripcion=descripcion.getText().toString().trim();
        final int fechaActivacion=descripcion.getText().toString().trim();
        final List imagenes=descripcion.getText().toString().trim();
        final String nombre=descripcion.getText().toString().trim();
        final Boolean posicionamiento=descripcion.getText().toString().trim();
        final int precio=descripcion.getText().toString().trim();
        final int promedioCalificaciones=descripcion.getText().toString().trim();
        final String tipo=descripcion.getText().toString().trim();
        if(TextUtils.isEmpty(descripcion) || TextUtils.isEmpty(fechaActivacion) || TextUtils.isEmpty(imagenes) || TextUtils.isEmpty(nombre)
                || TextUtils.isEmpty(posicionamiento) || TextUtils.isEmpty(precio) || TextUtils.isEmpty(promedioCalificaciones) || TextUtils.isEmpty(tipo)){
            Toast.makeText(this, "Tiene que ingresar todos los datos para registrarse !", Toast.LENGTH_SHORT).show();
        }
        else{

        }

    }*/
}
