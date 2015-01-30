package ucr.ff;

import java.io.BufferedReader;
import ucr.database.DBExportImport;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import ucr.fragments.HechosMascFragment;
import ucr.fragments.PersonaFragment;
import ucr.fragments.SocioeconomicoFragment;
import ucr.fragments.UbicacionFragment;
import ucr.fragments.VisitasFragment;
import ucr.fragments.ViviendaFragment;
import ucr.fragments.DenunciaFragment;
import ucr.fragments.MapaFragment;
import ucr.fragments.HechosFragment;
import ucr.send.FileChooser;
import ucr.tools.EstadoGlobal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

//Clase diseñada para administrar los procesos de la Ficha Familiar
public class FichaFamiliar extends Activity implements TabListener {
	 private static final int REQUEST_PATH = 1;
     private static final int MASCULINO = 2;
     String curFilePathCompleto;
	 EditText edittext;
	 String curnombre;
	 Context contexto = this;


	 
	 static private MapaFragment elMapa;//WeakReference<MapaFragment> elMapa;
		
	 private RelativeLayout mainLayout;	 
	 private FragmentTransaction fragTransactMgr;
	 
	 private String caption1;
	 private String caption2;
	 private String caption3;
	 private String caption4;
	 private String caption5;
	 private String caption6;
	 private String caption7;
	 private String caption8;
	 
	 private int codViv;
     private int codPer;
     private int genero;
	 
