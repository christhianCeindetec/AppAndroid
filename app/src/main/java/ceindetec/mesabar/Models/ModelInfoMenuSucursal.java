package ceindetec.mesabar.Models;

public class ModelInfoMenuSucursal {

    // Atributos
    private String idMenuSucursal;
    private String nombreMenuSucursal;
    private String descripcionMenuSucursal;
    private String nombreCategoria1;
    private String nombreCategoria2;
    private String nombreCategoria3;
    private String nombreCategoria4;
    private String nombreCategoria5;

    public ModelInfoMenuSucursal() {
    }

    public ModelInfoMenuSucursal(String idMenuSucursal, String nombreMenuSucursal, String descripcionMenuSucursal, String nombreCategoria1, String nombreCategoria2, String nombreCategoria3, String nombreCategoria4, String nombreCategoria5) {
        this.idMenuSucursal = idMenuSucursal;
        this.nombreMenuSucursal = nombreMenuSucursal;
        this.descripcionMenuSucursal = descripcionMenuSucursal;
        this.nombreCategoria1 = nombreCategoria1;
        this.nombreCategoria2 = nombreCategoria2;
        this.nombreCategoria3 = nombreCategoria3;
        this.nombreCategoria4 = nombreCategoria4;
        this.nombreCategoria5 = nombreCategoria5;
    }

    public String getIdMenuSucursal() {
        return idMenuSucursal;
    }

    public void setIdMenuSucursal(String idMenuSucursal) {
        this.idMenuSucursal = idMenuSucursal;
    }

    public String getNombreMenuSucursal() {
        return nombreMenuSucursal;
    }

    public void setNombreMenuSucursal(String nombreMenuSucursal) {
        this.nombreMenuSucursal = nombreMenuSucursal;
    }

    public String getDescripcionMenuSucursal() {
        return descripcionMenuSucursal;
    }

    public void setDescripcionMenuSucursal(String descripcionMenuSucursal) {
        this.descripcionMenuSucursal = descripcionMenuSucursal;
    }

    public String getNombreCategoria1() {
        return nombreCategoria1;
    }

    public void setNombreCategoria1(String nombreCategoria1) {
        this.nombreCategoria1 = nombreCategoria1;
    }

    public String getNombreCategoria2() {
        return nombreCategoria2;
    }

    public void setNombreCategoria2(String nombreCategoria2) {
        this.nombreCategoria2 = nombreCategoria2;
    }

    public String getNombreCategoria3() {
        return nombreCategoria3;
    }

    public void setNombreCategoria3(String nombreCategoria3) {
        this.nombreCategoria3 = nombreCategoria3;
    }

    public String getNombreCategoria4() {
        return nombreCategoria4;
    }

    public void setNombreCategoria4(String nombreCategoria4) {
        this.nombreCategoria4 = nombreCategoria4;
    }

    public String getNombreCategoria5() {
        return nombreCategoria5;
    }

    public void setNombreCategoria5(String nombreCategoria5) {
        this.nombreCategoria5 = nombreCategoria5;
    }

}
