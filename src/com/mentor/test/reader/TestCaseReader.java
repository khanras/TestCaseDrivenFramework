package com.mentor.test.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mentor.test.ActionType;
import com.mentor.test.TestMetaInfo;

public class TestCaseReader {
	
	private Map<String, List<TestMetaInfo>> data = new LinkedHashMap<>();
	private String dataSheetName = null;
	private String xlsPath;

	public TestCaseReader(String xlsPath) {
		this.xlsPath = xlsPath;
		loadData();
	}

	public void setDataSheetName(String dataSheetName) {
		this.dataSheetName = dataSheetName;
	}

	private void loadData() {
		try {
			readXL();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TestMetaInfo getData(String sheetName, int sequenceNo) {
		if (!data.containsKey(sheetName)) {
			assert false : "Invalid Sheet Name " + sheetName;
			return null;
		}
		return data.get(sheetName).get(sequenceNo);
	}

	public TestMetaInfo getData(int sequenceNo) {
		assert dataSheetName != null;
		return getData(dataSheetName, sequenceNo);
	}

	public void readXL() throws IOException {
		File f1 = new File(xlsPath);
		FileInputStream f2 = new FileInputStream(f1);
		XSSFWorkbook wr = new XSSFWorkbook(f2);
		
		for (int i = 0; i < wr.getNumberOfSheets(); i++) {
			XSSFSheet sh = wr.getSheetAt(i);
			//Ignoring the header row, so starts with 1
			for (int row = 1; row <= sh.getLastRowNum(); row++) {
				XSSFRow rw = sh.getRow(row);
				Integer sequenceNo = new Double(rw.getCell(0).getNumericCellValue()).intValue();
				/*String parentSheetName = rw.getCell(1).getStringCellValue();
				String actionType = rw.getCell(2).getStringCellValue();
				String locatorType = rw.getCell(3).getStringCellValue();
				String locatorValue = getCellValue(rw, 4);*/
				String actionCommand = rw.getCell(1).getStringCellValue();
				
				/**************************NEWLY ADDED***********************************/
				String actionType=null;
				String locatorKey=null;
				String testData=null;
				
				Pattern pattern = Pattern.compile("'(?:[^']|'')+'");
		        Matcher matcher = pattern.matcher(actionCommand);
		        List<String> list=new ArrayList<>();
		        while (matcher.find()) {
		            String match = matcher.group();
		            match=match.substring(1, match.length() - 1);
		            list.add(match);
		            //System.out.println(list);
		        }
		        
		        try {
					actionType=list.get(0).toLowerCase();
				} catch (Exception e) {
					System.out.println("Action Type couldn't be recognized!!");
				}
		        try {
		        	locatorKey=list.get(1).toLowerCase();
					testData=list.get(2);
				} catch (Exception e) {
				}
		        
				String sheetName=sh.getSheetName();
				if(!data.containsKey(sheetName)) {
					data.put(sheetName, new ArrayList<>());
				}
				List<TestMetaInfo> dataList = data.get(sheetName);
				dataList.add(new TestMetaInfo(sequenceNo, ActionType.getTypeByActionMsg(actionType), locatorKey,testData));
			}
		}
		wr.close();
	}

	/*private String getCellValue(XSSFRow rw, int index) {
		return rw.getCell(index).getCellTypeEnum() == CellType.NUMERIC ? rw.getCell(index).getNumericCellValue() + "" : rw.getCell(index).getStringCellValue();
	}*/

	public List<TestMetaInfo> getAllTestMetaInfo(String sheetName) {
		return data.get(sheetName);
	}

	public List<String> getAllTestSheetNames() {
		return data.keySet()
				.stream()
				.filter(sheet->sheet.startsWith("Test_")).collect(Collectors.toList());
	}
	
	public static void main(String ars[]) {
		new TestCaseReader("D:\\SELENIUM\\Workspace\\TestLinkDigital\\data\\testCases.xlsx");
	}
}
