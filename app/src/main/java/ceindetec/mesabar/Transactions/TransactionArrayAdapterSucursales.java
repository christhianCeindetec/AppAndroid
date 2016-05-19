package ceindetec.mesabar.Transactions;

import android.content.Context;
import android.content.Intent;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;

import ceindetec.mesabar.Controller.ControllerActivitySucursal;
import ceindetec.mesabar.Core.FunctionAppRestaurante;
import ceindetec.mesabar.Core.GlobalVars;
import ceindetec.mesabar.Core.GsonRequest;
import ceindetec.mesabar.Core.SingletonAppRestaurante;
import ceindetec.mesabar.Models.ModelInfoSucursal;
import ceindetec.mesabar.Models.ModelItemsInfoSucursal;
import ceindetec.mesabar.R;

public class TransactionArrayAdapterSucursales extends ArrayAdapter {

    private static String URL_BASE = GlobalVars.getGlobalVarsInstance().getUrlBase();
    private static final String TAG = "TransactionArrayAdapter";

    ModelItemsInfoSucursal modelItemsInfoSucursal;

    //Constructor de la clase TransactionArrayAdapterSucursales
    public TransactionArrayAdapterSucursales(Context context, String txtUrl) {

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
                        ModelItemsInfoSucursal.class,

                        //@param headers
                        null,

                        //@param Listener
                        new Response.Listener<ModelItemsInfoSucursal>() {
                            @Override
                            public void onResponse(ModelItemsInfoSucursal response) {
                                modelItemsInfoSucursal = response;
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
    public TransactionArrayAdapterSucursales(Context context, String txtUrl, HashMap<String, String> params) {

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
                        ModelItemsInfoSucursal.class,

                        //@param headers
                        params,

                        //@param Listener
                        new Response.Listener<ModelItemsInfoSucursal>() {
                            @Override
                            public void onResponse(ModelItemsInfoSucursal response) {
                                modelItemsInfoSucursal = response;
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
        return modelItemsInfoSucursal != null ? modelItemsInfoSucursal.getInfoSucursal().size() : 0;
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
                    R.layout.listview_info_sucursal,
                    parent,
                    false);
        } else listItemView = convertView;


        // Obtener el item actual
        ModelInfoSucursal item = modelItemsInfoSucursal.getInfoSucursal().get(position);

        // Obtener Views
        TextView txtNombre = (TextView) listItemView.findViewById(R.id.txtNombre);
        TextView txtCategoria = (TextView) listItemView.findViewById(R.id.txtCategoria);
        TextView txtDistancia = (TextView) listItemView.findViewById(R.id.txtDistancia);
        TextView txtRating = (TextView) listItemView.findViewById(R.id.txtRating);
        RelativeLayout rlyListView = (RelativeLayout) listItemView.findViewById(R.id.rlyListView);
        try {
            // Actualizar los Views
            txtNombre.setText(item.getNombre());
            txtCategoria.setText(item.getCategoria());
            txtDistancia.setText(FunctionAppRestaurante.Distancia(
                            Double.parseDouble(item.getLatitud()),
                            Double.parseDouble(item.getLongitud())
                    )
            );
            txtRating.setText(String.valueOf(FunctionAppRestaurante.truncateDecimal(Double.parseDouble(item.getPuntuacion()), 1)));
            rlyListView.setId(Integer.parseInt(item.getSucursal()));

            rlyListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ControllerActivitySucursal.class);
                    intent.putExtra("idSucursal", view.getId());
                    view.getContext().startActivity(intent);
                }
            });
        } catch (Exception error) {
            Log.e(TAG, error.toString());
        }
        return listItemView;
    }

}
