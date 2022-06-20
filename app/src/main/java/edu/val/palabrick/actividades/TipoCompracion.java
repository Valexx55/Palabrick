package edu.val.palabrick.actividades;


import android.os.Build;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

//EL TIPO ENUMERADO ES UNA FORMA DE INDICAR QUE UN DATO
//OSCILA EN UN RANGO DE VALORES CONCRETO
public enum TipoCompracion /*implements Serializable*/ {COINCIDE, NO_ESTA, DESORDENADO;

    public static TipoCompracion comprarPosicion (String palabra_usuario, String palabra_adivinar, int posicion)
    {
        TipoCompracion tipoCompracion;
        char letra_palabra_usuario = ' ';
        char letra_palabra_adivinar = ' ';

            letra_palabra_adivinar = palabra_adivinar.charAt(posicion);
            letra_palabra_usuario = palabra_usuario.charAt(posicion);

            if (letra_palabra_adivinar==letra_palabra_usuario)
            {
                tipoCompracion = TipoCompracion.COINCIDE;
            }
            else {
               if (palabra_adivinar.indexOf(letra_palabra_usuario)==-1)//si indexof devuelve -1, esa letra no pertence a la palabra
               {
                   //la letra, no está la palabra
                   tipoCompracion = TipoCompracion.NO_ESTA;
               } else {
                   tipoCompracion = TipoCompracion.DESORDENADO;
               }
               //IF TERNARIO alternativa
               //tipoCompracion = (palabra_adivinar.indexOf(letra_palabra_usuario)==-1) ? TipoCompracion.NO_ESTA : TipoCompracion.DESORDENADO;
            }


        return tipoCompracion;
    }

    public static boolean palabraAcertada (List<TipoCompracion> tipoCompracionList)
    {// [COINCIDE, NO_ESTA, DESORDENADO,COINCIDE, NO_ESTA]
        boolean acertada = false;
        TipoCompracion tipoCompracion = null;
        int posicion_actual = 0;
        boolean sigue = true;
        int tamanio_lista = tipoCompracionList.size();
           //PUEDO USAR JAVA 8 STREAMS PROGRAMACIÓN FUNCIONAL
            //para poder JAVA8, necesito asegurarme que el dispositivo
            //donde corre la app es version N o superior 7 Nougat 24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) //
        {
            //puedo usar expresiones lambda - programación funcional: código más económico
            //funciones, ya hechos, que puedo reutilizar.
            //a esas funciones dadas, les añado mi función y las completo a mi gusto
            acertada = tipoCompracionList.stream().allMatch(tCompracion -> tCompracion==COINCIDE);
            Log.d(InicioActivity.ETIQUETA_LOG, "acertada con stream lambda = " + acertada);

        } else {
            do { //recorremos la lista de compracion mientras se cumplan las 2 condiciones
                //que vaya coincidiendo cada posición
                //y que me quede array por compronar
                //TODO REVISARLO
                tipoCompracion = tipoCompracionList.get(posicion_actual);
                sigue = (tipoCompracion==COINCIDE);
                posicion_actual++;
            }while (sigue &&  (posicion_actual<tamanio_lista));

            acertada = sigue;
            Log.d(InicioActivity.ETIQUETA_LOG, "acertada con java 7 = " + acertada);
        }


        return acertada;
    }
}
