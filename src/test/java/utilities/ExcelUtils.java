package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtils {

    private String path;   // Excel file path

    public ExcelUtils(String path) {
        this.path = path;
    }

    private FileInputStream fi;
    private FileOutputStream fo;
    private XSSFWorkbook wb;
    private XSSFSheet ws;
    private XSSFRow row;
    private XSSFCell cell;
    private CellStyle style;
    
    // Get row count
    public int getRowCount(String sheetName) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(sheetName);
        int rowcount = ws.getLastRowNum();
        wb.close();
        fi.close();
        return rowcount;
    }

    // Get cell count
    public int getCellCount(String sheetName, int rownum) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(sheetName);
        row = ws.getRow(rownum);
        int cellcount = row.getLastCellNum();
        wb.close();
        fi.close();
        return cellcount;
    }

    // Get cell data
    public String getCellData(String sheetName, int rownum, int column) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(sheetName);
        row = ws.getRow(rownum);
        cell = row.getCell(column);

        String data;
        try {
            DataFormatter formatter = new DataFormatter();
            data = formatter.formatCellValue(cell);
        } catch (Exception e) {
            data = "";
        }

        wb.close();
        fi.close();
        return data;
    }

    // Set cell data
    public void setCellData(String sheetName, int rownum, int column, String data) throws IOException {
        File xfile= new File(path);
        if (!xfile.exists()) {
            wb = new XSSFWorkbook();
            fo = new FileOutputStream(path);
            wb.write(fo);
            wb.close();
            fo.close();
        }

        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);

        if (wb.getSheetIndex(sheetName) == -1) 
            wb.createSheet(sheetName);
        ws = wb.getSheet(sheetName);

        if (ws.getRow(rownum) == null)
            ws.createRow(rownum);
        row = ws.getRow(rownum);

        cell = row.createCell(column);
        cell.setCellValue(data);

        fo = new FileOutputStream(path);
        wb.write(fo);

        wb.close();
        fi.close();
        fo.close();
    }

    // Fill cell green
    public void fillGreenColor(String sheetName, int rownum, int column) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(sheetName);
        row = ws.getRow(rownum);
        cell = row.getCell(column);

        style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);

        fo = new FileOutputStream(path);
        wb.write(fo);
        wb.close();
        fi.close();
        fo.close();
    }

    // Fill cell red
    public void fillRedColor(String sheetName, int rownum, int column) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(sheetName);
        row = ws.getRow(rownum);
        cell = row.getCell(column);

        style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);

        fo = new FileOutputStream(path);
        wb.write(fo);
        wb.close();
        fi.close();
        fo.close();
    }
}
