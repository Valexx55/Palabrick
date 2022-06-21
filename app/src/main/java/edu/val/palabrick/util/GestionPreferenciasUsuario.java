package edu.val.palabrick.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import edu.val.palabrick.model.ResultadosEstadisticosJugador;

/**
 * en esta clase, vamos a definir todos los métodos
 * relaciones con operaciones de lectura y escritura
 * sobre ficheros de preferencias
 *
 * TODO LO DE PREFERENCIAS, VA A AQUÍ
 *
 */
public class GestionPreferenciasUsuario {

    //leerPreferenciaTamañoAjustes
    //guardarPreferenciaTamañoAjutes

    public static int leerPreferenciaTamanioAjustes (Context context)
    {
        int tamanio = 0;
        String tamanio_aux = null;

            SharedPreferences fichero_prefs = context.getSharedPreferences(Constantes.NOMBRE_FICHERO_PREFERENCES, Context.MODE_PRIVATE);
            tamanio_aux = fichero_prefs.getString("longitud_palabras","5");
            tamanio = Integer.parseInt(tamanio_aux);

        return tamanio;

    }

    public static void guardarPreferenciaTamanioAjustes (Context context, String tamanio)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.NOMBRE_FICHERO_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("longitud_palabras", tamanio);
        editor.commit();
    }

    //TODO mañana hacer los métodos de guardar/leer estadísticas
    //e invocarlos desde donde toque (buscar la lógica en el juego) donde haya que usar estos métodos

    /**
     * Método para guardar los resultados estadísticos del jugador
     *
     * @param context Contexto de la APP
     * @param resultadosEstadisticosJugador los resultados estadísiticos del jugador a almacenar
     */
    public static void guardarEstadisticasJugador (Context context, ResultadosEstadisticosJugador resultadosEstadisticosJugador)
    {
        Gson gson = new Gson();
        String resultados_json = gson.toJson(resultadosEstadisticosJugador);
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.NOMBRE_FICHERO_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString("estadisticas_jugador" ,resultados_json );
        editor.commit();

    }

    /**
     * Leemos las estadisticas del jugador del fichero de preferencias
     *
     * @param context Contexto de la APP
     * @return un objeto con los resultados estadísticos o null si no tenía nada
     * @see ResultadosEstadisticosJugador
     */
    public static  ResultadosEstadisticosJugador leerEstadísticasJugador (Context context)
    {
        ResultadosEstadisticosJugador resultadosEstadisticosJugador = null;

            SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.NOMBRE_FICHERO_PREFERENCES, Context.MODE_PRIVATE);
            String resultados_json = sharedPreferences.getString("estadisticas_jugador", null);
            Gson gson = new Gson();
            resultadosEstadisticosJugador = gson.fromJson(resultados_json, ResultadosEstadisticosJugador.class);


        return resultadosEstadisticosJugador;

    }


}

