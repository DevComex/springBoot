package cn.gyyx.playwd.utils;

import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import cn.gyyx.core.prop.PropertiesParser;
import cn.gyyx.core.prop.SimpleProperties;

/**
 * 版 权：光宇游戏 
 * 作 者：ChengLong 
 * 创建时间：2016年9月8日 下午2:01:00 
 * 描 述：加载配置
 */
public class GyyxPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,Properties props) {
		// 注意：spring 加载的文件 相同key会被 gyyx加载的替换掉
		Properties gyyxPros = SimpleProperties.getInstance().getProperties();
		Set<String> sets = gyyxPros.stringPropertyNames();
		for (String key : sets) {
			if("database.path".equals(key.trim())){
				String dataSql = SimpleProperties.getInstance().getStringProperty("database.path");
				Properties dataSqlProperties=new PropertiesParser(dataSql).getProperties();
				Set<String> dataSqlSets = dataSqlProperties.stringPropertyNames();
				for (String keyData : dataSqlSets) {
					props.setProperty(keyData, dataSqlProperties.getProperty(keyData));
				}
			}
			
			props.setProperty(key, gyyxPros.getProperty(key));
		}
		super.processProperties(beanFactoryToProcess, props);
	}
}
