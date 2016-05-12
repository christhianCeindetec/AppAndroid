package ceindetec.mesabar.Models;

public class ModelInfoAleatorio {

    // Atributos
    private String nombre;
    private String idCategoria;

    public ModelInfoAleatorio() {
    }

    public ModelInfoAleatorio(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }
}
