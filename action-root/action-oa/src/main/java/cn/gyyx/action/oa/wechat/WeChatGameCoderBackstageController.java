package cn.gyyx.action.oa.wechat;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.wechat.ConfigBean;
import cn.gyyx.action.beans.wechat.CustomBean;
import cn.gyyx.action.beans.wechat.OperateBean;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.bll.wechat.ConfigBLL;
import cn.gyyx.action.bll.wechat.CustomBLL;
import cn.gyyx.action.bll.wechat.OperateBLL;
import cn.gyyx.core.DataFormat;

@Controller
@RequestMapping("/wechatCoderBackstage")
public class WeChatGameCoderBackstageController {
	private static final Logger logger = LoggerFactory
			.getLogger(WeChatGameCoderBackstageController.class);
	private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
	private ConfigBLL configBLL = new ConfigBLL();
	private OperateBLL operateBLL = new OperateBLL();
	private CustomBLL customBLL = new CustomBLL();
	
	/**
	 * 开发人员配置页
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		List<ConfigBean> list = configBLL.getWechatConfig();
		model.addAttribute("configListJSON", DataFormat.objToJson(list));
		model.addAttribute("configList", list);
		model.addAttribute("count", list.size());
		model.addAttribute("listCount", list.size()%10==0?list.size()/10:list.size()/10	+1);
		return "wechat/wechatCoderIndex";
	}
	
	/**
	 * 配置操作1
	 * @param configCode
	 * @param model
	 * @return
	 */
	@RequestMapping("/configureIndex/step1")
	public String configureStep1(Integer configCode,Model model) {
		List<OperateBean> operateList = operateBLL.getOperateInfoByConfigCode(configCode);
		model.addAttribute("operateList", operateList);
		model.addAttribute("configCode", configCode);
		return "wechat/configureStep1";
	}
	
	/**
	 * 配置操作2
	 * @param configCode
	 * @param model
	 * @return
	 */
	@RequestMapping("/configureIndex/step2")
	public String configureStep2(Integer configCode,Model model) {
		List<CustomBean> customList = customBLL.getAllCustomInfoByConfigCode(configCode);
		for (CustomBean customBean : customList) {
			customBean.setOperateDesc(operateBLL.getOperateInfoByCode(customBean.getOperateCode()).getOperateDesc());
		}
		model.addAttribute("customList", customList);
		model.addAttribute("configCode", configCode);
		return "wechat/configureStep2";
	}
	
	/**
	 * 配置操作3
	 * @param configCode
	 * @param model
	 * @return
	 */
	@RequestMapping("/configureIndex/step3")
	public String configureStep3(Integer configCode,Model model) {
		List<OperateBean> operateList = operateBLL.getOperateInfoByConfigCode(configCode);
		model.addAttribute("operateList", operateList);
		model.addAttribute("configCode", configCode);
		return "wechat/configureStep3";
	}
	
	/**
	 * 配置操作4
	 * @param configCode
	 * @param model
	 * @return
	 */
	@RequestMapping("/configureIndex/step4")
	public String configureStep4(Integer configCode,Model model) {
		List<OperateBean> operateList = operateBLL.getOperateInfoByConfigCodeNoCount(configCode);
		model.addAttribute("operateList", operateList);
		model.addAttribute("configCode", configCode);
		return "wechat/configureStep4";
	}
	
