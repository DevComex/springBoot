package cn.mahjong.page.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import cn.gyyx.selenium.base.PageBase;

public class HeadLoginPage extends PageBase{
	protected WebDriver driver;
	public HeadLoginPage(WebDriver driver)
	{
		this.driver = driver;
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, 5);
		PageFactory.initElements(finder, this);
	}
	
	/**
	 * 用户名输入框
	 */
	@FindBy(name="account")
	private WebElement account;
	public WebElement getaccount(){
		return account;
	}
	
	/**
	 * 密码输入框
	 */
	@FindBy(name="password")
	private WebElement password;
	public WebElement getpassword(){
		return password;
	}
	
	/**
	 * 登录按钮
	 */
	@FindBy(xpath="html/body/div[2]/form/div[2]/p/span[2]/a[1]")
	private WebElement loginButton;
	public WebElement getLoginButton(){
		return loginButton;
	}
	
	/**
	 * 用户名提示信息
	 */
	@FindBy(xpath="html/body/div[2]/form/p[1]/span[2]/b")
	private WebElement accountMessage;
	public WebElement getaccountMessage(){
		return accountMessage;
	}
	
	/**
	 * 用户提示信息是否存在
	 */
	public boolean isAccountMessage(){
		if (accountMessage.getAttribute("style").contains("inline-block")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 密码提示信息
	 */
	@FindBy(xpath="html/body/div[2]/form/p[2]/span[2]/b")
	private WebElement passwordMessage;
	public WebElement getpasswordMessage(){
		return passwordMessage;
	}
	
	/**
	 * 登录成功进入充值房卡页面，房卡页面标识
	 */
	@FindBy(xpath="html/body/div[1]/div/div/ul/li")
	private WebElement rechargeRoomcard;
	public WebElement getrechargeRoomcard(){
		return rechargeRoomcard;
	}
}