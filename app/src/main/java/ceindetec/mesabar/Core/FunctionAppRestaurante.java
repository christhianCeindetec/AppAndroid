package ceindetec.mesabar.Core;

import java.math.BigDecimal;

public class FunctionAppRestaurante {

    public static String Distancia(double latitudInicial, double latitudFinal, double longitudInicial, double longitudFinal) {

        if (latitudInicial != 0 & latitudFinal != 0 & longitudInicial != 0 & longitudFinal != 0) {

            int radioTerrestre = 6371;
            double deltaLatitud = Math.toRadians(latitudFinal - latitudInicial);
            double deltaLongitud = Math.toRadians(longitudFinal - longitudInicial);
            double auxA = Math.pow(Math.sin(deltaLatitud / 2), 2) + Math.cos(Math.toRadians(latitudInicial)) * Math.cos(Math.toRadians(latitudFinal)) * Math.pow(Math.sin(deltaLongitud / 2), 2);
            double auxC = 2 * Math.atan2(Math.sqrt(auxA), Math.sqrt(1 - auxA));
            double distanciaFinal = radioTerrestre * auxC;

            if (distanciaFinal >= 1)
                return String.valueOf(truncateDecimal(distanciaFinal, 2)) + " Kms";
            else
                return String.valueOf(truncateDecimal(distanciaFinal * 1000, 2)) + " Ms";
        } else {
            return "Distancia No Disponible";
        }
    }

    public static String Distancia(double latitudFinal, double longitudFinal) {

        return Distancia(
                GlobalVars.getGlobalVarsInstance().getPosLatitud(),
                latitudFinal,
                GlobalVars.getGlobalVarsInstance().getPosLongitud(),
                longitudFinal
        );

    }

    public static BigDecimal truncateDecimal(double x, int numeroDecimales) {
        if (x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numeroDecimales, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numeroDecimales, BigDecimal.ROUND_CEILING);
        }
    }
}
