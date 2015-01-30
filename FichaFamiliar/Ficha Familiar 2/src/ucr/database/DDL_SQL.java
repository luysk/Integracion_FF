package ucr.database;

import java.util.ArrayList;

//Clase contenedora de las definiciones
//en lenguaje SQL de la base de datos
public class DDL_SQL {
	private String tabla;
	private String trigger;
	private ArrayList<String> lista_tablas;
	private String sentencia; // AGREGADO EL 2/10/13
		
	//Constructor
	public DDL_SQL() {	
		inicializar();
	}
	
	//Ubica en memoria las definiciones de las tablas que utiliza
	//la Ficha Familiar en memoria, para el caso en que sea necesario
	//crear toda la base de datos
	public void inicializar() {
		sentencia = "";// AGREGADO EL 2/10/13
		tabla = "";
		trigger = "";
		lista_tablas = new ArrayList<String>();
		lista_tablas = inicializarLista();
	}
	
	//Crea un array con la lista de las tablas que contiene la base de datos
	public ArrayList<String> inicializarLista() {
		ArrayList<String> lista = new ArrayList<String>();
		lista.add("Configuracion");
		lista.add("Provincia");
		lista.add("Canton");
		lista.add("Distrito");
		lista.add("Barrio");
		lista.add("RegionSalud");
		lista.add("AreaSalud");
		lista.add("EBAIS");		
		lista.add("Ubicacion");
		lista.add("Ocupacion");
		lista.add("CondSocioeconomica");
		lista.add("CondSalud");
		lista.add("CondSaludM");
		lista.add("Denuncia");
		lista.add("Vacunas");
		lista.add("ControlVacunas");
		lista.add("Escolaridad");
		lista.add("TipoAsegurado");		
		//lista.add("EstadoCivil");
		lista.add("Nacion");
		lista.add("Vivienda");
		lista.add("Persona");
		
		//AGREGADO 2/10/13
		lista.add("videofoto");
		lista.add("familia");
		lista.add("padron");
		lista.add("visita");
		lista.add("agenda");
		//FIN AGREGADO 2/10/13
		
		return lista;
	}
	
	//Permite acceder a la lista de tablas
	public ArrayList<String> getListaTablas() {
		return lista_tablas;
	}
	
	public String getSQLConfiguracion() {
		tabla = "CREATE TABLE Configuracion (" + 
				" Version DEC(1,1) PRIMARY KEY," +
				" CodProvincia INT NOT NULL REFERENCES Provincia(CodProvincia)," +
				" CodCanton INT NOT NULL REFERENCES Canton(CodCanton)," +
				" CodRegion INT NOT NULL REFERENCES RegionSalud(CodRegion)," +
				" CodAS INT NOT NULL REFERENCES AreaSalud(CodAS)," +
				" FechaAct DATE NOT NULL," +
				" Email VARCHAR(40) NOT NULL," +
				" UltCodViv INT NOT NULL)";
						
		return tabla;
	}
	
	/**
	 * AGREGADO 2/10/13
	 * los siguientes 16 son metodos nuevos
	 * */
	public String getSQLVideofoto() {
		 tabla = " create table if not exists videofoto   "+
				 "(consec int , "+
                 " tipo int ,"+
                 " nombre varchar(15) ,"+
                 " fecha date ,"+
                 " archivoasoc varchar (60) ,"+
                 " keyword1 varchar(10) ,"+
                 " keyword2 varchar(10),"+
                 " consecdenuncia int," +
                 " primary key(consec)," +
                 " foreign key (consecdenuncia) references denuncia(consec) );";
		return tabla;
	}

    /**
     * OJO: HAY QUE DEFINIR LA FAMILIA ANT. POR DEFECTO, ES DECIR LA QUE SE ASIGNA SI NO HAY ANTERIOR
     * */

 	public String getSQLFamilia() {
		 tabla = " create table if not exists familia"+
				 "(codfamilia varchar(40), "+
				 " ap1 varchar(20) ,"+
				 " ap2 varchar(20) ,"+
				 " codfamiliaant varchar(40),"+ 
				 " codviviendaact int ,"+
				 " codviviendaant int ,"+
				 " fechavisita date," +
                 " primary key(codfamilia)," +
                 " foreign key(codfamiliaant) references familia(codfamilia)," +
                 " foreign key (codviviendaact) references vivienda(codvivienda)," +
                 " foreign key (codviviendaant) references vivienda(codvivienda));";
		return tabla;
	}

