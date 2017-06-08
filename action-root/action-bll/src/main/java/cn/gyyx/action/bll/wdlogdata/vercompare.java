package cn.gyyx.action.bll.wdlogdata;

public class vercompare {
/*
 * 比较问道客服端版本与指定版本
 * 客户端小于指定版本返回false，大于等于指定版本返回true
 */
	public static boolean isNewVersion(String ClientVersion, String newVersion)
	{
	    if (newVersion.equals(ClientVersion))
	    {
	        return true;
	    }
	    
	    String[] ClientArray = ClientVersion.split("\\.");
	    String[] newArray = newVersion.split("\\.");
	 
	    int length = ClientArray.length < newArray.length ? ClientArray.length : newArray.length;
	 
	    for (int i = 0; i < length; i++)
	    {
	        if (Integer.parseInt(ClientArray[i]) > Integer.parseInt(newArray[i]))
	        {
	            return true;
	        }
	        
	        else if (Integer.parseInt(ClientArray[i]) < Integer.parseInt(newArray[i]))
	        {
	            return false;
	        }
	        // 相等 比较下一组值
	    }
	 
	    return true;
	}
	
	
	
}
