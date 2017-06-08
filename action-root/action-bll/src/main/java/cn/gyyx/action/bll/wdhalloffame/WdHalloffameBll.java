package cn.gyyx.action.bll.wdhalloffame;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdhalloffame.WdHalloffameBean;
import cn.gyyx.action.dao.WdHalloffame.WdHalloffameDao;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WdHalloffameBll {
	private static final Logger logger = GYYXLoggerFactory.getLogger(WdHalloffameBll.class);
	WdHalloffameDao wdHalloffameDao = new WdHalloffameDao();

	/***
	 * 分页查询
	 **/
	public ResultBean<WdHalloffameBean> selectByPage(int pageSize, int start) {
		ResultBean<WdHalloffameBean> result = new ResultBean<WdHalloffameBean>(false, "未知错误", null);
		result = wdHalloffameDao.selectByPage(pageSize, start);
		if (result.getRows() == null || result.getRows().size() == 0) {
			result.setIsSuccess(true);
			result.setMessage("还没有数据");
			return result;
		} else {
			/***
			 * 加密电话号码只保留最后2位
			 **/
			String b = "*********";
			for (int i = 0; i <result.getRows().size() ; i++) {
				String pn = result.getRows().get(i).getPhoneNo();
				if(pn!=null&&!pn.equals("")){
				String pn9 = pn.substring(0, pn.length() - 2);
				pn = pn.replace(pn9, b);
				result.getRows().get(i).setPhoneNo(pn);
				logger.info(pn);
				}

			}
			result.setIsSuccess(true);
			result.setMessage("成功");
			return result;
		}

	}

	/***
	 * 更新标记
	 **/
	public void updateismark(int code, int ismark) {
		wdHalloffameDao.updateismark(code, ismark);

	}

	/***
	 * 更新备注
	 **/
	public void updateremark(int code, String remark) {
		wdHalloffameDao.updateremark(code, remark);

	}

	/**
	 * 按QQ号查询用户信息
	 **/
	public ResultBean<WdHalloffameBean> selectByQq(String qqNo) {
		ResultBean<WdHalloffameBean> result = new ResultBean<WdHalloffameBean>(false, "未知错误", null);
		if (qqNo == null && qqNo.length() <= 0) {
			result.setMessage("qq号不能为空");
			return result;
		} else {
			return wdHalloffameDao.selectByQq(qqNo);

		}
	}

	/**
	 * 按用户账号查询用户信息
	 **/
	public ResultBean<WdHalloffameBean> selectByUserId(String userName) {
		ResultBean<WdHalloffameBean> result = new ResultBean<WdHalloffameBean>(false, "未知错误", null);
		if (userName == null && userName.length() <= 0) {
			result.setMessage("用户号不能为空");
			return result;
		} else {
			return wdHalloffameDao.selectByUserId(userName);

		}
	}

	/**
	 * 按时间查询用户信息
	 **/
	public ResultBean<WdHalloffameBean> selectByTime(Date stdate, Date endate) {
		ResultBean<WdHalloffameBean> result = new ResultBean<WdHalloffameBean>(false, "未知错误", null);

		if (endate.before(stdate)) {
			result.setMessage("请选择正确的开始结束时间");
		}
		return wdHalloffameDao.selectByTime(stdate, endate);

	}

	/**
	 * 插入EXCEL表
	 **/
	public ResultBean<Integer> insertExcel(List<WdHalloffameBean> WdHalloffameBean) {
		ResultBean<Integer> result = new ResultBean<Integer>();
		int a = wdHalloffameDao.insertExcel(WdHalloffameBean);
		if (a == 0) {
			result.setIsSuccess(false);
			result.setMessage("没有数据,插入失败");
			return result;
		} else {
			result.setIsSuccess(true);
			result.setMessage("插入成功");
			result.setData(a);
			return result;
		}
	}

	/**
	 * 检查excel表是否有重复
	 **/
	public ResultBean<String> checkusername(String username) {
		ResultBean<String> un = wdHalloffameDao.selectByUserName(username);
		if (un.getData()==null) {
			un.setIsSuccess(true);
			return un;
		} else if (!un.getData().isEmpty()) {
			un.setIsSuccess(false);
			un.setMessage("已存在此用户名,插入失败:" + un.getData());
			return un;

		} else {
			un.setIsSuccess(false);
			return un;
		}

	}
	/**
	 *逐条插入game_vip_qualification表
	 * 
	 * @param userName
	 * @return
	 */
	public void insertUserName(String username){
		
		wdHalloffameDao.insertUserName(username);
				
	}
}
