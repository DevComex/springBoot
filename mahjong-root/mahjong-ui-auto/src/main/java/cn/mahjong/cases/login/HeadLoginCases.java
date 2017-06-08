package cn.mahjong.cases.login;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cn.gyyx.selenium.core.Browsers;
import cn.gyyx.selenium.core.BrowsersType;
import cn.mahjong.business.login.HeadLoginBusiness;

public class HeadLoginCases{
	
	private HeadLoginBusiness login;
	private WebDriver webdriver;
	
	public HeadLoginCases(){
		webdriver = Browsers.browserDriver(BrowsersType.chrome);
		login = new HeadLoginBusiness(webdriver);
	}
	
	/**
	 * 登录测试数据
	 * 类型（No1用户名、No2密码、No3弹框）、用户名、密码、断言
	 */
	@DataProvider(name="account")
	public static Object[][] accountParam(){
		return new Object[][]{
			{"No1","", "test09", "用户名不能为空"},
			{"No1", " ", "test09", "用户名不能为空"},
			{"No1", "测试", "test09","用户名不能为空"},
			{"No1","~", "test09", "用户名不能为空"},
			{"No2","test09", "", "密码不能为空"},
			{"No2", "test09", " ", "密码不能为空"},
			{"No2", "test09", "测试","密码不能为空"},
			{"No2","test09", "<>", "密码不能为空"},
			{"No2", "test09", "1234", "密码不能为空"},
			{"No2", "test09", "1821018163818210181638", "只接收前16位字符"},
			{"No3", "18210181638", "test09", "用户名或密码错误"},
		};
	}

	/**
	 * 验证码测试数据
	 * 
	 */
	
	@DataProvider(name="validationCode")
	public static Object[][] validationCodeParam(){
		return new Object[][]{
			{"No1", "18210181638", "test09", "验证码未显示"},
			{"No1", "18210181638", "test09", "验证码未显示"},
			{"No1", "18210181638", "test09", "验证码未显示"},
			{"No2", "18210181638", "test09", "显示验证码"},
		};
	}

	
	@Test(enabled=true)
	public void login_test() throws InterruptedException
	{
		login.loginTest("");
	}
	
	@Test(dataProvider="account",enabled=true)
	public void accountValidation_test(String type, String account, String password, String prompt_msg) throws InterruptedException{
		login.accountValidationTest(type, account, password, prompt_msg);
	}
	
}
