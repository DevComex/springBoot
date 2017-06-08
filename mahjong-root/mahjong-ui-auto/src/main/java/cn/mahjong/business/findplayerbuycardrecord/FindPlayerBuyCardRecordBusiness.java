package cn.mahjong.business.findplayerbuycardrecord;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import cn.gyyx.selenium.core.SeleniumUtil;
import cn.mahjong.page.findplayerbuycardrecord.FindPlayerBuyCardRecordPage;

public class FindPlayerBuyCardRecordBusiness extends SeleniumUtil{
	//page对象
	private FindPlayerBuyCardRecordPage playBuyCardRecord;

	public FindPlayerBuyCardRecordBusiness(WebDriver driver) {
		super(driver);
		this.driver=driver;
		playBuyCardRecord = new FindPlayerBuyCardRecordPage(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void gameIDVerify(String gameID,String promptMsg)
	{
		//输入游戏ID
		sendKeys(playBuyCardRecord.account(),gameID);
		//点击查询按钮触发游戏ID输入框下面的提示语
		click(playBuyCardRecord.selectBtn());
		Assert.assertTrue(playBuyCardRecord.gameIDMsg().getText().contains(promptMsg));
	}
	

}
