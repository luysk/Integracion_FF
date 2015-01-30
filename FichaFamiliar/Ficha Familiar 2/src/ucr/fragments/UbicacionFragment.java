package ucr.fragments;

import ucr.ff.R;
import java.util.ArrayList;
import java.util.List;

import ucr.database.SQLiteAdapter;
import ucr.tools.EstadoGlobal;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


@SuppressWarnings("unused")
public class UbicacionFragment extends Fragment{

    private int autoIncrement;

    private View view;
    private TextView txtTituloTab;
    private TextView txtCodViv;
    private TextView txtFechaVisita;
    private TextView txtRegionSalud;
    private TextView txtAreaSalud;
    private TextView txtEBAIS;
    private TextView txtProvincia;
    private TextView txtCanton;
    private TextView txtDistrito;
    private TextView txtDireccion;
    private TextView txtLatitud;
    private TextView txtLongitud;

    private Spinner spinnerDist;
    private Spinner spinnerBarr;
    private Spinner spinnerEBAIS;

    private EditText editCodViv;
    private EditText editFechaVis;
    private EditText editRegionSalud;
    private EditText editAreaSalud;
    private EditText editProv;
    private EditText editCant;
    private EditText editLat;
    private EditText editLong;

    private Button searchButton;
    private Button insertButton;
    private Button updateButton;
    private Button botonLimpiar;

    private SQLiteAdapter adapter;

    protected ArrayList<Integer> codDistritos;
    protected ArrayList<Integer> codBarrios;
    protected ArrayList<Integer> codEBAIS;
    protected ArrayAdapter<String> lista_Dist;

    private Switch visita;

    private static int regionSalud;
    private static int areaSalud;
    private static int prov;
    private static int cant;
    private static int dist;
    private static int barr;
    private static int arsa;
    private static int ebais;
    private int indice_barrio;

    private Validation validation;
    private boolean preloaded_data;
    private ContentValues values;

    ArrayAdapter<String> lista_Barr;

