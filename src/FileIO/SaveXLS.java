package FileIO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import model.Spiel;
import model.TabelleSpielTage;

public class SaveXLS {
	String path;
	List<Spiel> daten;
	String[] columnNames;

	public SaveXLS(String path, TabelleSpielTage tabelle) {
		this.path = path;
		this.daten = tabelle.getSpielTageOutput();
		this.columnNames = tabelle.columNames;

	}

	public void saveXLS() {
		try (HSSFWorkbook wb = new HSSFWorkbook()) {
				CreationHelper createHelper = wb.getCreationHelper();
				
				// Create a Sheet
				Sheet sheet = wb.createSheet("Spielplan");
				
				// Create a Font for styling header cells
				Font headerFont = wb.createFont();
				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 14);
				headerFont.setColor(IndexedColors.RED.getIndex());

				// Create a CellStyle with the font
				CellStyle headerCellStyle = wb.createCellStyle();
				headerCellStyle.setFont(headerFont);

				// Create a Row
				Row headerRow = sheet.createRow(0);

				// Create cells
				for (int i = 0; i < columnNames.length; i++) {
					Cell cell = headerRow.createCell(i);
					cell.setCellValue(columnNames[i]);
					cell.setCellStyle(headerCellStyle);
				}

				// Create Cell Style for formatting Date
				CellStyle dateCellStyle = wb.createCellStyle();
				dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

				// Create Other rows and cells with employees data
				int rowNum = 1;
				for (Spiel spiel : daten) {
					Row row = sheet.createRow(rowNum++);
					row.createCell(0).setCellValue(spiel.getM1().getName());
					row.createCell(1).setCellValue(spiel.getM2().getName());
					row.createCell(2).setCellValue(spiel.getErg().getErg1() +":" + spiel.getErg().getErg2());
					row.createCell(3).setCellValue(spiel.toStringDate());
				}
				// Resize all columns to fit the content size
				for (int i = 0; i < columnNames.length; i++) {
					sheet.autoSizeColumn(i);
				}
				// Write the output to a file
				FileOutputStream fileOut = new FileOutputStream(path);
				wb.write(fileOut);
				fileOut.close();
				// Closing the workbook
				wb.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
