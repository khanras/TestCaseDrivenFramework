package com.mentor.test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Utilities {

	public static WebDriver dv;

	public static void OpenBrowser(String URL) {
		System.setProperty("webdriver.chrome.driver", "D:\\SELENIUM\\CHROME_DRIVER\\chromedriver.exe");
		dv = new ChromeDriver();
		dv.manage().window().maximize();
		dv.get(URL);
	}

	public static String readProperty(String key) throws IOException {
		String value = "";
		File f1 = new File("D:\\SELENIUM\\Workspace\\TestCaseDrivenFramework\\data\\propertiesdata.properties");
		FileInputStream f2 = new FileInputStream(f1);
		Properties p = new Properties();
		p.load(f2);
		value = p.getProperty(key);
		return value;
	}

	public static String readXL(int sheet, int row, int col) throws IOException {
		String value = "";
		File f1 = new File("D:\\sel\\workspace\\TestNG_Framework_Goibibo\\dataFile\\DataXL.xlsx");
		FileInputStream f2 = new FileInputStream(f1);
		XSSFWorkbook wr = new XSSFWorkbook(f2);
		XSSFSheet sh = wr.getSheetAt(sheet);
		XSSFRow rw = sh.getRow(row);
		try {
			XSSFCell cl = rw.getCell(col);
			value = cl.getStringCellValue();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		wr.close();
		return value;
	}

	public static void writeXL(String data, int sheet, int row, int col) throws IOException {
		File f1 = new File("D:\\sel\\workspace\\TestNG_Framework_Goibibo\\dataFile\\DataXL.xlsx");
		FileInputStream f2 = new FileInputStream(f1);
		XSSFWorkbook wr = new XSSFWorkbook(f2);
		XSSFSheet sh = wr.getSheetAt(sheet);
		XSSFRow rw;
		int count = sh.getLastRowNum();
		if (row <= count && count > 0) {
			rw = sh.getRow(row);
		} else {
			rw = sh.createRow(row);
		}
		XSSFCell cl = rw.createCell(col);
		cl.setCellValue(data);
		FileOutputStream fos = new FileOutputStream(f1);
		wr.write(fos);
		wr.close();
		fos.close();
	}

	public static int GetRowCountXL(int sheet) throws IOException {
		int count = 0;
		File f1 = new File("D:\\sel\\workspace\\TestNG_Framework_Goibibo\\dataFile\\DataXL.xlsx");
		FileInputStream f2 = new FileInputStream(f1);
		XSSFWorkbook wr = new XSSFWorkbook(f2);
		XSSFSheet sh = wr.getSheetAt(sheet);
		count = sh.getLastRowNum();
		System.out.println(count);
		wr.close();
		return count;

	}

	public static void takeScreenshot(WebDriver driver, String screenshotname) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File scrFile = ts.getScreenshotAs(OutputType.FILE);
			String timestamp = new SimpleDateFormat("yyyy_mm_dd_hh_mm_ss").format(new Date());
			FileUtils.copyFile(scrFile, new File("D:\\sel\\workspace\\TestNG_Framework_Goibibo\\dataFile\\Screenshoot\\" + screenshotname + "_" + timestamp + ".png"));
		} catch (Exception e) {
			System.out.println("Exception while taking screenshot: " + e.getMessage());
		}
	}

}
