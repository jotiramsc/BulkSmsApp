package com.sms.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.monitorjbl.xlsx.StreamingReader;

public abstract class ExcelUploader<T> {

	List<String> headers = new ArrayList<String>();
	List<String> fileHeaders = new ArrayList<String>();
	String path = null;
	List<HashMap<String, String>> dataList = null;
	String fileName = null;

	public ExcelUploader(String uploadFileName, List<String> headersList) {

		this.headers = headersList;
		this.fileName = uploadFileName;

	}

	abstract boolean validateRow(Row row, T model, List<String> errors, int rowNum);

	abstract boolean validateData(List<Row> rows, List<T> contacts, List<String> errors);

	public abstract List<T> getFileContents(MultipartFile file) throws IOException, SmsException;

	protected List<Row> getFileRows(MultipartFile file) throws IOException {
		InputStream is = file.getInputStream();
		StreamingReader reader = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).sheetIndex(0).read(is);

		if (reader == null)
			throw new IOException("No file found");
		List<Row> rows = new ArrayList<Row>();

		for (Row row : reader) {
			rows.add(row);
		}

		if (rows.isEmpty())
			throw new IOException("No data in files");
		return rows;
	}

	private void readFileHeaders(Row row) {

		for (Iterator<Cell> iterator = row.cellIterator(); iterator.hasNext();) {
			Cell cell = (Cell) iterator.next();
			fileHeaders.add(cell.getStringCellValue());

		}

	}

	protected String writeErrorsInFile(List<Row> rows, List<String> errors) throws FileNotFoundException {

		File file = new File(this.fileName+"_"+System.currentTimeMillis()+".xls");
		FileOutputStream out = new FileOutputStream(file);

		Workbook wb = new XSSFWorkbook();

		Sheet s = wb.createSheet();

		Row r = null;

		Cell c = null;

		wb.setSheetName(0, "Sheet");

		CellStyle style1 = wb.createCellStyle();
		style1.setFillForegroundColor(IndexedColors.RED.getIndex());
		Font font1 = wb.createFont();
		font1.setColor(IndexedColors.RED.getIndex());
		style1.setFont(font1);

		int rownum1;
		for (rownum1 = (short) 0; rownum1 < rows.size(); rownum1++) {
			// create a row

			r = s.createRow(rownum1);
			Row r1 = rows.get(rownum1);
			if (rownum1 == 0) {
				c = r.createCell(3);
				c.setCellValue(errors.get(rownum1));
				c.setCellStyle(style1);
			}

			short cellnum = (short) 0;

			for (Iterator<Cell> iterator = r1.cellIterator(); iterator.hasNext();) {
				Cell cellold = (Cell) iterator.next();
				c = r.createCell(cellnum);
				c.setCellValue(cellold.getStringCellValue());

				cellnum++;
			}

			if (cellnum < headers.size()) {
				for (int i = cellnum; i < 3; i++) {

					r.createCell(cellnum);
					cellnum++;
				}
			}
			c = r.createCell(cellnum);
			c.setCellValue(errors.get(rownum1));

		}

		try {
			wb.write(out);
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			wb.close();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return file.getAbsolutePath();

	}

	public boolean validateHeaders(Row row) throws SmsException {

		readFileHeaders(row);
		if (null == fileHeaders) {
			throw new SmsException("File does not having header's");
		}

		if (headers.size() != fileHeaders.size()) {
			throw new SmsException("File headers are not matching");
		}

		for (Iterator<String> iterator = headers.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			if (!fileHeaders.stream().anyMatch(t -> t.equalsIgnoreCase(string))) {
				throw new SmsException("No matching header found for '" + string + "' in file");
			}

		}
		for (int i = 0; i < headers.size(); i++) {

			if (!headers.get(i).equalsIgnoreCase(fileHeaders.get(i))) {
				throw new SmsException("Sequence of '" + headers.get(i) + "' does not match in file");

			}
		}

		return true;

	}

}
