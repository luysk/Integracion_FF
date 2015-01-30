package ucr.fragments;

import java.util.ArrayList;
import java.util.Arrays;

import ucr.database.SQLiteAdapter;
import ucr.dialogs.VacunaDialog;
import ucr.dialogs.VisitaDialog;
import ucr.ff.R;
import ucr.tools.EstadoGlobal;
import ucr.tools.Validation;


import android.app.DialogFragment;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressWarnings("unused")
public class HechosFragment extends Fragment{

	private View view;

	private TextView txtTituloHechos;
	private TextView txtCodPer;
	private TextView txtEspec;
	private TextView txtSaludAct;
	private TextView txtTipoViol;
	private TextView txtFechaPlan;
	private TextView txtTipoPlan;
	private TextView txtFechaPap;
	private TextView txtEstPap;
	private TextView txtFechaEmb;
	private TextView txtFechaVac;
	private TextView txtTipoVac;
	private TextView txtListaVacRec;
	private TextView txtObservaciones;

    private VacunaDialog dialogoVacuna;

    private static int codPer;
    private static String fechaPlan;
    private static String tipoPlan;
    private static String fechaPap;
    private static String fechaUltReg;
    private static String fechaEmb;
    private static String estadoPap;
    private static int estadoPapa;

    private static int condSalud;
    private static int condViolenciaDom;
    private static int visita;
    private static String observ;


    private SQLiteAdapter adapter;

	EditText editCodPer;
	EditText editFechaPlan;
	EditText editFechaPap;
	EditText editFechaEmb;
	EditText editFechaUltRegla;
	EditText editObservaciones;

	Spinner spinnerSaludAct;
	Spinner spinnerTipoViol;
	Spinner spinnerTipoPlan;
	Spinner spinnerEstPap; //no esta en la tabla condsaludm
	Spinner spinnerTipoVac;
	Spinner spinnerListaVacRec;
    Spinner spinnerVisitaEspecialista;


    private static String tipo_Estados[] = {"B","R","M"};
    private static String tipo_Violencia[] = {"N","F","P"};
    private static String tipo_Planificacion[] = {"DIU","GO","PRE","QX","IMP","INY","NAT","OTR"};




	Button  searchButton;
    Button  clearButton;
	Button  updateButton;
    private ImageButton btnVacuna;
    private int idVacuna;

    private Validation validation;
//    private boolean preloaded_data;
    private ContentValues values;

	private ListView viewListaVacunas;

/*
    public HechosFragment() {
        super();
        validation = new Validation();
    }*/

    public HechosFragment(int codPers) {
        super();
        this.codPer = codPers;
        validation = new Validation();
    }

	public void createTextViews() {
		txtTituloHechos = (TextView) view.findViewById(R.id.txtTituloHechos);
		txtCodPer = (TextView) view.findViewById(R.id.txtCodPer);
		txtEspec = (TextView) view.findViewById(R.id.txtEspec);
		txtSaludAct = (TextView) view.findViewById(R.id.txtSaludAct);
		txtTipoViol = (TextView) view.findViewById(R.id.txtTipoViol);
		txtFechaPlan = (TextView) view.findViewById(R.id.txtFechaPlan);
		txtTipoPlan = (TextView) view.findViewById(R.id.txtTipoPlan);
		txtFechaPap = (TextView) view.findViewById(R.id.txtFechaPap);
		txtEstPap = (TextView) view.findViewById(R.id.txtEstPap);
		txtFechaEmb = (TextView) view.findViewById(R.id.txtFechaEmb);
		txtObservaciones = (TextView) view.findViewById(R.id.txtObservaciones);
	}

