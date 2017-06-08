package cn.mahjong.cases.findplayerbuycardrecord;

import org.mahjong.ui.auto.CommonBase;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cn.gyyx.selenium.core.Browsers;
import cn.gyyx.selenium.core.BrowsersType;
import cn.gyyx.selenium.util.PropertyUtil;
import cn.mahjong.business.findplayerbuycardrecord.FindPlayerBuyCardRecordBusiness;
import cn.mahjong.business.login.HeadLoginBusiness;

public class FindPlayerBuyCardRecordCases{
	//业务对象
	private FindPlayerBuyCardRecordBusiness findPlayerBuyCardRecord;
	private WebDriver webdriver;
	//配置文件对象
	private  PropertyUtil property = new PropertyUtil("/conf/env.properties");
	private String webUrl=property.getProperty("playerexchangeUrl");
	//构造函数
	public FindPlayerBuyCardRecordCases(){
		webdriver = Browsers.browserDriver(BrowsersType.chrome);
		findPlayerBuyCardRecord = new FindPlayerBuyCardRecordBusiness(webdriver);
	}
	
	@DataProvider(name="gameIDParam")
	public static Object[][] loginAccountParam()
		{
			return new Object[][] {
					{"","游戏ID不能为空"},
					//{"abcd","正常"},
					//{"abcdefg","正常"},
					//{"aaaabbbbccccdddd","正常"},
					//{"aaaabbbbccccddddeeeef","只接受前20位输入"},//只接受前20位输入
					//{"1abcdef","正常"},
					//{"测试","账号不能为空"},//不接受汉字输入
					//{"$","账号不能为空"},    //不接受特殊符号输入
					//{"ａｂｃｄ","账号不能为空"}//不接受全角输入
			};
		}
	
	//局头账号先登录
	@Test(enabled = true)
	public void loginFirst_test() throws InterruptedException  {
		HeadLoginBusiness login = new HeadLoginBusiness(webdriver);
		login.loginTest(property.getProperty("loginUrl"));
		// 然后打开另一个标签页
		CommonBase common = new CommonBase();
		common.actionOpenLinkInNewTab(webdriver, webUrl);
	}
	
	//游戏ID验证
	@Test(dataProvider = "gameIDParam", enabled = true)
	public void phoneVerifyCodeVerify_test1(String gameID, String promptMsg){
		findPlayerBuyCardRecord.gameIDVerify(gameID,promptMsg);
	}
}

