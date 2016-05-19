package ceindetec.mesabar.Transactions;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.sql.Date;
import java.util.HashMap;

import ceindetec.mesabar.Core.FunctionAppRestaurante;
import ceindetec.mesabar.Core.GlobalVars;
import ceindetec.mesabar.Core.GsonRequest;
import ceindetec.mesabar.Core.SingletonAppRestaurante;
import ceindetec.mesabar.Models.ModelInfoComentariosSucursal;
import ceindetec.mesabar.Models.ModelItemsInfoComentariosSucursal;
import ceindetec.mesabar.R;

public class TransactionArrayAdapterComentariosSucursal extends ArrayAdapter {

    private static String URL_BASE = GlobalVars.getGlobalVarsInstance().getUrlBase();
    private static final String TAG = "TransactionArrayAdapter";

    ModelItemsInfoComentariosSucursal modelItemsInfoComentariosSucursal;

    //Constructor de la clase TransactionArrayAdapterSucursales
    public TransactionArrayAdapterComentariosSucursal(Context context, String txtUrl) {

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
                        ModelItemsInfoComentariosSucursal.class,

                        //@param headers
                        null,

                        //@param Listener
                        new Response.Listener<ModelItemsInfoComentariosSucursal>() {
                            @Override
                            public void onResponse(ModelItemsInfoComentariosSucursal response) {
                                modelItemsInfoComentariosSucursal = response;
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
    public TransactionArrayAdapterComentariosSucursal(Context context, String txtUrl, HashMap<String, String> params) {

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
                        ModelItemsInfoComentariosSucursal.class,

                        //@param headers
                        params,

                        //@param Listener
                        new Response.Listener<ModelItemsInfoComentariosSucursal>() {
                            @Override
                            public void onResponse(ModelItemsInfoComentariosSucursal response) {
                                modelItemsInfoComentariosSucursal = response;
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
        return modelItemsInfoComentariosSucursal != null ? modelItemsInfoComentariosSucursal.getInfoComentariosSucursal().size() : 0;
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
                    R.layout.listview_comentarios_sucursal,
                    parent,
                    false);
        } else listItemView = convertView;


        // Obtener el item actual
        ModelInfoComentariosSucursal item = modelItemsInfoComentariosSucursal.getInfoComentariosSucursal().get(position);

        // Obtener Views
        TextView txtTituloComentario = (TextView) listItemView.findViewById(R.id.txtTituloComentario);
        TextView txtTextoComentario = (TextView) listItemView.findViewById(R.id.txtTextoComentario);
        TextView txtPuntuacionComentario = (TextView) listItemView.findViewById(R.id.txtPuntuacionComentario);
        TextView txtFechaComentario = (TextView) listItemView.findViewById(R.id.txtFechaComentario);
        ImageView imgAvatarComentario = (ImageView) listItemView.findViewById(R.id.imgAvatarComentario);
        try {

            // Actualizar los Views
            byte[] avatarComentario = Base64.decode(item.getAvatarComentario().getBytes(), Base64.DEFAULT);
            imgAvatarComentario.setImageBitmap(BitmapFactory.decodeByteArray(avatarComentario, 0, avatarComentario.length));
            txtTituloComentario.setText(item.getTituloComentario());
            txtTextoComentario.setText(item.getTextoComentario());
            txtPuntuacionComentario.setText(String.valueOf(FunctionAppRestaurante.truncateDecimal(Double.parseDouble(item.getPuntuacionComentario()), 1)));
            txtFechaComentario.setText(String.valueOf(Date.valueOf(item.getFechaComentario())));
        } catch (Exception error) {
            Log.e(TAG, error.toString());
        }
        return listItemView;
    }

}
