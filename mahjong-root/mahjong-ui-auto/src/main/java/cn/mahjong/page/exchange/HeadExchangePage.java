package cn.mahjong.page.exchange;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public class HeadExchangePage {
	protected WebDriver driver;
	public HeadExchangePage(WebDriver driver)
	{
		this.driver = driver;
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, 5);
		PageFactory.initElements(finder, this);
	}
	
	/**
	 * 个人库存
	 */
	@FindBy(xpath="html/body/div[2]/div/div/form/div[1]/p[2]/span")
	private WebElement inventory;
	public WebElement getinventory(){
		return inventory;
	}
	
	/**
	 * 玩家ID
	 */
	@FindBy(name="account")
	private WebElement account;
	public WebElement getaccount(){
		return account;
	}
	
	/**
	 * 查找按钮
	 */
	@FindBy(xpath="html/body/div[2]/div/div/form/div[1]/p[3]/a")
	private WebElement findBtn;
	public WebElement getfindBtn(){
		return findBtn;
	}
	
	/**
	 * 玩家ID提示语
	 */
	@FindBy(xpath="html/body/div[2]/div/div/form/div[1]/p[3]/span[2]/b")
	private WebElement accountMessage;
	public WebElement getaccountMessage(){
		return accountMessage;
	}
	
	/**
	 * 充卡数量
	 */
	@FindBy(name="amount")
	private WebElement amount;
	public WebElement getamount(){
		return amount;
	}
	
	/**
	 * 充卡数量提示信息
	 */
	@FindBy(xpath="html/body/div[2]/div/div/form/div[2]/p[5]/span[2]/b")
	private WebElement amountMessage;
	public WebElement getamountMessage(){
		return amountMessage;
	}
	
	/**
	 * 充值按钮
	 */
	@FindBy(xpath="html/body/div[2]/div/div/form/div[2]/p[5]/a")
	private WebElement successBtn;
	public WebElement getSuccessBtn(){
		return successBtn;
	}
	
	/**
	 * 第二步页面
	 */
	@FindBy(xpath="html/body/div[2]/div/div/form/div[2]/p[1]/span")
	private WebElement secondStep;
	public WebElement getsecondStop(){
		return secondStep;
	}
}
