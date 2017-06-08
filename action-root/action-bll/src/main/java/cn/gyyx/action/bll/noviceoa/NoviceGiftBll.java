/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/2/24 17:13
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.bll.noviceoa;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.noviceoa.NoviceBatchBean;
import cn.gyyx.action.beans.noviceoa.NoviceGiftBean;
import cn.gyyx.action.beans.noviceoa.NoviceGiftGenerateBean;
import cn.gyyx.action.beans.noviceoa.NoviceGiftGenerateInfoBean;
import cn.gyyx.action.dao.noviceoa.NoviceBatchDao;
import cn.gyyx.action.dao.noviceoa.NoviceCardDao;
import cn.gyyx.action.dao.noviceoa.NoviceGiftDao;
import cn.gyyx.action.dao.noviceoa.NoviceGiftGenerateDao;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 描述:
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class NoviceGiftBll {
	NoviceGiftDao noviceGiftDao = new NoviceGiftDao();
	NoviceBatchDao noviceBatchDao = new NoviceBatchDao();
	NoviceGiftGenerateDao noviceGiftGenerateDao = new NoviceGiftGenerateDao();
	NoviceCardDao noviceCardDao = new NoviceCardDao();
	NoviceCardBll noviceCardBll = new NoviceCardBll();

	/**
	 * 添加礼包
	 *
	 * @param bean
	 * @return
	 */
	public ResultBean<Integer> insert(NoviceGiftBean bean) {
		ResultBean<Integer> resultBean = new ResultBean<>();

		ResultBean<String> baseCheckResult = baseGiftInfoCheck(bean);

		if (!baseCheckResult.getIsSuccess()) {
			resultBean.setIsSuccess(false);
			resultBean.setMessage(baseCheckResult.getMessage());
			return resultBean;
		}

		int gitId = noviceGiftDao.insert(bean);
		resultBean.setIsSuccess(true);
		resultBean.setData(gitId);

		return resultBean;
	}

	/**
	 * 更新礼包
	 *
	 * @param bean
	 * @return
	 */
	public ResultBean<String> update(NoviceGiftBean bean) {
		ResultBean<String> resultBean = new ResultBean<>();
		NoviceGiftBean info = noviceGiftDao.selectBeanByCode(bean.getCode());
		if (info == null) {
			resultBean.setIsSuccess(false);
			resultBean.setMessage("更新的记录不存在！");
			return resultBean;
		}
		if (!info.getGiftName().equals(bean.getGiftName())) {
			NoviceGiftBean giftNameBean = selectBeanByGiftName(bean.getBatchId(), bean.getGiftName());
			if (giftNameBean != null) {
				resultBean.setIsSuccess(false);
				resultBean.setMessage("已存在相同名礼包！");
				return resultBean;
			}
		}
		if (!info.getGiftCode().equals(bean.getGiftCode())) {
			NoviceGiftBean giftCodeBean = selectBeanByGiftCode(bean.getBatchId(), bean.getGiftCode());
			if (giftCodeBean != null) {
				resultBean.setIsSuccess(false);
				resultBean.setMessage("已存在相同礼包编码！");
				return resultBean;
			}
		}
		info.setGiftName(bean.getGiftName());
		info.setgiftCode(bean.getGiftCode());
		info.setIsDefault(bean.getIsDefault());
		boolean updateResult = noviceGiftDao.update(info);
		if (updateResult) {
			resultBean.setIsSuccess(updateResult);
			resultBean.setMessage("更新成功");
			return resultBean;
		} else {
			resultBean.setIsSuccess(updateResult);
			resultBean.setMessage("更新失败");
			return resultBean;
		}

	}

	private ResultBean<String> baseGiftInfoCheck(NoviceGiftBean bean) {
		ResultBean<String> resultBean = new ResultBean<>();

		NoviceBatchBean noviceBatchBean = noviceBatchDao.selectBeanByCode(bean.getBatchId());
		if (noviceBatchBean == null) {
			resultBean.setIsSuccess(false);
			resultBean.setMessage("批次信息不存在！");
			return resultBean;
		}

		NoviceGiftBean giftNameBean = selectBeanByGiftName(bean.getBatchId(), bean.getGiftName());
		if (giftNameBean != null) {
			resultBean.setIsSuccess(false);
			resultBean.setMessage("已存在相同名礼包！");
			return resultBean;
		}

		NoviceGiftBean giftCodeBean = selectBeanByGiftCode(bean.getBatchId(), bean.getGiftCode());
		if (giftCodeBean != null) {
			resultBean.setIsSuccess(false);
			resultBean.setMessage("已存在相同礼包编码！");
			return resultBean;
		}

		resultBean.setIsSuccess(true);

		return resultBean;
	}

	/**
	 * 根据礼包发奖编号获取礼包
	 *
	 * @param batchId
	 * @param giftCode
	 * @return
	 */
	public NoviceGiftBean selectBeanByGiftCode(int batchId, String giftCode) {
		return noviceGiftDao.selectBeanByGiftCode(batchId, giftCode);
	}

	/**
	 * 根据礼包名获取礼包
	 *
	 * @param batchId
	 * @param giftName
	 * @return
	 */
	public NoviceGiftBean selectBeanByGiftName(int batchId, String giftName) {
		return noviceGiftDao.selectBeanByGiftName(batchId, giftName);
	}

	/**
	 * 根据礼包主键获取礼包
	 *
	 * @param code
	 * @return
	 */
	public NoviceGiftBean selectBeanByCode(int code) {
		return noviceGiftDao.selectBeanByCode(code);
	}

	/**
	 * 根据批次获取礼包列表
	 *
	 * @param batchId
	 * @return
	 */
	public List<NoviceGiftBean> selectListByBatchId(int batchId) {
		return noviceGiftDao.selectListByBatchId(batchId);
	}

	/**
	 * 删除礼包信息
	 * 
	 * @param code
	 * @return
	 */
	public boolean deleteGiftInfo(int code) {
		return noviceGiftDao.delete(code);
	}

	/**
	 * 根据批号获取新手卡礼包生成记录
	 * 
	 * @param batchId
	 * @return
	 */
	public List<NoviceGiftGenerateInfoBean> selectNoviceGiftGenerateList(int batchId) {
		return noviceGiftGenerateDao.selectByBatchId(batchId);
	}

	/**
	 * 更新新手卡礼包渠道
	 *
	 * @param bean
	 * @return
	 */
	public ResultBean<String> noviceGiftUpdate(NoviceGiftGenerateBean bean) {
		ResultBean<String> resultBean = new ResultBean<>();
		NoviceGiftGenerateBean noviceGiftInfo = noviceGiftGenerateDao.selectByPrimaryKey(bean.getCode());
		if (noviceGiftInfo == null) {
			resultBean.setIsSuccess(false);
			resultBean.setMessage("更新的记录不存在！");
			return resultBean;
		}

		noviceGiftInfo.setBeginDate(bean.getBeginDate());
		noviceGiftInfo.setEndDate(bean.getEndDate());
		noviceGiftInfo.setChannel(bean.getChannel());
		noviceGiftInfo.setDescription(bean.getDescription());
		noviceGiftInfo.setGiftId(bean.getGiftId());
		noviceGiftInfo.setIsDelete(bean.getIsDelete());
		int updateResult = noviceGiftGenerateDao.updateByPrimaryKeySelective(noviceGiftInfo);
		if (updateResult > 0) {
			resultBean.setIsSuccess(true);
			resultBean.setMessage("更新成功");
			return resultBean;
		} else {
			resultBean.setIsSuccess(false);
			resultBean.setMessage("更新失败");
			return resultBean;
		}
	}

	/**
	 * 添加渠道新手卡礼包
	 *
	 * @param bean
	 * @return
	 */
	public ResultBean<String> noviceGiftAdd(NoviceGiftGenerateBean bean) {
		ResultBean<String> resultBean = new ResultBean<>();

		// 判断批次下渠道是否已生成过礼包(同一批次下统一渠道只能有一个礼包生成记录)
		resultBean = isChannelGiftHasGenerate(bean.getBatchId(), bean.getChannel(), bean.getGiftId());
		if (!resultBean.getIsSuccess()) {
			return resultBean;
		}

		String newBeginCardNo;
		String tName = noviceCardBll.getNoviceCardTableName(bean.getBatchId());
		List<String> batchCodesList = new ArrayList<String>();
		// 查看该批次的新手卡表是否存在
		String result = noviceCardDao.existTable(tName);
		if (result == null) {
			resultBean.setIsSuccess(false);
			resultBean.setMessage("该业务组下的【新手卡号表】未创建,请联系相关人员进行创建");
			return resultBean;
		}

		// 获取新手卡号表最后一条记录
		newBeginCardNo = noviceCardDao.selectLastNoviceCardNo(tName);
		if (newBeginCardNo == null)
			newBeginCardNo = noviceCardBll.getNewBeginCard(bean.getBatchId());

		batchCodesList = noviceCardBll.GetBatchCodesList(tName, newBeginCardNo, bean.getCount(), bean.getGiftId());

		int insertResult = noviceGiftGenerateDao.insertSelective(bean, batchCodesList);
		if (insertResult > 0) {
			resultBean.setIsSuccess(true);
			resultBean.setMessage("生成成功");
			return resultBean;
		} else {
			resultBean.setIsSuccess(false);
			resultBean.setMessage("生成失败");
			return resultBean;
		}
	}

	/**
	 * 判断渠道是否已生成相应礼包(同一批次下统一渠道只能有一个礼包生成记录)
	 * 
	 * @param code
	 * @param channel
	 * @return
	 */
	private ResultBean<String> isChannelGiftHasGenerate(int batchId, String channel, int giftId) {
		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setIsSuccess(true);

		// 渠道为空同批次可以生成过个礼包
		if (channel == null || channel.equals("")) {
			return resultBean;
		}

		String[] channels = channel.split(";");
		for (int i = 0; i < channels.length; i++) {
			List<NoviceGiftGenerateInfoBean> listNoviceGiftInfo = noviceGiftGenerateDao.selectByBatchId(batchId);
			for (NoviceGiftGenerateInfoBean info : listNoviceGiftInfo) {
				if (info.getChannel().contains(channels[i]) && info.getGiftId() != giftId) {
					resultBean.setIsSuccess(false);
					resultBean
							.setMessage("投放对象【" + channels[i] + "】已经在该业务组使用了【" + info.getGiftName() + "】礼包，不能再使用其他礼包");
					break;
				}
			}
		}
		return resultBean;
	}

	/**
	 * 根据ID获取渠道新手卡记录
	 * 
	 * @param code
	 * @return
	 */
	public NoviceGiftGenerateBean selectByPrimaryKey(Integer code) {
		return noviceGiftGenerateDao.selectByPrimaryKey(code);
	}

	/**
	 * 根据礼包ID获取新手卡生成记录
	 * 
	 * @param giftId
	 * @return
	 */
	public List<NoviceGiftGenerateBean> selectByGiftId(Integer giftId) {
		return noviceGiftGenerateDao.selectByGiftId(giftId);
	}
}
