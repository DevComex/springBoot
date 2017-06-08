package org.mahjong.ui.auto;
import java.util.ArrayList;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CommonBase{ 
	 
/*
 * 输入框后面是否显示了对勾按钮
 */
	public boolean isShowRightIcon(WebElement webElement) 
	{
		if(webElement.getAttribute("style").contains("inline-block"))
		{
			System.out.println("true");
			return true;
		}
		else
		{   System.out.println("false");
			return false;
		}
}
 
	/*
	 * 在浏览器打开新标签页
	 */
	public void actionOpenLinkInNewTab(WebDriver driver,String webUrl)
	{
	    Actions actionOpenLinkInNewTab = new Actions(driver);
	    actionOpenLinkInNewTab.keyDown(Keys.CONTROL).sendKeys("t").keyUp(Keys.CONTROL).perform();
	    ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(0));
	    driver.get(webUrl);
	}
 
 
}