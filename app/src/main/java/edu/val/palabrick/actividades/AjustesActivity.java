package edu.val.palabrick.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import edu.val.palabrick.R;
import edu.val.palabrick.util.Constantes;
import edu.val.palabrick.util.GestionPreferenciasUsuario;

public class AjustesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Integer[] opciones_longitud_palabra = {3, 4, 5};
    private Spinner spinner;
    private boolean primera_vez;
    private int prefencia_tamanio_antigua;
    private int prefencia_tamanio_nueva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        primera_vez = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);



        //SPINNER O LISTA DESPLEGABLE
        this.spinner = findViewById(R.id.spinner_longitud);
        //Adapter --> actúa como proveedor de datos del spinner
        //yo el adapter, se lo asgino al spinner
        //cuando el spinner se vaya a representar, usa al adapter
        //y le dice: DAME LOS DATOS!
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, opciones_longitud_palabra);
        //le tenemos que dar una apariencia para cuando esté desplegado el spinner
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.spinner.setAdapter(arrayAdapter);//le digo al spinner, éste es tu adapter

        //al spinner, le tengo que programar su listener
        this.spinner.setOnItemSelectedListener(this);
    }

    private int obtenerPosicion (int preferencia_guardada)
    {
        int posicion = 0;

            //ARRAY : tiene un tamaño fijo una vez que se crea, no se puede cambiar, tenemos operaciones más limitadas
            if (preferencia_guardada == opciones_longitud_palabra[0])
            {
                posicion =0;
            } else if (preferencia_guardada == opciones_longitud_palabra[1])
            {
                posicion = 1;
            } else {
                posicion = 2;
            }

        Log.d(InicioActivity.ETIQUETA_LOG, "Posicion guardada con Array = " + posicion);

        //LISTA : tiene un tamaño variable, no me preocupo de cuántos elementos tiene, tenemos más operaciones
        /* if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
         {
              List<Integer> lista = List.of(opciones_longitud_palabra);
              posicion = lista.indexOf(preferencia_guardada);
             Log.d(InicioActivity.ETIQUETA_LOG, "Posicion guardada con Lista = " + posicion);
         }*/


        return posicion;

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (!primera_vez)
        {
            //el usuario ha tocao el spinner de verdad
            Log.d(InicioActivity.ETIQUETA_LOG, "OPCION SELECCIONADA DEL DESPLAGABLE");
            //cómo puedo hacer para obtener el texto si view la opción seleecionada
            TextView opcion_seleccionada = (TextView) view;
            String opcion = opcion_seleccionada.getText().toString();
            //opcion_seleccionada.getText();
            Log.d(InicioActivity.ETIQUETA_LOG, "OPCION seleccionada = " + opcion );
            //GUARDAR LA SELECCIÓN
            guardarOpcionLongitud (opcion);
        } else {
            //el usuario no ha tocado, se ha entrado por primera vez
            prefencia_tamanio_antigua = GestionPreferenciasUsuario.leerPreferenciaTamanioAjustes(this);
            prefencia_tamanio_nueva = prefencia_tamanio_antigua;
            //TODO PONER LA PRERENCIA ACTUAL AL INICIO DEL SPINER
            //dado un número, decir en qué posición está
            int posicion = obtenerPosicion(prefencia_tamanio_antigua);
            this.spinner.setSelection(posicion);
        }


        //LA OPCIÓN ACTUAL
        //LEER LA NUEVA
        primera_vez = false;
    }




    @Override
    protected void onStop() {
        if (prefencia_tamanio_antigua!=prefencia_tamanio_nueva)
        {
            Toast.makeText(this, R.string.mensaje_ajustes_cambio_tamanio, Toast.LENGTH_LONG).show();
        }
        super.onStop();
    }

    private void guardarOpcionLongitud (String longitud_seleccionada)
    {
        GestionPreferenciasUsuario.guardarPreferenciaTamanioAjustes(this, longitud_seleccionada);
        prefencia_tamanio_nueva = Integer.parseInt(longitud_seleccionada);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d(InicioActivity.ETIQUETA_LOG, "OPCION DESAPARECIDA DEL DESPLAGABLE onNothingSelected");

    }
}