	public String getSQLVisita() {
	     tabla =" create table if not exists visita"+
	    		" (fecha date ,"+
	    		" codvivienda int  ,"+
	    		" visitaefectiva boolean ,"+
				" motivo varchar(120) ,"+
				" accionrealizada varchar(120) ,"+
				" codfam varchar(40), "+
				" m2 dec(5,2) ,"+
				" tenencia int ,"+
				" energia int ,"+
				" numaposentos int ,"+
				" numdormitorios int ,"+
				" tipococina int ,"+
				" condcocina int , "+
				" tipoenergiacocina int , "+
		        " tipobanio int ,"+
				" condbanio int ,"+
				" tipopiso int ,"+
				" condpiso int ,"+
				" matpared int ,"+
				" condpared int ,"+
				" mattecho int ,"+
				" condtecho int ,"+
				" condventilacion int ,"+
				" condiluminacion int ,"+
				" destmanejoexcretas int ,"+
				" destbasura int ,"+
				" animalesenriesgo int ,"+
				" riesgosalud int ,"+
				" tipocondicionsocioec int , "+
				" poseerefri boolean , "+
				" poseeradio boolean , "+
				" poseetelhab boolean , "+
				" poseetv boolean , "+
				" poseelavadora boolean , "+
				" poseecomputadora boolean , "+
				" poseeinternet boolean ,"+
				" observ varchar(70)," +
                " primary key(codvivienda,fecha)," +
                "foreign key (codvivienda) references vivienda(codvivienda)," +
                "foreign key (codfam) references familia(codfamilia) );	";
		return tabla;
	}
	
	public String getSQLAgenda() {
		 tabla = " create table if not exists agenda   "+
				 " (codvivienda int  , " +
				 " fechaproxvisita date ," +
				 " codfam varchar(40) ," +
				 " longitud dec(3,3) check (longitud > -1) not null  default 0, " +
				 " latitud dec(3,3) check (latitud > -1) not null  default 0 , " +
				 " motivo varchar (120)," +
                 " primary key(codvivienda,fechaproxvisita)," +
                 " foreign key (codvivienda) references vivienda(codvivienda)," +
                 " foreign key (codfam) references familia(codfamilia) );";
		return tabla;
	}
	
	public String modifySQLAgendaPK() {
		 sentencia = " alter table agenda" +
		 			 " add constraint PK_agenda_codvivienda" +
		 			 " primary key(codvivienda,fechaproxvisita); "; 
		return sentencia;
	}

	public String getSQLPadron() {
		 tabla = " create table if not exists padron  "+
				 "(cedula integer , "+
				 " ap1 varchar(10) ,"+
				 " ap2 varchar(10) ,"+
				 " nom varchar(10) ,"+
				 " provvot int ,"+
				 " cantvot int ,"+
				 " distvot int ,"+
				 " sexo int ,"+
				 " fechadefuncion date ,"+
				 " fechanac date," +
                 " primary key(cedula) );";
		return tabla;
	}
	
	
	/**
	 * FIN AGREGADO 2/10/13
	 * */
	//Retorna el lenguaje de definicion SQL para la tabla Ubicacion
	public String getSQLUbicacion() {
		tabla = "CREATE TABLE IF NOT EXISTS Ubicacion" +
                " (CodViv INTEGER PRIMARY KEY AUTOINCREMENT," +
                " FechaVis VARCHAR(10) NOT NULL," +
                " CodRS INT NOT NULL DEFAULT 0," +
                " CodAS INT NOT NULL DEFAULT 0," +
                " CodEBAIS INT NOT NULL DEFAULT 0," +
                " CodProvincia INT NOT NULL DEFAULT 0," +
                " CodCanton INT NOT NULL DEFAULT 0," +
                " CodDistrito INT NOT NULL DEFAULT 0," +
                " CodBarrio INT NOT NULL DEFAULT 0," +
                " Latitud DEC(3,3) CHECK (Latitud > -1) NOT NULL DEFAULT 0," +
                " Longitud DEC(3,3) CHECK (Longitud > -1) NOT NULL DEFAULT 0," +
                " VisEfec VARCHAR(1) NOT NULL DEFAULT \"N\" )";
		return tabla;
	}

