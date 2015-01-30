package ucr.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class VisitaDialog extends DialogFragment {
	private String historial[] = { 
			"2/05/1998  --- Vacunacion     -- Rubeola y Sarampion",
			"7/11/1999  --- Control        -- Embarazo adolescente",
			"12/05/2002 --- Vacunacion     -- Rubeola y Sarampion",
			"7/11/2004  --- Control        -- Embarazo adolescente",			
			"2/05/2006  --- Vacunacion     -- Rubeola y Sarampion",
			"7/11/2008  --- Campaea        -- Papanicolau",
			"2/05/2009  --- Vacunacion     -- Rotavirus",
			"7/11/2011  --- Campaea        -- Desparasitacion"};
	private String selectedOption;
    private Builder historialBuilder;
    private static String codViv;
	
	
	public VisitaDialog() {
		// TODO Auto-generated constructor stub
	}
	
	public static VisitaDialog newInstance(String value) {
		// Crea un Fragmento nuevo con los parametros indicados
		codViv = value;
		VisitaDialog fragment = new VisitaDialog();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		selectedOption = "None";
		// Crea un dialogo nuevo usando el Builder
		historialBuilder = new AlertDialog.Builder(getActivity());

		historialBuilder.setTitle("Historial para vivienda " + codViv);
		historialBuilder.setItems(historial, new DialogInterface.OnClickListener() {
			
			@Override
		public void onClick(DialogInterface dialog, int which) {
			selectedOption = historial[which];		
			Log.i("Historial", "Opcion elegida: " + selectedOption);				
			}
		});

		return historialBuilder.create();		
	}
	
	public void loadHistorial() {
	//Incluir el uso del metodo cursorToList.
	
	}
	

}
