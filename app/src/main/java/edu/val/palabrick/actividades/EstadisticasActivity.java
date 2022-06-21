package edu.val.palabrick.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import edu.val.palabrick.R;
import edu.val.palabrick.model.ResultadosEstadisticosJugador;
import edu.val.palabrick.util.GestionPreferenciasUsuario;

public class EstadisticasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        Intent intent_estadisticas = getIntent();//sé el intent que me ha traído aquí
        int num_intentos = intent_estadisticas.getIntExtra("INTENTOS", -1);
        Log.d(InicioActivity.ETIQUETA_LOG, "Numero de intentos = "+ num_intentos);

        initActividad();
    }

    private void initActividad()
    {
        //TODO EN 10/15 MINUTOS
        //LEER DEL FICHERO DE PREFRENCIAS
        ResultadosEstadisticosJugador rej= GestionPreferenciasUsuario.leerEstadísticasJugador(this);
        //Y ACTUALIZAR LA PATANLLA DE ESTADÍSTICAS
        TextView partidas_jugadas = findViewById(R.id.partidas_jugadas);
        TextView porcentaje_victorias = findViewById(R.id.porcentaje_victorias);
        //CON LOS DATOS LEÍDOS
        partidas_jugadas.setText(rej.getPartidas_jugadas()+"");
        porcentaje_victorias.setText(rej.getPorcentaje_victorias()+"");
    }


}