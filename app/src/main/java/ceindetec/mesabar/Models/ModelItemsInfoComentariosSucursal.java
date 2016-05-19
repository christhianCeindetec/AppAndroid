package ceindetec.mesabar.Models;

import java.util.List;

public class ModelItemsInfoComentariosSucursal {

    // Encapsulamiento de Posts
    private List<ModelInfoComentariosSucursal> infoComentariosSucursal;

    public ModelItemsInfoComentariosSucursal(List<ModelInfoComentariosSucursal> infoComentariosSucursal) {
        this.infoComentariosSucursal = infoComentariosSucursal;
    }

    public void setInfoComentariosSucursal(List<ModelInfoComentariosSucursal> infoComentariosSucursal) {
        this.infoComentariosSucursal = infoComentariosSucursal;
    }

    public List<ModelInfoComentariosSucursal> getInfoComentariosSucursal() {
        return infoComentariosSucursal;
    }

}