	private void createSpinners() {
		spinnerSaludAct = (Spinner) view.findViewById(R.id.spinnerSaludAct);
        ArrayAdapter<CharSequence> lista_SaludAct =
        		ArrayAdapter.createFromResource(view.getContext(),R.array.condSalud,android.R.layout.simple_spinner_item);
        lista_SaludAct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSaludAct.setAdapter(lista_SaludAct);
        spinnerSaludAct.setSelection(0, true);

		spinnerTipoViol = (Spinner) view.findViewById(R.id.spinnerTipoViol);
        ArrayAdapter<CharSequence> lista_TipoViol =
        		ArrayAdapter.createFromResource(view.getContext(),R.array.violIntrafamiliar,android.R.layout.simple_spinner_item);
        lista_TipoViol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoViol.setAdapter(lista_TipoViol);
        spinnerTipoViol.setSelection(0, true);

		spinnerTipoPlan = (Spinner) view.findViewById(R.id.spinnerTipoPlan);
        ArrayAdapter<CharSequence> lista_TipoPlan =
        		ArrayAdapter.createFromResource(view.getContext(),R.array.metodoPlanificacion,android.R.layout.simple_spinner_item);
        lista_TipoViol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoPlan.setAdapter(lista_TipoPlan);
        spinnerTipoPlan.setSelection(0, true);
        //spinnerTipoPlan.setEnabled(false);

		spinnerEstPap = (Spinner) view.findViewById(R.id.spinnerEstPap);
        ArrayAdapter<CharSequence> lista_EstPap =
        		ArrayAdapter.createFromResource(view.getContext(),R.array.resultPap,android.R.layout.simple_spinner_item);
        lista_TipoViol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstPap.setAdapter(lista_EstPap);
        spinnerEstPap.setSelection(0, true);
        //spinnerEstPap.setEnabled(false);

        spinnerVisitaEspecialista = (Spinner) view.findViewById(R.id.spinnerVisitaEspecialista);
        ArrayAdapter<CharSequence> lista_Visita = ArrayAdapter.createFromResource(view.getContext(),R.array.seleccion,android.R.layout.simple_spinner_item);
        lista_Visita.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVisitaEspecialista.setAdapter(lista_Visita);
        spinnerVisitaEspecialista.setSelection(0, true);


    }

	private void spinnerFunctions() {
		spinnerSaludAct.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                condSalud = spinnerSaludAct.getSelectedItemPosition();
				//spinnerSaludAct.setBackgroundColor(getResources().getColor(R.color.lightorange));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});


		spinnerTipoViol.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                condViolenciaDom = spinnerTipoViol.getSelectedItemPosition();
				//spinnerTipoViol.setBackgroundColor(getResources().getColor(R.color.lightorange));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		spinnerTipoPlan.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				//spinnerTipoPlan.setBackgroundColor(getResources().getColor(R.color.lightorange));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		spinnerEstPap.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				//spinnerEstPap.setBackgroundColor(getResources().getColor(R.color.lightorange));
                estadoPapa = spinnerEstPap.getSelectedItemPosition();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

        spinnerVisitaEspecialista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                visita = spinnerTipoViol.getSelectedItemPosition();
                //spinnerTipoViol.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
	}

	private void createEditTexts() {
		editCodPer = (EditText) view.findViewById(R.id.editCodPer);
		editFechaPlan =  (EditText) view.findViewById(R.id.editFechaPlan);
        editFechaEmb =  (EditText) view.findViewById(R.id.editFechaEmb);
        editFechaUltRegla =  (EditText) view.findViewById(R.id.editFechaUltRegla);
        //editFechaPlan.setEnabled(false);
		editFechaPap =  (EditText) view.findViewById(R.id.editFechaPap);
		//editFechaPap.setEnabled(false);
		editObservaciones = (EditText) view.findViewById(R.id.editObservaciones);
	}

