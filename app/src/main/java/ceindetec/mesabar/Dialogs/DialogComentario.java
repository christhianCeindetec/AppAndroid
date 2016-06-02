package ceindetec.mesabar.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.HashMap;

import ceindetec.mesabar.R;
import ceindetec.mesabar.Transactions.TransactionAppRestaurante;

/**
 * Fragmento con un diálogo personalizado
 */
public class DialogComentario extends DialogFragment {
    private static final String TAG = DialogComentario.class.getSimpleName();

    private static final String TAG_ERROR_INS_COMENTARIO = "Error: Ins com";
    private static final String URL_JSON_INSERTAR_COMENTARIO = "insComentario";
    private static final String JSON_ID_KEY_RESULT_COMENTARIO = "insercionComentarioSucursal";

    //Declaracion de las transacciones que se utilizaran en la activity
    TransactionAppRestaurante transactionAppRestaurante;

    public DialogComentario() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogComentario();
    }

    /**
     * Crea un diálogo con personalizado para comportarse
     * como formulario de comentario
     *
     * @return Diálogo
     */
    public AlertDialog createDialogComentario() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_comentario, null);

        builder.setView(v);

        Button btnCancelarComentario = (Button) v.findViewById(R.id.btnCancelarComentario);
        Button btnAceptarComentario = (Button) v.findViewById(R.id.btnAceptarComentario);

        final EditText edtTituloComentario = (EditText) v.findViewById(R.id.edtTituloComentario);
        final EditText edtTextoComentario = (EditText) v.findViewById(R.id.edtTextoComentario);
        final RatingBar rtbPuntajeComentario = (RatingBar) v.findViewById(R.id.rtbPuntajeComentario);

        btnCancelarComentario.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dismiss();
                    }
                }
        );

        btnAceptarComentario.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Conversion de parametro a hashmap
                        final HashMap<String, String> parameters = new HashMap<String, String>();
                        parameters.put("idUsuario", getArguments().getString("idUsuario"));
                        parameters.put("idSucursal", getArguments().getString("idSucursal"));
                        parameters.put("tituloComentario", edtTituloComentario.getText().toString());
                        parameters.put("textoComentario", edtTextoComentario.getText().toString());
                        parameters.put("puntajeComentario", String.valueOf(rtbPuntajeComentario.getRating()));

                        transactionAppRestaurante = new TransactionAppRestaurante(v.getContext());
                        transactionAppRestaurante.getDataTransactionAppRestaurante(URL_JSON_INSERTAR_COMENTARIO, JSON_ID_KEY_RESULT_COMENTARIO, parameters, new TransactionAppRestaurante.VolleyCallback() {
                            @Override
                            public void onSuccess(JsonObject dataSucursal) {
                                try {

                                    Log.wtf("usuario  Comentario", getArguments().getString("idUsuario"));
                                    Log.wtf("sucursal  Comentario", getArguments().getString("idSucursal"));
                                    Log.wtf("titulo Comentario", edtTituloComentario.getText().toString());
                                    Log.wtf("texto  Comentario", edtTextoComentario.getText().toString());
                                    Log.wtf("puntake  Comentario", String.valueOf(rtbPuntajeComentario.getRating()));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e(TAG_ERROR_INS_COMENTARIO, e.toString());
                                }
                            }
                        });

                        dismiss();
                    }
                }
        );

        return builder.create();
    }

}

