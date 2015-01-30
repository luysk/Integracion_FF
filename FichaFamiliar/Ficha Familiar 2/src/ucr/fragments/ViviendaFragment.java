package ucr.fragments;

import ucr.ff.R;
import java.util.Arrays;
import ucr.database.SQLiteAdapter;
import ucr.tools.Validation;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
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
public class ViviendaFragment extends Fragment {

    private View view;

    private TextView txtCodViv;
    private TextView txtM2;
    private TextView txtTenencia;
    private TextView txtEnerg;
    private TextView txtNumApos;
    private TextView txtNumDor;
    private TextView txtTipoCoc;
    private TextView txtCondCoc;
    private TextView txtEnergCoc;
    private TextView txtTipoBan;
    private TextView txtCondBan;
    private TextView txtTipoPiso;
    private TextView txtCondPiso;
    private TextView txtTipoPared;
    private TextView txtCondPared;
    private TextView txtTipoTecho;
    private TextView txtCondTecho;
    private TextView txtVentilac;
    private TextView txtIluminac;
    private TextView txtExcretas;
    private TextView txtBasura;
    private TextView txtAnimales;
    private TextView txtRiesgo;
    private TextView txtObservaciones;

    private Spinner spinnerTen;
    private Spinner spinnerEnerg;
    private Spinner spinnerNumApos;
    private Spinner spinnerNumDor;
    private Spinner spinnerTipoCoc;
    private Spinner spinnerCondCoc;
    private Spinner spinnerEnergCoc;
    private Spinner spinnerTipoBan;
    private Spinner spinnerCondBan;
    private Spinner spinnerTipoPiso;
    private Spinner spinnerCondPiso;
    private Spinner spinnerTipoPared;
    private Spinner spinnerCondPared;
    private Spinner spinnerTipoTecho;
    private Spinner spinnerCondTecho;
    private Spinner spinnerVentilacion;
    private Spinner spinnerIluminacion;
    private Spinner spinnerFtesAguas;
    private Spinner spinnerCalAguas;
    private Spinner spinnerExcretas;
    private Spinner spinnerBasura;
    private Spinner spinnerAnimales;
    private Spinner spinnerRiesgo;

    private EditText editCodViv;
    private EditText editM2;
    private EditText editObservaciones;

    private Button searchButton;
    private Button insertButton;
    private Button updateButton;
    private Button botonLimpiar;

    private static String tipo_Estados[] = {"B", "R", "M"};
    private static String tipo_Seleccion[] = {"S", "N"};
    private static String tipo_Materiales[] = {"T", "C", "K", "S", "M", "#", "P", "Z"};
    private static String tipo_Tenencia[] = {"P", "A", "R"};
    private static String tipo_Energia[] = {"E", "Q", "B", "G", "L"};
    private static String tipo_Banio[] = {"I", "E"};
    private static String tipo_Cocina[] = {"I", "C"};
    private static String tipo_Riesgo[] = {"1", "2", "3"};
    private static String tipo_ftesAgua[] = {"L", "Q", "PB", "R", "Q", "P", "C"};
    private static String tipo_dispExc[] = {"R", "C", "TS", "M", "L", "CL"};
    private static String tipo_elimBas[] = {"R", "C", "Q", "RP", "M", "E", "Q", "RPr"};

    private int codViv;
    private String fechaVisita;
    private String m2;
    private int CodTenencia;
    private int CodEnergia;
    private int NApos;
    private int NDorm;
    private int TipoCocina;
    private int CondCocina;
    private int EnergiaCocina;
    private int TipoBanio;
    private int CondBanio;
    private int TipoPiso;
    private int CondPiso;
    private int TipoPared;
    private int CondPared;
    private int TipoTecho;
    private int CondTecho;
    private int CondVent;
    private int CondIlum;
    private int FtesAgua;
    private int CondFtesAgua;
    private int DisExcretas;
    private int DispBasura;
    private int AnimCondIns;
    private int Riesgo;
    private int Observaciones;


    private ContentValues values;
    private SQLiteAdapter adapter;

    private Validation validation;

    public ViviendaFragment() {
        super();
        validation = new Validation();
    }

