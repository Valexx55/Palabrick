package edu.val.palabrick.model;

//import com.google.gson.Gson;

public class ResultadosEstadisticosJugador {

    //QUIERO GUARDAR LOS RESULTADOS ESTADISTICOS EN UN FICHERO DE PREFERNCES...
    //LAS PREFERENCES SOLO PUEDO GUARDAR INT, STRING, BOOLEAN, FLOAT....
    //¿CÓMO GUARDAR UN Objeto de la Clase ResultadosEstadisticosJugador?¿?¿?¿

    //EL PASO PREVIO ES SERIALIZAR (oBJETO A TEXTO)

    //OBJETO A formato JSON: vamos a usar una librería de terceros GSON de Google

    //¿¿qué es GSON. es una librería JAVA de GOOGLE, que me permite
    //fácilmente serializar y des a JSON objetos JAVA


   // Gson
    private int partidas_jugadas;
    private int porcentaje_victorias;
    private int numero_victorias;
    private int numero_derrotas;

    public ResultadosEstadisticosJugador(int partidas_jugadas, int porcentaje_victorias) {
        this.partidas_jugadas = partidas_jugadas;
        this.porcentaje_victorias = porcentaje_victorias;
    }

    public ResultadosEstadisticosJugador(int partidas_jugadas, int porcentaje_victorias, int numero_victorias, int numero_derrotas) {
        this.partidas_jugadas = partidas_jugadas;
        this.porcentaje_victorias = porcentaje_victorias;
        this.numero_victorias = numero_victorias;
        this.numero_derrotas = numero_derrotas;
    }

    public int getNumero_victorias() {
        return numero_victorias;
    }

    public void setNumero_victorias(int numero_victorias) {
        this.numero_victorias = numero_victorias;
    }

    public int getNumero_derrotas() {
        return numero_derrotas;
    }

    public void setNumero_derrotas(int numero_derrotas) {
        this.numero_derrotas = numero_derrotas;
    }

    public int getPartidas_jugadas() {
        return partidas_jugadas;
    }

    public void setPartidas_jugadas(int partidas_jugadas) {
        this.partidas_jugadas = partidas_jugadas;
    }

    public int getPorcentaje_victorias() {
        return porcentaje_victorias;
    }

    public void setPorcentaje_victorias(int porcentaje_victorias) {
        this.porcentaje_victorias = porcentaje_victorias;
    }
}
