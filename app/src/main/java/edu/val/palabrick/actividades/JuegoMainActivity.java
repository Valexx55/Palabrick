package edu.val.palabrick.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.val.palabrick.R;

public class JuegoMainActivity extends AppCompatActivity {

    //TODO INFLADO DINÁMICO??
    //TODO ELEGIR NÚMERO DE LETRAS/INTENTOS DINÁMAMENTE?¿

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_main);

    }

    public void cajaTocada(View view) {
        Log.d(InicioActivity.ETIQUETA_LOG, "caja tocada");
    }
}