    public ViviendaFragment(int codViv) {
        super();
        this.codViv = codViv;
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
        view = inflater.inflate(R.layout.vivienda_tab, null);

        createWidgets();
        spinnerFunctions();
        buttonFunctions();
        editTextFunctions();
        adapter = new SQLiteAdapter(view.getContext());
        inicializar();
        return view;
    }

    public void createTextViews() {
        txtCodViv = (TextView) view.findViewById(R.id.txtCodViv);
        txtM2 = (TextView) view.findViewById(R.id.txtM2);
        txtTenencia = (TextView) view.findViewById(R.id.txtTenencia);
        txtEnerg = (TextView) view.findViewById(R.id.txtEnerg);
        txtNumApos = (TextView) view.findViewById(R.id.txtNumApos);
        txtNumDor = (TextView) view.findViewById(R.id.txtNumDor);
        txtTipoCoc = (TextView) view.findViewById(R.id.txtTipoCoc);
        txtCondCoc = (TextView) view.findViewById(R.id.txtCondCoc);
        txtEnergCoc = (TextView) view.findViewById(R.id.txtEnergCoc);
        txtTipoBan = (TextView) view.findViewById(R.id.txtTipoBan);
        txtCondBan = (TextView) view.findViewById(R.id.txtCondBan);
        txtTipoPiso = (TextView) view.findViewById(R.id.txtTipoPiso);
        txtCondPiso = (TextView) view.findViewById(R.id.txtCondPiso);
        txtTipoPared = (TextView) view.findViewById(R.id.txtTipoPared);
        txtCondPared = (TextView) view.findViewById(R.id.txtCondPared);
        txtTipoTecho = (TextView) view.findViewById(R.id.txtTipoTecho);
        txtCondTecho = (TextView) view.findViewById(R.id.txtCondTecho);
        txtVentilac = (TextView) view.findViewById(R.id.txtVentilac);
        txtIluminac = (TextView) view.findViewById(R.id.txtIluminac);
        txtExcretas = (TextView) view.findViewById(R.id.txtExcretas);
        txtBasura = (TextView) view.findViewById(R.id.txtBasura);
        txtAnimales = (TextView) view.findViewById(R.id.txtAnimales);
        txtRiesgo = (TextView) view.findViewById(R.id.txtRiesgo);
        txtObservaciones = (TextView) view.findViewById(R.id.txtObservaciones);
    }

