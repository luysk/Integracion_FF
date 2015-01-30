package ucr.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

//Esta clase se usa para definir la estructura de la base de datos
//Ficha Familiar
public class AdminBD extends SQLiteOpenHelper {

    private DDL_SQL sql;
    private SQLiteAdapter adapter;
    private View view;

    public AdminBD(Context context, String name, CursorFactory factory,
                   int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        sql = new DDL_SQL();
    }

    public AdminBD(Context context, String databaseName, Object object, int databaseVersion) {
        super(context, databaseName, (CursorFactory) object, databaseVersion);
        sql = new DDL_SQL();
    }

    @Override
    //Este metodo se ejecuta cuando la base de datos es creada
    //o se indica expresamente que hay una modificacion
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(sql.getSQLConfiguracion());
            db.execSQL(sql.getSQLProvincia());
            db.execSQL(sql.getSQLCanton());
            db.execSQL(sql.getSQLDistrito());
            db.execSQL(sql.getSQLBarrio());
            db.execSQL(sql.getSQLUbicacion());
            db.execSQL(sql.getSQLRegionSalud());

            /*****************************************************************/
            /*    Se crean las tablas con sus respectivas llaves foraneas    */
            /*****************************************************************/
            db.execSQL(sql.getSQLAreaSalud());
            db.execSQL(sql.getSQLEBAIS());
            db.execSQL(sql.getSQLCondSocioeconomica());
            db.execSQL(sql.getSQLDenuncia());
            db.execSQL(sql.getSQLNacion());
            db.execSQL(sql.getTipoAsegurado());
            db.execSQL(sql.getSQLOcupacion());
            db.execSQL(sql.getSQLVacunas());
            db.execSQL(sql.getSQLVivienda());
            db.execSQL(sql.getSQLPersona());
            db.execSQL(sql.getSQLCondSalud());
            db.execSQL(sql.getSQLCondSaludM());
            db.execSQL(sql.getSQLControlVacunas());
            db.execSQL(sql.getSQLPadron());
            db.execSQL(sql.getSQLVideofoto());
            db.execSQL(sql.getSQLFamilia());
            db.execSQL(sql.getSQLVisita());
            db.execSQL(sql.getSQLAgenda());

