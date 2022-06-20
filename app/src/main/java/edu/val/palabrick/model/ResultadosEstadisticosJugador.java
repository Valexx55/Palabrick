package edu.val.palabrick.model;

public class ResultadosEstadisticosJugador {

    //QUIERO GUARDAR LOS RESULTADOS ESTADISTICOS EN UN FICHERO DE PREFERNCES...
    //LAS PREFERENCES SOLO PUEDO GUARDAR INT, STRING, BOOLEAN, FLOAT....
    //¿CÓMO GUARDAR UN Objeto de la Clase ResultadosEstadisticosJugador?¿?¿?¿


    private int partidas_jugadas;
    private int porcentaje_victorias;

    public ResultadosEstadisticosJugador(int partidas_jugadas, int porcentaje_victorias) {
        this.partidas_jugadas = partidas_jugadas;
        this.porcentaje_victorias = porcentaje_victorias;
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
