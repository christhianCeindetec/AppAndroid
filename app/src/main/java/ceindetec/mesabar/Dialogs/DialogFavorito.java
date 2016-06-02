package ceindetec.mesabar.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import ceindetec.mesabar.R;

/**
 * Fragmento con diálogo básico
 */
public class DialogFavorito extends DialogFragment  {

    public DialogFavorito() {
    }

    public interface onDialogFavoritoListener {
        void onPossitiveButtonClick();
        void onNegativeButtonClick();
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
     * @return Nuevo diálogo
     */
    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String accion = getArguments().getString("accion");
        String mensaje="";
        if(accion.equals("add"))
            mensaje=getResources().getString(R.string.agregar_favorito);
        else
            mensaje=getResources().getString(R.string.remover_favorito);

        builder.setTitle(R.string.favorito)
                .setMessage(mensaje)
                .setPositiveButton(R.string.opcion_si,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialogFavoritoListener.onPossitiveButtonClick();
                            }
                        })
                .setNegativeButton(R.string.opcion_cancelar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialogFavoritoListener.onNegativeButtonClick();
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
                            " no implementó onDialogFavoritoListener");

        }
    }

}

