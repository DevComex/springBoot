/*************************************************
       Copyright ©, 2015, GY Game
       Author: huangguoqiang
       Created: 2015年12月09日 
       Note：记录注册前浏览地址及注册地址 接口类
************************************************/
package cn.gyyx.action.dao.common;

import java.util.List;


import cn.gyyx.action.beans.common.NoteURLBean;

public interface INoteURLBean {
	/**
	 * 
	 * @日期：2015年12月09日
	 * @Title: insertNoteURLBean
	 * @Description: TODO 插入一条记录
	 * @param model
	 */
	public void insertNoteURLBean(NoteURLBean noteURLBean);
	/**
	 * 
	 * @日期：2015年12月09日
	 * @Title: selectNoteURLBeanList
	 * @Description: TODO 查询最新100条记录
	 * @return List<NoteURLBean>
	 */
	public List<NoteURLBean> selectNoteURLBeanList();
}