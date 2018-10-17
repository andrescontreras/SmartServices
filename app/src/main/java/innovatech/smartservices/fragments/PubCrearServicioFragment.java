package innovatech.smartservices.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import innovatech.smartservices.R;

public class PubCrearServicioFragment extends Fragment {
    Button sig;
    Spinner spinner_nv1;
    Spinner spinner_nv2;
    Spinner spinner_nv3;
    Spinner spinner_nv4;
    Boolean spinnerCompleto = false;
    ConstraintLayout c_layout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pub_categoria_servicio, container, false);
        inicializar(view);
        accionBotones(view);
        return view;
    }
    private void inicializar(View view){
        sig = (Button)view.findViewById(R.id.btn_sig_infoBasica);
        spinner_nv1 = (Spinner)view.findViewById(R.id.spin_servicio_nivel1);
        spinner_nv2 = view.findViewById(R.id.spin_servicio_nivel2);
        spinner_nv3 = view.findViewById(R.id.spin_servicio_nivel3);
        spinner_nv4 = view.findViewById(R.id.spin_servicio_nivel4);
        c_layout = view.findViewById(R.id.constraint_categoria);
    }
    private void accionBotones(View view){
        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nivel1 = (String)spinner_nv1.getSelectedItem();
                String nivel2 = (String)spinner_nv2.getSelectedItem();
                String nivel3 = (String)spinner_nv3.getSelectedItem();
                String nivel4 = (String)spinner_nv4.getSelectedItem();
                /*
                System.out.println("Elemento nivel 1 ------------------------> " + nivel1);
                System.out.println("Elemento nivel 2 --------------------------------------------> " + nivel2);
                System.out.println("Elemento nivel 3 -------------------------------------------------------------> " + nivel3);
                System.out.println("Elemento nivel 4 ------------------------------------------------------------------------------> "+ nivel4);
                */

                if((nivel2.equals("") || nivel3.equals("") || nivel4.equals("")) ||
                        (spinnerCompleto && !nivel4.equals("Seleccionar categoria"))){
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    PubInfoBasicaFragment infoBasica = new PubInfoBasicaFragment();
                    ft.replace(R.id.fragment_container, infoBasica);
                    ft.addToBackStack(null);
                    //Tal vez tenga que guardar en otras variables locales lo que me llega de fragmentos anteriores para mandarlos al siguiente
                    Bundle bundle = new Bundle();
                    bundle.putString("categorias",nivel1+nivel2+nivel3+nivel4);
                    infoBasica.setArguments(bundle);
                    ft.commit();
                }else{
                    Toast.makeText(getActivity(), "Tiene que seleccionar hasta la ultima subcategoria", Toast.LENGTH_SHORT).show();
                }
            }
        });
        spinner_nv1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerCompleto=false;
                if(!adapterView.getItemAtPosition(i).equals("Seleccionar categoria")){
                    String nivel = (String)spinner_nv1.getSelectedItem();
                    //Toast.makeText(getActivity(), "Se selecciono el item "+nivel, Toast.LENGTH_SHORT).show();
                    spinner_nv3.setVisibility(View.INVISIBLE);
                    spinner_nv4.setVisibility(View.INVISIBLE);
                    ArrayList<String> elementos = spinnerNv2(nivel);
                    if(elementos.size()<=2){
                        elementos.clear();
                        elementos.add("");
                        ArrayAdapter<String> adapterElem = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,elementos);
                        spinner_nv2.setAdapter(adapterElem);
                        spinner_nv3.setAdapter(adapterElem);
                        spinner_nv4.setAdapter(adapterElem);
                        spinner_nv2.setVisibility(View.INVISIBLE);
                    }else{
                        ArrayAdapter<String> adapterElem = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,elementos);
                        spinner_nv2.setAdapter(adapterElem); //Este es por funcionalidad
                        spinner_nv3.setAdapter(adapterElem);// Lo coloco para que sirva de filtro para no permitir pasar a la siguiente pantalla
                        spinner_nv4.setAdapter(adapterElem);// Lo coloco para que sirva de filtro para no permitir pasar a la siguiente pantalla
                        //Opcion -> Colocar los spinner que necesite en el XML y colocar la propiedad Visible = invisible y cuando los necesite los hago visibles
                        spinner_nv2.setVisibility(View.VISIBLE);

                    }

                }else{
                    spinner_nv2.setVisibility(View.INVISIBLE);
                    spinner_nv3.setVisibility(View.INVISIBLE);
                    spinner_nv4.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_nv2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerCompleto=false;
                if(!adapterView.getItemAtPosition(i).equals("Seleccionar categoria")){
                    spinner_nv3.setVisibility(View.VISIBLE);
                    spinner_nv4.setVisibility(View.INVISIBLE);
                    String nivel = (String)spinner_nv2.getSelectedItem();
                    //Toast.makeText(getActivity(), "Se selecciono el item "+nivel, Toast.LENGTH_SHORT).show();
                    ArrayList<String> elementos = spinnerNv3(nivel);
                    if(elementos.size()<=2){
                        elementos.clear();
                        elementos.add("");
                        ArrayAdapter<String> adapterElem = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,elementos);
                        spinner_nv3.setAdapter(adapterElem);
                        spinner_nv4.setAdapter(adapterElem);
                        spinner_nv3.setVisibility(View.INVISIBLE);
                    }else{
                        ArrayAdapter<String> adapterElem = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,elementos);
                        spinner_nv3.setAdapter(adapterElem);// Este es por funcionalidad
                        spinner_nv4.setAdapter(adapterElem);// Lo coloco para que sirva de filtro para no permitir pasar a la siguiente pantalla
                    }

                }
                else{
                    spinner_nv3.setVisibility(View.INVISIBLE);
                    spinner_nv4.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_nv3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerCompleto=false;
                if(!adapterView.getItemAtPosition(i).equals("Seleccionar categoria")){
                    spinner_nv4.setVisibility(View.VISIBLE);
                    String nivel = (String)spinner_nv3.getSelectedItem();
                    ArrayList<String> elementos = spinnerNv4(nivel);
                    if(elementos.size()<=2){
                        elementos.clear();
                        elementos.add("");
                        ArrayAdapter<String> adapterElem = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,elementos);
                        spinner_nv4.setAdapter(adapterElem);
                        spinner_nv4.setVisibility(View.INVISIBLE);
                    }else{
                        ArrayAdapter<String> adapterElem = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,elementos);
                        spinner_nv4.setAdapter(adapterElem);
                        spinnerCompleto=true;
                    }
                }
                else{
                    spinner_nv4.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public ArrayList<String> spinnerNv2(String categoria){
        ArrayList<String> elementos = new ArrayList<String>();
        elementos.add("Seleccionar categoria");
        switch (categoria){
            case "Belleza":
                elementos.add("Centros estéticos");
                elementos.add("Cosmetología");
                elementos.add("Peluquerías");
                elementos.add("Spa y masajes");
                elementos.add("Otros");
                break;
            case "Clases":
                elementos.add("Idiomas");
                elementos.add("Cocina");
                elementos.add("Conduccion");
                elementos.add("Deportes");
                elementos.add("Música");
                elementos.add("Yoga");
                elementos.add("Otros");
                break;
            case "Fiestas y eventos":
                elementos.add("Carros para eventos");
                elementos.add("Decoración");
                elementos.add("Entretenimiento");
                elementos.add("Mobiliario");
                elementos.add("Salones para eventos");
                elementos.add("Servicios audiovisuales");
                elementos.add("Otros");
                break;
            case "Hogar":
                elementos.add("Construcción");
                elementos.add("Mantenimiento");
                elementos.add("Otros");
                break;
            case "Mantenimiento de vehiculos":
                elementos.add("Pintura");
                elementos.add("Lavado de vehiculos");
                elementos.add("Mecánica");
                elementos.add("Montallantas");
                elementos.add("Otros");
                break;
            case "Salud":
                elementos.add("Examenes clínicos");
                elementos.add("Servicios de ortopedia");
                elementos.add("Otros");
                break;
            case "Profesionales":
                elementos.add("Abogados");
                elementos.add("Consultorias");
                elementos.add("Contadores");
                elementos.add("Diseñadores");
                elementos.add("Ingenieros de sistemas");
                elementos.add("Traductores");
                elementos.add("Otros");
                break;
            case "Reparaciones e instalaciones":
                elementos.add("Hogar y Oficina");
                elementos.add("Bodega");
                elementos.add("Fabrica");
                elementos.add("Tecnología");
                elementos.add("Otros");
                break;
            case "Ropa":
                elementos.add("Confección de uniformes");
                elementos.add("Diseñadores de moda");
                elementos.add("Estampados");
                elementos.add("Lavanderia y Tintorería");
                elementos.add("Otros");
                break;
            case "Mascotas":
                elementos.add("Adiestramiento");
                elementos.add("Recreación");
                elementos.add("Cuidado de animales");
                elementos.add("Otros");
                break;
            case "Transporte":
                elementos.add("Mudanzas");
                elementos.add("Mensajeria");
                elementos.add("Pasajeros");
                elementos.add("Otros");
                break;
            case "Viajes":
                elementos.add("Alquiler de carros");
                elementos.add("Hoteles");
                elementos.add("Paquetes turísticos");
                elementos.add("Guias");
                elementos.add("Otros");
                break;
            case "Otros":
                elementos.add("Alquiler de equipos");
                elementos.add("Comercio");
                elementos.add("Hosting y dominios");
                elementos.add("Planes de telefonía");
                elementos.add("Restaurantes");
                elementos.add("Otros");
                break;
            default:
                elementos.add("No se encuentran más subcategorias");
        }
        return elementos;
    }
    public ArrayList<String> spinnerNv3(String categoria){
        ArrayList<String> elementos = new ArrayList <String>();
        elementos.add("Seleccionar categoria");
        switch (categoria){
            case "Centros estéticos":
                elementos.add("Centros de bronceo");
                elementos.add("Depilaciones");
                elementos.add("Tratamientos corporales");
                elementos.add("Tratamientos faciales");
                elementos.add("Otros");
                break;
            case "Peluquerías":
                elementos.add("Estilistas");
                elementos.add("Manicure y Pedicure");
                elementos.add("Otros");
                break;
            case "Deportes":
                elementos.add("Futbol");
                elementos.add("Baloncesto");
                elementos.add("Tenis");
                elementos.add("Ping pong");
                elementos.add("Otros");
                break;
            case "Idiomas":
                elementos.add("Inglés");
                elementos.add("Japones");
                elementos.add("Mandarin");
                elementos.add("Alemán");
                elementos.add("Otros");
                break;
            case "Música":
                elementos.add("Guitarra");
                elementos.add("Piano");
                elementos.add("Bajo");
                elementos.add("Acordeon");
                elementos.add("Otros");
                break;
            case "Decoración":
                elementos.add("Arreglos florales");
                elementos.add("Decoración con globos");
                elementos.add("Tarjetas y recordatorios");
                elementos.add("Otros");
                break;
            case "Entretenimiento":
                elementos.add("Minitecas");
                elementos.add("Inflabes");
                elementos.add("Shows");
                elementos.add("Otros");
                break;
            case "Mobiliario":
                elementos.add("Mesas, sillas y mantelería");
                elementos.add("Otros");
                break;
            case "Servicios audiovisuales":
                elementos.add("DJ");
                elementos.add("Sonido e iluminación");
                elementos.add("Fotos y video");
                elementos.add("Proyectores y pantallas");
                elementos.add("Otros");
                break;
            case "Construcción":
                elementos.add("Especialistas");
                elementos.add("Maquinaria y herramientas");
                elementos.add("Otros");
                break;
            case "Mantenimiento":
                elementos.add("Decoración");
                elementos.add("Limpieza");
                elementos.add("Pisos");
                elementos.add("Seguridad");
                elementos.add("Otros");
                break;
            case "Hogar y Oficina":
                elementos.add("Electrodomésticos");
                elementos.add("Fotocopiadoras");
                elementos.add("Otros");
                break;
            case "Tecnología":
                elementos.add("Televisores");
                elementos.add("Celulares");
                elementos.add("Computadores");
                elementos.add("Otros");
                break;
            case "Adiestramiento":
                elementos.add("Adiestramiento canino");
                elementos.add("Paseador de perro");
                elementos.add("Otros");
                break;
            case "Cuidado de animales":
                elementos.add("Guarderías");
                elementos.add("Peluquerías caninas");
                elementos.add("Veterinaria");
                elementos.add("Otros");
                break;
            default:
                elementos.add("No se encuentran más subcategorias");
        }
        return elementos;
    }
    public ArrayList<String> spinnerNv4(String categoria){
        ArrayList<String> elementos = new ArrayList<String>();
        elementos.add("Seleccionar categoria");
        switch (categoria){
            case "Depilaciones":
                elementos.add("Depilación con cera");
                elementos.add("Depilación diodo");
                elementos.add("Depilación IPL");
                elementos.add("Otros");
                break;
            case "Shows":
                elementos.add("Show de baile");
                elementos.add("Músicos y cantantes");
                elementos.add("Shows infantiles");
                elementos.add("Otros");
                break;
            case "Maquinaria y herramientas":
                elementos.add("Bombas para concreto");
                elementos.add("Elevadores de construcción");
                elementos.add("Otros");
                break;
            case "Decoración":
                elementos.add("Restauración");
                elementos.add("Tapizados");
                elementos.add("Otros");
                break;
            case "Limpieza":
                elementos.add("Alfombras, tapetes y cortinas");
                elementos.add("Muebles");
                elementos.add("Otros");
                break;
            case "Pisos":
                elementos.add("Cristalización de marmol");
                elementos.add("Laminados");
                elementos.add("Pulido y lacado");
                elementos.add("Otros");
                break;
            case "Seguridad":
                elementos.add("Alarmas");
                elementos.add("Camaras de seguridad");
                elementos.add("Rejas y portones");
                elementos.add("Otros");
            default:
                elementos.add("No se encuentran más subcategorias");
        }
        return elementos;
    }
}
