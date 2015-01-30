package ucr.tools;

import java.text.DecimalFormat;
import java.util.Calendar;

public class Validation {
	
	private Calendar myCalendar; 
	
	public Validation() {
		
	}	
	
	public boolean isEmpty(String text) {
		if (text == null || text.length() == 0) {
			return true;
		}
		for (int i = 0; i < text.length(); i++) {
			if (!Character.isWhitespace(text.codePointAt(i)))
				return false;
		}
		return true;
	}
	
	public String getDate() {
		myCalendar = Calendar.getInstance();
		String date = Calendar.DAY_OF_MONTH + "/" + myCalendar.get(Calendar.MONTH) + "/" +
				myCalendar.get(Calendar.YEAR);
		
		return date;
	}
	
	@SuppressWarnings("static-access")
	public String getDate(Calendar calendar) {
		
		return calendar.DAY_OF_MONTH + "/" + calendar.MONTH + "/" +
				calendar.YEAR;
	}
	
	public double getIMC(double weight, double size) {
		return (weight/(Math.pow(size, 2)));
	}
	
//	public String getIMC(double weight, double size) {
//		return (weight/(Math.pow(size, 2)));
//	}
	
	public String getIMC(double imc) {
		DecimalFormat format = new DecimalFormat("####.####");
		return format.format(String.valueOf(imc));
	}

}