    private void createSpinners() {

        spinnerTen = (Spinner) view.findViewById(R.id.spinnerTen);
        ArrayAdapter<CharSequence> lista_Ten = ArrayAdapter.createFromResource(view.getContext(), R.array.tenencia, android.R.layout.simple_spinner_item);
        lista_Ten.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTen.setAdapter(lista_Ten);
        spinnerTen.setSelection(0, true);

        spinnerEnerg = (Spinner) view.findViewById(R.id.spinnerEnerg);
        ArrayAdapter<CharSequence> lista_Energ = ArrayAdapter.createFromResource(view.getContext(), R.array.energia, android.R.layout.simple_spinner_item);
        lista_Energ.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEnerg.setAdapter(lista_Energ);
        spinnerEnerg.setSelection(0, true);

        spinnerNumApos = (Spinner) view.findViewById(R.id.spinnerNumApos);
        ArrayAdapter<CharSequence> lista_NumApos = ArrayAdapter.createFromResource(view.getContext(), R.array.numeric, android.R.layout.simple_spinner_item);
        lista_NumApos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNumApos.setAdapter(lista_NumApos);
        spinnerNumApos.setSelection(0, true);

        spinnerNumDor = (Spinner) view.findViewById(R.id.spinnerNumDor);
        ArrayAdapter<CharSequence> lista_NumDor = ArrayAdapter.createFromResource(view.getContext(), R.array.numeric, android.R.layout.simple_spinner_item);
        lista_NumDor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNumDor.setAdapter(lista_NumDor);
        spinnerNumDor.setSelection(0, true);

        spinnerTipoCoc = (Spinner) view.findViewById(R.id.spinnerTipoCoc);
        ArrayAdapter<CharSequence> lista_TipoCoc = ArrayAdapter.createFromResource(view.getContext(), R.array.cocina, android.R.layout.simple_spinner_item);
        lista_TipoCoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoCoc.setAdapter(lista_TipoCoc);
        spinnerTipoCoc.setSelection(0, true);

        spinnerCondCoc = (Spinner) view.findViewById(R.id.spinnerCondCoc);
        ArrayAdapter<CharSequence> lista_CondCoc = ArrayAdapter.createFromResource(view.getContext(), R.array.estados, android.R.layout.simple_spinner_item);
        lista_CondCoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondCoc.setAdapter(lista_CondCoc);
        spinnerCondCoc.setSelection(0, true);

        spinnerEnergCoc = (Spinner) view.findViewById(R.id.spinnerEnergCoc);
        ArrayAdapter<CharSequence> lista_EnergCoc = ArrayAdapter.createFromResource(view.getContext(), R.array.energia, android.R.layout.simple_spinner_item);
        lista_EnergCoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEnergCoc.setAdapter(lista_EnergCoc);
        spinnerEnergCoc.setSelection(0, true);

        spinnerTipoBan = (Spinner) view.findViewById(R.id.spinnerTipoBan);
        ArrayAdapter<CharSequence> lista_TipoBan = ArrayAdapter.createFromResource(view.getContext(), R.array.banio, android.R.layout.simple_spinner_item);
        lista_TipoBan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoBan.setAdapter(lista_TipoBan);
        spinnerTipoBan.setSelection(0, true);

        spinnerCondBan = (Spinner) view.findViewById(R.id.spinnerCondBan);
        ArrayAdapter<CharSequence> lista_CondBan = ArrayAdapter.createFromResource(view.getContext(), R.array.estados, android.R.layout.simple_spinner_item);
        lista_CondBan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondBan.setAdapter(lista_CondBan);
        spinnerCondBan.setSelection(0, true);

        spinnerTipoPiso = (Spinner) view.findViewById(R.id.spinnerTipoPiso);
        ArrayAdapter<CharSequence> lista_TipoPiso = ArrayAdapter.createFromResource(view.getContext(), R.array.materiales, android.R.layout.simple_spinner_item);
        lista_TipoPiso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoPiso.setAdapter(lista_TipoPiso);
        spinnerTipoPiso.setSelection(0, true);

        spinnerCondPiso = (Spinner) view.findViewById(R.id.spinnerCondPiso);
        ArrayAdapter<CharSequence> lista_CondPiso = ArrayAdapter.createFromResource(view.getContext(), R.array.estados, android.R.layout.simple_spinner_item);
        lista_CondPiso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondPiso.setAdapter(lista_CondPiso);
        spinnerCondPiso.setSelection(0, true);

        spinnerTipoPared = (Spinner) view.findViewById(R.id.spinnerTipoPared);
        ArrayAdapter<CharSequence> lista_TipoPared = ArrayAdapter.createFromResource(view.getContext(), R.array.materiales, android.R.layout.simple_spinner_item);
        lista_TipoPared.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoPared.setAdapter(lista_TipoPared);
        spinnerTipoPared.setSelection(0, true);

        spinnerCondPared = (Spinner) view.findViewById(R.id.spinnerCondPared);
        ArrayAdapter<CharSequence> lista_CondPared = ArrayAdapter.createFromResource(view.getContext(), R.array.estados, android.R.layout.simple_spinner_item);
        lista_CondPared.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondPared.setAdapter(lista_CondPared);
        spinnerCondPared.setSelection(0, true);

        spinnerTipoTecho = (Spinner) view.findViewById(R.id.spinnerTipoTecho);
        ArrayAdapter<CharSequence> lista_MatTec = ArrayAdapter.createFromResource(view.getContext(), R.array.materiales, android.R.layout.simple_spinner_item);
        lista_MatTec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoTecho.setAdapter(lista_MatTec);
        spinnerTipoTecho.setSelection(0, true);

        spinnerCondTecho = (Spinner) view.findViewById(R.id.spinnerCondTecho);
        ArrayAdapter<CharSequence> lista_CondTecho = ArrayAdapter.createFromResource(view.getContext(), R.array.estados, android.R.layout.simple_spinner_item);
        lista_CondTecho.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondTecho.setAdapter(lista_CondTecho);
        spinnerCondTecho.setSelection(0, true);

        spinnerVentilacion = (Spinner) view.findViewById(R.id.spinnerVentilacion);
        ArrayAdapter<CharSequence> lista_Vent = ArrayAdapter.createFromResource(view.getContext(), R.array.estados, android.R.layout.simple_spinner_item);
        lista_Vent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVentilacion.setAdapter(lista_Vent);
        spinnerVentilacion.setSelection(0, true);

        spinnerIluminacion = (Spinner) view.findViewById(R.id.spinnerIluminacion);
        ArrayAdapter<CharSequence> lista_Ilum = ArrayAdapter.createFromResource(view.getContext(), R.array.estados, android.R.layout.simple_spinner_item);
        lista_Ilum.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIluminacion.setAdapter(lista_Ilum);
        spinnerIluminacion.setSelection(0, true);

        spinnerFtesAguas = (Spinner) view.findViewById(R.id.spinnerFtesAguas);
        ArrayAdapter<CharSequence> lista_Ftes = ArrayAdapter.createFromResource(view.getContext(), R.array.ftesAgua, android.R.layout.simple_spinner_item);
        lista_Ftes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFtesAguas.setAdapter(lista_Ftes);
        spinnerFtesAguas.setSelection(0, true);

        spinnerCalAguas = (Spinner) view.findViewById(R.id.spinnerCalAguas);
        ArrayAdapter<CharSequence> lista_Cal = ArrayAdapter.createFromResource(view.getContext(), R.array.estados, android.R.layout.simple_spinner_item);
        lista_Cal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCalAguas.setAdapter(lista_Cal);
        spinnerCalAguas.setSelection(0, true);

        spinnerExcretas = (Spinner) view.findViewById(R.id.spinnerExcretas);
        ArrayAdapter<CharSequence> lista_Excretas = ArrayAdapter.createFromResource(view.getContext(), R.array.dispExcretas, android.R.layout.simple_spinner_item);
        lista_Excretas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExcretas.setAdapter(lista_Excretas);
        spinnerExcretas.setSelection(0, true);

        spinnerBasura = (Spinner) view.findViewById(R.id.spinnerBasura);
        ArrayAdapter<CharSequence> lista_Bas = ArrayAdapter.createFromResource(view.getContext(), R.array.elimBasura, android.R.layout.simple_spinner_item);
        lista_Bas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBasura.setAdapter(lista_Bas);
        spinnerBasura.setSelection(0, true);

        spinnerAnimales = (Spinner) view.findViewById(R.id.spinnerAnimales);
        ArrayAdapter<CharSequence> lista_Animales = ArrayAdapter.createFromResource(view.getContext(), R.array.seleccion, android.R.layout.simple_spinner_item);
        lista_Animales.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnimales.setAdapter(lista_Animales);
        spinnerAnimales.setSelection(0, true);

        spinnerRiesgo = (Spinner) view.findViewById(R.id.spinnerRiesgo);
        ArrayAdapter<CharSequence> lista_Riesgo = ArrayAdapter.createFromResource(view.getContext(), R.array.riesgo, android.R.layout.simple_spinner_item);
        lista_Riesgo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRiesgo.setAdapter(lista_Riesgo);
        spinnerRiesgo.setSelection(0, true);
    }

