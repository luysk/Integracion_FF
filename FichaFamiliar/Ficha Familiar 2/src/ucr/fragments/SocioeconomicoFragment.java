package ucr.fragments;

import ucr.ff.R;
import java.util.ArrayList;

import ucr.database.SQLiteAdapter;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressWarnings("unused")
public class SocioeconomicoFragment extends Fragment {
	
	private View view;

	private TextView txtCondSoc;
	private TextView txtRefrig;
	private TextView txtRadio;
	private TextView txtTelefono;
	private TextView txtTV;
	private TextView txtLavadora;
	private TextView txtPC;	
	private TextView txtInternet;	
	
	private Spinner spinnerCondSoc;
	private Spinner spinnerRefrig;
	private Spinner spinnerRadio;
	private Spinner spinnerTelefono;
	private Spinner spinnerTV;
	private Spinner spinnerLavadora;
	private Spinner spinnerPC;
	private Spinner spinnerInternet;
	
	private Button updateButton;
	
	private static String select[] = {"S", "N"};
	private static String condSoc[] = {"PE", "P", "V", "E"};	
	
	private SQLiteAdapter adapter;
	private ContentValues values;
	private int codViv;

	public SocioeconomicoFragment() {
		super();
		codViv = 0;
	}
	
	public SocioeconomicoFragment(int codViv) {
		super();
		this.codViv = codViv;
	}	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("Prueba", "SocioeconomicoFragment");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.socioec_tab, null);
		adapter = new SQLiteAdapter(view.getContext());
		createWidgets();
		spinnerFunctions();
		buttonFunctions();
		return view;
	}
			 
	public void createTextViews() {
		txtCondSoc = (TextView) view.findViewById(R.id.txtCondSoc);
		txtRefrig = (TextView) view.findViewById(R.id.txtRefrig);
		txtRadio = (TextView) view.findViewById(R.id.txtRadio);
		txtTelefono = (TextView) view.findViewById(R.id.txtTelefono);
		txtTV = (TextView) view.findViewById(R.id.txtTV);
		txtLavadora = (TextView) view.findViewById(R.id.txtLavadora);
		txtPC = (TextView) view.findViewById(R.id.txtPC);
		txtInternet = (TextView) view.findViewById(R.id.txtInternet);
		
	}
	
	private void createSpinners() {
		spinnerCondSoc = (Spinner) view.findViewById(R.id.spinnerCondSoc);
        ArrayAdapter<CharSequence> lista_CondSoc = ArrayAdapter.createFromResource(view.getContext(),R.array.condSoc,android.R.layout.simple_spinner_item);
        lista_CondSoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondSoc.setAdapter(lista_CondSoc);	  
        spinnerCondSoc.setSelection(0, true);	
		
		spinnerRefrig = (Spinner) view.findViewById(R.id.spinnerRefrig);
        ArrayAdapter<CharSequence> lista_Refrig = ArrayAdapter.createFromResource(view.getContext(),R.array.seleccion,android.R.layout.simple_spinner_item);
        lista_Refrig.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRefrig.setAdapter(lista_Refrig);	  
        spinnerRefrig.setSelection(0, true);	
		
		spinnerRadio = (Spinner) view.findViewById(R.id.spinnerRadio);
        ArrayAdapter<CharSequence> lista_Radio = ArrayAdapter.createFromResource(view.getContext(),R.array.seleccion,android.R.layout.simple_spinner_item);
        lista_Radio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRadio.setAdapter(lista_Radio);	  
        spinnerRadio.setSelection(0, true);	
		
		spinnerTelefono = (Spinner) view.findViewById(R.id.spinnerTelefono);
        ArrayAdapter<CharSequence> lista_Telefono = ArrayAdapter.createFromResource(view.getContext(),R.array.seleccion,android.R.layout.simple_spinner_item);
        lista_Telefono.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTelefono.setAdapter(lista_Telefono);	  
        spinnerTelefono.setSelection(0, true);	
		
		spinnerTV = (Spinner) view.findViewById(R.id.spinnerTV);
        ArrayAdapter<CharSequence> lista_TV = ArrayAdapter.createFromResource(view.getContext(),R.array.seleccion,android.R.layout.simple_spinner_item);
        lista_TV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTV.setAdapter(lista_TV);	  
        spinnerTV.setSelection(0, true);	
        
		spinnerLavadora = (Spinner) view.findViewById(R.id.spinnerLavadora);
        ArrayAdapter<CharSequence> lista_Lavadora = ArrayAdapter.createFromResource(view.getContext(),R.array.seleccion,android.R.layout.simple_spinner_item);
        lista_Lavadora.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLavadora.setAdapter(lista_Lavadora);	  
        spinnerLavadora.setSelection(0, true);	
        
		spinnerPC = (Spinner) view.findViewById(R.id.spinnerPC);
        ArrayAdapter<CharSequence> lista_PC = ArrayAdapter.createFromResource(view.getContext(),R.array.seleccion,android.R.layout.simple_spinner_item);
        lista_PC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPC.setAdapter(lista_PC);	  
        spinnerPC.setSelection(0, true);	
		
		spinnerInternet = (Spinner) view.findViewById(R.id.spinnerInternet);
        ArrayAdapter<CharSequence> lista_Internet = ArrayAdapter.createFromResource(view.getContext(),R.array.seleccion,android.R.layout.simple_spinner_item);
        lista_Internet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInternet.setAdapter(lista_Internet);	  
        spinnerInternet.setSelection(0, true);	
	}
	
	private void spinnerFunctions() {
		spinnerCondSoc.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				spinnerCondSoc.setBackgroundColor(getResources().getColor(R.color.lightorange));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});		
		
		spinnerRefrig.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				spinnerRefrig.setBackgroundColor(getResources().getColor(R.color.lightorange));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});		
		
		spinnerRadio.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				spinnerRadio.setBackgroundColor(getResources().getColor(R.color.lightorange));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});		
		
		spinnerTelefono.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				spinnerTelefono.setBackgroundColor(getResources().getColor(R.color.lightorange));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});		
		
		spinnerTV.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				spinnerTV.setBackgroundColor(getResources().getColor(R.color.lightorange));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});	 	
        
		spinnerLavadora.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				spinnerLavadora.setBackgroundColor(getResources().getColor(R.color.lightorange));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});		
        
		spinnerPC.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				spinnerPC.setBackgroundColor(getResources().getColor(R.color.lightorange));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});	 	
		
		spinnerInternet.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				spinnerInternet.setBackgroundColor(getResources().getColor(R.color.lightorange));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});	 		
	}
	
	
	private void createButtons() {
		updateButton =  (Button) view.findViewById(R.id.updateButton);
	}
	
	private void buttonFunctions() {				
		updateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                values = new ContentValues();
                values.put("codViv", codViv);
                values.put("CodCondSocioeco", condSoc[spinnerCondSoc.getSelectedItemPosition()]);
                values.put("Refrigeracion", select[spinnerRefrig.getSelectedItemPosition()]);
                values.put("Radio", select[spinnerRadio.getSelectedItemPosition()]);
                values.put("Telefono", select[spinnerTelefono.getSelectedItemPosition()]);
                values.put("TV", select[spinnerTV.getSelectedItemPosition()]);
                values.put("Lavadora", select[spinnerLavadora.getSelectedItemPosition()]);
                values.put("Computadora", select[spinnerPC.getSelectedItemPosition()]);
                values.put("Internet", select[spinnerInternet.getSelectedItemPosition()]);

                adapter.open();

                boolean exito =adapter.update("condsoc",values,"codviv",codViv);
                if(exito){
                   Toast.makeText(view.getContext(),"Los datos han sido correctamente actualizados",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(view.getContext(),"Ha ocurrido un error, los datos no han sido actualizados",Toast.LENGTH_LONG).show();
                }

                adapter.close();
            }
		});
	}
	private void createWidgets() {
		createTextViews();
		createSpinners();
		createButtons();
	}

}
