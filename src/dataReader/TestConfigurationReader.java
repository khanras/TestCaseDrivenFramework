package dataReader;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestConfigurationReader {
	private Map<String,Map<String,TestKey>> data=new HashMap<>();
	private String xlspath;
	public TestConfigurationReader(String pomPath) throws Exception {
		this.xlspath=pomPath;
		readXL();
		//String Otype=getPOMType("aa","d");
		//System.out.println("Type of d is "+Otype);
		//System.out.println("Value of d is "+getPOMValue("POM","d"));
	}
	private void readXL() throws Exception {
		
		File file=new File(xlspath);
		FileInputStream fis=new FileInputStream(file);
		XSSFWorkbook wr=new XSSFWorkbook(fis);
		for(int i=0;i<wr.getNumberOfSheets();i++) {
			String sheetName=wr.getSheetName(i);
			//System.out.println(sheetName);
			XSSFSheet sh=wr.getSheetAt(i);
			for(int j=1;j<=sh.getLastRowNum();j++) {
				XSSFRow rw=sh.getRow(j);
				String key=rw.getCell(0).getStringCellValue();
				String type;
				try {
					type = rw.getCell(1).getStringCellValue();
				} catch (Exception e1) {
					type=null;
				}
				String value;
				try {
					value = rw.getCell(2).getStringCellValue();
				} catch (Exception e) {
					value=null;
				}
				//System.out.println(key	+type	+value);
				if(!data.containsKey(sheetName)) {
					data.put(sheetName, new HashMap<>());
				}
				Map<String, TestKey> dataKey=data.get(sheetName);
				dataKey.put(key, new TestKey(key,type,value));
			}
		}
		wr.close();
		
	}
	
	public String getPOMType(String sheet,String key) {
		return getTestKey(sheet,key).getType();
	}
	
	public String getPOMType(String key) {
		String sheet="POM";
		return getPOMType(sheet,key);
	}
	
	public String getValueByKey(String sheet,String key) {
		String testKey= getTestKey(sheet,key).getType();
		return testKey;
	}
	
	public String getPOMValue(String sheet,String key) {
		return getTestKey(sheet,key).getValue();
	}
	
	public String getPOMValue(String key) {
		String sheet="POM";
		return getPOMValue(sheet,key);
	}
	
	public TestKey getTestKey(String sheet, String key) {
		if(!data.containsKey(sheet)) {
			assert false:"Invalid Sheet name: "+sheet;
		}
			
		return data.get(sheet).get(key);
	}
	
	public static void main(String ars[]) throws Exception {
		new TestConfigurationReader("D:\\SELENIUM\\Workspace\\TestLinkDigital\\data\\POMData.xlsx");
	}

}
