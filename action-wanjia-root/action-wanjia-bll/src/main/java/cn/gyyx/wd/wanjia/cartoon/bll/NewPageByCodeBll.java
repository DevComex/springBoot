package cn.gyyx.wd.wanjia.cartoon.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.wd.wanjia.cartoon.beans.ManhuaInfoBean;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaPage;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaBookMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaPageMapper;

@Service
public class NewPageByCodeBll {
	@Autowired
	private WanwdManhuaPageMapper wanwdManhuaPageMapper;
	@Autowired
	private WanwdManhuaBookMapper wanwdManhuaBookMapper;
	@Autowired
	private WanwdManhuaMapper wanwdManhuaMapper;
	
	public ManhuaInfoBean findNewPageByCode(Integer manhuaCode){
		ManhuaInfoBean infoBean = new ManhuaInfoBean();
		WanwdManhua manhua = wanwdManhuaMapper.selectByPrimaryKey(manhuaCode);
		WanwdManhuaBook book =wanwdManhuaBookMapper.findNewBookByManhuaCode(manhuaCode);
		if(book!=null){
			WanwdManhuaPage page=wanwdManhuaPageMapper.findNewPageByBookCode(book.getCode());
			if(page!=null){
				infoBean.setManhuaBookCode(book.getCode());
				infoBean.setManhuaBookName(book.getBookName());
				infoBean.setManhuaCode(manhuaCode);
				infoBean.setManhuaName(manhua.getTitle());
				infoBean.setManhuaPageCode(page.getCode());
				infoBean.setManhuaPageUrl(page.getPagePictureUrl());
			}
		}
		return infoBean;
	}
}
