package cn.gyyx.action.bll.noviceoa;

import cn.gyyx.action.beans.noviceoa.NoviceGiftPutBean;
import cn.gyyx.action.dao.noviceoa.NoviceGiftPutDao;

import java.util.List;

/**
 * 新手卡礼包投放对象业务逻辑层
 * 
 * @author tanjunkai
 *
 */
public class NoviceGiftPutBll {

	private NoviceGiftPutDao noviceGiftPutDao = new NoviceGiftPutDao();

	/**
	 * 添加新手卡礼包投放对象
	 * 
	 * @param bean
	 * @return
	 */
	public int insertSelective(NoviceGiftPutBean bean) {
		return noviceGiftPutDao.insertSelective(bean);
	}

	/**
	 * 新手卡礼包投放对象查询
	 * 
	 * @param putType
	 * @param putName
	 * @param index
	 * @param pageCount
	 * @return
	 */
	public List<NoviceGiftPutBean> selectPageList(int putType, String putName, int index, int pageCount) {
		return noviceGiftPutDao.selectPageList(putType, putName, index, pageCount);
	}

	/**
	 * 通过投放类型获取投放对象列表
	 * @param putType
	 * @return
	 */
	public List<NoviceGiftPutBean> selectByPutType(int putType)
	{
		return noviceGiftPutDao.selectByPutType(putType);
	}
	
	/**
	 * 根据投放对象和投放类型获取投放实体
	 * 
	 * @param putName
	 * @param putType
	 * @return
	 */
	public List<NoviceGiftPutBean> selectNoviceGiftPut(String putName, int putType) {
		return noviceGiftPutDao.selectNoviceGiftPut(putName, putType);
	}

	/**
	 * 获取记录总数
	 * 
	 * @return
	 */
	public int selectTotalCount(int putType, String putName) {
		return noviceGiftPutDao.selectTotalCount(putType, putName);
	}

	/**
	 * 更新投放对象信息
	 * 
	 * @param bean
	 * @return
	 */
	public int updateByPrimaryKey(NoviceGiftPutBean bean) {
		return noviceGiftPutDao.updateByPrimaryKey(bean);
	}

	/**
	 * 通过主键获取投放对象信息
	 * @param code
	 * @return
	 */
	public NoviceGiftPutBean selectByPrimaryKey(Integer code) {
		return noviceGiftPutDao.selectByPrimaryKey(code);
	}
}