	private void editTextFunctions() {

		editObservaciones.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
					editObservaciones.setBackgroundColor(getResources().getColor(R.color.lightblue));
				} else {
					if (editObservaciones.getText().toString().isEmpty()) {
						editObservaciones.setBackgroundColor(getResources().getColor(R.color.white));
					} else {
						editObservaciones.setBackgroundColor(getResources().getColor(R.color.lightorange));
					}
				}
			}
		});
	}

	private void createButtons() {


		searchButton =  (Button) view.findViewById(R.id.searchButtonFem);
		clearButton =  (Button) view.findViewById(R.id.clearButtonFem);
		updateButton =  (Button) view.findViewById(R.id.updateButtonFem);
        btnVacuna = (ImageButton)view.findViewById(R.id.btnVacuna);
        btnVacuna.setEnabled(true);
	}

	public HechosFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("Cargando...", "HechosFragmentFemenino");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.hechos_tab, null);
        adapter = new SQLiteAdapter(view.getContext());
		createWidgets();
		spinnerFunctions();
		buttonFunctions();
		return view;
	}

/*
	public ArrayList<String> demo() {
		ArrayList<String> tmp = new ArrayList<String>();
		tmp.add("DD/MM/AAAA");
		tmp.add("Vacuna aplicada en ese día");
		return tmp;
	}

	public ArrayAdapter<ArrayList<String>> datos(int valor) {
		ArrayAdapter<ArrayList<String>> tmp = new ArrayAdapter<ArrayList<String>>(view.getContext(), android.R.layout.simple_list_item_1);
		for (int i = 0; i < valor; ++i) {
			ArrayList<String> tmp1 = new ArrayList<String>();
			tmp1 = demo();
			tmp.add(tmp1);
		}
		return tmp;
	}

	public void createListView() {
		viewListaVacunas = (ListView) view.findViewById(R.id.viewListaVacunas);
		ArrayAdapter<ArrayList<String>> array = new ArrayAdapter<ArrayList<String>>(view.getContext(), android.R.layout.simple_list_item_1);
		array = datos(7);
		viewListaVacunas.setAdapter(array);
		viewListaVacunas.setSelection(0);
		viewListaVacunas.setBackgroundColor(80);
	}
*/
	public void createWidgets() {
		createTextViews();
		createEditTexts();
	//	createListView();
		createSpinners();
		createButtons();
		editTextFunctions();
	}

    private void limpiar(){
        editObservaciones.setText("");
        editCodPer.setText("");
        editFechaUltRegla.setText("");
        editFechaPlan.setText("");
        editFechaEmb.setText("");
        editFechaPap.setText("");
        spinnerSaludAct.setSelection(0);
        spinnerTipoViol.setSelection(0);
        spinnerVisitaEspecialista.setSelection(0);
        spinnerEstPap.setSelection(0);
        spinnerTipoPlan.setSelection(0);
    }

	public void buttonFunctions() {


        clearButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiar();
                Toast.makeText(getActivity(), "Datos por defecto reestablecidos", Toast.LENGTH_SHORT).show();
            }
        });

        searchButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!validation.isEmpty(editCodPer.getText().toString())){
                    int codPersona = Integer.parseInt(editCodPer.getText().toString());
                    String sentenciaM = "select * from condsalud where consec = "+codPersona;
                    Log.e("codPersonaM", "evento en HechosFragment");
                    String sentenciaF = "select * from condsaludm where consec = "+codPersona;
                    Log.e("codPersonaF", "evento en HechosFragment");

                    adapter.open();
                    Cursor resultadoM = adapter.select(sentenciaM);
                    Cursor resultadoF = adapter.select(sentenciaF);

                    if(resultadoM.getCount()==0 &&resultadoF.getCount()==0  ){
                        Toast.makeText(view.getContext(),"No existen entradas en la base de datos para el codigo persona",Toast.LENGTH_LONG).show();
                    }else{
                        //trajo de la BD la consulta y la guarda en resultado
                        resultadoM.moveToFirst();
                        String consec = resultadoM.getString(0);
                        String visitaEspecialista = resultadoM.getString(1);
                        String condsalud = resultadoM.getString(2);
                        String condviolenciadom = resultadoM.getString(3);
                        String observaciones = resultadoM.getString(4);

                        resultadoF.moveToFirst();
                        String consecF = resultadoF.getString(0);
                        String fechaPlani = resultadoF.getString(1);
                        String tipoPlani = resultadoF.getString(2);
                        String fechaPapani = resultadoF.getString(3);
                        String fechaUltiReg = resultadoF.getString(4);
                        String fechaEmb = resultadoF.getString(5);
                        String estadoPap = resultadoF.getString(6);

                        //muestra en la aplicacion los valores traidos de la BD
                        spinnerSaludAct.setSelection(Integer.parseInt(condsalud));
                        spinnerTipoViol.setSelection(Integer.parseInt(condviolenciadom));
                        spinnerVisitaEspecialista.setSelection(Integer.parseInt(visitaEspecialista));
                        editObservaciones.setText(observaciones);

                        editFechaEmb.setText(fechaEmb);
                        editFechaPlan.setText(fechaPlani);
                        editFechaPap.setText(fechaPapani);
                        editFechaUltRegla.setText(fechaUltiReg);
                        spinnerTipoPlan.setSelection(Arrays.asList(tipo_Planificacion).indexOf(tipoPlani));
                        spinnerEstPap.setSelection(Integer.parseInt(estadoPap));
                        //new EstadoGlobal().getInstance().setCodPersona(codPersona);
                        Log.e("SearchButton", "evento en HechosFragment");
                    }
                    adapter.close();
                }else{
                    Toast.makeText(view.getContext(),"Debe ingresar una persona nueva en la pestaña Persona para hacer la consulta",Toast.LENGTH_LONG).show();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int codPersona = Integer.parseInt(editCodPer.getText().toString());
                if (codPersona != 0) {
                    ContentValues valores_actualizar = new ContentValues();


                   // valores_actualizar.put("consec", codPersona);
                    valores_actualizar.put("visitaespecialista", spinnerVisitaEspecialista.getSelectedItemPosition());
                    valores_actualizar.put("condSalud", spinnerSaludAct.getSelectedItemPosition());
                    valores_actualizar.put("condviolenciadom", spinnerTipoViol.getSelectedItemPosition());
                    valores_actualizar.put("observaciones", editObservaciones.getText().toString());

                    adapter.open();
                    boolean exito = adapter.update("condsalud", valores_actualizar, "consec", codPersona);
                    adapter.close();
                    if (!exito) {
                        Toast.makeText(view.getContext(), "No se han insertado correctamente los datos de condsalud en la base de datos", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(view.getContext(), "Datos condsalud actualizados correctamente", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (validation.isEmpty(editCodPer.getText().toString())) {
                        Toast.makeText(view.getContext(), "Debe ingresar el codigo de la persona que desea actualizar", Toast.LENGTH_LONG).show();
                    }
                }
                adapter.close();

                adapter.open();
                if (codPersona != 0) {
                    ContentValues valores_actualizarF = new ContentValues();
                    //valores_actualizarF.put("consec", codPersona);
                    valores_actualizarF.put("fechaPlan", editFechaPlan.getText().toString());
                    valores_actualizarF.put("tipoPlan", spinnerTipoPlan.getSelectedItemPosition());
                    valores_actualizarF.put("fechaPap", editFechaPap.getText().toString());
                    valores_actualizarF.put("fechaUltRegla", editFechaUltRegla.getText().toString());
                    valores_actualizarF.put("fechaEmb", editFechaEmb.getText().toString());
                    valores_actualizarF.put("estadoPap", spinnerEstPap.getSelectedItemPosition());
                    adapter.open();
                    boolean exitoF = adapter.update("condsaludm", valores_actualizarF, "consec", codPersona);
                    adapter.close();
                    if (!exitoF) {
                        Toast.makeText(view.getContext(), "No se han insertado correctamente los datos de condsaludm en la base de datos", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(view.getContext(), "Datos condsaludm actualizados correctamente", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (validation.isEmpty(editCodPer.getText().toString())) {
                        Toast.makeText(view.getContext(), "Debe ingresar el codigo de la persona que desea actualizar", Toast.LENGTH_LONG).show();
                    }
                    adapter.close();
                }
            }
        });



        btnVacuna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int codPersona = Integer.parseInt(editCodPer.getText().toString());

                    //Toast.makeText(getActivity(), "Tarjetas de vacunas en proceso de creacion", Toast.LENGTH_SHORT).show();
                    dialogoVacuna = VacunaDialog.newInstance(String.valueOf(codPersona));
                    //visitas = VisitaDialog.newInstance("777");
                    ((DialogFragment) dialogoVacuna).show(getFragmentManager(), "btnVacuna");
                }catch (Exception e){}
            }
        });