	public String getSQLVivienda() {
		 tabla = "CREATE TABLE Vivienda\n" +
                 "(CodViv INTEGER PRIMARY KEY,\n" +
                 "M2 DEC(4, 2) CHECK (M2 > -1) DEFAULT 0,\n" +
                 "CodTenencia VARCHAR,\n" +
                 "CodEnergia VARCHAR,\n" +
                 "NApos SMALLINT CHECK (NApos > -1) DEFAULT 0,\n" +
                 "NDorm SMALLINT CHECK (NDorm > -1) DEFAULT 0,\n" +
                 "TipoCocina VARCHAR,\n" +
                 "CondCocina VARCHAR,\n" +
                 "EnergCocina VARCHAR,\n" +
                 "TipoBanio VARCHAR,\n" +
                 "CondBanio VARCHAR,\n" +
                 "TipoPiso VARCHAR,\n" +
                 "CondPiso VARCHAR,\n" +
                 "TipoPared VARCHAR,\n" +
                 "CondPared VARCHAR,\n" +
                 "TipoTecho VARCHAR,\n" +
                 "CondTecho VARCHAR,\n" +
                 "CondVent VARCHAR,\n" +
                 "CondIlum VARCHAR,\n" +
                 "FtesAgua VARCHAR,\n" +
                 "CondFtesAgua VARCHAR,\n" +
                 "DispExcretas VARCHAR,\n" +
                 "DispBasura VARCHAR,\n" +
                 "AnimCondIns VARCHAR,\n" +
                 "Riesgo VARCHAR,\n" +
                 "Observaciones VARCHAR(20));";
		return tabla;
	}
/*
	public String getSQLPersona() {
		 tabla = " create table if not exists persona "+
				 "(consec int , "+
				 " cedula varchar(40) ,"+
				 " codfamant varchar(40) ,"+
				 " sexo char,"+
				 " relacionjefefam varchar(10), 	"+
				 " grupoetnico int , "+
				 " paisnac int , "+
				 " nivelescolaridad int , "+
				 " peso dec(5,2) default 0 ," +
				 " talla dec(5,2) default 0 ," 	+
	 			 " estadonut int , "+
	 			 " estadocivil int ," +
	 			 " codocupacion int ," +
	 			 " codcondlaboral int," +
	 			 " tipoasegurado int , "+
	 			 " fechanac date," +
                 " primary key(consec) );	";
		return tabla;
	}
	*/

    public String getSQLPersona() {
        tabla = " create table if not exists persona "+
                "(consec INTEGER PRIMARY KEY AUTOINCREMENT," +
                " cedula varchar(20) unique, "+  //0
                " nombre varchar(30) not null, "+
                " apellido1 varchar(30) not null , "+           //2
                " apellido2 varchar(30)  , "+
                " codfamact varchar(40) not null ,"+            //4
                " sexo char,"+
                " relacionjefefam varchar(10), 	"+              //6
                " grupoetnico int , "+
                " paisnac varchar(30) , "+                      //8
                " nivelescolaridad int , "+
                " peso dec(5,2) default 0 ," +                  //10
                " talla dec(5,2) default 0 ," 	+
                " estadonut int , "+                            //12
                " estadocivil int ," +
                " codocupacion int ," +                         //14
                " codcondlaboral varchar(5)," +
                " tipoasegurado int , "+                        //16
                " codvivienda int not null, "+
                " fechanac date , "+                            //18
                " fechadef date ); " ;
        //" foreign key (codfamant) references familia(codfamilia) );	";
        return tabla;
    }
	
	public String getSQLCondSocioeconomica() {
		tabla = "CREATE TABLE IF NOT EXISTS CondSoc" +
				" (CodViv INTEGER PRIMARY KEY," + 
				" CodCondSocioeco VARCHAR(2)," +
				" Refrigeracion VARCHAR(1)," +
				" Radio VARCHAR(1)," +
				" Telefono VARCHAR(1)," +
				" TV VARCHAR(1)," +
				" Lavadora VARCHAR(1)," +
				" Computadora VARCHAR(1)," +
				" Internet VARCHAR(1))"; 
		return tabla;
	}

