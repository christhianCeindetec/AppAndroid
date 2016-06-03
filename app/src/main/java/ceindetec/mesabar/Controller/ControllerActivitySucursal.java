package ceindetec.mesabar.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import android.support.v4.app.FragmentActivity;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

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

import ceindetec.mesabar.Dialogs.DialogComentario;
import ceindetec.mesabar.Dialogs.DialogFavorito;

import ceindetec.mesabar.R;

import ceindetec.mesabar.Transactions.TransactionAppRestaurante;
import ceindetec.mesabar.Transactions.TransactionArrayAdapteMenuSucursal;
import ceindetec.mesabar.Transactions.TransactionArrayAdapterComentariosSucursal;

public class ControllerActivitySucursal
        extends
        FragmentActivity
        implements
        OnMapReadyCallback,
        DialogFavorito.onDialogFavoritoListener,
        DialogComentario.onDialogComentarioListener {

    //Declaracion de los elementos que se utilizaran en la activity
    TextView txtTextView;
    LinearLayout linearLayout;
    ListView lvListaComentariosSucursal;
    ListView lvListaMenuSucursal;
    ImageView imgFavoritoSucursal;
    ArrayAdapter adaptadorArregloComentariosSucursal;
    ArrayAdapter adaptadorArregloMenuSucursal;
    Button btnAgregarComentario;
    Bitmap bmpIconoFavorito;
    //Declaracion de las transacciones que se utilizaran en la activity
    TransactionAppRestaurante transactionAppRestaurante;

    //Posicion geograficas de sucursales
    double latitudSucursal = -666;
    double longitudSucursal = -666;
    double puntuacionSucursal = 0;
    String categoriaSucursal = "Categoria";
    String nombreSucursal = "Sucursal";

    //tags de informacion y errores
    String TAG_ERROR_DATA = "Error: Data: ";
    String TAG_ERROR_BUNDLE = "Error: Bundle: ";
    String TAG_ERROR_LOCATION = "Error: Location: ";

    //TransactionArrayAdapterComentariosSucursal
    String URL_JSON_COMENTARIOS = "getDataPuntuacionBySucursal";

    //TransactionArrayAdapteMenuSucursal
    String URL_JSON_MENU = "getDataMenuBySucursal";

    //TransactionAppRestaurante
    String URL_JSON_DATA_SUCURSAL = "getDataSucursalById";
    String JSON_ID_KEY_SUCURSAL = "infoSucursal";

    //TransactionAppRestaurante
    String URL_JSON_FAVORITO = "getFavoritoSucursalByUsuario";
    String JSON_ID_KEY_FAVORITO = "infoFavorito";

    //Argumentos pasados al dialogFragment
    Bundle argsDialogFavorito = new Bundle();
    Bundle argsDialogComentario = new Bundle();
    boolean estadoFavorito; //true:activo false:inactivo

    String ID_SUCURSAL;
    String ID_USUARIO;

    private GoogleMap googleMap;

    TabHost tabHost;
    TabHost.TabSpec tabSpec;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursal);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapUbicacion);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            try {

                //Almacena el id de la sucursal y el usuario
                ID_SUCURSAL = bundle.get("idSucursal").toString();
                ID_USUARIO = "7";

                //Conversion de parametro a hashmap
                final HashMap<String, String> parameters = new HashMap<>();
                parameters.put("idSucursal", ID_SUCURSAL);

                //Creacion del encabezado
                transactionAppRestaurante = new TransactionAppRestaurante(this);
                transactionAppRestaurante.getDataTransactionAppRestaurante(URL_JSON_DATA_SUCURSAL, JSON_ID_KEY_SUCURSAL, parameters, new TransactionAppRestaurante.VolleyCallback() {
                    @Override
                    public void onSuccess(JsonObject dataSucursal) {
                        try {

                            latitudSucursal = dataSucursal.get("latitud").getAsDouble(); // paso de la latitud
                            longitudSucursal = dataSucursal.get("longitud").getAsDouble(); // paso de la longitud
                            puntuacionSucursal = dataSucursal.get("puntuacion").getAsDouble(); // paso de la puntuacion
                            categoriaSucursal = dataSucursal.get("categoria").getAsString(); // paso de la categoria
                            nombreSucursal = dataSucursal.get("nombre").getAsString(); // paso del nombre de la sucursal

                            // ENCABEZADO DE LA ACTIVITY *******************************************
                            cargarEncabezadoSucursal();

                            // PESTAÑAS DE LA ACTIVITY *********************************************

                            //Llenado de datos de las pestañas de la activity
                            tabHost = (TabHost) findViewById(R.id.tabHost);
                            tabHost.setup();

                            // PESTAÑA COMENTARIOS *************************************************
                            cargarTabComentario();

                            // PESTAÑA MENU ********************************************************
                            cargarTabMenu();

                            // PESTAÑA MAPA ********************************************************
                            cargarTabMapa();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG_ERROR_DATA, e.toString());
                        }
                    }
                });

                //Conversion de parametro a hashmap
                final HashMap<String, String> parametersFavoritos = new HashMap<>();
                parametersFavoritos.put("idSucursal", ID_SUCURSAL);
                parametersFavoritos.put("idUsuario", ID_USUARIO);

                transactionAppRestaurante.getDataTransactionAppRestaurante(URL_JSON_FAVORITO, JSON_ID_KEY_FAVORITO, parametersFavoritos, new TransactionAppRestaurante.VolleyCallback() {
                    @Override
                    public void onSuccess(JsonObject infoFavorito) {
                        try {
                            imgFavoritoSucursal = (ImageView) findViewById(R.id.imgFavoritoSucursal);
                            Bitmap bitmap;

                            if (infoFavorito.get("favorito").getAsBoolean()) {
                                estadoFavorito = true;
                                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.icon_del_favorite);
                                imgFavoritoSucursal.setImageBitmap(bitmap);
                            } else {
                                estadoFavorito = false;
                                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.icon_add_favorite);
                                imgFavoritoSucursal.setImageBitmap(bitmap);
                            }

                            imgFavoritoSucursal.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    argsDialogFavorito.clear();
                                    argsDialogFavorito.putString("idSucursal", ID_SUCURSAL);
                                    argsDialogFavorito.putString("idUsuario", ID_USUARIO);
                                    if (estadoFavorito)
                                        argsDialogFavorito.putString("accion", "del");
                                    else
                                        argsDialogFavorito.putString("accion", "add");

                                    FragmentTransaction fragmentTransactionRemover = getSupportFragmentManager().beginTransaction();
                                    DialogFavorito dialogFavorito = new DialogFavorito();
                                    dialogFavorito.setArguments(argsDialogFavorito);
                                    dialogFavorito.show(fragmentTransactionRemover, "Gestion Favoritos");
                                }
                            });

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

    private void cargarEncabezadoSucursal() {

        // NOMBRE DE LA SUCURSAL ***********************************************

        //Agregando el valor del nombre de la sucursal
        txtTextView = (TextView) findViewById(R.id.txtNombreSucursal);
        txtTextView.setText(nombreSucursal);

        // CATEGORIA DE LA SUCURSAL ********************************************

        //Agregando el valor de la categoria de la sucursal
        txtTextView = (TextView) findViewById(R.id.txtCategoriaSucursal);
        txtTextView.setText(categoriaSucursal);

        // DISTANCIA DE LA SUCURSAL ********************************************

        //Agregando el valor de la distancia de la sucursal
        txtTextView = (TextView) findViewById(R.id.txtDistanciaSucursal);
        txtTextView.setText(FunctionAppRestaurante.Distancia(
                GlobalVars.getGlobalVarsInstance().getPosLatitud(),
                latitudSucursal,
                GlobalVars.getGlobalVarsInstance().getPosLongitud(),
                longitudSucursal
        ));

        // PUNTUACION **********************************************************

        //Agregando el valor de la puntuacion
        txtTextView = (TextView) findViewById(R.id.txtRatingSucursal);
        txtTextView.setText((String.valueOf(FunctionAppRestaurante.truncateDecimal(puntuacionSucursal, 1))));

    }

    private void cargarTabMapa() {
        //Obteniendo una instacia de la capa linear contenida en la pestaña tabUbicacion
        linearLayout = (LinearLayout) findViewById(R.id.tabUbicacion);

        //Si no se encuentran datos de la posicion de la sucursal no muestra la pestaña del mapa
        if (latitudSucursal == 0 && longitudSucursal == 0) {
            linearLayout.setVisibility(View.INVISIBLE);
        } else {
            posicionarGeograficamenteSucursal();
            linearLayout.setVisibility(View.VISIBLE);

            //Tab 3: tab que muestra el mapa con la ubicacion
            tabSpec = tabHost.newTabSpec(getResources().getString(R.string.sucursal_tab_tabUbicacion));
            tabSpec.setContent(R.id.tabUbicacion);
            tabSpec.setIndicator(getResources().getString(R.string.sucursal_tab_tabUbicacion));
            tabHost.addTab(tabSpec);
        }
    }


    private void cargarTabMenu() {

        //Obtener instancia de la lista lvListaMenuSucursal
        lvListaMenuSucursal = (ListView) findViewById(R.id.lvListaMenuSucursal);

        //Crear adaptador para la lista lvListaMenuSucursal
        adaptadorArregloMenuSucursal = new TransactionArrayAdapteMenuSucursal(getBaseContext(), URL_JSON_MENU);

        //Ingresa el adaptador en la instancia de la lista lvListaMenuSucursal
        lvListaMenuSucursal.setAdapter(adaptadorArregloMenuSucursal);

        //Tab 2: Tab que muestra la lista de menus
        tabSpec = tabHost.newTabSpec(getResources().getString(R.string.sucursal_tab_tabMenu));
        tabSpec.setContent(R.id.tabMenu);
        tabSpec.setIndicator(getResources().getString(R.string.sucursal_tab_tabMenu));
        tabHost.addTab(tabSpec);

    }

    private void cargarTabComentario() {

        btnAgregarComentario = (Button) findViewById(R.id.btnAgregarComentario);
        btnAgregarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarComentario(view);
            }
        });

        //Obtener instancia de la lista lvListaComentariosSucursal
        lvListaComentariosSucursal = (ListView) findViewById(R.id.lvListaComentariosSucursal);

        //Crear adaptador para la lista lvListaComentariosSucursal
        adaptadorArregloComentariosSucursal = new TransactionArrayAdapterComentariosSucursal(getBaseContext(), URL_JSON_COMENTARIOS);

        //Ingresa el adaptador en la instancia de la lista lvListaComentariosSucursal
        lvListaComentariosSucursal.setAdapter(adaptadorArregloComentariosSucursal);

        //Tab 1: Tab que muestra la lista de comentarios
        tabSpec = tabHost.newTabSpec(getResources().getString(R.string.sucursal_tab_tabComentario));
        tabSpec.setContent(R.id.tabComentarios);
        tabSpec.setIndicator(getResources().getString(R.string.sucursal_tab_tabComentario));
        tabHost.addTab(tabSpec);

    }

    // METODOS QUE POSICIONA GEOGRAFICAMENTE LOS PUNTOS TOMADOS DESDE LA BASE DE DATOS EN EL MAPA##

    private void posicionarGeograficamenteSucursal() {
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

    // METODOS QUE LLAMA EL DIALOG PARA AGREGAR COMENTARIO ########################################

    private void agregarComentario(View view) {

        argsDialogComentario.putString("idSucursal", ID_SUCURSAL);
        argsDialogComentario.putString("idUsuario", ID_USUARIO);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DialogComentario dialogComentario = new DialogComentario();
        dialogComentario.setArguments(argsDialogComentario);
        dialogComentario.show(fragmentTransaction, "DialogComentario");

    }

    // METODOS DE RESPUESTA IMPLEMENTADOS DEL DIALOG COMENTARIO ###################################

    @Override
    public void onAddComentario() {
        cargarTabComentario();
        Toast.makeText(getBaseContext(), getResources().getString(R.string.sucursal_message_comentarioAgregado), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorAddComentario() {
        Toast.makeText(getBaseContext(), getResources().getString(R.string.sucursal_message_comentarioError), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNegativeButtonClickComentario() {

    }

    // METODOS DE RESPUESTA IMPLEMENTADOS DEL DIALOG FAVORITO #####################################

    @Override
    public void onErrorAddFavorito() {
        Toast.makeText(getBaseContext(), getResources().getString(R.string.sucursal_message_favoritoError), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddFavorito() {
        estadoFavorito = true;
        bmpIconoFavorito = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.icon_del_favorite);
        imgFavoritoSucursal.setImageBitmap(bmpIconoFavorito);
        Toast.makeText(getBaseContext(), getResources().getString(R.string.sucursal_message_favoritoAgregado), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDelFavorito() {
        estadoFavorito = false;
        bmpIconoFavorito = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.icon_add_favorite);
        imgFavoritoSucursal.setImageBitmap(bmpIconoFavorito);
        Toast.makeText(getBaseContext(), getResources().getString(R.string.sucursal_message_favoritoRemovido), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNegativeButtonClickFavorito() {
    }

    // METODOS DE RESPUESTA IMPLEMENTADOS DEL MAP #################################################

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
            posicionarGeograficamenteSucursal();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG_ERROR_LOCATION, e.toString());
        }
    }

}

