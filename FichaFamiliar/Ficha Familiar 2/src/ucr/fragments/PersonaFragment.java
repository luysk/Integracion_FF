package ucr.fragments;

import ucr.ff.R;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import ucr.tools.Validation;
import ucr.database.SQLiteAdapter;
import ucr.dialogs.PersonaDialog;
import ucr.dialogs.VisitaDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressWarnings("unused")
public class PersonaFragment extends Fragment {
    private static boolean childrenOptions = true;
    private static boolean adultOptions = false;

    //Dialogs
    private PersonaDialog lasPersonas;

    //Views
    private View view;

    //TextViews
    private TextView txtTituloTab;
    private TextView txtIdentificacion;
    private TextView txtNombre;
    private TextView txtApe1;
    private TextView txtApe2;
    private TextView txtFechaNac;
    private TextView txtFechaDef;
    private TextView txtPeso;
    private TextView txtTalla;
    private TextView txtIMC;
    private TextView txtSexo;
    private TextView txtRolFam;
    private TextView txtEstCiv;
    private TextView txtAseg;
    private TextView txtEsc;
    private TextView txtEstNut;
    private TextView txtOcup;
    private TextView txtCondLab;
    private TextView txtLugNac;
    private TextView txtRaza;
    private TextView txtJefeViv;
    private TextView txtCodViv;

    //Spinners
    private Spinner spinnerSexo;
    private Spinner spinnerRolF;
    private Spinner spinnerEstCiv;
    private Spinner spinnerAseg;
    private Spinner spinnerEscP;
    private Spinner spinnerEstNut;
    private Spinner spinnerOcup;
    private Spinner spinnerCondLab;
    private Spinner spinnerLugNac;
    private Spinner spinnerRaza;

    //AutoCompleteTextView
    private AutoCompleteTextView editNacion;

    //EditTexts
    private EditText editId;
    protected static EditText editFechaNac;
    protected static EditText editFechaDef;
    private EditText editPeso;
    private EditText editTalla;
    private EditText editIMC;
    private EditText editConsecJefeF;
    private EditText editCodViv;
    private EditText editEstNut;

    private EditText editNombre;
    private EditText editApe1;
    private EditText editApe2;

    //Buttons
    private Button updateButton;
    private Button insertButton;
    private Button grupoPersonas;
    private ImageButton btnFecha;
    private ImageButton btnFechaDef;

    //Codificacion
    private static String tipo_Sexo[] = {"M","F"};
    private static String tipo_RolF[] = {"C", "HI", "HE", "T", "PM", "A", "PR", "D"};
    private static String tipo_EstC[] = {"S", "C", "V", "UL", "SH"};
    private static String tipo_EstNut[] = {"D","N","S"};
    private static String tipo_CondLaboral[] = {"P","O","D", "Pen"};
    private static String tipo_Etnia[] = {"B","I","N", "C", "O"};

    private ArrayList<Integer> codAseg;
    private ArrayList<Integer> codOcup;
    private ArrayList<Integer> codNacion;
    private ArrayList<Integer> codEsc;

    private int aseg;
    private int ocup;
    private int nacion;
    private int esc;

    //Conexion con base de datos SQLite
    private SQLiteAdapter adapter;

    private int codViv;

    //Manejo de fechas
    private Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener datePicker;
    private DatePickerDialog.OnDateSetListener deathDatePicker;
    private Validation validation;
    private ArrayList<Integer> dateData;
    private DecimalFormat format;

    //Constructor
    public PersonaFragment() {
        super();
        codViv = 0;
        validation = new Validation();
        dateData = new ArrayList<Integer>();
        format = new DecimalFormat("####.####");
    }

    //Constructor con codigo de vivienda como parametro
    public PersonaFragment(int codViv) {
        super();
        this.codViv = codViv;
        validation = new Validation();
        dateData = new ArrayList<Integer>();
        format = new DecimalFormat("####.####");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Prueba", "PersonaFragment");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.persona_tab, null);
        adapter = new SQLiteAdapter(view.getContext());
        codAseg = new ArrayList<Integer>();
        codOcup = new ArrayList<Integer>();
        codNacion = new ArrayList<Integer>();
        codEsc = new ArrayList<Integer>();
        aseg = 0;
        ocup = 0;
        nacion = 0;
        esc = 0;
        createWidgets();
        spinnerFunctions();
        buttonFunctions();
        pickersFunctions();
        editTextFunctions();
        setVariableOptions(childrenOptions);
        return view;
    }