    private void spinnerFunctions() {
       /* spinnerTen.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerTen.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerEnerg.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerEnerg.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });


        spinnerNumApos.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerNumApos.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerNumDor.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerNumDor.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerTipoCoc.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerTipoCoc.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerCondCoc.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerCondCoc.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerEnergCoc.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerEnergCoc.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerTipoBan.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerTipoBan.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerCondBan.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerCondBan.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerTipoPiso.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerTipoPiso.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerCondPiso.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerCondPiso.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerTipoPared.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerTipoPared.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerCondPared.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerCondPared.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerTipoTecho.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerTipoTecho.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerCondTecho.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerCondTecho.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerVentilacion.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerVentilacion.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerIluminacion.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerIluminacion.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerFtesAguas.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerFtesAguas.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerCalAguas.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerCalAguas.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerExcretas.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerExcretas.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerBasura.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerBasura.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerAnimales.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerAnimales.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerRiesgo.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerRiesgo.setBackgroundColor(getResources().getColor(R.color.lightorange));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });*/
    }

    private void createEditTexts() {
        editCodViv = (EditText) view.findViewById(R.id.editCodViv);
        editCodViv.setText(String.valueOf(codViv));
        editCodViv.setEnabled(false);
        editM2 = (EditText) view.findViewById(R.id.editM2); //validar que no sea <= 0
        editObservaciones = (EditText) view.findViewById(R.id.editObservaciones);
    }

