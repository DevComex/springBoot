package cn.mahjong.page.findplayerbuycardrecord;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public class FindPlayerBuyCardRecordPage {
	protected WebDriver driver;
	public FindPlayerBuyCardRecordPage(WebDriver driver) {
		this.driver = driver;
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, 5);
		PageFactory.initElements(finder, this);
	}
	
	/**
	 *游戏ID输入框
	 */
	@FindBy(name="account")
	private WebElement account;
	public WebElement account() {
		return account;
	}
	/**
	 *游戏ID提示语
	 */
	@FindBy(xpath="html/body/div[2]/div/div/form/p[1]/span[2]/b")
	private WebElement gameIDMsg;
	public WebElement gameIDMsg() {
		return gameIDMsg;
	}
	
	
	
	/**
	 *月份选择框
	 */
	@FindBy(name="month")
	private WebElement month;
	public WebElement month() {
		return month;
	}
	
	/**
	 *查询按钮
	 */
	@FindBy(xpath="html/body/div[2]/div/div/form/p[3]/a")
	private WebElement selectBtn;
	public WebElement selectBtn() {
		return selectBtn;
	}
	
	
	
	
	
}
