package cn.gyyx.playwd.dao.playwd;

import cn.gyyx.playwd.beans.playwd.TimeLineBean;

public interface TimeLineDao {

	/**
	 * 插入
	 * @param record
	 * @return
	 */
    int insert(TimeLineBean record);

    /**
	 * 根据Code获取
	 * @param code
	 * @return
	 */
    TimeLineBean getByCode(Integer code);

    /**
	 * 更新
	 * @param record
	 * @return
	 */
    int update(TimeLineBean record);

	/**
	 * 获取数量
	 * @param bean
	 * @return
	 */
	int getCount(TimeLineBean bean);
}