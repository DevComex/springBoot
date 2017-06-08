package cn.gyyx.action.service.lottery;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.service.ILoggerService;

public interface IActionQualificationService extends ILoggerService {

	ResultBean<Boolean> add(int activityId, String userId, String type, String ip);
}