	 public String getSQLCondSalud() {
		 tabla = " create table if not exists condsalud "+
				 " (consec int, "+
				 " visitaespecialista boolean ,"+
				 " condsalud int , "+
				 " condviolenciadom int , "+
				// " fechainicioplanificacion date ,  "+
				// " estadopapanicolau int ,"+
				 //" fechaultregla date ,"+
				 " observaciones varchar(50) ,"+
				 //" fecha date," +
                 " primary key(consec)," +
                 " foreign key (consec) references persona(consec) );";
		return tabla;
	}
	
	public String getSQLCondSaludM() {
		tabla = "CREATE TABLE IF NOT EXISTS CondSaludM" +
				" (Consec INTEGER PRIMARY KEY REFERENCES ConSalud(Consec)" +
				" ON DELETE CASCADE ON UPDATE CASCADE," +
				" FechaPlan DATE," +
				" TipoPlan VARCHAR(3)," +
				" FechaPap DATE," +
                " FechaUltRegla DATE," +
				" FechaEmb DATE," +
                " estadoPap int)";
		return tabla;		
	}

	public String getSQLDenuncia() {
		 tabla = "CREATE TABLE Denuncia(\n" +
                 "CodDenuncia INTEGER PRIMARY KEY,\n" +
                 "Descripcion VARCHAR(75) NOT NULL,\n" +
                 "FechaDenuncia DATE NOT NULL,\n" +
                 "NombreDenunciante VARCHAR(20) NOT NULL,\n" +
                 "TelDenunciante VARCHAR(8) NOT NULL,\n" +
                 "ResponsableDen INTEGER NOT NULL,\n" +
                 "Longitud DEC(3,3) CHECK (Longitud > -1) NOT NULL DEFAULT 0,\n" +
                 "Latitud DEC(3,3) CHECK (Latitud > -1) NOT NULL DEFAULT 0);";
		return tabla;
	}

	public String getSQLMapa() {
		return tabla;
	}
	
	public String getSQLProvincia() {
		tabla = "CREATE TABLE IF NOT EXISTS Provincia " +
				" (CodProvincia INT PRIMARY KEY," + 
				" Nombre_Provincia VARCHAR(30) NOT NULL );";
		return tabla;
	}
	
	public String getSQLCanton() {
		tabla = "CREATE TABLE IF NOT EXISTS Canton" +     			   
				" (CodProvincia INT NOT NULL REFERENCES Provincia(CodProvincia) ON DELETE RESTRICT," +
				" CodCanton INT NOT NULL," +
				" Nombre_Canton VARCHAR(30) NOT NULL," +
				" PRIMARY KEY(CodProvincia, CodCanton))";
		return tabla;
	}	
	
	public String getSQLDistrito() {
		tabla = "CREATE TABLE IF NOT EXISTS Distrito" +
				" (CodProvincia INTEGER NOT NULL," +
				" CodCanton INTEGER NOT NULL," +
				" CodDistrito INTEGER NOT NULL," +
				" Nombre_Distrito VARCHAR(30) NOT NULL," +
				" PRIMARY KEY(CodProvincia, CodCanton, CodDistrito)," +
				" FOREIGN KEY(CodProvincia, CodCanton) REFERENCES Canton(CodProvincia, CodCanton))";
		return tabla;
	}		
	
	public String getSQLBarrio() {
		tabla = "CREATE TABLE IF NOT EXISTS Barrio" + 
				" (CodProvincia INT NOT NULL REFERENCES Provincia(CodProvincia) ON DELETE RESTRICT," +
				" CodCanton INTEGER NOT NULL REFERENCES Canton(CodCanton) ON DELETE RESTRICT," +
				" CodDistrito INT NOT NULL REFERENCES Distrito(CodDistrito) ON DELETE RESTRICT," +
				" CodBarrio INT NOT NULL," +
				" Nombre_Barrio VARCHAR(50)," +
				" PRIMARY KEY(CodProvincia, CodCanton, CodDistrito, CodBarrio)," +
				" FOREIGN KEY(CodProvincia, CodCanton, CodDistrito)" +
				" REFERENCES Distrito(CodProvincia, CodCanton, CodDistrito))";
		return tabla;
	}	
	
	public String getSQLRegionSalud() {
		tabla = "CREATE TABLE IF NOT EXISTS RegionSalud "  +
				" (CodRegion INT PRIMARY KEY," +
				" NombreRegion VARCHAR(16) NOT NULL)";			
		return tabla;
	}

