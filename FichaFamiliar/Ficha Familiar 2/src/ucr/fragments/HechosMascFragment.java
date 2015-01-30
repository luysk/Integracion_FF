
package ucr.fragments;

import java.util.ArrayList;

import ucr.database.SQLiteAdapter;
import ucr.dialogs.VacunaDialog;
import ucr.ff.R;
import ucr.tools.EstadoGlobal;
import ucr.tools.Validation;

import android.app.DialogFragment;
import android.widget.ImageButton;
import android.widget.Toast;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import static android.widget.Toast.*;

/**
 * Created by Laura on 12/13/2014.
 */
public class HechosMascFragment extends Fragment {
    private View view;

    private TextView txtTituloHechos;
    private TextView txtCodPer;
    private TextView txtEspec;
    private TextView txtSaludAct;
    private TextView txtTipoViol;
    private TextView txtListaVacRec;
    private TextView txtObservaciones;

    private static int codPer;
    private static int condSalud;
    private static int condViolenciaDom;
    private static int visita;
    private static String observ;
    private static String saludActual;
    private static String tipoViol;
    private static String espec;
    private VacunaDialog dialogoVacuna;

    private SQLiteAdapter adapter;

    protected ArrayList<Integer> indViolencia;
    protected ArrayList<Integer> indSalud;
    protected ArrayList<Integer> indEspecialista;

    EditText editCodPer;
    EditText editObservaciones;

    Spinner spinnerSaludAct;
    Spinner spinnerTipoViol;
    Spinner spinnerVisitaEspecialista;

    private static String tipo_Estados[] = {"B","R","M"};
    private static String tipo_Violencia[] = {"N","F","P"};
    private static String tipo_Planificacion[] = {"N","D","P","I","C"};


    Button searchButton;
    Button clearButton;
    Button updateButton;
    private ImageButton btnVacuna;
    private int idVacuna;

    private Validation validation;
    private ContentValues values;



    public HechosMascFragment(int codPers) {
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
        txtObservaciones = (TextView) view.findViewById(R.id.txtObservaciones);
    }

    private void createSpinners() {
        spinnerSaludAct = (Spinner) view.findViewById(R.id.spinnerSaludAct);
        ArrayAdapter<CharSequence> lista_SaludAct =
                ArrayAdapter.createFromResource(view.getContext(),R.array.condSalud,android.R.layout.simple_spinner_item);
        lista_SaludAct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        /*
        for (int i = 0; i < 3; ++i) {
            indSalud.add(Integer.valueOf(lista_SaludAct[i]));
        }*/

        spinnerSaludAct.setAdapter(lista_SaludAct);
        spinnerSaludAct.setSelection(0, true);

        spinnerTipoViol = (Spinner) view.findViewById(R.id.spinnerTipoViol);
        ArrayAdapter<CharSequence> lista_TipoViol =
                ArrayAdapter.createFromResource(view.getContext(),R.array.violIntrafamiliar,android.R.layout.simple_spinner_item);
        lista_TipoViol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoViol.setAdapter(lista_TipoViol);
        spinnerTipoViol.setSelection(0, true);

        spinnerVisitaEspecialista = (Spinner) view.findViewById(R.id.spinnerVisitaEspecialista);
        ArrayAdapter<CharSequence> lista_Visita = ArrayAdapter.createFromResource(view.getContext(),R.array.seleccion,android.R.layout.simple_spinner_item);
        lista_Visita.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVisitaEspecialista.setAdapter(lista_Visita);
        spinnerVisitaEspecialista.setSelection(0, true);
    }

    private void spinnerFunctions() {
        spinnerSaludAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                condSalud = spinnerSaludAct.getSelectedItemPosition();
                //spinnerSaludAct.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        spinnerTipoViol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                condViolenciaDom = spinnerTipoViol.getSelectedItemPosition();
                //spinnerTipoViol.setBackgroundColor(getResources().getColor(R.color.lightorange));
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
		searchButton =  (Button) view.findViewById(R.id.searchButtonMasc);
        clearButton =  (Button) view.findViewById(R.id.clearButtonMasc);
        updateButton =  (Button) view.findViewById(R.id.updateButtonMasc);
        btnVacuna = (ImageButton)view.findViewById(R.id.btnVacuna);
        btnVacuna.setEnabled(true);
    }

