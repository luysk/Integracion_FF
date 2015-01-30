
package ucr.dialogs;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class PersonaOptionsDialog<View> extends DialogFragment {
	private String opciones[] = { 
			"Borrar",
			"Trasladar"};
	private String selectedOption;
    private Builder historialBuilder;
    private static String codViv;
    private static String personaSeleccionada = "";
	
	
	public PersonaOptionsDialog() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static PersonaOptionsDialog newInstance(String value, String laPersonaSeleccionada) {
		// Crea un Fragmento nuevo con los parametros indicados
		codViv = value;
		personaSeleccionada = laPersonaSeleccionada;
		PersonaOptionsDialog fragment = new PersonaOptionsDialog();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		selectedOption = "None";
		// Crea un dialogo nuevo usando el Builder
		historialBuilder = new AlertDialog.Builder(getActivity());

		historialBuilder.setTitle("Opciones");
		historialBuilder.setItems(opciones, new DialogInterface.OnClickListener() {
			
			@Override
		public void onClick(DialogInterface dialog, int which) {
			selectedOption = opciones[which];		
			Log.i("Grupo Familiar(opciones) ", "Opcion elegida: " + selectedOption + " a: " + personaSeleccionada);				
			}
		});
		
		return historialBuilder.create();
	}
	
	public void loadHistorial() {
	//Incluir el uso del metodo cursorToList.
	
	}
	

}