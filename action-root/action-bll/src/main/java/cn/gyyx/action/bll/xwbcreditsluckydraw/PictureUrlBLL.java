package cn.gyyx.action.bll.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.PictureUrlBean;
import cn.gyyx.action.dao.xwbcreditsluckydraw.PictureUrlDAO;

public class PictureUrlBLL {
	private PictureUrlDAO pictureUrlDAO = new PictureUrlDAO();
	public PictureUrlBean getPictureUrlByPrizeCode(Integer prizeCode){
		return pictureUrlDAO.getPictureUrlByPrizeCode(prizeCode);
		
	}
	public PictureUrlBean getPictureUrlByPrizeFlag(Integer prizeFlag){
		return pictureUrlDAO.getPictureUrlByPrizeFlag(prizeFlag);
		
	}
	public List<PictureUrlBean> getPrizeByFlag(Integer prizeFlag){
		return pictureUrlDAO.getPrizeByFlag(prizeFlag);
		
	}
	public Integer updatePictureUrlBean(PictureUrlBean pictureUrlBean){
		return pictureUrlDAO.updatePictureUrlBean(pictureUrlBean);
		
	}
	public PictureUrlBean getPrizeByPrizeCode(Integer prizeCode){
		return pictureUrlDAO.getPrizeByPrizeCode(prizeCode);
		
	}
}
