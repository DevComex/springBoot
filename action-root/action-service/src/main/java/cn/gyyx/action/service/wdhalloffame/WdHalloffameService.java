package cn.gyyx.action.service.wdhalloffame;

import java.util.List;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.bll.wdhalloffame.WdHalloffameBll;

public class WdHalloffameService {

	public ResultBean<String> saveexcel(List<List<Object>> listob) {
		ResultBean<String> result = new ResultBean<String>();
		StringBuffer mes =new StringBuffer("已存在账号如下:");
		WdHalloffameBll wdHalloffameBll = new WdHalloffameBll();
		/**
		 * 逐条读取excel表中数据,
		 * 进行查重,如果重复则记录到mes中.
		 * **/
		for (int i = 0; i < listob.size(); i++) {
			List<Object> lo = listob.get(i);
			String username = String.valueOf(lo.get(0));
			result = wdHalloffameBll.checkusername(username);
			if (result.getIsSuccess()) {				
				wdHalloffameBll.insertUserName(username);
			} else {
				mes=mes.append(username+"-");
				result.setIsSuccess(false);
			}		
		}
		if(mes.toString().length()>8){		
			result.setIsSuccess(false);
			result.setMessage("总条数为"+listob.size()+"-"+mes.toString());
		}
		return result;
	}

}
