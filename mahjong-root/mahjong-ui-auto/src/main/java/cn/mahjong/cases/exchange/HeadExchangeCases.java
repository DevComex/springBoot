package cn.mahjong.cases.exchange;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cn.gyyx.selenium.core.Browsers;
import cn.gyyx.selenium.core.BrowsersType;
import cn.gyyx.selenium.util.PropertyUtil;
import cn.mahjong.business.exchange.HeadExchangeBusiness;
import cn.mahjong.business.login.HeadLoginBusiness;

public class HeadExchangeCases {
	
	private HeadExchangeBusiness exchange;
	private WebDriver webdriver;
	
	private PropertyUtil property = new PropertyUtil("/conf/env.properties");
	private String webUrl=property.getProperty("loginUrl");
	
	public HeadExchangeCases(){
		webdriver = Browsers.browserDriver(BrowsersType.chrome);
		exchange = new HeadExchangeBusiness(webdriver);
	}
	
	/**
	 * 玩家ID验证
	 */
	@DataProvider(name="account")
	public static Object[][] accountParam(){
		return new Object[][]{
				{"No1", "", "玩家ID不能为空"},
				{"No1", " ", "玩家ID不能为空"},
//				{"No1", "abcdefg", ""}, 只能输入数字
				{"No2", "123456789321456", "用户不存在"},
				{"No3", "123456789321", "用户不存在"},
				{"No4", "12", "第二步：填写充卡数量，给玩家充卡"}
		};
	}
	
	/**
	 * 充卡数量验证
	 */
	@DataProvider(name="amount")
	public static Object[][] amountParam(){
		return new Object[][]{
				{"No1",  "", "充卡数量不能为空"},	
				{"No1", " ", "充卡数量不能为空"},	
				{"No1", "abcd", "充卡数量不能为空"},
				{"No1", "@#$%%", "充卡数量不能为空"},
				{"No1", "１２３４５", "充卡数量不能为空"},
				{"No1", "测试", "充卡数量不能为空"},
				{"No1", "0", "充卡数量必须为大于0的整数"},
				{"No1", "0.5", "充卡数量必须为大于0的整数"},
				{"No1", "3000", "不能大于当前库存"},
		};
	}
	
	
	/**
	 * 保持登录
	 */
	@Test(enabled=true)
	public void lginFirstTest() throws InterruptedException{
		HeadLoginBusiness login=new HeadLoginBusiness(webdriver);
		login.loginTest(webUrl);
	}
	
	/**
	 * 游戏ID验证
	 */
	@Test(dataProvider="account", enabled=true)
	public void accountValidation_test(String type, String account, String promptMsg) throws InterruptedException{
		exchange.accountValidation_test(type, account, promptMsg);
	}
	
	/**
	 * 验证库存
	 */
	@Test(enabled=true)
	public void inventoryValidation_test() throws InterruptedException{
		exchange.inventoryValidation_test();
	}
	
	/**
	 * 充卡数量验证
	 * @throws InterruptedException 
	 */
	@Test(dataProvider="amount", enabled=true)
	public void amountValidationTest(String type, String amount, String promptMsg) throws InterruptedException{
		exchange.amountValidationTest(type, amount, promptMsg);
	}
}
