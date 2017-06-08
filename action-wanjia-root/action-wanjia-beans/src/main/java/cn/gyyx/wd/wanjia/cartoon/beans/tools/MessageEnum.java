package cn.gyyx.wd.wanjia.cartoon.beans.tools;


public  enum MessageEnum {
		REVIEW_FAIL_MESSAGE(1,"非常抱歉，您的作品《{0}》经编辑审核未通过，请重新上传！"),
		REVIEW_YES_MESSAGE(2,"恭喜，您的漫画《{0}》十分优秀，已通过官方审核并在平台发布 ！"),
		EDIT_MESSAGE(3,"您收到了编辑关于稿件《{0}》的回复：{1}"),
		//REWAR_LEVEL(4,"恭喜您的作品《{0}》被推荐到{1}，奖励等级为{2}！");
		REWAR_LEVEL(4,"恭喜，您的漫画《{0}》十分优秀，已被官方推荐，获得的奖励等级为{1}，请关注官方公告 ！");
		private int index;
		private String name;
		
		// 构造方法  
	    private MessageEnum(int index,String name) {  
	        this.name = name;  
	        this.index = index;  
	    }  
	    // 普通方法  
	    public static String getName(int index) {  
	        for (MessageEnum c : MessageEnum.values()) {  
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
