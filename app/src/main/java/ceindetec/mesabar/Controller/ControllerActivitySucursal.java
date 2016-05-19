package ceindetec.mesabar.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;

import java.util.HashMap;

import ceindetec.mesabar.Core.FunctionAppRestaurante;
import ceindetec.mesabar.Core.GlobalVars;
import ceindetec.mesabar.R;
import ceindetec.mesabar.Transactions.TransactionAppRestaurante;
import ceindetec.mesabar.Transactions.TransactionArrayAdapteMenuSucursal;
import ceindetec.mesabar.Transactions.TransactionArrayAdapterComentariosSucursal;
import ceindetec.mesabar.Transactions.TransactionArrayAdapterSucursales;

public class ControllerActivitySucursal extends FragmentActivity implements OnMapReadyCallback {

    //Declaracion de los elementos que se utilizaran en la activity
    TextView txtTextView;
    LinearLayout linearLayout;
    ListView lvListaComentariosSucursal;
    ListView lvListaMenuSucursal;
    ArrayAdapter adaptadorArregloComentariosSucursal;
    ArrayAdapter adaptadorArregloMenuSucursal;

    //Declaracion de las transacciones que se utilizaran en la activity
    TransactionAppRestaurante transactionAppRestaurante;

    String URL_JSON_COMENTARIOS = "getDataPuntuacionBySucursal";
    String URL_JSON_MENU = "getDataMenuBySucursal";

    //Posicion geograficas de sucursales
    double latitudSucursal = -666;
    double longitudSucursal = -666;
    String nombreSucursal = "Sucursal";

    //tags de informacion y errpres
    String TAG_ERROR_DATA = "Error: Data: ";
    String TAG_ERROR_BUNDLE = "Error: Bundle: ";
    String TAG_INFO_BUNDLE = "Info: Bundle: ";
    String TAG_ERROR_LOCATION = "Error: Location: ";

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursal);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapUbicacion);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            try {


                //Obtener datos de la id de la sucursal enviado por parametro
                Log.i(TAG_INFO_BUNDLE, bundle.get("idSucursal").toString());

                //Conversion de parametro a hashmap
                final HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("idSucursal", bundle.get("idSucursal").toString());

                //Creacion del encabezado
                transactionAppRestaurante = new TransactionAppRestaurante(this);
                transactionAppRestaurante.getDataTransactionAppRestaurante("getDataSucursalById", "infoSucursal", parameters, new TransactionAppRestaurante.VolleyCallback() {
                    @Override
                    public void onSuccess(JsonObject dataSucursal) {
                        try {

                            txtTextView = (TextView) findViewById(R.id.txtNombreSucursal);
                            txtTextView.setText(dataSucursal.get("nombre").getAsString());

                            txtTextView = (TextView) findViewById(R.id.txtCategoriaSucursal);
                            txtTextView.setText(dataSucursal.get("categoria").getAsString());

                            txtTextView = (TextView) findViewById(R.id.txtDistanciaSucursal);
                            txtTextView.setText(FunctionAppRestaurante.Distancia(
                                            GlobalVars.getGlobalVarsInstance().getPosLatitud(),
                                            dataSucursal.get("latitud").getAsDouble(),
                                            GlobalVars.getGlobalVarsInstance().getPosLongitud(),
                                            dataSucursal.get("longitud").getAsDouble()
                                    )
                            );

                            txtTextView = (TextView) findViewById(R.id.txtRatingSucursal);
                            txtTextView.setText((String.valueOf(FunctionAppRestaurante.truncateDecimal(dataSucursal.get("puntuacion").getAsDouble(), 1))));

                            //Obtener instancia de la lista
                            lvListaComentariosSucursal = (ListView) findViewById(R.id.lvListaComentariosSucursal);

                            //Crear adaptador
                            adaptadorArregloComentariosSucursal = new TransactionArrayAdapterComentariosSucursal(getBaseContext(), URL_JSON_COMENTARIOS);

                            //Ingresa el adaptador en la instancia de la lista
                            lvListaComentariosSucursal.setAdapter(adaptadorArregloComentariosSucursal);


                            //Obtener instancia de la lista
                            lvListaMenuSucursal = (ListView) findViewById(R.id.lvListaMenuSucursal);

                            //Crear adaptador
                            adaptadorArregloMenuSucursal = new TransactionArrayAdapteMenuSucursal(getBaseContext(), URL_JSON_MENU);

                            //Ingresa el adaptador en la instancia de la lista
                            lvListaMenuSucursal.setAdapter(adaptadorArregloMenuSucursal);

                            //Llenado de datos de las pestañas de la activity
                            TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
                            tabHost.setup();

                            //Tab 1
                            TabHost.TabSpec tabSpec = tabHost.newTabSpec(getResources().getString(R.string.tab_comentarios));
                            tabSpec.setContent(R.id.tabComentarios);
                            tabSpec.setIndicator(getResources().getString(R.string.tab_comentarios));
                            tabHost.addTab(tabSpec);

                            //Tab 2
                            tabSpec = tabHost.newTabSpec(getResources().getString(R.string.tab_menu));
                            tabSpec.setContent(R.id.tabMenu);
                            tabSpec.setIndicator(getResources().getString(R.string.tab_menu));
                            tabHost.addTab(tabSpec);

                            //Paso de parametros de ubicacion de la sucursal al map
                            latitudSucursal = dataSucursal.get("latitud").getAsDouble();
                            longitudSucursal = dataSucursal.get("longitud").getAsDouble();

                            //Paso de parametros nombre de la sucursal al map
                            nombreSucursal = dataSucursal.get("nombre").getAsString();

                            if (latitudSucursal == 0 && longitudSucursal == 0) {

                                linearLayout = (LinearLayout) findViewById(R.id.tabUbicacion);
                                linearLayout.setVisibility(View.INVISIBLE);

                            } else {

                                posicionarSucursal();

                                linearLayout = (LinearLayout) findViewById(R.id.tabUbicacion);
                                linearLayout.setVisibility(View.VISIBLE);

                                //Tab 3
                                tabSpec = tabHost.newTabSpec(getResources().getString(R.string.tab_ubicacion));
                                tabSpec.setContent(R.id.tabUbicacion);
                                tabSpec.setIndicator(getResources().getString(R.string.tab_ubicacion));
                                tabHost.addTab(tabSpec);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG_ERROR_DATA, e.toString());
                        }

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG_ERROR_BUNDLE, e.toString());
            }
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap_MapReady) {
        this.googleMap = googleMap_MapReady;
        try {
            posicionarSucursal();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG_ERROR_LOCATION, e.toString());
        }
    }

    public void posicionarSucursal() {
        LatLng latLng;
        if (latitudSucursal != -666 && longitudSucursal != -666) {
            latLng = new LatLng(latitudSucursal, longitudSucursal);
        } else {
            double latitude = GlobalVars.getGlobalVarsInstance().getPosLatitud();
            double longitude = GlobalVars.getGlobalVarsInstance().getPosLongitud();
            latLng = new LatLng(latitude, longitude);
        }

        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, (float) 16.5));

        this.googleMap.addMarker(
                new MarkerOptions()
                        .position(latLng)
                        .title(nombreSucursal)
                        .snippet("Ubicación de " + nombreSucursal)
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador))
        );
    }
}

