package edu.val.palabrick.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import edu.val.palabrick.R;
import edu.val.palabrick.util.Constantes;


/**
 * En esta Pantalla, se carga la animación inicial del juego (video_inicio)
 */
public class InicioActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private VideoView videoView;

    /*
    LISTA DE TAREAS PENDIENTES

    - HACER QUE LOS TECLAS SE VEAN BIEN
    - QUE LAS CASILLAS LAS LETRAS SE VEAN CENTRADAS
    - TERMINAR CON LA SELECCIÓN DE PALABRAS (3,4,5)
    - HACER QUE EL JUEGO FUNCIONE REALMENTE CON EL AJUESTE DE LONGUITUD
    - PÁGINA DE AYUDA (NOS PODEMOS INSPIRAR EL WORDLE https://wordle.danielfrg.com/)
    - PONER LA FLECHITA DE HACIA ATRÁS PARA MEJORAR LA NAVEGACIÓN
    - REMATAR LAS ESTADÍSITICAS Y LA OPCIÓN DE COMPARTIR "IDEA DE COMPARTIR UN ENLACE QUE LE LLEVE A JUGAR AL USUARIO QUE RECIBE
    EL MENSAJE A LA MISMA PALABRA" APP://PALABRICK/3/35 <a href="APP://PALABRICK/3/35>Juega a supera</a>
    - TRAER LAS DEFINICIONES DE LA RAE CUANDO ACABA LA PARTIDA
    - TAMBIÉN USAR EL DICCIONARIO PARA VALIDAR
    - JUGAR CON TIEMPO COMO AJUSTE /DIBUJAR UN TEMPORIZADOR EN LA PARTIDA
    - CAMBIAR LOS COLORES EN AJUSTES




     */


    //defino como constante el LOG, para que en todas las clases, pueda usarlo igual
    public final static String ETIQUETA_LOG = "PALABRICK_APP";

    //TODO PROGRAMAR QUE CUANDO EL VIDEO, CAMBIEMOS DE PANTALLA
    //1 "ESCUCHAR EL FINAL DEL VIDEO" -- definir un listener-evento- esucchar el final del video
    //2 "CAMBIAR DE PANTALLA"
    //TODO QUITAR EL APP BAR - LA BARRA DEL TÍTULO
    //TODO CHECKBOX encima del video con FrameLayaout
    //TODO CREAR EL ICONO de aplicación X HECHO
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //INTENTA ESTO
        Log.d(InicioActivity.ETIQUETA_LOG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        getSupportActionBar().hide();//ocultamos la ActionBar

        //antes de que ser cargue la actvidad, si el ususario tiene la pref de saltar, me voy al juego
        //SI EL USUARIO HABÍA QUE DICHO QUE NO QUIERO VIDEO, SALTO
        SharedPreferences fichero_prefs = getSharedPreferences(Constantes.NOMBRE_FICHERO_PREFERENCES, MODE_PRIVATE);
        boolean saltar = fichero_prefs.getBoolean("saltar_intro", false);
        if (saltar) {
            //si está guardado que quiere saltar, me voy a la pantalla de juego
            saltarAlJuego();
        } else {
            mostrarVideo ();
        }
    }

    private void mostrarVideo ()
    {
        try {
            //cargar el video NO necesitamos ningún permiso para leer el video
            //por que es un fichero que reside dentro de las carpetas de mi app
            //memoria más privada
            String ruta_video = "android.resource://" + getPackageName() + "/" + R.raw.video_inicio;
            Log.d(InicioActivity.ETIQUETA_LOG, "ruta video = " + ruta_video);
            //URI - string pero con caracteres especiales
            this.videoView = findViewById(R.id.videoView); //inicio la vista
            this.videoView.setOnCompletionListener(this::onCompletion);//video, cuando acabes esta clase te escucha
            this.videoView.setOnCompletionListener(this);//video, cuando acabes esta clase te escucha
            this.videoView.setVideoPath(ruta_video);//cargo la ruta
            this.videoView.start();//reproduzco el video "play"
            Log.d(InicioActivity.ETIQUETA_LOG, "play video");


        } catch (Throwable fallo) {   //Y SI OCURRRE CUALQUIER FALLO, SE METE POR AQUÍ
            //CAPTURAR LA EXCEPCIÓN
            Log.e(ETIQUETA_LOG, "ha ocurrido un fallo", fallo);
        }
    }

    /**
     * cuando acabe el video, Android, llama a este método para avisarnos - CallBack
     */

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d(InicioActivity.ETIQUETA_LOG, "FIN DEL VIDEO");
        //TODO ir a la pantalla del JUEGO ?¿Como --> con un INTENT!
        //EJEMPLO DE INTENT EXPLÍCITO --> LE DECIMOS A ANDROID, SIN AMBIGÜEDAD A QUÉ PANTALLA QUIERO IR

       saltarAlJuego();

        //INTENTES IMPLÍCITOS --> DESCRIBIR UNA ACCCIÓN, DE FORMA GENÉRICA Y ANDROID, VA A BUSCAR
        //QUÉ ACTIVIDADES, DE LAS APLICACIONES INSTALADAS EN EL DISPOSITIVO, PUEDEN
        //HACERSE CARGO

        /*Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com"));
        //ME ASEGURO QUE AL MENOS HAY UNA ACTIVIDAD DE UNA APP QUE PUEDE LLEVAR A CABO
        //LA ACCIÓN DESCRITA POR EL INTENT- VER UNA WEB
        if (intent3.resolveActivity(getPackageManager())!=null)
        {
            Log.d(ETIQUETA_LOG, "Al menos hay una actividad en el teléfono que puede hacer lo del intent");
            startActivity(intent3);
        } else {
            Toast mensaje = Toast.makeText(this, "NO hay una app que pueda hacer esta acción", Toast.LENGTH_LONG);
            mensaje.show();
        }
        startActivity (intent3);*/

    }

    private void saltarAlJuego() {
        Intent intent = new Intent(this, JuegoMainActivityInflada.class);
        startActivity(intent);
        finish();
    }

    public void saltar_animacion(View view) {


        //YO ME TENGO QUE APUNTAR, QUE EL USUARIO NO QUIERO VER LA ANIMACIÓN MÁS DE INICIO
        //LEO EL PREFERENCES
        SharedPreferences fichero_prefs = getSharedPreferences(Constantes.NOMBRE_FICHERO_PREFERENCES, MODE_PRIVATE);
        //GUARDO QUE NO QUIERE- SALTAR_INTRO TRUE/FALSE
        SharedPreferences.Editor editor = fichero_prefs.edit();
        editor.putBoolean("saltar_intro", true);
        editor.commit();

        saltarAlJuego();


    }
}