package ucr.dialogs;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class PersonaDialog<View> extends DialogFragment {
	private String historial[] = { 
			"Juan  --- Perez     -- Perez",
			"Andres  --- Gonzalez        -- Herrera",
			"Carlos --- Gomez     -- Gomez"};
	private String selectedOption;
    private Builder historialBuilder;
    private static String codViv;
    private PersonaOptionsDialog lasOpciones;
	
	
	public PersonaDialog() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static PersonaDialog newInstance(String value) {
		// Crea un Fragmento nuevo con los parametros indicados
		codViv = value;
		PersonaDialog fragment = new PersonaDialog();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		selectedOption = "None";
		// Crea un dialogo nuevo usando el Builder
		historialBuilder = new AlertDialog.Builder(getActivity());

		historialBuilder.setTitle("Grupo Familiar " + codViv);
		historialBuilder.setItems(historial, new DialogInterface.OnClickListener() {
			
			@Override
		public void onClick(DialogInterface dialog, int which) {
			selectedOption = historial[which];		
			Log.i("Grupo Familiar ", "Opcion elegida: " + selectedOption);				
			}
		});
		
		AlertDialog resultado = historialBuilder.create();
		ListView lista = resultado.getListView();
		lista.setLongClickable(true);
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, android.view.View arg1,
                    int pos, long id) {
            	
                Log.v("Click largo en ","pos: " + pos);
                lasOpciones = PersonaOptionsDialog.newInstance(String.valueOf(codViv),historial[pos]);
				((DialogFragment) lasOpciones).show(getFragmentManager(), "Historial");

                return true;
            }
        }); 
		return resultado;
	}
	
	public void loadHistorial() {
	//Incluir el uso del metodo cursorToList.
	
	}
	

}