            insertarProvincias(db);
            insertarDatosPruebaPersona(db);
            insertarVacunas(db);
            insertarControlVacunas(db);
            insertarDatosPruebaCondSalud(db);
            insertarDatosPruebaCondSaludM(db);
            insertarDatosPruebaCanton(db);
            insertarDatosPruebaDistrito(db);
            insertarDatosPruebaBarrio(db);
            insertarDatosPruebaAreaSalud(db);
            insertarRegion(db);
            insertarEbais(db);
            insertarDatosPruebaConfiguracion(db);



        } catch (SQLiteException SQLEx) {
            Log.v("Create table exception esto es un error", SQLEx.getMessage());
        }
    }
    private void insertarDatosPruebaPersona(SQLiteDatabase db) {
        ContentValues valores_insertar = new ContentValues();

        valores_insertar.put("consec", 2);
        valores_insertar.put("cedula", "11364000");
        valores_insertar.put("nombre", "Marcela");
        valores_insertar.put("apellido1", "Montero");
        valores_insertar.put("apellido2", "Vargas");
        valores_insertar.put("codfamact", "3S3");
        valores_insertar.put("sexo", "F");
        valores_insertar.put("relacionjefefam", "hija");
        valores_insertar.put("grupoetnico", 2);
        valores_insertar.put("paisnac", 2);
        valores_insertar.put("nivelescolaridad", 2);
        valores_insertar.put("peso", 50);
        valores_insertar.put("talla", 1.72);
        valores_insertar.put("estadonut", 1);
        valores_insertar.put("estadocivil", 0);
        valores_insertar.put("codocupacion", 2);
        valores_insertar.put("codcondlaboral", 1);
        valores_insertar.put("tipoasegurado", 0);
        valores_insertar.put("codvivienda", 3);
        valores_insertar.put("fechanac", "26/08/2000");
        valores_insertar.put("fechadef", "");
        db.insert("persona", null, valores_insertar);

        valores_insertar.clear();

        valores_insertar.put("consec", 3);
        valores_insertar.put("cedula", "11364001");
        valores_insertar.put("nombre", "Mario");
        valores_insertar.put("apellido1", "Romero");
        valores_insertar.put("apellido2", "Araya");
        valores_insertar.put("codfamact", "3S34");
        valores_insertar.put("sexo", "M");
        valores_insertar.put("relacionjefefam", "hijo");
        valores_insertar.put("grupoetnico", 2);
        valores_insertar.put("paisnac", 2);
        valores_insertar.put("nivelescolaridad", 2);
        valores_insertar.put("peso", 50);
        valores_insertar.put("talla", 1.72);
        valores_insertar.put("estadonut", 1);
        valores_insertar.put("estadocivil", 0);
        valores_insertar.put("codocupacion", 2);
        valores_insertar.put("codcondlaboral", 1);
        valores_insertar.put("tipoasegurado", 0);
        valores_insertar.put("codvivienda", 2);
        valores_insertar.put("fechanac", "26/08/2000");
        valores_insertar.put("fechadef", "");
        db.insert("persona", null, valores_insertar);
        valores_insertar.clear();


        valores_insertar.put("consec", 4);
        valores_insertar.put("cedula", "11364003");
        valores_insertar.put("nombre", "Amanda");
        valores_insertar.put("apellido1", "Rojas");
        valores_insertar.put("apellido2", "Quesada");
        valores_insertar.put("codfamact", "3S213");
        valores_insertar.put("sexo", "F");
        valores_insertar.put("relacionjefefam", "madre");
        valores_insertar.put("grupoetnico", 2);
        valores_insertar.put("paisnac", 2);
        valores_insertar.put("nivelescolaridad", 2);
        valores_insertar.put("peso", 50);
        valores_insertar.put("talla", 1.72);
        valores_insertar.put("estadonut", 1);
        valores_insertar.put("estadocivil", 0);
        valores_insertar.put("codocupacion", 2);
        valores_insertar.put("codcondlaboral", 1);
        valores_insertar.put("tipoasegurado", 0);
        valores_insertar.put("codvivienda", 1);
        valores_insertar.put("fechanac", "26/08/2000");
        valores_insertar.put("fechadef", "");
        db.insert("persona", null, valores_insertar);



    }




    private void insertarDatosPruebaCondSalud(SQLiteDatabase db) {
        ContentValues valores_insertar = new ContentValues();

        valores_insertar.put("consec", 2);
        valores_insertar.put("visitaespecialista", 0);
        valores_insertar.put("condsalud", 0);
        valores_insertar.put("condviolenciadom", 0);
        valores_insertar.put("observaciones", "cuadro viral");
        db.insert("condsalud", null, valores_insertar);

        valores_insertar.clear();
        valores_insertar.put("consec", 3);
        valores_insertar.put("visitaespecialista", 1);
        valores_insertar.put("condsalud", 0);
        valores_insertar.put("condviolenciadom", 0);
        valores_insertar.put("observaciones", "cuadro gripal");
        db.insert("condsalud", null, valores_insertar);

        valores_insertar.clear();
        valores_insertar.put("consec", 4);
        valores_insertar.put("visitaespecialista", 0);
        valores_insertar.put("condsalud", 1);
        valores_insertar.put("condviolenciadom", 0);
        valores_insertar.put("observaciones", "cuadro viral agudo");
        db.insert("condsalud", null, valores_insertar);

    }


    private void insertarDatosPruebaCondSaludM(SQLiteDatabase db) {
        ContentValues valores_insertar = new ContentValues();

        valores_insertar.put("consec", 2);
        valores_insertar.put("FechaPlan", "12/13/14");
        valores_insertar.put("TipoPlan", "GO");
        valores_insertar.put("FechaPap", "12/13/14");
        valores_insertar.put("FechaUltRegla", "12/13/14");
        valores_insertar.put("FechaEmb", "12/13/14");
        valores_insertar.put("estadoPap", 0);
        db.insert("condsaludm", null, valores_insertar);

        valores_insertar.clear();
        valores_insertar.put("consec", 3);
        valores_insertar.put("FechaPlan", "12/13/14");
        valores_insertar.put("TipoPlan", "GO");
        valores_insertar.put("FechaPap", "12/13/14");
        valores_insertar.put("FechaUltRegla", "12/13/14");
        valores_insertar.put("FechaEmb", "12/13/14");
        valores_insertar.put("estadoPap", 0);
        db.insert("condsaludm", null, valores_insertar);

        valores_insertar.clear();
        valores_insertar.put("consec", 4);
        valores_insertar.put("FechaPlan", "12/13/14");
        valores_insertar.put("TipoPlan", "GO");
        valores_insertar.put("FechaPap", "12/13/14");
        valores_insertar.put("FechaUltRegla", "12/13/14");
        valores_insertar.put("FechaEmb", "12/13/14");
        valores_insertar.put("estadoPap", 0);
        db.insert("condsaludm", null, valores_insertar);

    }

    public int insertarProvincias(SQLiteDatabase db) {

        ContentValues tuplas = new ContentValues();
        int exito = 0;
        String provincias[] = new String[8];
        provincias[0] = "NINGUNO";
        provincias[1] = "SAN JOSE";
        provincias[2] = "ALAJUELA";
        provincias[3] = "CARTAGO";
        provincias[4] = "HEREDIA";
        provincias[5] = "GUANACASTE";
        provincias[6] = "PUNTARENAS";
        provincias[7] = "LIMON";

        for (int i = 0; i < provincias.length; i++) {

            tuplas.clear();
            tuplas.put("CodProvincia", i);
            tuplas.put("Nombre_Provincia", provincias[i]);
            db.insert("provincia", null, tuplas);
        }
        return exito;
    }

    public int insertarVacunas(SQLiteDatabase db) {
        ContentValues tuplas = new ContentValues();
        int exito = 0;
        String vacunas[] = new String[10];
        vacunas[0] = "NINGUNO";
        vacunas[1] = "BCG";
        vacunas[2] = "HB ADICIONAL";
        vacunas[3] = "H.B. PRIMERA DOSIS";
        vacunas[4] = "H.B. SEGUNDA DOSIS";
        vacunas[5] = "H.B. TERCERA DOSIS";
        vacunas[6] = "PENTAVALENTE IPV I DOSIS";
        vacunas[7] = "PENTAVALENTE IPV II DOSIS";
        vacunas[8] = "PENTAVALENTE IPV III DOSIS";
        vacunas[9] = "PENTAVALENTE IPV REFUERZO";

        for (int i = 0; i < vacunas.length; i++) {

            tuplas.clear();
            tuplas.put("IdVacuna", i);
            tuplas.put("Nombre_Vacuna", vacunas[i]);
            db.insert("vacuna", null, tuplas);
        }
        return exito;
    }

    public int insertarControlVacunas(SQLiteDatabase db){
        int exito = 0;
        ContentValues valores_insertar = new ContentValues();

        valores_insertar.put("consec", 3);
        valores_insertar.put("IdVacuna", 1);
        valores_insertar.put("FechaVac", "10/11/2013");
        db.insert("ControlVacunas", null, valores_insertar);

        valores_insertar.clear();

        valores_insertar.put("consec", 2);
        valores_insertar.put("IdVacuna", 3);
        valores_insertar.put("FechaVac", "10/11/2014");
        db.insert("ControlVacunas", null, valores_insertar);
        return exito;
    }


    @Override
    //Para cambios en el script de la base de datos
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ArrayList<String> lista_tablas = new ArrayList<String>(sql.getListaTablas());

        for (int i = 0; i < lista_tablas.size(); ++i) {
            db.execSQL("DROP TABLE IF EXISTS " + lista_tablas.get(i));
            //Eliminacion de triggers requiere ser añadida.
        }
        onCreate(db);
    }

    private void insertarDatosPruebaCanton(SQLiteDatabase db) {
        ContentValues valores_insertar = new ContentValues();

        valores_insertar.put("codProvincia", 1);
        valores_insertar.put("codCanton", 0);
        valores_insertar.put("Nombre_Canton", "NINGUNO");
        db.insert("canton", null, valores_insertar);

        valores_insertar.clear();
        valores_insertar.put("codProvincia", 1);
        valores_insertar.put("codCanton", 1);
        valores_insertar.put("Nombre_Canton", "SAN JOSE");
        db.insert("canton", null, valores_insertar);
    }


    private void insertarDatosPruebaDistrito(SQLiteDatabase db) {
        ContentValues valores_insertar = new ContentValues();

        valores_insertar.put("codprovincia", 1);
        valores_insertar.put("codcanton", 1);
        valores_insertar.put("coddistrito", 0);
        valores_insertar.put("Nombre_Distrito", "NINGUNO");
        db.insert("distrito", null, valores_insertar);

        valores_insertar.clear();
        valores_insertar.put("codprovincia", 1);
        valores_insertar.put("codcanton", 1);
        valores_insertar.put("coddistrito", 1);
        valores_insertar.put("Nombre_Distrito", "CARMEN");
        db.insert("distrito", null, valores_insertar);

        valores_insertar.clear();
        valores_insertar.put("codprovincia", 1);
        valores_insertar.put("codcanton", 1);
        valores_insertar.put("coddistrito", 2);
        valores_insertar.put("Nombre_Distrito", "MERCED");
        db.insert("distrito",null,valores_insertar);

    }

    private void insertarDatosPruebaBarrio(SQLiteDatabase db) {
        ContentValues valores_insertar = new ContentValues();
        valores_insertar.put("codProvincia", 1);
        valores_insertar.put("codCanton", 1);
        valores_insertar.put("codDistrito", 1);
        valores_insertar.put("codBarrio", 0);
        valores_insertar.put("Nombre_Barrio", "NINGUNO");
        db.insert("barrio", null, valores_insertar);

        valores_insertar.clear();
        valores_insertar.put("codProvincia", 1);
        valores_insertar.put("codCanton", 1);
        valores_insertar.put("codDistrito", 1);
        valores_insertar.put("codBarrio", 101);
        valores_insertar.put("Nombre_Barrio", "AMON");
        db.insert("barrio", null, valores_insertar);

        valores_insertar.clear();
        valores_insertar.put("codProvincia", 1);
        valores_insertar.put("codCanton", 1);
        valores_insertar.put("codDistrito", 1);
        valores_insertar.put("codBarrio", 102);
        valores_insertar.put("Nombre_Barrio", "ARANJUEZ");
    }

    private void insertarRegion(SQLiteDatabase db){
        ContentValues valores = new ContentValues();
        valores.put("CODREGION",1);
        valores.put("NOMBREREGION","SJCENTRO");
        db.insert("regionsalud",null,valores);
    }

    private void insertarDatosPruebaConfiguracion(SQLiteDatabase db) {
        ContentValues valores_insertar = new ContentValues();
        valores_insertar.put("version", 1);
        valores_insertar.put("codProvincia", 1);
        valores_insertar.put("codCanton", 1);
        valores_insertar.put("codRegion", 1);
        valores_insertar.put("codAS", 2215);
        valores_insertar.put("FechaAct", 10 / 10 / 2014);
        valores_insertar.put("Email", "email@gmail.com");
        valores_insertar.put("UltCodViv", 5);
        db.insert("configuracion", null, valores_insertar);

    }

    private void insertarDatosPruebaAreaSalud(SQLiteDatabase db) {
        ContentValues valores_insertar = new ContentValues();
        valores_insertar.put("ID", 2215);
        valores_insertar.put("NOMBRE", "AREA DE SALUD ABANGARES");
        valores_insertar.put("TEL", 88888888);
        valores_insertar.put("NOMJEFEAS", "PRUEBAS");
        db.insert("areasalud", null, valores_insertar);
    }


    private void insertarEbais(SQLiteDatabase db){
        ContentValues valores = new ContentValues();
        valores.put("CODEBAIS",1);
        valores.put("NOMBRE","SAN JERONIMO");
        valores.put("ID_AREASALUD",2215);
        db.insert("ebais",null,valores);
    }

}