	/**
	 * 获取所有配置信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getActivityConfigInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getActivityConfigInfo(Model model) {
		return DataFormat.objToJson(activityConfigBll.getActivityConfig());
	}
	
	/**
	 * 获取最大统计编号
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getMaxCodeFromConfig")
	@ResponseBody
	public Integer getMaxCodeFromConfig(Model model) {
		return configBLL.getMaxCodeFromConfig();
	}
	
	/**
	 * 增加统计配置
	 * @param weChatConfigBean
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/addWechatConfig", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addWechatConfig(ConfigBean weChatConfigBean,Model model){
		if(weChatConfigBean.getConfigName() == null || "".equals(weChatConfigBean.getConfigName())){
			return "统计名称不能为空！";
		}else{
			Integer count = configBLL.getWechatConfigByConfigName(weChatConfigBean.getConfigName());
			if(count == 0){
				weChatConfigBean.setIsOpen(true);
				configBLL.addWechatConfig(weChatConfigBean);
			}else{
				return "统计名称重复！";
			}
		}
		return "添加成功！";
	}
	
	/**
	 * 获取配置信息
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getWechatConfigInfoByCode", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getWechatConfigInfoByCode(Integer code,Model model) {
		return DataFormat.objToJson(configBLL.getWechatConfigInfoByCode(code));
	}
	
	/**
	 * 修改统计配置
	 * @param weChatConfigBean
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/updateWechatConfig", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String updateWechatConfig(ConfigBean weChatConfigBean,Model model){
		if(weChatConfigBean.getConfigName() == null || "".equals(weChatConfigBean.getConfigName())){
			return "统计名称不能为空！";
		}else{
			ConfigBean bean = configBLL.getWechatConfigInfoByCode(weChatConfigBean.getCode());
			Integer count = configBLL.getWechatConfigByConfigName(weChatConfigBean.getConfigName());
			if(count == 0){
				configBLL.updateWechatConfig(weChatConfigBean);
			}else if(count == 1 && bean.getConfigName().equals(weChatConfigBean.getConfigName())){
				configBLL.updateWechatConfig(weChatConfigBean);
			}else{
				return "统计名称重复！";
			}
		}
		return "修改成功！";
	}
	
	/**
	 * 编辑统计开启状态
	 * @param weChatConfigBean
	 * @param model
	 */
	@RequestMapping(value="/updateConfigIsOpen", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void updateConfigIsOpen(ConfigBean weChatConfigBean,Model model) {
		configBLL.updateConfigIsOpen(weChatConfigBean);
	}
	
	/**
	 * 增加操作信息
	 * @param operateBean
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/addWechatOperate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addWechatOperate(OperateBean operateBean,Model model){
		if(operateBean.getOperateType() == null || "".equals(operateBean.getOperateType())){
			return "操作类型不能为空！";
		}else if(operateBean.getOperateDesc() == null || "".equals(operateBean.getOperateDesc())){
			return "操作描述不能为空！";
		}else{
			Integer count = operateBLL.getCountByOperateType(operateBean);
			if(count == 0){
				operateBean.setInCount(true);
				operateBean.setInDetail(false);
				operateBean.setDelete(false);
				operateBLL.addOperateInfo(operateBean);
			}else{
				return "操作类型重复！";
			}
		}
		return "添加操作成功！";
	}
	
	/**
	 * 获取操作信息
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getWechatOperateInfoByCode", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getWechatOperateInfoByCode(Integer code,Model model) {
		return DataFormat.objToJson(operateBLL.getOperateInfoByCode(code));
	}
	
	/**
	 * 修改操作信息
	 * @param operateBean
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/updateWechatOperate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String updateWechatOperate(OperateBean operateBean,Model model){
		if(operateBean.getOperateType() == null || "".equals(operateBean.getOperateType())){
			return "操作类型不能为空！";
		}else if(operateBean.getOperateDesc() == null || "".equals(operateBean.getOperateDesc())){
			return "操作描述不能为空！";
		}else{
			OperateBean bean = operateBLL.getOperateInfoByCode(operateBean.getCode());
			Integer count = operateBLL.getCountByOperateType(operateBean);
			if(count == 0){
				operateBLL.updateOperateInfo(operateBean);
			}else if(count > 0 && bean.getOperateType().equals(operateBean.getOperateType())){
				operateBLL.updateOperateInfo(operateBean);
			}else{
				return "操作类型重复！";
			}
		}
		return "修改操作成功！";
	}
	
	/**
	 * 删除操作
	 * @param code
	 * @param model
	 */
	@RequestMapping(value="/updateDeleteFlag", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void updateDeleteFlag(Integer code,Model model) {
		operateBLL.updateDeleteFlag(code);
	}
	
	/**
	 * 根据操作编号获取自定义参数
	 * @param configCode
	 * @param operateCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getCustomInfoByOperate", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getCustomInfoByOperate(Integer configCode,Integer operateCode,Model model) {
		List<CustomBean> list = customBLL.getAllCustomInfoByOperate(operateCode);
		CustomBean bean1 = new CustomBean(configCode,operateCode, "nickName", "昵称", false);
		CustomBean bean2 = new CustomBean(configCode,operateCode, "partakeTime", "时长/s", false);
		list.add(bean1);
		list.add(bean2);
		return DataFormat.objToJson(list);
	}
	
	/**
	 * 修改自定义参数
	 * @param operateBean
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/updateWechatCustomPara", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String updateWechatCustomPara(OperateBean operateBean,Model model){
		operateBLL.updateWechatCustomPara(operateBean);
		return "编辑参数成功！";
	}
	
	/**
	 * 获取自定义参数信息
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getCustomInfoByCode", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getCustomInfoByCode(Integer code,Model model) {
		return DataFormat.objToJson(customBLL.getCustomInfoByCode(code));
	}
	
	/**
	 * 根据配置编号获取操作信息
	 * @param configCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getOperateConfigInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getOperateConfigInfo(Integer configCode,Model model) {
		return DataFormat.objToJson(operateBLL.getOperateInfoByConfigCode(configCode));
	}
	
	/**
	 * 增加自定义参数
	 * @param customBean
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/addWechatPara", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addWechatPara(CustomBean customBean,Model model){
		if(customBean.getCustomType() == null || "".equals(customBean.getCustomType())){
			return "参数不能为空！";
		}else if(customBean.getCustomDesc() == null || "".equals(customBean.getCustomDesc())){
			return "参数名不能为空！";
		}else{
			Integer count = customBLL.getCustomCountByCustomType(customBean.getCustomType());
			if(count == 0){
				customBean.setDelete(false);
				customBLL.addCustomInfo(customBean);
			}else{
				return "参数重复！";
			}
		}
		return "添加参数成功！";
	}
	
	/**
	 * 编辑参数
	 * @param customBean
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/updateWechatPara", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String updateWechatPara(CustomBean customBean,Model model){
		if(customBean.getCustomType() == null || "".equals(customBean.getCustomType())){
			return "参数不能为空！";
		}else if(customBean.getCustomDesc() == null || "".equals(customBean.getCustomDesc())){
			return "参数名不能为空！";
		}else{
			CustomBean bean = customBLL.getCustomInfoByCode(customBean.getCode());
			Integer count = customBLL.getCustomCountByCustomType(customBean.getCustomType());
			if(count == 0){
				customBLL.updateCustomInfo(customBean);
			}else if(count > 0 && bean.getCustomType().equals(customBean.getCustomType())){
				customBLL.updateCustomInfo(customBean);
			}else{
				return "参数重复！";
			}
		}
		return "修改参数成功！";
	}
	
	/**
	 * 删除自定义参数
	 * @param code
	 * @param model
	 */
	@RequestMapping(value="/deleteCustomInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void deleteCustomInfo(Integer code,Model model) {	
		OperateBean bean = operateBLL.getOperateInfoByCode(customBLL.getCustomInfoByCode(code).getOperateCode());
		String str = "&"+customBLL.getCustomInfoByCode(code).getCustomType()+"={参数}";
		if(bean.getOperatePara().indexOf(str) > -1){
			bean.setOperatePara(bean.getOperatePara().replace(str, ""));
		}
		operateBLL.updateWechatCustomPara(bean);
		customBLL.deleteCustomInfo(code);
	}
	
	/**
	 * 修改统计状态
	 * @param OperateBean
	 * @param model
	 */
	@RequestMapping(value="/updateOperateInCount", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void updateOperateInCount(OperateBean OperateBean,Model model) {
		operateBLL.updateOperateInCount(OperateBean);
	}
	
	/**
	 * 修改详细统计状态
	 * @param OperateBean
	 * @param model
	 */
	@RequestMapping(value="/updateOperateInDetail", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void updateOperateInDetail(OperateBean OperateBean,Model model) {
		operateBLL.updateOperateInDetail(OperateBean);
	}
}
