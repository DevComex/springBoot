package cn.gyyx.action.service.userinfo;

import cn.gyyx.action.service.ILoggerService;

public interface IGameDataConvertService extends ILoggerService {

	boolean excute(int activityId, String userId, String ip);
}
