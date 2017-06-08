package cn.gyyx.action.service.wdtenyearfans;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wdtenyearfans.AccountVoteCountBean;
import cn.gyyx.action.beans.wdtenyearfans.AccountVoteScoreBean;
import cn.gyyx.action.beans.wdtenyearfans.PageBean;
import cn.gyyx.action.beans.wdtenyearfans.WdAccountInfoBean;
import cn.gyyx.action.beans.wdtenyearfans.WdAccountScoreBean;
import cn.gyyx.action.beans.wdtenyearfans.WdCommentsBean;
import cn.gyyx.action.beans.wdtenyearfans.WdNominationBackBean;
import cn.gyyx.action.beans.wdtenyearfans.WdNominationInfoBean;
import cn.gyyx.action.bll.wdtenyearfans.NominatedBLL;
import cn.gyyx.action.bll.wdtenyearfans.WDBackstage;
import cn.gyyx.action.bll.wdtenyearfans.WdNominationInfoBll;
import cn.gyyx.action.bll.wdtenyearfans.WdScoreBLL;
import cn.gyyx.action.bll.wdtenyearfans.WdVoteInfoBll;
import cn.gyyx.action.service.WdTenYearService;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class WDBackstageService {
	private WDBackstage wDBackstage = new WDBackstage();
	private WdVoteInfoBll wdVoteInfoBll = new WdVoteInfoBll();
	private WdScoreBLL wdScoreBLL = new WdScoreBLL();
	private NominatedBLL nominatedBLL = new NominatedBLL();
	private WdNominationInfoBll wdNominationInfoBll = new WdNominationInfoBll();
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WDBackstageService.class);

	/**
	 * @Title batchShow
	 * @Description 批量显示评论
	 * @return
	 */
	public ResultBean<String> commentsShow(int[] codes) {
		ResultBean<String> result = new ResultBean<String>();
		WdCommentsBean commentBean = new WdCommentsBean();
		if (codes == null || codes.length == 0) {
			result.setProperties(false, "参数有误", "修改失败");
			return result;
		}
		for (int i = 0; i < codes.length; i++) {
			commentBean = wDBackstage.getWdCommentsBeanBycode(codes[i]);
			commentBean.setCheckFlag(1);
			wDBackstage.updateFlag(commentBean);
		}
		result.setProperties(true, "批量展示成功", "修改成功");
		return result;
	}

	/**
	 * @Title batchShow
	 * @Description 批量删除评论
	 * @return
	 */
	ResultBean<String> result = new ResultBean<String>();

	
		public ResultBean<String> commentsDelete(int[] codes){
			ResultBean<String> result = new ResultBean<String>();
		WdCommentsBean commentBean = new WdCommentsBean();
		if (codes == null || codes.length == 0) {
			result.setProperties(false, "参数有误", "修改失败");
			return result;
		}
		for (int i = 0; i < codes.length; i++) {
			commentBean = wDBackstage.getWdCommentsBeanBycode(codes[i]);
			commentBean.setCheckFlag(2);
			wDBackstage.updateFlag(commentBean);
		}
		result.setProperties(true, "批量删除成功", "修改成功");
		return result;
	}

	/**
	 * @Title batchShow
	 * @Description 批量隐藏评论
	 * @return
	 */

	public ResultBean<String> commentsHide(int[] codes) {
		ResultBean<String> result = new ResultBean<String>();
		WdCommentsBean commentBean = new WdCommentsBean();
		if (codes == null || codes.length == 0) {
			result.setProperties(false, "参数有误", "修改失败");
			return result;
		}
		for (int i = 0; i < codes.length; i++) {
			commentBean = wDBackstage.getWdCommentsBeanBycode(codes[i]);
			commentBean.setCheckFlag(-1);
			wDBackstage.updateFlag(commentBean);
		}
		result.setProperties(true, "批量隐藏成功", "修改成功");
		return result;
	}

	/**
	 * @Title NominationInfoBeanShow
	 * @Description 批量显示提名
	 * @return
	 */
	public ResultBean<String> nominationInfoBeanShow(int[] codes) {
		ResultBean<String> result = new ResultBean<String>();
		WdNominationInfoBean nominationInfoBean = new WdNominationInfoBean();
		if (codes == null || codes.length == 0) {
			result.setProperties(false, "参数有误", "修改失败");
			return result;
		}
		for (int i = 0; i < codes.length; i++) {
			nominationInfoBean = wDBackstage
					.getWdNominationInfoByCode(codes[i]);
			if (nominationInfoBean.getAuditStatus() != 1) {
				nominationInfoBean.setAuditStatus(1);
				wDBackstage.updateAuditStatus(nominationInfoBean);

				String accountName = nominationInfoBean.getAccountName();
				WdAccountScoreBean wdAccountScoreBean = wDBackstage
						.selectWdAccountScoreBeanByAccount(accountName);
				wdAccountScoreBean.setScore(wdAccountScoreBean.getScore() + 30);
				wDBackstage.updateWdAccountScoreBean(wdAccountScoreBean);
				WdTenYearService.setWdtenYearScore(30,
						nominationInfoBean.getUserID(), 346,
						nominationInfoBean.getAccountName(),
						nominationInfoBean.getNominatedIp());
			}
		}
		result.setProperties(true, "批量展示成功", "修改成功");
		return result;
	}

	/**
	 * @Title nominationInfoBeanDelete
	 * @Description 批量删除提名
	 * @return
	 */
	public ResultBean<String> nominationInfoBeanDelete(int[] codes) {
		ResultBean<String> result = new ResultBean<String>();
		WdNominationInfoBean nominationInfoBean = new WdNominationInfoBean();
		if (codes == null || codes.length == 0) {
			result.setProperties(false, "参数有误", "修改失败");
			return result;
		}
		for (int i = 0; i < codes.length; i++) {
			nominationInfoBean = wDBackstage
					.getWdNominationInfoByCode(codes[i]);
			if (nominationInfoBean.getAuditStatus() == 1) {
				nominationInfoBean.setAuditStatus(2);
				wDBackstage.updateAuditStatus(nominationInfoBean);

				String accountName = nominationInfoBean.getAccountName();
				WdAccountScoreBean wdAccountScoreBean = wDBackstage
						.selectWdAccountScoreBeanByAccount(accountName);
				wdAccountScoreBean.setScore(wdAccountScoreBean.getScore() - 30);
				wDBackstage.updateWdAccountScoreBean(wdAccountScoreBean);
				WdTenYearService.setWdtenYearScore(-30,
						nominationInfoBean.getUserID(), 346,
						nominationInfoBean.getAccountName(),
						nominationInfoBean.getNominatedIp());
			} else {
				nominationInfoBean.setAuditStatus(2);
				wDBackstage.updateAuditStatus(nominationInfoBean);
			}
		}
		result.setProperties(true, "批量删除成功", "修改成功");
		return result;
	}

	/**
	 * 批量隐藏提名
	 * 
	 * @param codes
	 * @return
	 */
	public ResultBean<String> nominationInfoBeanHide(int[] codes) {
		ResultBean<String> result = new ResultBean<String>();
		WdNominationInfoBean nominationInfoBean = new WdNominationInfoBean();
		if (codes == null || codes.length == 0) {
			result.setProperties(false, "参数有误", "修改失败");
			return result;
		}
		for (int i = 0; i < codes.length; i++) {
			nominationInfoBean = wDBackstage
					.getWdNominationInfoByCode(codes[i]);
			if (nominationInfoBean.getAuditStatus() == 1) {
				nominationInfoBean.setAuditStatus(-1);
				wDBackstage.updateAuditStatus(nominationInfoBean);

				String accountName = nominationInfoBean.getAccountName();
				WdAccountScoreBean wdAccountScoreBean = wDBackstage
						.selectWdAccountScoreBeanByAccount(accountName);
				wdAccountScoreBean.setScore(wdAccountScoreBean.getScore() - 30);
				wDBackstage.updateWdAccountScoreBean(wdAccountScoreBean);
				WdTenYearService.setWdtenYearScore(-30,
						nominationInfoBean.getUserID(), 346,
						nominationInfoBean.getAccountName(),
						nominationInfoBean.getNominatedIp());
			} else {
				nominationInfoBean.setAuditStatus(-1);
				wDBackstage.updateAuditStatus(nominationInfoBean);
			}
		}
		result.setProperties(true, "批量隐藏成功", "修改成功");
		return result;
	}

	/**
	 * 查询投票账号数量
	 * 
	 * @return
	 */
	public ResultBean<Integer> getVoteAccountNum(String accountName) {
		ResultBean<Integer> result = new ResultBean<Integer>(false, "", 0);
		if (wdVoteInfoBll.getVoteAccountNum(accountName) == 0) {
			result.setProperties(true, "", 0);
			logger.info("result:" + result);
			return result;
		} else {
			result.setProperties(true, "",
					wdVoteInfoBll.getVoteAccountNum(accountName));
			logger.info("result:" + result);
			return result;
		}
	}

	/**
	 * 查询投票IP数量
	 * 
	 * @return
	 */
	public ResultBean<Integer> getVoteIpNum() {
		ResultBean<Integer> result = new ResultBean<Integer>(false, "", 0);
		if (wdVoteInfoBll.getVoteIpNum() == 0) {
			result.setProperties(true, "", 0);
			logger.info("result:" + result);
			return result;
		} else {
			result.setProperties(true, "", wdVoteInfoBll.getVoteIpNum());
			logger.info("result:" + result);
			return result;
		}
	}

	/**
	 * 查询提名账号数量
	 * 
	 * @return
	 */
	public ResultBean<Integer> getNominationAccountNum(String accountName) {
		ResultBean<Integer> result = new ResultBean<Integer>(false, "", 0);
		if (nominatedBLL.getNominationAccountNum(accountName) == 0) {
			result.setProperties(true, "", 0);
			logger.info("result:" + result);
			return result;
		} else {
			result.setProperties(true, "",
					nominatedBLL.getNominationAccountNum(accountName));
			logger.info("result:" + result);
			return result;
		}
	}

	/**
	 * 查询提名IP数量
	 * 
	 * @return
	 */
	public ResultBean<Integer> getNominationIpNum() {
		ResultBean<Integer> result = new ResultBean<Integer>(false, "", 0);
		if (nominatedBLL.getNominationIpNum() == 0) {
			result.setProperties(true, "", 0);
			logger.info("result:" + result);
			return result;
		} else {
			result.setProperties(true, "", nominatedBLL.getNominationIpNum());
			logger.info("result:" + result);
			return result;
		}
	}

	/**
	 * 投票后台信息
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @param accountName
	 * @return
	 */
	public List<AccountVoteScoreBean> getAccountVoteScoreShow(int pageSize,
			int pageNo, String accountName) {
		// 查询投票的角色
		List<String> accountList = wdVoteInfoBll.getVoteAccount(pageSize,
				pageNo, accountName);
		// 拼装组合出展示信息
		List<AccountVoteScoreBean> list = getVoteScoreShowBeans(accountList);
		return list;
	}

	/**
	 * 拼装投票后台展示信息
	 * 
	 * @param accountList
	 * @return
	 */
	public List<AccountVoteScoreBean> getVoteScoreShowBeans(
			List<String> accountList) {
		List<AccountVoteScoreBean> list = new ArrayList<>();

		// 非空判断
		if (accountList != null && !accountList.isEmpty()) {
			// 遍历信息
			for (int i = 0; i < accountList.size(); i++) {
				// 取出实体
				String account = accountList.get(i);
				// 声明展示实体
				AccountVoteScoreBean accountVoteScoreBean = new AccountVoteScoreBean();
				// 账号
				accountVoteScoreBean.setAccountName(account);
				// 投票次数
				accountVoteScoreBean.setVoteNum(wdVoteInfoBll
						.getVoteNumByAccount(account));
				// 积分
				WdAccountScoreBean wdAccountScoreBean = wdScoreBLL
						.getWdAccountScoreBeanByAccountName(account);
				if (wdAccountScoreBean == null) {
					accountVoteScoreBean.setScore(0);
				} else {
					accountVoteScoreBean
							.setScore(wdAccountScoreBean.getScore());
				}
				logger.debug(accountVoteScoreBean.toString());
				list.add(accountVoteScoreBean);
			}
		}
		return list;
	}

	/**
	 * 投票后台信息导出
	 * 
	 * @param response
	 * @param list
	 * @throws IOException
	 */
	public void extendVoteExcel(HttpServletResponse response,
			List<AccountVoteScoreBean> list) throws IOException {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("投票信息列表");

		sheet.setColumnWidth(0, 20 * 256);
		sheet.setColumnWidth(1, 10 * 256);
		sheet.setColumnWidth(2, 20 * 256);

		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		ArrayList<String> cellList = new ArrayList<String>();
		cellList.add("账号");
		cellList.add("积分");
		cellList.add("投票次数（总）");

		HSSFCell cell = row.createCell(0);
		for (int i = 0; i < cellList.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(cellList.get(i));
			cell.setCellStyle(style);
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			AccountVoteScoreBean accountVoteScoreBean = list.get(i);

			// 第四步，创建单元格，并设置值
			row.createCell(0).setCellValue(
					accountVoteScoreBean.getAccountName());
			row.createCell(1).setCellValue(accountVoteScoreBean.getScore());
			row.createCell(2).setCellValue(accountVoteScoreBean.getVoteNum());
		}

		// 第六步，将文件存到指定位置
		String fileName = "voteInfo.xls";
		// 取得输出流
		OutputStream os = response.getOutputStream();
		// 清空输出流
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));
		response.setContentType("application/msexcel");
		wb.write(os);
		os.close();
	}

	/**
	 * 拼装提名后台展示信息
	 * 
	 * @param accountList
	 * @return
	 */
	public List<WdNominationBackBean> getNominationBackShowBeans(
			List<String> accountList) {
		List<WdNominationBackBean> list = new ArrayList<>();

		// 非空判断
		if (accountList != null && !accountList.isEmpty()) {
			// 遍历信息
			for (int i = 0; i < accountList.size(); i++) {
				// 取出实体
				String account = accountList.get(i);
				// 声明展示实体
				WdNominationBackBean wdNominationBackBean = new WdNominationBackBean();
				// 被提名者区组
				WdNominationInfoBean wdNominationInfoBean = nominatedBLL
						.getNominationInfo(account);
				wdNominationBackBean
						.setNominatedServerName(wdNominationInfoBean
								.getNominatedServerName());
				// 被提名者角色
				wdNominationBackBean.setNominatedRole(wdNominationInfoBean
						.getNominatedRole());
				// 提名者账号
				wdNominationBackBean.setAccountName(account);
				// 提名者区组
				WdAccountInfoBean wdAccountInfoBean = nominatedBLL
						.getAccountInfoByAccountName(account);
				wdNominationBackBean.setAccountServerName(wdAccountInfoBean
						.getServerName());
				// 提名者角色
				wdNominationBackBean.setAccountRole(wdAccountInfoBean
						.getRoleName());
				// 当前粉丝数
				wdNominationBackBean.setVoteWhite(wdNominationInfoBean
						.getVoteWhite());
				// 粉丝数排名
				wdNominationBackBean.setWhiteRanking(wdNominationInfoBll
						.getWhiteOrderWhenSameOrder(
								wdNominationInfoBean.getVoteWhiteDate(),
								wdNominationInfoBean.getVoteWhite())
						+ wdNominationInfoBll
								.getWhiteNumCount(wdNominationInfoBean
										.getCode()) + 1);
				// 当前黑粉数
				wdNominationBackBean.setVoteBlack(wdNominationInfoBean
						.getVoteBlack());
				// 黑粉排名
				wdNominationBackBean.setBlackRanking(wdNominationInfoBll
						.getBlackOrderWhenSameOrder(
								wdNominationInfoBean.getVoteBlackDate(),
								wdNominationInfoBean.getVoteBlack())
						+ wdNominationInfoBll
								.getBlackNumCount(wdNominationInfoBean
										.getCode()) + 1);
				logger.debug(wdNominationBackBean.toString());
				list.add(wdNominationBackBean);
			}
		}
		return list;
	}

	/**
	 * 提名后台信息
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @param accountName
	 * @return
	 */
	public List<WdNominationBackBean> getNomanitionBeansShow(int pageSize,
			int pageNo, String accountName) {
		// 查询提名的角色
		List<String> accountList = nominatedBLL.getNominationAccount(pageSize,
				pageNo, accountName);
		// 拼装组合出展示信息
		List<WdNominationBackBean> list = getNominationBackShowBeans(accountList);
		return list;
	}

	/**
	 * 提名后台信息导出
	 * 
	 * @param response
	 * @param list
	 * @throws IOException
	 */
	public void extendNomanitionExcel(HttpServletResponse response,
			List<WdNominationBackBean> list) throws IOException {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("投票信息列表");

		sheet.setColumnWidth(0, 20 * 256);
		sheet.setColumnWidth(1, 20 * 256);
		sheet.setColumnWidth(2, 20 * 256);
		sheet.setColumnWidth(3, 20 * 256);
		sheet.setColumnWidth(4, 20 * 256);
		sheet.setColumnWidth(5, 20 * 256);
		sheet.setColumnWidth(6, 20 * 256);
		sheet.setColumnWidth(7, 20 * 256);
		sheet.setColumnWidth(8, 20 * 256);

		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		ArrayList<String> cellList = new ArrayList<String>();
		cellList.add("被提名者区组");
		cellList.add("被提名者角色");
		cellList.add("提名者账号");
		cellList.add("提名者区组");
		cellList.add("提名者角色");
		cellList.add("当前粉丝数");
		cellList.add("粉丝数排名");
		cellList.add("当前黑粉数");
		cellList.add("黑粉排名");

		HSSFCell cell = row.createCell(0);
		for (int i = 0; i < cellList.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(cellList.get(i));
			cell.setCellStyle(style);
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			WdNominationBackBean wdNominationBackBean = list.get(i);

			// 第四步，创建单元格，并设置值
			row.createCell(0).setCellValue(
					wdNominationBackBean.getNominatedServerName());
			row.createCell(1).setCellValue(
					wdNominationBackBean.getNominatedRole());
			row.createCell(2).setCellValue(
					wdNominationBackBean.getAccountName());
			row.createCell(3).setCellValue(
					wdNominationBackBean.getAccountServerName());
			row.createCell(4).setCellValue(
					wdNominationBackBean.getAccountRole());
			row.createCell(5).setCellValue(wdNominationBackBean.getVoteWhite());
			row.createCell(6).setCellValue(
					wdNominationBackBean.getWhiteRanking());
			row.createCell(7).setCellValue(wdNominationBackBean.getVoteBlack());
			row.createCell(8).setCellValue(
					wdNominationBackBean.getBlackRanking());
		}

		// 第六步，将文件存到指定位置
		String fileName = "nomanitionInfo.xls";
		// 取得输出流
		OutputStream os = response.getOutputStream();
		// 清空输出流
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));
		response.setContentType("application/msexcel");
		wb.write(os);
		os.close();
	}

	/**
	 * 拼装统计后台展示信息
	 * 
	 * @param accountList
	 * @return
	 */
	public List<AccountVoteCountBean> getCountShowBeans(
			List<String> accountList, PageBean pageBean) {
		List<AccountVoteCountBean> list = new ArrayList<>();

		// 非空判断
		if (accountList != null && !accountList.isEmpty()) {
			// 遍历信息
			for (int i = 0; i < accountList.size(); i++) {
				// 取出实体
				String account = accountList.get(i);
				pageBean.setAccountName(account);
				// 声明展示实体
				AccountVoteCountBean accountVoteCountBean = new AccountVoteCountBean();
				// 账号
				accountVoteCountBean.setAccountName(account);
				// 关注数量
				accountVoteCountBean.setWhiteNum(wDBackstage
						.getWhiteNumByAccountDate(pageBean));
				// 拉黑数量
				accountVoteCountBean.setBlackNum(wDBackstage
						.getBlackNumByAccountDate(pageBean));
				// 每日参加为自己提名账号数
				accountVoteCountBean.setNomaForMe(wDBackstage
						.getNomanitionCountForMe(pageBean));
				// 每日参加为别人提名账号数
				accountVoteCountBean.setNomaForHe(wDBackstage
						.getNomanitionCountForHe(pageBean));
				// 每日参加关注账号数
				accountVoteCountBean.setJoinWhiteNum(wDBackstage
						.getAccountNumByWhite(pageBean));
				// 每日参加拉黑账号数
				accountVoteCountBean.setJoinBlackNum(wDBackstage
						.getAccountNumByBlack(pageBean));
				// 每日参加投票账号数
				accountVoteCountBean.setJoinVoteNum(wDBackstage
						.getAccountNumByVote(pageBean));
				logger.debug(accountVoteCountBean.toString());
				list.add(accountVoteCountBean);
			}
		}
		return list;
	}

	/**
	 * 统计后台信息
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @param accountName
	 * @return
	 */
	public List<AccountVoteCountBean> getCountBeansShow(PageBean pageBean) {
		// 查询提名的角色
		List<String> accountList = wDBackstage.selectAllVoteAccount(pageBean);
		// 拼装组合出展示信息
		List<AccountVoteCountBean> list = getCountShowBeans(accountList,
				pageBean);
		return list;
	}

}