    public void createTextViews() {
        txtTituloTab = (TextView) view.findViewById(R.id.txtTituloTab);
        txtIdentificacion = (TextView) view.findViewById(R.id.txtId);
        txtNombre = (TextView) view.findViewById(R.id.txtNombre);
        txtApe1 = (TextView) view.findViewById(R.id.txtApellido1);
        txtApe2 = (TextView) view.findViewById(R.id.txtApellido2);
        txtFechaNac = (TextView) view.findViewById(R.id.txtFechaNac);
        txtFechaDef = (TextView) view.findViewById(R.id.txtFechaDef);
        txtPeso = (TextView) view.findViewById(R.id.txtPeso);
        txtTalla = (TextView) view.findViewById(R.id.txtTalla);
        txtIMC = (TextView) view.findViewById(R.id.txtIMC);
        txtSexo = (TextView) view.findViewById(R.id.txtSexo);
        txtRolFam = (TextView) view.findViewById(R.id.txtRolFam);
        txtEstCiv = (TextView) view.findViewById(R.id.txtEstCiv);
        txtAseg = (TextView) view.findViewById(R.id.txtAseg);
        txtEsc = (TextView) view.findViewById(R.id.txtEscol);
        txtEstNut = (TextView) view.findViewById(R.id.txtEstNut);
        txtOcup = (TextView) view.findViewById(R.id.txtOcup);
        txtCondLab = (TextView) view.findViewById(R.id.txtCondLab);
        txtLugNac = (TextView) view.findViewById(R.id.txtLugNac);
        txtRaza = (TextView) view.findViewById(R.id.txtEtnia);
        txtJefeViv = (TextView) view.findViewById(R.id.txtJefeF);
        txtCodViv = (TextView) view.findViewById(R.id.txtCodViv);
    }

    public void createAutoCompleteTextView() {
        adapter.open();
        editNacion = (AutoCompleteTextView) view.findViewById(R.id.editNacion);

        List<ArrayList<String>> valoresNac = new ArrayList<ArrayList<String>>();
        ArrayAdapter<String> lista_Nac = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_dropdown_item_1line);
        String sqlQuery = "SELECT CodNacion, Nombre_Nacion FROM Nacion";
        valoresNac = adapter.cursorToList(sqlQuery);
        for (int i = 0; i < valoresNac.size(); ++i) {
            codNacion.add(Integer.valueOf(valoresNac.get(i).get(0)));
            lista_Nac.add(valoresNac.get(i).get(1));
        }
        editNacion.setAdapter(lista_Nac);
        adapter.close();
        //El codigo de la nacion seleccionada se recupera mediante el siguiente query
        //"SELECT codNacion FROM Nacion WHERE Nombre_Nacion = " + editNacion.getText().toString();
    }

    //Recupera datos para los spinners, de arreglos en arrays.xml y en base de datos
    private void createSpinners() {
        spinnerSexo = (Spinner) view.findViewById(R.id.spinnerSexo);
        ArrayAdapter<CharSequence> lista_Sexo = ArrayAdapter.createFromResource(view.getContext(),R.array.sexo,android.R.layout.simple_spinner_item);
        lista_Sexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(lista_Sexo);
        spinnerSexo.setSelection(0, true);

        spinnerRolF = (Spinner) view.findViewById(R.id.spinnerRolFam);
        ArrayAdapter<CharSequence> lista_rolF = ArrayAdapter.createFromResource(view.getContext(),R.array.rolFamiliar,android.R.layout.simple_spinner_item);
        lista_rolF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRolF.setAdapter(lista_rolF);
        spinnerRolF.setSelection(0, true);

        spinnerEstCiv = (Spinner) view.findViewById(R.id.spinnerEstCiv);
        ArrayAdapter<CharSequence> lista_EstCiv =
                ArrayAdapter.createFromResource(view.getContext(),R.array.estadocivil,android.R.layout.simple_spinner_item);
        lista_EstCiv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstCiv.setAdapter(lista_EstCiv);
        spinnerEstCiv.setSelection(0, true);

        spinnerAseg = (Spinner) view.findViewById(R.id.spinnerAseg);
        adapter.open();
        List<ArrayList<String>> valoresAseg = new ArrayList<ArrayList<String>>();
        String sqlQuery = "SELECT CodAsegurado, Descripcion FROM TipoAsegurado";
        valoresAseg = adapter.cursorToList(sqlQuery);
        ArrayAdapter<String> lista_Aseg = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item);
        for (int i = 0; i < valoresAseg.size(); ++i) {
            codAseg.add(Integer.valueOf(valoresAseg.get(i).get(0)));
            lista_Aseg.add(valoresAseg.get(i).get(1));
        }
        spinnerAseg.setAdapter(lista_Aseg);
        spinnerAseg.setSelection(0, true);

        spinnerEscP = (Spinner) view.findViewById(R.id.spinnerEsc);
        List<ArrayList<String>> valoresEsc = new ArrayList<ArrayList<String>>();
        sqlQuery = "SELECT CodEscolaridad, Nivel_Esc FROM Escolaridad";
        valoresEsc = adapter.cursorToList(sqlQuery);
        ArrayAdapter<String> lista_Esc = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item);
        for (int i = 0; i < valoresEsc.size(); ++i) {
            codEsc.add(Integer.valueOf(valoresEsc.get(i).get(0)));
            lista_Esc.add(valoresEsc.get(i).get(1));
        }

        ArrayAdapter<CharSequence> lista_EscP =
                ArrayAdapter.createFromResource(view.getContext(),R.array.escolaridad,android.R.layout.simple_spinner_item);
        lista_EscP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEscP.setAdapter(lista_Esc);
        spinnerEscP.setSelection(0, true);

