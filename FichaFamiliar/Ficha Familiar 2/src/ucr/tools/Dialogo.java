package ucr.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import ucr.ff.R;

/**
 * Created by Batman on 13/12/2014.
 */

public class Dialogo extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.escogerpersona, null))
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    public interface notificador{
        public void onDialogPositiveClick(DialogFragment dialogo);
        public void onDialogNegativeClick(DialogFragment dialogo);
    }

    notificador mi_notificador;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mi_notificador = (notificador) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "Debe imlementar un notificador");
        }
    }
}