package ceindetec.mesabar.Core;

public final class GlobalVars {

    private static String urlBase = "http://192.168.0.245/AppRestauranteAndroid/";
    private static String urlTypeFile = ".php";
    private double posLatitud;
    private double posLongitud;

    private static GlobalVars globalVarsInstance;

    protected GlobalVars() {
    }

    public static synchronized GlobalVars getGlobalVarsInstance() {
        if (null == globalVarsInstance) {
            globalVarsInstance = new GlobalVars();
        }
        return globalVarsInstance;
    }

    public static String getUrlTypeFile() {
        return urlTypeFile;
    }

    public String getUrlBase() {
        return urlBase;
    }

    synchronized public double getPosLongitud() {
        return posLongitud;
    }

    synchronized public void setPosLongitud(double posLongitud) {
        this.posLongitud = posLongitud;
    }

    synchronized public double getPosLatitud() {
        return posLatitud;
    }

    synchronized public void setPosLatitud(double posLatitud) {
        this.posLatitud = posLatitud;
    }
}
