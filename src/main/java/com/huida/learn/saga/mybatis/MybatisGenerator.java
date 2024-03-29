package com.huida.learn.saga.mybatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: huida
 * @date: 2024/3/28
 **/
public class MybatisGenerator {

	private static final Log logger = LogFactory.getLog(MybatisGenerator.class);

	public static void main(String[] args) {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		String genCfg = "/Users/huidamao/maomao/git/saga-demo/src/main/java/com/huida/learn/saga/mybatis/generatorConfig.xml";
		File configFile = new File(genCfg);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		org.mybatis.generator.config.Configuration config = null;
		try {
			config = cp.parseConfiguration(configFile);
		} catch (IOException e) {
			logger.error("MybatisGenerator has IOException" , e);
		} catch (XMLParserException e) {
			logger.error("MybatisGenerator has XMLParserException" , e);
		}
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = null;
		try {
			myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		} catch (InvalidConfigurationException e) {
			logger.error("MybatisGenerator has InvalidConfigurationException", e);
		}
		try {
			if(myBatisGenerator != null){
				myBatisGenerator.generate(null);
			}
		} catch (SQLException e) {
			logger.error("MybatisGenerator has SQLException", e);
		} catch (IOException e) {
			logger.error("MybatisGenerator has IOException 2", e);
		} catch (InterruptedException e) {
			logger.error("MybatisGenerator has InterruptedException", e);
		}
	}
}
