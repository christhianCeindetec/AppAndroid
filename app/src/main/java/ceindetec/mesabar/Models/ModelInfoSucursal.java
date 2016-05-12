package ceindetec.mesabar.Models;

public class ModelInfoSucursal {

    // Atributos
    private String sucursal;
    private String nombre;
    private String categoria;
    private String puntuacion;
    private String latitud;
    private String longitud;

    public ModelInfoSucursal() {
    }

    public ModelInfoSucursal(String sucursal,String nombre, String categoria, String puntuacion) {
        this.sucursal = sucursal;
        this.nombre = nombre;
        this.categoria = categoria;
        this.puntuacion = puntuacion;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
