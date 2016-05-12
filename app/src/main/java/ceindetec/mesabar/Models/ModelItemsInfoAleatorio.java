package ceindetec.mesabar.Models;

import java.util.List;

public class ModelItemsInfoAleatorio {

    // Encapsulamiento de Posts
    private List<ModelInfoAleatorio> listAleatorio;

    public ModelItemsInfoAleatorio(List<ModelInfoAleatorio> listAleatorio) {
        this.listAleatorio = listAleatorio;
    }

    public void setListAleatorio(List<ModelInfoAleatorio> listAleatorio) {
        this.listAleatorio = listAleatorio;
    }

    public List<ModelInfoAleatorio> getListAleatorio() {
        return listAleatorio;
    }

}
