/*************************************************
       Copyright ©, 2015, GY Game
       Author: huangguoqiang
       Created: 2015年12月09日 
       Note：记录注册前浏览地址及注册地址 逻辑层
************************************************/
package cn.gyyx.action.bll.common;

import cn.gyyx.action.beans.common.NoteURLBean;
import cn.gyyx.action.dao.common.NoteURLDAO;

public class NoteURLBLL {
	NoteURLDAO noteURLDAO = new NoteURLDAO();
	
	/**
	 * 
	 * @日期：2015年12月09日
	 * @Title: insertNoteURLBean
	 * @Description: TODO 插入一条记录
	 * @param model
	 */
	public void insertNoteURLBean(NoteURLBean noteURLBean) {
		noteURLDAO.insertNoteURLBean(noteURLBean);
		
	}
}
