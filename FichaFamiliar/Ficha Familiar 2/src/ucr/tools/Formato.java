package ucr.tools;

public class Formato {

	public Formato() {
	}

	/**
	 * Método para convertir la fecha en formato Año-Mes-Dia para su inserción en la base de datos
	 * @param fecha Fecha a convertir
	 * @return Fecha convertida al forma Año-Mes-Dia
	 */
	public String formatoAMD(String fecha) {

		
		String fechaFormato = "";
		if (fecha != null && fecha.length() > 1) {			
			String split[] = fecha.split("-");
			String dia = split[0];
			String mes = split[1];
			String anio = split[2];
			if (dia.length() == 1)
				dia = "0" + dia;
			if (mes.length() == 1)
				mes = "0" + mes;

			fechaFormato = anio + "-" + mes + "-" + dia;		
		}
		return fechaFormato;
	}
	
	/**
	 * Método para convertir la fecha en formato Dia-Mes-Año para su presentación al usuario
	 * @param fecha Fecha a convertir
	 * @return Fecha convertida al forma Dia-Mes-Año
	 */
	public String formatoDMA(String fecha) {

		// System.out.println("fecha original " + fecha);
		String fechaFormato = "";
		if (fecha != null && fecha.length() > 1) {			
			String split[] = fecha.split("-");
			String anio = split[0];
			String mes = split[1];
			String dia = split[2];

			fechaFormato = dia + "-" + mes + "-" + anio;
			// System.out.println("fecha con formato " + fechaFormato);
		}
		return fechaFormato;
	}
	
}