package ws.slink.spm.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelTools {

	public static Optional<Workbook> getExcelWorkbook(String filename) {
		Workbook workbook = null;
		try {
			File inputFile = new File(filename);
			FileInputStream inputStream = new FileInputStream(inputFile);
			if (getExtension(filename).equalsIgnoreCase("XLSX"))
				workbook = new XSSFWorkbook(inputStream); // xlsx
			else if (getExtension(filename).equalsIgnoreCase("XLS"))
				workbook = new HSSFWorkbook(inputStream); // xls
		} catch (FileNotFoundException e) { // file not found error
			e.printStackTrace();
		} catch (IOException e) { // workbook creation error
			workbook = null;
			e.printStackTrace();
		}
		return Optional.ofNullable(workbook);
	}

	private static final String getExtension(final String filename) {
		  if (filename == null) return null;
		  final String afterLastSlash = filename.substring(filename.lastIndexOf('/') + 1);
		  final int afterLastBackslash = afterLastSlash.lastIndexOf('\\') + 1;
		  final int dotIndex = afterLastSlash.indexOf('.', afterLastBackslash);
		  return (dotIndex == -1) ? "" : afterLastSlash.substring(dotIndex + 1);
		}

}
