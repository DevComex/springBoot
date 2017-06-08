package cn.gyyx.action.bll.novicecard;

import java.util.List;

import cn.gyyx.action.beans.novicecard.NoviceCardMutexBean;
import cn.gyyx.action.dao.novicecard.NoviceCardMutexDAO;

public class NoviceCardMutexBLL {
	private NoviceCardMutexDAO noviceCardMutexDAO = new NoviceCardMutexDAO();
	public List<NoviceCardMutexBean> getMutexMes(int symbol) {
		List<NoviceCardMutexBean> result = noviceCardMutexDAO.getMutexMes(symbol);
		return result;
	}
}
