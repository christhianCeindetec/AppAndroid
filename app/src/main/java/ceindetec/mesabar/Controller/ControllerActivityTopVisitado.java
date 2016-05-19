package ceindetec.mesabar.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ceindetec.mesabar.R;
import ceindetec.mesabar.Transactions.TransactionArrayAdapterSucursales;

public class ControllerActivityTopVisitado extends AppCompatActivity {

    // Atributos locales del controlador
    ListView lvListaTop;
    ArrayAdapter adaptadorArregloTop;

    private static String URL_JSON = "getListTopVisitadoOrderConteoPuntuacion";

    //Metodo que se ejecuta en la creacion de la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_visitado);

        //Obtener instancia de la lista
        lvListaTop = (ListView) findViewById(R.id.lvListaTopVisitado);

        //Crear adaptador
        adaptadorArregloTop = new TransactionArrayAdapterSucursales(this, URL_JSON);

        //Ingresa el adaptador en la instancia de la lista
        lvListaTop.setAdapter(adaptadorArregloTop);


    }
}