//		spinnerEstNut = (Spinner) view.findViewById(R.id.spinnerEstNut);
//        ArrayAdapter<CharSequence> lista_EstNut = ArrayAdapter.createFromResource(view.getContext(),R.array.estNutri,android.R.layout.simple_spinner_item);
//        lista_EstNut.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerEstNut.setAdapter(lista_EstNut);	  
//        spinnerEstNut.setSelection(0, true);	

        spinnerOcup = (Spinner) view.findViewById(R.id.spinnerOcup);
        List<ArrayList<String>> valoresOcup = new ArrayList<ArrayList<String>>();
        sqlQuery = "SELECT CodOcup, Desc_Ocup FROM Ocupacion";
        valoresOcup = adapter.cursorToList(sqlQuery);
        ArrayAdapter<String> lista_Ocup = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item);
        for (int i = 0; i < valoresOcup.size(); ++i) {
            codOcup.add(Integer.valueOf(valoresOcup.get(i).get(0)));
            lista_Ocup.add(valoresOcup.get(i).get(1));
        }
        spinnerOcup.setAdapter(lista_Ocup);
        spinnerOcup.setSelection(0, true);

        spinnerCondLab = (Spinner) view.findViewById(R.id.spinnerCondLab);
        ArrayAdapter<CharSequence> lista_CondLab = ArrayAdapter.createFromResource(view.getContext(),R.array.condLaboral,android.R.layout.simple_spinner_item);
        lista_CondLab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondLab.setAdapter(lista_CondLab);
        spinnerCondLab.setSelection(0, true);

        adapter.close();

        spinnerRaza = (Spinner) view.findViewById(R.id.spinnerGrupEtn);
        ArrayAdapter<CharSequence> lista_Etnia = ArrayAdapter.createFromResource(view.getContext(),R.array.etnia,android.R.layout.simple_spinner_item);
        lista_Etnia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRaza.setAdapter(lista_Etnia);
        spinnerRaza.setSelection(0, true);
    }

    //Funciones asignadas a los spinners
    private void spinnerFunctions() {

        spinnerSexo.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerSexo.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerRolF.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerRolF.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerEstCiv.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerEstCiv.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerAseg.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                aseg =  Integer.valueOf(codAseg.get(spinnerAseg.getSelectedItemPosition()));
                spinnerAseg.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerEscP.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerEscP.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        spinnerOcup.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                ocup =  Integer.valueOf(codOcup.get(spinnerOcup.getSelectedItemPosition()));
                spinnerOcup.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerCondLab.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerCondLab.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerRaza.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerRaza.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    //Enlace de los campos de edicion de texto (Java - XML)
    private void createEditTexts() {
        editId =  (EditText) view.findViewById(R.id.editId);
        editNombre = (EditText) view.findViewById(R.id.editNombre);
        editApe1 = (EditText) view.findViewById(R.id.editApe1);
        editApe2 = (EditText) view.findViewById(R.id.editApe2);
        editFechaNac = (EditText) view.findViewById(R.id.editFecha);
        editFechaDef =  (EditText) view.findViewById(R.id.editFechaDef);
        editPeso =  (EditText) view.findViewById(R.id.editPeso);
        editTalla =  (EditText) view.findViewById(R.id.editTalla);
        editIMC = (EditText) view.findViewById(R.id.editIMC);
        editConsecJefeF =  (EditText) view.findViewById(R.id.editCodJF);
//		editCodViv =  (EditText) view.findViewById(R.id.editCodViv);
//		editCodViv.setText(String.valueOf(codViv));
//		editCodViv.setEnabled(false);	
        editEstNut = (EditText) view.findViewById(R.id.editEstNut);
        editEstNut.setEnabled(false);
        editEstNut.setText("INDEFINIDO");
    }

    //Enlace de los botones
    private void createButtons() {
        //saludButton =  (Button) view.findViewById(R.id.saludButton);
        updateButton =  (Button) view.findViewById(R.id.updateButton);
        insertButton =  (Button) view.findViewById(R.id.insertButton);
        btnFecha = (ImageButton) view.findViewById(R.id.btnFecha);
        btnFechaDef = (ImageButton) view.findViewById(R.id.btnFechaDef);
        grupoPersonas = (Button) view.findViewById(R.id.GrupoPersonasButton);
    }

//	private void buttonFunctions() {
//		
////		saludButton.setOnClickListener(new OnClickListener() {
////			
////			public void onClick(View v) {
////				String nombre = editNombre.getText().toString();
////				Toast.makeText(getActivity(), nombre, Toast.LENGTH_SHORT).show();		
////			}			
////		});
//		
//		updateButton.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				String nombre = editNombre.getText().toString();
//				Toast.makeText(getActivity(), nombre, Toast.LENGTH_LONG).show();		
//			}				
//		});			
//	}

    private void enableChildrenOptions(boolean flag) {
        int sel = 0;
        editPeso.setEnabled(flag);
        editTalla.setEnabled(flag);
        if (flag != false) {
            sel++;
        }
        switch (sel) {
            case 0:
                txtPeso.setTextColor(getResources().getColor(R.color.lightgray));
                txtTalla.setTextColor(getResources().getColor(R.color.lightgray));
                txtIMC.setTextColor(getResources().getColor(R.color.lightgray));
                txtEstNut.setTextColor(getResources().getColor(R.color.lightgray));
                break;
            case 1:
                txtPeso.setBackgroundColor(getResources().getColor(R.color.white));
                txtTalla.setBackgroundColor(getResources().getColor(R.color.white));
                txtIMC.setBackgroundColor(getResources().getColor(R.color.white));
                txtEstNut.setBackgroundColor(getResources().getColor(R.color.white));
                break;
        }
        //editEstNut.setEnabled(flag);
        //editIMC.setEnabled(flag);
    }

    private void enableAdultOptions(boolean flag) {
        int sel = 0;

        spinnerEstCiv.setEnabled(flag);
        spinnerOcup.setEnabled(flag);
        spinnerCondLab.setEnabled(flag);
        spinnerAseg.setEnabled(flag);

        if (flag != false) {
            sel++;
        }
        switch (sel) {
            case 0:
                txtEstCiv.setTextColor(getResources().getColor(R.color.lightgray));
                txtOcup.setTextColor(getResources().getColor(R.color.lightgray));
                txtCondLab.setTextColor(getResources().getColor(R.color.lightgray));
                txtAseg.setTextColor(getResources().getColor(R.color.lightgray));
                break;
            case 1:
                txtEstCiv.setBackgroundColor(getResources().getColor(R.color.white));
                txtOcup.setBackgroundColor(getResources().getColor(R.color.white));
                txtCondLab.setBackgroundColor(getResources().getColor(R.color.white));
                txtAseg.setBackgroundColor(getResources().getColor(R.color.white));
                break;
        }
    }

    private void setVariableOptions(boolean flag) {
        enableChildrenOptions(flag);
        enableAdultOptions(!flag);
    }

    private void createWidgets() {
        createTextViews();
        createAutoCompleteTextView();
        createSpinners();
        createEditTexts();
        createButtons();
    }

    public void buttonFunctions() {

        insertButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Validation validation = new Validation();
                if (!validation.isEmpty(editId.getText().toString()) && !validation.isEmpty(editNombre.getText().toString())
                        && !validation.isEmpty(editApe1.getText().toString()) && !validation.isEmpty(editApe2.getText().toString())
                        && !validation.isEmpty(editFechaNac.getText().toString()) && !validation.isEmpty(editNacion.getText().toString())
                        && !validation.isEmpty(editApe1.getText().toString()) && !validation.isEmpty(editApe2.getText().toString())
                        && !validation.isEmpty(editApe1.getText().toString()) && !validation.isEmpty(editApe2.getText().toString()))  {
                    String cedula = "\""+editId.getText().toString()+"\"";
                    String nombre = "\""+editNombre.getText().toString()+"\"";
                    String ap1 = "\""+editApe1.getText().toString()+"\"";
                    String ap2 = "\""+editApe2.getText().toString()+"\"";
                    //Toast.makeText(view.getContext(),   " civil: ", Toast.LENGTH_SHORT).show();

                    ContentValues valores_insertar = new ContentValues();
                    valores_insertar.put("cedula",cedula);
                    valores_insertar.put("nombre",nombre);
                    valores_insertar.put("ape1",ap1);
                    valores_insertar.put("ape2",ap2);
                    valores_insertar.put("fechanac",editFechaNac.getText().toString());
                    valores_insertar.put("sexo",tipo_Sexo[spinnerSexo.getFirstVisiblePosition()]);
                    valores_insertar.put("rolfamilia",tipo_RolF[spinnerRolF.getFirstVisiblePosition()]);
                    valores_insertar.put("codestadocivil",tipo_EstC[spinnerEstCiv.getFirstVisiblePosition()]);
                    valores_insertar.put("codasegurado",aseg);
                    valores_insertar.put("codescolaridad",esc);
                    valores_insertar.put("codestadonutri","D"); // falta arreglar!!
                    valores_insertar.put("codocup",ocup);
                    valores_insertar.put("codnacion",editNacion.getText().toString());
                    valores_insertar.put("codraza",tipo_Etnia[spinnerRaza.getFirstVisiblePosition()]);
                    valores_insertar.put("jefefamilia",cedula); // falta arreglar
                    valores_insertar.put("codvivienda",1);  //falta arreglar
                    //faltan valores no obligatorios


                    adapter.open();
                    long exito = adapter.insert("persona",valores_insertar);
                    adapter.close();
                    if(exito==-1){
                        Toast.makeText(view.getContext(),"No se han insertado correctamente los datos en la base de datos",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(view.getContext(),"Datos insertados correctamente", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(view.getContext(),  "Datos de persona insertados", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getActivity(), "Datos de ubicacion actualizados", Toast.LENGTH_SHORT).show();
            }
        });

        grupoPersonas.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Buscar", Toast.LENGTH_SHORT).show();
            }
        });


        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se toma por defecto el valor de la fecha
                //recuperada de la base de datos
                //Por ahora queda como esta
                DatePickerDialog dateDialog = new DatePickerDialog(
                        v.getContext(),
                        datePicker,
                        //first time around show today's date
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.show();
            }
        });

        btnFechaDef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se toma por defecto el valor de la fecha
                //recuperada de la base de datos
                //Por ahora queda como esta
                DatePickerDialog dateDialog = new DatePickerDialog(
                        v.getContext(),
                        deathDatePicker,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.show();
            }
        });

        grupoPersonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Grupo Familiar en proceso de creacion", Toast.LENGTH_SHORT).show();
                lasPersonas = PersonaDialog.newInstance(String.valueOf(codViv));
                ((DialogFragment) lasPersonas).show(getFragmentManager(), "Historial");
            }
        });
    }

    private void pickersFunctions() {
        datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                editFechaNac.setText( dayOfMonth + "/" + (++monthOfYear) + "/" + year );
                editFechaNac.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }
        };

        deathDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                editFechaDef.setText( dayOfMonth + "/" + (++monthOfYear) + "/" + year );
                editFechaDef.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }
        };
    }

    private void editTextFunctions(){
        editId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    editId.setBackgroundColor(getResources().getColor(R.color.lightblue));
                } else {
                    if (editId.getText().toString().isEmpty()) {
                        editId.setBackgroundColor(getResources().getColor(R.color.white));
                    } else {
                        editId.setBackgroundColor(getResources().getColor(R.color.lightorange));
                    }
                }
            }
        });

        editNombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    editNombre.setBackgroundColor(getResources().getColor(R.color.lightblue));
                } else {
                    if (editNombre.getText().toString().isEmpty()) {
                        editNombre.setBackgroundColor(getResources().getColor(R.color.white));
                    } else {
                        editNombre.setBackgroundColor(getResources().getColor(R.color.lightorange));
                    }
                }
            }
        });

        editApe1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    editApe1.setBackgroundColor(getResources().getColor(R.color.lightblue));
                } else {
                    if (editApe1.getText().toString().isEmpty()) {
                        editApe1.setBackgroundColor(getResources().getColor(R.color.white));
                    } else {
                        editApe1.setBackgroundColor(getResources().getColor(R.color.lightorange));
                    }
                }
            }
        });

        editApe2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    editApe2.setBackgroundColor(getResources().getColor(R.color.lightblue));
                } else {
                    if (editApe2.getText().toString().isEmpty()) {
                        editApe2.setBackgroundColor(getResources().getColor(R.color.white));
                    } else {
                        editApe2.setBackgroundColor(getResources().getColor(R.color.lightorange));
                    }
                }
            }
        });

        editFechaNac.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    editFechaNac.setBackgroundColor(getResources().getColor(R.color.lightblue));
                } else {
                    if (editFechaNac.getText().toString().isEmpty()) {
                        editFechaNac.setBackgroundColor(getResources().getColor(R.color.white));
                    } else {
                        editFechaNac.setBackgroundColor(getResources().getColor(R.color.lightorange));
                    }
                }
            }
        });

        editFechaDef.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    editFechaDef.setBackgroundColor(getResources().getColor(R.color.lightblue));
                } else {
                    if (editFechaDef.getText().toString().isEmpty()) {
                        editFechaDef.setBackgroundColor(getResources().getColor(R.color.white));
                    } else {
                        editFechaDef.setBackgroundColor(getResources().getColor(R.color.lightorange));
                    }
                }
            }
        });


        editPeso.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    editPeso.setBackgroundColor(getResources().getColor(R.color.lightblue));
                } else {
                    if (editPeso.getText().toString().isEmpty()) {
                        editPeso.setBackgroundColor(getResources().getColor(R.color.white));
                        editIMC.setText("0");
                    } else {
                        editPeso.setBackgroundColor(getResources().getColor(R.color.lightorange));
                        if (!editTalla.getText().toString().isEmpty()) {
                            double imc = validation.getIMC(Double.valueOf(editPeso.getText().toString()), Double.valueOf(editTalla.getText().toString()));
                            editIMC.setText(String.valueOf(format.format(imc)));
                            defineEstNut(imc);
                        }
                    }
                }
            }
        });

        editTalla.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    editTalla.setBackgroundColor(getResources().getColor(R.color.lightblue));
                } else {
                    if (editTalla.getText().toString().isEmpty()) {
                        editTalla.setBackgroundColor(getResources().getColor(R.color.white));
                        editIMC.setText("0");
                    } else {
                        editTalla.setBackgroundColor(getResources().getColor(R.color.lightorange));
                        if (!editPeso.getText().toString().isEmpty()) {
                            double imc = validation.getIMC(Double.valueOf(editPeso.getText().toString()), Double.valueOf(editTalla.getText().toString()));
                            editIMC.setText(String.valueOf(format.format(imc)));
                            defineEstNut(imc);
                        }
                    }
                }
            }
        });
    }

    private void defineEstNut(double imc) {
        if (imc == 0.0) {
            editEstNut.setText("INDEFINIDO");
            editEstNut.setBackgroundColor(getResources().getColor(R.color.white));
        }
        if ((imc > 0) && (imc < 18.5)) {
            editEstNut.setText("DELGADO");
            editEstNut.setBackgroundColor(getResources().getColor(R.color.red));
            editEstNut.setTextColor(getResources().getColor(R.color.lightyellow));
            editIMC.setBackgroundColor(getResources().getColor(R.color.red));
            editIMC.setTextColor(getResources().getColor(R.color.lightyellow));
        }
        if ((imc >= 18.5) && (imc < 24.99)) {
            editEstNut.setText("NORMAL");
            editEstNut.setBackgroundColor(getResources().getColor(R.color.green));
            editEstNut.setTextColor(getResources().getColor(R.color.black));
            editIMC.setBackgroundColor(getResources().getColor(R.color.green));
            editIMC.setTextColor(getResources().getColor(R.color.black));
        }
        if ((imc > 24.99)) {
            editEstNut.setText("OBESO");
            editEstNut.setBackgroundColor(getResources().getColor(R.color.red));
            editEstNut.setTextColor(getResources().getColor(R.color.lightyellow));
            editIMC.setBackgroundColor(getResources().getColor(R.color.red));
            editIMC.setTextColor(getResources().getColor(R.color.lightyellow));
        }
    }


    public void store() {

    }

    public void load() {

    }
}