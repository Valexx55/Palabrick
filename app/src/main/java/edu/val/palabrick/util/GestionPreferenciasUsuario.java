package edu.val.palabrick.util;

import android.content.Context;
import android.content.SharedPreferences;

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

    //guardarEstadisticasJugador
    //leerEstadísticasJugador


}
