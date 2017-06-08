package cn.mahjong.business.exchange;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import cn.gyyx.selenium.core.SeleniumUtil;
import cn.mahjong.page.exchange.HeadExchangePage;

public class HeadExchangeBusiness extends SeleniumUtil{
	
	private HeadExchangePage exchange;
	
	public HeadExchangeBusiness(WebDriver driver) 
	{
		super(driver);
		this.driver=driver;
		exchange = new HeadExchangePage(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void accountValidation_test(String type, String account, String promptMsg) throws InterruptedException{
		sendKeys(exchange.getaccount(), account);
		click(exchange.getfindBtn());
		Thread.sleep(2000);
		if ("No1".equals(type)){
			String prompt = getText(exchange.getaccountMessage());
			Assert.assertTrue(prompt.contains(promptMsg));
		}
		if ("No2".equals(type)){
			Assert.assertTrue(account.substring(0,10).equals(exchange.getaccount().getAttribute("value")));
			Thread.sleep(2000);
			alertDismiss(promptMsg);
		}
		if ("No3".equals(type)){
			alertDismiss(promptMsg);
		}
		if ("No4".equals(type)){
			String prompt = getText(exchange.getsecondStop());
			Assert.assertTrue(prompt.contains(promptMsg));
		}
	}
	
	/**
	 * 断言alert框
	 * @param expected alert框提示信息
	 */
	public void alertDismiss(String expected){
        try {
            new WebDriverWait(driver, 5).until(ExpectedConditions
                    .alertIsPresent());
            Alert alert = driver.switchTo().alert();
            if(expected!=null){
            	Assert.assertEquals(alert.getText(), expected);
            }
            alert.accept();
        } catch (NoAlertPresentException NofindAlert) {
            NofindAlert.printStackTrace();
        }
    }
	 

	public void inventoryValidation_test(){
		String prompt = getText(exchange.getinventory());
		Assert.assertTrue(prompt.contains(1500 + " 张"));
	}

	public void amountValidationTest(String type, String amount, String promptMsg) throws InterruptedException {
		sendKeys(exchange.getamount(), amount);
		click(exchange.getSuccessBtn());
		if ("No1".equals(type)){
			String prompt = getText(exchange.getamountMessage());
			Assert.assertTrue(prompt.contains(promptMsg));
		}
	}
	

}
