package cn.mahjong.business.login;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import cn.gyyx.selenium.core.SeleniumUtil;
import cn.gyyx.selenium.util.PropertyUtil;
import cn.mahjong.page.login.HeadLoginPage;

public class HeadLoginBusiness extends SeleniumUtil{

	private  PropertyUtil property = new PropertyUtil("/conf/env.properties");
	private String url=property.getProperty("loginUrl");
	
	private HeadLoginPage login;
	
	public HeadLoginBusiness(WebDriver driver) 
	{
		super(driver);
		this.driver=driver;
		login = new HeadLoginPage(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void loginTest(String strUrl) throws InterruptedException
	{   strUrl=url;
		get(strUrl);
		sendKeys(login.getaccount(), "18210181639");
		sendKeys(login.getpassword(), "288131");
		click(login.getLoginButton());
		
		Thread.sleep(2000);
		Assert.assertTrue(getText(login.getrechargeRoomcard()).contains("充值房卡"));
	}
	
	public void accountValidationTest(String type, String account, String password, String promptMsg) throws InterruptedException{
		get(url);
		sendKeys(login.getaccount(), account);
		sendKeys(login.getpassword(), password);
		click(login.getLoginButton());
		
		if ("No1".equals(type)){
			String prompt = getText(login.getaccountMessage());
			Assert.assertTrue(prompt.contains(promptMsg));
		}
		if("No2".equals(type)){
			String prompt = getText(login.getpasswordMessage());
			Assert.assertTrue(prompt.contains(promptMsg));
		}else if(promptMsg.contains("只接收前16位字符")){
			Assert.assertTrue(password.substring(0,16).equals(login.getpassword().getAttribute("value")));
			Thread.sleep(2000);
			Alert alert = driver.switchTo().alert();
		    Assert.assertTrue(alert.getText().contains("用户名或密码错误"));
		    Thread.sleep(2000);
		    alert.accept();
		}
		if("No3".equals(type)){
			Thread.sleep(2000);
			Alert alert = driver.switchTo().alert();
		    Assert.assertTrue(alert.getText().contains(promptMsg));
		    Thread.sleep(2000);
		    alert.accept();
		}
	}

}