    private void editTextFunctions() {
        editM2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    editM2.setBackgroundColor(getResources().getColor(R.color.lightblue));
                } else {
                    if (editM2.getText().toString().isEmpty()) {
                        editM2.setBackgroundColor(getResources().getColor(R.color.white));
                    } else {
                        editM2.setBackgroundColor(getResources().getColor(R.color.lightorange));
                    }
                }
            }
        });

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
        updateButton = (Button) view.findViewById(R.id.btnact);
        botonLimpiar = (Button) view.findViewById(R.id.btnlimp);
    }

    private void buttonFunctions() {

        botonLimpiar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                inicializar();
            }
        });
        updateButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ContentValues valores_actualizar = new ContentValues();
                valores_actualizar.put("codViv", codViv);
                valores_actualizar.put("M2", editM2.getText().toString());
                valores_actualizar.put("codTenencia", tipo_Tenencia[CodTenencia]);
                valores_actualizar.put("codEnergia", tipo_Energia[CodEnergia]);
                valores_actualizar.put("nApos", Integer.parseInt(spinnerNumApos.getSelectedItem().toString()) );
                valores_actualizar.put("nDorm", Integer.parseInt(spinnerNumDor.getSelectedItem().toString()));
                valores_actualizar.put("tipoCocina", tipo_Cocina[spinnerTipoCoc.getSelectedItemPosition()]);
                valores_actualizar.put("condCocina", tipo_Estados[spinnerCondCoc.getSelectedItemPosition()]);
                valores_actualizar.put("energCocina", tipo_Energia[spinnerEnergCoc.getSelectedItemPosition()]);
                valores_actualizar.put("tipoBanio", tipo_Banio[spinnerTipoBan.getSelectedItemPosition()]);
                valores_actualizar.put("condBanio", tipo_Estados[spinnerCondBan.getSelectedItemPosition()]);
                valores_actualizar.put("tipoPiso", tipo_Materiales[spinnerTipoPiso.getSelectedItemPosition()]);
                valores_actualizar.put("condPiso", tipo_Estados[spinnerCondPiso.getSelectedItemPosition()]);
                valores_actualizar.put("tipoPared", tipo_Materiales[spinnerTipoPared.getSelectedItemPosition()]);
                valores_actualizar.put("condPared", tipo_Estados[spinnerCondPared.getSelectedItemPosition()]);
                valores_actualizar.put("tipoTecho", tipo_Materiales[spinnerTipoTecho.getSelectedItemPosition()]);
                valores_actualizar.put("condTecho", tipo_Estados[spinnerCondTecho.getSelectedItemPosition()]);
                valores_actualizar.put("condVent", tipo_Estados[spinnerVentilacion.getSelectedItemPosition()]);
                valores_actualizar.put("condIlum", tipo_Estados[spinnerIluminacion.getSelectedItemPosition()]);
                valores_actualizar.put("ftesAgua", tipo_ftesAgua[spinnerFtesAguas.getSelectedItemPosition()]);
                valores_actualizar.put("CondFtesAgua", tipo_Estados[spinnerCalAguas.getSelectedItemPosition()]);
                valores_actualizar.put("dispExcretas", tipo_dispExc[spinnerExcretas.getSelectedItemPosition()]);
                valores_actualizar.put("dispBasura", tipo_elimBas[spinnerBasura.getSelectedItemPosition()]);
                valores_actualizar.put("animCondIns", tipo_Seleccion[spinnerAnimales.getSelectedItemPosition()]);
                valores_actualizar.put("riesgo", tipo_Riesgo[spinnerRiesgo.getSelectedItemPosition()]);
                valores_actualizar.put("observaciones", editObservaciones.getText().toString());

                adapter.open();
                boolean exito = adapter.update("vivienda", valores_actualizar, "codviv", editCodViv.getText().toString());
                adapter.close();

                if (exito == true) {
                    Toast.makeText(getActivity(), "Datos de ubicacion actualizados", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(view.getContext(), "No existen datos en la base de datos para el codigo de vivienda ingresado",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createWidgets() {
        createTextViews();
        createSpinners();
        createEditTexts();
        createButtons();
    }


    public void load() {

    }

    public void inicializar() {
        if (codViv!=-25){
            String sentencia = "select * from vivienda where codviv = " + codViv;
            adapter.open();
            Cursor resultado = adapter.select(sentencia);

            resultado.moveToFirst();
            String codvivienda = resultado.getString(0);
            String m2 = resultado.getString(1);
            String codtenencia = resultado.getString(2);
            String codenergia = resultado.getString(3);
            String napos = resultado.getString(4);
            String ndorm = resultado.getString(5);
            String tipococina = resultado.getString(6);
            String condcocina = resultado.getString(7);
            String energcocina = resultado.getString(8);
            String tipobanio = resultado.getString(9);
            String condbanio = resultado.getString(10);
            String tipopiso = resultado.getString(11);
            String condpiso = resultado.getString(12);
            String tipopared = resultado.getString(13);
            String condpared = resultado.getString(14);
            String tipotecho = resultado.getString(15);
            String condtecho = resultado.getString(16);
            String condvent = resultado.getString(17);
            String condilum = resultado.getString(18);
            String ftesagua = resultado.getString(19);
            String condftesagua = resultado.getString(20);
            String dispexcretas = resultado.getString(21);
            String dispbasura = resultado.getString(22);
            String animcondins = resultado.getString(23);
            String riesgo = resultado.getString(24);
            String observaciones = resultado.getString(25);

            editCodViv.setText(codvivienda);
            editObservaciones.setText(observaciones);
            editM2.setText(m2);
            spinnerTen.setSelection(Arrays.asList(tipo_Tenencia).indexOf(codtenencia));
            spinnerEnerg.setSelection(Arrays.asList(tipo_Energia).indexOf(codenergia));
            spinnerNumApos.setSelection(Integer.parseInt(napos)-1);
            spinnerNumDor.setSelection(Integer.parseInt(ndorm)-1);
            spinnerTipoCoc.setSelection(Arrays.asList(tipo_Cocina).indexOf(tipococina));
            spinnerCondCoc.setSelection(Arrays.asList(tipo_Estados).indexOf(condcocina));
            spinnerEnergCoc.setSelection(Arrays.asList(tipo_Energia).indexOf(energcocina));
            spinnerTipoBan.setSelection(Arrays.asList(tipo_Banio).indexOf(tipobanio));
            spinnerCondBan.setSelection(Arrays.asList(tipo_Estados).indexOf(condbanio));
            spinnerTipoPiso.setSelection(Arrays.asList(tipo_Materiales).indexOf(tipopiso));
            spinnerCondPiso.setSelection(Arrays.asList(tipo_Estados).indexOf(condpiso));
            spinnerTipoPared.setSelection(Arrays.asList(tipo_Materiales).indexOf(tipopared));
            spinnerCondPared.setSelection(Arrays.asList(tipo_Estados).indexOf(condpared));
            spinnerTipoTecho.setSelection(Arrays.asList(tipo_Materiales).indexOf(tipotecho));
            spinnerCondTecho.setSelection(Arrays.asList(tipo_Estados).indexOf(condtecho));
            spinnerVentilacion.setSelection(Arrays.asList(tipo_Estados).indexOf(condvent));
            spinnerIluminacion.setSelection(Arrays.asList(tipo_Estados).indexOf(condilum));
            spinnerFtesAguas.setSelection(Arrays.asList(tipo_ftesAgua).indexOf(ftesagua));
            spinnerCalAguas.setSelection(Arrays.asList(tipo_Estados).indexOf(condftesagua));
            spinnerExcretas.setSelection(Arrays.asList(tipo_dispExc).indexOf(dispexcretas));
            spinnerBasura.setSelection(Arrays.asList(tipo_elimBas).indexOf(dispbasura));
            spinnerAnimales.setSelection(Arrays.asList(tipo_Seleccion).indexOf(animcondins));
            spinnerRiesgo.setSelection(Arrays.asList(tipo_Riesgo).indexOf(riesgo));

            adapter.close();

        }
    }
}