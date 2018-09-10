package ws.slink.spm.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class DataTools {
	
	static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(DataTools.class);
	
	
	public static Date parseExcelDate(String dateStr) {
		if (dateStr == null || dateStr.isEmpty()) return null;
		try {
			double d = Double.parseDouble(dateStr);
			return DateUtil.getJavaDate(d);
		} catch (Exception ex) {
			logger.error(ex.getMessage() + ": '" + dateStr + "'");
		}
		return null;
	}
	
	public static Date parseDate(String dateStr, String dateFormat) {
		logger.debug("dateStr: " + dateStr + ", dateFormat: " + dateFormat);
		SimpleDateFormat parserSDF = new SimpleDateFormat(dateFormat /*"yyyy-MM-dd HH:mm:ss"*/);
		try {
			Date date = parserSDF.parse(dateStr);
			logger.debug("date is " + date);
			return date;
		} catch (ParseException e) {
			logger.debug("date is null");
			return null;
		}
	}

	public static int parseIntValue(String field) {
		if (field == null || field.isEmpty())
			return 0;
		else {
			double value = Double.parseDouble(field.replaceAll(",", "."));
			return (int)value;
		}
	}

	public static String dateToStr(Date date) {
		if (date == null) return "";
		SimpleDateFormat format=new SimpleDateFormat("yyyy.MM.dd HH:mm");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return format.format(cal.getTime());
	}

	public static String dateToStr(Date date, String fmt) {
		if (date == null) return "";
		SimpleDateFormat format=new SimpleDateFormat(fmt);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return format.format(cal.getTime());
	}

	public static Integer dateToNow(Date date) {
		if (date != null) {
			long diff = date.getTime() - (new Date()).getTime();
	 		return (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	 	} else {
	 		return null;
	 	}
	}
	
	public static String nullableToString(Integer value) {
		if (value == null)
			return "";
		else 
			return value.toString();
	}

	public static String getLine(Iterator<Cell> cellIterator, String separator) {
		String res = "";
		while (cellIterator.hasNext()) {
			String str = "";
			
			// get cell value
            Cell cell = cellIterator.next();
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                	str += cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                	str += cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                	str += cell.getNumericCellValue();
                    break;
            }
            
            // remove .0 from numbers
            if (str.endsWith(".0"))
            	str = str.substring(0, str.length()-2);
//             remove tabs from string
            str = str.replaceAll("\t", " ");
            res += str;
            
            // if no last cell, add separator
        	if (cellIterator.hasNext())
        		res += separator;
		}
		return res;
	}

	// parsing risks
	public static String getLine(Iterator<Cell> cellIterator, int nCol, String separator) {
		String res = "";
		int idx = 0;
		if (cellIterator.hasNext())
			while (idx++ <= nCol) {
				String str = "";
				
				// get cell value
				try {
					Cell cell = cellIterator.next();
		            switch (cell.getCellType()) {
		                case Cell.CELL_TYPE_STRING:
		                	str += cell.getStringCellValue();
		                    break;
		                case Cell.CELL_TYPE_BOOLEAN:
		                	str += cell.getBooleanCellValue();
		                    break;
		                case Cell.CELL_TYPE_NUMERIC:
		                	str += cell.getNumericCellValue();
		                    break;
		            }
				} catch (NoSuchElementException ex) {
					str += " "; 
				}
	            
				if (idx == 1 && (str == null || str.isEmpty())) break;
				
	            // remove .0 from numbers
	//            if (str.endsWith(".0"))
	//            	str = str.substring(0, str.length()-2);
	//             remove tabs from string
	//            str = str.replaceAll("\t", " ");
	            res += str;
	            
	            // if no last cell, add separator
	        	if (idx < nCol)
	        		res += separator;
	        	
//	        	System.out.println(idx);
			}
//		System.out.println(res);
		return res;
	}

}
