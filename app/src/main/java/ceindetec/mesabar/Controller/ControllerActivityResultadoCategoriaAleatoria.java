package ceindetec.mesabar.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ceindetec.mesabar.R;
import ceindetec.mesabar.Transactions.TransactionArrayAdapterSucursales;

import java.util.HashMap;

public class ControllerActivityResultadoCategoriaAleatoria extends AppCompatActivity {

    // Atributos locales del controlador
    ListView lvListaResultado;
    ArrayAdapter adaptadorArregloResultado;
    TextView txtTextView;

    private static String URL_JSON_CATEGORIA_RESULTADO = "getListCategoriaResultado";

    //TAG para el log de errores
    String TAG_ERROR_DATA = "Error: Data: ";
    String TAG_ERROR_BUNDLE = "Error: Bundle: ";

    //Metodo que se ejecuta en la creacion de la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busqueda);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            try {

                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("idCategoria", bundle.get("idCategoria").toString());

                txtTextView = (TextView) findViewById(R.id.txtResultadoBusquedaTitulo);
                txtTextView.setText(bundle.get("nombreCategoria").toString());

                //Obtener instancia de la lista
                lvListaResultado = (ListView) findViewById(R.id.lvListaResultadoCategoria);

                //Crear adaptador
                adaptadorArregloResultado = new TransactionArrayAdapterSucursales(this, URL_JSON_CATEGORIA_RESULTADO, parameters);

                //Ingresa el adaptador en la instancia de la lista
                lvListaResultado.setAdapter(adaptadorArregloResultado);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG_ERROR_BUNDLE, e.toString());
            }

        }
    }
}