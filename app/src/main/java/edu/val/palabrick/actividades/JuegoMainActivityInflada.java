package edu.val.palabrick.actividades;

import static edu.val.palabrick.actividades.InicioActivity.ETIQUETA_LOG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import edu.val.palabrick.R;
import edu.val.palabrick.model.ResultadosEstadisticosJugador;
import edu.val.palabrick.util.Constantes;
import edu.val.palabrick.util.GestionPreferenciasUsuario;

public class JuegoMainActivityInflada extends AppCompatActivity {


    //2 constantes (seleccionar con una lista desplegable) - Spinner
    private static final int MAX_INTENTOS = 6;//NUMERO DE FILAS
    //private static final int TAMANIO_PALABRAS = 5;//NUMEROS DE CASILLAS

    private LinearLayout espacio_tablero;//parte superior de la pantalla
    private String palabra_adivnar;

    private int intentos_actual;
    private int casilla_actual;
    private String palabra_usuario;

    private List<LinearLayout> lista_filas = null;

    private int preferencia_tamanio;
   // Logger

    private final static String RUTA_TIENDA = "https://play.google.com/store/apps/details?id=edu.val.palabrick";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_main_inflado);

        iniciarActividad ();

    }

    //método para dibujar el menu de la barra APP Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_juego, menu);
        return true;
    }

    private void navegarAPantalla (Class actividad_destino)
    {
        Intent intent_pantalla = new Intent(this, actividad_destino);
        startActivity(intent_pantalla);
    }
    //sobreescribo este método, para recibir las opciones de menú seleccionadas
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.opcion_ajustes:
                navegarAPantalla(AjustesActivity.class);
                Log.d(ETIQUETA_LOG, "Ha tocado la opción de ajustes");
            break;
            case R.id.opcion_ayuda:
                navegarAPantalla(AyudaActivity.class);
                Log.d(ETIQUETA_LOG, "Ha tocado la opción de ayuda");
                break;
            case R.id.opcion_estadisticas:
                navegarAPantalla(EstadisticasActivity.class);
                Log.d(ETIQUETA_LOG, "Ha tocado la opción de estadisticas");
                break;
            case R.id.opcion_compartir:
                Log.d(ETIQUETA_LOG, "Ha tocado la opción de compartir");
                Intent intent_compartir = new Intent(Intent.ACTION_SEND);
                String texto_compartir = getResources().getString(R.string.mensaje_compartir);
                texto_compartir = texto_compartir + " " + RUTA_TIENDA;
                intent_compartir.putExtra(Intent.EXTRA_TEXT, texto_compartir);
                intent_compartir.setType("text/plain");//tipo de dato que quiero compartir TIPO MIME
                intent_compartir.setPackage("com.whatsapp");
                //TODO comprobar si no tiene whatsAPP, probar con el telegram
                //intent_compartir.setPackage("org.telegram.messenger");

                if (intent_compartir.resolveActivity(getPackageManager()) != null)
                {
                    //tiene whatsapp
                    Log.d(ETIQUETA_LOG, "TIENE WSAP");
                    startActivity(intent_compartir);
                }else {

                    intent_compartir.setPackage("org.telegram.messenger");
                    if (intent_compartir.resolveActivity(getPackageManager()) != null)
                    {
                        Log.d(ETIQUETA_LOG, "TIENE TELEGRAM");
                        startActivity(intent_compartir);
                    } else {
                        Log.d(ETIQUETA_LOG, "NO TIENE WSAP NI TELEGRAM");
                        Toast.makeText(this, R.string.mensaje_compartir, Toast.LENGTH_LONG).show();
                    }

                }

                break;
            case R.id.opcion_creditos:
                navegarAPantalla(CreditosActivity.class);
                Log.d(ETIQUETA_LOG, "Ha tocado la opción de creditos");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Dado un array de strings te devuelve un string aleatorio de ese array
     * @param array_especifico
     * @return
     */
    public String palabraOculta( CharSequence [] array_especifico){
        String palabra = null;

        int aleatoria = (int) (Math.random() * array_especifico.length);
        Log.d("MENSAJE2", aleatoria + " "+array_especifico.length);
        palabra = array_especifico[aleatoria].toString();

        return palabra;

    }

    public String obtenerPalabraDeLongitud5() {
        String palabra = null;
        //TypedArray XML - ANDROID
        TypedArray array_categorias = getResources().obtainTypedArray(R.array.array_categorias);

        //CHarSequence = String
        CharSequence [] arrayDe5 = array_categorias.getTextArray(0);//el de 5
        array_categorias.recycle();//libera la memoria

        palabra = palabraOculta(arrayDe5);

        return palabra;
    }

    private int leerPrefrenciaTamanio()
    {
        int tamanio = 0;
        String tamanio_aux = null;

            SharedPreferences fichero_prefs = getSharedPreferences(Constantes.NOMBRE_FICHERO_PREFERENCES, MODE_PRIVATE);
            tamanio_aux = fichero_prefs.getString("longitud_palabras","5");
            tamanio = Integer.parseInt(tamanio_aux);

        return tamanio;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * Iniciamos la pantalla (tablero, teclados)
     * y las variables de control
     */
    private void iniciarActividad ()
    {
        preferencia_tamanio = leerPrefrenciaTamanio();
        dibujarTablero ();
        programarBotones ();
        this.palabra_adivnar = generarPablraAdivnar();
        this.intentos_actual = 0;
        this.casilla_actual = 0;
        this.palabra_usuario = "";
    }


    /**
     * Programo el listener de todos los botones del teclado dinámicamente
     */
    private void programarBotones ()
    {
        LinearLayout teclado = findViewById(R.id.espacioTeclado);
        int hijos = teclado.getChildCount();
        for (int filabotones=0; filabotones < hijos; filabotones++)
        {
            LinearLayout layoutfilaboton = (LinearLayout) teclado.getChildAt(filabotones);
            int numbotonesfila = layoutfilaboton.getChildCount();
            for (int nbotones = 0; nbotones < numbotonesfila; nbotones++)
            {
                Button boton_actual = (Button)layoutfilaboton.getChildAt(nbotones);
                boton_actual.setOnClickListener(this::botonTocado);
            }
        }
    }

    /**
     * Generamos una palabra aleatorio para que la adivine el usuario
     * @return la palabra generada a adivinar
     */
    private String generarPablraAdivnar()
    {
        String palabra_adivinar_local = null;

        //TODO generar una palabra aleatoriamente Arrays Tipados -tener el cuenta la longitud de PALABRA-
        //palabra_adivinar = PalabraService.obtenerPalabraDeLongitud5();
        palabra_adivinar_local = obtenerPalabraDeLongitud5();

        Log.d(ETIQUETA_LOG, "PALABRA A ADIVINAR = "+palabra_adivinar_local);
        return palabra_adivinar_local;


    }


    /**
     * Inflo el tablero dinámicamente en base al número de intentos
     * y la longitud de palabra actuales
     */
    private void dibujarTablero ()
    {
        this.espacio_tablero = findViewById(R.id.espacioTablero);
        LayoutInflater layoutInflater = getLayoutInflater();

        this.lista_filas = new ArrayList<LinearLayout>(MAX_INTENTOS);
        for (int fila=0; fila<MAX_INTENTOS; fila++)
        {
            LinearLayout fila_layout = (LinearLayout) layoutInflater.inflate(R.layout.fila_palabra, espacio_tablero, false);
            for (int casilla=0; casilla < preferencia_tamanio; casilla++)
            {
                LinearLayout casilla_layout = (LinearLayout) layoutInflater.inflate(R.layout.casilla_letra, fila_layout,false);
                fila_layout.addView(casilla_layout);
            }
            espacio_tablero.addView(fila_layout);
            this.lista_filas.add(fila_layout);
        }
    }

    /**
     * Relleno la casilla actual con la letra recibida
     *
     * @param texto_boton la letra recibida
     */
    private void rellenarCasilla (String texto_boton)
    {
        if (casilla_actual< preferencia_tamanio)
        {
            LinearLayout fila_actual =  this.lista_filas.get(this.intentos_actual);
            LinearLayout casilla_linear = (LinearLayout) fila_actual.getChildAt(casilla_actual);
            TextView textView = (TextView) casilla_linear.getChildAt(0);
            textView.setText(texto_boton);
            this.casilla_actual++;//corro a la siguiente casilla
            this.palabra_usuario = this.palabra_usuario+texto_boton;//actualizo la pabra
            Log.d(ETIQUETA_LOG, "PALABRA USUARIO " + this.palabra_usuario);
        } else {
            Log.d(ETIQUETA_LOG, "PULSACIÓN IGNORADA LA PALABRA TIENE TODAS SUS LETRAS");
        }

    }

    /**
     * Método para borrar la casilla cuando el usuario toca borrar del teclado
     */
    private void borrarCasilla () {
        if (casilla_actual>0)
        {
            casilla_actual--;
            LinearLayout fila_actual =  this.lista_filas.get(this.intentos_actual);
            LinearLayout casilla_linear = (LinearLayout) fila_actual.getChildAt(casilla_actual);
            TextView textView = (TextView) casilla_linear.getChildAt(0);
            textView.setText("");
            this.palabra_usuario = this.palabra_usuario.substring(0, this.palabra_usuario.length()-1);
            Log.d(ETIQUETA_LOG, "PALABRA USUARIO " + this.palabra_usuario);
        } else {
            Log.d(ETIQUETA_LOG, "la palabra está vacía, no hay casilla que borrar");
        }

    }

    /**
     * Método que valida si la palabra introducida por el usuario es válida
     * @param palabra la palabra introducida por el usuario
     * @return true si la palabra es válida, false en caso contrario
     */
    private boolean esPalabraUsuarioValida (String palabra)
    {
        //?¿?¿ TODO estudiar la viablidad de la validación
        //¿¿ validar contra el diccionario
        //?? validar con una lista
        return true;
    }

    /**
     * Método que determina el acierto/fallo de cada letra según la posición de la palabra
     * introducida por el usuario, en compración con la palabra que hay que adivinar
     *
      *@param palabra_usuario la palabra introducida por el usuario
     * @param palabra_adivinar la palabra que hay que adivinar en la partida
     * @return una lista de TipoCompracion para indicar el fallo/acierto de cada posición
     * @see TipoCompracion
     */
    private List<TipoCompracion> comprarPalabras (String palabra_usuario, String palabra_adivinar)
    {
        //[NO_ESTA, NO_ESTA, NO_ESTA, NO_ESTA, NO_ESTA ] -- PINTAR TODO GRIS
        //[NO_ESTA, DESORDENADA, NO_ESTA, NO_ESTA, NO_ESTA ] -- PINTAR TODO GRIS, menos la segunda EN AMARILLO
        List<TipoCompracion> lista_resultados = null;

            lista_resultados = new ArrayList<TipoCompracion>();
            for (int pos=0; pos<preferencia_tamanio; pos++)
            {
                TipoCompracion tc = TipoCompracion.comprarPosicion(palabra_usuario, palabra_adivnar, pos);
                lista_resultados.add(tc);
            }

        return lista_resultados;
    }

    private void actualizarFila (List<TipoCompracion> lista_resultados)
    {
     //TODO recorrer la lista_resultados e ir pintando cada casilla de la fila en curso
     LinearLayout fila_palabra_actual = this.lista_filas.get(intentos_actual);
     int color = 0;
     for (int num_casilla = 0; num_casilla<preferencia_tamanio; num_casilla++)
     {
         LinearLayout casilla = (LinearLayout) fila_palabra_actual.getChildAt(num_casilla);//obtenemos la casilla
         //TextView caja_letra = (TextView) casilla.getChildAt(0);
         TipoCompracion tipoCompracionActual = lista_resultados.get(num_casilla);
         switch (tipoCompracionActual)
         {
             case  NO_ESTA:
                 color = R.color.noesta;
                 break;
             case  COINCIDE:
                 color = R.color.coincide;
                 break;
             case  DESORDENADO:
                 color = R.color.desordenado;
                 break;
         }

         casilla.setBackgroundColor(getResources().getColor(color));
     }
    }

    /**
     *
     * @param button EL BOTÓN QUE HAY QUE PINTAR
     * @param tipoCompracion NOS DICE DE QUÉ COLOR EN FUNCIÓN DE SI ESTÁ, COINCIDE O NO ESTA
     */
    private void pintarBoton (Button button, TipoCompracion tipoCompracion)
    {
        //SI ES DESDORDEANDO _> AMARILLO
        //SI ES COINCIDE -> VERDE
        //SI NO ESTA -- GRIS
        int color = 0;

        switch (tipoCompracion)
        {
            case  NO_ESTA:
                color = R.color.black;
                break;
            case  COINCIDE:
                color = R.color.coincide;
                break;
            case DESORDENADO:
                color = R.color.desordenado;
                break;
        }
        button.setBackgroundColor(getResources().getColor(color));
        button.setTextColor(getResources().getColor(R.color.white));
    }

    private void actualilzarTeclado(String palabra_usuario, List<TipoCompracion> lista_resultados )
    {

        String palabra_usuario_minuscula = palabra_usuario.toLowerCase();//pasamos la palabra a minúscula
        String id_busqueda;
        for (int indice=0;indice<palabra_usuario_minuscula.length();indice++)
        {

            char letra_actual = palabra_usuario_minuscula.charAt(indice);
            if (letra_actual=='ñ')
            {
                id_busqueda = "enye";
            } else {
                id_busqueda = letra_actual+"";//convertimos en un cadena un char
            }
            Log.d(ETIQUETA_LOG, "LETRA ACTUAL = " + letra_actual);//letra actual, coincide con el nombre del id
            int id_boton = getResources().getIdentifier(id_busqueda, "id", getPackageName());
            Button letra_boton =  findViewById(id_boton);
            pintarBoton(letra_boton, lista_resultados.get(indice));

        }



    }



    private void probarPalabra()
    { //TENDRÍA QUE COMPROBAR SI ESTÁ LA PALABRA COMPLETA
        if (casilla_actual==preferencia_tamanio)
        {
            Log.d(ETIQUETA_LOG, "La pabra está completa");
            if (esPalabraUsuarioValida(this.palabra_usuario))
            {
                Log.d(ETIQUETA_LOG, "La pabra del usario no es válida");
                List<TipoCompracion> lista_resultados = comprarPalabras(palabra_usuario, palabra_adivnar);
                Log.d(ETIQUETA_LOG, "LISTA RESULTADOS = " +lista_resultados);
                //TODO pintar tanto el tablero (la fila de la palbra actual) como los botones con la lista de resultados
                actualizarFila (lista_resultados);
                actualilzarTeclado(palabra_usuario, lista_resultados );
                if (TipoCompracion.palabraAcertada(lista_resultados)) //== true
                {
                    Log.d(ETIQUETA_LOG, "HAS GANADO " +this.palabra_adivnar);
                    String mensaje_victoria = getString(R.string.mensaje_victoria);
                    Toast.makeText(this, mensaje_victoria +" "+this.palabra_adivnar, Toast.LENGTH_LONG).show();
                    irAEstadisticas (true);
                } else {
                    //SI NO HA HACERTADO
                    Log.d(ETIQUETA_LOG, "NO HA ACERTADO " +this.palabra_adivnar);
                    this.intentos_actual++;//pasar a la siguiente palabra
                    if (intentos_actual<MAX_INTENTOS)
                    {
                        //QUEDAN INTENTOS
                        this.casilla_actual=0;
                        this.palabra_usuario = "";
                    }
                    else {

                        Log.d(ETIQUETA_LOG, "HAS PERDIDO, ERA " +this.palabra_adivnar);
                        String mensaje_derrota = getString(R.string.mensaje_derrota);
                        Toast.makeText(this, mensaje_derrota + " " +this.palabra_adivnar, Toast.LENGTH_LONG).show();
                        irAEstadisticas (false);
                    }
                }
            } else {
                //
                Log.d(ETIQUETA_LOG, "La pabra del usario no es válida");
                String palabra_novalida = getString(R.string.palabra_no_valida);
                Toast.makeText(this, palabra_novalida, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(ETIQUETA_LOG, "COMPLETE LA PALABRA PARA PROBAR");
            String palabra_incompleta = getString(R.string.palabra_incompleta);
            Toast.makeText(this, palabra_incompleta, Toast.LENGTH_LONG).show();
        }

        //2 comprobar si la palabra es correcta =? / LISTA / DICCIONARIO - OPCIONAL
        //3 comparar con la que hay que adivinar
            //coincida --> has ganado y vas a estadísticas
            //no coincide --> actualizar teclado / tablero con los colores
                //cambiar de fila, ACTUALIZAR CASILLA actual
    }

    private void  irAEstadisticas (boolean victoria)
    {
        //lanzamos un intent para visitar la pantalla de estadísticas
        actualizarEstadisticas (victoria);

        Intent intent_estadisticas = new Intent(this, EstadisticasActivity.class);
        intent_estadisticas.putExtra("INTENTOS", this.intentos_actual);
        //intent_estadisticas.putExtra("LISTA_INTENTOS", TipoCompracion.COINCIDE);
        startActivity(intent_estadisticas);
    }

    private void actualizarEstadisticas (boolean victoria)
    {
        //leer estadísticas
        ResultadosEstadisticosJugador resultadosEstats = GestionPreferenciasUsuario.leerEstadísticasJugador(this);
        if (resultadosEstats==null)
        {
            resultadosEstats = new ResultadosEstadisticosJugador(0, 0, 0, 0);
        }
        //incrementar el número de partidas jugadas
        int partidas_jugadas = resultadosEstats.getPartidas_jugadas();
        partidas_jugadas = partidas_jugadas + 1;
        resultadosEstats.setPartidas_jugadas(partidas_jugadas);

        if (victoria)
        {
            int numero_victorias = resultadosEstats.getNumero_victorias();
            numero_victorias++;
            resultadosEstats.setNumero_victorias(numero_victorias);
        } else {
            int numero_derrotas = resultadosEstats.getNumero_derrotas();
            numero_derrotas++;
            resultadosEstats.setNumero_derrotas(numero_derrotas);
        }

        //TODO actualizar el % de victorias
        //porcentaje vicotias = victorias/total

        float porcentaje_victorias = (resultadosEstats.getNumero_victorias()*100/resultadosEstats.getPartidas_jugadas());
        int porcentaje_victorias_entero = Math.round(porcentaje_victorias);//redondea el decimal al entero más próximo
        resultadosEstats.setPorcentaje_victorias(porcentaje_victorias_entero);

        GestionPreferenciasUsuario.guardarEstadisticasJugador(this, resultadosEstats);
        //guardar nuevas estadísticas
    }

    public void botonTocado(View view) {

        if (view instanceof Button)
        {
            Log.d(ETIQUETA_LOG, "LA VISTA TOCADA ES UN BOTÓN");
            switch (view.getId())
            {
                case R.id.boton_borrar:
                    Log.d(ETIQUETA_LOG, "ha tocado el boton de borrar");
                    borrarCasilla ();
                    break;
                case R.id.boton_enviar:
                    Log.d(ETIQUETA_LOG, "ha tocado el boton de enviar");
                    probarPalabra();
                    break;
                default://caso general, ha tocado una letra
                    Log.d(ETIQUETA_LOG, "caso general, ha tocado una letra");
                    Button boton_tocado = (Button) view;//hago el casting
                    String texto_boton = boton_tocado.getText().toString();//para poder accedeer al texto
                    Log.d(ETIQUETA_LOG, "TEXTO BOTON = "+texto_boton);
                    //TODO EL TEXTO DEL BOTÓN EN LA CASILLA CORRESPONDIENTE
                    rellenarCasilla (texto_boton);
                    break;
            }


        }


    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Log.d(ETIQUETA_LOG, "SALGO DE LA APP DEL TODO");
        //this.finishAffinity();//esto te cierra todas las ventanas de la app
    }
}