package cn.gyyx.action.beans.wd10yearcoser;
/**
 * 版        权：光宇游戏
 * 作        者：WangAndong
 * 创建时间：2016年9月9日 下午1:08:01
 * 描        述：
 */
public class Constants {
	
	public static enum RESOURCETYPE{
		COS_PIC,
		HANDPAINTED,
		MUSIC,
		VIDEO
	}
	
	public static enum PASSTYPE{
		CHECKED,
		CHECKING,
		UNCHECK
	}
	
	public static int getTopNumByWorksType(String type){
		int num = 4;
		if("COS_PIC".equals(type)){
			
		}else if("HANDPAINTED".equals(type)){
			num = 5;
		}else if("MUSIC".equals(type)){
			num = 10;
	    }else if("VIDEO".equals(type)){
			num = 3;
	    }
		return num;
	}
}
