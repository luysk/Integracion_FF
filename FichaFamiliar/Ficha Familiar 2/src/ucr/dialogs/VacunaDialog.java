package ucr.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import ucr.database.SQLiteAdapter;
import android.database.Cursor;
import android.app.AlertDialog.Builder;
import android.widget.Toast;

/**
 * Created by Laura on 11/18/2014.
 */
public class VacunaDialog extends DialogFragment {
    /*
        private String tarjeta[] = {
                "2/05/1998  --- Vacunacion     -- Rubeola y Sarampion",
                "7/11/1999  --- Vacunacion     -- Paperas",
                "12/05/2002 --- Vacunacion     -- Rubeola y Sarampion",
                "7/11/2004  --- Vacunacion     -- Paperas",
                "2/05/2006  --- Vacunacion     -- Rubeola y Sarampion"};*/
    //Conexion con base de datos SQLite
    private SQLiteAdapter adapter;
    boolean esta = false;
    private String tarjeta[] = {
            "2/05/1998     -- Rubeola y Sarampion",
            "7/11/1999     -- Paperas",
            "12/05/2002    -- Rubeola y Sarampion",
            "7/11/2004     -- Paperas",
            "2/05/2006     -- Rubeola y Sarampion"};

        private String selectedOption;
        private Builder historialBuilder;
        private static String consec;
        private static String nombAp = "";


        public VacunaDialog() {
            // TODO Auto-generated constructor stub
        }

        public static VacunaDialog newInstance(String value) {
            // Crea un Fragmento nuevo con los parametros indicados
            consec = value;
            VacunaDialog fragment = new VacunaDialog();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        public String getNombre(String consec){
            String nombreAp = "";
            String nombre = "";
            String ape1 = "";
            int codPer = Integer.parseInt(consec);
            //Toast.makeText(getActivity(), "codPer "+codPer, Toast.LENGTH_LONG).show();
            String sentencia = "select nombre,apellido1 from persona where consec = "+codPer;
            adapter.open();
            Cursor resultado = adapter.select(sentencia);
            if(resultado.getCount()==0){
                Toast.makeText(getActivity(), "No existen esta persona en la base de datos", Toast.LENGTH_LONG).show();
                esta = false;
            }else {
                        /* Se mueve el cursor al primer resultado, y en este caso es el unico*/
                resultado.moveToFirst();
                        /*Se extraen los valores de las columnas*/
                nombre = resultado.getString(0);
                ape1 = resultado.getString(1);
                esta = true;
            }
            adapter.close();

            return nombreAp = nombre+ " "+ape1;
        }

;



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            selectedOption = "None";
            adapter = new SQLiteAdapter(getActivity());
            // Crea un dialogo nuevo usando el Builder
            historialBuilder = new AlertDialog.Builder(getActivity());
            nombAp = getNombre(consec);
            if(esta == true) {
                historialBuilder.setTitle("Tarjeta de vacunas de " + nombAp);
                historialBuilder.setItems(tarjeta, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedOption = tarjeta[which];
                        Log.i("Tarejeta Vacunas", "Opcion elegida: " + selectedOption);
                    }
                });
            } else {

                Toast.makeText(getActivity(), "Intente con una persona existente", Toast.LENGTH_LONG).show();
            }

            return historialBuilder.create();
        }

        public void loadHistorial() {
            //Incluir el uso del metodo cursorToList.

        }

    }
