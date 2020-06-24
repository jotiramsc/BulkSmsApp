package com.sms.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.multipart.MultipartFile;

import com.sms.model.ContactModel;

public class ContactUploader extends ExcelUploader<ContactModel> {
	private static List<String> headersList =Arrays.asList(new String[]{"name","countrycode","contactno"});

	
	public ContactUploader() {
		super(CommonLiterals.CONTACT_UPLOAD_FILE_NAME, headersList);

	}

	public List<ContactModel> getFileContents(MultipartFile file) throws IOException, SmsException {

		List<Row> rows = getFileRows(file);

		if (!validateHeaders(rows.get(0)))
			throw new IOException("Invalid headers in file");

		List<ContactModel> contacts = new ArrayList<ContactModel>();
		List<String> errors = new ArrayList<String>(rows.size());
		if (!validateData(rows, contacts, errors)) {
			String filePath = writeErrorsInFile(rows, errors);
			throw new IOException("Invalid data:" + filePath);
		}

		return contacts;
	}

	@Override
	public boolean validateData(List<Row> rows, List<ContactModel> contacts, List<String> errors) {

		boolean flag = true;

		long count = 0;
		for (Row row : rows) {

			ContactModel model = new ContactModel();
			errors.add("");
			if (count == 0) {
				errors.set((int) count, "Error");

			} else if (!validateRow(row, model, errors, (int) count)) {
				flag = false;
				
			}
			
			if (count != 0)
				contacts.add(model);
			
			count++;

		}
		return flag;
	}

	public boolean validateRow(Row row, ContactModel model, List<String> errors, int rowNum) {
		StringBuilder error = new StringBuilder("");

		boolean flag = true;

		Cell name = row.getCell(0);
		Cell contryCode = row.getCell(1);
		Cell contactNo = row.getCell(2);
		if (name == null || !CommonLiterals.isValidString(name.getStringCellValue(), true)) {
			error.append(" Invalid name");
			flag = false;
		}
		if (contryCode == null || !CommonLiterals.validateCountryCode(contryCode.getStringCellValue())) {
			error.append(" Invalid country code");
			flag = false;
		}
		if (contactNo == null || !CommonLiterals.isValidMobile(contactNo.getStringCellValue(), contryCode.getStringCellValue())) {
			error.append(" Invalid contact no");
			flag = false;
		}
		errors.set(rowNum, error.toString());
		if (flag) {
			model.setContactName(name.getStringCellValue());
			model.setContactNumber(contactNo.getStringCellValue());
		}
		return flag;
	}

}
