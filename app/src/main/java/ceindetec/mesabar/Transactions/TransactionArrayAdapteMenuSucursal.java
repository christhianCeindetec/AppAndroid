package ceindetec.mesabar.Transactions;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;

import ceindetec.mesabar.Core.GlobalVars;
import ceindetec.mesabar.Core.GsonRequest;
import ceindetec.mesabar.Core.SingletonAppRestaurante;
import ceindetec.mesabar.Models.ModelInfoMenuSucursal;
import ceindetec.mesabar.Models.ModelItemsInfoMenuSucursal;
import ceindetec.mesabar.R;

public class TransactionArrayAdapteMenuSucursal extends ArrayAdapter {

    private static String URL_BASE = GlobalVars.getGlobalVarsInstance().getUrlBase();
    private static final String TAG = "TransactionArrayAdapter";

    ModelItemsInfoMenuSucursal modelItemsInfoMenuSucursal;

    //Constructor de la clase TransactionArrayAdapterSucursales
    public TransactionArrayAdapteMenuSucursal(Context context, String txtUrl) {

        super(context, 0);

        // Añadir petición GSON a la cola
        SingletonAppRestaurante.getInstance(getContext()).addToRequestQueue(

                //Llamado al constructor de la clase GsonRequest
                new GsonRequest<>(

                        //@param int method
                        Request.Method.GET,

                        //@param URL
                        URL_BASE + txtUrl + ".php",

                        //@param Class<T> clazz Clase o modelo en el que se formatean los datos
                        ModelItemsInfoMenuSucursal.class,

                        //@param headers
                        null,

                        //@param Listener
                        new Response.Listener<ModelItemsInfoMenuSucursal>() {
                            @Override
                            public void onResponse(ModelItemsInfoMenuSucursal response) {
                                modelItemsInfoMenuSucursal = response;
                                notifyDataSetChanged();
                            }
                        },
                        //@param errorListener
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley:" + error.getMessage());
                            }
                        }
                )
        );
    }

    //Constructor de la clase TransactionArrayAdapter
    public TransactionArrayAdapteMenuSucursal(Context context, String txtUrl, HashMap<String, String> params) {

        super(context, 0);

        // Añadir petición GSON a la cola
        SingletonAppRestaurante.getInstance(getContext()).addToRequestQueue(

                //Llamado al constructor de la clase GsonRequest
                new GsonRequest<>(

                        //@param int method
                        Request.Method.POST,

                        //@param URL
                        URL_BASE + txtUrl + ".php",

                        //@param Class<T> clazz Clase o modelo en el que se formatean los datos
                        ModelItemsInfoMenuSucursal.class,

                        //@param headers
                        params,

                        //@param Listener
                        new Response.Listener<ModelItemsInfoMenuSucursal>() {
                            @Override
                            public void onResponse(ModelItemsInfoMenuSucursal response) {
                                modelItemsInfoMenuSucursal = response;
                                notifyDataSetChanged();
                            }
                        },
                        //@param errorListener
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley:" + error.getMessage());
                            }
                        }
                )
        );
    }

    @Override
    public int getCount() {
        return modelItemsInfoMenuSucursal != null ? modelItemsInfoMenuSucursal.getInfoMenuSucursal().size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // View auxiliar
        View listItemView;

        //Comprobando si el View no existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo
            listItemView = layoutInflater.inflate(
                    R.layout.listview_menu_sucursal,
                    parent,
                    false);
        } else listItemView = convertView;


        // Obtener el item actual
        ModelInfoMenuSucursal item = modelItemsInfoMenuSucursal.getInfoMenuSucursal().get(position);

        // Obtener Views
        TextView txtNombreMenuSucursal = (TextView) listItemView.findViewById(R.id.txtNombreMenuSucursal);
        TextView txtDescripcionMenuSucursal = (TextView) listItemView.findViewById(R.id.txtDescripcionMenuSucursal);
        TextView txtNombreCategoria = (TextView) listItemView.findViewById(R.id.txtNombreCategoria);

        try {

            // Actualizar los Views
            txtNombreMenuSucursal.setText(item.getNombreMenuSucursal());
            txtDescripcionMenuSucursal.setText(item.getDescripcionMenuSucursal());
            txtNombreCategoria.setText(item.getNombreCategoria1());

        } catch (Exception error) {
            Log.e(TAG, error.toString());
        }
        return listItemView;
    }

}
