package cn.gyyx.action.dao.common;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.common.ActionCommonFormBean;
import cn.gyyx.action.beans.common.ActionCommonFormBean.Codeable;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class ActionCommonFormDAO implements IActionCommonFormBean {
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	@Override
	public void insertActionCommonFormBean(ActionCommonFormBean bean) {
		try (SqlSession session = getSession()) {
			IActionCommonFormBean mapper = session
					.getMapper(IActionCommonFormBean.class);
			mapper.insertActionCommonFormBean(bean);
			session.commit();
		}
	}

	@Override
	public ActionCommonFormBean selectActionCommonFormBeanBycode(int code) {
		try (SqlSession session = getSession()) {
			IActionCommonFormBean mapper = session
					.getMapper(IActionCommonFormBean.class);
			return mapper.selectActionCommonFormBeanBycode(code);
		}
	}

	@Override
	public void updateActionCommonFormBean(ActionCommonFormBean bean) {
		try (SqlSession session = getSession()) {
			IActionCommonFormBean mapper = session
					.getMapper(IActionCommonFormBean.class);
			mapper.updateActionCommonFormBean(bean);
			session.commit();
		}
	}

	@Override
	public void deleteActionCommonFormBeanBycode(int code) {
		try (SqlSession session = getSession()) {
			IActionCommonFormBean mapper = session
					.getMapper(IActionCommonFormBean.class);
			mapper.deleteActionCommonFormBeanBycode(code);
			session.commit();
		}
	}

	@Override
	public List<ActionCommonFormBean> selectActionCommonFormByActionCode(
			int actionCode) {
		try (SqlSession session = getSession()) {
			IActionCommonFormBean mapper = session
					.getMapper(IActionCommonFormBean.class);
			return mapper.selectActionCommonFormByActionCode(actionCode);
		}
	}

	@Override
	public List<ActionCommonFormBean> selectActionCommonFormByActionAndAccountCode(
			int actionCode, int accountCode) {
		try (SqlSession session = getSession()) {
			IActionCommonFormBean mapper = session
					.getMapper(IActionCommonFormBean.class);
			return mapper.selectActionCommonFormByActionAndAccountCode(actionCode,accountCode);
		}
	}

	// public <T extends Codeable> T selectActionCommonFormBeanBycode(int
	// code,Class<? extends T> clazz) {
	// try(SqlSession session = getSession()) {
	// IActionCommonFormBean mapper =
	// session.getMapper(IActionCommonFormBean.class);
	// ActionCommonFormBean form =
	// mapper.selectActionCommonFormBeanBycode(code);
	// T jsonBean = getObjFromJson(form.getJsonStr(),clazz);
	// jsonBean.setCode(form.getCode());
	// return jsonBean;
	// }
	// }

	@SuppressWarnings("unchecked")
	public <T extends Codeable> T convertFromJson(
			ActionCommonFormBean formBean, Class<? extends T> clazz) {
		try {
			JSONObject jsonObj = JSONObject.fromObject(formBean.getJsonStr());
			T result = (T) JSONObject.toBean(jsonObj, clazz);
			if (result != null) {
				result.setCode(formBean.getCode());
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public String convertToJson(Object o) {
		String str = null;
		try {
			str = JSONObject.fromObject(o).toString();
		} catch (Exception e) {

		}
		return str;
	}

}
