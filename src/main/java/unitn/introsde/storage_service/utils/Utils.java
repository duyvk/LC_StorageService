package unitn.introsde.storage_service.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	public static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	public static boolean isValidDate(String dateString){
		return dateString.matches("\\d{4}-\\d{2}-\\d{2}");
	}
	public static Date strToDate(String dateString){
		try {
			Date date = dateFormatter.parse(dateString);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
