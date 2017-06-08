package cn.gyyx.action.beans.wdlight2017.base;
/**
 * 版        权：光宇游戏
 * 作        者：WangAndong
 * 创建时间：2016年9月9日 下午1:08:01
 * 描        述：
 */
public class Constants {
	public static final int actionCode = 437;
	public static final String key = "WDLIGHT2017_LOTTERY_" + actionCode;
	
	public static enum UserStatus {
		unbind("未绑定",0),
		binding("绑定",1);
		
		private String name;
		private int status;
		private UserStatus(String name, int status) {
			this.name = name;
			this.status = status;
		}
		
		public static String getName(int status) {
			for (UserStatus mapper : UserStatus.values()) {
				if (mapper.status == status) {
					return mapper.name;
				}
			}
			return null;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		
	}
	
	// 灯亮度的状态
	public static enum LightType {
		NONE_LIGHT("不亮",0),
		MICRO_LIGHT("微亮",1),
		BRIGHT_LIGHT("明亮",2),
		COMPLETE_LIGHT("全亮",3);
		
		private String name;
		private int type;
		private LightType(String name, int type) {
			this.name = name;
			this.type = type;
		}
		
		public static String getName(int type) {
			for (LightType mapper : LightType.values()) {
				if (mapper.type == type) {
					return mapper.name;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}
		
	}
	
	// 层数对应的奖品
	public static enum MapperActionCode {
		FIRST_FLOOR(1,437),
		SECOND_FLOOR(2,430),
		THIRD_FLOOR(3,431),
		FOURTH_FLOOR(4,432);
		
		private int level;
		private int actionCode;
		
		
		private MapperActionCode(int level, int actionCode) {
			this.level = level;
			this.actionCode = actionCode;
		}

		public static int getName(int level) {
			for (MapperActionCode mapper : MapperActionCode.values()) {
				if (mapper.actionCode == level) {
					return mapper.actionCode;
				}
			}
			return 437;
		}
		
		public static int getActionCode(int level){
			int actionCode = 437;
			if (level == 1) {
				actionCode = MapperActionCode.FIRST_FLOOR.actionCode;
			} else if (level == 2){
				actionCode = MapperActionCode.SECOND_FLOOR.actionCode;
			} else if (level == 3){
				actionCode = MapperActionCode.THIRD_FLOOR.actionCode;
			} else if (level == 4){
				actionCode = MapperActionCode.FOURTH_FLOOR.actionCode;
			} 
			return actionCode;
		}
		
		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
		}

		public int getActionCode() {
			return actionCode;
		}
		public void setActionCode(int actionCode) {
			this.actionCode = actionCode;
		}
		
	}
	
}
