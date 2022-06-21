package edu.val.palabrick.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import edu.val.palabrick.R;

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
        //LEER DEL FICHERO DE PREFRENCIAS
        //Y ACTUALIZAR LA PATANLLA DE ESTADÍSTICAS
        //CON LOS DATOS LEÍDOS
    }


}