    private void limpiar(){
        editObservaciones.setText("");
        editCodPer.setText("");
        spinnerSaludAct.setSelection(0);
        spinnerTipoViol.setSelection(0);
        spinnerVisitaEspecialista.setSelection(0);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Cargando...", "HechosFragmentMasculino");
        indEspecialista = new ArrayList<Integer>();
        indSalud = new ArrayList<Integer>();
        indViolencia = new ArrayList<Integer>();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hechos_masc_tab, null);
        adapter = new SQLiteAdapter(view.getContext());
        createWidgets();
        spinnerFunctions();
        buttonFunctions();
        return view;
    }


    public void createWidgets() {
        createTextViews();
        createEditTexts();
        createSpinners();
        createButtons();
        editTextFunctions();
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
                    String sentencia = "select * from condsalud where consec = "+codPersona;
                    adapter.open();
                    Cursor resultado = adapter.select(sentencia);
                    if(resultado.getCount()==0){
                        Toast.makeText(view.getContext(),"No existen entradas en la base de datos para el codigo persona",Toast.LENGTH_LONG).show();
                    }else{
                        //trajo de la BD la consulta y la guarda en resultado
                        resultado.moveToFirst();
                        //String consec = resultado.getString(0);
                        String visitaEspecialista = resultado.getString(1);
                        String condsalud = resultado.getString(2);
                        String condviolenciadom = resultado.getString(3);
                        String observaciones = resultado.getString(4);
                        //muestra en la aplicacion los valores traidos de la BD
                        spinnerSaludAct.setSelection(Integer.parseInt(condsalud));
                        spinnerTipoViol.setSelection(Integer.parseInt(condviolenciadom));
                        spinnerVisitaEspecialista.setSelection(Integer.parseInt(visitaEspecialista));
                        editObservaciones.setText(observaciones);
                        //new EstadoGlobal().getInstance().setCodPersona(codPersona);
                        Log.e("SearchButton", "evento en HechosFragmentMasc");
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
                    //valores_actualizar.put("consec", codPersona);
                    valores_actualizar.put("visitaespecialista", spinnerVisitaEspecialista.getSelectedItemPosition());
                    valores_actualizar.put("condSalud", spinnerSaludAct.getSelectedItemPosition());
                    valores_actualizar.put("condviolenciadom", spinnerTipoViol.getSelectedItemPosition());
                    valores_actualizar.put("observaciones", editObservaciones.getText().toString());

                    adapter.open();
                    boolean exito = adapter.update("condsalud", valores_actualizar, "consec", codPersona);
                    adapter.close();
                    if (!exito) {
                        Toast.makeText(view.getContext(), "No se han insertado correctamente en la base de datos", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(view.getContext(), "Datos actualizados correctamente", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (validation.isEmpty(editCodPer.getText().toString())) {
                        Toast.makeText(view.getContext(), "Debe ingresar el codigo de la persona que desea actualizar", Toast.LENGTH_LONG).show();
                    }
                    adapter.close();
                }
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
                                                 adapter.close();

                                                 //dialogoVacuna = VacunaDialog.newInstance(String.valueOf(0), vacunas);
                                                 //((DialogFragment) dialogoVacuna).show(getFragmentManager(), "Historial");
                                                 //String r = dialogoVacuna.getResultado();
                                                 //Toast.makeText(view.getContext(),"->"+r,Toast.LENGTH_LONG).show();
                                             }
                                             adapter.close();
                                         }
                                     }

        );*/

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

    }

    private void setValues() {
        values = new ContentValues();
        values.put("codPer", getCodPer());
        values.put("observ", editObservaciones.getText().toString());
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