    public UbicacionFragment() {
        super();
        validation = new Validation();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Iniciando...", "UbicacionFragment");
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ubicacion_tab, null);
        adapter = new SQLiteAdapter(view.getContext());
        codDistritos = new ArrayList<Integer>();
        codBarrios = new ArrayList<Integer>();
        codEBAIS = new ArrayList<Integer>();
        initialize();
        return view;
    }


    private void initialize() {
        indice_barrio = -1;
        prov = 0;
        cant = 0;
        dist = 0;
        barr = 0;
        regionSalud = 0;
        areaSalud = 0;
        ebais = 0;
        getConfigurationData();
        createWidgets();
        widgetsFunctions();
        autoIncrement = 0;
        lista_Barr = null;
    }


    /**
     * Funcion : Toma los datos de la configuracion que se bajo de la base de datos centralizada
     * Requiere : No requiere nada
     * Modifica : No modifica nada
     * */
    private void getConfigurationData() {
        adapter.open();
        String sqlQuery = "SELECT CodProvincia, CodCanton, CodRegion, CodAS FROM Configuracion WHERE Version =  1";
        List<ArrayList<String>> valores = new ArrayList<ArrayList<String>>();
        valores = adapter.cursorToList(sqlQuery);
        prov = Integer.valueOf(valores.get(0).get(0));
        cant = Integer.valueOf(valores.get(0).get(1));
        regionSalud = Integer.valueOf(valores.get(0).get(2));
        areaSalud = Integer.valueOf(valores.get(0).get(3));
        adapter.close();
    }

    /**
     * Funcion : llama a los metodos que inicializan los componentes de la interfaz
     * Requiere : No requiere nada
     * Modifica : No modifica nada
     * */
    private void createWidgets() {
        createTextViews();
        createSpinners();
        createEditTexts();
        createButtons();
    }

    /**
     * Funcion : Inicializa todos los textviews del view actual
     * Requiere : No requiere nada
     * Modifica : No modifica nada
     * */
    public void createTextViews() {
        txtTituloTab = (TextView) view.findViewById(R.id.txtTituloTab);
        txtCodViv = (TextView) view.findViewById(R.id.txtCodViv);
        txtProvincia = (TextView) view.findViewById(R.id.txtProvincia);
        txtCanton = (TextView) view.findViewById(R.id.txtCanton);
        txtDistrito = (TextView) view.findViewById(R.id.txtDistrito);
        txtDireccion = (TextView) view.findViewById(R.id.txtDireccion);
        txtAreaSalud = (TextView) view.findViewById(R.id.txtAreaSalud);
        txtEBAIS = (TextView) view.findViewById(R.id.txtEBAIS);
        txtLatitud = (TextView) view.findViewById(R.id.txtLatitud);
        txtLongitud = (TextView) view.findViewById(R.id.txtLongitud);
    }

    /**
     * Funcion : Crea los spinners de la interfaz de ubicacion
     * Requiere : No requiere nada
     * Modifica : No modifica nada
     * */
    private void createSpinners() {
        String sqlQuery = "";
        adapter.open();
        spinnerDist = (Spinner) view.findViewById(R.id.spinnerDist);
        spinnerBarr = (Spinner) view.findViewById(R.id.spinnerBarr);
        spinnerEBAIS = (Spinner) view.findViewById(R.id.spinnerEBAIS);

        /*******************************************************************************************/
        sqlQuery = "SELECT CodDistrito, Nombre_Distrito FROM Distrito WHERE CodProvincia = " + prov +
                " AND CodCanton = " + cant;
        List<ArrayList<String>> valores = new ArrayList<ArrayList<String>>();
        valores = adapter.cursorToList(sqlQuery);
        lista_Dist = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item);
        for (int i = 0; i < valores.size(); ++i) {
            lista_Dist.add(valores.get(i).get(1));
            codDistritos.add(Integer.valueOf(valores.get(i).get(0)));
        }
        spinnerDist.setAdapter(lista_Dist);
        spinnerDist.setSelection(0, true);
        /*******************************************************************************************/
        sqlQuery = "SELECT CodEBAIS, nombre FROM EBAIS WHERE id_areasalud = " + areaSalud;
        valores = adapter.cursorToList(sqlQuery);
        ArrayAdapter<String> lista_EBAIS = new ArrayAdapter<String>(view.getContext(),
                                                                android.R.layout.simple_spinner_item);
        for (int i = 0; i < valores.size(); ++i) {
            lista_EBAIS.add(valores.get(i).get(1));
            codEBAIS.add(Integer.valueOf(valores.get(i).get(0)));
        }
        spinnerEBAIS.setAdapter(lista_EBAIS);
        spinnerEBAIS.setSelection(0, true);
        /*******************************************************************************************/
        adapter.close();
    }
    public void spinnerFunctions() {

        spinnerDist.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                dist = codDistritos.get(spinnerDist.getSelectedItemPosition());
                adapter.open();
                spinnerBarr.invalidate();
                codBarrios.clear();

                obtenerBarrios(""+prov,""+cant,""+dist);

                spinnerBarr.setAdapter(lista_Barr);

                if(indice_barrio!=-1){
                    spinnerBarr.setSelection(indice_barrio,true);
                    indice_barrio = -1;
                }else {
                    spinnerBarr.setSelection(0, true);
                }
                adapter.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerBarr.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                barr = codBarrios.get(spinnerBarr.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        spinnerEBAIS.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                ebais =  Integer.valueOf(codEBAIS.get(spinnerEBAIS.getSelectedItemPosition()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void createEditTexts() {
        editCodViv = (EditText) view.findViewById(R.id.editCodViv);
        editFechaVis = (EditText) view.findViewById(R.id.editFechaVis);
        editRegionSalud = (EditText) view.findViewById(R.id.editRegionSalud);
        editAreaSalud = (EditText) view.findViewById(R.id.editAreaSalud);
        editProv = (EditText) view.findViewById(R.id.editProv);
        editCant = (EditText) view.findViewById(R.id.editCant);
        editLat =  (EditText) view.findViewById(R.id.editLat);
        editLong =  (EditText) view.findViewById(R.id.editLong);
    }


    private String getData(String sqlQuery) {
        adapter.open();
        List<ArrayList<String>> valores = new ArrayList<ArrayList<String>>();
        valores = adapter.cursorToList(sqlQuery);
        adapter.close();
        return valores.get(0).get(0);
    }

    /**
     * Funcion : Coloca el valor inicial a los edittext
     * Requiere : No requiere nada
     * Modifica : No modifica nada
     * */
    private void editTextFunctions() {
        editFechaVis.setText(validation.getDate());
        adapter.open();
        String sqlQuery = "SELECT Nombre_Provincia FROM Provincia WHERE CodProvincia = " + prov;
        editProv.setText(getData(sqlQuery));
        editProv.setEnabled(false);
        /******************************************************************************************/
        sqlQuery = "SELECT Nombre_Canton FROM Canton WHERE CodProvincia = " + prov +
                " AND CodCanton = " + cant;
        editCant.setText(getData(sqlQuery));
        editCant.setEnabled(false);
        /******************************************************************************************/
        sqlQuery = "SELECT NombreRegion FROM RegionSalud WHERE CodRegion = " + regionSalud;
        editRegionSalud.setText(getData(sqlQuery));
        editRegionSalud.setEnabled(false);
        /******************************************************************************************/
        sqlQuery = "SELECT nombre FROM AreaSalud WHERE id = " + areaSalud;
        editAreaSalud.setText(getData(sqlQuery));
        editAreaSalud.setEnabled(false);
    }

    private void createButtons() {
        searchButton =  (Button) view.findViewById(R.id.searchButton);
        insertButton =  (Button) view.findViewById(R.id.insertButton);
        updateButton =  (Button) view.findViewById(R.id.updateButton);
        botonLimpiar =  (Button) view.findViewById(R.id.clearButton);
    }

    private void limpiar(){
        editCodViv.setText("");
        editLat.setText("");
        editLong.setText("");
        spinnerDist.setSelection(0);
        spinnerBarr.setSelection(0);
        spinnerEBAIS.setSelection(0);
        visita = (Switch)view.findViewById(R.id.switchVisitaEfectiva);
        visita.setChecked(false);
    }

    private void buttonFunctions() {

        botonLimpiar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiar();
            }
        });

        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validation.isEmpty(editCodViv.getText().toString())){
                    int codVivienda = Integer.parseInt(editCodViv.getText().toString());
                    String sentencia = "select * from ubicacion where codviv = "+codVivienda;
                    adapter.open();
                    Cursor resultado = adapter.select(sentencia);
                    if(resultado.getCount()==0){
                        Toast.makeText(view.getContext(),"No existen entradas en la base de datos para el codigo vivienda",Toast.LENGTH_LONG).show();
                    }else{
                        /* Se mueve el cursor al primer resultado, y en este caso es el unico*/
                        resultado.moveToFirst();
                        /*Se extraen los valores de las columnas*/
                        String codViv =resultado.getString(0);
                        String fecha = resultado.getString(1);
                        String regionSalud = resultado.getString(2);
                        String areaSalud = resultado.getString(3);
                        String Ebais = resultado.getString(4);
                        String provincia = resultado.getString(5);
                        String canton = resultado.getString(6);
                        String distrito = resultado.getString(7);
                        String barrio = resultado.getString(8);
                        String lat = resultado.getString(9);
                        String lon = resultado.getString(10);
                        String visitaEfectiva = resultado.getString(11);

                        visita = (Switch) view.findViewById(R.id.switchVisitaEfectiva);
                        if (visitaEfectiva.charAt(0)=='Y') {
                            visita.setChecked(true);
                        }else{
                            visita.setChecked(false);
                        }

                        editLat.setText(lat);
                        editLong.setText(lon);
                        int indice_dist = getIndiceDistrito(distrito);
                        obtenerBarrios(provincia,canton,distrito);
                        indice_barrio = getIndiceBarrio(barrio);
                        spinnerEBAIS.setSelection(getIndiceEbaias(Ebais),true);
                        spinnerDist.setSelection(indice_dist, true);
                        new EstadoGlobal().getInstance().setCodViv(codVivienda);

                    }
                    adapter.close();

                }else{
                    Toast.makeText(view.getContext(),"Debe ingresar un codigo de vivienda para hacer la consulta",Toast.LENGTH_LONG).show();
                }
            }
        });

        insertButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if ((prov != 0) && (cant != 0) && (dist != 0) && (barr != 0)
                        && (!validation.isEmpty(editLat.getText().toString())) && (!validation.isEmpty(editLong.getText().toString()))) {
                    int codVivienda = Integer.parseInt(editCodViv.getText().toString());
                    String sentencia = "select * from ubicacion where codviv = "+codVivienda;
                    adapter.open();
                    Cursor resultado = adapter.select(sentencia);
                    if(resultado.getCount()==0) {
                        double latitud = Double.valueOf(editLat.getText().toString());
                        double longitud = Double.valueOf(editLong.getText().toString());

                        ContentValues valores_insertar = new ContentValues();
                        String fecha = editFechaVis.getText().toString();
                        String visitaEfectiva = "";

                        visita = (Switch) view.findViewById(R.id.switchVisitaEfectiva);
                        if (visita.isChecked()) {
                            visitaEfectiva += "Y";
                        } else {
                            visitaEfectiva += "N";
                        }

                        valores_insertar.put("codviv", editCodViv.getText().toString());
                        valores_insertar.put("fechavis", fecha);
                        valores_insertar.put("codrs", regionSalud);
                        valores_insertar.put("codprovincia", prov);
                        valores_insertar.put("codcanton", cant);
                        valores_insertar.put("coddistrito", dist);
                        valores_insertar.put("codbarrio", barr);
                        valores_insertar.put("codas", areaSalud);
                        valores_insertar.put("codebais", ebais);
                        valores_insertar.put("latitud", latitud);
                        valores_insertar.put("longitud", longitud);
                        valores_insertar.put("visefec", visitaEfectiva);


                        adapter.open();
                        long exito = adapter.insert("ubicacion", valores_insertar);
                        adapter.close();
                        if (exito == -1) {
                            Toast.makeText(view.getContext(), "No se han insertado correctamente los datos en la base de datos", Toast.LENGTH_LONG).show();
                        } else {
                            int vivienda = insertarVivienda();
                            if (vivienda != 0) {
                                new EstadoGlobal().getInstance().setCodViv(codVivienda);
                                limpiar();
                                Toast.makeText(view.getContext(), "Datos insertados correctamente", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(view.getContext(), "No se han insertado correctamente los datos en la base de datos", Toast.LENGTH_LONG).show();
                            }
                        }
                    }else{
                        Toast.makeText(view.getContext(),"El codigo de vivienda ingresado ya esta en uso, ingrese uno nuevo",Toast.LENGTH_LONG).show();
                    }
            } else {
                Toast.makeText(view.getContext(),  "Datos de ubicacion insertados", Toast.LENGTH_SHORT).show();
            }
        }
    });

        updateButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if((prov != 0) && (cant != 0) && (dist != 0) && (barr != 0)
                        &&(!validation.isEmpty(editCodViv.getText().toString())) &&
                        (!validation.isEmpty(editLat.getText().toString())) && (!validation.isEmpty(editLong.getText().toString()))) {

                    String visitaEfectiva = "";

                    visita = (Switch) view.findViewById(R.id.switchVisitaEfectiva);
                    if(visita.isChecked()){
                        visitaEfectiva+="Y";
                        Toast.makeText(view.getContext(),"Esta activada",Toast.LENGTH_LONG).show();
                    }else{
                        visitaEfectiva+="N";
                        Toast.makeText(view.getContext(),"Esta desactivada",Toast.LENGTH_LONG).show();
                    }

                    ContentValues valores_actualizar  = new ContentValues();
                    valores_actualizar.put("codrs",regionSalud);
                    valores_actualizar.put("codprovincia",prov);
                    valores_actualizar.put("codcanton",cant);
                    valores_actualizar.put("coddistrito",dist);
                    valores_actualizar.put("codbarrio",barr);
                    valores_actualizar.put("codas",areaSalud);
                    valores_actualizar.put("codebais",ebais);
                    valores_actualizar.put("latitud",editLat.getText().toString());
                    valores_actualizar.put("longitud",editLong.getText().toString());
                    valores_actualizar.put("visefec",visitaEfectiva);

                    adapter.open();
                    boolean exito = adapter.update("ubicacion",valores_actualizar,"codviv",editCodViv.getText().toString());

                    if(exito==true){
                        Toast.makeText(getActivity(), "Datos de ubicacion actualizados", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(view.getContext(),"No existen datos en la base de datos para el codigo de vivienda ingresado",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(validation.isEmpty(editCodViv.getText().toString())){
                        Toast.makeText(view.getContext(),"Debe ingresar el codigo de la vivienda que desea actualizar",Toast.LENGTH_LONG).show();
                    }else{
                        if(validation.isEmpty(editLat.getText().toString()) || validation.isEmpty(editLong.getText().toString())){
                            Toast.makeText(view.getContext(),"Debe ingresar los valores de la longitud y latitud",Toast.LENGTH_LONG).show();
                        }else{
                            if(prov==0 || cant==0 || dist == 0 || barr ==0){
                                Toast.makeText(view.getContext(),"Debe seleccionar una provincia,un canton, un distrito y un barrio",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }

            }
        });
    }



    private void widgetsFunctions() {
        spinnerFunctions();
        editTextFunctions();
        buttonFunctions();
    }


    private int getIndiceDistrito(String distrito){
        boolean accion = true;
        int contador = 0;
        while(accion ==true){
            if(codDistritos.get(contador)==Integer.parseInt(distrito)){
                accion = false;
            }else{
                contador++;
                if(contador == codDistritos.size()){
                    accion = false;
                    contador = -1;
                }
            }
        }
        return  contador;
    }

    private int getIndiceEbaias(String ebais){
        boolean accion = true;
        int contador = 0;
        while(accion ==true){
            if(codEBAIS.get(contador)==Integer.parseInt(ebais)){
                accion = false;
            }else{
                contador++;
                if(contador == codEBAIS.size()){
                    accion = false;
                    contador = -1;
                }
            }
        }
        return  contador;
    }

    private int getIndiceBarrio(String barrio){
        boolean accion = true;
        int contador = 0;
        while(accion == true){
            if(codBarrios.get(contador)==Integer.parseInt(barrio)){
                accion = false;
            }else{
                contador++;
                if(contador == codBarrios.size()){
                    accion = false;
                    contador = -1;
                }
            }
        }
        return contador;
    }


    private void obtenerBarrios(String provincia,String canton,String distrito){
        String sqlQuery = "SELECT CodBarrio, Nombre_Barrio FROM Barrio WHERE CodProvincia = " + provincia +
                " AND CodCanton = " + canton + " AND CodDistrito = " + distrito;
        List<ArrayList<String>> valores = new ArrayList<ArrayList<String>>();
        valores = adapter.cursorToList(sqlQuery);
        lista_Barr = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item);
        for (int i = 0; i < valores.size(); ++i) {
            lista_Barr.add(valores.get(i).get(1));
            codBarrios.add(Integer.valueOf(valores.get(i).get(0)));
        }
    }
    public int insertarVivienda(){
        int correcto = 0;
        int codViv = Integer.parseInt(editCodViv.getText().toString());
        ContentValues valores = new ContentValues();
        valores.put("codViv", codViv);
        valores.put("M2", 0);
        valores.put("codEnergia","E");
        valores.put("codTenencia","P");
        valores.put("nApos",1);
        valores.put("nDorm",1);
        valores.put("tipoCocina","I");
        valores.put("condCocina","B");
        valores.put("energCocina","E");
        valores.put("tipoBanio","I");
        valores.put("condBanio","B");
        valores.put("tipoPiso","T");
        valores.put("condPiso","B");
        valores.put("tipoPared","T");
        valores.put("condPared","B");
        valores.put("tipoTecho","T");
        valores.put("condTecho","B");
        valores.put("condVent","B");
        valores.put("condIlum","B");
        valores.put("ftesAgua","L");
        valores.put("CondFtesAgua","B");
        valores.put("dispExcretas","R");
        valores.put("dispBasura", "R");
        valores.put("animCondIns","S");
        valores.put("riesgo",1);
        valores.put("observaciones","------");

        adapter.open();
        long exito = adapter.insert("vivienda",valores);
        if(exito!=-1){
            correcto++;
        }
        adapter.close();
        return correcto;
    }
}


