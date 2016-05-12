package ceindetec.mesabar.Transactions;

import android.content.Context;
import android.content.Intent;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import ceindetec.mesabar.Controller.ControllerActivityResultadoCategoriaAleatoria;
import ceindetec.mesabar.Core.GlobalVars;
import ceindetec.mesabar.Core.GsonRequest;
import ceindetec.mesabar.Core.SingletonAppRestaurante;

import ceindetec.mesabar.Models.ModelInfoAleatorio;
import ceindetec.mesabar.Models.ModelItemsInfoAleatorio;
import ceindetec.mesabar.R;

public class TransactionArrayAdapterAleatorio extends ArrayAdapter {

    private static String URL_BASE = GlobalVars.getGlobalVarsInstance().getUrlBase();
    private static final String TAG = "TransArrayAdapAleat";

    ModelItemsInfoAleatorio modelItemsInfoAleatorio;

    //Constructor de la clase TransactionArrayAdapterAleatorio
    public TransactionArrayAdapterAleatorio(Context context, String txtUrl) {

        super(context, 0);

        // Añadir petición GSON a la cola
        SingletonAppRestaurante.getInstance(getContext()).addToRequestQueue(

                //Llamado al constructor de la clase GsonRequest
                new GsonRequest<ModelItemsInfoAleatorio>(

                        //@param int method
                        Request.Method.GET,

                        //@param URL
                        URL_BASE + txtUrl + ".php",

                        //@param Class<T> clazz Clase o modelo en el que se formatean los datos
                        ModelItemsInfoAleatorio.class,

                        //@param headers
                        null,

                        //@param Listener
                        new Response.Listener<ModelItemsInfoAleatorio>() {
                            @Override
                            public void onResponse(ModelItemsInfoAleatorio response) {
                                modelItemsInfoAleatorio = response;
                                notifyDataSetChanged();
                            }
                        },
                        //@param errorListener
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, "Error Volley:" + error.getMessage());
                            }
                        }
                )
        );
    }


    @Override
    public int getCount() {
        return modelItemsInfoAleatorio != null ? modelItemsInfoAleatorio.getListAleatorio().size() : 0;
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
                    R.layout.listview_info_aleatorio,
                    parent,
                    false);
        } else listItemView = convertView;


        // Obtener el item actual
        ModelInfoAleatorio item = modelItemsInfoAleatorio.getListAleatorio().get(position);

        // Obtener Views
        final TextView txtNombre = (TextView) listItemView.findViewById(R.id.txtAleatorio);
        // Actualizar los Views
        try {

            txtNombre.setText(item.getNombre());
            txtNombre.setId(Integer.parseInt(item.getIdCategoria()));

            txtNombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getContext(), ControllerActivityResultadoCategoriaAleatoria.class);
                    intent.putExtra("nombreCategoria", ((TextView) view).getText());
                    intent.putExtra("idCategoria", view.getId());
                    view.getContext().startActivity(intent);
                }
            });

        } catch (Exception error) {
            Log.e(TAG, error.toString());
        }
        return listItemView;
    }

}
