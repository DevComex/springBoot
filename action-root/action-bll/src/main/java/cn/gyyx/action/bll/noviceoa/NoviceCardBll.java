package cn.gyyx.action.bll.noviceoa;

import cn.gyyx.action.beans.noviceoa.*;
import cn.gyyx.action.dao.noviceoa.NoviceBatchDao;
import cn.gyyx.action.dao.noviceoa.NoviceCardDao;
import cn.gyyx.action.dao.noviceoa.NoviceGiftDao;
import cn.gyyx.action.dao.noviceoa.NoviceGiftGenerateDao;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoviceCardBll {

	NoviceCardDao noviceCardDao = new NoviceCardDao();
	NoviceBatchDao noviceBatchDao = new NoviceBatchDao();
	NoviceGiftGenerateDao noviceGiftGenerateDao = new NoviceGiftGenerateDao();
	NoviceGiftDao noviceGiftDao = new NoviceGiftDao();

	// 卡号编号基准值
	private int BASE_CARD_NUMBER = 0x7ED2978;
	// 卡号批次基准值
	private int BASE_CARD_HEADER = 0x159F;
	// 卡号生成校验key
	private String BASE_CARD_KEY = "520A520";

	/**
	 * 生成新的起始卡号
	 * 
	 * @param cardHeader
	 * @return
	 */
	public String getNewBeginCard(int cardHeader) {
		int newCard = cardHeader + BASE_CARD_HEADER;
		return this.createNoviceCard(Integer.toHexString(newCard) + BASE_CARD_NUMBER);
	}

	/**
	 * 生成新手卡插入SQL(10个一批)
	 * 
	 * @param tableName
	 * @param newBeginCardNo
	 * @param count
	 * @param giftId
	 * @return
	 */
	public List<String> GetBatchCodesList(String tableName, String newBeginCardNo, int count, Integer giftId) {
		List<String> batchCodesList = new ArrayList<String>();

		StringBuilder sbHeader = new StringBuilder();
		sbHeader.append(String.format("insert into %s ", tableName));
		sbHeader.append("(card_num, description, gift_id, status,task_id,update_time,create_time) values");

		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= count; i++) {
			String nextCardNo = this.createNoviceCard(newBeginCardNo);
			sb.append("(");
			sb.append("'" + nextCardNo + "',");
			sb.append("'',");
			sb.append(giftId + ",");
			sb.append("0,");
			sb.append("##,");

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateNow = dateFormat.format(new Date());

			sb.append("'" + dateNow + "',");
			sb.append("'" + dateNow + "'),");
			if (i % Constant.Insert_Novice_Card_Batch_Count == 0 || i == count) {
				String sql = sbHeader.toString() + sb.toString();
				batchCodesList.add(sql.toString().substring(0, sql.toString().length() - 1));
				sb = new StringBuilder();
			}
			newBeginCardNo = nextCardNo;
		}
		return batchCodesList;
	}

	/**
	 * 根据TaskId获取新手卡号列表
	 * @param batchId
	 * @param taskId
	 * @return
	 */
	public List<NoviceCardBean> getNoviceCardListByTaskId(int batchId, int taskId) {
		String tbName = this.getNoviceCardTableName(batchId);
		return noviceCardDao.getNoviceCardListByTaskId(tbName, taskId);
	}

	/**
	 * 根据批次获取新手卡号表名
	 * 
	 * @param batchId
	 * @return
	 */
	public String getNoviceCardTableName(int batchId) {
		return "novice_card_" + batchId + "_tb";
	}

	public SXSSFWorkbook exportNoviceCard(int batchId, int taskId, String channel) {
		SXSSFWorkbook wb = new SXSSFWorkbook(Constant.Flush_Excel_To_Disk_When_Count);
		// 建立新的sheet对象
		Sheet sh = wb.createSheet("新手卡号表");
		sh.setColumnWidth(0, 10 * 450);
		sh.setColumnWidth(1, 10 * 350);
		sh.setColumnWidth(2, 20 * 256);
		sh.setColumnWidth(3, 20 * 256);
		sh.setColumnWidth(4, 20 * 256);
		sh.setColumnWidth(5, 20 * 256);

		// 创建第一行对象
		Row row = sh.createRow(0);
		Cell cel0 = row.createCell(0);
		cel0.setCellValue("卡号");
		Cell cel1 = row.createCell(1);
		cel1.setCellValue("批次编号");
		Cell cel2 = row.createCell(2);
		cel2.setCellValue("批次名称");
		Cell cel3 = row.createCell(3);
		cel3.setCellValue("礼包名称");
		Cell cel4 = row.createCell(4);
		cel4.setCellValue("渠道");
		Cell cel5 = row.createCell(5);
		cel5.setCellValue("创建时间");

		// 查询要导出的数据
		List<NoviceCardBean> list = this.getNoviceCardListByTaskId(batchId, taskId);
		NoviceGiftGenerateBean giftGenerateBean = noviceGiftGenerateDao.selectByPrimaryKey(taskId);
		NoviceGiftBean giftBean = noviceGiftDao.selectBeanByCode(giftGenerateBean.getGiftId());
		NoviceBatchBean batchBean = noviceBatchDao.selectBeanByCode(batchId);

		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < list.size(); i++) {
			row = sh.createRow(i + 1);
			NoviceCardBean noviceCardInfo = list.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell(0).setCellValue(noviceCardInfo.getCardNum());
			row.createCell(1).setCellValue(batchId);
			row.createCell(2).setCellValue(batchBean.getName());
			row.createCell(3).setCellValue(giftBean.getGiftName());
			row.createCell(4).setCellValue(channel);
			row.createCell(5).setCellValue(format2.format(noviceCardInfo.getCreateTime()));
		}
		return wb;
	}

	/**
	 * 校验卡号
	 * 
	 * @param card
	 * @return
	 */
	public boolean checkNoviceCard(String card) {
		String waitCheckCard = BASE_CARD_KEY + card;

		char[] cardArr = waitCheckCard.toCharArray();
		char[] checkCardArr = new char[cardArr.length - 1];

		System.arraycopy(cardArr, 0, checkCardArr, 0, cardArr.length - 1);

		int temp = Integer.parseInt(String.valueOf(checkCardArr[0]), 16);

		for (char i = 1; i < checkCardArr.length; i++) {
			temp = temp ^ Integer.parseInt(String.valueOf(checkCardArr[i]), 16);
		}

		return temp == Integer.parseInt(String.valueOf(cardArr[cardArr.length - 1]), 16);
	}

	/**
	 * 生成卡号
	 * 
	 * @param baseCard
	 * @return
	 */
	public String createNoviceCard(String baseCard) {
		int baseCardNum = Integer.parseInt(baseCard.substring(4, 11), 16);
		int cardNum = baseCardNum + 0x1;

		int cardHeader = Integer.parseInt(baseCard.substring(0, 4), 16);

		String waitCheckCard = Integer.toHexString(cardHeader) + Integer.toHexString(cardNum);

		char[] cardArr = (BASE_CARD_KEY + waitCheckCard).toCharArray();

		int temp = Integer.parseInt(String.valueOf(cardArr[0]), 16);
		for (char i = 1; i < cardArr.length; i++) {
			temp = temp ^ Integer.parseInt(String.valueOf(cardArr[i]), 16);
		}

		String result = waitCheckCard + Integer.toHexString(temp);
		return result.toUpperCase();
	}

	/**
	 * 获取卡号编号
	 * 
	 * @param card
	 * @return
	 */
	public int getCardNumber(String card) {
		int baseCardNum = Integer.parseInt(card.substring(4, 11), 16);
		int cardNum = baseCardNum - BASE_CARD_NUMBER;
		return cardNum;
	}

	/**
	 * 获取批次编号
	 * 
	 * @param card
	 * @return
	 */
	private int getCardBatch(String card) {
		int baseCardNum = Integer.parseInt(card.substring(0, 4), 16);
		int batchNum = BASE_CARD_HEADER - baseCardNum;
		return batchNum;
	}
}
