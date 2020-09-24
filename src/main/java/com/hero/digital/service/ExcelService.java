package com.hero.digital.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hero.digital.config.HeroDigitalProperties;
import com.hero.digital.model.HeroDigitalModel;
import com.hero.digital.model.Person;
import com.hero.digital.util.HeroDigitalUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExcelService {

	@Autowired
	private HeroDigitalProperties properties;
	
	private AtomicInteger counter = new AtomicInteger(1);
	
	public void generateExcel(HeroDigitalModel resp, List<String> errorList) {
		Map<String, Object[]> personMap = new TreeMap<>();
		List<Person> personList = resp.getPersons();
		personMap.put("1", new Object[] { "FIRST_NAME", "LAST_NAME", "DOB" });
		
		personList.parallelStream().forEach(x->{
			personMap.put(String.valueOf(counter.incrementAndGet()),
					new Object[] { x.getFirstName(), x.getLastName(), x.getDob() });
		});
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet(HeroDigitalUtil.PERSONS_SHEET_NAME);
		
		int rowCounter = 0;
		for (String key : personMap.keySet()) {
			XSSFRow row = spreadsheet.createRow(rowCounter++);
			Object[] objectArr = personMap.get(key);
			int cellid = 0;
			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}
		
		try {
			Path excelLocation = Paths.get(properties.getExcelFolder()).toAbsolutePath().normalize();
			if(!excelLocation.toFile().exists())
				Files.createDirectories(excelLocation);
			FileOutputStream out = new FileOutputStream(
					new File(excelLocation + HeroDigitalUtil.FORWARD_SLASH + properties.getExcelName()));
			workbook.write(out);
		} catch (IOException e) {
			LOG.error("Exception while generating Excel {}",e);
			errorList.add("Exception while generating Excel : " + e.getMessage());
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				LOG.error("Exception in close Excel {}",e);
			}
		}

	}
	
}
