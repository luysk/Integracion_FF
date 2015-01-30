package ucr.fragments;

import ucr.ff.R;
import java.util.ArrayList;

import ucr.database.SQLiteAdapter;
import ucr.dialogs.VisitaDialog;
import ucr.tools.Validation;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("unused")
public class VisitasFragment extends Fragment{
	
	private static View view;

	private SQLiteAdapter adapter;
	private String date;
	private int codViv;

	private TextView txtCodViv;
	private TextView txtFechaVis;
	private TextView txtMotivos;
	private TextView txtAccion;
	
	private EditText editCodViv;
	private EditText editFecha;
	private EditText editMotivos;
	private EditText editAccion;
	
	private Button updateButton;
	private Button historicButton;
	
	private Validation validation;
	
	private VisitaDialog visitas;

	public VisitasFragment() {
		view = null;
		validation = new Validation();
	}
	
	public VisitasFragment(int codViv) {
		view = null;
		//this.date = date;
		this.codViv = codViv;
		validation = new Validation();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("Prueba", "VisitasFragment");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.visitas_tab, null);
		adapter = new SQLiteAdapter(view.getContext());
		createWidgets();
		editTextFunctions();
		buttonFunctions();
		return view;
	}
	
	public void createTextViews() {
		txtCodViv = (TextView) view.findViewById(R.id.txtCodViv);
		txtFechaVis = (TextView) view.findViewById(R.id.txtFechaVisita);
		txtMotivos = (TextView) view.findViewById(R.id.txtMotivos);
		txtAccion = (TextView) view.findViewById(R.id.txtAccion);
	}
	
	public void createEditTexts() {
		editCodViv = (EditText) view.findViewById(R.id.editCodViv);
		editCodViv.setText(String.valueOf(codViv));
		editFecha = (EditText) view.findViewById(R.id.editFecha);
		editMotivos = (EditText) view.findViewById(R.id.editMotivos);
		editAccion = (EditText) view.findViewById(R.id.editAccion);
		
		editFecha.setText(validation.getDate());
		editFecha.setEnabled(false);
	}
	
	private void editTextFunctions() {
		editMotivos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
					editMotivos.setBackgroundColor(getResources().getColor(R.color.lightblue));  
				} else {
					if (editMotivos.getText().toString().isEmpty()) {
						editMotivos.setBackgroundColor(getResources().getColor(R.color.white));  
					} else {
						editMotivos.setBackgroundColor(getResources().getColor(R.color.lightorange));  
					}	        	
				}
			}
		});
		
		editAccion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
					editAccion.setBackgroundColor(getResources().getColor(R.color.lightblue));  
				} else {
					if (editAccion.getText().toString().isEmpty()) {
						editAccion.setBackgroundColor(getResources().getColor(R.color.white));  
					} else {
						editAccion.setBackgroundColor(getResources().getColor(R.color.lightorange));  
					}	        	
				}
			}
		});
		
	}
	
	public void createButtons() {
		updateButton = (Button) view.findViewById(R.id.updateButton);
		historicButton = (Button) view.findViewById(R.id.beforeButton);
	}
	
	public void buttonFunctions() {
		updateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Datos actualizados", Toast.LENGTH_SHORT).show();		
			}			
		});
		historicButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Historial en proceso de creacion", Toast.LENGTH_SHORT).show();
				visitas = VisitaDialog.newInstance(String.valueOf(codViv));
				//visitas = VisitaDialog.newInstance("777");
				((DialogFragment) visitas).show(getFragmentManager(), "Historial");
			}			
		});
	}
	
	public void createWidgets() {
		createTextViews();
		createEditTexts();
		createButtons();
	}
	
//	public class HistorialFragment extends DialogFragment {
//		private int codViv;
//		
//		public HistorialFragment newInstance(String title, int codViv) {
//			HistorialFragment frag = new HistorialFragment();
//			Bundle args = new Bundle();
//			args.putString("Historial", title);
//			frag.setArguments(args);
//			this.codViv = codViv;
//			return frag;
//		}
//
//		@Override
//		public Dialog onCreateDialog(Bundle savedInstanceState) {
//			String title = getArguments().getString("Historial", "- de Visitas -");
//
//			AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
//			ab.setIcon(R.drawable.ic_launcher);
//			ab.setTitle(title);
//			
//					
//			ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int whichButton) {
//
//
//				}
//			});
//
//			return ab.create();
//		}
//		
//		public void loadHistorial() {
//			//Incluir el uso del metodo cursorToList.
//			
//		}
//		
//		
//		
//	}

}
