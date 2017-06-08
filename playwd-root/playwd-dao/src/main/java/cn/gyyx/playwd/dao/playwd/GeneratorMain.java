package cn.gyyx.playwd.dao.playwd;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年10月31日 上午10:48:37
 * 描        述：代码生成器 可以run 运行 也可以 项目上  mvn mybatis-generator:generate
 */
public class GeneratorMain {
	public static void main(String[] args) throws Exception {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		InputStream configFile = GeneratorMain.class.getClassLoader().getResourceAsStream("generator/PlaywdGenerator.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
				callback, warnings);
		myBatisGenerator.generate(null);
		System.out.println("生成完成！");
	}
}
