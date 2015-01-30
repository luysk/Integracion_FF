package ucr.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
//import android.os.Environment;
import android.os.Environment;

//Esta clase permite administrar la base de datos
//que se crea con la clase AdminBD
public class SQLiteAdapter {
	private static final String DATABASE_NAME = "FF.sqlite"; //Nombre por el que se reconocera a la BD
    private static final int DATABASE_VERSION = 1; //Este atributo se cambia cuando hay modificaciones
	private SQLiteDatabase db;
	private final Context context;
	private final AdminBD adminBD;
	
	//Constructor
	public SQLiteAdapter(Context ctx) {
        //Para emulador de Android
        String environment = Environment.getDataDirectory().toString() + "/data/ucr.ff/databases/";
        //Para Motorola Xoom
        //String environment = Environment.getDataDirectory().toString() + "/Android/data/ucr.ff/databases/";
        //String environment = "/storage/sdcard0/Android/data/ff.ucr/databases/";
        //String environment = Environment.getExternalStorageDirectory().getPath();
        context = ctx;
        adminBD = new AdminBD(context, environment + DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	//Su función es abrir la base de datos
	public void open() throws SQLiteException {
		try {
			db = adminBD.getWritableDatabase();
		} catch (SQLiteException SQLEx) {
			db = adminBD.getReadableDatabase();
		}
	}
	
	//Su propósito es insertar en una tabla específica una tupla
	public long insert(String tabla, ContentValues tupla) throws SQLiteException { 

        /**************************************************************************/
        /* Cambio realizado el 5/10/2014 LCVQ                                     */
        /**************************************************************************/
        try {
			return db.insert(/*"insertando en tabla " + */tabla, null, tupla);
		} catch (SQLiteException SQLEx) {
			return -1;
		}
	}
	
	//Su propósito es insertar en una tabla específica una tupla
	public boolean insert1(String tabla, ContentValues tupla) throws SQLiteException { 
		try {
			db.insert("insertando en tabla " + tabla, null, tupla);
			return true;
		} catch (SQLiteException SQLEx) {
			return false;
		}
	}
	
	//Su función es eliminar de una tabla lo que corresponda a una consulta SQL
    public boolean delete(String tabla, int pk, String rowId) {
        return db.delete(tabla, pk + "=" + rowId, null) > 0;
    }
    
    //Selecciona de una tabla que se indica la o las tuplas que cumplan con la instruccion SQL
    public Cursor select(String tabla, String [] columnas, String pk,String rowId) throws SQLException {
        Cursor mCursor = db.query(true, tabla, columnas, pk + "=" + rowId, null, null, null, null, null);
        return mCursor;
    }
    
    //Selecciona de una tabla que se indica la o las tuplas que cumplan con la instruccion SQL
    public Cursor select(String tabla, String [] columnas, String where, String []args) throws SQLException {
        Cursor mCursor = db.query(true, tabla, columnas, where, args, null, null, null, null);        
        return mCursor;
    }

  //Selecciona de una tabla las tuplas que correspondan a una consulta SQL
    public Cursor select(String sql) throws SQLException {
    	Cursor mCursor = db.rawQuery(sql, null);
    	return mCursor;
    }
    
    //Ejecuta una actualizacion
    public boolean update(String tabla,ContentValues args, String pk, long rowId) {
        return db.update(tabla, args, pk + "=" + rowId, null) > 0;
    }
    
    //Ejecuta una actualizacion
    public boolean update(String tabla,ContentValues args, String pk, String rowId) {
        return db.update(tabla, args, pk + "=" + rowId, null) > 0;
    }
    
    //Recupera una consulta y coloca el resultado en una lista
	public List<ArrayList<String>> cursorToList(String sqlQuery) {
		List<ArrayList<String>> valores = new ArrayList<ArrayList<String>>();
		Cursor cursor = select(sqlQuery);
		while (cursor.moveToNext()) {
			ArrayList<String> temp = new ArrayList<String>();
			for (int i = 0; i < cursor.getColumnCount(); ++i) {				
				temp.add(cursor.getString(i));
			}
			valores.add(temp);
		}		
		return valores;
	}
	
    //Cierra la base de datos
	public void close() {
		db.close();
	}

}