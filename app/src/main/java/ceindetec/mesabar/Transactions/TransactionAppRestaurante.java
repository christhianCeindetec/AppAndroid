package ceindetec.mesabar.Transactions;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.util.HashMap;

import ceindetec.mesabar.Core.GlobalVars;
import ceindetec.mesabar.Core.GsonRequest;
import ceindetec.mesabar.Core.SingletonAppRestaurante;

public class TransactionAppRestaurante extends TextView {

    private static String URL_BASE = GlobalVars.getGlobalVarsInstance().getUrlBase();
    private static String URL_TYPE_FILE = GlobalVars.getGlobalVarsInstance().getUrlTypeFile();

    private static final String TAG_ERROR_JSON = "Error Json: ";
    private static final String TAG_ERROR_VOLLEY = "Error Volley: ";

    //Constructor de la clase TransactionArrayAdapter
    public TransactionAppRestaurante(Context context) {

        super(context);

    }

    //Constructor de la clase TransactionArrayAdapterSucursales
    public void getDataTransactionAppRestaurante(String txtUrl, final String idJson, final VolleyCallback callback) {

        // Añadir petición GSON a la cola
        SingletonAppRestaurante.getInstance(getContext()).addToRequestQueue(

                //Llamado al constructor de la clase GsonRequest
                new GsonRequest<JsonObject>(

                        //@param int method
                        Request.Method.GET,

                        //@param URL
                        URL_BASE + txtUrl + URL_TYPE_FILE,

                        //@param Class<T> clazz Clase o modelo en el que se formatean los datos
                        JsonObject.class,

                        //@param headers
                        null,

                        //@param Listener
                        new Response.Listener<JsonObject>() {
                            @Override
                            public void onResponse(JsonObject response) {
                                try {
                                    JsonArray jsonArray = response.getAsJsonArray(idJson);
                                    for (int i = 0; i < jsonArray.size(); i++) {
                                        JsonElement data = jsonArray.get(i);
                                        callback.onSuccess(data.getAsJsonObject());
                                    }
                                } catch (JsonIOException e) {
                                    e.printStackTrace();
                                    Log.e(TAG_ERROR_JSON, e.toString());
                                } catch (JsonParseException e) {
                                    e.printStackTrace();
                                    Log.e(TAG_ERROR_JSON, e.toString());
                                }
                            }
                        },
                        //@param errorListener
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG_ERROR_VOLLEY, error.getMessage());
                            }
                        }
                )
        );
    }

    //Constructor de la clase TransactionArrayAdapterSucursales
    public void getDataTransactionAppRestaurante(String txtUrl, final String idJson, HashMap<String, String> parameters, final VolleyCallback callback) {

        // Añadir petición GSON a la cola
        SingletonAppRestaurante.getInstance(getContext()).addToRequestQueue(

                //Llamado al constructor de la clase GsonRequest
                new GsonRequest<JsonObject>(

                        //@param int method
                        Request.Method.POST,

                        //@param URL
                        URL_BASE + txtUrl + URL_TYPE_FILE,

                        //@param Class<T> clazz Clase o modelo en el que se formatean los datos
                        JsonObject.class,

                        //@param headers
                        parameters,

                        //@param Listener
                        new Response.Listener<JsonObject>() {
                            @Override
                            public void onResponse(JsonObject response) {
                                try {
                                    JsonArray jsonArray = response.getAsJsonArray(idJson);
                                    for (int i = 0; i < jsonArray.size(); i++) {
                                        JsonElement data = jsonArray.get(i);
                                        callback.onSuccess(data.getAsJsonObject());
                                    }
                                } catch (JsonIOException e) {
                                    e.printStackTrace();
                                    Log.e(TAG_ERROR_JSON, e.toString());
                                } catch (JsonParseException e) {
                                    e.printStackTrace();
                                    Log.e(TAG_ERROR_JSON, e.toString());
                                }
                            }
                        },
                        //@param errorListener
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG_ERROR_VOLLEY, error.getMessage());
                            }
                        }
                )
        );
    }

    public interface VolleyCallback {
        void onSuccess(JsonObject data);
    }
}