	 //Constructor
	 public FichaFamiliar() {
		 fragTransactMgr = null;
		 caption1 = "Ubicacion";
		 caption2 = "Vivienda";
		 caption3 = "Cond. Socioec.";
		 caption4 = "Personas";
		 caption5 = "Hechos";
		 caption6 = "Visitas";
		 caption7 = "Denuncias";
		 caption8 = "Mapa";
		 
		 codViv = 0;
         codPer = 0;
		 
		 elMapa = null;
	 }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    	setContentView(R.layout.activity_tab);
    	mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
    	fragTransactMgr = getFragmentManager().beginTransaction(); 
    	ActionBar bar = getActionBar();
        bar.addTab(bar.newTab().setText(caption1).setIcon(R.drawable.ubication).setTabListener(this));
        bar.addTab(bar.newTab().setText(caption2).setIcon(R.drawable.home).setTabListener(this));
    	bar.addTab(bar.newTab().setText(caption3).setIcon(R.drawable.socioeconomics).setTabListener(this));
    	bar.addTab(bar.newTab().setText(caption4).setIcon(R.drawable.person).setTabListener(this)); 
    	bar.addTab(bar.newTab().setText(caption5).setIcon(R.drawable.medical_report).setTabListener(this)); 
    	bar.addTab(bar.newTab().setText(caption6).setIcon(R.drawable.prescription).setTabListener(this)); 
    	bar.addTab(bar.newTab().setText(caption7).setTabListener(this)); 
    	bar.addTab(bar.newTab().setText(caption8).setTabListener(this)); 
    	bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,ActionBar.DISPLAY_USE_LOGO);
    	bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
    	bar.setDisplayShowHomeEnabled(true); //True si se quiere icono, false si no
    	bar.setDisplayShowTitleEnabled(true);
        new DBExportImport().mkdir();
    	bar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tab, menu);
        return true;
    }
    /** CODIGO AGREGADO 10-6-13 PARA MANEJAR MENU DE ENVIO DE DATOS MODIFICADO 19/7/13 **/
    /***************************************************************/
    
    
    /**Este metodo crea y ejecuta la activity que permite elegir el archivo a enviar 
     * No retorna un valor porque lo que hace es "pasar" al metodo onActivityResult despues de ejecutarse
     *
     * */
    public void getfile(){ 
    	Intent intent1 = new Intent(this, FileChooser.class);
        startActivityForResult(intent1,REQUEST_PATH);
    }
 	
    /**Este metodo comprueba que se halla elegido un path, lo coloca junto con el nombre del archivo
     *  en el basicnamevaluepair y  ejecuta la tarea asincrona de envio de dicho archivo
     *  NOTA: en la linea: int respuesta = new Enviador().execute(enviado).get(); el ".get()" deberea hacer que el thread principal se espere a recibir un resultado del envio antes de continuar
     * */
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // See which child activity is calling us back.
    	if (requestCode == REQUEST_PATH){
    		if (resultCode == RESULT_OK) { 
    			curFilePathCompleto = data.getStringExtra("GetPath") +"/"+ data.getStringExtra("GetFileName"); 
            	curnombre = data.getStringExtra("GetFileName"); 
    			
            	BasicNameValuePair enviado = new BasicNameValuePair(curnombre,curFilePathCompleto);	
            	try {
        			int respuesta = new Enviador().execute(enviado).get(); //ejecuta la tarea asincrona de mandar la imagen	
        																	// con esto el ui thread sigue ejecutandose, pero si se le agrega .get() se espera a que termine la asynctask
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
            	

    		}
    	 }
    }
    
    /** metodo que maneja la seleccion de items del menu*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Manejo de menu
        switch (item.getItemId()) {
            case R.id.menu_option0:
            	//getfile();//en curFilePathCompleto tengo el path del archivo a enviar y en curnombre el nombre
            	//en getfile se inicia una activity y esta se termina en activityresult, donde se llama al enviador
                new DBExportImport().exportDB(this);
            	return true;
            case  R.id.menu_option1 :
                new DBExportImport().importBD(this);
            	Toast.makeText(this, "Seleccionada importarDatos", Toast.LENGTH_LONG).show();
                return true;
            case  R.id.menu_option2 :
            	Toast.makeText(this, "Seleccionada limpiarFicha", Toast.LENGTH_LONG).show();
                return true;
            case  R.id.menu_option3 :
            	Toast.makeText(this, "Seleccionada EnviarRecibir", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
   
    
    /**  FIN CODIGO AGREGADO/MODIFICADO 19/7/13 **/
	
    @Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub		
	}

	//Metodo que administra la ejecucion de los fragments asociados a cada tab
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

        UbicacionFragment ubicacion = new UbicacionFragment();
		  if (tab.getText().equals(caption1)) {
		      executeFragment(ubicacion);
		      Log.e("Valor recuperado", String.valueOf(codViv));
		  } else if (tab.getText().equals(caption2)) {
              //codViv = ubicacion.getCodViv();
              codViv = new EstadoGlobal().getInstance().getCodViv();
			  ViviendaFragment vivienda = new ViviendaFragment(codViv);
			  executeFragment(vivienda);
		  } else if (tab.getText().equals(caption3)) {
			  executeFragment( new SocioeconomicoFragment());
		  } else if (tab.getText().equals(caption4)) {
			  PersonaFragment persona = new PersonaFragment(codViv);
			  executeFragment(persona);
		  } else if (tab.getText().equals(caption5)) {
              genero = new EstadoGlobal().getInstance().getCodGenero();
              Log.e("Valor recuperado genero", String.valueOf(genero));
              codPer = new EstadoGlobal().getInstance().getCodPersona();
              Log.e("Valor recuperado codPer", String.valueOf(codPer));
              if(genero == MASCULINO){ //genero == 2
                  HechosMascFragment hechosMasc = new HechosMascFragment(codPer);
                  executeFragment(hechosMasc);
              } else {//FEMENINO
                  HechosFragment hechos = new HechosFragment(codPer);
                  executeFragment(hechos);
              }
		  } else if (tab.getText().equals(caption6)) {
			  executeFragment(new VisitasFragment(codViv));
		  } else if (tab.getText().equals(caption7)) {
			  executeFragment( new DenunciaFragment());
		  } else if (tab.getText().equals(caption8)) {
			/*  if(elMapa == null){
				  elMapa = new MapaFragment();//new WeakReference<MapaFragment>(new MapaFragment());
				  Log.e("Seleccionado tab mapa","se creo el mapafragment");
			  }else{
				  Log.e("seleccionado tab mapa","no se creo el mapafragment");
			  }*/
			  elMapa = new MapaFragment();//new WeakReference<MapaFragment>(new MapaFragment());
			  executeFragment( elMapa);
		  }		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	public void executeFragment (Fragment fragment) {
		try {    
			mainLayout.removeAllViews();
			fragTransactMgr.addToBackStack(null);
			fragTransactMgr = getFragmentManager().beginTransaction();
			fragTransactMgr.add(mainLayout.getId(), fragment);
			fragTransactMgr.commit();   
		} catch (Exception e) {

		}
	}
	
	/*public void executeFragment (Fragment fragment,int noUsado) {
		try {    
			mainLayout.removeAllViews();
			fragTransactMgr.addToBackStack(null);
			fragTransactMgr = getFragmentManager().beginTransaction();
			fragTransactMgr.replace(mainLayout.getId(), fragment);
			fragTransactMgr.commit();   
		} catch (Exception e) {
			Log.e("FALLO EXECUTE FRAGMENT NUEVO","FALLO EXECUTE FRAGMENT NUEVO");
			e.printStackTrace();
		}
	}*/
	
	/** CODIGO PARA ENVIAR DATOS AGREGADO/MODIFICADO 19/7/13 **/
	/**
	 * Esta clase se encarga de enviar datos a un servidor y obtener una respuesta para verificar el envio.
	 * El envio se hace en el doInBackground, y esto asemeja a un thread ajeno al principal.
	 * se debe llamar al metodo execute(parametros) con parametros el basicNameValuePair que contiene el 
	 * nombre del archivo como name y el path del archivo que se va a enviar en el value
	 * */
	
	public class Enviador extends AsyncTask<BasicNameValuePair,Integer,Integer> {
		byte[] archivosByte;
		String archivoNom;
		String miresultado = "";
	       
		//@Override
	        protected Integer doInBackground(BasicNameValuePair... i) {
	        	archivosByte = this.obtenerByteArr(i[0].getValue());//saca el byte[] del archivo con el path dado en el basicnamevaluepair
	        	archivoNom = i[0].getName();
	        	
	            String archivoStr = Base64.encodeToString(archivosByte, Base64.DEFAULT);// codifica la imagen
	             ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();//crea una lista de "pares"
	            
	            nameValuePairs.add(new BasicNameValuePair("archivo",archivoStr));// agrega un "par" con la imagen codificada por Base64
	            nameValuePairs.add(new BasicNameValuePair("nom",archivoNom));
	        	
	            try {
		            	 HttpClient httpclient = new DefaultHttpClient(); // crea el cliente http
		                 HttpPost httppost = new HttpPost("http://10.0.2.2:8080/pruebaGen.php");//recordar que el 10.0.2.2 es la direccion virtual dentro del emulador para el localhost
		                 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		                 HttpResponse respuesta = httpclient.execute(httppost);
		                 
		                 HttpEntity entity = respuesta.getEntity();
		                 //Creamos el InputStream 
		                 InputStream is = entity.getContent();
		                 //Limpiamos el codigo obtenido atraves de la funcion
		                 //StreamToString explicada mes abajo
		                 String resultado= StreamToString(is);
		                 
		                 String[] resultados = resultado.split("\n");
		                 miresultado = resultado;
		                 		                 
		                 int resNum = Integer.parseInt(resultados[0]);
		                 publishProgress(resNum);// dice al usuario que hubo un resultado esperado
		                 
	                
	                 
	            } catch (Exception e) {
	                e.printStackTrace();
	                this.publishProgress(1);//dice que hubo error
	            }
	 
	            
	            return 1;
	        }
	        
	        /**Este metodo toma el path para traer un archivo y convertirlo en un arreglo de bytes, necesarios para codificar en Base64
	         * */
	        public byte[] obtenerByteArr(String path){
	        	File archivoFile = new File(path);
	            int  tamano = (int)archivoFile.length();
	    		byte [] byte_arr = new byte[tamano+2];//esto limita a que el archivo sea de menos de 2GB
	            try {
	    			RandomAccessFile archivoRAF = new RandomAccessFile(archivoFile, "r");
	    	        archivoRAF.readFully(byte_arr);// lee todos los bytes de archivoRAF y lo coloca en byte_arr	
	    	        archivoRAF.close(); // agregado por JVM para evitar memory leak
	    	        
	            } catch (Exception e1) {
	    			e1.printStackTrace();
	    		}
	            return byte_arr;
	        }
	        
	        
	        /**este metodo convierte la respuesta del servidor(is), en una hilera
	         * */
	    	public String StreamToString(InputStream is) {
	    	    //Creamos el Buffer
	    	    BufferedReader reader = 
	    	        new BufferedReader(new InputStreamReader(is));
	    	   StringBuilder sb = new StringBuilder();
	    	 String line = null;
	    	 try {
	    	 //Bucle para leer todas las leneas
	    	 //En este ejemplo al ser solo 1 la respuesta
	    	 //Pues no harea falta
	    	 while ((line = reader.readLine()) != null) {
	    	      sb.append(line + "\n");
	    	 }
	    	 } catch (IOException e) {
	    	     e.printStackTrace();
	    	 } finally {
	    	    try {
	    	    is.close();
	    	    } catch (IOException e) {
	    	    e.printStackTrace();
	    	    }
	    	 }
	    	 //retornamos el codigo lempio
	    	return sb.toString();
	    	}
	    	
	    	/**Este metodo se ejecuta con acceso a los elementos del thread principal, con el se pueden informar cosas que pasan en el background
	    	 *La forma de llamarlo es publishProgress(int) .
	    	 * */
//	        @Override
	        protected void onProgressUpdate(Integer... values) {
	            int opcion = values[0];
	            
	            if(opcion == 0){
        			Toast.makeText(contexto, "Archivo Enviado", Toast.LENGTH_LONG).show();
	            }else if(opcion == 1){
        			Toast.makeText(contexto, "Error: Archivo no enviado", Toast.LENGTH_LONG).show();
	            }
	            	
	            
	            
	        }
	 
	  //      @Override
	        protected void onPreExecute() {
	        }
	 
	    //    @Override
	        protected void onPostExecute(Boolean result) {
	            
	        }
	 
	      //  @Override
	        protected void onCancelled() {
	            Toast.makeText(contexto, "Tarea cancelada!",
	                Toast.LENGTH_SHORT).show();
	        }

	    

	}
	
	
	/** FIN CODIGO PARA ENVIAR DATOS**/
	
	
	
	
}