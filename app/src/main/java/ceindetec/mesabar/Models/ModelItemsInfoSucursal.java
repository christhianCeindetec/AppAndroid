package ceindetec.mesabar.Models;

import java.util.List;

public class ModelItemsInfoSucursal {

    // Encapsulamiento de Posts
    private List<ModelInfoSucursal> infoSucursal;

    public ModelItemsInfoSucursal(List<ModelInfoSucursal> infoSucursal) {
        this.infoSucursal = infoSucursal;
    }

    public void setInfoSucursal(List<ModelInfoSucursal> infoSucursal) {
        this.infoSucursal = infoSucursal;
    }

    public List<ModelInfoSucursal> getInfoSucursal() {
        return infoSucursal;
    }

}
