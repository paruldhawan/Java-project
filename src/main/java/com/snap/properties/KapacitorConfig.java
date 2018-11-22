package com.snap.properties;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KapacitorConfig {
	
	private final static Logger logger = LoggerFactory.getLogger(KapacitorConfig.class);
	
	//#FIXME
	private static Properties properties;
	{

		try {
			properties = new Properties();
			String propertyFileName = "kapacitor.properties";
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName);
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				logger.error("File kapacitor.properties not found in classpath");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		

	}
	
	public static String getString(String key){
		return properties.getProperty(key);
	}
	
	public String getIP(String IP)
	{
		return properties.getProperty(IP);
	}
	   

}
