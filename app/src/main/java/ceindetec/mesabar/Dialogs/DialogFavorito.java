package ceindetec.mesabar.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.HashMap;

import ceindetec.mesabar.R;
import ceindetec.mesabar.Transactions.TransactionAppRestaurante;

/**
 * Fragmento con diálogo básico
 */
public class DialogFavorito extends DialogFragment {

    //Declaracion de las transacciones que se utilizaran en la activity
    TransactionAppRestaurante transactionAppRestaurante;

    //TransactionAppRestaurante
    String URL_JSON_FAVORITO_GESTION = "gestionFavoritoSucursalByUsuario";
    String JSON_ID_KEY_FAVORITO = "infoFavorito";

    //tags de informacion y errores
    String TAG_ERROR_DATA = "Error: Data: ";

    public DialogFavorito() {
    }

    public interface onDialogFavoritoListener {
        void onNegativeButtonClickFavorito();

        void onErrorAddFavorito();

        void onAddFavorito();

        void onDelFavorito();
    }

    // Interfaz de comunicación
    DialogFavorito.onDialogFavoritoListener dialogFavoritoListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createSimpleDialog();
    }

    /**
     * Crea un diálogo de alerta sencillo
     *
     * @return Nuevo diálogo
     */
    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String accion = getArguments().getString("accion");
        String mensaje = null;

        if (accion == null)
            dialogFavoritoListener.onErrorAddFavorito();
        else if (accion.equals("add"))
            mensaje = getResources().getString(R.string.sucursal_message_favoritoConfirmarAgregar);
        else
            mensaje = getResources().getString(R.string.sucursal_message_favoritoConfirmarRemover);

        builder.setTitle(R.string.dialogFavorito_title_favorito)
                .setMessage(mensaje)
                .setPositiveButton(R.string.general_button_opcionSi,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Conversion de parametro a hashmap
                                final HashMap<String, String> parameters = new HashMap<>();
                                parameters.put("idUsuario", getArguments().getString("idUsuario"));
                                parameters.put("idSucursal", getArguments().getString("idSucursal"));

                                transactionAppRestaurante = new TransactionAppRestaurante(getContext());
                                transactionAppRestaurante.getDataTransactionAppRestaurante(URL_JSON_FAVORITO_GESTION, JSON_ID_KEY_FAVORITO, parameters, new TransactionAppRestaurante.VolleyCallback() {
                                    @Override
                                    public void onSuccess(JsonObject dataFavorito) {
                                        try {
                                            switch (dataFavorito.get("codigo").getAsString()) {

                                                case "ERROR_SELECT":
                                                case "ERROR_INSERT":
                                                case "ERROR_UPDATE":
                                                case "NO_INSERT":
                                                case "NO_UPDATE":

                                                    dialogFavoritoListener.onErrorAddFavorito();

                                                    break;

                                                case "OK_INSERT":

                                                    dialogFavoritoListener.onAddFavorito();

                                                    break;

                                                case "OK_UPDATE":

                                                    if (dataFavorito.get("estado").getAsInt() == 1)
                                                        dialogFavoritoListener.onAddFavorito();
                                                    else
                                                        dialogFavoritoListener.onDelFavorito();
                                                    break;
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Log.e(TAG_ERROR_DATA, e.toString());
                                            dialogFavoritoListener.onErrorAddFavorito();
                                        }

                                    }
                                });
                            }
                        })
                .setNegativeButton(R.string.general_button_opcionCancelar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialogFavoritoListener.onNegativeButtonClickFavorito();
                            }
                        });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {

            dialogFavoritoListener = (DialogFavorito.onDialogFavoritoListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() +
                            " no implementó dialogFavoritoListener");

        }
    }

}

