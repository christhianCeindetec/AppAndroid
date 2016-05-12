package ceindetec.mesabar.Controller;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;

import ceindetec.mesabar.Core.FunctionAppRestaurante;
import ceindetec.mesabar.Core.GlobalVars;
import ceindetec.mesabar.Core.LocationTracker;
import ceindetec.mesabar.R;
import ceindetec.mesabar.Transactions.TransactionAppRestaurante;

public class ControllerActivityMain extends AppCompatActivity {

    //Declaracion de los elementos que se utilizaran en la activity
    TextView txtTextView;

    RelativeLayout relativeLayout;

    //Declaracion de las transacciones que se utilizaran en la activity
    TransactionAppRestaurante transactionAppRestaurante;

    //TAG para el log de errores
    String TAG_ERROR_LOCATION = "Error: Location ";
    String TAG_ERROR_DATA = "Error: Data ";

    //URL's de acceso a los JSON de datos
    private static String URL_JSON_TOP_VISITADO = "getTopVisitado";
    private static String URL_JSON_TOP_PUNTUADO = "getTopPuntuado";
    private static String URL_JSON_TOP_EDITOR = "getTopEditor";

    private static String JSON_ID_KEY = "infoSucursal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            LocationTracker locationTracker;
            locationTracker = new LocationTracker(locationManager);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationTracker);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG_ERROR_LOCATION, e.toString());
        }

        /*TOP VISITADO ###########################################################################*/
        transactionAppRestaurante = new TransactionAppRestaurante(this);
        transactionAppRestaurante.getDataTransactionAppRestaurante(URL_JSON_TOP_VISITADO, JSON_ID_KEY, new TransactionAppRestaurante.VolleyCallback() {
            @Override
            public void onSuccess(JsonObject dataSucursal) {
                try {
                    txtTextView = (TextView) findViewById(R.id.txtTopVisitadoNombre);
                    txtTextView.setText(dataSucursal.get("nombre").getAsString());

                    txtTextView = (TextView) findViewById(R.id.txtTopVisitadoCategoria);
                    txtTextView.setText(dataSucursal.get("categoria").getAsString());

                    txtTextView = (TextView) findViewById(R.id.txtTopVisitadoDistancia);
                    txtTextView.setText(FunctionAppRestaurante.Distancia(
                                    GlobalVars.getGlobalVarsInstance().getPosLatitud(),
                                    dataSucursal.get("latitud").getAsDouble(),
                                    GlobalVars.getGlobalVarsInstance().getPosLongitud(),
                                    dataSucursal.get("longitud").getAsDouble()
                            )
                    );

                    txtTextView = (TextView) findViewById(R.id.txtTopVisitadoRating);
                    txtTextView.setText((String.valueOf(FunctionAppRestaurante.truncateDecimal(dataSucursal.get("puntuacion").getAsDouble(), 1))));

                    relativeLayout = (RelativeLayout) findViewById(R.id.rlyTopVisitado);
                    relativeLayout.setId(dataSucursal.get("sucursal").getAsInt());
                    relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), ControllerActivitySucursal.class);
                            intent.putExtra("idSucursal", view.getId());
                            view.getContext().startActivity(intent);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG_ERROR_DATA, e.toString());
                }


            }
        });

        /*TOP PUNTUADO ###########################################################################*/
        transactionAppRestaurante = new TransactionAppRestaurante(this);
        transactionAppRestaurante.getDataTransactionAppRestaurante(URL_JSON_TOP_PUNTUADO, JSON_ID_KEY, new TransactionAppRestaurante.VolleyCallback() {
            @Override
            public void onSuccess(JsonObject dataSucursal) {
                try {
                    txtTextView = (TextView) findViewById(R.id.txtTopPuntuadoNombre);
                    txtTextView.setText(dataSucursal.get("nombre").getAsString());

                    txtTextView = (TextView) findViewById(R.id.txtTopPuntuadoCategoria);
                    txtTextView.setText(dataSucursal.get("categoria").getAsString());

                    txtTextView = (TextView) findViewById(R.id.txtTopPuntuadoDistancia);
                    txtTextView.setText(FunctionAppRestaurante.Distancia(
                                    GlobalVars.getGlobalVarsInstance().getPosLatitud(),
                                    dataSucursal.get("latitud").getAsDouble(),
                                    GlobalVars.getGlobalVarsInstance().getPosLongitud(),
                                    dataSucursal.get("longitud").getAsDouble()
                            )
                    );

                    txtTextView = (TextView) findViewById(R.id.txtTopPuntuadoRating);
                    txtTextView.setText((String.valueOf(FunctionAppRestaurante.truncateDecimal(dataSucursal.get("puntuacion").getAsDouble(), 1))));

                    relativeLayout = (RelativeLayout) findViewById(R.id.rlyTopPuntuado);
                    relativeLayout.setId(dataSucursal.get("sucursal").getAsInt());
                    relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), ControllerActivitySucursal.class);
                            intent.putExtra("idSucursal", view.getId());
                            view.getContext().startActivity(intent);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG_ERROR_DATA, e.toString());
                }


            }
        });


        /*TOP EDITOR ###########################################################################*/
        transactionAppRestaurante = new TransactionAppRestaurante(this);
        transactionAppRestaurante.getDataTransactionAppRestaurante(URL_JSON_TOP_EDITOR, JSON_ID_KEY, new TransactionAppRestaurante.VolleyCallback() {
            @Override
            public void onSuccess(JsonObject dataSucursal) {
                try {
                    txtTextView = (TextView) findViewById(R.id.txtTopEditorNombre);
                    txtTextView.setText(dataSucursal.get("nombre").getAsString());

                    txtTextView = (TextView) findViewById(R.id.txtTopEditorCategoria);
                    txtTextView.setText(dataSucursal.get("categoria").getAsString());

                    txtTextView = (TextView) findViewById(R.id.txtTopEditorDistancia);
                    txtTextView.setText(FunctionAppRestaurante.Distancia(
                                    GlobalVars.getGlobalVarsInstance().getPosLatitud(),
                                    dataSucursal.get("latitud").getAsDouble(),
                                    GlobalVars.getGlobalVarsInstance().getPosLongitud(),
                                    dataSucursal.get("longitud").getAsDouble()
                            )
                    );

                    txtTextView = (TextView) findViewById(R.id.txtTopEditorRating);
                    txtTextView.setText((String.valueOf(FunctionAppRestaurante.truncateDecimal(dataSucursal.get("puntuacion").getAsDouble(), 1))));

                    relativeLayout = (RelativeLayout) findViewById(R.id.rlyTopEditor);
                    relativeLayout.setId(dataSucursal.get("sucursal").getAsInt());
                    relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), ControllerActivitySucursal.class);
                            intent.putExtra("idSucursal", view.getId());
                            view.getContext().startActivity(intent);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG_ERROR_DATA, e.toString());
                }


            }
        });

    }

    public void viewTopVisitado(View view) {
        Intent i = new Intent(this, ControllerActivityTopVisitado.class);
        startActivity(i);
    }

    public void viewTopPuntuado(View view) {
        Intent i = new Intent(this, ControllerActivityTopPuntuado.class);
        startActivity(i);
    }

    public void viewTopEditor(View view) {
        //Intent i = new Intent(this, ControllerActivityTopEditor.class);
        //startActivity(i);
        Intent i = new Intent(this, ControllerActivitySucursal.class);
        startActivity(i);
    }

    public void viewBusqueda(View view) {
        Intent i = new Intent(this, ControllerActivityBusqueda.class);
        startActivity(i);
    }
}


