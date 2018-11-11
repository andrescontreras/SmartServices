package innovatech.smartservices.fragments;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import innovatech.smartservices.R;
import innovatech.smartservices.helpers.EstadoReserva;
import innovatech.smartservices.models.Reserva;
import innovatech.smartservices.models.Servicio;
import innovatech.smartservices.models.Usuario;
import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;
import sun.bob.mcalendarview.vo.DateData;

public class ServicioSolicitarDiaFragment extends Fragment {
    TextView titulo;
    TextView precio;
    ImageView imagen ;
    MCalendarView calendario;
    Button reservar;
    Servicio serv ;
    Bundle bundle;
    Spinner horas;
    Spinner pagos;
    private FirebaseAuth mAuth;
    DatabaseReference mDataBase;
    List<DateData> marcados = new ArrayList<DateData>(); //Se encuentran todos los elementos dentro del MCalendarView que estan marcados por el punto verde
    List<DateData> disponibles = new ArrayList<DateData>(); //Se encuentran los dias disponibles del servicio
    List<Reserva> fechasReservadas = new ArrayList<Reserva>();  // Se encuentran todas las fechas reservados del servicio
    List<String> horasString = new ArrayList<String>();
    List<String> horasDisponibles = new ArrayList<String>();
    int mes;
    int anio;
    int dia;
    int hora;
    int horaSeleccionada;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_servicio_solicitar_dia, container, false);
        titulo = (TextView)view.findViewById(R.id.txtSolicDiaTitulo);
        precio = (TextView) view.findViewById(R.id.txtSolicDiaPrecio);
        imagen = (ImageView) view.findViewById(R.id.txtSolicDiaImagen);
        calendario = (MCalendarView)view.findViewById(R.id.calendarSolicDia);
        reservar = (Button) view.findViewById(R.id.btnSolicDiaReserva);
        horas = (Spinner)view.findViewById(R.id.spinSolicHoras);
        pagos = (Spinner)view.findViewById(R.id.spinSolicPagos);
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference("users");
        marcados = new ArrayList<DateData>();
        disponibles= new ArrayList<DateData>();
        fechasReservadas = new ArrayList<Reserva>();
        horasDisponibles = new ArrayList<String>();
        limpiarPuntos();
        bundle = getArguments();
        titulo.setText(bundle.getString("nombreServ"));
        precio.setText("$"+bundle.getString("precioServ"));
                Picasso.with(getContext()).load(Uri.parse(bundle.getString("imagenIni"))).into(imagen);
        serv = (Servicio) bundle.getSerializable("servicio");
        llenarReservas(serv.getId());
        accionBotones();
        return view;
    }
    public void cargarDatosIniciales(){
        List<String> inicio = new ArrayList<String>();
        inicio.add("Seleccionar hora");
        horasString = new ArrayList<String>();
        ArrayAdapter<String> adapterElem = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,inicio);
        horas.setAdapter(adapterElem);
        for(int i =0;i<serv.getDisponibilidadHoras().size();i++){
            horasString.add(String.valueOf(serv.getDisponibilidadHoras().get(i)));
        }
        anio = Calendar.getInstance().get(Calendar.YEAR); // Variable que contiene el año actual
        mes = Calendar.getInstance().get(Calendar.MONTH); // Variable que contiene el mes actual
        dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        System.out.println("El dia en el que estamos hoy ----------------------------------->>> "+dia);
        System.out.println("La hora en la que estamos actualmente  ----------------------------------->>> "+hora);
        Calendar mycal = new GregorianCalendar(anio,mes,1);
        int diasMes = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("AÑO->>>>>>>>>>>>>> "+anio+" MES----------->>>>>>>>> "+mes+" DIAS QUE TIENE EL MES ->>>>>>>>>>>>> "+diasMes);
        //System.out.println("El dia que estoy buscando es el ----------------------------->"+serv.getDisponibilidadDias().get(0));
        Calendar cal = Calendar.getInstance();
        DateData dateData;
        for(int i = 1 ;i<=diasMes;i++){
            cal.set(anio,mes,i);
            int dayweek =(cal.get(Calendar.DAY_OF_WEEK)-1);
            if(verificarDias(dayweek)){
                dateData = new DateData(anio,mes+1,i);
                if(i==dia){
                    if(hora<Integer.parseInt(horasString.get(horasString.size()-1))){
                        disponibles.add(dateData);
                        calendario.unMarkDate(dateData);
                        calendario.markDate(dateData.setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND,Color.BLUE)));
                    }
                }
                if(i>dia){
                    System.out.println("Esta es la fecha que meto --------------------------> "+dateData.toString());
                    disponibles.add(dateData);
                    calendario.unMarkDate(dateData);
                    calendario.markDate(dateData.setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND,Color.BLUE)));
                }else{
                    calendario.unMarkDate(dateData);
                }
            }
        }
        horas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!adapterView.getItemAtPosition(i).equals("Seleccionar hora")){
                    String seleccion = (String)horas.getSelectedItem();
                    horaSeleccionada = Integer.parseInt(seleccion);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        calendario.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {

                ArrayAdapter adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, Collections.emptyList());
                horas.setAdapter(adapter);
                System.out.println("Oprimo diaaaaaaaaaaaaaaaaa ->>>>>>>>>>>>>>>>>>>>>>>>>>>");
                horasDisponibles.clear();
                horasDisponibles.add("Seleccionar hora");
                System.out.println("Tamaño de horasDisponibles en setOnDateClick 111111 ----------------------->>>>>>>>>> "+horasDisponibles.size());
                if(esDiaDisponible(date) ){
                    if(!verificarMarcados(date)) {
                        if(marcados.size()==0){
                            horasDisponibles.addAll(horasString);
                            ArrayAdapter<String> adapterElem = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,horasDisponibles);
                            System.out.println("Tamaño de horasDisponibles en setOnDateClick 222222222 ----------------------->>>>>>>>>> "+horasDisponibles.size());
                            horas.setAdapter(adapterElem);
                            calendario.unMarkDate(date);
                            calendario.markDate(date.setMarkStyle(new MarkStyle(MarkStyle.DOT, Color.GREEN)));
                            marcados.add(date);
                        }else{
                            Toast.makeText(getActivity(), "Solo puede marcar un dia", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        horasDisponibles.clear();
                        horasDisponibles.add("Seleccionar hora");
                        ArrayAdapter<String> adapterElem = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,horasDisponibles);
                        horas.setAdapter(adapterElem);

                        calendario.unMarkDate(date);
                        calendario.markDate(date.setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND,Color.BLUE)));
                        marcados.remove(date);
                    }
                }else{
                    Toast.makeText(getActivity(), "Seleccione uno de los dias disponibles que estan marcados en azul", Toast.LENGTH_SHORT).show();
                }
            }
        });
        calendario.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                DateData changeMonth;
                if((year==anio && (month-1)>mes) || (year>anio)){
                    Calendar calendar = new GregorianCalendar(year,month-1,1);
                    int diaMesCambio = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); //Saco el numero de dias, del mes al que se cambio el MCalendarView
                    for(int j=0;j<diaMesCambio;j++){
                        calendar.set(year,month-1,j); // El -1 es porque en la Clase Calendar, los meses empiezan desde el 0
                        int diaSemanaCambio =  (calendar.get(Calendar.DAY_OF_WEEK)-1);
                        if(verificarDias(diaSemanaCambio)){
                            changeMonth = new DateData(year,month,j);
                                calendario.markDate(changeMonth.setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND,Color.BLUE)));
                                disponibles.add(changeMonth);
                        }
                    }
                }
            }
        });
    }
    public void accionBotones(){
        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nivelHoras = (String)horas.getSelectedItem();
                String nivelPagos = (String)pagos.getSelectedItem();
                if(marcados.size()>0 && !nivelHoras.equals("Seleccionar hora") && !nivelPagos.equals("Seleccionar medio de pago")){
                    subirReserva(serv);

                }else{
                    Toast.makeText(getActivity(), "Tiene que seleccionar almenos una fecha, una hora y un metodo de pago para realizar la solicitud de reserva", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //Verifica si el dia de la semana(Domingo(0), Lunes(1), Martes(1), ...) que llega como parametro, esta dentro de los dias que establecio el usuario como disponible
    public boolean verificarDias(int dayweek){
        if(dayweek==0)
            dayweek= 7;
        for(int i = 0 ;i<serv.getDisponibilidadDias().size();i++){
            if(serv.getDisponibilidadDias().get(i)==dayweek)
                return true;
        }
        return false;
    }
    //Verifica las fechas que fueron marcadas por el usuario
    public boolean verificarMarcados(DateData date){
        for(int i=0;i<marcados.size();i++){
            if(marcados.get(i).equals(date))
                return true;
        }
        return false;
    }
    public boolean esDiaDisponible(DateData date){
        for(int i=0;i<disponibles.size();i++){
            if(disponibles.get(i).equals(date)){
                return true;
            }
        }
        return false;
    }
    public void limpiarPuntos(){
        int year = Calendar.getInstance().get(Calendar.YEAR); // Variable que contiene el año actual
        int month = Calendar.getInstance().get(Calendar.MONTH); // Variable que contiene el mes actual
        Calendar mycal = new GregorianCalendar(year,month,1);
        for(int i=0;i<12;i++){
            int diasMes = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
            for(int j=1;j<=diasMes;j++)
                calendario.unMarkDate(new DateData(year,i,j).setMarkStyle(new MarkStyle(MarkStyle.DOT, Color.GREEN)));
        }
    }
    //Llena una lista que contiene las reservas hechas a un servicio
    public void llenarReservas(final String idServ){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("reservas");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot!=null){
                        Reserva reserva = snapshot.getValue(Reserva.class);
                        if(reserva!=null){
                            if(reserva.getIdServicio().equals(idServ)){
                                System.out.println("Fechas reservadas del servicio ----------------------->>>>>>>>>>>>> "+reserva.getFecha());
                                fechasReservadas.add(reserva);
                            }
                        }
                    }
                    else{
                        Toast.makeText(getActivity(), "Hubo un problema encontrando las reservas", Toast.LENGTH_SHORT).show();
                    }
                }
                cargarDatosIniciales();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
    //Le llega una fecha, para comprobar si esta se encuentra dentro de los datos reservados
    public boolean estaReservado(DateData fecha){
        String fechaString = String.valueOf(fecha.getYear())+"-"+fecha.getMonthString()+"-"+fecha.getDayString();
        System.out.println("Verifica si esta reservada la fecha --------------->>>>>>>>>>>>>> "+fechaString);
        if(estaReservadoHoras(fechaString)){
            return true;
        }else{
            return false;
        }
        /*
        for(int i=0;i<fechasReservadas.size();i++){
            if(fechasReservadas.get(i).getFecha().equals(fechaString)){
                System.out.println("ESTA RESERVADOOOOOOOO");
                if(estaReservadoHoras())
                return true;
            }
        }
        return false;
        */
    }
    //Metodo para saber si para una la reserva de un dia, estan ocupadas todas las horas
    public boolean estaReservadoHoras(String fecha){
        boolean flag=false;
        List<String> copiaHoras = new ArrayList<String>();
        //copiaHoras.addAll(horasString);

        if(fechasReservadas.size()>0){
            for(int j=0;j<horasString.size();j++){
                int i=0;
                flag=false;
                System.out.println("Tamaño de la lista de reservas -----------------------------------------------------------------> "+fechasReservadas.size());
                while(i<fechasReservadas.size() && !flag){
                    System.out.println("Tamaño de horasDisponibles en reservadoHoras 111111 ----------------------->>>>>>>>>> "+horasDisponibles.size());
                    System.out.println(fechasReservadas.get(i).getFecha());
                    if(fechasReservadas.get(i).getFecha().equals(fecha)/*&& fechasReservadas.get(i).getEstado() == EstadoReserva.ACEPTADO*/){ //Toca mirar si esta ACEPTADO , para quitar la hora
                        System.out.println("ESTA RESERVADAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                            if(horasString.get(j).equals(String.valueOf(fechasReservadas.get(i).getHora()))){ //Si una de las horas que tiene el servicio es igual a una de las horas que se encuentra en las reservas
                                /*
                                System.out.println("Se elimino la hora ----------------------------------->"+ horasString.get(cont));
                                copiaHoras.remove(cont); //La elimino porque ya esta reservada
                                cont--;
                                */
                                flag=true;
                                break;
                            }
                    }
                    i++;
                    System.out.println("Esto es i ->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><"+i);
                }
                System.out.println("Estado de la bandera "+flag );
                if(!flag){
                    //Si no entro al if donde encontraba una fecha en la lista de reservados igual a la que se estaba comparando, entonces se agrega porque es una fecha disponible
                    System.out.println("La hora "+horasString.get(j) + " no esta reservada");
                    copiaHoras.add(horasString.get(j));
                }

            }
        }else{
            copiaHoras.addAll(horasString);
        }

        System.out.println("Tamaño de horasDisponibles en reservadoHoras 2222222222 ----------------------->>>>>>>>>> "+horasDisponibles.size());
        horasDisponibles.addAll(copiaHoras);
        System.out.println("Tamaño de horasDisponibles en reservadoHoras 33333333333 ----------------------->>>>>>>>>> "+horasDisponibles.size());
        if(copiaHoras.size()==0)
            return true;
        else
            return false;
    }
    public void subirReserva(Servicio servicio){
        final String idReserva=servicio.getId()+String.valueOf(System.currentTimeMillis());
        FirebaseUser user = mAuth.getCurrentUser();
        String fecha = String.valueOf(marcados.get(0).getYear())+"-"+marcados.get(0).getMonthString()+"-"+marcados.get(0).getDayString();
        Reserva reserva = new Reserva();
        reserva.setId(idReserva);
        reserva.setEstado(EstadoReserva.PENDIENTE);
        reserva.setIdUsuSolicitante(user.getUid());
        reserva.setIdServicio(servicio.getId());
        reserva.setFecha(fecha);
        reserva.setHora(horaSeleccionada);
        reserva.setVisto(false);
        reserva.setRazon("");
        FirebaseDatabase.getInstance().getReference("reservas").child(idReserva).setValue(reserva).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //progressbar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    infoActualUsuario(idReserva);
                    /*
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ServiciosDestacadosFragment servDestacados = new ServiciosDestacadosFragment();
                    ft.replace(R.id.fragment_container, servDestacados);
                    ft.addToBackStack(null);
                    ft.commit();*/
                }
                else{
                    Toast.makeText( getActivity(),"Hubo un error al crear la reserva", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
    private void infoActualUsuario(final String idReserva){
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    Usuario usr = dataSnapshot.getValue(Usuario.class);
                    usr.setServicioSolicitado(idReserva);
                    mDataBase.child(mAuth.getCurrentUser().getUid()).setValue(usr);
                    Toast.makeText(getActivity(), "Se ha mandado la solicitud de la reserva", Toast.LENGTH_SHORT).show();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ServiciosDestacadosFragment servDest= new ServiciosDestacadosFragment();
                    ft.replace(R.id.fragment_container, servDest);
                    ft.commit();
                    //Toast.makeText(getActivity(), "Se realizaron los cambios", Toast.LENGTH_SHORT).show();
                    //int cedula = dataSnapshot.child("cedula").getValue(Integer.class);
                    //String nombre = dataSnapshot.child("nombre").getValue(String.class);
                    System.out.println("ESTO ES EL NOMBREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE  en PUB POSICIONAMIENT"+ usr.getNombre());
                    System.out.println("ESTO ES EL EMAILLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL  EN PUB POSICIONAMIENTO"+ usr.getEmail());
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
