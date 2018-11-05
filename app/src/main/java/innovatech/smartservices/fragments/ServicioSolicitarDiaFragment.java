package innovatech.smartservices.fragments;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import innovatech.smartservices.R;
import innovatech.smartservices.models.Servicio;
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
    Button siguiente;
    Servicio serv ;
    Bundle bundle;
    List<DateData> marcados = new ArrayList<DateData>(); //Se encuentran todos los elementos dentro del MCalendarView que estan marcados por el punto verde
    List<DateData> disponibles = new ArrayList<DateData>();
    int mes;
    int anio;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_servicio_solicitar_dia, container, false);
        titulo = (TextView)view.findViewById(R.id.txtSolicDiaTitulo);
        precio = (TextView) view.findViewById(R.id.txtSolicDiaPrecio);
        imagen = (ImageView) view.findViewById(R.id.txtSolicDiaImagen);
        calendario = (MCalendarView)view.findViewById(R.id.calendarSolicDia);
        siguiente = (Button) view.findViewById(R.id.btnSolicDiaSig);
        limpiarPuntos();
        bundle = getArguments();
        titulo.setText(bundle.getString("nombreServ"));
        precio.setText("$"+bundle.getString("precioServ"));
                Picasso.with(getContext()).load(Uri.parse(bundle.getString("imagenIni"))).into(imagen);
        serv = (Servicio) bundle.getSerializable("servicio");
        anio = Calendar.getInstance().get(Calendar.YEAR); // Variable que contiene el año actual
        mes = Calendar.getInstance().get(Calendar.MONTH); // Variable que contiene el mes actual
        Calendar mycal = new GregorianCalendar(anio,mes,1);
        int diasMes = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("AÑO->>>>>>>>>>>>>> "+anio+" MES----------->>>>>>>>> "+mes+" DIAS QUE TIENE EL MES ->>>>>>>>>>>>> "+diasMes);
        System.out.println("El dia que estoy buscando es el ----------------------------->"+serv.getDisponibilidadDias().get(0));
        Calendar cal = Calendar.getInstance();
        DateData dateData;
        for(int i = 1 ;i<=diasMes;i++){
            cal.set(anio,mes,i);
            int dayweek =(cal.get(Calendar.DAY_OF_WEEK)-1);
            if(verificarDias(dayweek)){
                 dateData = new DateData(anio,mes+1,i);
                 disponibles.add(dateData);
                 calendario.unMarkDate(dateData);
                 calendario.markDate(dateData.setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND,Color.BLUE)));
            }

        }
        calendario.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                System.out.println("Oprimo diaaaaaaaaaaaaaaaaa ->>>>>>>>>>>>>>>>>>>>>>>>>>>");
                if(esDiaDisponible(date)){
                    if(!verificarMarcados(date) ) {
                        calendario.unMarkDate(date);
                        calendario.markDate(date.setMarkStyle(new MarkStyle(MarkStyle.DOT, Color.GREEN)));
                        marcados.add(date);
                    }else{
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
        accionBotones();
        return view;
    }
    public void accionBotones(){
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(marcados.size()>0){
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ServicioSolicitarFinalFragment solicitarFinal = new ServicioSolicitarFinalFragment();
                    ft.replace(R.id.fragment_container, solicitarFinal);
                    ft.addToBackStack(null);
                    solicitarFinal.setArguments(bundle);
                    ft.commit();
                }else{
                    Toast.makeText(getActivity(), "Tiene que seleccionar almenos una fecha para reservar el servicio", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean verificarDias(int dayweek){
        for(int i = 0 ;i<serv.getDisponibilidadDias().size();i++){
            if(serv.getDisponibilidadDias().get(i)==dayweek)
                return true;
        }
        return false;
    }
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
}