	public String getSQLAreaSalud() {
        tabla = " create table areasalud"  +
                "(id int, " +
                "nombre varchar(10),"+
                "tel int,"	+
                "nomjefeas varchar(20), " +
                "primary key(id));";
        return tabla;
	}	


	public String getSQLEBAIS() {
		 tabla = " create table ebais"+   
				 "(codebais int , "+
				 "nombre varchar(40)  ,"+
				 "id_areasalud int, "+
				 "primary key(codebais)," +
                 "foreign key (id_areasalud) references areasalud(id));";
		return tabla;
	}

    //id es el refuerzo de la vacuna codvac
	public String getSQLVacunas() {
		 tabla = " create table if not exists vacuna "+
                 "(IdVacuna int , "+
                 //"ced int, "+
                 //"codfam varchar(40) , "+
                 "nombre_vacuna varchar(40) , "+
                 //"fechavis date , "+
                 //"codvac int," +
                 //"primary key (ced,codvac,id)," +
                 "primary key (idVacuna) ); " ;
        //"foreign key (codfam) references familia(codfamilia)," +
        //"foreign key (ced) references persona(ced) ); ";
		return tabla;
	}



    /*
    *         tabla = "CREATE TABLE IF NOT EXISTS ControlVacunas" +
                " (Cedula INTEGER NOT NULL REFERENCES Persona(Cedula)" +
                " ON DELETE CASCADE ON UPDATE CASCADE," +
                " IdVacuna INT NOT NULL REFERENCES Vacuna(IdVacuna)," +
                " FechaVac DATE NOT NULL," +
                " PRIMARY KEY(Cedula, IdVacuna));";
    *
    *
    * */
	public String getSQLControlVacunas() {
		tabla = "CREATE TABLE IF NOT EXISTS ControlVacunas" +
				" (Consec INTEGER NOT NULL REFERENCES Persona(Consec)" +
				" ON DELETE CASCADE ON UPDATE CASCADE," +
				" IdVacuna INT NOT NULL REFERENCES Vacuna(IdVacuna)," +
				" FechaVac DATE NOT NULL," +
				" PRIMARY KEY(Consec, IdVacuna))";	
		return tabla;
	}	
	
	public String getSQLNacion() {
		tabla = "CREATE TABLE IF NOT EXISTS Nacion" + 
				" (CodNacion VARCHAR(3) PRIMARY KEY," + 
				" Nombre_Nacion VARCHAR(30) NOT NULL)";
		return tabla;
	}	
	
	public String getSQLEscolaridad() {
		tabla = " CREATE TABLE IF NOT EXISTS Escolaridad" + 
				" (CodEscolaridad INT PRIMARY KEY," + 
				" Nivel_Esc VARCHAR(30) NOT NULL)";
		return tabla;
	}
	
	public String getTipoAsegurado() {
		tabla = " CREATE TABLE TipoAsegurado(" +
				" CodAsegurado INT PRIMARY KEY," +
				" Descripcion VARCHAR(50) NOT NULL)";
		return tabla;
	}
	
	public String getSQLOcupacion() {
		tabla = "CREATE TABLE IF NOT EXISTS Ocupacion" + 
				" (CodOcup VARCHAR(3) PRIMARY KEY, " +
				" Desc_Ocup VARCHAR(50) NOT NULL)";
		return tabla;
	}
	
	public String trigger_Insertar_Ubicacion() {
		trigger = "";
		return trigger;
	}
	
	public String trigger_Insertar_Persona() {
		trigger = "";
		return trigger;
	}	
	
	public String trigger_Insertar_Vivienda() {
		trigger = "";
		return trigger;
	}
	
	public String trigger_Insertar_CondSalud() {
		trigger = "";
		return trigger;
	}
	
	public String trigger_Insertar_CondSocioeconomica() {
		trigger = "";
		return trigger;
	}
	
	public String trigger_Insertar_EsqVacunacion() {
		trigger = "";
		return trigger;
	}
	
	public String trigger_Borrar_Persona() {
		trigger = "CREATE TRIGGER IF NOT EXISTS trigger_Borrar_Persona" +
				  " BEFORE DELETE ON Vivienda " +
				  " FOR EACH ROW BEGIN " +
				  " DELETE from persona WHERE codVivienda = OLD.codViv; " +
				  " END";
		return trigger;
	}	
}