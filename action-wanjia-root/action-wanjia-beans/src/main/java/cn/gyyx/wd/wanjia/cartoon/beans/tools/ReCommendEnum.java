package cn.gyyx.wd.wanjia.cartoon.beans.tools;

import java.util.ArrayList;
import java.util.List;

public  enum ReCommendEnum {
	//最新力作、浏览排行榜、道姐推荐（完结）、道姐推荐（未完结）、他们还看过
		RECOMMEND_NEW(35,"漫画专区-最新力作"),
		RECOMMEND_LIULAN(36,"漫画专区-浏览排行榜"),
		RECOMMEND_DAOJIE_OVER(37,"漫画专区-道姐推荐（完结）"),
		RECOMMEND_DAOJIE_ING(38,"漫画专区-道姐推荐（未完结）"),
		RECOMMEND_LOOK(39,"漫画专区-他们还看过");
		private int index;
		private String name;
		
		// 构造方法  
	    private ReCommendEnum(int index,String name) {  
	        this.name = name;  
	        this.index = index;  
	    }  
	    // 普通方法  
	    public static String getName(int index) {  
	        for (ReCommendEnum c : ReCommendEnum.values()) {  
	            if (c.getIndex() == index) {  
	                return c.name;  
	            }  
	        }  
	        return null;  
	    }
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	
		
}
