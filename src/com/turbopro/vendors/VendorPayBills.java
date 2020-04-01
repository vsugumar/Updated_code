package com.turbopro.vendors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import com.turbopro.MethodsLibrary.*;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class VendorPayBills extends Methods {

	private StringBuffer verificationErrors = new StringBuffer();
	String ourPO = "";

	private String Url, UName, Password, PayableTo, VendorName, LineItem, Quantity, Freight;
	FileInputStream fis;
	HSSFWorkbook srcBook ;

	//accessing the chrome driver
	@BeforeTest
	public void beforeTest() throws FileNotFoundException, IOException, Exception
	{
		srcBook=new HSSFWorkbook(new FileInputStream(new File("./testdata/VendorInputs.xls")));
		openChromeBrowser();
		
		Url= srcBook.getSheetAt(0).getRow(1).getCell(ColumnNumber(srcBook,0,0,"baseURL")).toString();
		UName= srcBook.getSheetAt(0).getRow(1).getCell(ColumnNumber(srcBook,0,0,"username")).toString();
		Password= srcBook.getSheetAt(0).getRow(1).getCell(ColumnNumber(srcBook,0,0,"password")).toString();
		PayableTo= srcBook.getSheetAt(0).getRow(1).getCell(ColumnNumber(srcBook,0,0,"PayableTo")).toString();
		LineItem = srcBook.getSheetAt(0).getRow(1).getCell(ColumnNumber(srcBook,0,0,"LineItem")).toString();
		Quantity= srcBook.getSheetAt(0).getRow(1).getCell(ColumnNumber(srcBook,0,0,"Quantity")).toString();
		Freight= srcBook.getSheetAt(0).getRow(1).getCell(ColumnNumber(srcBook,0,0,"Freight")).toString();
		VendorName= srcBook.getSheetAt(0).getRow(1).getCell(ColumnNumber(srcBook,0,0,"VendorName")).toString();
	}

	private int ColumnNumber(HSSFWorkbook Hwb,int sheetNum, int RowCount,String ColumnHeader) throws Exception
	{			
		int patchColumn = -1;
		for (int cn=0; cn<Hwb.getSheetAt(sheetNum).getRow(RowCount).getLastCellNum(); cn++) {
			Cell c = Hwb.getSheetAt(sheetNum).getRow(RowCount).getCell(cn);
			if (c.toString() == null) {
				// Can't be this cell - it's empty
				continue;
			}
			else {
				String text = c.toString();
				if (ColumnHeader.equalsIgnoreCase(text)) {
					patchColumn = cn;
					break;
				}
			}
		}
		if (patchColumn == -1) {
			throw new Exception("None of the cells in the first row were Patch");
		} 
		else
			return patchColumn;
	}

	//TC_VPB_0001 - logging into the application and navigate to vendor pay bills 
	@Test(enabled = true, priority = 1)	
	public void vendorPayBill() throws InterruptedException, Exception
	{
		loggingIn(Url, UName, Password);
		Thread.sleep(3000);
		navigateVendorPayBills();
		System.out.println("Case 1: Vendor Pay Bill");
	}


	//TC_VPB_0002 - view invoices based on vendor grouping and ungrouping 
	@Test(enabled = true, priority = 2)	
	public void vendorGroupAndUngroup() throws InterruptedException, Exception
	{
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("vendorGroupOrUnGroup")));
		driver.findElement(By.id("vendorGroupOrUnGroup")).click();
		Thread.sleep(4000);
		driver.findElement(By.id("vendorGroupOrUnGroup")).click();
		System.out.println("Case 2: vendorGroupAndUngroup");
	}

	//TC_VPB_0003 - view invoices based on applied filters 
	@Test(enabled = true, priority = 3)	
	public void viewVenInvByFiltering() throws InterruptedException, Exception
	{
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("filterButton")));
		driver.findElement(By.id("filterButton")).click();
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='filtertypesDiv']/table/tbody/tr[1]")));
		driver.findElement(By.xpath("//*[@id='filtertypesDiv']/table/tbody/tr[1]")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("filterButton")).click();
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='filtertypesDiv']/table/tbody/tr[2]")));
		driver.findElement(By.xpath("//*[@id='filtertypesDiv']/table/tbody/tr[2]")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("filterButton")).click();
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='filtertypesDiv']/table/tbody/tr[3]")));
		driver.findElement(By.xpath("//*[@id='filtertypesDiv']/table/tbody/tr[3]")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("filterButton")).click();
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='filtertypesDiv']/table/tbody/tr[3]")));
		driver.findElement(By.xpath("//*[@id='filtertypesDiv']/table/tbody/tr[1]")).click();
		Thread.sleep(3000);
		System.out.println("Case 3: viewVenInvByFiltering");
	}


	//TC_VPB_0004 - pay vendor bills which is appearing first  
	@Test(enabled = true, priority = 4)	
	public void payVendorBills() throws InterruptedException, Exception
	{
		//getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='vendorBillsghead_0_1']/td/span")));
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@id='vendorBillsghead_0_0']/td[1]/span[1]")));
		driver.findElement(By.xpath("//tr[@id='vendorBillsghead_0_0']/td[1]/span[1]")).click();
		//getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='2']/td[1]/img")));
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@id='1']//img[1]")));
		driver.findElement(By.xpath("//tr[@id='1']//img[1]")).click();
		navigateBankWriteChecks();
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Print']")));
		driver.findElement(By.xpath("//input[@value='Print']")).click();
		
		Thread.sleep(2000);
		if(driver.findElements(By.xpath("(//span[text()='Warning'])[2]")).size()!=0)
		{
		if(driver.findElement(By.xpath("(//span[text()='Warning'])[2]")).isDisplayed())
		{
			driver.findElement(By.xpath("(//span[text()='OK'])[2]")).click();
		}
		}
		
		Thread.sleep(4000);
		navigateVendorPayBills();
		System.out.println("Case 4: payVendorBills");
	}


	//TC_VPB_0005 - create a vendor bills and then paying it through vendor pay bills 
	@Test(enabled = true, priority = 5)	
	public void createVendInvAndPayVendBills() throws InterruptedException, Exception
	{
		navigateVendorInvoices();
		Thread.sleep(3000);
		VendorInvoicesAndBills vendorInvoicesAndBillsObj = new VendorInvoicesAndBills(PayableTo); 
		vendorInvoicesAndBillsObj.createVendorInvoice();  
		Thread.sleep(3000);
		navigateVendorPayBills();
		driver.findElement(By.id("vendorBillPay_groupID")).click();
		driver.findElement(By.id("vendorBillPay_groupID")).clear();
		driver.findElement(By.id("vendorBillPay_groupID")).sendKeys(PayableTo);
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/ul[13]/li/a")));
		driver.findElement(By.xpath("//body/ul[13]/li/a")).click();
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='vendorBillsghead_0_0']/td/span")));
		driver.findElement(By.xpath("//*[@id='vendorBillsghead_0_0']/td/span")).click();
		List<WebElement> noOfVendorInvoice = driver.findElements(By.xpath("//td[1]/img"));
		int count = noOfVendorInvoice.size();
		driver.findElement(By.xpath("//*[@id='"+count+"']/td[1]/img")).click(); 
		navigateBankWriteChecks();
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Print']")));
		driver.findElement(By.xpath("//input[@value='Print']")).click();
		Thread.sleep(4000);
		System.out.println("Case 5: createVendInvAndPayVendBills");
		driver.navigate().refresh();
	}	

	//TC_VPB_0006 - creating a vendor invoice for received inventory (of purchase order) and pay vendor bills
	@Test(enabled = false, priority = 6)	//some issue needs to be discussed 
	public void payVendBillsForReceivedInventoryVeBill() throws InterruptedException, Exception
	{
		VendorInvoicesAndBills vendorInvoicesAndBillsObj = new VendorInvoicesAndBills(VendorName, LineItem, Quantity, Freight); 
		vendorInvoicesAndBillsObj.createVendorInvoiceForPurchaseOrder();
		Thread.sleep(3000);
		navigateVendorPayBills();
		driver.findElement(By.id("vendorBillPay_groupID")).click();
		driver.findElement(By.id("vendorBillPay_groupID")).clear();
		driver.findElement(By.id("vendorBillPay_groupID")).sendKeys(VendorName);
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/ul[13]/li/a")));
		driver.findElement(By.xpath("//body/ul[13]/li/a")).click();
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='vendorBillsghead_0_0']/td/span")));
		driver.findElement(By.xpath("//*[@id='vendorBillsghead_0_0']/td/span")).click();
		List<WebElement> noOfVendorInvoice = driver.findElements(By.xpath("//td[1]/img"));
		int count = noOfVendorInvoice.size();
		driver.findElement(By.xpath("//*[@id='"+count+"']/td[1]/img")).click(); 
		navigateBankWriteChecks();
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Print']")));
		driver.findElement(By.xpath("//input[@value='Print']")).click();
		Thread.sleep(4000);
		System.out.println("Case 6");
	}

	
	//TC_VPB_0007 - attempt to write checks first, navigate to write check and then pay vendor bills 
	@Test(enabled = true, priority = 7)	
	public void accessBankChecksForVenPayBills() throws InterruptedException, Exception
	{
		navigateBankWriteChecks();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@value='Print']")).click();
		Thread.sleep(2000);
		if(driver.findElement(By.id("msgdlg")).isDisplayed())
		{
			driver.findElement(By.xpath("//span[text()='OK']/parent::button")).click();
		}
		
		Thread.sleep(3000);
		navigateVendorPayBills();
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@id='vendorBillsghead_0_1']/td/span")));
		driver.findElement(By.xpath("//*[@id='vendorBillsghead_0_1']/td/span")).click();
		//getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='2']/td[1]/img")));
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='vendorBills']/tbody[1]/tr[13]/td[1]/img[1]")));
		driver.findElement(By.xpath("//table[@id='vendorBills']/tbody[1]/tr[13]/td[1]/img[1]")).click();
		//driver.findElement(By.xpath("//*[@id='2']/td[1]/img")).click();
		navigateBankWriteChecks();
		getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Print']")));
		driver.findElement(By.xpath("//input[@value='Print']")).click();
		Thread.sleep(4000);
		if(driver.findElement(By.id("msgdlg")).isDisplayed())
		{
			driver.findElement(By.xpath("//span[text()='OK']/parent::button")).click();
		}
		System.out.println("Case 7");
	}
	

	@AfterTest
	public void teardown() 
	{
		driver.quit();
	}
}
