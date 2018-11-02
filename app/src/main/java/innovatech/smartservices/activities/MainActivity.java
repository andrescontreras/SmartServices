package innovatech.smartservices.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.fragments.ServiciosDestacadosFragment;
import innovatech.smartservices.models.Servicio;
import innovatech.smartservices.models.Usuario;
import innovatech.smartservices.fragments.CuentaFragment;
import innovatech.smartservices.fragments.NotificacionesFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer ;
    private FirebaseAuth mAuth;
    TextView nombreHeader;
    TextView emailHeader;
    ImageButton imgUsuario;
    ArrayList<Servicio> listaServicios = new ArrayList<Servicio>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);

        //Compontentes del header del navigation
        nombreHeader = (TextView) hView.findViewById(R.id.header_nombre);
        emailHeader = (TextView) hView.findViewById(R.id.header_email);
        imgUsuario = (ImageButton)hView.findViewById(R.id.header_imagen);
        imgUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,InicioSesionActivity.class);
                startActivity(intent);
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ServiciosDestacadosFragment()).commit();
    }

    //Metodo se activa cuando se selecciona un item del drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_inicio:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ServiciosDestacadosFragment()).commit();
                break;
            case R.id.nav_account:
                if(verificarSesion()){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new CuentaFragment()).commit();
                }else{
                    Toast.makeText(this, "Debe iniciar sesion para entrar a Cuenta", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_notificaciones:
                if(verificarSesion()){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new NotificacionesFragment()).commit();
                }else{
                    Toast.makeText(this, "Debe iniciar sesion para entrar a Notificaciones", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_favoritos:
                if(verificarSesion()){
                    Toast.makeText(this, "Oprimio favoritos", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Debe iniciar sesion para entrar a Favoritos", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_exit:
                FirebaseAuth.getInstance().signOut();
                nombreHeader.setText("Usuario visitante");
                emailHeader.setText("");
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){ //Cuando el usuario ya esta logeado, mandarlo a la actividad principal
            //updateUI(currentUser);
            informacionUsuarioDrawer();
        }
        else{
            nombreHeader.setText("Usuario visitante");
            emailHeader.setText("");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ServiciosDestacadosFragment()).commit();

        }
        //Si no mandarlo a la pagina de login
    }
    public boolean verificarSesion(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){ //Cuando el usuario ya esta logeado, mandarlo a la actividad principal
            return true;
        }
        return false;
    }
    private void informacionUsuarioDrawer(){

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
                    nombreHeader.setText(usr.getNombre());
                    emailHeader.setText(usr.getEmail());
                }
                else{
                    Toast.makeText(MainActivity.this, "Hubo un problema encontrando el uid", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

}
