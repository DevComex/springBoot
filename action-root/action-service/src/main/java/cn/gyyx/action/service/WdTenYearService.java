/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年3月11日下午3:00:51
 * 版本号：v1.0
 * 本类主要用途叙述：问道十年的业务层逻辑
 */

package cn.gyyx.action.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.novicecard.ActivityConfigBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.tenyearpicture.ScoreBean;
import cn.gyyx.action.beans.tenyearpicture.ScoreLogBean;
import cn.gyyx.action.beans.tenyearpicture.ScoreOaBean;
import cn.gyyx.action.bll.wdtenyearspicture.ScoreBll;
import cn.gyyx.action.bll.wdtenyearspicture.ScoreLogBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.dao.novicecard.ActivityConfigDAO;
import cn.gyyx.action.service.insurance.MemcacheUtil;
import cn.gyyx.core.memcache.XMemcachedClientAdapter;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WdTenYearService {
	private static final Logger log = GYYXLoggerFactory
			.getLogger(WdTenYearService.class);
	private ScoreBll scoreTempBll = new ScoreBll();
	private ScoreLogBll scoreLogTemp = new ScoreLogBll();
	private static MemcacheUtil memcacheUtil = new MemcacheUtil();
	private static XMemcachedClientAdapter memcachedClientAdapter = memcacheUtil
			.getMemcache();

	/**
	 * 
	 * @日期：2015年3月19日
	 * @Title: getSession 获取session
	 * @return SqlSession
	 */
	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	/***
	 * 
	 * @param score
	 *            分数
	 * @param userCode
	 *            用户主键
	 * @param hdCode
	 *            活动号
	 * @param account
	 *            账户
	 * @param ip
	 *            IP地址
	 * @return
	 */
	public static ResultBean<String> setWdtenYearScore(int score, int userCode,
			int hdCode, String account, String ip) {
		ActivityConfigDAO acd = new ActivityConfigDAO();
		ScoreLogBll scoreLogBll = new ScoreLogBll();
		ResultBean<String> resultBean = new ResultBean<>();
		try (SqlSession sqlSession = getSession()) {
			try {
				if (!isEnough(userCode, sqlSession, score)) {
					return new ResultBean<>(false, "分数不足", "分数不足");
				}
				// 活动信息配置
				ActivityConfigBean activityConfig = acd
						.selectActivityConfigByCode(hdCode);
				// 设定分数
				ScoreBean scoreBean = setScore(sqlSession, userCode, score, ip,
						account);
				scoreBean.setScore(scoreBean.getScore() + score);
				memcachedClientAdapter.set("310." + userCode + ".scoreBean",
						300, scoreBean);
				// 日志实体
				ScoreLogBean scoreLogBean = new ScoreLogBean();
				scoreLogBean.setAccount(account);
				scoreLogBean.setHdCode(hdCode);
				scoreLogBean.setHdName(activityConfig.getActivityName());
				scoreLogBean.setScore(score);
				scoreLogBean.setUserCode(userCode);
				// 增加日志
				scoreLogBll.addScoreLog(scoreLogBean, sqlSession);
				sqlSession.commit(true);
				resultBean.setProperties(true, "更改积分成功", "更改积分成功");
			} catch (Exception e) {
				log.warn("setWdtenYearScore" + e);
				sqlSession.rollback();
				return new ResultBean<>(false, "更改积分失败", "更改积分失败");
			}
		}
		return resultBean;
	}

	/***
	 * 
	 * @param score
	 *            分数
	 * @param userCode
	 *            用户主键
	 * @param hdCode
	 *            活动号
	 * @param account
	 *            账户
	 * @param ip
	 *            IP地址
	 * @return
	 */
	public static ResultBean<String> setWdtenYearScoreSession(int score, int userCode,
			int hdCode, String account, String ip,SqlSession sqlSession) {
		ActivityConfigDAO acd = new ActivityConfigDAO();
		ScoreLogBll scoreLogBll = new ScoreLogBll();
		ResultBean<String> resultBean = new ResultBean<>();
			try {
				if (!isEnough(userCode, sqlSession, score)) {
					return new ResultBean<>(false, "分数不足", "分数不足");
				}
				// 活动信息配置
				ActivityConfigBean activityConfig = acd
						.selectActivityConfigByCode(hdCode);
				// 设定分数
				ScoreBean scoreBean = setScore(sqlSession, userCode, score, ip,
						account);
				scoreBean.setScore(scoreBean.getScore() + score);
				memcachedClientAdapter.set("310." + userCode + ".scoreBean",
						300, scoreBean);
				// 日志实体
				ScoreLogBean scoreLogBean = new ScoreLogBean();
				scoreLogBean.setAccount(account);
				scoreLogBean.setHdCode(hdCode);
				scoreLogBean.setHdName(activityConfig.getActivityName());
				scoreLogBean.setScore(score);
				scoreLogBean.setUserCode(userCode);
				// 增加日志
				scoreLogBll.addScoreLog(scoreLogBean, sqlSession);
				sqlSession.commit(true);
				resultBean.setProperties(true, "更改积分成功", "更改积分成功");
			} catch (Exception e) {
				return new ResultBean<>(false, "更改积分失败", "更改积分失败");
			}
		return resultBean;
	}
	/**
	 * 
	 * @Title: getScoreBean
	 * @Description: 根据主键查询积分
	 * @param @param userCode
	 * @param @return
	 * @return ScoreBean
	 * @throws
	 */
	public static Integer getScoreBean(int userCode) {
		ScoreBll scoreBll = new ScoreBll();
		ScoreBean scoreBean = null;
		try (SqlSession sqlSession = getSession()) {
			try {
				scoreBean = scoreBll.getScoreBean(userCode, sqlSession);
			} catch (Exception e) {
				log.warn("获取十周年积分:" + e);
			}
		}
		// 返回时Integer是尽量用0，不要用null,判断时多麻烦啊!
		// 而且得出实体灵活使用，改成只返回分数，我要用其他数据还得在写方法，不灵活了!
		if (scoreBean == null) {
			log.warn("十周年积分表不存在此userCode");
			return null;
		}
		return scoreBean.getScore();
	}

	/**
	 * 
	 * @Title: getScoreBean
	 * @Description: 根据主键查询积分
	 * @param @param userCode
	 * @param @return
	 * @return ScoreBean
	 * @throws
	 */
	public static ScoreBean getEScoreBean(int userCode, SqlSession sqlSession) {
		ScoreBll scoreBll = new ScoreBll();
		return scoreBll.getScoreBean(userCode, sqlSession);
	}

	/***
	 * 增加分数
	 * 
	 * @param sqlSession
	 * @param userCode
	 * @param score
	 * @param ip
	 * @param account
	 */
	private static ScoreBean setScore(SqlSession sqlSession, int userCode,
			int score, String ip, String account) {
		ScoreBll scoreBll = new ScoreBll();
		ScoreBean scoreBean = scoreBll.getScoreBean(userCode, sqlSession);
		// 如果不等于空
		if (scoreBean != null) {
			// 设定分数
			scoreBll.setScoreBean(userCode, score, sqlSession);
		} else {
			ScoreBean scoreBeanTemp = new ScoreBean();
			scoreBeanTemp.setAccount(account);
			scoreBeanTemp.setIp(ip);
			scoreBeanTemp.setScore(score);
			scoreBeanTemp.setUserCode(userCode);
			// 增加信息
			scoreBll.addScoreBean(scoreBeanTemp, sqlSession);
		}
		return scoreBean;
	}

	/***
	 * 判断是否有足够的分数
	 * 
	 * @param userCode
	 * @param sqlSession
	 * @param score
	 * @return
	 */
	private static boolean isEnough(int userCode, SqlSession sqlSession,
			int score) {
		ScoreBll scoreBll = new ScoreBll();
		boolean is = true;
		ScoreBean scoreBean = scoreBll.getScoreBean(userCode, sqlSession);
		if (scoreBean == null && score < 0) {
			is = false;
		} else if (scoreBean != null && (scoreBean.getScore() + score) < 0) {

			is = false;
		}
		return is;
	}

	/***
	 * 
	 * @param account
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public ResultBeanWithPage<List<ScoreOaBean>> getScoreOaBeans(
			String account, int pageNo, int pageSize, SqlSession sqlSession) {
		ResultBeanWithPage<List<ScoreOaBean>> resultBean = new ResultBeanWithPage<>();
		List<ScoreOaBean> list = new ArrayList<>();
		// 获取所有积分信息
		List<ScoreBean> listScoreBeans = scoreTempBll
				.getScoreBeanas(sqlSession);
		// 筛选账户并组建返回实体
		if (listScoreBeans != null && !listScoreBeans.isEmpty()) {
			for (int i = 0; i < listScoreBeans.size(); i++) {
				ScoreBean scoreBean = listScoreBeans.get(i);
				list = isAccount(list, scoreBean, account);
			}
		}
		// 按页数筛选
		// 总页数
		int allPage = getAllPage(pageSize, list);
		// 得到相应页数信息
		list = getScoreOaBeansByPage(pageSize, allPage, pageNo, list);
		// 增加分数信息
		list = getScoreOaBeansHdScore(list, sqlSession);
		resultBean.setProperties(true, "获取成功", list, allPage);
		return resultBean;
	}

	/***
	 * 加入各个活动的分数信息
	 * 
	 * @param list
	 * @return
	 */
	private List<ScoreOaBean> getScoreOaBeansHdScore(List<ScoreOaBean> list,
			SqlSession sqlSession) {
		List<ScoreOaBean> listTemp = list;
		// 获取所有十周年活动信息
		List<String> listAction = scoreLogTemp.getScoreLogHdName(sqlSession);
		if (listTemp != null && !listTemp.isEmpty()) {
			for (int i = 0; i < listTemp.size(); i++) {
				// 具体分数信息
				ScoreOaBean scoreOaBean = listTemp.get(i);
				// 获取分数列表
				List<Integer> listScore = getListScore(listAction, scoreOaBean,
						sqlSession);
				// 加入记录
				listTemp.get(i).setHdScore(listScore);
			}
		}
		return listTemp;
	}

	/***
	 * 获取分数列表
	 * 
	 * @param listAction
	 * @param scoreOaBean
	 * @param sqlSession
	 * @return
	 */
	private List<Integer> getListScore(List<String> listAction,
			ScoreOaBean scoreOaBean, SqlSession sqlSession) {
		List<Integer> listScore = new ArrayList<>();
		if (listAction != null && !listAction.isEmpty()) {
			for (int j = 0; j < listAction.size(); j++) {
				// 获取每个活动的总积分
				int score = scoreLogTemp
						.getSumScoreByHdNameAndAccount(listAction.get(j),
								scoreOaBean.getAccount(), sqlSession);
				listScore.add(score);
			}
		}
		return listScore;
	}

	/***
	 * 得到总页码数
	 * 
	 * @param pageSize
	 * @param list
	 * @return
	 */
	private Integer getAllPage(int pageSize, List<ScoreOaBean> list) {
		if (list == null || list.isEmpty()) {
			return 1;
		} else {
			int temp = list.size();
			int allPage = temp / pageSize;
			if (temp % pageSize != 0) {
				allPage = allPage + 1;
			}
			return allPage;
		}
	}

	/***
	 * 根据页码筛选
	 * 
	 * @param pageSize
	 * @param allPage
	 * @param pageNo
	 * @param list
	 * @return
	 */
	private List<ScoreOaBean> getScoreOaBeansByPage(int pageSize, int allPage,
			int pageNo, List<ScoreOaBean> list) {
		int pageTemp = pageNo;
		List<ScoreOaBean> listTemp = new ArrayList<>();
		// 页码限制
		if (pageTemp < 1) {
			pageTemp = 1;
		}
		if (pageTemp > allPage) {
			pageTemp = allPage;
		}
		if (list != null) {
			// 开始结束范围的规定
			int begin = (pageTemp - 1) * pageSize;
			int end = pageTemp * pageSize;
			if (end >= list.size()) {
				end = list.size();
			}
			for (int i = begin; i < end; i++) {
				listTemp.add(list.get(i));
			}
		}
		return listTemp;
	}

	/***
	 * 筛选
	 * 
	 * @param list
	 * @param scoreBean
	 * @param account
	 * @return
	 */
	private List<ScoreOaBean> isAccount(List<ScoreOaBean> list,
			ScoreBean scoreBean, String account) {
		List<ScoreOaBean> listTemp = list;
		boolean is = false;
		// 判断是否符合账户筛选条件
		if ("".equals(account) || scoreBean.getAccount().equals(account)) {
			is = true;
		}
		if (is) {
			ScoreOaBean scoreOaBean = new ScoreOaBean();
			scoreOaBean.setAccount(scoreBean.getAccount());
			scoreOaBean.setScore(scoreBean.getScore());
			listTemp.add(scoreOaBean);
		}
		return listTemp;
	}

	/***
	 * 生成积分的xml
	 * 
	 * @param response
	 * @throws IOException
	 */
	public void getScoreAccountXLS(HttpServletResponse response,
			String account, SqlSession sqlSession) throws IOException {
		// 获取账户积分信息
		List<ScoreOaBean> listScoreOaBeans = getScoreOaBeansNoPage(account,
				sqlSession);
		// 活动信息
		List<String> listAction = scoreLogTemp.getScoreLogHdName(sqlSession);
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("十周年积分活动");

		sheet.setColumnWidth(0, 12 * 256);
		sheet.setColumnWidth(1, 12 * 256);
		if (listAction != null && !listAction.isEmpty()) {
			int count = 2;
			for (int i = 0; i < listAction.size(); i++) {
				sheet.setColumnWidth(count, 12 * 256);
				count = count + 1;
			}
		}
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		tableCreat(wb, listAction, row);
		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		addInfo(listAction, sheet, listScoreOaBeans);
		// 第六步，将文件存到指定位置

		String fileName = "attendanceSummary.xls";

		OutputStream os = response.getOutputStream();// 取得输出流
		response.reset();// 清空输出流
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));
		response.setContentType("application/msexcel");
		wb.write(os);
		os.close();

	}

	/**
	 * 创建单元格，并设置值表头 设置表头居中
	 * 
	 * @param listAction
	 * @param row
	 * @param sheet
	 * @param listScoreOaBeans
	 */
	private void addInfo(List<String> listAction, HSSFSheet sheet,
			List<ScoreOaBean> listScoreOaBeans) {
		for (int i = 0; i < listScoreOaBeans.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			ScoreOaBean scoreOaBean = listScoreOaBeans.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell(0).setCellValue(scoreOaBean.getAccount());
			row.createCell(1).setCellValue(scoreOaBean.getScore());
			if (listAction != null && !listAction.isEmpty()) {
				int count = 2;
				for (int j = 0; j < scoreOaBean.getHdScore().size(); j++) {
					row.createCell(count).setCellValue(
							scoreOaBean.getHdScore().get(j));
					count = count + 1;
				}
			}
		}
	}

	/**
	 * ，写入实体数据 实际应用中这些数据从数据库得到，
	 * 
	 * @param wb
	 * @param listAction
	 * @param row
	 */
	private void tableCreat(HSSFWorkbook wb, List<String> listAction,
			HSSFRow row) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		ArrayList<String> cellList = new ArrayList<>();
		cellList.add("账户");
		cellList.add("积分");
		// 设定表头
		if (listAction != null && !listAction.isEmpty()) {
			for (int i = 0; i < listAction.size(); i++) {
				cellList.add(listAction.get(i));
			}
		}

		for (int i = 0; i < cellList.size(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(cellList.get(i));
			cell.setCellStyle(style);
		}
	}

	/***
	 * 
	 * @param account
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	private List<ScoreOaBean> getScoreOaBeansNoPage(String account,
			SqlSession sqlSession) {
		List<ScoreOaBean> list = new ArrayList<>();
		// 获取所有积分信息
		List<ScoreBean> listScoreBeans = scoreTempBll
				.getScoreBeanas(sqlSession);
		// 筛选账户并组建返回实体
		if (listScoreBeans != null && !listScoreBeans.isEmpty()) {
			for (int i = 0; i < listScoreBeans.size(); i++) {
				ScoreBean scoreBean = listScoreBeans.get(i);
				list = isAccount(list, scoreBean, account);
			}
		}
		// 增加分数信息
		list = getScoreOaBeansHdScore(list, sqlSession);
		return list;
	}
}
