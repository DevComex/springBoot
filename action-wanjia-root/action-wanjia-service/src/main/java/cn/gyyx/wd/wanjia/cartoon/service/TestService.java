package cn.gyyx.wd.wanjia.cartoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.wd.wanjia.cartoon.dao.WanwdPictureTbMapper;

@Service
public class TestService {

	@Autowired
	private WanwdPictureTbMapper mapper;

	public void select(int code) {
		// TODO Auto-generated method stub
		mapper.selectByPrimaryKey(code);
	}
	
}
