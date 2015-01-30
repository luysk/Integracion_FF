package ucr.fragments;


import ucr.database.SQLiteAdapter;
import ucr.ff.R;

import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentValues;

import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class DenunciaFragment extends Fragment {


	private View view;
	private EditText id;
	private EditText descripcion;
	private DatePicker fecha;
	private EditText denunciante;
	private EditText telDenunciante;
	private EditText asignada;
	private EditText longitud;
	private EditText latitud;

    private Button boton_insertar;
    private Button boton_actualizar;
    private Button boton_GPS;
    private Button satelite;
    private Button terreno;
    private Button hibrido;
    private Button normal;

    private SQLiteAdapter adapter;

    private GoogleMap el_mapa;

    private int tipo_de_mapa;

	public DenunciaFragment() {
		super();
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Log.e("Prueba", "DenunciaFragment");
}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

    /*/@Override
    /*public void onDestroyView() {
        super.onDestroyView();
        com.google.android.gms.maps.MapFragment f = (com.google.android.gms.maps.MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapa);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
    }*/
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.denuncias_tab, null);

		createWidgets();
        crearBotones();
        crearFecha();
		buttonFunctions();
		editTextFunctions();
        adapter = new SQLiteAdapter(view.getContext());
       try{
            inicializarMapa();
        }catch(Exception e ){
            System.out.println("El error en los mapas es " + e.getCause());
        }
        return view;
	}

    public void inicializarMapa(){
        if(el_mapa == null) {
            el_mapa = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapa)).getMap();
            el_mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            el_mapa.setMyLocationEnabled(true);
            el_mapa.getUiSettings().setMyLocationButtonEnabled(true);
            el_mapa.getUiSettings().setCompassEnabled(true);
            if(el_mapa == null){
                Toast.makeText(view.getContext()," No se ha podido crear el mapa",Toast.LENGTH_LONG).show();
            }
        }
    }



    private void crearFecha(){
        fecha = (DatePicker)view.findViewById(R.id.datePicker);
    }

	private void createEditTexts() {
		id = (EditText) view.findViewById(R.id.ID);
		descripcion = (EditText) view.findViewById(R.id.descripcion);
		denunciante = (EditText) view.findViewById(R.id.denunciante);
		telDenunciante = (EditText) view.findViewById(R.id.telDenunciante);
		asignada = (EditText) view.findViewById(R.id.asignada);
		longitud = (EditText) view.findViewById(R.id.longitud);
		latitud = (EditText) view.findViewById(R.id.latitud);
	}


    private void crearBotones(){
        boton_insertar = (Button)view.findViewById(R.id.insertarDenucia);
        boton_actualizar = (Button)view.findViewById(R.id.actualizarDenuncia);
        boton_GPS =  (Button) view.findViewById(R.id.obtenerGPS);
        satelite = (Button) view.findViewById(R.id.satelite);
        normal = (Button) view.findViewById(R.id.normal);
        terreno = (Button) view.findViewById(R.id.terreno);
        hibrido = (Button) view.findViewById(R.id.hibrido);
    }
	private void editTextFunctions() {
		id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    id.setBackgroundColor(getResources().getColor(R.color.lightblue));
                } else {
                    if (id.getText().toString().isEmpty()) {
                        id.setBackgroundColor(getResources().getColor(R.color.white));
                    } else {
                        id.setBackgroundColor(getResources().getColor(R.color.lightorange));
                    }
                }
            }
        });

		descripcion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
					descripcion.setBackgroundColor(getResources().getColor(R.color.lightblue));
				} else {
					if (descripcion.getText().toString().isEmpty()) {
						descripcion.setBackgroundColor(getResources().getColor(R.color.white));
					} else {
						descripcion.setBackgroundColor(getResources().getColor(R.color.lightorange));
					}
				}
			}
		});

		denunciante.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
					denunciante.setBackgroundColor(getResources().getColor(R.color.lightblue));
				} else {
					if (denunciante.getText().toString().isEmpty()) {
						denunciante.setBackgroundColor(getResources().getColor(R.color.white));
					} else {
						denunciante.setBackgroundColor(getResources().getColor(R.color.lightorange));
					}
				}
			}
		});

		telDenunciante.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
					telDenunciante.setBackgroundColor(getResources().getColor(R.color.lightblue));
				} else {
					if (telDenunciante.getText().toString().isEmpty()) {
						telDenunciante.setBackgroundColor(getResources().getColor(R.color.white));
					} else {
						telDenunciante.setBackgroundColor(getResources().getColor(R.color.lightorange));
					}
				}
			}
		});

		asignada.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
					asignada.setBackgroundColor(getResources().getColor(R.color.lightblue));
				} else {
					if (asignada.getText().toString().isEmpty()) {
						asignada.setBackgroundColor(getResources().getColor(R.color.white));  
					} else {
						asignada.setBackgroundColor(getResources().getColor(R.color.lightorange));  
					}	        	
				}
			}
		});	    


		longitud.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
					longitud.setBackgroundColor(getResources().getColor(R.color.lightblue));  
				} else {
					if (longitud.getText().toString().isEmpty()) {
						longitud.setBackgroundColor(getResources().getColor(R.color.white));  
					} else {
						longitud.setBackgroundColor(getResources().getColor(R.color.lightorange));  
					}	        	
				}
			}
		});
		
		latitud.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
					latitud.setBackgroundColor(getResources().getColor(R.color.lightblue));  
				} else {
					if (latitud.getText().toString().isEmpty()) {
						latitud.setBackgroundColor(getResources().getColor(R.color.white));  
					} else {
						latitud.setBackgroundColor(getResources().getColor(R.color.lightorange));  
					}	        	
				}
			}
		});
	}
	
	public void createWidgets() {
		createEditTexts();
	}


	private void buttonFunctions() {
		boton_insertar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    String id_denun = id.getText().toString();
                    if(!id_denun.isEmpty()) {
                        String sql = "select * from denuncia where coddenuncia = "+id_denun+";";
                        adapter.open();
                        Cursor resultado = adapter.select(sql);
                        if(resultado.getCount()==0) {
                            String desc = descripcion.getText().toString();
                            if (!desc.isEmpty()){
                                String denun = denunciante.getText().toString();
                                if(!denun.isEmpty()){
                                    String telefono = telDenunciante.getText().toString();
                                    if(!telefono.isEmpty()){
                                        String responsable = asignada.getText().toString();
                                        if(!responsable.isEmpty()) {
                                            String longitud_text = longitud.getText().toString();
                                            String latitud_text = latitud.getText().toString();
                                            if (!latitud_text.isEmpty() && !longitud_text.isEmpty()) {
                                                ContentValues valores = new ContentValues();
                                                valores.put("coddenuncia", id_denun);
                                                valores.put("descripcion", desc);
                                                valores.put("Nombredenunciante", denun);
                                                valores.put("teldenunciante", telefono);
                                                int dia = fecha.getDayOfMonth();
                                                int mes = fecha.getMonth();
                                                int anyo = fecha.getYear();
                                                valores.put("fechadenuncia", dia + "/" + mes + "/" + anyo);
                                                valores.put("longitud", longitud_text);
                                                valores.put("latitud", latitud_text);
                                                valores.put("responsableden",responsable);
                                                adapter.insert("denuncia", valores);
                                                Toast.makeText(view.getContext(),"Datos insertados correctamente",Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(view.getContext(), "Ingrese la latitud y la longitud", Toast.LENGTH_LONG).show();
                                            }
                                        }else{
                                            Toast.makeText(view.getContext(),"Ingrese a un responsable para la denuncia",Toast.LENGTH_LONG).show();
                                        }
                                    }else{
                                        Toast.makeText(view.getContext(),"Favor ingresar el telefono del denunciante",Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(view.getContext(),"Debe ingresar un codigo de denuncuiante",Toast.LENGTH_LONG).show();
                                }
                        }else{
                            Toast.makeText(view.getContext(),"Debe ingresar una descripción",Toast.LENGTH_LONG).show();
                        }
                        }else{
                            Toast.makeText(view.getContext(),"El codigo de denuncia ingresado ya está en uso",Toast.LENGTH_LONG).show();
                        }
                        adapter.close();
                    }else{
                        Toast.makeText(view.getContext(),"Debe ingresar un codigo de denuncia válido",Toast.LENGTH_LONG).show();
                    }
            }
        });

        boton_actualizar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        satelite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo_de_mapa == GoogleMap.MAP_TYPE_SATELLITE){
                    Toast.makeText(view.getContext(),"El tipo de mapa ya está establecido como Satelite",Toast.LENGTH_LONG).show();
                }else{
                    tipo_de_mapa = GoogleMap.MAP_TYPE_SATELLITE;
                    el_mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
            }
        });

        hibrido.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo_de_mapa == GoogleMap.MAP_TYPE_HYBRID){
                    Toast.makeText(view.getContext(),"El tipo de mapa ya está establecido como Hibrido",Toast.LENGTH_LONG).show();
                }else{
                    tipo_de_mapa = GoogleMap.MAP_TYPE_HYBRID;
                    el_mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
            }
        });

        normal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo_de_mapa == GoogleMap.MAP_TYPE_NORMAL){
                    Toast.makeText(view.getContext(),"El tipo de mapa ya está establecido como Normal",Toast.LENGTH_LONG).show();
                }else{
                    tipo_de_mapa = GoogleMap.MAP_TYPE_NORMAL;
                    el_mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });

        terreno.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo_de_mapa == GoogleMap.MAP_TYPE_TERRAIN){
                    Toast.makeText(view.getContext(),"El tipo de mapa ya está establecido como Terreno",Toast.LENGTH_LONG).show();
                }else{
                    tipo_de_mapa = GoogleMap.MAP_TYPE_TERRAIN;
                    el_mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }
            }
        });

        boton_GPS.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Location ubicacion = el_mapa.getMyLocation();
                    latitud.setText("" + ubicacion.getLatitude());
                    longitud.setText("" + ubicacion.getLongitude());
                    CameraPosition posicion_camara = new CameraPosition.Builder().target(new LatLng(ubicacion.getLatitude(), ubicacion.getLongitude())).zoom(12).build();
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(ubicacion.getLatitude(), ubicacion.getLongitude())).title("Ubicación actual");
                    el_mapa.addMarker(marker);
                    el_mapa.animateCamera(CameraUpdateFactory.newCameraPosition(posicion_camara));
                }catch (Exception e){
                     Toast.makeText(view.getContext(),"Se ha producido un error, favor revisar su conexion a Internet",Toast.LENGTH_LONG).show();
                }
            }
        });
	}



	public void store() {

	}
	
	public void load() {
		
	}

}
