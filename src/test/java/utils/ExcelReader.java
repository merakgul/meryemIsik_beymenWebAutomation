package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

public class ExcelReader {
    private Workbook workbook;

    public ExcelReader(String filePath) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IOException("File not found: " + filePath);
        }
        workbook = new XSSFWorkbook(inputStream);
    }

    public String getCellData(int rowNum, int colNum) {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            return "";
        }
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            return "";
        }
        return cell.getStringCellValue();
    }
}