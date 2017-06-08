package cn.gyyx.action.dao.xwbcreditsluckydraw;

import java.util.List;

import cn.gyyx.action.beans.xwbcreditsluckydraw.PictureUrlBean;

public interface IPictureUrlMapper {
	public PictureUrlBean getPictureUrlByPrizeCode(Integer prizeCode);
	public PictureUrlBean getPictureUrlByPrizeFlag(Integer prizeFlag);
	public List<PictureUrlBean> getPrizeByFlag(Integer prizeFlag);
	public Integer updatePictureUrlBean(PictureUrlBean pictureUrlBean);
	public PictureUrlBean getPrizeByPrizeCode(Integer prizeCode);
}
