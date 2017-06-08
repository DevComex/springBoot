package cn.gyyx.action.service;

import org.slf4j.Logger;

import cn.gyyx.log.sdk.GYYXLoggerFactory;

public interface ILoggerService {
	Logger logger = GYYXLoggerFactory.getLogger(ILoggerService.class);
}
