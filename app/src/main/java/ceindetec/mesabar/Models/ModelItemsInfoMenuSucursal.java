package ceindetec.mesabar.Models;

import java.util.List;

public class ModelItemsInfoMenuSucursal {

    // Encapsulamiento de Posts
    private List<ModelInfoMenuSucursal> infoMenuSucursal;

    public ModelItemsInfoMenuSucursal(List<ModelInfoMenuSucursal> infoMenuSucursal) {
        this.infoMenuSucursal = infoMenuSucursal;
    }

    public void setInfoMenuSucursal(List<ModelInfoMenuSucursal> infoMenuSucursal) {
        this.infoMenuSucursal = infoMenuSucursal;
    }

    public List<ModelInfoMenuSucursal> getInfoMenuSucursal() {
        return infoMenuSucursal;
    }

}