/*
        btnVacuna.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             int codPersona = Integer.parseInt(editCodPer.getText().toString());
                                             String sentencia = "select * from controlvacunas where consec = "+codPersona;
                                             adapter.open();
                                             Cursor resultado = adapter.select(sentencia);
                                             if(resultado.getCount()==0){
                                                 Toast.makeText(view.getContext(),"Esta persona no tiene esta vacuna",Toast.LENGTH_LONG).show();
                                             }else {
                                                 // Se mueve el cursor al primer resultado, y en este caso es el unico
                                                 String[] nombres = new String[resultado.getCount()];
                                                 int cantidadIdVacunas = (resultado.getCount()/3);
                                                 String[] idVacunas = new String[cantidadIdVacunas];
                                                 resultado.moveToFirst();
                                                 // Se extraen los valores de las columnas
                                                 for (int i = 0; !resultado.isLast(); i++) {
                                                     nombres[i] = resultado.getString(0) + " "+ resultado.getString(1) + " "+ resultado.getString(2);
                                                     //consec               " "  idVacuna                "  "    fechaVac
                                                     //12345678 1 10/10/2014
                                                     idVacunas[i] = resultado.getString(1);
                                                     resultado.moveToNext();
                                                 }
                                                 adapter.close();

                                                 idVacuna = Integer.parseInt(idVacunas[0]);
                                                 String consulta = "select nombre_vacuna from vacuna where idvacuna = "+idVacuna;
                                                 adapter.open();
                                                 Cursor resultadoConsulta = adapter.select(consulta);
                                                 if(resultadoConsulta.getCount() == 0){
                                                     Toast.makeText(view.getContext(),"Este identificador de vacuna no existe",Toast.LENGTH_LONG).show();
                                                 }else{
                                                     String[] vacunas = new String[resultadoConsulta.getCount()];
                                                     resultadoConsulta.moveToFirst();
                                                     //se extraen los valores de las columnas idVacuna y nombreVacuna
                                                     for(int i = 0; !resultadoConsulta.isLast(); i++){
                                                         vacunas[i] = resultadoConsulta.getString(0) + " " +resultadoConsulta.getString(1);
                                                         resultadoConsulta.moveToNext();
                                                     }
                                                 }


                                               //  dialogoVacuna = VacunaDialog.newInstance(String.valueOf(0), vacunas);
                                                 //((DialogFragment) dialogoVacuna).show(getFragmentManager(), "Historial");
                                                 //String r = dialogoVacuna.getResultado();
                                                 //Toast.makeText(view.getContext(),"->"+r,Toast.LENGTH_LONG).show();
                                             }
                                             adapter.close();
                                         }
                                     }

        );*/




	}

    private void setValues() {
        values = new ContentValues();
        values.put("codPer", getCodPer());
        //condsaludm
        values.put("fechaPlan", editFechaPlan.getText().toString());
        values.put("fechaPap", editFechaPap.getText().toString());
        values.put("observ", editObservaciones.getText().toString());
        values.put("tipoPlan",tipo_Planificacion[spinnerTipoPlan.getSelectedItemPosition()]);
        //condsalud
        values.put("saludActual",tipo_Estados[spinnerSaludAct.getSelectedItemPosition()]);
        values.put("tipoViolencia",tipo_Violencia[spinnerTipoViol.getSelectedItemPosition()]);


    }



    public int getCodPer() {
        int res = 0;
        try {
            res = Integer.parseInt(editCodPer.getText().toString());
        } catch (Exception e) {
            res = -1;
        }
        return res;
    }




    public void store() {

	}

	public void load() {

	}


}
